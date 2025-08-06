package src; // Indique que ce fichier se trouve dans le package "src"

import java.io.IOException; // Pour gérer les erreurs de lecture/écriture
import java.nio.file.*; // Pour manipuler les fichiers et chemins
import java.util.*; // Importe les structures de données comme List et ArrayList

public class JeuDeplacement { // Déclaration de la classe principale

    public static void main(String[] args) { // Point d'entrée de l'application
        System.out.println("Bienvenue dans CarteAventure !"); // Message de bienvenue

        List<char[]> carte = chargerCarte("carte.txt"); // Charge la carte depuis un fichier texte
        afficherCarte(carte); // Affiche la carte telle qu'elle est dans le fichier

        try {
            List<String> mouvements = Files.readAllLines(Paths.get("mouvement.txt")); // Lit les lignes du fichier
                                                                                      // mouvement.txt
            int[] position = lirePositionInitiale(mouvements.get(0)); // Lit la position de départ
            String instructions = mouvements.get(1).trim(); // Lit les instructions de déplacement (ex: "NESO")

            System.out.println("Position de départ : (" + position[0] + "," + position[1] + ")"); // Affiche la position
                                                                                                  // initiale
            System.out.println("Déplacements : " + instructions); // Affiche les directions

            int[] positionFinale = executerDeplacements(position[0], position[1], instructions, carte); // Applique les
                                                                                                        // déplacements

            System.out.println("Position finale : (" + positionFinale[0] + "," + positionFinale[1] + ")"); // Affiche la
                                                                                                           // position
                                                                                                           // finale
            afficherCarteAvecPersonnage(carte, positionFinale[0], positionFinale[1]); // Affiche la carte avec 'P' à la
                                                                                      // position finale

        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture de mouvement.txt : " + e.getMessage()); // Gère une erreur si
                                                                                                  // le fichier ne peut
                                                                                                  // pas être lu
        }
    }

    // Lit le fichier carte et le convertit en liste de tableaux de caractères
    public static List<char[]> chargerCarte(String chemin) {
        List<char[]> carte = new ArrayList<>(); // Initialise la liste qui va contenir chaque ligne de la carte
        try {
            List<String> lignes = Files.readAllLines(Paths.get(chemin)); // Lis toutes les lignes du fichier
            for (String ligne : lignes) {
                carte.add(ligne.toCharArray()); // Convertit chaque ligne en tableau de caractères
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture de la carte : " + e.getMessage()); // En cas d'erreur de
                                                                                             // lecture
        }
        return carte; // Retourne la carte chargée
    }

    // Affiche simplement la carte sans personnage
    public static void afficherCarte(List<char[]> carte) {
        for (char[] ligne : carte) {
            System.out.println(new String(ligne)); // Affiche chaque ligne
        }
    }

    // Récupère les coordonnées de départ depuis une ligne du fichier
    public static int[] lirePositionInitiale(String ligne) {
        String[] coords = ligne.split(","); // Sépare la ligne par la virgule
        int x = Integer.parseInt(coords[0].trim()); // Convertit la première coordonnée en entier
        int y = Integer.parseInt(coords[1].trim()); // Convertit la seconde coordonnée en entier
        return new int[] { x, y }; // Retourne les coordonnées dans un tableau
    }

    // Exécute les déplacements en fonction des instructions
    public static int[] executerDeplacements(int x, int y, String instructions, List<char[]> carte) {

        if (!caseLibre(x, y, carte)) {
            System.out.println(
                    "La position initiale (" + x + ", " + y + ") est sur un obstacle. Aucun déplacement possible.");
            return new int[] { x, y }; // on ne fait aucun déplacement
        }

        for (char direction : instructions.toCharArray()) { // Boucle sur chaque caractère des instructions
            int nx = x; // Coordonnée x temporaire
            int ny = y; // Coordonnée y temporaire

            switch (direction) { // Change les coordonnées selon la direction
                case 'N':
                    ny--;
                    break; // Nord = haut
                case 'S':
                    ny++;
                    break; // Sud = bas
                case 'E':
                    nx++;
                    break; // Est = droite
                case 'O':
                    nx--;
                    break; // Ouest = gauche
                default:
                    System.out.println("Direction inconnue : " + direction); // Si direction invalide
                    continue;
            }

            if (caseLibre(nx, ny, carte)) { // Vérifie si la case est accessible
                x = nx; // Met à jour la position x
                y = ny; // Met à jour la position y
            } else {
                System.out.println("Déplacement bloqué à (" + nx + "," + ny + ")"); // Affiche un message si l'obstacle
                                                                                    // bloque le passage
            }
        }
        return new int[] { x, y }; // Retourne la position finale
    }

    // Affiche la carte avec le personnage 'P' à la bonne position
    public static void afficherCarteAvecPersonnage(List<char[]> carte, int posX, int posY) {
        for (int y = 0; y < carte.size(); y++) {
            for (int x = 0; x < carte.get(y).length; x++) {
                if (x == posX && y == posY) {
                    System.out.print('P'); // Place le personnage
                } else {
                    System.out.print(carte.get(y)[x]); // Affiche la case normale
                }
            }
            System.out.println(); // Passe à la ligne suivante
        }
    }

    // Vérifie si une case est libre (pas un arbre et pas en dehors de la carte)
    public static boolean caseLibre(int x, int y, List<char[]> carte) {
        if (y < 0 || y >= carte.size() || x < 0 || x >= carte.get(y).length) {
            return false; // Hors de la carte
        }
        return carte.get(y)[x] != '#'; // Vrai si ce n'est pas un arbre
    }
}
