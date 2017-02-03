package spl.peerdock;

import java.io.*;
import java.util.Scanner;

public class Main {

    static boolean is64bit;

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

        header();
        Scanner in = new Scanner(System.in);
        System.out.println("--> Welcome in Peerdock 17.3\n");

        System.out.println("═╗ 1. Install package");
        System.out.println(" ║ 2. Remove package");
        System.out.println(" ║ 3. Replace actual source");
        System.out.println(" ║ 4. Update actual source");
        System.out.println(" ║ 5. List installed package(s)");
        System.out.println(" ║ 6. List available package(s) in actual source");
        System.out.println(" ║ 7. Exit program");
        System.out.println("═╝ 8. Show credits\n");

        System.out.print("--> Select a option : ");
        int option = in.nextInt();

        switch (option) {
            default:
                header();
                System.out.println("--> Please type a correct option (between 1 and 8)");
                break;
            case 4:
                header();
                Package.update();
                break;
            case 5:
                header();
                Package.list();
                break;
            case 1:
                header();
                System.out.print("--> Type package to install : ");
                String i = in.next();
                header();
                Installer installation = new Installer();
                installation.install(i);
                break;
            case 2:
                header();
                System.out.print("--> Type package to remove : ");
                String r = in.next();
                header();
                Package.remove(r);
                break;
            case 3:
                header();
                System.out.println("--> Type your source : ");
                System.out.print("--> ");
                String so = in.next();
                header();
                Package.source(so);
                break;
            case 7:
                System.exit(1);
                break;
            case 8:
                header();
                credit();
                break;
            case 6:
                header();
                Package.slist();
                break;
        }
    }

    private static void header() throws IOException, InterruptedException{
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        System.out.println("");
        System.out.print(" ██████╗ ███████╗███████╗██████╗ ██████╗  ██████╗  ██████╗██╗  ██╗\n" +
                " ██╔══██╗██╔════╝██╔════╝██╔══██╗██╔══██╗██╔═══██╗██╔════╝██║ ██╔╝\n" +
                " ██████╔╝█████╗  █████╗  ██████╔╝██║  ██║██║   ██║██║     █████╔╝ \n" +
                " ██╔═══╝ ██╔══╝  ██╔══╝  ██╔══██╗██║  ██║██║   ██║██║     ██╔═██╗ \n" +
                " ██║     ███████╗███████╗██║  ██║██████╔╝╚██████╔╝╚██████╗██║  ██╗\n" +
                " ╚═╝     ╚══════╝╚══════╝╚═╝  ╚═╝╚═════╝  ╚═════╝  ╚═════╝╚═╝  ╚═╝\n" +
                "\n");
    }

    private static void help(){
        System.out.println(">> java -jar Peerdock.jar [OPTION] [ARGS]\n");
        System.out.println("|------------|--------------------|---------------------|");
        System.out.println("|  OPTIONS   |        ARGS        |       UTILITY       |");
        System.out.println("|------------|--------------------|---------------------|");
        System.out.println("| install    |   packages, libs   | install packages    |");
        System.out.println("| remove     |   packages, libs   | Remove a packages   |");
        System.out.println("| update     |   - no arguments   | update package list |");
        System.out.println("| uninstall  |   - no arguments   | delete Peerdock dir |");
        System.out.println("| source     |   URL XML source   | add package list    |");
        System.out.println("| list       |   - no arguments   | list installed pkgs |");
        System.out.println("| slist      |   - no arguments   | list available pkgs |");
        System.out.println("| credit     |   - no arguments   | Peerdock credits    |");
        System.out.println("|------------|--------------------|---------------------|");
    }
    private static void credit(){
        System.out.println("Peerdock is a software developed by Victor Lourme");
        System.out.println("Please take care of sources and use trusted repositories.");
        System.out.println("We are not responsible of malwares, hacks or others viruses.\n");
        System.out.println("Website : https://www.peerdock.co");
        System.out.println("Trusted sources : https://packages.peerdock.co\n");
        System.out.println("Source, repository means a list of packages in XML.");
    }
}
