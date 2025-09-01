package com.example.internshipproject.Models;

public class ClientModel {
    private String supplierInfo;
    private String supplierType;
    private String lastReservedDays;

    public ClientModel(String name, String surname, String type, String days){
        this.supplierInfo = name+" "+surname;
        this.supplierType = type;
        this.lastReservedDays = days;
    }
    public ClientModel(String info, String type, String days){
        this.supplierInfo = info;
        this.supplierType = type;
        this.lastReservedDays = days;
    }
    public String getSupplierInfo() {
        return supplierInfo;
    }

    public void setSupplierInfo(String supplierInfo) {
        this.supplierInfo = supplierInfo;
    }

    public String getSupplierType() {
        return supplierType;
    }

    public void setSupplierType(String supplierType) {
        this.supplierType = supplierType;
    }

    public String getLastReservedDays() {
        return lastReservedDays;
    }

    public void setLastReservedDays(String lastReservedDays) {
        this.lastReservedDays = lastReservedDays;
    }
}
