package br.edu.fatecguarulhos.quiz;

import java.util.Random;

public class Randomizer {
    private static Random rndGen = new Random();

    public static void setSeed(long d){
        rndGen.setSeed(d);
    }

    private static boolean isInArray(int value, int arr[], int len) {
        if(len <= 0) return false;
        for(int i = 0; i < len; i++) if(value == arr[i]) return true;
        return false;
    }

    /**
     * Retorna um numero pertencente a [min, max]
     * @param min
     * @param max
     * @return
     */
    public static int generateRandomNumber(int min, int max) {
        return rndGen.nextInt(max- min +1) + min;
        //return (int) (Math.random() * (max - min + 1) + min);
    }

    /**
     * Retorna um subconjunto aleatorio e desordenado contido em [min, max].
     * Nenhum numero é repetido
     * @param min
     * @param max
     * @param len
     * @return
     */
    public static int[] uniqueRandomInRange(int min, int max, int len) {
        int unique_numbers = max - min + 1;
        if(unique_numbers < len) {
            return null;
        }
        int randomNumbers[] = new int[len];
        int numGenerated = 0;
        for(int i = 0, n = 0; i < len; i++) {
            do {
                n = generateRandomNumber(min, max);
            } while(isInArray(n, randomNumbers, numGenerated));
            randomNumbers[numGenerated++] = n;
        }
        return randomNumbers;
    }

    /**
     * Retorna um subconjunto aleatorio e desordenado contido em (set)
     * @param set
     * @param subset_length
     * @return
     */
    public static String[] randomSubsetOf(String[] set, int subset_length) {
        if(set == null || subset_length < 1 || set.length < subset_length) {
            return null;
        }
        int[] randomInts = uniqueRandomInRange(0, set.length-1, subset_length);
        String[] subset = new String[subset_length];
        for(int i = 0; i < randomInts.length; i++)
            subset[i] = set[randomInts[i]];
        return subset;
    }
}

