package com.example.amarhumaki.autoservis;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.amarhumaki.autoservis.data.AutentifikacijaLoginPostVM;
import com.example.amarhumaki.autoservis.data.AutentifikacijaResultVM;
import com.example.amarhumaki.autoservis.data.Global;
import com.example.amarhumaki.autoservis.helper.MyApiRequest;
import com.example.amarhumaki.autoservis.helper.MyRunnable;

public class login extends AppCompatActivity {

    private EditText txtUsername;
    private EditText txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        Button btnRegistracija = (Button) findViewById(R.id.btnRegistracija);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_btnLogin_click();
            }
        });

        btnRegistracija.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this, registracija.class));
            }
        });
    }

    private void do_btnLogin_click() {

        if(validacija())
        {
            String strUsername = txtUsername.getText().toString();
            String strPassword = txtPassword.getText().toString();

            AutentifikacijaLoginPostVM model = new AutentifikacijaLoginPostVM(strUsername, strPassword);

            MyApiRequest.get(this, "api/Autentifikacija/LoginCheck/" + strUsername + "/" + strPassword, new MyRunnable<AutentifikacijaResultVM>() {
                @Override
                public void run(AutentifikacijaResultVM x)
                {
                    checkLogin(x);
                }
            });
        }
        else
        {
            AlertDialog alertDialog = new AlertDialog.Builder(login.this).create();
            alertDialog.setTitle("Greska");
            alertDialog.setMessage("Unesite korisnicko ime i lozinku.");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
    }

    private void checkLogin(AutentifikacijaResultVM x) {
        if ("PogresniPodaci".equals(x.Ime)) {
            View parentLayout = findViewById(android.R.id.content);
            Snackbar.make(parentLayout, "Pogrešno korisničko ime/lozinka.", Snackbar.LENGTH_LONG).show();
        } else {
            Global.prijavljeniKlijent = x;
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    private boolean validacija() {
        if (txtUsername.getText().toString().isEmpty())
            return false;
        if (txtPassword.getText().toString().isEmpty())
            return false;
        return true;
    }

    @Override
    public void onBackPressed() {

        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);

    }
}
