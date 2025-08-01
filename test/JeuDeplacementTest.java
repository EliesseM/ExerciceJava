package test;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JeuDeplacementTest {

    @Test
    public void testCaseLibre_caseVide() {
        // Création d'une carte simple sans obstacle
        List<char[]> carte = new ArrayList<>();
        carte.add("     ".toCharArray()); // ligne 0
        carte.add("     ".toCharArray()); // ligne 1

        // Test : la case (2, 1) est vide
        assertTrue(JeuDeplacement.caseLibre(2, 1, carte));
    }

    @Test
    public void testCaseLibre_caseArbre() {
        List<char[]> carte = new ArrayList<>();
        carte.add("  #  ".toCharArray()); // ligne 0
        carte.add("     ".toCharArray()); // ligne 1

        // Test : la case (2, 0) est un arbre
        assertFalse(JeuDeplacement.caseLibre(2, 0, carte));
    }

    @Test
    public void testCaseLibre_horsLimite() {
        List<char[]> carte = new ArrayList<>();
        carte.add("     ".toCharArray());

        // Test : la case (10, 0) est hors des limites
        assertFalse(JeuDeplacement.caseLibre(10, 0, carte));
    }
}
