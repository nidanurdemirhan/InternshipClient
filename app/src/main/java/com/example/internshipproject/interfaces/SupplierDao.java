package com.example.internshipproject.interfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.internshipproject.entities.Supplier;

import java.util.List;

@Dao
public interface SupplierDao {
    @Insert
    void insert(Supplier supplier);

    @Query("SELECT * FROM Suppliers")
    List<Supplier> getAll();

    @Query("DELETE FROM Suppliers WHERE supplierInfo = :info")
    void deleteByInfo(String info);
    @Query("UPDATE Suppliers SET supplierType = :type WHERE supplierInfo = :info")
    void updateType(String info, String type);

    @Query("UPDATE Suppliers SET lastReservedDays = :days WHERE supplierInfo = :info")
    void updateReservedDays(String info, String days);

    @Query("SELECT * FROM Suppliers WHERE supplierInfo LIKE '%' || :word || '%'")
    List<Supplier> searchByWord(String word);
}

