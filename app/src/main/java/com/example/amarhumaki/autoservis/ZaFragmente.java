package com.example.amarhumaki.autoservis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.amarhumaki.autoservis.Util.Util;
import com.example.amarhumaki.autoservis.data.Global;
import com.example.amarhumaki.autoservis.fragments.PonudaFragment;

public class ZaFragmente extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_za_fragmente);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Util.otvoriFragmentKaoReplace(this, R.id.mjestoFragment1, PonudaFragment.newInstance(Global.izabraniUpitID));

    }
}
