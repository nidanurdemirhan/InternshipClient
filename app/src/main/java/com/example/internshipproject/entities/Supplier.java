package com.example.internshipproject.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Suppliers")
public class Supplier {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String supplierInfo;
    public String supplierType;
    public String lastReservedDays;

    public Supplier() {}
    @Ignore
    public Supplier(String supplierInfo, String supplierType, String lastReservedDays) {
        this.supplierInfo = supplierInfo;
        this.supplierType = supplierType;
        this.lastReservedDays = lastReservedDays;
    }

}

