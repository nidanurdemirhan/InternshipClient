package com.example.internshipproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


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

    }

    public boolean addOneClient(ClientModel clientModel){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("supplierInfo",clientModel.getSupplierInfo());
        cv.put("supplierType",clientModel.getSupplierType());
        cv.put("lastReservedDays",clientModel.getLastReservedDays());

        long insert = db.insert("Suppliers",null,cv);
        if(insert>0){
            return true;
        }
        return false;
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
    public boolean changeReservedDays(String info,String days){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("lastReservedDays", days);
        //String info_short = info.split(" ")[0]+" "+info.split(" ")[1];
        int updatedRows = db.update("Suppliers", values, "supplierInfo = ?", new String[]{info});
        db.close();
        return updatedRows>0;
    }
}
