package spl.peerdock;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

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
                        System.out.println("<+> Error ! Can't make " + name + " folder in Peerdock folder.");
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
                    System.out.println("<!> Error : " + e.getMessage());
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
                        System.out.println("<!> Error ! Temporary file was not removed.");
                    }
                }

                System.out.println("\n<+> " + name + " was installed !");
                System.out.println("<+> Find it into /Peerdock/" + name + "/ folder");
            }
        });

        Thread animation = new Thread(new Runnable() {
            public void run() {
                long startTime = System.currentTimeMillis();
                String anim = "|/-\\";
                int x = 0;
                while (!finish) {
                    long estimatedTime = System.currentTimeMillis() - startTime;
                    String time = String.format("%d hours, %d min, %d sec",
                            TimeUnit.MILLISECONDS.toHours(estimatedTime),
                            TimeUnit.MILLISECONDS.toMinutes(estimatedTime),
                            TimeUnit.MILLISECONDS.toSeconds(estimatedTime) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(estimatedTime))
                    );
                    String data = null;
                    try {
                        Long size = fos.getChannel().size();
                        String position = humanReadableByteCount(size, true);
                        data = "\r" + "<+> Downloading... " + anim.charAt(x % anim.length()) + " [" + time + "] " + position;
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
                        System.out.println("<!> Error : " + a.getMessage());
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
            System.out.println("<!> Error : " + e.getMessage());
        }
    }

    void install(String pkgs[]) throws Exception{
        File xmlPath = new File("/Peerdock/Sources/spkg-peerdock.xml");
        if(!xmlPath.exists()){
            System.out.println("<!> Error ! Please provide a source.xml");
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
        for (int i = 1; i < pkgs.length; i++) {
            if (pkg.element(pkgs[i]) == null) {
                System.out.println("<!> Error ! Can't find " + pkgs[i]);
                System.exit(1);
            }
        }

        // Checking errors
        System.out.print("<+> All packages exists ! Install [Y/N] +> ");
        Scanner in = new Scanner(System.in);
        if (in.next().equalsIgnoreCase("y")) {
            cont = true;
        } else {
            cont = false;
        }

        // If all good, install
        if(cont) {
            for (int i = 1; i < pkgs.length; i++) {
                if (pkg.element(pkgs[i]) != null) {
                    Installer.error = false;
                    Installer.pkg = pkgs[i];
                    String version = pkg.element(pkgs[i]).element("version").getText();
                    String url = pkg.element(pkgs[i]).element("url").getText();
                    String unzip = pkg.element(pkgs[i]).element("unzip").getText();
                    URL hp = null;
                    try {
                        hp = new URL(url);
                    } catch (Exception e) {
                        System.out.println("<!> Error : " + e.getMessage());
                    }
                    pkgz = ((Installer.getFileSize(hp) / 1000) / 1000);
                    System.out.println("\n<+> Installing " + pkgs[i]);
                    System.out.println(" |> Version : " + version);
                    System.out.println(" |> Size : " + pkgz + "Mb");
                    System.out.println(" |> Source : " + url);
                    System.out.println(" |> ZipPKG : " + unzip);
                    download(url, pkgs[i], unzip);
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