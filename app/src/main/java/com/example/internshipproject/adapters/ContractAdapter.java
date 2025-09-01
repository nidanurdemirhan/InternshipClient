package com.example.internshipproject.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.internshipproject.Models.ClientModel;
import com.example.internshipproject.R;
import com.example.internshipproject.activities.EditReservedDaysActivity;

import java.util.List;

public class ContractAdapter extends RecyclerView.Adapter<ContractAdapter.ContractViewHolder>{
    Context context;
    List<ClientModel> allClients;
    public ContractAdapter(Context context, List<ClientModel> clients) {
        this.context = context;
        this.allClients = clients;
    }

    @NonNull
    @Override
    public ContractViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.client_selection, parent, false);
        return new ContractViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContractViewHolder holder, int position) {
        ClientModel client = allClients.get(position);
        //String info = client.getSupplierInfo()+" "+client.getSupplierType()+ " " +client.getLastReservedDays();
        String info = client.getSupplierInfo();
        holder.clientInfo.setText(info);
        holder.selectClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditReservedDaysActivity.class);
                intent.putExtra("clientInfo", holder.clientInfo.getText().toString());
                intent.putExtra("clientType", holder.clientInfo.getText().toString());
                //BELKİ BURDA Bİ DE RESERVED DAYS VERMEN GEREKEBİLİR BİLMİYORUM
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
            //clientType burda assignlanıcak (ui kısmında type yok)

        }

    }



}
