package spl.peerdock;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Language {

    public static Map<String, String> lang = new HashMap<String, String>();

    public static void set(){
        if(Locale.getDefault(Locale.Category.DISPLAY).getLanguage() == "fr"){
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
            lang.put("install-not-exists", "Le paquet suivant n'existe pas : ");
            lang.put("no-xml", "Erreur ! Vous n'avez pas de source");

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

            lang.put("hours", "heures");
            lang.put("minutes", "min");
            lang.put("seconds", "sec");

            lang.put("installed", " à été installé");
            lang.put("folder-installed", "Trouvez le dans ");

            lang.put("tmprm-error", "Erreur ! Les fichiers temporaires n'ont pas été supprimés.");
            lang.put("init-folder-error", "Erreur ! Impossible d'initialiser le dossier du paquet.");

        } else {
            lang.put("credit", "\nPeerdock is a software developed by Victor Lourme" +
            "\nPlease take care of sources and use trusted repositories." +
            "\nWe are not responsible of malwares, hacks or others viruses.\n" +
            "\n\nWebsite : https://www.peerdock.co" +
            "\nTrusted sources : https://packages.peerdock.co\n" +
            "\nSource, repository means a list of packages in XML.");

            lang.put("continue", "\n--> Press [enter] to continue");

            lang.put("header.1", "--> Welcome in Peerdock " + Main.version + "\n");

            lang.put("install", "Install a package");
            lang.put("remove", "Remove a package");
            lang.put("replace", "Replace source");
            lang.put("update", "Update actual source");
            lang.put("list-sources", "List available sources");
            lang.put("list-installed", "List installed sources");
            lang.put("list-available", "List available packages in actual source");
            lang.put("show-credit", "Show credits");
            lang.put("check-update", "Check for Peerdock update");
            lang.put("close", "Exit program");

            lang.put("option", "Select an option : ");
            lang.put("option-unselected", "Please select a correct option (between 1 and 11)");

            lang.put("install-selector", "Type a package to install : ");
            lang.put("remove-selector", "Type a package to remove : ");
            lang.put("replace-selector", "Type your source : ");

            lang.put("package-exists-install", "Package exists ! Install (Y/N) +> ");
            lang.put("install-not-existe", "This package doesn't exists : ");
            lang.put("no-xml", "Error ! You don't have source");

            lang.put("error", "Error : ");
            lang.put("installing", "Installing ");
            lang.put("version", "Version : ");
            lang.put("size", "Size : ");
            lang.put("from-source", "Source : ");
            lang.put("note", "Note : ");
            lang.put("arch", "Arch");
            lang.put("unzip", "Unzip : ");
            lang.put("provider-website", "Visit provider website (Y/N) +> ");
            lang.put("downloading", "Downloading... ");

            lang.put("hours", "hours");
            lang.put("minutes", "min");
            lang.put("seconds", "sec");

            lang.put("installed", " was installed !");
            lang.put("folder-installed", "Find it into ");

            lang.put("tmprm-error", "Error ! Temporary files was not deleted.");
            lang.put("init-folder-error", "Error ! Can't initialize package folder.");
        }
    }
}
