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
import com.example.internshipproject.ClientModel;
import com.example.internshipproject.R;

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
        Cursor cursor;
        if(searchText.getText().toString().length()==0){
            cursor = db.rawQuery("SELECT * FROM suppliers", null);
        }
        else{
            cursor = db.rawQuery("SELECT * FROM suppliers WHERE supplierInfo = '" + searchText.getText().toString() + "'", null);
        }

        if (cursor.moveToFirst()) {
            do {
                String info = cursor.getString(cursor.getColumnIndexOrThrow("supplierInfo"));
                String type = cursor.getString(cursor.getColumnIndexOrThrow("supplierType"));
                String resDays = cursor.getString(cursor.getColumnIndexOrThrow("lastReservedDays"));
                clientList.add(new ClientModel(info, type,resDays));
            } while (cursor.moveToNext());

        }

        cursor.close();
        db.close();
        /*if (clientList.size()>0){
            String text = Integer.toString(clientList.size());
            Toast.makeText(EditActivity.this,text,Toast.LENGTH_SHORT).show();
        }*/
        clientAdapter.notifyDataSetChanged();

    }

}
