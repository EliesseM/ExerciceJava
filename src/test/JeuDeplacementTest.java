package src.test;

import static org.junit.jupiter.api.Assertions.*; // Import des assertions JUnit

import org.junit.jupiter.api.Test; // Import de l'annotation @Test

import src.JeuDeplacement;

import java.util.*; // Pour List et Arrays

public class JeuDeplacementTest {

    // Test de la méthode lirePositionInitiale
    @Test
    public void testLirePositionInitiale() {
        // On donne une ligne comme dans mouvement.txt
        String ligne = "2, 3";

        // Appel de la méthode à tester
        int[] position = JeuDeplacement.lirePositionInitiale(ligne);

        // Vérification que la position retournée est correcte
        assertArrayEquals(new int[] { 2, 3 }, position, "La position initiale devrait être (2,3)");
    }

    // Test de caseLibre avec une case vide
    @Test
    public void testCaseLibre_Vide() {
        List<char[]> carte = Arrays.asList(
                "....".toCharArray(), // ligne 0
                "....".toCharArray(), // ligne 1
                "....".toCharArray() // ligne 2
        );

        // Test d'une position libre (0,0)
        assertTrue(JeuDeplacement.caseLibre(0, 0, carte), "La case (0,0) devrait être libre.");
    }

    // Test de caseLibre avec un mur '#'
    @Test
    public void testCaseLibre_Obstacle() {
        List<char[]> carte = Arrays.asList(
                "....".toCharArray(),
                ".#..".toCharArray(),
                "....".toCharArray());

        // (1,1) est un mur (car c’est un '#')
        assertFalse(JeuDeplacement.caseLibre(1, 1, carte), "La case (1,1) est un obstacle.");
    }

    // Test de caseLibre hors des limites de la carte
    @Test
    public void testCaseLibre_HorsLimites() {
        List<char[]> carte = Arrays.asList(
                "....".toCharArray());

        // Position en dehors de la carte
        assertFalse(JeuDeplacement.caseLibre(-1, 0, carte), "Case hors de la carte (négatif).");
        assertFalse(JeuDeplacement.caseLibre(0, 5, carte), "Case hors de la carte (trop bas).");
    }

    // Test de position initiale sur obstacle
    @Test
    public void testExecuterDeplacement_PositionInitialeSurObstacle() {
        List<char[]> carte = Arrays.asList(
                "....".toCharArray(),
                ".##.".toCharArray(),
                "....".toCharArray());

        int[] positionDepart = new int[] { 2, 1 }; // position sur un arbre
        String instructions = "EESS";

        // Le personnage ne doit pas bouger du tout
        int[] resultat = JeuDeplacement.executerDeplacements(positionDepart[0], positionDepart[1], instructions, carte);
        assertArrayEquals(new int[] { 2, 1 }, resultat,
                "la position est un obstacle, aucun deplacement ne doit etre effectué");
    }

    // Test des déplacements simples sans obstacle
    @Test
    public void testExecuterDeplacements_SansObstacle() {
        List<char[]> carte = Arrays.asList(
                "....".toCharArray(),
                "....".toCharArray(),
                "....".toCharArray());

        int x = 0, y = 0;
        String instructions = "SEE"; // Sud, Est, Est

        // Résultat attendu : (2,1)
        int[] resultat = JeuDeplacement.executerDeplacements(x, y, instructions, carte);
        assertArrayEquals(new int[] { 2, 1 }, resultat, "La position finale devrait être (2,1)");
    }

    // Test des déplacements avec obstacle (devrait bloquer)
    @Test
    public void testExecuterDeplacements_Obstacle() {
        List<char[]> carte = Arrays.asList(
                "....".toCharArray(),
                ".#..".toCharArray(), // obstacle à (1,1)
                "....".toCharArray());

        int x = 0, y = 0;
        String instructions = "SE"; // Sud → (0,1), Est → (1,1) (bloqué)

        // On s'attend à rester sur (0,1), car (1,1) est un mur
        int[] resultat = JeuDeplacement.executerDeplacements(x, y, instructions, carte);
        assertArrayEquals(new int[] { 0, 1 }, resultat, "Doit s'arrêter avant l'obstacle.");
    }

    // Test de déplacement avec direction invalide
    @Test
    public void testExecuterDeplacements_DirectionInconnue() {
        List<char[]> carte = Arrays.asList(
                "....".toCharArray(),
                "....".toCharArray());

        int x = 1, y = 1;
        String instructions = "XZNS"; // X et Z sont inconnues

        // Seuls N et S sont valides, donc on revient au même y
        int[] resultat = JeuDeplacement.executerDeplacements(x, y, instructions, carte);
        assertArrayEquals(new int[] { 1, 1 }, resultat, "Les directions invalides doivent être ignorées.");
    }
}
