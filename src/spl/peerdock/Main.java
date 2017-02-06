package spl.peerdock;

import java.io.*;
import java.util.Scanner;

public class Main {

    static boolean is64bit;
    static String version = "17.4";
    static String update_url = "https://www.peerdock.co/update";

    public static void main(String args[]) throws Exception {
        if (System.getProperty("os.name").contains("Windows")) {
            is64bit = (System.getenv("ProgramFiles(x86)") != null);
        } else {
            is64bit = (System.getProperty("os.arch").indexOf("64") != -1);
        }

        File f = new File("/Peerdock/");
        if(!f.exists()){
            Boolean rs = f.mkdir();
            if(!rs){
                System.out.println("<!> Impossible to initialize Peerdock folder.");
                System.exit(1);
            }
        }

        File s = new File("/Peerdock/Sources/");
        if(!s.exists()){
            Boolean rs = s.mkdir();
            if(!rs){
                System.out.println("<!> Impossible to initialize Sources folder.");
                System.exit(1);
            }
        }

        menu();
    }

    public static void header() throws Exception{
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        System.out.println("");
        System.out.println("   ▄███████▄    ▄████████    ▄████████    ▄████████  ███████▄   ▄██████▄   ▄████████    ▄█   ▄█▄ \n" +
                "   ███    ███   ███    ███   ███    ███   ███    ███ ███   ▀███ ███    ███ ███    ███   ███ ▄███▀ \n" +
                "   ███    ███   ███    █▀    ███    █▀    ███    ███ ███    ███ ███    ███ ███    █▀    ███ ██▀   \n" +
                "   ███    ███  ▄███▄▄▄      ▄███▄▄▄      ▄███▄▄▄▄██▀ ███    ███ ███    ███ ███         ▄█████▀    \n" +
                " ▀█████████▀  ▀▀███▀▀▀     ▀▀███▀▀▀     ▀▀███▀▀▀▀▀   ███    ███ ███    ███ ███        ▀▀█████▄    \n" +
                "   ███          ███    █▄    ███    █▄  ▀███████████ ███    ███ ███    ███ ███    █▄    ███ ██▄   \n" +
                "   ███          ███    ███   ███    ███   ███    ███ ███   ▄███ ███    ███ ███    ███   ███ ▀███▄ \n" +
                "  ▄████▀        ██████████   ██████████   ███    ███ ████████▀   ▀██████▀  ████████▀    ███   ▀█▀ \n" +
                "                                          ███    ███                                    ▀         "
        );
    }

    private static void credit(){
        System.out.println("\nPeerdock is a software developed by Victor Lourme");
        System.out.println("Please take care of sources and use trusted repositories.");
        System.out.println("We are not responsible of malwares, hacks or others viruses.\n");
        System.out.println("Website : https://www.peerdock.co");
        System.out.println("Trusted sources : https://packages.peerdock.co\n");
        System.out.println("Source, repository means a list of packages in XML.");
    }

    public static void next()
    {
        System.out.println("\n--> Press [enter] to continue...");
        try
        {
            System.in.read();
            Main.menu();
        } catch (Exception e){

        }
    }

    public static void menu() throws Exception{
        header();
        Scanner in = new Scanner(System.in);
        System.out.println("--> Welcome in Peerdock " + version + "\n");

        System.out.println("═╗ 1. Install package");
        System.out.println(" ║ 2. Remove package");
        System.out.println(" ║ 3. Replace actual source");
        System.out.println(" ║ 4. Update actual source");
        System.out.println(" ║ 5. List sources available");
        System.out.println(" ║ 6. List installed package(s)");
        System.out.println(" ║ 7. List available package(s) in actual source");
        System.out.println(" ║ 8. Show credits");
        System.out.println(" ║ 9. Check for Peerdock update");
        System.out.println("═╝ 10. Exit program\n");

        System.out.print("--> Select a option : ");
        int option = in.nextInt();

        switch (option) {
            default:
                header();
                System.out.println("--> Please type a correct option (between 1 and 8)");
                next();
                menu();
                break;
            case 4:
                header();
                Package.update();
                next();
                menu();
                break;
            case 6:
                header();
                Package.list();
                next();
                menu();
                break;
            case 5:
                header();
                Package.pkgls();
                next();
                menu();
                break;
            case 1:
                header();
                System.out.print("--> Type package to install : ");
                String i = in.next();
                header();
                Installer installation = new Installer();
                installation.install(i);
                next();
                menu();
                break;
            case 2:
                header();
                System.out.print("--> Type package to remove : ");
                String r = in.next();
                header();
                Package.remove(r);
                next();
                menu();
                break;
            case 3:
                header();
                System.out.println("--> Type your source : ");
                System.out.print("--> ");
                String so = in.next();
                header();
                Package.source(so);
                next();
                menu();
                break;
            case 10:
                System.exit(1);
                break;
            case 8:
                header();
                credit();
                next();
                menu();
                break;
            case 7:
                header();
                Package.slist();
                next();
                menu();
                break;
            case 9:
                header();
                Package.checkupdate();
                next();
                menu();
                break;
        }
    }
}
