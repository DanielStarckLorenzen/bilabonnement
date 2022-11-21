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

    public String getFrameName() {
        return frameNumber;
    }

    public void setFrameName(String frameName) {
        this.frameNumber = frameName;
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

    public double getCo2Discharge() {
        return CO2Discharge;
    }

    public void setCo2Discharge(double co2Discharge) {
        this.CO2Discharge = co2Discharge;
    }

    public boolean isOnStock() {
        return isOnStock;
    }

    public void setOnStock(boolean onStock) {
        isOnStock = onStock;
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
