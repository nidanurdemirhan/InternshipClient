package com.example.internshipproject.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.internshipproject.AppDatabase;
import com.example.internshipproject.DatabaseClient;
import com.example.internshipproject.R;
import com.example.internshipproject.entities.Supplier;
import com.example.internshipproject.entities.SupplierAssignments;
import com.example.internshipproject.interfaces.SupplierAssignmentDao;
import com.example.internshipproject.interfaces.SupplierDao;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PortInformationActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.server_information);

        Button sendServerButton = findViewById(R.id.connectServer);
        EditText ipInput = findViewById(R.id.textView3);
        EditText portInput = findViewById(R.id.textView4);
        sendServerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(() -> {
                    // "10.0.2.2"
                    //8080
                    try (Socket socket = new Socket(ipInput.getText().toString(), Integer.parseInt(portInput.getText().toString()));
                         DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                         DataInputStream dis = new DataInputStream(socket.getInputStream())) {

                        File file = new File(getFilesDir(), "suppliers.json");

                        byte[] bytes = null;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            bytes = Files.readAllBytes(file.toPath());
                        } else {
                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                            FileInputStream fis = new FileInputStream(file);
                            byte[] buffer = new byte[1024];
                            int read;
                            while ((read = fis.read(buffer)) != -1) {
                                bos.write(buffer, 0, read);
                            }
                            fis.close();
                            bytes = bos.toByteArray();
                        }

                        dos.writeInt(bytes.length);
                        dos.write(bytes);
                        dos.flush();// buna gerek yokmuş

                        int length = dis.readInt();
                        byte[] received = new byte[length];
                        dis.readFully(received);

                        File receivedFile = new File(getFilesDir(), "contracts.json");
                        try (FileOutputStream fos = new FileOutputStream(receivedFile)) {
                            fos.write(received);
                        }

                        reservedDaysUpdate(file);
                        saveAssignments(receivedFile);

                        startActivity(new Intent(PortInformationActivity.this, SendServerActivity.class));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();

            }
        });

    }

    public void reservedDaysUpdate(File file) throws IOException, JSONException {

        String content = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            content = new String(Files.readAllBytes(file.toPath()));
        }

        JSONObject jsonObject = new JSONObject(content);
        JSONArray clientsArray = jsonObject.getJSONArray("clients");

        AppDatabase db = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase();
        SupplierDao supplierDao = db.supplierDao();

        for(int i= 0 ; i <clientsArray.length(); i++){
            JSONObject client = clientsArray.getJSONObject(i);
            supplierDao.updateReservedDays(client.getString("supplierInfo"),client.getString("lastReservedDays"));
        }

    }
    public void saveAssignments(File file) throws IOException, JSONException {
        AppDatabase db = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase();
        SupplierAssignmentDao supplierAssignmentDao = db.supplierAssignmentDao();

        String content = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            content = new String(Files.readAllBytes(file.toPath()));
        }

        JSONObject jsonObject = new JSONObject(content);
        JSONArray assignmentArray = jsonObject.getJSONArray("Assignments");

        for(int i= 0 ; i <assignmentArray.length(); i++){
            JSONObject assignment = assignmentArray.getJSONObject(i);

            SupplierAssignments suppAssignment = new SupplierAssignments(assignment.getString("contractSupplier"),assignment.getString("stockSupplier"),assignment.getString("dayOfMonth"));

            Calendar calendar = Calendar.getInstance();

            int monthZeroBased = calendar.get(Calendar.MONTH);
            suppAssignment.month =Integer.toString(monthZeroBased+1);

            supplierAssignmentDao.insert(suppAssignment);
        }

    }

}
