package com.example.bilabonnement.model;

public class Car {

    private int vehicleNumber;
    private String frameNumber;
    private String model;
    private String manufacturer;
    private boolean isManual;
    private String accessories;
    private double CO2Discharge;
    private boolean isOnStock;

    public Car(int vehicleNumber, String frameName, String model, String manufacturer, boolean isManual, String accessories, double co2Discharge, boolean isOnStock) {
        this.vehicleNumber = vehicleNumber;
        this.frameNumber = frameName;
        this.model = model;
        this.manufacturer = manufacturer;
        this.isManual = isManual;
        this.accessories = accessories;
        this.CO2Discharge = co2Discharge;
        this.isOnStock = isOnStock;
    }

    public Car() {
    }

    public int getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(int vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getFrameNumber() {
        return frameNumber;
    }

    public void setFrameNumber(String frameNumber) {
        this.frameNumber = frameNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public boolean isManual() {
        return isManual;
    }

    public void setManual(boolean manual) {
        isManual = manual;
    }

    public String getAccessories() {
        return accessories;
    }

    public void setAccessories(String accessories) {
        this.accessories = accessories;
    }

    public double getCO2discharge() {
        return CO2discharge;
    }

    public void setCO2discharge(double CO2discharge) {
        this.CO2discharge = CO2discharge;
    }

    public boolean isOnStock() {
        return isOnStock;
    }

    public void setOnStock(boolean onStock) {
        isOnStock = onStock;
    }

    public int getMonthsPrice3() {
        return monthsPrice3;
    }

    public void setMonthsPrice3(int monthsPrice3) {
        this.monthsPrice3 = monthsPrice3;
    }

    public int getMonthsPrice6() {
        return monthsPrice6;
    }

    public void setMonthsPrice6(int monthsPrice6) {
        this.monthsPrice6 = monthsPrice6;
    }

    public int getMonthsPrice12() {
        return monthsPrice12;
    }

    public void setMonthsPrice12(int monthsPrice12) {
        this.monthsPrice12 = monthsPrice12;
    }

    public int getMonthsPrice24() {
        return monthsPrice24;
    }

    public void setMonthsPrice24(int monthsPrice24) {
        this.monthsPrice24 = monthsPrice24;
    }

    public int getMonthsPrice36() {
        return monthsPrice36;
    }

    public void setMonthsPrice36(int monthsPrice36) {
        this.monthsPrice36 = monthsPrice36;
    }

    @Override
    public String toString() {
        return "Car{" +
                "vehicleNumber=" + vehicleNumber +
                ", frameNumber='" + frameNumber + '\'' +
                ", model='" + model + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", isManual=" + isManual +
                ", accessories='" + accessories + '\'' +
                ", co2Discharge=" + CO2Discharge +
                ", isOnStock=" + isOnStock +
                '}';
    }
}
