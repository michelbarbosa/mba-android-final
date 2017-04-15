package com.example.michelbarbosa.trabalhoconclusao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.michelbarbosa.trabalhoconclusao.dao.FilmeDao;
import com.example.michelbarbosa.trabalhoconclusao.model.Filme;

public class FormularioActivity extends AppCompatActivity {

    private EditText campoTitulo;
    private Spinner campoGenero;
    private EditText campoAno;
    private EditText campoSinopse;
    private FilmeDao dao;
    private Integer id = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        dao = new FilmeDao(this);
        campoTitulo = (EditText) findViewById(R.id.campo_titulo);
        campoGenero = (Spinner) findViewById(R.id.campo_genero);
        campoAno = (EditText) findViewById(R.id.campo_ano);
        campoSinopse = (EditText) findViewById(R.id.campo_sinopse);

        int id = getIntent().getIntExtra("id", -1);
        if (id == -1) {
            return;
        }
        this.id = id;
        Filme filme = dao.getById(id);
        campoTitulo.setText(filme.titulo);
        campoGenero.setSelection(((ArrayAdapter)campoGenero.getAdapter()).getPosition(filme.genero));
        campoAno.setText(String.valueOf(filme.ano));
        campoSinopse.setText(filme.sinopse);
    }

    public void salvar(View view) {
        Filme filme = criarFilme();
        dao.save(filme);
        goToMain();
    }

    @Override
    public void onBackPressed() {
        goToMain();
    }

    private Filme criarFilme() {
        String titulo = campoTitulo.getText().toString();
        String genero = campoGenero.getSelectedItem().toString();
        String sinopse = campoSinopse.getText().toString();
        int ano = Integer.parseInt(campoAno.getText().toString());
        return new Filme(id, titulo, ano, genero, sinopse);
    }

    private void goToMain () {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
