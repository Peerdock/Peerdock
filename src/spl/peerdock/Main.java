package spl.peerdock;

import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
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

        if(args.length == 0){
            header();
            System.out.println("<+> Please type correct args.");
        } else {
            switch (args[0]) {
                default:
                    header();
                    System.out.println("<+> Please type correct args.");
                    break;
                case "help":
                    header();
                    help();
                    break;
                case "update":
                    header();
                    Package.update();
                    break;
                case "list":
                    header();
                    Package.list();
                    break;
                case "install":
                    header();
                    if(args.length == 1){
                        System.out.println("<+> Please type a package name.");
                    } else {
                        Installer installation = new Installer();
                        installation.install(args);
                    }
                    break;
                case "remove":
                    header();
                    if(args.length == 1){
                        System.out.println("<+> Please type a package name.");
                    } else {
                        Package.remove(args);
                    }
                    break;
                case "uninstall":
                    header();
                    Package.reset();
                    break;
                case "source":
                    header();
                    if(args.length == 1){
                        System.out.println("<+> Please type a URL source.");
                    } else {
                        Package.source(args[1]);
                    }
                    break;
                case "dev":
                    if(args.length == 1){
                        System.out.println("<+> Please type a dev-cmd.");
                    } else {
                        switch(args[1]){
                            case "search":
                                if(args.length == 2) {
                                    System.out.println("false");
                                } else {
                                    System.out.println(Developers.search(args[2]));
                                }
                                break;
                        }
                    }
                    break;
            }
        }
    }

    private static void header() throws IOException, InterruptedException{
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        System.out.println("");
        System.out.println(" 01010101 01010101 01010101 01010101 01010101    0101010101 0101010101 01   01");
        System.out.println(" 01    01 01       01       01    01 01      01  01      01 01         01   01");
        System.out.println(" 01    01 01       01       01    01 01       01 01      01 01         01  01");
        System.out.println(" 01010101 0101     0101     01010101 01       01 01      01 01         0101");
        System.out.println(" 01       0101     0101     01   01  01       01 01      01 01         0101");
        System.out.println(" 01       01       01       01    01 01       01 01      01 01         01  01");
        System.out.println(" 01       01       01       01    01 01      01  01      01 01         01   01");
        System.out.println(" 01       01010101 01010101 01    01 01010101    0101010101 0101010101 01   01");
        System.out.println("");
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
        System.out.println("|------------|--------------------|---------------------|");
    }
}
