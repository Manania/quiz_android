package br.edu.fatecguarulhos.quiz;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class QuizHomeFragment extends Fragment {
    private QuizHomeViewModel quizVwModel;
    private Button btn_sair, btn_iniciar;
    private EditText edtTxt_id_jogador;
    private TextView txtVw_seed;
    final private ActivityResultLauncher<Intent> getResposta = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            (activityResult) -> {
                switch (activityResult.getResultCode()){
                    case TelaPergunta.RESPONDIDO:
                        Intent it = activityResult.getData();
                        quizVwModel.responder(it.getStringExtra("resposta"));
                        continuarSessao();
                        break;
                    case TelaPergunta.CANCELADO:
                        limpar_id();
                        break;
                    default: break;
                }
            });
    final private ActivityResultLauncher<Intent> launchPlacar = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            (activityResult) -> {
                switch (activityResult.getResultCode()){
                    case TelaPlacar.NOVAMENTE:
                        iniciarQuiz();
                        break;
                    default:
                        limpar_id();
                        break;
                }
            });

    public static QuizHomeFragment newInstance() {
        return new QuizHomeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quiz_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        quizVwModel = new ViewModelProvider(requireActivity()).get(QuizHomeViewModel.class);

        btn_iniciar = view.findViewById(R.id.btn_iniciar_quiz);
        btn_sair = view.findViewById(R.id.btn_sair);
        edtTxt_id_jogador = view.findViewById(R.id.edtTxt_id_jogador);
        txtVw_seed = view.findViewById(R.id.txtVw_seed);

        txtVw_seed.setText(String.format("seed:%s", quizVwModel.getSeed()));
        quizVwModel.setOnSeedChangeListener((vm) -> txtVw_seed.setText(String.format("seed:%s", vm.getSeed())));

        btn_iniciar.setEnabled(false);
        btn_iniciar.setOnClickListener((view1) -> iniciarQuiz());

        edtTxt_id_jogador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                btn_iniciar.setEnabled(!charSequence.toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        btn_sair.setOnClickListener((view1) -> requireActivity().finish());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    private void iniciarQuiz(){
        quizVwModel.forcarNovaSessao(edtTxt_id_jogador.getText().toString());
        continuarSessao();
    }

    private void continuarSessao(){
        if(quizVwModel.estado_sessao() == QuizHomeViewModel.ATIVO){
            getResposta.launch(quizVwModel.criarIntentPergunta(getContext(), TelaPergunta.class));
        } else {
            launchPlacar.launch(quizVwModel.criarIntentPlacar(getContext(), TelaPlacar.class));
        }
    }

    private void limpar_id(){
        edtTxt_id_jogador.setText("");
    }
}
