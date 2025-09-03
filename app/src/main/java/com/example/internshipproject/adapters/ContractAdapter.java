package com.example.internshipproject.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.internshipproject.R;
import com.example.internshipproject.activities.EditReservedDaysActivity;
import com.example.internshipproject.entities.Supplier;

import java.util.List;

public class ContractAdapter extends RecyclerView.Adapter<ContractAdapter.ContractViewHolder>{
    List<Supplier> allClients;
    public ContractAdapter( List<Supplier> clients) {
        this.allClients = clients;
    }

    @NonNull
    @Override
    public ContractViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_selection, parent, false);
        return new ContractViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContractViewHolder holder, int position) {
        Supplier client = allClients.get(position);
        String info = client.supplierInfo;
        String type = client.supplierType;
        holder.clientInfo.setText(info);
        holder.clientType.setText(type);

        holder.selectClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditReservedDaysActivity.class);
                intent.putExtra("clientInfo", holder.clientInfo.getText().toString());
                intent.putExtra("clientType", holder.clientType.getText().toString());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return allClients.size();
    }

    public static class ContractViewHolder extends RecyclerView.ViewHolder {
        Button selectClient;
        TextView clientInfo;
        TextView clientType;
        public ContractViewHolder(@NonNull View itemView) {
            super(itemView);
            selectClient = itemView.findViewById(R.id.pickClientButton);
            clientInfo = itemView.findViewById(R.id.clientInfo);
            clientType = itemView.findViewById(R.id.clientType);

        }

    }



}
