package com.example.internshipproject.halpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import androidx.annotation.Nullable;
import com.example.internshipproject.Models.ClientModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;


public class DataBaseHalper extends SQLiteOpenHelper {

    public DataBaseHalper(@Nullable Context context) {
        super(context, "suppliers.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tableCreateQuery = "CREATE TABLE IF NOT EXISTS Suppliers (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "supplierInfo TEXT, " +
                "supplierType TEXT, " +
                "lastReservedDays TEXT)";
        db.execSQL(tableCreateQuery);

        String tableCreateQuery2 = "CREATE TABLE IF NOT EXISTS SupplierAssignments (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "contractSupplier TEXT, " +
                "stockSupplier TEXT, " +
                "dayOfMonth TEXT," +
                "month TEXT)";
        db.execSQL(tableCreateQuery2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // bu method ne durumda çalışıcak bilmiyorum
    }

    public boolean addOneClient(ClientModel clientModel){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("supplierInfo",clientModel.getSupplierInfo());
        cv.put("supplierType",clientModel.getSupplierType());
        cv.put("lastReservedDays",clientModel.getLastReservedDays());

        long insert = db.insert("Suppliers",null,cv);
        return insert > 0;
    }
    public boolean addAssignments(int month, File file) throws IOException, JSONException {
        String content = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            content = new String(Files.readAllBytes(file.toPath()));
        }

        JSONObject jsonObject = new JSONObject(content);
        JSONArray clientsArray = jsonObject.getJSONArray("Assignments");
        SQLiteDatabase db = this.getWritableDatabase();


        long insertRows = 0;
        for(int i= 0 ; i <clientsArray.length(); i++){
            JSONObject client = clientsArray.getJSONObject(i);
            ContentValues values = new ContentValues();
            values.put("contractSupplier", client.getString("contractSupplier"));
            values.put("stockSupplier", client.getString("stockSupplier"));
            values.put("dayOfMonth", client.getString("dayOfMonth"));
            values.put("dayOfMonth", Integer.toString(month));

            insertRows = db.insert("SupplierAssignments",null ,values );
        }


        db.close();
        return insertRows>0;

    }


    public boolean deleteClient (String info){
        SQLiteDatabase db = this.getWritableDatabase();
        String info_short = info.split(" ")[0]+" "+info.split(" ")[1];
        int deletedRows = db.delete("Suppliers", "supplierInfo = ?", new String[]{info_short});
        db.close();
        return deletedRows>0;
    }

    public boolean deleteAllClients() {
        SQLiteDatabase db = this.getWritableDatabase();
        int deletedRows = db.delete("Suppliers", null, null); // null => tüm satırlar silinir
        db.delete("SupplierAssignments",null,null);
        db.close();
        return deletedRows > 0;
    }

    public boolean changeClientStatus(String info, String status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("supplierType", status);
        String info_short = info.split(" ")[0]+" "+info.split(" ")[1];
        int updatedRows = db.update("Suppliers", values, "supplierInfo = ?", new String[]{info_short});
        db.close();
        return updatedRows>0;
    }
    public boolean changeReservedDays() throws IOException, JSONException {

        File file = new File("/data/user/0/com.example.internshipproject/files/suppliers.json");
        String content = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            content = new String(Files.readAllBytes(file.toPath()));
        }
        //direkt kayıtlı olanı alıyorum yeni reserved days almıyorum(bu kısmı aslında serverda contract doldururken yapabilirim)

        JSONObject jsonObject = new JSONObject(content);
        JSONArray clientsArray = jsonObject.getJSONArray("clients");
        SQLiteDatabase db = this.getWritableDatabase();

        int updatedRows = 0;
        for(int i= 0 ; i <clientsArray.length(); i++){
            JSONObject client = clientsArray.getJSONObject(i);
            ContentValues values = new ContentValues();
            values.put("lastReservedDays", client.getString("lastReservedDays"));
            db.update("Suppliers", values, "supplierInfo = ?", new String[]{client.getString("supplierInfo")});
        }


        db.close();
        return updatedRows>0;
    }
    public ArrayList<ClientModel> listClients(String input,SQLiteDatabase db) {
        ArrayList<ClientModel> clients = new ArrayList<>();
        ArrayList<String> addedClients = new ArrayList<>();
        Cursor cursor;

        if (input.trim().isEmpty()) {

            cursor = db.rawQuery("SELECT * FROM suppliers", null);

            if (cursor.moveToFirst()) {
                do {
                    String info = cursor.getString(cursor.getColumnIndexOrThrow("supplierInfo"));
                    String type = cursor.getString(cursor.getColumnIndexOrThrow("supplierType"));
                    String resDays = cursor.getString(cursor.getColumnIndexOrThrow("lastReservedDays"));
                    clients.add(new ClientModel(info, type,resDays));
                } while (cursor.moveToNext());

            }
            cursor.close();

        }else{
            String[] words = input.trim().split(" ");

            for(String word: words){
                cursor = db.rawQuery(
                        "SELECT * FROM suppliers WHERE supplierInfo LIKE ?",
                        new String[]{"%" + word + "%"}
                );
                if (cursor.moveToFirst()) {
                    do {
                        String info = cursor.getString(cursor.getColumnIndexOrThrow("supplierInfo"));
                        if(!(addedClients.contains(info))){
                            String type = cursor.getString(cursor.getColumnIndexOrThrow("supplierType"));
                            String resDays = cursor.getString(cursor.getColumnIndexOrThrow("lastReservedDays"));
                            clients.add(new ClientModel(info, type,resDays));
                            addedClients.add(info);
                        }
                    } while (cursor.moveToNext());

                }

                cursor.close();
            }
        }
        return clients;
    }








}
