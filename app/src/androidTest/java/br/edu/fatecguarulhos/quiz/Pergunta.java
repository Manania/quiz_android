package br.edu.fatecguarulhos.quiz;

public class Pergunta {
    private final int imageId;
    private final String enunciado;
    private final String[] alternativas;
    private final String resposta;

    public  Pergunta(int imageId, String enunciado, String[] alternativas, String resposta){
        this.imageId = imageId;
        this.enunciado = enunciado;
        this.alternativas = alternativas;
        this.resposta = resposta;
    }

    public int getImageId() {
        return imageId;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public String getResposta() {
        return resposta;
    }

    public String[] getAlternativas() {
        return alternativas;
    }
}
