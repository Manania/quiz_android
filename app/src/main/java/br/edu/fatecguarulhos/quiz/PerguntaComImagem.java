package br.edu.fatecguarulhos.quiz;

public class PerguntaComImagem extends Pergunta{
    private int imageId;

    public PerguntaComImagem(String enunciado, String[] alternativas, String resposta) {
        super(enunciado, alternativas, resposta);
    }

    public PerguntaComImagem(String enunciado, String[] alternativas, String resposta, int imageId) {
        super(enunciado, alternativas, resposta);
        this.imageId = imageId;
    }

    public int getImageId(){
        return this.imageId;
    }

    public void setImageId(int imageId){
        this.imageId = imageId;
    }

}

