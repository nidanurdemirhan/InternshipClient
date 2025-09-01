package com.example.internshipproject.Models;

public class ContractModel {
    private ClientModel contract;
    private ClientModel stock;
    private int dayOfMonth;

    public ContractModel(ClientModel contract, ClientModel stock, int dayOfMonth) {
        this.contract = contract;
        this.stock = stock;
        this.dayOfMonth = dayOfMonth;
    }

    public ClientModel getContract() {
        return contract;
    }

    public void setContract(ClientModel contract) {
        this.contract = contract;
    }

    public ClientModel getStock() {
        return stock;
    }

    public void setStock(ClientModel stock) {
        this.stock = stock;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public String toString() {
        return  "Contract "+contract.getSupplierInfo()+" Stock"+stock.getSupplierInfo()+" "+dayOfMonth;
    }
}

