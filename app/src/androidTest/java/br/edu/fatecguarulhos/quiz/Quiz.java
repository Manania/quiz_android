package br.edu.fatecguarulhos.quiz;

import java.util.Arrays;

public class Quiz {

    private String[][] respostas = new String[][]{
            {"Brasil", "@drawable/brasil"},
            {"Bulgaria", "@drawable/bulgaria"},
            {"Canada", "@drawable/canada"},
            {"China", "@drawable/china"},
            {"Coreia do Sul", "@drawable/coreia_sul"},
            {"Cuba", "@drawable/cuba"},
            {"Guatemala", "@drawable/guatemala"},
            {"Hungria", "@drawable/hungria"},
            {"Italia", "@drawable/italia"},
            {"Estados Unidos", "@drawable/usa"},
    };

    private static int generateRandomNumber(int min, int max){
        return (int) (Math.random() * (max - min + 1) + min);
    }

    private static boolean isInArray(int value, int arr[], int len){
        if(len == 0){
            return true;
        }

        for(int i = 0; i < len; i++){
            if(value == arr[i]){
                return true;
            }
        }
        return false;
    }

    private static int[] randomSubsetInRange(int min, int max, int len){
        int randomNumbers[] = new int[len];
        if((max - min) < len){
            return null;
        }

        int numGenerated = 0;
        for(int i = 0; i < len; i++){
            int n = 0;
            do {
                n = generateRandomNumber(min, max);
            } while(isInArray(n, randomNumbers, numGenerated));
            randomNumbers[numGenerated++] = n;
        }

        return randomNumbers;
    }

    private static String[] randomSubsetOf(String[] set, int subset_len){
        int[] randomInts = randomSubsetInRange(0, subset_len-1, subset_len);
        String[] subset = new String[subset_len];
        for(int i = 0; i < randomInts.length; i++){
            subset[i] = set[randomInts[i]];
        }
        return subset;
    }

    private static String[] randomAlternatives(String resposta, String[] alternativas_erradas, int len){
        String[] set = randomSubsetOf(alternativas_erradas, len);
        set[generateRandomNumber(0, len-1)] = resposta;
        return set;
    }

    public static Pergunta[] paises(){
        final int num_alt = 4;
        String enunciado = "Essa banderia é de qual país?";
        String[] respostasErradas = new String[] {
                "Africa do Sul",
                "Argentina",
                "Cabo Verde",
                "Chile",
                "Congo",
                "Equador",
                "Russia",
                "Venezuela"
        };

        Pergunta[] perguntas = new Pergunta[]{
                new Pergunta(R.drawable.brasil,     enunciado, randomAlternatives("Brasil", respostasErradas, num_alt), "Brasil"),
                new Pergunta(R.drawable.bulgaria,   enunciado, randomAlternatives("Bulgaria", respostasErradas, num_alt), "Bulgaria"),
                new Pergunta(R.drawable.canada,     enunciado, randomAlternatives("Canada", respostasErradas, num_alt), "Canada"),
                new Pergunta(R.drawable.china,      enunciado, randomAlternatives("China", respostasErradas, num_alt), "China"),
                new Pergunta(R.drawable.coreia_sul, enunciado, randomAlternatives("Coreia do Sul", respostasErradas, num_alt), "Coreia do Sul"),
                new Pergunta(R.drawable.cuba,       enunciado, randomAlternatives("Cuba", respostasErradas, num_alt), "Cuba"),
                new Pergunta(R.drawable.guatemala,  enunciado, randomAlternatives("Guatemala", respostasErradas, num_alt), "Guatemala"),
                new Pergunta(R.drawable.hungria,    enunciado, randomAlternatives("Hungria", respostasErradas, num_alt), "Hungria"),
                new Pergunta(R.drawable.italia,     enunciado, randomAlternatives("Itália", respostasErradas, num_alt), "Itália"),
                new Pergunta(R.drawable.usa,        enunciado, randomAlternatives("Estados Unidos", respostasErradas, num_alt), "Estados Unidos")
        };

        return perguntas;
    }

}
