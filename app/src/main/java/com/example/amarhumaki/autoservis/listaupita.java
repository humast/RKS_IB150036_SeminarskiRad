package com.example.amarhumaki.autoservis;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amarhumaki.autoservis.Util.Util;
import com.example.amarhumaki.autoservis.data.AutentifikacijaResultVM;
import com.example.amarhumaki.autoservis.data.Global;
import com.example.amarhumaki.autoservis.data.KlijentPostVM;
import com.example.amarhumaki.autoservis.data.PonudeResultVM;
import com.example.amarhumaki.autoservis.data.UpitiPostVM;
import com.example.amarhumaki.autoservis.data.UpitiResultVM;
import com.example.amarhumaki.autoservis.fragments.PonudaFragment;
import com.example.amarhumaki.autoservis.helper.MyApiRequest;
import com.example.amarhumaki.autoservis.helper.MyRunnable;

import java.util.ArrayList;
import java.util.List;

public class listaupita extends AppCompatActivity {

    private DrawerLayout dl;
    ActionBarDrawerToggle abdt;
    private ListView rv;
    private UpitiResultVM podaci;
    private BaseAdapter adapter;
    private ListView lvUpiti;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listaupita);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarAdd);
        setSupportActionBar(toolbar);

        dl = (DrawerLayout) findViewById(R.id.dl);
        abdt = new ActionBarDrawerToggle(
                this, dl, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        dl.addDrawerListener(abdt);
        abdt.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navView);

        ImageView floatingDodajUpit = findViewById(R.id.floatingDodajUpit);
        floatingDodajUpit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(listaupita.this, noviupit.class));
            }
        });

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
                    Intent intent = new Intent(listaupita.this, MainActivity.class);
                    startActivity(intent);
                }
                if(id == R.id.item2)
                {
                    Intent intent = new Intent(listaupita.this, noviupit.class);
                    startActivity(intent);
                }
                if(id == R.id.item3)
                {
//                    Intent intent = new Intent(listaupita.this, listaupita.class);
//                    startActivity(intent);
                }
                if(id == R.id.item4)
                {
                    izbrisiToken();
                    startActivity(new Intent(listaupita.this, login.class));
                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.dl);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        lvUpiti = (ListView) findViewById(R.id.lvUpiti);

        lvUpiti.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String odabrani = ((TextView) view.findViewById(R.id.SkriveniUpitID)).getText().toString();
                Global.izabraniUpitID = Integer.parseInt(odabrani);

                provjeriPostojanjePonude();
            }
        });

        popuniPodatkeTask();

    }

    private void provjeriPostojanjePonude() {
        MyApiRequest.get(this, "/api/Ponude/GetPostojanjePonude/" + String.valueOf(Global.izabraniUpitID), new MyRunnable<String>() {
            @Override
            public void run(String x) {
                if ("Postoji".equals(x))
                {
                    otvoriPonudu();
                }
                else
                {
                    Toast.makeText(listaupita.this, "Ponuda još nije kreirana.", Toast.LENGTH_LONG).show();
                }
            }
        });
//        otvoriPonudu();
    }

    private void otvoriPonudu() {

        startActivity(new Intent(this, ZaFragmente.class));
    }

    private void popuniPodatkeTask() {
        MyApiRequest.get(this, "/api/upiti/getUpitiByKlijentID/" + String.valueOf(Global.prijavljeniKlijent.KlijentID), new MyRunnable<UpitiResultVM>() {
            @Override
            public void run(UpitiResultVM x) {
                podaci = x;
                popuniListu();
            }
        });
    }

    private void popuniListu() {
        if (podaci.rows.size() > 0) {

            adapter = new BaseAdapter() {
                @Override
                public int getCount() {
                    return podaci.rows.size();
                }

                @Override
                public Object getItem(int position) {
                    return null;
                }

                @Override
                public long getItemId(int position) {
                    return 0;
                }

                @Override
                public View getView(int position, View view, ViewGroup parent) {

                    if (view == null) {
                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        view = inflater.inflate(R.layout.itemupiti, parent, false);
                    }
                    TextView txtFirstLine = view.findViewById(R.id.NaslovUpitaTxt);
                    TextView txtSecondLine = view.findViewById(R.id.DatumSlanjaTxt);
                    TextView txtSkriveniID = view.findViewById(R.id.SkriveniUpitID);

                    UpitiResultVM.Row x = podaci.rows.get(position);

                    txtFirstLine.setText(x.MarkaAuta);
                    txtSecondLine.setText(x.DatumSlanja);
                    txtSkriveniID.setText(String.valueOf(x.UpitID));

                    return view;
                }
            };

            lvUpiti.setAdapter(adapter);
        } else {
            TextView u = (TextView) findViewById(R.id.textView3);
            u.setText("Nije pronađen nijedan upit");
        }
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
            startActivity(new Intent(listaupita.this, MainActivity.class));
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
