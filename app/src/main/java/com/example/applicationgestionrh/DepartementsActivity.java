package com.example.applicationgestionrh;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class DepartementsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_departements);

        ImageView imgHome = findViewById(R.id.img_home);
        imgHome.setOnClickListener(v -> {
            Intent intent = new Intent(DepartementsActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        findViewById(R.id.card_rh).setOnClickListener(v ->
                ouvrirListeEmployes("RH"));

       findViewById(R.id.card_informatique).setOnClickListener(v ->
                ouvrirListeEmployes("Informatique"));

        findViewById(R.id.card_finance).setOnClickListener(v ->
                ouvrirListeEmployes("Finance"));

        findViewById(R.id.card_marketing).setOnClickListener(v ->
                ouvrirListeEmployes("Marketing"));

       findViewById(R.id.card_production).setOnClickListener(v ->
                ouvrirListeEmployes("Production"));

        findViewById(R.id.card_logistique).setOnClickListener(v ->
                ouvrirListeEmployes("Logistique"));
    }

    private void ouvrirListeEmployes(String nomDepartement) {
        Intent intent = new Intent(this, EmployesParDepartementActivity.class);
        intent.putExtra("departement_nom", nomDepartement);
        startActivity(intent);
    }
}