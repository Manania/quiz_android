package br.edu.fatecguarulhos.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.mainBar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_appbar_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_set_seed:
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                LinearLayout layout = new LinearLayout(this);
                EditText edtTxt = new EditText(this);

                edtTxt.setHint("seed");
                layout.addView(edtTxt);
                layout.setOrientation(LinearLayout.VERTICAL);
                dialog.setView(layout);

                QuizHomeViewModel quiz = new ViewModelProvider(this).get(QuizHomeViewModel.class);
                dialog.setPositiveButton("Confirmar", (dialogInterface, btn_id) -> quiz.setSeed(edtTxt.getText().toString()) );
                dialog.setNegativeButton("Cancelar", (dialogInterface, btn_id) -> dialogInterface.cancel() );
                dialog.show();
                return true;
            case R.id.action_info:
                Intent it = new Intent(this, ConfigActivity.class);
                startActivity(it);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}