package com.example.michelbarbosa.trabalhoconclusao.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UsuarioDao {
    private final DbOpenHelper helper;

    public UsuarioDao(Context context) {
        helper = new DbOpenHelper(context);
    }

    public void save(String login, String senha) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            ContentValues values = new ContentValues();
            values.put("senha", senha);
            db = helper.getWritableDatabase();
            cursor = db.query(true, "usuarios", new String[]{ "login" }, "login = ?", new String[]{ login }, null, null, null, null);
            if (cursor.moveToFirst()) {
                db.update("usuarios", values, "login = ?", new String[]{ login });
                db.close();
                return;
            }
            values.put("login", login);
            db.insert("usuarios", null, values);
            db.close();
        } finally {
            close(cursor, db);
        }
    }

    public boolean exists(String login, String senha) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(true, "usuarios", new String[]{ "login" }, "login = ? and senha = ?", new String[]{ login, senha }, null, null, null, null);
        boolean exists = cursor.moveToFirst();
        close(cursor, db);
        return exists;
    }

    private static void close(Cursor cursor, SQLiteDatabase db) {
        if (cursor != null) {
            cursor.close();
        }
        if (db != null) {
            db.close();
        }
    }
}
