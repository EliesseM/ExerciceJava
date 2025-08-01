package src;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class JeuDeplacement {

    public static void main(String[] args) {
        System.out.println("Bienvenue dans CarteAventure !");

        // Chargement de la carte depuis le fichier carte.txt
        Path path = Paths.get("carte.txt");
        List<char[]> carte = new java.util.ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(path);

            // Affiche la carte ligne par ligne
            for (String line : lines) {
                System.out.println(line);
            }

            // Conversion de chaque ligne en tableau de caractères
            for (String ligne : lines) {
                carte.add(ligne.toCharArray());
            }

        } catch (Exception e) {
            System.err.println("Erreur lors de la lecture du fichier : " + e.getMessage());
        }

        try {
            // Lecture du fichier mouvement.txt (position initiale + directions)
            Path cheminMouvements = Paths.get("mouvement.txt");
            List<String> lignes = Files.readAllLines(cheminMouvements);

            // Lecture de la position de départ
            String lignePosition = lignes.get(0);
            String[] coords = lignePosition.split(",");
            int posX = Integer.parseInt(coords[0].trim()); // Abscisse (colonne)
            int posY = Integer.parseInt(coords[1].trim()); // Ordonnée (ligne)

            // Lecture des instructions de déplacement (N, S, E, O)
            String instructions = lignes.get(1).trim();

            System.out.println("Position de départ : (" + posX + "," + posY + ")");
            System.out.println("Déplacements : " + instructions);

            // Traitement de chaque instruction
            for (int i = 0; i < instructions.length(); i++) {
                char direction = instructions.charAt(i);

                int nx = posX; // Nouvelle position X
                int ny = posY; // Nouvelle position Y

                // Déplacement selon la direction
                switch (direction) {
                    case 'N':
                        ny = posY - 1;
                        break;
                    case 'S':
                        ny = posY + 1;
                        break;
                    case 'E':
                        nx = posX + 1;
                        break;
                    case 'O':
                        nx = posX - 1;
                        break;
                    default:
                        System.out.println("Direction inconnue : " + direction);
                        continue;
                }

                // Test si la case cible est libre
                if (caseLibre(nx, ny, carte)) {
                    posX = nx;
                    posY = ny;
                } else {
                    System.out.println("Déplacement bloqué à (" + nx + "," + ny + ") : obstacle ou hors carte.");
                }
            }

            // Affichage de la position finale
            System.out.println("Position finale : (" + posX + "," + posY + ")");

            // Affichage de la carte avec 'P' à la position finale
            if (posY >= 0 && posY < carte.size() && posX >= 0 && posX < carte.get(posY).length) {
                afficherCarteAvecPersonnage(carte, posX, posY);
            } else {
                System.out.println("Le personnage est hors de la carte, impossible de l'afficher.");
            }

        } catch (IOException e) {
            System.out.println("Erreur lecture mouvements.txt : " + e.getMessage());
        }
    }

    // Affiche la carte avec 'P' à la position actuelle
    public static void afficherCarteAvecPersonnage(List<char[]> carte, int posX, int posY) {
        for (int y = 0; y < carte.size(); y++) {
            for (int x = 0; x < carte.get(y).length; x++) {
                if (x == posX && y == posY) {
                    System.out.print('P');
                } else {
                    System.out.print(carte.get(y)[x]);
                }
            }
            System.out.println();
        }
    }

    // Teste si la case est libre (pas un arbre et dans les limites de la carte)
    private static boolean caseLibre(int x, int y, List<char[]> carte) {
        if (y < 0 || y >= carte.size() || x < 0 || x >= carte.get(y).length) {
            return false; // Hors des limites de la carte
        }
        return carte.get(y)[x] != '#'; // Libre si ce n’est pas un arbre
    }
}
