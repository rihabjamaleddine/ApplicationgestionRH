package com.example.applicationgestionrh;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class EmployeAdapter extends RecyclerView.Adapter<EmployeAdapter.ViewHolder> {

    private List<Employe> employes;
    private OnEmployeClickListener listener;

    public interface OnEmployeClickListener {
        void onEmployeClick(Employe employe);
        void onEditClick(Employe employe);
        void onDeleteClick(Employe employe);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_employe, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Employe e = employes.get(position);

        holder.txtNom.setText(e.getPrenom() + " " + e.getNom());
        holder.txtMatricule.setText("Matricule: " + e.getMatricule());
        holder.txtDepartement.setText("Département: " + e.getDepartement());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEmployeClick(e);
            }
        });

        holder.btnEdit.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEditClick(e);
            }
        });

        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteClick(e);
            }
        });
    }

    @Override
    public int getItemCount() {
        return employes.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNom, txtMatricule, txtDepartement;
        ImageButton btnEdit, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNom = itemView.findViewById(R.id.txt_nom);
            txtMatricule = itemView.findViewById(R.id.txt_matricule);
            txtDepartement = itemView.findViewById(R.id.txt_departement);

        }
    }
}