package com.example.internshipproject.activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.internshipproject.adapters.ClientAdapter;
import com.example.internshipproject.Models.ClientModel;
import com.example.internshipproject.R;
import com.example.internshipproject.halpers.DataBaseHalper;

import java.util.ArrayList;
import java.util.List;

public class EditActivity extends AppCompatActivity {
    private ClientAdapter clientAdapter;
    private RecyclerView recyclerView;
    List<ClientModel> clientList = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Button search_btn = findViewById(R.id.search);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchClients();
            }
        });


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        clientAdapter = new ClientAdapter(EditActivity.this,clientList);
        recyclerView.setAdapter(clientAdapter);

    }


    public void searchClients(){
        clientList.clear();
        SQLiteDatabase db = openOrCreateDatabase("suppliers.db", MODE_PRIVATE, null);
        EditText searchText = findViewById(R.id.searchInput);
        DataBaseHalper dbHalper = new DataBaseHalper(recyclerView.getContext());
        clientList.addAll(dbHalper.listClients(searchText.getText().toString(),db));
        db.close();
        clientAdapter.notifyDataSetChanged();

    }

}
