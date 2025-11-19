package com.example.applicationgestionrh;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class FormulaireEmployeActivity extends AppCompatActivity {

    private TextInputEditText editMatricule, editNom, editPrenom, editPoste, editDepartement;
    private Spinner spinnerStatut;
    private Button btnEnregistrer;
    private ImageView imgHome;

    private Employe employeEnEdition = null;
    private boolean modeEdition = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_formulaire_employe);

        editMatricule = findViewById(R.id.editMatricule);
        editNom = findViewById(R.id.editNom);
        editPrenom = findViewById(R.id.editPrenom);
        editPoste = findViewById(R.id.editPoste);
        editDepartement = findViewById(R.id.editDepartement);
        spinnerStatut = findViewById(R.id.spinnerStatut);
        btnEnregistrer = findViewById(R.id.btnEnregistrer);
        imgHome = findViewById(R.id.img_home);
        Intent intent = getIntent();
        if (intent.hasExtra("mode") && "EDIT".equals(intent.getStringExtra("mode"))) {
            modeEdition = true;
            int employeId = intent.getIntExtra("employe_id", -1);

            for (Employe e : GestionEmployesActivity.listeEmployesOriginale) {
                if (e.getId() == employeId) {
                    employeEnEdition = e;
                    break;
                }
            }

            if (employeEnEdition != null) {
                editMatricule.setText(employeEnEdition.getMatricule());
                editNom.setText(employeEnEdition.getNom());
                editPrenom.setText(employeEnEdition.getPrenom());
                editPoste.setText(employeEnEdition.getGrade());
                editDepartement.setText(employeEnEdition.getDepartement());
             }
        }

        btnEnregistrer.setOnClickListener(v -> enregistrerEmploye());
        imgHome.setOnClickListener(v -> finish());
    }



    private void enregistrerEmploye() {
        String matricule = editMatricule.getText().toString().trim();
        String nom = editNom.getText().toString().trim();
        String prenom = editPrenom.getText().toString().trim();
        String poste = editPoste.getText().toString().trim();
        String departement = editDepartement.getText().toString().trim();
        String statut = spinnerStatut.getSelectedItem().toString();

        if (matricule.isEmpty() || nom.isEmpty() || prenom.isEmpty() || poste.isEmpty() || departement.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs obligatoires.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent resultIntent = new Intent();

       if (modeEdition && employeEnEdition != null) {
            Employe employeModifie = new Employe(
                    employeEnEdition.getId(),
                    nom,
                    prenom,
                    matricule,
                    departement,
                    poste,
                    statut
            );
            resultIntent.putExtra("employe_modifie", employeModifie);
        } else {
            Employe nouvelEmploye = new Employe(
                    matricule,
                    nom,
                    prenom,
                    poste,
                    departement,
                    statut
            );
            resultIntent.putExtra("nouvel_employe", nouvelEmploye);
        }

        setResult(RESULT_OK, resultIntent);
        finish();
    }
}