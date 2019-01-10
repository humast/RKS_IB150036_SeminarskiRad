package com.example.amarhumaki.autoservis.fragments;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amarhumaki.autoservis.R;
import com.example.amarhumaki.autoservis.data.Global;
import com.example.amarhumaki.autoservis.data.PonudeResultVM;
import com.example.amarhumaki.autoservis.helper.MyApiRequest;
import com.example.amarhumaki.autoservis.helper.MyRunnable;
import com.example.amarhumaki.autoservis.noviupit;


public class PonudaFragment extends Fragment {

    PonudeResultVM p;

    TextView markaAuta;
    TextView cijena;
    TextView datumPocetka;
    TextView datumZavrsetka;
    Button odbijBtn;
    Button prihvatiBtn;


    private static final String ARG_PARAM1 = "param1";

    private int upitID;


    public PonudaFragment() {
        // Required empty public constructor
    }

    public static PonudaFragment newInstance(int param1) {
        PonudaFragment fragment = new PonudaFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            upitID = (int) getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ponuda, container, false);


        markaAuta = view.findViewById(R.id.MarkaAutaTxtP);
        cijena = view.findViewById(R.id.CijenaTxtP);
        datumPocetka = view.findViewById(R.id.DatumPocetkaTxtP);
        datumZavrsetka = view.findViewById(R.id.DatumZavrsetkaTxtP);
        odbijBtn = view.findViewById(R.id.odbijBtn);
        prihvatiBtn = view.findViewById(R.id.prihvatiBtn);

        odbijBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p.Odgovoren=true;
                p.Prihvacen=false;
                do_odbijBtnClick();
            }
        });

        prihvatiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p.Odgovoren=true;
                p.Prihvacen=true;
                do_prihvatiBtnClick();
            }
        });

        popuniPodatkeTask();

        return view;
    }

    private void do_prihvatiBtnClick() {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("Potvrda");
        alertDialog.setMessage("Prihvatate ponudu?");

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "DA",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        potvrdaIzmjene();
                        dialog.dismiss();
                    }
                });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NE",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        alertDialog.show();

    }

    private void do_odbijBtnClick() {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("Potvrda");
        alertDialog.setMessage("Odbijate ponudu?");

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "DA",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        potvrdaIzmjene();
                        dialog.dismiss();
                    }
                });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NE",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        alertDialog.show();

    }

    private void potvrdaIzmjene() {
        MyApiRequest.put(getActivity(), "api/Ponude/", p, new MyRunnable<PonudeResultVM>() {
            @Override
            public void run(PonudeResultVM x) {
                Toast.makeText(getActivity(), "Odgovor je uspješno poslan.", Toast.LENGTH_SHORT).show();
            }
        });
        dizajnirajButtone();
    }


    private void popuniPodatkeTask() {

        MyApiRequest.get(getActivity(), "/api/Ponude/GetPonudaByID/" + String.valueOf(upitID), new MyRunnable<PonudeResultVM>() {
            @Override
            public void run(PonudeResultVM x) {
                p = x;
                popuniPodatke();
            }
        });

    }

    private void popuniPodatke() {

        markaAuta.setText(p.MarkaAuta);
        cijena.setText(p.Cijena + " KM");
        datumPocetka.setText(p.DatumPocetka);
        datumZavrsetka.setText(p.DatumZavrsetka);

        dizajnirajButtone();

    }

    private void dizajnirajButtone() {
        if(p.Prihvacen==true && p.Odgovoren==true)
        {
            prihvatiBtn.setText("PRIHVAĆENO");
            odbijBtn.setVisibility(View.GONE);
            prihvatiBtn.setEnabled(false);
//            odbijBtn.setVisibility(View.VISIBLE);
        }
        else if(p.Prihvacen==false && p.Odgovoren==true)
        {
            odbijBtn.setText("ODBIJENO");
            prihvatiBtn.setVisibility(View.GONE);
            odbijBtn.setEnabled(false);
        }
        else
        {
            prihvatiBtn.setEnabled(true);
            odbijBtn.setEnabled(true);
        }
    }


}
