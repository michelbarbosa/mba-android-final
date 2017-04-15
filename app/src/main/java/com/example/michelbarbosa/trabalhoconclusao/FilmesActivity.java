package com.example.michelbarbosa.trabalhoconclusao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.michelbarbosa.trabalhoconclusao.adapter.FilmeAdapter;
import com.example.michelbarbosa.trabalhoconclusao.dao.FilmeDao;

public class FilmesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filmes);

        FilmeDao dao = new FilmeDao(this);
        RecyclerView listaFilmes = (RecyclerView) findViewById(R.id.lista_filmes);
        FilmeAdapter adapter = new FilmeAdapter(this, dao.getAll(), dao);
        listaFilmes.setLayoutManager(new LinearLayoutManager(this));
        listaFilmes.setAdapter(adapter);
    }

    public void editar(View view) {
        int id = (int) view.getTag();
        Intent intent = new Intent(this, FormularioActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
