package br.edu.fatecguarulhos.quiz;

import android.content.Context;
import android.content.Intent;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class QuizHomeViewModel extends ViewModel {
    final public static int INATIVO = 0, ATIVO = 1;
    private String seed;
    private Prova quizPaises;
    private Prova.Sessao sessaoAtual;
    private List<OnSeedChangeListener> onSeedChangeListeners = new ArrayList<>();

    public interface OnSeedChangeListener{
        void onSeedChange(QuizHomeViewModel model);
    }

    private Prova getQuizPaises() {
        if(quizPaises == null){
            quizPaises = buildQuiz(getSeedAsLong());
        }
        return quizPaises;
    }

    private void setQuizPaises(Prova quiz){
        quizPaises = quiz;
    }

    private Prova.Sessao getSessaoAtual(){
        return sessaoAtual;
    }

    private void setSessaoAtual(Prova.Sessao sessao){
        this.sessaoAtual = sessao;
    }

    public String getSeed(){
        if(seed == null || seed.isEmpty()){
            Random rndGen = new Random();
            seed = Long.toString(rndGen.nextLong(), 36);
        }
        return seed;
    }

    public long getSeedAsLong(){
        String seed = getSeed();
        return Long.parseLong(seed, 36);
    }

    public void setSeed(String seed){
        try{
            setSeed(Long.parseLong(seed, 36));
        } catch (NumberFormatException e){}
    }

    private void setSeed(long l){
        seed = Long.toString(l,36);
        quizPaises = null;
        sessaoAtual = null;

        for(OnSeedChangeListener listener : onSeedChangeListeners){
            listener.onSeedChange(this);
        }
    }

    public void setOnSeedChangeListener(OnSeedChangeListener l){
        if(l != null){
            onSeedChangeListeners.add(l);
        }
    }

    public int getIndex(){
        return getSessaoAtual().getIndex();
    }

    public int getNumPerguntas(){
        return getSessaoAtual().getNumPerguntas();
    }

    private Prova buildQuiz(long seed){
        Randomizer.setSeed(seed);
        String enunciado = "Esta bandeira é de que pais?";
        String[] alternativas = new String[]{
                "Afeganistão",
                "Africa do Sul",
                "Alemanha",
                "Argentina",
                "Australia",
                "Cabo Verde",
                "Chile",
                "Colombia",
                "Coreia do Norte",
                "Equador",
                "França",
                "Noruega",
                "Paraguai",
                "Quênia",
                "Rússia",
                "Somália",
                "Ucrania",
                "Uruguai",
                "Venezuela"
        };

        Pergunta[] perguntas = new PerguntaComImagem[]{
                new PerguntaComImagem(enunciado, Randomizer.randomSubsetOf(alternativas, 3), "Brasil", R.drawable.brasil),
                new PerguntaComImagem(enunciado, Randomizer.randomSubsetOf(alternativas, 3), "Bulgaria", R.drawable.bulgaria),
                new PerguntaComImagem(enunciado, Randomizer.randomSubsetOf(alternativas, 3), "Canada", R.drawable.canada),
                new PerguntaComImagem(enunciado, Randomizer.randomSubsetOf(alternativas, 3), "China", R.drawable.china),
                new PerguntaComImagem(enunciado, Randomizer.randomSubsetOf(alternativas, 3), "Coreia do Sul", R.drawable.coreia_sul),
                new PerguntaComImagem(enunciado, Randomizer.randomSubsetOf(alternativas, 3), "Cuba", R.drawable.cuba),
                new PerguntaComImagem(enunciado, Randomizer.randomSubsetOf(alternativas, 3), "Guatemala", R.drawable.guatemala),
                new PerguntaComImagem(enunciado, Randomizer.randomSubsetOf(alternativas, 3), "Hungria", R.drawable.hungria),
                new PerguntaComImagem(enunciado, Randomizer.randomSubsetOf(alternativas, 3), "Italia", R.drawable.italia),
                new PerguntaComImagem(enunciado, Randomizer.randomSubsetOf(alternativas, 3), "Estados Unidos", R.drawable.usa),
        };

        return new Prova(perguntas).embaralharPerguntas();
    }

    public void novaSessao(String id){
        Prova quiz = getQuizPaises();
        if(getSessaoAtual() == null){
            Prova.Sessao sessao = quiz.iniciarSessao(id);
            setSessaoAtual(sessao);
        }
    }

    public void forcarNovaSessao(String id){
        setSessaoAtual(getQuizPaises().iniciarSessao(id));
    }

    public PerguntaComImagem getPergunta(){
        Prova.Sessao sessao = getSessaoAtual();
        if(sessao != null){
            if(sessao.isEmpty()){
                sessao.encerrar();
                setSessaoAtual(null);
                return null;
            } else {
                return (PerguntaComImagem) sessao.enunciado();
            }
        }
        return null;
    }

    public void responder(String resposta){
        Prova.Sessao sessao = getSessaoAtual();
        if(sessao != null){
            sessao.responder(resposta);
            if(sessao.isEmpty()){
                sessao.encerrar();
                setSessaoAtual(null);
            }
        }
    }

    public void iniciarQuiz(String id){
        novaSessao(id);
    }

    public Intent criarIntentPergunta(Context context, Class<?> cls){
        PerguntaComImagem p = getPergunta();
        if(p != null){
            Intent it = new Intent(context, cls);
            it.putExtra("titulo", String.format("Questão %d de %d",
                    getIndex() +1, getNumPerguntas()));
            it.putExtra("imageId", p.getImageId());
            it.putExtra("enunciado", p.getEnunciado());
            it.putExtra("alternativas", p.getAlternativas());
            return it;
        }
        return  null;
    }

    public Intent criarIntentPlacar(Context context, Class<?> cls){
        LinkedList<Prova.Resolucao> lista = getQuizPaises().getResolucoes();
        Prova.Resolucao res = lista.getLast();
        Intent it = new Intent(context, cls);

        it.putExtra("id", res.id);
        it.putExtra("pontuacao", res.pontuacao);
        it.putExtra("seed", getSeed());
        return it;
    }

    public int estado_sessao(){
        Prova.Sessao sessao = getSessaoAtual();
        if(sessao == null || sessao.isEmpty()){
            return INATIVO;
        }
        return ATIVO;
    }
}