package br.edu.fatecguarulhos.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class TelaPergunta extends AppCompatActivity {
    final public static int RESPONDIDO = 0, CANCELADO = 1;
    RadioGroup rdGrpAlternativas;
    TextView txtVwEnunciado, txtVwTitulo;
    ImageView imgVwImagem;
    Button btn_responder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_pergunta);

        rdGrpAlternativas = findViewById(R.id.rdGrpAlternativas);
        imgVwImagem =  findViewById(R.id.imgVwImagem);
        txtVwTitulo = findViewById(R.id.txtVwTitulo);
        txtVwEnunciado = findViewById(R.id.txtVwEnunciado);
        btn_responder = findViewById(R.id.btn_responder);

        btn_responder.setEnabled(false);
        btn_responder.setOnClickListener(view -> responder());
        rdGrpAlternativas.setOnCheckedChangeListener((radioGroup, i) -> btn_responder.setEnabled(true));

        Intent it = getIntent();
        txtVwTitulo.setText(it.getStringExtra("titulo"));
        txtVwEnunciado.setText(it.getStringExtra("enunciado"));
        for(String alt : it.getStringArrayExtra("alternativas")){
            RadioButton btt = new RadioButton(this);
            btt.setText(alt);
            rdGrpAlternativas.addView(btt);
        }
        int imageId = it.getIntExtra("imageId", -1);
        if(imageId != -1){
            imgVwImagem.setImageResource(imageId);
        }
    }

    public void responder(){
        if(rdGrpAlternativas.getCheckedRadioButtonId() == -1){
            return;
        }
        Intent it = new Intent();
        RadioButton btt = findViewById(rdGrpAlternativas.getCheckedRadioButtonId());
        String txt = btt.getText().toString();
        it.putExtra("resposta", txt);
        setResult(RESPONDIDO, it);
        finish();
    }

    @Override
    public void onBackPressed() {
        setResult(CANCELADO);
        super.onBackPressed();
        finish();
    }
}
