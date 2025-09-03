package com.example.internshipproject.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "SupplierAssignments")
public class SupplierAssignments {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String contractSupplier;
    public String stockSupplier;
    public String dayOfMonth;
    public String month;

    public SupplierAssignments(){}
    @Ignore
    public SupplierAssignments(String contractSupplier,String stockSupplier,String dayOfMonth){
        this.contractSupplier = contractSupplier;
        this.stockSupplier = stockSupplier;
        this.dayOfMonth = dayOfMonth;
    }
}

