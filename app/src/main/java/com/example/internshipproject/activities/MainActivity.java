package com.example.internshipproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.internshipproject.AppDatabase;
import com.example.internshipproject.DatabaseClient;
import com.example.internshipproject.R;
import com.example.internshipproject.interfaces.SupplierAssignmentDao;
import com.example.internshipproject.interfaces.SupplierDao;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        AppDatabase db = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase();
        SupplierAssignmentDao supplierDao = db.supplierAssignmentDao();
        //supplierDao.deleteAll();

        Button addClientPage = findViewById(R.id.addClient);
        addClientPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddActivity.class));
            }
        });

        Button editClientPage = findViewById(R.id.editClient);
        editClientPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, EditActivity.class));
            }
        });

        Button sendServerPage = findViewById(R.id.sendServer);
        sendServerPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SendServerActivity.class));
            }
        });
    }


}