package br.edu.fatecguarulhos.quiz;

public class Pergunta {
    final private String enunciado;
    private String[] alternativas;
    final private String resposta;

    public Pergunta(String enunciado, String[] alternativas, String resposta){
        this.enunciado = enunciado;
        this.resposta = resposta;
        this.alternativas = new String[alternativas.length + 1];
        this.alternativas[this.alternativas.length -1] = resposta;
        for(int i = 0; i < alternativas.length; i++){
            this.alternativas[i] = alternativas[i];
        }
        embaralhar();
    }

    public String getEnunciado(){
        return enunciado;
    }

    public String[] getAlternativas() {
        return alternativas;
    }

    public String getResposta(){
        return resposta;
    }

    public void embaralhar(){
        alternativas = Randomizer.randomSubsetOf(alternativas, alternativas.length);
    }

    public boolean alternativaCorreta(String alternativa){
        return resposta == alternativa || resposta.equals(alternativa);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(enunciado);
        str.append('\n');
        for(String alt : alternativas){
            str.append(alt);
            str.append('\n');
        }
        return str.toString();
    }
}
