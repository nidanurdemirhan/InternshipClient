package com.example.internshipproject.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.internshipproject.AppDatabase;
import com.example.internshipproject.DatabaseClient;
import com.example.internshipproject.adapters.ClientAdapter;
import com.example.internshipproject.R;
import com.example.internshipproject.entities.Supplier;
import com.example.internshipproject.interfaces.SupplierDao;

import java.util.ArrayList;
import java.util.List;

public class EditActivity extends AppCompatActivity {
    private ClientAdapter clientAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        List<Supplier> clientList = new ArrayList<>();
        RecyclerView recyclerView;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        AppDatabase db = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase();

        Button search_btn = findViewById(R.id.search);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clientList.clear();
                EditText searchText = findViewById(R.id.searchInput);
                SupplierDao supplierDao = db.supplierDao();
                clientList.addAll(listClients(searchText.getText().toString(),supplierDao));
                clientAdapter.notifyDataSetChanged();
            }
        });


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        clientAdapter = new ClientAdapter(clientList, db);
        recyclerView.setAdapter(clientAdapter);

    }

    public List<Supplier> listClients(String input, SupplierDao supplierDao) {
        List<Supplier> clients = new ArrayList<>();
        List<String> addedClients = new ArrayList<>();

        if (input.trim().isEmpty()) {
            clients.addAll(supplierDao.getAll());

        } else {
            String[] words = input.trim().split(" ");

            for (String word : words) {
                List<Supplier> foundSuppliers = supplierDao.searchByWord(word);

                for (Supplier s : foundSuppliers) {
                    if (!addedClients.contains(s.supplierInfo)) {
                        clients.add(s);
                        addedClients.add(s.supplierInfo);
                    }
                }
            }
        }

        return clients;
    }


}
