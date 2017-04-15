package com.example.michelbarbosa.trabalhoconclusao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.michelbarbosa.trabalhoconclusao.R;
import com.example.michelbarbosa.trabalhoconclusao.dao.FilmeDao;
import com.example.michelbarbosa.trabalhoconclusao.model.Filme;

import java.util.List;

public class FilmeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<Filme> filmes;
    private final FilmeDao dao;

    public FilmeAdapter(Context context, List<Filme> filmes, FilmeDao dao) {
        this.context = context;
        this.filmes = filmes;
        this.dao = dao;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.filme, parent, false);
        return new FilmeHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FilmeHolder itemHolder = (FilmeHolder) holder;
        Filme filme = filmes.get(position);

        itemHolder.titulo.setText(filme.titulo);
        itemHolder.ano.setText(String.valueOf(filme.ano));
        itemHolder.genero.setText(filme.genero);
        itemHolder.editar.setTag(filme.id);
        itemHolder.excluir.setOnClickListener(new Excluir(position));
    }

    @Override
    public int getItemCount() {
        return filmes.size();
    }

    private class FilmeHolder extends RecyclerView.ViewHolder {
        private final TextView titulo;
        private final TextView ano;
        private final TextView genero;
        private final Button editar;
        private final Button excluir;

        FilmeHolder(View itemView) {
            super(itemView);

            titulo = (TextView) itemView.findViewById(R.id.titulo);
            ano = (TextView) itemView.findViewById(R.id.ano);
            genero = (TextView) itemView.findViewById(R.id.genero);
            editar = (Button) itemView.findViewById(R.id.editar);
            excluir = (Button) itemView.findViewById(R.id.excluir);
        }
    }

    private class Excluir implements View.OnClickListener {
        private final int position;

        public Excluir(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            dao.delete(filmes.get(position).id);
            filmes.remove(position);
            notifyDataSetChanged();
        }
    }
}
