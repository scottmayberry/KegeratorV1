package com.example.sctma.kegeratorv1;

/**
 * Created by SMAYBER8 on 7/12/2017.
 */

public class KegInfo {
    private String name;
    private String description;
    private String kegSize;
    private double totalPrice;
    private double spent;
    private double fee;
    private double savings;
    private String purchaser;

    public KegInfo()
    {}

    public KegInfo(String name, String description, String kegSize, double totalPrice, double spent, double fee, double savings, String purchaser) {
        this.name = name;
        this.description = description;
        this.kegSize = kegSize;
        this.totalPrice = totalPrice;
        this.spent = spent;
        this.fee = fee;
        this.savings = savings;
        this.purchaser = purchaser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKegSize() {
        return kegSize;
    }

    public void setKegSize(String kegSize) {
        this.kegSize = kegSize;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getSpent() {
        return spent;
    }

    public void setSpent(double spent) {
        this.spent = spent;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public double getSavings() {
        return savings;
    }

    public void setSavings(double savings) {
        this.savings = savings;
    }

    public String getPurchaser() {
        return purchaser;
    }

    public void setPurchaser(String purchaser) {
        this.purchaser = purchaser;
    }
}
