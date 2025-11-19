package com.example.applicationgestionrh;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class EmployesParDepartementActivity extends AppCompatActivity {

    private ListView listViewEmployes;
    private Spinner spinnerPostes;
    private TextView txtDepartement;
    private List<Employe> employesDuDepartement;
    private List<Employe> employesAffiches;
    private ArrayAdapter<Employe> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_employes_par_departement);

        String departement = getIntent().getStringExtra("departement_nom");
        if (departement == null) {
            Toast.makeText(this, "Erreur : département non spécifié", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        txtDepartement = findViewById(R.id.txt_titre_departement);
        spinnerPostes = findViewById(R.id.spinner_postes);
        listViewEmployes = findViewById(R.id.listViewEmployes);
        ImageView btn_retour = findViewById(R.id.btn_retour);
        txtDepartement.setText(departement);
        btn_retour.setOnClickListener(v -> {
            Intent intent = new Intent(EmployesParDepartementActivity.this, DepartementsActivity.class);
            startActivity(intent);
            finish();
        });
        employesDuDepartement = new ArrayList<>();
        for (Employe e : GestionEmployesActivity.listeEmployesOriginale) {
            if (e.getDepartement().equals(departement)) {
                employesDuDepartement.add(e);
            }
        }

        List<String> postesUniques = new ArrayList<>();
        postesUniques.add("Tous les postes");
        for (Employe e : employesDuDepartement) {
            if (!postesUniques.contains(e.getGrade())) {
                postesUniques.add(e.getGrade());
            }
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, postesUniques);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPostes.setAdapter(spinnerAdapter);

        employesAffiches = new ArrayList<>(employesDuDepartement);

       adapter = new ArrayAdapter<Employe>(this, android.R.layout.simple_list_item_2, android.R.id.text1, employesAffiches) {
            @Override
            public View getView(int position, View convertView, android.view.ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                Employe e = getItem(position);
                TextView text1 = view.findViewById(android.R.id.text1);
                TextView text2 = view.findViewById(android.R.id.text2);
                text1.setText(e.getPrenom() + " " + e.getNom());
                text2.setText(e.getGrade());
                return view;
            }
        };
        listViewEmployes.setAdapter(adapter);

        spinnerPostes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String posteSelectionne = parent.getItemAtPosition(position).toString();
                filtrerParPoste(posteSelectionne);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                filtrerParPoste("Tous les postes");
            }
        });
    }

    private void filtrerParPoste(String poste) {
        employesAffiches.clear();
        if (poste.equals("Tous les postes")) {
            employesAffiches.addAll(employesDuDepartement);
        } else {
            for (Employe e : employesDuDepartement) {
                if (e.getGrade().equals(poste)) {
                    employesAffiches.add(e);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }
}