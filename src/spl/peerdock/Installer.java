package spl.peerdock;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.spi.LocaleNameProvider;

class Installer {
    public static Boolean error = null;
    private static String pkg;
    private static FileOutputStream fos;
    private boolean cont;
    static boolean finish = false;
    private static double pkgz;

    public static int getFileSize(URL url) throws NullPointerException{
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("HEAD");
            conn.getInputStream();
            return conn.getContentLength();
        } catch (IOException e) {
            return -1;
        } finally {
            conn.disconnect();
        }
    }

    private static void download(String url, String name, String unzip) throws Exception {
        Thread download = new Thread(new Runnable() {
            public void run() {
                finish = false;
                String extension = null;

                if(url.contains(".")) {
                    extension = url.substring(url.lastIndexOf("."));
                }

                File f = new File("/Peerdock/" + name);
                if(!f.exists()) {
                    Boolean result = f.mkdir();
                    if(!result){
                        System.out.println("<+> " + Language.lang.get("init-folder-error"));
                    }
                }

                SecureConnection.TrustAll();

                try {
                    URL website = new URL(url);
                    ReadableByteChannel rbc = Channels.newChannel(website.openStream());
                    fos = new FileOutputStream("/Peerdock/" + name + "/" + name + extension);
                    fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                    fos.close();
                    finish = true;
                } catch (IOException e){
                    System.out.println("<!> " + Language.lang.get("error") + e.getMessage());
                }

                if(unzip.equalsIgnoreCase("true")) {
                    String source = "/Peerdock/" + name + "/" + name + extension;
                    String destination = "/Peerdock/" + name + "/";

                    try {
                        ZipFile zipFile = new ZipFile(source);
                        zipFile.extractAll(destination);
                    } catch (ZipException e) {
                        e.printStackTrace();
                    }
                } else if (unzip.equalsIgnoreCase("true/rm-tmp")){
                    String source = "/Peerdock/" + name + "/" + name + extension;
                    String destination = "/Peerdock/" + name + "/";

                    try {
                        ZipFile zipFile = new ZipFile(source);
                        zipFile.extractAll(destination);
                    } catch (ZipException e) {
                        e.printStackTrace();
                    }

                    File tmp = new File("/Peerdock/" + name + "/" + name + extension);
                    Boolean rsp = tmp.delete();
                    if(!rsp){
                        System.out.println("<!> " + Language.lang.get("tmprm-error"));
                    }
                }

                System.out.println("\n<+> " + name + Language.lang.get("installed"));
                System.out.println("<+> " + Language.lang.get("folder-installed") + " /Peerdock/" + name + "/ folder");
            }
        });

        Thread animation = new Thread(new Runnable() {
            public void run() {
                long startTime = System.currentTimeMillis();
                String anim = "|/-\\";
                int x = 0;
                while (!finish) {
                    long estimatedTime = System.currentTimeMillis() - startTime;
                    String time = String.format("%d " + Language.lang.get("hours") + ", %d " + Language.lang.get("minutes") + ", %d " + Language.lang.get("seconds"),
                            TimeUnit.MILLISECONDS.toHours(estimatedTime),
                            TimeUnit.MILLISECONDS.toMinutes(estimatedTime),
                            TimeUnit.MILLISECONDS.toSeconds(estimatedTime) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(estimatedTime))
                    );
                    String data = null;
                    try {
                        Long size = fos.getChannel().size();
                        String position = humanReadableByteCount(size, true);
                        data = "\r" + "<+> " + Language.lang.get("downloading") + anim.charAt(x % anim.length()) + " [" + time + "] " + position;
                    } catch (Exception e){
                        /*
                        DISABLED : Echo null for download size progress
                        System.out.println("<!> Error " + e.getMessage());
                         */
                    }
                    try {
                        System.out.write(data.getBytes());
                    } catch (Exception b) {
                        /*
                        DISABLED : Echo null for download size progress
                        System.out.println("<!> Error : " + b.getMessage());
                        */
                    }
                    try {
                        Thread.sleep(50);
                        x++;
                    } catch (Exception a) {
                        System.out.println("<!> " + Language.lang.get("error") + a.getMessage());
                    }
                }
            }
        });

        download.start();
        animation.start();

        try {
            download.join();
            animation.join();
        } catch (InterruptedException e) {
            System.out.println("<!> Error : " + Language.lang.get("error") + e.getMessage());
        }
    }

    void install(String ipkg) throws Exception{
        File xmlPath = new File("/Peerdock/Sources/spkg-peerdock.xml");
        if(!xmlPath.exists()){
            System.out.println("<!> " + Language.lang.get("no-xml"));
            System.exit(1);
        }

        // Getting XML parser
        Element pkg = null;
        try {
            SAXReader xmlReader = new SAXReader();
            Document doc = xmlReader.read(xmlPath);
            pkg = doc.getRootElement();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        // Testing if all packages exists...
        if (pkg.element(ipkg) == null) {
            System.out.println("<!> " + Language.lang.get("install-not-exists") + ipkg);
            System.exit(1);
        }

        // Checking errors
        System.out.print("<+> " + Language.lang.get("package-exists-install"));
        Scanner in = new Scanner(System.in);
        if (in.next().equalsIgnoreCase("y")) {
            cont = true;
        } else {
            cont = false;
        }

        // If all good, install
        if(cont) {
            if (pkg.element(ipkg) != null) {
                Installer.error = false;
                Installer.pkg = ipkg;
                String version = pkg.element(ipkg).element("version").getText();
                String arch64 = pkg.element(ipkg).element("arch64").getText();
                String arch32 = pkg.element(ipkg).element("arch32").getText();
                String website = null;
                String note = null;
                String unzip = pkg.element(ipkg).element("unzip").getText();
                String url = null;
                if(Main.is64bit){
                    url = arch64;
                } else {
                    url = arch32;
                }
                try {
                    if (pkg.element(ipkg).element("note").getText() != null) {
                        note = pkg.element(ipkg).element("note").getText();
                    }
                    if (pkg.element(ipkg).element("website").getText() != null) {
                        website = pkg.element(ipkg).element("website").getText();
                    }
                } catch (NullPointerException e){
                }
                URL hp = null;
                try {
                    hp = new URL(url);
                } catch (Exception e) {
                    System.out.println("<!> " + Language.lang.get("error") + e.getMessage());
                }

                String tr_url = url;
                if(url.length() >= 60){
                    tr_url = url.substring(0, 60) + "...";
                }

                pkgz = ((Installer.getFileSize(hp) / 1000) / 1000);
                System.out.println("\n<+> " + Language.lang.get("installing") + ipkg);
                System.out.println(" |> " + Language.lang.get("version") + version);
                System.out.println(" |> " + Language.lang.get("size") + pkgz + "Mb");
                System.out.println(" |> " + Language.lang.get("from-source") + tr_url);
                if(note != null) {
                    System.out.println(" |> " + Language.lang.get("note") + note);
                }
                if(Main.is64bit){
                    System.out.println(" |> " + Language.lang.get("arch") +" : 64-bit");
                } else {
                    System.out.println(" |> " + Language.lang.get("arch") +" : 32-bit");
                }
                System.out.println(" |> " + Language.lang.get("unzip") + unzip);
                download(url, ipkg, unzip);
                if(website != null) {
                    System.out.print("<+> " + Language.lang.get("provider-website"));
                    if (in.next().equalsIgnoreCase("y")) {
                        Desktop.getDesktop().browse(new URL(website).toURI());
                    }
                }
            }
        } else {
            System.exit(1);
        }
    }
    public static String humanReadableByteCount(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }
}