package com.example.michelbarbosa.trabalhoconclusao;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.michelbarbosa.trabalhoconclusao.dao.UsuarioDao;

public class LoginActivity extends AppCompatActivity {

    public static final String APP_KEY = "trabalho_final";
    public static final String LOGIN_KEY = "login";

    private EditText campoLogin;
    private EditText campoSenha;
    private CheckBox campoManterConectado;
    private UsuarioDao dao;

    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pref = getSharedPreferences(APP_KEY, MODE_PRIVATE);
        if (!pref.getString(LOGIN_KEY, "").isEmpty()) {
            goToMain();
            return;
        }

        campoLogin = (EditText) findViewById(R.id.campo_login);
        campoSenha = (EditText) findViewById(R.id.campo_password);
        campoManterConectado = (CheckBox) findViewById(R.id.campo_manter_conectado);
        dao = new UsuarioDao(this);
    }


    public void login(View view) {
        String login = campoLogin.getText().toString();
        String senha = campoSenha.getText().toString();
        boolean exists = dao.exists(login, senha);
        if (!exists) {
            String mensagem = getString(R.string.invalid_login_and_or_pass);
            Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show();
            return;
        }
        boolean manterConectado = campoManterConectado.isChecked();
        if (!manterConectado) {
            goToMain();
            return;
        }
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(LOGIN_KEY, login);
        editor.apply();
        goToMain();
    }

    private void goToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
