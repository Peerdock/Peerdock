package spl.peerdock;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.regex.*;

class Package {
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
                    System.out.println(" |> Package : " + directories[i]);
                } catch (ArrayIndexOutOfBoundsException e){
                    System.out.println("<!> Error : " + e.getMessage());
                }
            }
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

        File xmlPath = new File("/Peerdock/source.xml");
        SAXReader xmlReader = new SAXReader();
        Document doc = xmlReader.read(xmlPath);

        Element pkg = doc.getRootElement();
        String update = pkg.attribute("update").getText();

        source(update);
    }
    static boolean remove(String pkgs[]) throws Exception {
        Scanner in = new Scanner(System.in);

        for (int i = 1; i < pkgs.length; i++) {
            File pkg = new File("/Peerdock/" + pkgs[i]);
            if (!pkg.exists()) {
                System.out.println("<!> Error ! Can't find " + pkgs[i]);
                System.exit(1);
            }
        }

        System.out.print("<+> Package found ! Remove [Y/N] +> ");

        if(in.next().equalsIgnoreCase("y")){
            for (int i = 1; i < pkgs.length; i++) {
                delete(new File("/Peerdock/" + pkgs[i]));
                System.out.println("<+> " + pkgs[i] + " was deleted.");
            }
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

        URL xml = new URL(arg);
        URLConnection yc = xml.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        yc.getInputStream()));
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            result = result + inputLine + "\n" ;
        }

        Pattern pattern = Pattern.compile(result);
        Matcher matcher = pattern.matcher("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        Boolean m1 = matcher.matches();
        Matcher matcher2 = pattern.matcher("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        Boolean m2 = matcher.matches();
        
        if(m1 || m2){
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
