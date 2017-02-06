package spl.peerdock;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

class Package {
    public static void pkgls() throws Exception{
        System.out.println("<+> Loading sources available...");
        SecureConnection.TrustAll();
        URL xml = new URL("https://packages.peerdock.co/FileIndex.xml");

        SAXReader xmlReader = new SAXReader();
        Document doc = xmlReader.read(xml);

        System.out.println("<+> Available sources...");

        Element pkg = doc.getRootElement();
        Map<Integer, String> map = new HashMap<Integer, String>();
        int increment = 1;
        for ( Iterator i = pkg.elementIterator(); i.hasNext(); ) {
            Element element = (Element) i.next();
            try {
                map.put(increment, element.getText().toString());
                System.out.println(" |> " + increment + ". "+ element.attribute("name").getText().toString());
            } catch (ArrayIndexOutOfBoundsException e){

            }
            increment++;
        }

        Scanner in = new Scanner(System.in);
        System.out.print("\n<+> Please select a source to add : ");
        int source = in.nextInt();
        Main.header();
        source(map.get(source));
    }

    public static void checkupdate() throws Exception{
        SecureConnection.TrustAll();

        InputStream in = new URL(Main.update_url).openStream();
        String version = "";

        try {
            version = IOUtils.toString(in);
        } finally {
            IOUtils.closeQuietly(in);
        }

        if(!version.equals(Main.version)){
            System.out.println("<+> A new update is available : " + version + "!");
            Desktop.getDesktop().browse(new URL("https://www.peerdock.co/").toURI());
        } else {
            System.out.println("<+> No update available");
        }
    }

    public static void list(){
        File file = new File("/Peerdock/");
        String[] directories = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });
        System.out.println("<+> Installed packages...");
        if (directories != null) {
            for(int i = 0; i <= directories.length; i++){
                try {
                    if(!directories[i].equalsIgnoreCase("sources")){
                        System.out.println(" |> Package : " + directories[i]);
                    }
                } catch (ArrayIndexOutOfBoundsException e){
                }
            }
        }
    }

    public static void slist() throws DocumentException{
        File xmlPath = new File("/Peerdock/Sources/spkg-peerdock.xml");

        if(!xmlPath.exists()){
            System.out.println("<!> Please install a source before fetch it.");
            Main.next();
            try {
                Main.menu();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        SAXReader xmlReader = new SAXReader();
        Document doc = xmlReader.read(xmlPath);

        System.out.println("<+> Available packages...");

        Element pkg = doc.getRootElement();
        for ( Iterator i = pkg.elementIterator(); i.hasNext(); ) {
            Element element = (Element) i.next();
            System.out.println(" |> " + element.getName().toString() + " - " + element.element("version").getData().toString());
        }
    }

    private static void delete(File file) throws Exception{
        if(file.isDirectory()){

            String[] ts = file.list();
            if(ts.length==0){
                Boolean rs = file.delete();
                if(!rs){
                    System.out.println("<!> Can't delete a file.");
                }
            }else{

                //list all the directory contents
                String files[] = file.list();

                for (String temp : files) {
                    //construct the file structure
                    File fileDelete = new File(file, temp);

                    //recursive delete
                    delete(fileDelete);
                }

                //check the directory again, if empty then delete it
                String[] st = file.list();
                if(st.length==0){
                    Boolean rs = file.delete();
                    if(!rs){
                        System.out.println("<!> Can't delete a file.");
                    }
                }
            }
        }else{
            Boolean rs = file.delete();
            if(!rs){
                System.out.println("<!> Can't delete a file.");
            }
        }
    }
    static void update() throws Exception {
        System.out.println("<+> Retrieving update source URL... ");

        File xmlPath = new File("/Peerdock/Sources/spkg-peerdock.xml");

        if(!xmlPath.exists()){
            System.out.println("<!> Please install a source before update it.");
            Main.next();
            Main.menu();
        }

        SAXReader xmlReader = new SAXReader();
        Document doc = xmlReader.read(xmlPath);

        Element pkg = doc.getRootElement();
        String update = pkg.attribute("update").getText();

        source(update);
    }
    static boolean remove(String ipkg) throws Exception {
        Scanner in = new Scanner(System.in);


        File pkg = new File("/Peerdock/" + ipkg);
        if (!pkg.exists()) {
            System.out.println("<!> Error ! Can't find " + ipkg);
            System.exit(1);
        }

        System.out.print("<+> Package found ! Remove [Y/N] +> ");

        if(in.next().equalsIgnoreCase("y")){
                delete(new File("/Peerdock/" + ipkg));
                System.out.println("<+> " + ipkg + " was deleted.");
        } else {
            System.out.println("<!> Package delete aborted.");
        }

        return true;
    }

    static boolean reset() throws Exception {
        System.out.println("<+> Fetch Peerdock directory");
        File pkg = new File("/Peerdock");
        if(pkg.exists() && pkg.isDirectory()){
            System.out.print("<+> Peerdock directory exists ! Remove [Y/N] +> ");
            Scanner in = new Scanner(System.in);
            if(in.next().equalsIgnoreCase("y")){
                delete(pkg);
                System.out.println("<+> Peerdock was deleted.");
            } else {
                System.out.println("<!> Peerdock was not deleted.");
            }

        } else {
            System.out.println("<!> This package is not installed.");
        }
        return true;
    }

    static void source(String arg) throws Exception {
        System.out.println("<+> Fetching package list");
        System.out.println(" |> URL : " + arg);
        String result = "";

        SecureConnection.TrustAll();


        URL xml = new URL(arg);
        URLConnection yc = xml.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        yc.getInputStream()));
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            result = result + inputLine + "\n" ;
        }
        
        if(StringUtils.contains(result, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>")){
            System.out.println("<+> Saving package list file...");
            save(result);
            System.out.println("<+> Saved package list file !");
        } else {
            System.out.println("<+> Error ! Package list must be a XML file.");
        }

        in.close();
    }

    private static void save(String rsp) throws IOException {
        File dir_home = new File("/Peerdock/Sources/");
        if(!dir_home.exists()) {
            Boolean result = dir_home.mkdir();
            if(!result){
                System.out.println("<+> Error ! Can't make Peerdock folder at root.");
            }
        }

        String id = "spkg-peerdock.xml";

        FileUtils.writeStringToFile(new File("/Peerdock/sources/" + id), rsp);
    }
}
