package com.example.amarhumaki.autoservis;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amarhumaki.autoservis.data.AutentifikacijaResultVM;
import com.example.amarhumaki.autoservis.data.Global;
import com.example.amarhumaki.autoservis.data.UpitiPostVM;
import com.example.amarhumaki.autoservis.helper.MyApiRequest;
import com.example.amarhumaki.autoservis.helper.MyRunnable;

import java.util.Calendar;

public class noviupit extends AppCompatActivity {

    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;
    private EditText markaAuta;
    private EditText podaciOMotoru;
    private EditText opisKvara;
    private Button posaljiBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noviupit);

        markaAuta = findViewById(R.id.markaAutaInput);
        podaciOMotoru = findViewById(R.id.podaciOMotoruInput);
        opisKvara = findViewById(R.id.opisKvaraInput);
        posaljiBtn = findViewById(R.id.posaljiBtn);

        posaljiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_posaljiBtnClick();
            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarAdd);
        setSupportActionBar(toolbar);

        dl = (DrawerLayout) findViewById(R.id.dl);
        abdt = new ActionBarDrawerToggle(
                this, dl, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        dl.addDrawerListener(abdt);
        abdt.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navView);

        //postavljanje username-a
        View headerView = navigationView.inflateHeaderView(R.layout.navigation_header);
        TextView t = (TextView) headerView.findViewById(R.id.korisnikTxt);
        t.setText("Dobrodosli, " + Global.prijavljeniKlijent.Ime + " " + Global.prijavljeniKlijent.Prezime + ".");

        //postavljanje navigation bara
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                int id = item.getItemId();

                if(id == R.id.item1)
                {
                    Intent intent = new Intent(noviupit.this, MainActivity.class);
                    startActivity(intent);
                }
                if(id == R.id.item2)
                {
//                    Intent intent = new Intent(noviupit.this, noviupit.class);
//                    startActivity(intent);
                }
                if(id == R.id.item3)
                {
                    Intent intent = new Intent(noviupit.this, listaupita.class);
                    startActivity(intent);
                }
                if(id == R.id.item4)
                {
                    izbrisiToken();
                    startActivity(new Intent(noviupit.this, login.class));
                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.dl);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void do_posaljiBtnClick() {
        if(validacija())
        {
            UpitiPostVM model = new UpitiPostVM();
            model.MarkaAuta = markaAuta.getText().toString();
            model.PodaciOMotoru = podaciOMotoru.getText().toString();
            model.OpisKvara = opisKvara.getText().toString();
            model.DatumSlanja = Calendar.getInstance().getTime();
            model.KlijentID = Global.prijavljeniKlijent.KlijentID;

            MyApiRequest.post(this, "api/Upiti", model, new MyRunnable<UpitiPostVM>(){
                @Override
                public void run(UpitiPostVM x)
                {
                    Toast.makeText(noviupit.this, "Upit je uspješno poslan.", Toast.LENGTH_SHORT).show();

                }
            });
            startActivity(new Intent(noviupit.this, listaupita.class));
        }
    }

    private boolean validacija()
    {
        AlertDialog adb = new AlertDialog.Builder(noviupit.this).create();
        adb.setTitle("Greška");
        adb.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        if(markaAuta.getText().toString().length() < 3)
        {
            adb.setMessage("Marka auta treba da sadrzi minimalno 3 karaktera.");
            adb.show();
            return false;
        }

        if(podaciOMotoru.getText().toString().length() < 3)
        {
            adb.setMessage("Podaci o motoru treba da sadrze minimalno 10 karaktera.");
            adb.show();
            return false;
        }

        if(opisKvara.getText().toString().length() < 6)
        {
            adb.setMessage("Opis kvara treba da sadrzi minimalno 15 karaktera.");
            adb.show();
            return false;
        }

        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                dl.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.dl);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            startActivity(new Intent(noviupit.this, MainActivity.class));
        }
    }

    private void izbrisiToken() {

        MyApiRequest.delete(this, "api/autentifikacija/Logout", new MyRunnable<AutentifikacijaResultVM>() {
            @Override
            public void run(AutentifikacijaResultVM x) {
                //
            }
        });

    }
}
