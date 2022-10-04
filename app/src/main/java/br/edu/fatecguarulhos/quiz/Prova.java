package br.edu.fatecguarulhos.quiz;

import java.util.Iterator;
import java.util.LinkedList;

public class Prova implements Iterable<Pergunta>{
    final private Pergunta[] perguntas;
    private LinkedList<Resolucao> resolucoes = new LinkedList<>();

    public class Resolucao{
        final public String id;
        final public String[] respostas;
        final public int pontuacao;
        Resolucao(String id, String[] respostas, int pontuacao){
            this.id = id;
            this.respostas = respostas;
            this.pontuacao = pontuacao;
        }

        @Override
        public String toString() {
            StringBuilder str = new StringBuilder(id.length() + respostas.length * 10);
            //TODO resolver conflito API
            //str.append("\"id\":%s,\n".formatted(id));
            str.append(String.format("\"id\":%s,\n", id));
            str.append("\"respostas\": [\n");
            for(Pergunta p : perguntas){
                str.append('\"');
                str.append(p);
                str.append("\",\n");
            }
            str.append("]\n");
            return super.toString();
        }
    }

    public class Sessao implements Iterator<Pergunta>{
        private int i = 0;
        String id;
        String respostas[] = new String[perguntas.length];

        public Sessao(String id){
            this.id = id;
        }

        public int getIndex(){
            return i;
        }

        public int getNumPerguntas(){
            return perguntas.length;
        }

        @Override
        public boolean hasNext() {
            return (perguntas.length - i) > 1;
        }

        @Override
        public Pergunta next() {
            return perguntas[i++];
        }

        public boolean isEmpty() { return (perguntas.length - i) < 1; }

        public Pergunta enunciado() {
            return perguntas[i];
        }

        public void responder(String resposta){ respostas[i++] = resposta; }

        public void responder(String resposta, Pergunta p){
            respostas[indexOf(p)] = resposta;
        }

        public void encerrar(){
            Resolucao r = new Resolucao(id, respostas, pontuacao(respostas));
            resolucoes.add(r);
            //TODO resolver conflito API
            //resolucoes.sort((o1, o2) -> o2.pontuacao - o1.pontuacao);
        }
    }

    public Prova(Pergunta[] perguntas){
        this.perguntas = perguntas;
    }

    public Pergunta[] getPerguntas(){
        return perguntas.clone();
    }

    public LinkedList<Resolucao> getResolucoes(){
        return resolucoes;
    }

    private int indexOf(Pergunta p){
        for(int i = 0; i < perguntas.length; i++)
            if(p == perguntas[i]) return i;
        return -1;
    }

    public Prova embaralharPerguntas(){
        int len = perguntas.length;
        int[] indices = Randomizer.uniqueRandomInRange(0, len -1, len);
        Pergunta[] nova_ordem = new Pergunta[len];
        for(int i = 0; i < len; i++){
            nova_ordem[i] = perguntas[indices[i]];
        }
        return new Prova(nova_ordem);
    }

    public void embaralharAlternativas(){
        for(Pergunta p : perguntas){
            p.embaralhar();
        }
    }

    public int pontuacao(String[] resposta){
        int total = 0;
        for(int i = 0; i < perguntas.length; i++){
            if(perguntas[i].alternativaCorreta(resposta[i])){
                total++;
            }
        }
        return total;
    }

    public void submeterResposta(String id, String[] respostas){
        Resolucao r = new Resolucao(id, respostas, pontuacao(respostas));
        resolucoes.add(r);
        //TODO resolver conflito de API < 24
        //resolucoes.sort((o1, o2) -> o2.pontuacao - o1.pontuacao);
    }

    public Sessao iniciarSessao(String id){
        return new Sessao(id);
    }

    @Override
    public Iterator<Pergunta> iterator() {
        return new Iterator<Pergunta>() {
            int i = 0;
            @Override
            public boolean hasNext() {
                return perguntas.length -i > 0;
            }

            @Override
            public Pergunta next() {
                return perguntas[i++];
            }
        };
    }
}

