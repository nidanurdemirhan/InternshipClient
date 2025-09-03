package com.example.internshipproject.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.internshipproject.AppDatabase;
import com.example.internshipproject.entities.Supplier;
import com.example.internshipproject.R;
import com.example.internshipproject.activities.EditClientActivity;
import com.example.internshipproject.halpers.JsonHelper;
import com.example.internshipproject.interfaces.SupplierDao;
import java.util.List;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ClientViewHolder>{
    List<Supplier> clients;
    AppDatabase db;
    public ClientAdapter(List<Supplier> clients,AppDatabase db) {
        this.clients = clients;
        this.db = db;
    }

    @NonNull
    @Override
    public ClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_item, parent, false);
        return new ClientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientViewHolder holder, int position) {
        Supplier client = clients.get(position);
        String info = client.supplierInfo;
        String type = client.supplierType;
        holder.clientInfo.setText(info);
        holder.clientType.setText(type);

        holder.pickClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditClientActivity.class);
                intent.putExtra("clientInfo", holder.clientInfo.getText().toString());
                intent.putExtra("clientType",holder.clientType.getText().toString());
                v.getContext().startActivity(intent);
            }
        });
        holder.deleteClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SupplierDao supplierDao = db.supplierDao();
                supplierDao.deleteByInfo(holder.clientInfo.getText().toString());

                JsonHelper jsonHelper = new JsonHelper();
                jsonHelper.deleteClient(v.getContext(),holder.clientInfo.getText().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return clients.size();
    }

    public static class ClientViewHolder extends RecyclerView.ViewHolder {
        TextView clientInfo;
        TextView clientType;
        Button pickClient;
        Button deleteClient;

        public ClientViewHolder(@NonNull View itemView) {
            super(itemView);
            clientInfo = itemView.findViewById(R.id.clientInfo);
            clientType = itemView.findViewById(R.id.clientType);
            pickClient = itemView.findViewById(R.id.pickClientButton);
            deleteClient = itemView.findViewById(R.id.deleteClientButton);

        }


    }



}
