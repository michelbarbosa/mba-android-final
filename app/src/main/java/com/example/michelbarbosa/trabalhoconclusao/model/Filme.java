package com.example.michelbarbosa.trabalhoconclusao.model;

public class Filme {
    public final Integer id;
    public final String titulo;
    public final int ano;
    public final String genero;
    public final String sinopse;

    public Filme(Integer id, String titulo, int ano, String genero, String sinopse) {
        this.id = id;
        this.titulo = titulo;
        this.ano = ano;
        this.genero = genero;
        this.sinopse = sinopse;
    }

    public Filme(Integer id, String titulo, String ano, String genero, String sinopse) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new RuntimeException();
        }
        if (genero == null || genero.trim().isEmpty()) {
            throw new RuntimeException();
        }
        if (sinopse == null || sinopse.trim().isEmpty()) {
            throw new RuntimeException();
        }
        this.id = id;
        this.titulo = titulo;
        this.ano = Integer.parseInt(ano);
        this.genero = genero;
        this.sinopse = sinopse;
    }
}
