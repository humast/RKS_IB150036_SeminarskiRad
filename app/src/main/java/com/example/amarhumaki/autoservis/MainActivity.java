package com.example.amarhumaki.autoservis;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amarhumaki.autoservis.data.AutentifikacijaResultVM;
import com.example.amarhumaki.autoservis.data.Global;
import com.example.amarhumaki.autoservis.data.KlijentPostVM;
import com.example.amarhumaki.autoservis.helper.MyApiRequest;
import com.example.amarhumaki.autoservis.helper.MyRunnable;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
//                    Intent intent = new Intent(MainActivity.this, Pocetna.class);
//                    startActivity(intent);
                }
                if(id == R.id.item2)
                {
                    Intent intent = new Intent(MainActivity.this, noviupit.class);
                    startActivity(intent);
                }
                if(id == R.id.item3)
                {
                    Intent intent = new Intent(MainActivity.this, listaupita.class);
                    startActivity(intent);
                }
                if(id == R.id.item4)
                {
                    izbrisiToken();
                    startActivity(new Intent(MainActivity.this, login.class));
                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.dl);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//        menuItem.setChecked(true);
        int id = menuItem.getItemId();

        if(id == R.id.item1)
        {

        }
        if(id == R.id.item2)
        {
            Intent intent = new Intent(MainActivity.this, noviupit.class);
            startActivity(intent);
        }
        if(id == R.id.item3)
        {
            Intent intent = new Intent(MainActivity.this, listaupita.class);
            startActivity(intent);
        }
        if(id == R.id.item4)
        {
            izbrisiToken();
            startActivity(new Intent(MainActivity.this, login.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.dl);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
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
            startActivity(new Intent(MainActivity.this, MainActivity.class));
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
