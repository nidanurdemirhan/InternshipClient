package com.example.internshipproject.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.internshipproject.ClientModel;
import com.example.internshipproject.DataBaseHalper;
import com.example.internshipproject.R;
import com.example.internshipproject.activities.EditClientActivity;

import java.util.List;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ClientViewHolder>{
    Context context;
    List<ClientModel> clients;
    public ClientAdapter(Context context, List<ClientModel> clients) {
        this.context = context;
        this.clients = clients;
    }

    @NonNull
    @Override
    public ClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.client_item, parent, false);
        return new ClientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientViewHolder holder, int position) {
        ClientModel client = clients.get(position);
        String info = client.getSupplierInfo()+" "+client.getSupplierType()+ " " +client.getLastReservedDays();
        holder.clientInfo.setText(info);

        holder.pickClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditClientActivity.class);
                intent.putExtra("clientInfo", holder.clientInfo.getText().toString());
                v.getContext().startActivity(intent);
            }
        });
        holder.deleteClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseHalper db = new DataBaseHalper(v.getContext());
                boolean aa =db.deleteClient(holder.clientInfo.getText().toString());

                if(aa){
                    Toast.makeText(v.getContext(),"deleted",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return clients.size();
    }

    public static class ClientViewHolder extends RecyclerView.ViewHolder {
        TextView clientInfo;
        Button pickClient;
        Button deleteClient;


        public ClientViewHolder(@NonNull View itemView) {
            super(itemView);
            clientInfo = itemView.findViewById(R.id.clientInfo);
            pickClient = itemView.findViewById(R.id.pickClientButton);
            deleteClient = itemView.findViewById(R.id.deleteClientButton);

        }


    }



}
