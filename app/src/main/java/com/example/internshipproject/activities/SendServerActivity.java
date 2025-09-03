package com.example.internshipproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.internshipproject.AppDatabase;
import com.example.internshipproject.DatabaseClient;
import com.example.internshipproject.adapters.ContractAdapter;
import com.example.internshipproject.R;
import com.example.internshipproject.entities.Supplier;
import com.example.internshipproject.entities.SupplierAssignments;
import com.example.internshipproject.interfaces.SupplierAssignmentDao;
import com.example.internshipproject.interfaces.SupplierDao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SendServerActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        ContractAdapter contractAdapter;
        RecyclerView recyclerView;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_server);

        recyclerView = findViewById(R.id.allClientsView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        contractAdapter = new ContractAdapter(listClients());
        recyclerView.setAdapter(contractAdapter);
    }

    private List<Supplier> listClients(){

        AppDatabase db = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase();
        SupplierDao supplierDao = db.supplierDao();

        List<Supplier>  allClientList = supplierDao.getAll();

        Button sendServerButton = findViewById(R.id.sendServerButton);
        sendServerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppDatabase db = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase();
                SupplierAssignmentDao supplierAssignmentDao = db.supplierAssignmentDao();
                SupplierDao supplierDao = db.supplierDao();
                SupplierAssignments lastAssignment = supplierAssignmentDao.getLastAssignment();
                Calendar calendar = Calendar.getInstance();

                if(lastAssignment != null && lastAssignment.month.equals(Integer.toString(calendar.get(Calendar.MONTH)+1))){
                    Toast.makeText(SendServerActivity.this, "Assignments already done for this month.", Toast.LENGTH_SHORT).show();

                }else{
                    clearReservedDays(supplierDao);
                    startActivity(new Intent(SendServerActivity.this, PortInformationActivity.class));
                }

            }
        });
        return allClientList;
    }

    public void clearReservedDays(SupplierDao supplierDao){
        List<Supplier> all = supplierDao.getAll();
        for(Supplier supp : all){
            supplierDao.updateReservedDays(supp.supplierInfo,"");
        }
    }


}
