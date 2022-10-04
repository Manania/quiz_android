package br.edu.fatecguarulhos.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class TelaPlacar extends AppCompatActivity {
    final public static int VOLTAR = 0, NOVAMENTE = 1;
    TextView txtVwPlacarNome, txtVwPlacarPontuacao, txtVwPlacarSeed;
    Button bttVoltar, bttTentarNovamente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_placar);

        txtVwPlacarNome = findViewById(R.id.txtViewPlacarNome);
        txtVwPlacarPontuacao = findViewById(R.id.txtViewPlacarPontuacao);
        txtVwPlacarSeed = findViewById(R.id.txtVw_placar_seed_value);
        bttVoltar = findViewById(R.id.btn_placar_voltar);
        bttTentarNovamente = findViewById(R.id.btn_placar_novamente);

        bttVoltar.setOnClickListener(view -> {
            setResult(VOLTAR);
            finish();
        });

        bttTentarNovamente.setOnClickListener(view -> {
            setResult(NOVAMENTE);
            finish();
        });

        Intent it = getIntent();
        txtVwPlacarNome.setText(it.getStringExtra("id"));
        txtVwPlacarPontuacao.setText(String.valueOf(it.getIntExtra("pontuacao", 0)));
        txtVwPlacarSeed.setText(it.getStringExtra("seed"));
    }
}