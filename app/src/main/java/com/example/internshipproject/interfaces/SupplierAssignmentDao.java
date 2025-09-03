package com.example.internshipproject.interfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.internshipproject.entities.SupplierAssignments;
import java.util.List;

@Dao
public interface SupplierAssignmentDao {
    @Insert
    void insert(SupplierAssignments assignment);
    @Query("SELECT * FROM SupplierAssignments ORDER BY id DESC LIMIT 1")
    SupplierAssignments getLastAssignment();

}

