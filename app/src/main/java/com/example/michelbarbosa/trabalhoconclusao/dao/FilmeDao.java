package com.example.michelbarbosa.trabalhoconclusao.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.michelbarbosa.trabalhoconclusao.model.Filme;

import java.util.ArrayList;
import java.util.List;

public class FilmeDao {
    private final DbOpenHelper helper;

    public FilmeDao(Context context) {
        helper = new DbOpenHelper(context);
    }

    public Filme getById(int id) {
        Cursor cursor = null;
        try {
            SQLiteDatabase db = helper.getReadableDatabase();
            String colunas[] = { "titulo", "ano", "genero", "sinopse" };
            cursor = db.query(true, "filmes", colunas, "id = ?", new String[]{ String.valueOf(id) }, null, null, null, null);
            if (!cursor.moveToFirst()) {
                return null;
            }
            String titulo = cursor.getString(cursor.getColumnIndex("titulo"));
            int ano = cursor.getInt(cursor.getColumnIndex("ano"));
            String genero = cursor.getString(cursor.getColumnIndex("genero"));
            String sinopse = cursor.getString(cursor.getColumnIndex("sinopse"));
            return new Filme(id, titulo, ano, genero, sinopse);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public List<Filme> getAll() {
        List<Filme> filmes = new ArrayList<>();
        final String sql = "select id, titulo, ano, genero, sinopse from filmes order by titulo";
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String titulo = cursor.getString(1);
            int ano = cursor.getInt(2);
            String genero = cursor.getString(3);
            String sinopse = cursor.getString(4);
            Filme filme = new Filme(id, titulo, ano, genero, sinopse);
            filmes.add(filme);
        }
        cursor.close();
        return filmes;
    }

    public void save(Filme filme) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("titulo", filme.titulo);
        values.put("genero", filme.genero);
        values.put("ano", filme.ano);
        values.put("sinopse", filme.sinopse);
        if (filme.id != null) {
            db.update("filmes", values, "id = ?", new String[]{ String.valueOf(filme.id) });
            db.close();
            return;
        }
        long resultado = db.insert("filmes", null, values);
        db.close();
        if (resultado == -1) {
            throw new RuntimeException("Erro ao inserir filme");
        }
    }

    public void delete(int id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete("filmes", "id = ?", new String[]{ String.valueOf(id) });
        db.close();
    }
}
