package com.example.internshipproject.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.internshipproject.ClientModel;
import com.example.internshipproject.adapters.ContractAdapter;
import com.example.internshipproject.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
                new Thread(() -> {
                    try {
                        URL url = new URL("http://10.0.2.2:8080/upload"); // emülatör -> PC
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                        conn.setDoOutput(true);

                        // JSON gönder
                        String jsonToSend = createJsonContent();
                        try (OutputStream os = conn.getOutputStream()) {
                            os.write(jsonToSend.getBytes());
                            os.flush();
                        }

                        // Response oku
                        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        StringBuilder response = new StringBuilder();
                        String inputLine;
                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        in.close();

                        Log.d("SERVER_RESPONSE", response.toString());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();

            }
        });

    }

    public String createJsonContent(){
        return "BURAYA CONTENT GELICEK " ;
    }
}
