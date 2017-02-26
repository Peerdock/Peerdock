package spl.peerdock;

import java.io.*;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    static boolean is64bit;
    static String version = "17.4.1";
    static String update_url = "https://update.peerdock.co/";

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

        Language.set();
        menu();
    }

    public static void header() throws Exception{
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        System.out.println("");
        System.out.println("          _          _            _            _          _            _             _             _        \n" +
                "         /\\ \\       /\\ \\         /\\ \\         /\\ \\       /\\ \\         /\\ \\         /\\ \\           /\\_\\      \n" +
                "        /  \\ \\     /  \\ \\       /  \\ \\       /  \\ \\     /  \\ \\____   /  \\ \\       /  \\ \\         / / /  _   \n" +
                "       / /\\ \\ \\   / /\\ \\ \\     / /\\ \\ \\     / /\\ \\ \\   / /\\ \\_____\\ / /\\ \\ \\     / /\\ \\ \\       / / /  /\\_\\ \n" +
                "      / / /\\ \\_\\ / / /\\ \\_\\   / / /\\ \\_\\   / / /\\ \\_\\ / / /\\/___  // / /\\ \\ \\   / / /\\ \\ \\     / / /__/ / / \n" +
                "     / / /_/ / // /_/_ \\/_/  / /_/_ \\/_/  / / /_/ / // / /   / / // / /  \\ \\_\\ / / /  \\ \\_\\   / /\\_____/ /  \n" +
                "    / / /__\\/ // /____/\\    / /____/\\    / / /__\\/ // / /   / / // / /   / / // / /    \\/_/  / /\\_______/   \n" +
                "   / / /_____// /\\____\\/   / /\\____\\/   / / /_____// / /   / / // / /   / / // / /          / / /\\ \\ \\      \n" +
                "  / / /      / / /______  / / /______  / / /\\ \\ \\  \\ \\ \\__/ / // / /___/ / // / /________  / / /  \\ \\ \\     \n" +
                " / / /      / / /_______\\/ / /_______\\/ / /  \\ \\ \\  \\ \\___\\/ // / /____\\/ // / /_________\\/ / /    \\ \\ \\    \n" +
                " \\/_/       \\/__________/\\/__________/\\/_/    \\_\\/   \\/_____/ \\/_________/ \\/____________/\\/_/      \\_\\_\\   \n" +
                "                                                                                                            \n");
    }

    private static void credit(){
        System.out.println(Language.lang.get("credit"));
    }

    public static void next()
    {
        System.out.println(Language.lang.get("continue"));
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
        System.out.println(Language.lang.get("header.1"));

        System.out.println("═╗ 1. " + Language.lang.get("install"));
        System.out.println(" ║ 2. " + Language.lang.get("remove"));
        System.out.println(" ║ 3. " + Language.lang.get("replace"));
        System.out.println(" ║ 4. " + Language.lang.get("update"));
        System.out.println(" ║ 5. " + Language.lang.get("list-sources"));
        System.out.println(" ║ 6. " + Language.lang.get("list-installed"));
        System.out.println(" ║ 7. " + Language.lang.get("list-available"));
        System.out.println(" ║ 8. " + Language.lang.get("show-credit"));
        System.out.println(" ║ 9. " + Language.lang.get("check-update"));
        System.out.println(" ║ 10. " + Language.lang.get("change-language"));
        System.out.println("═╝ 11. " + Language.lang.get("close") + "\n");

        System.out.print("--> " + Language.lang.get("option"));
        int option = in.nextInt();

        switch (option) {
            default:
                header();
                System.out.println("--> " + Language.lang.get("option-unselected"));
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
                System.out.print("--> " + Language.lang.get("install-selector"));
                String i = in.next();
                header();
                Installer installation = new Installer();
                installation.install(i);
                next();
                menu();
                break;
            case 2:
                header();
                System.out.print("--> " + Language.lang.get("remove-selector"));
                String r = in.next();
                header();
                Package.remove(r);
                next();
                menu();
                break;
            case 3:
                header();
                System.out.println("--> " + Language.lang.get("replace-selector"));
                System.out.print("--> ");
                String so = in.next();
                header();
                Package.source(so);
                next();
                menu();
                break;
            case 10:
                header();
                Language.change();
                next();
                System.exit(1);
                break;
            case 11:
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
