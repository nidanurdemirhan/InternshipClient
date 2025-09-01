package com.example.internshipproject.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.internshipproject.Models.ClientModel;
import com.example.internshipproject.adapters.ContractAdapter;
import com.example.internshipproject.R;
import com.example.internshipproject.halpers.DataBaseHalper;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class SendServerActivity extends AppCompatActivity {
    private ContractAdapter contractAdapter;
    private RecyclerView recyclerView;
    List<ClientModel> allClientList = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_server);

        listClients();

        recyclerView = findViewById(R.id.allClientsView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        contractAdapter = new ContractAdapter(SendServerActivity.this,allClientList);
        recyclerView.setAdapter(contractAdapter);
    }

    public void listClients(){
        allClientList.clear();
        SQLiteDatabase db = openOrCreateDatabase("suppliers.db", MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery("SELECT * FROM suppliers", null);


        if (cursor.moveToFirst()) {
            do {
                String info = cursor.getString(cursor.getColumnIndexOrThrow("supplierInfo"));
                String type = cursor.getString(cursor.getColumnIndexOrThrow("supplierType"));
                String resDays = cursor.getString(cursor.getColumnIndexOrThrow("lastReservedDays"));
                allClientList.add(new ClientModel(info, type,resDays));
            } while (cursor.moveToNext());

        }

        cursor.close();
        db.close();

        Button sendServerButton = findViewById(R.id.sendServerButton);
        sendServerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SendServerActivity.this, PortInformationActivity.class));
            }
        });

    }



}
