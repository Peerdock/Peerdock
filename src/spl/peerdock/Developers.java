package spl.peerdock;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;

public class Developers {

    public static Boolean search(String pkg){
        File xmlPath = new File("/Peerdock/Sources/spkg-peerdock.xml");
        if(!xmlPath.exists()){
            System.out.println("<!> Error ! Please provide a source.xml");
            System.exit(1);
        }

        // Getting XML parser
        Element source = null;
        try {
            SAXReader xmlReader = new SAXReader();
            Document doc = xmlReader.read(xmlPath);
            source = doc.getRootElement();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        try {
            return source.element(pkg).getText() != null;
        } catch (NullPointerException e){
            return false;
        }
    }

}
