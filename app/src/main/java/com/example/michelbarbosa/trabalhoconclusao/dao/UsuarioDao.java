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
        ContentValues values = new ContentValues();
        values.put("senha", senha);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.query(true, "usuarios", new String[]{ "login" }, "login = ?", new String[]{ login }, null, null, null, null);
        if (cursor != null) {
            db.update("usuarios", values, "login = ?", new String[]{ login });
            db.close();
            return;
        }
        values.put("login", login);
        db.insert("usuarios", null, values);
        db.close();
    }
}
