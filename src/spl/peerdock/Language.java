package spl.peerdock;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class Language {

    public static Map<String, String> lang = new HashMap<String, String>();
    public static Locale locale = Locale.getDefault();
    public static Boolean lw = false;

    public static void set() {
        if (new File("/Peerdock/fr").exists()) {
            set_fr();
        } else if (new File("/Peerdock/en").exists()){
            set_en();
        }

        if(!lw){
            if(locale.getLanguage().equalsIgnoreCase("fr")){
                set_fr();
            } else {
                set_en();
            }
        }
    }
    public static void change() throws Exception{
        System.out.println("<+> " + lang.get("load-language") + "\n");
        System.out.println("═╗ 1. English");
        System.out.println("═╝ 2. French\n");
        System.out.print("--> " + lang.get("language-select") + " : ");
        Scanner sc = new Scanner(System.in);
        int language = sc.nextInt();
        Main.header();
        switch (language) {
            default:
                System.out.println("<!> " + lang.get("unexpected-number-lang"));
                break;
            case 1:
                System.out.println("<+> " + lang.get("changing-lang") + " : English");
                if(new File("/Peerdock/fr").exists()){
                    new File("/Peerdock/fr").delete();
                }
                FileUtils.writeStringToFile(new File("/Peerdock/en"), "lang file");
                break;
            case 2:
                System.out.println("<+> " + lang.get("changing-lang") + " : French");
                if(new File("/Peerdock/en").exists()){
                    new File("/Peerdock/en").delete();
                }
                FileUtils.writeStringToFile(new File("/Peerdock/fr"), "lang file");
                break;
        }
        System.out.println("\n<+> " + lang.get("lang-changed"));
    }
    public static void set_fr(){
        lw = true;

        lang.put("credit", "\nPeerdock est un logiciel crée par Victor Lourme" +
                "\nMerci de faire attention au sources que vous installez." +
                "\nNous ne sommes pas responsable des malwares, hacks et autres virus." +
                "\n\nSite internet : https://www.peerdock.co" +
                "\nSources sûres : https://packages.peerdock.co");

        lang.put("continue", "\n--> Pressez [enter] pour continuer.");

        lang.put("header.1", "--> Bienvenue dans Peerdock " + Main.version + "\n");

        lang.put("install", "Installer un paquet");
        lang.put("remove", "Supprimer un paquet");
        lang.put("replace", "Remplacer la source des paquets");
        lang.put("update", "Mettre à jour la liste des paquets");
        lang.put("list-sources", "Lister les sources disponibles");
        lang.put("list-installed", "Lister les paquets installés");
        lang.put("list-available", "Lister les paquets disponibles dans la source actuelle");
        lang.put("show-credit", "Voir les crédits");
        lang.put("check-update", "Vérifier si une mise à jour est disponible");
        lang.put("close", "Fermer le programme");

        lang.put("option", "Sélectionnez une option : ");
        lang.put("option-unselected", "Sélectionnez une option correcte (entre 1 et 11)");

        lang.put("install-selector", "Entrez le nom d'un paquet à installer : ");
        lang.put("remove-selector", "Entrez le nom d'un paquet à supprimer  : ");
        lang.put("replace-selector", "Entrez l'adresse de votre source : ");

        lang.put("package-exists-install", "Ce paquet existe ! Installer (Y/N) +> ");
        lang.put("package-exists-remove", "Ce paquet existe ! Supprimer (Y/N) +> ");
        lang.put("directory-exists-remove", "Le dossier existe ! Supprimer (Y/N) +> ");

        lang.put("install-not-exists", "Le paquet suivant n'existe pas : ");
        lang.put("no-xml", "Erreur ! Vous n'avez pas de source");
        lang.put("dont-exists", "Erreur ! Le paquet suivant n'existe pas : ");

        lang.put("error", "Erreur : ");
        lang.put("installing", "Installation de ");
        lang.put("version", "Version : ");
        lang.put("size", "Taille : ");
        lang.put("from-source", "Source : ");
        lang.put("note", "Note : ");
        lang.put("arch", "Architecture");
        lang.put("unzip", "Extraction : ");
        lang.put("provider-website", "Visiter le site du fournisseur (Y/N) +> ");
        lang.put("downloading", "Téléchargement... ");
        lang.put("package", "Paquet : ");
        lang.put("url", "URL");

        lang.put("hours", "heures");
        lang.put("minutes", "min");
        lang.put("seconds", "sec");

        lang.put("installed", " à été installé");
        lang.put("folder-installed", "Trouvez le dans ");

        lang.put("tmprm-error", "Erreur ! Les fichiers temporaires n'ont pas été supprimés.");
        lang.put("init-folder-error", "Erreur ! Impossible d'initialiser le dossier du paquet.");

        lang.put("pkgls", "Chargement de la liste des sources...");
        lang.put("pkgls-option", "Séléctionnez une source : ");

        lang.put("update-new", "Une mise à jour est disponible : ");
        lang.put("update-none", "Aucune mise à jour disponible");

        lang.put("installed-pkgs", "Paquets installés :");
        lang.put("available-pkgs", "Paquets disponibles : ");
        lang.put("install-bfetch", "Installez une source avant de chercher des paquets");

        lang.put("delete-wont", "Impossible de supprimer des fichiers");

        lang.put("retrieve-update", "Récuperation de l'URL de mise à jour...");
        lang.put("retrieve-fail", "Installez une source avant de mettre à jour.");

        lang.put("was-deleted", " a été supprimé.");
        lang.put("was-not-deleted", " n'a pas été supprimé.");
        lang.put("remove-aborded", "La tache à été annulée.");
        lang.put("fetch-peerdock", "Recherche du dossier Peerdock...");
        lang.put("fetch-list", "Recherche de la liste de paquets...");

        lang.put("saving", "Sauvegarde en cours...");
        lang.put("saving-success", "Sauvegarde reussie !");

        lang.put("change-language", "Changer la langue");
        lang.put("load-language", "Chargement de la liste des langues : ");
        lang.put("language-select", "Selectionnez la langue");
        lang.put("changing-lang", "Changement de la langue : ");
        lang.put("lang-changed", "La langue a été changée !");
    }
    public static void set_en(){
        lw = true;

        lang.put("credit", "\nPeerdock is a software created by Victor Lourme" +
                "\nPlease take care of what you install." +
                "\nWe are not responsible of malwares, adwares and other types of virus." +
                "\n\nWebsite: https://www.peerdock.co" +
                "\nTrusted sources: https://packages.peerdock.co");

        lang.put("continue", "\n--> Press [enter] to continue.");

        lang.put("header.1", "--> Welcome on Peerdock " + Main.version + "\n");

        lang.put("install", "Install a package");
        lang.put("remove", "Delete a package");
        lang.put("replace", "Replace the source");
        lang.put("update", "Update the source");
        lang.put("list-sources", "List available sources");
        lang.put("list-installed", "List installed packages");
        lang.put("list-available", "List available packages in actual source");
        lang.put("show-credit", "Show credits");
        lang.put("check-update", "Check for update");
        lang.put("close", "Exit program");

        lang.put("option", "Choose a option: ");
        lang.put("option-unselected", "Please choose a valid option (between 1 and 11)");

        lang.put("install-selector", "Type a package name to install: ");
        lang.put("remove-selector", "Type a package name to remove: ");
        lang.put("replace-selector", "Type your source URL: ");

        lang.put("package-exists-install", "This package exists! Install (Y/N) +> ");
        lang.put("package-exists-remove", "This package exists! Delete (Y/N) +> ");
        lang.put("directory-exists-remove", "This directory exists! Delete (Y/N) +> ");

        lang.put("install-not-exists", "The following package don't exists: ");
        lang.put("no-xml", "Error ! You don't have any source");
        lang.put("dont-exists", "Error ! The following package don't exists: ");

        lang.put("error", "Error : ");
        lang.put("installing", "Installing ");
        lang.put("version", "Version: ");
        lang.put("size", "Size: ");
        lang.put("from-source", "Source: ");
        lang.put("note", "Note: ");
        lang.put("arch", "Architecture");
        lang.put("unzip", "Unzip: ");
        lang.put("provider-website", "Visit provider website (Y/N) +> ");
        lang.put("downloading", "Downloading... ");
        lang.put("package", "Package: ");
        lang.put("url", "URL");

        lang.put("hours", "hours");
        lang.put("minutes", "mn");
        lang.put("seconds", "sec");

        lang.put("installed", " was installed");
        lang.put("folder-installed", "Find it in ");

        lang.put("tmprm-error", "Error ! Temporary files was not deleted.");
        lang.put("init-folder-error", "Error ! Can't initialize package folder.");

        lang.put("pkgls", "Loading the list of source...");
        lang.put("pkgls-option", "Choose a source: ");

        lang.put("update-new", "A update is available: ");
        lang.put("update-none", "The is no update available");

        lang.put("installed-pkgs", "Installed packages:");
        lang.put("available-pkgs", "Available packages: ");
        lang.put("install-bfetch", "Please install a source before fetch it.");

        lang.put("delete-wont", "Can't delete files.");

        lang.put("retrieve-update", "Retrieving update URL...");
        lang.put("retrieve-fail", "Please install a source before update it.");

        lang.put("was-deleted", " was deleted.");
        lang.put("was-not-deleted", " was not deleted.");
        lang.put("remove-aborded", "The task was aborted.");
        lang.put("fetch-peerdock", "Searching Peerdock folder...");
        lang.put("fetch-list", "Fetching list of packages...");

        lang.put("saving", "Saving...");
        lang.put("saving-success", "Save finished!");

        lang.put("change-language", "Change the language");
        lang.put("load-language", "Loading the list of languages: ");
        lang.put("language-select", "Choose a lang");
        lang.put("changing-lang", "Changing language to: ");
        lang.put("lang-changed", "The language has been changed!");
    }
}
