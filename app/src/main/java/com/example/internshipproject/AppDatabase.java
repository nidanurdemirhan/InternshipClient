package com.example.internshipproject;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.internshipproject.entities.Supplier;
import com.example.internshipproject.entities.SupplierAssignments;
import com.example.internshipproject.interfaces.SupplierAssignmentDao;
import com.example.internshipproject.interfaces.SupplierDao;

@Database(
        entities = {Supplier.class, SupplierAssignments.class},
        version = 1
)
public abstract class AppDatabase extends RoomDatabase {
    public abstract SupplierDao supplierDao();
    public abstract SupplierAssignmentDao supplierAssignmentDao();
}

