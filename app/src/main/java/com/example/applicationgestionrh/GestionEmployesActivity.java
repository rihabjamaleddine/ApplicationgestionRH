package com.example.applicationgestionrh;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class GestionEmployesActivity extends AppCompatActivity {

    private List<Employe> listeEmployes = new ArrayList<>();
    public static List<Employe> listeEmployesOriginale = new ArrayList<>();
    private EditText editTextSearch;
    private Button btnAjouter, btnModifier, btnSupprimer;
    private ArrayAdapter<Employe> employeAdapter;
    private Employe employeSelectionne = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gestion_employes);

        ListView listViewEmployes = findViewById(R.id.listViewEmployes);
        editTextSearch = findViewById(R.id.editTextSearch);
        btnAjouter = findViewById(R.id.btn_ajouter);
        btnModifier = findViewById(R.id.btn_edit);
        btnSupprimer = findViewById(R.id.btn_delete);
        ImageView imgHome = findViewById(R.id.img_home);

        btnModifier.setEnabled(false);
        btnSupprimer.setEnabled(false);

        initialiserDonnees();
        configurerListView();

        imgHome.setOnClickListener(v -> {
            Intent intent = new Intent(GestionEmployesActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filtrerEmployes(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        btnAjouter.setOnClickListener(v -> {
            Intent intent = new Intent(GestionEmployesActivity.this, FormulaireEmployeActivity.class);
            startActivityForResult(intent, 100);
        });

        btnModifier.setOnClickListener(v -> {
            if (employeSelectionne != null) {
                Intent intent = new Intent(GestionEmployesActivity.this, FormulaireEmployeActivity.class);
                intent.putExtra("mode", "EDIT");
                intent.putExtra("employe_id", employeSelectionne.getId());
                startActivityForResult(intent, 200);
            }
        });

        btnSupprimer.setOnClickListener(v -> {
            if (employeSelectionne != null) {
                new AlertDialog.Builder(this)
                        .setTitle("Confirmer la suppression")
                        .setMessage("Voulez-vous vraiment supprimer " + employeSelectionne.getNom() + " ?")
                        .setPositiveButton("Oui", (dialog, which) -> {
                            listeEmployes.remove(employeSelectionne);
                            listeEmployesOriginale.remove(employeSelectionne);
                            employeAdapter.notifyDataSetChanged();
                            reinitialiserSelection();
                            Toast.makeText(GestionEmployesActivity.this, "Employé supprimé", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("Non", null)
                        .show();
            }
        });
    }

    private void initialiserDonnees() {
        listeEmployesOriginale.clear();
        listeEmployesOriginale.add(new Employe(1, "Zahraoui", "Yasmine", "EMP001", "RH", "Responsable RH", "Actif"));
        listeEmployesOriginale.add(new Employe(2, "Ansari", "Imane", "EMP002", "RH", "Recruteur", "Actif"));
        listeEmployesOriginale.add(new Employe(3, "Nafil", "Asmae", "EMP003", "RH", "Recruteur", "En congé"));
        listeEmployesOriginale.add(new Employe(4, "El Fassi", "Laila", "EMP004", "RH", "Gestionnaire de paie", "Actif"));
        listeEmployesOriginale.add(new Employe(5, "Jamal Eddine", "Rihab", "EMP005", "Informatique", "Développeur Android", "Actif"));
        listeEmployesOriginale.add(new Employe(6, "Benali", "Karim", "EMP006", "Informatique", "Développeur Android", "Actif"));
        listeEmployesOriginale.add(new Employe(7, "Amine", "Chaimae", "EMP007", "Informatique", "Développeur Backend", "Actif"));
        listeEmployesOriginale.add(new Employe(8, "Tazi", "Salma", "EMP008", "Informatique", "Chef de projet", "Actif"));
        listeEmployesOriginale.add(new Employe(9, "El Mansouri", "Omar", "EMP009", "Finance", "Comptable", "Actif"));
        listeEmployesOriginale.add(new Employe(10, "Hamidi", "Khaoula", "EMP010", "Finance", "Comptable", "Actif"));
        listeEmployesOriginale.add(new Employe(11, "Bouhaddou", "Youssef", "EMP011", "Finance", "Analyste financier", "Licencié"));
        listeEmployesOriginale.add(new Employe(12, "Bellaoui", "Nadia", "EMP012", "Marketing", "Responsable Marketing", "Actif"));
        listeEmployesOriginale.add(new Employe(13, "Ait Benali", "Sofia", "EMP013", "Marketing", "Chargée de communication", "Actif"));
        listeEmployesOriginale.add(new Employe(14, "Rahmouni", "Younes", "EMP014", "Marketing", "Chargé de communication", "Actif"));
        listeEmployesOriginale.add(new Employe(15, "Karimi", "Karim", "EMP015", "Production", "Chef d'atelier", "Actif"));
        listeEmployesOriginale.add(new Employe(16, "Boukili", "Fatima", "EMP016", "Production", "Opératrice", "En congé"));
        listeEmployesOriginale.add(new Employe(17, "Zerouali", "Ahmed", "EMP017", "Production", "Opérateur", "Actif"));
        listeEmployesOriginale.add(new Employe(18, "Lachhab", "Mehdi", "EMP018", "Logistique", "Responsable logistique", "Actif"));
        listeEmployesOriginale.add(new Employe(19, "El Ouafi", "Samira", "EMP019", "Logistique", "Préparatrice de commandes", "Démissionnaire"));
        listeEmployesOriginale.add(new Employe(20, "Dahbi", "Reda", "EMP020", "Logistique", "Préparateur de commandes", "Actif"));

        listeEmployes.clear();
        listeEmployes.addAll(listeEmployesOriginale);
    }

    private void configurerListView() {
        employeAdapter = new ArrayAdapter<Employe>(this, R.layout.item_employe_simple, R.id.txtNomComplet, listeEmployes) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View row = super.getView(position, convertView, parent);
                Employe e = getItem(position);

                TextView txtNomComplet = row.findViewById(R.id.txtNomComplet);
                TextView txtInfos = row.findViewById(R.id.txtInfos);

                txtNomComplet.setText(e.getPrenom() + " " + e.getNom());
                txtInfos.setText(e.getMatricule() + " • " + e.getGrade() + " • " + e.getDepartement() + " • " + e.getStatut());


                return row;
            }
        };

        ListView listView = findViewById(R.id.listViewEmployes);
        listView.setAdapter(employeAdapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            employeSelectionne = listeEmployes.get(position);
            btnModifier.setEnabled(true);
            btnSupprimer.setEnabled(true);
        });
    }

    private void filtrerEmployes(String query) {
        String recherche = query.toLowerCase().trim();
        listeEmployes.clear();

        if (recherche.isEmpty()) {
            listeEmployes.addAll(listeEmployesOriginale);
        } else {
            for (Employe e : listeEmployesOriginale) {
                if (e.getNomComplet().contains(recherche) ||
                        e.getMatricule().toLowerCase().contains(recherche)) {
                    listeEmployes.add(e);
                }
            }
        }

        employeAdapter.notifyDataSetChanged();

        if (employeSelectionne != null && !listeEmployes.contains(employeSelectionne)) {
            reinitialiserSelection();
        }
    }

    private void reinitialiserSelection() {
        employeSelectionne = null;
        btnModifier.setEnabled(false);
        btnSupprimer.setEnabled(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == 100) {
                Employe nouvelEmploye = (Employe) data.getSerializableExtra("nouvel_employe");
                if (nouvelEmploye != null) {
                    int nouveauId = listeEmployesOriginale.isEmpty() ? 1 : listeEmployesOriginale.get(listeEmployesOriginale.size() - 1).getId() + 1;
                    nouvelEmploye.setId(nouveauId);
                    listeEmployesOriginale.add(nouvelEmploye);
                    listeEmployes.add(nouvelEmploye);
                    employeAdapter.notifyDataSetChanged();
                    Toast.makeText(this, "Employé ajouté avec succès", Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == 200) {
                Employe employeModifie = (Employe) data.getSerializableExtra("employe_modifie");
                if (employeModifie != null) {
                    for (int i = 0; i < listeEmployesOriginale.size(); i++) {
                        if (listeEmployesOriginale.get(i).getId() == employeModifie.getId()) {
                            listeEmployesOriginale.set(i, employeModifie);
                            break;
                        }
                    }
                    for (int i = 0; i < listeEmployes.size(); i++) {
                        if (listeEmployes.get(i).getId() == employeModifie.getId()) {
                            listeEmployes.set(i, employeModifie);
                            break;
                        }
                    }
                    employeAdapter.notifyDataSetChanged();
                    reinitialiserSelection();
                    Toast.makeText(this, "Employé mis à jour avec succès", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}