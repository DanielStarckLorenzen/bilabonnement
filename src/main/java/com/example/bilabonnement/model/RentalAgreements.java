package com.example.bilabonnement.model;

import java.time.LocalDate;
import java.util.Date;

public class RentalAgreements {

    private int rentalId;
    private int monthsRented;
    private int kilometerPerMonth;
    private int kilometersOverDriven;
    private String frameNumber;
    private int vehicleNumber;
    private double overdrivenCost;

    private String customerName;
    private Date startDate;
    private Date endDate;

    public RentalAgreements(int rentalId, int monthsRented, int kilometerPerMonth, int kilometersOverDriven, String frameNumber, int vehicleNumber, double overdrivenCost, String customerName, Date startDate, Date endDate) {
        this.rentalId = rentalId;
        this.monthsRented = monthsRented;
        this.kilometerPerMonth = kilometerPerMonth;
        this.kilometersOverDriven = kilometersOverDriven;
        this.frameNumber = frameNumber;
        this.vehicleNumber = vehicleNumber;
        this.overdrivenCost = overdrivenCost;
        this.customerName = customerName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public RentalAgreements(int rentalId, int monthsRented, int kilometerPerMonth, int kilometersOverDriven, String frameNumber, int vehicleNumber, String customerName, Date startDate, Date endDate) {
        this.rentalId = rentalId;
        this.monthsRented = monthsRented;
        this.kilometerPerMonth = kilometerPerMonth;
        this.kilometersOverDriven = kilometersOverDriven;
        this.frameNumber = frameNumber;
        this.vehicleNumber = vehicleNumber;
        this.customerName = customerName;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    public RentalAgreements() {
    }

    public int getRentalId() {
        return rentalId;
    }

    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }

    public int getMonthsRented() {
        return monthsRented;
    }

    public void setMonthsRented(int monthsRented) {
        this.monthsRented = monthsRented;
    }

    public int getKilometerPerMonth() {
        return kilometerPerMonth;
    }

    public void setKilometerPerMonth(int kilometerPerMonth) {
        this.kilometerPerMonth = kilometerPerMonth;
    }

    public String getFrameNumber() {
        return frameNumber;
    }

    public void setFrameNumber(String frameNumber) {
        this.frameNumber = frameNumber;
    }

    public int getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(int vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public int getKilometersOverDriven() {
        return kilometersOverDriven;
    }

    public void setKilometersOverDriven(int kilometersOverDriven) {
        kilometersOverDriven = kilometersOverDriven;
    }

    public double getOverdrivenCost() {
        return overdrivenCost;
    }

    public void setOverdrivenCost(double overdrivenCost) {
        this.overdrivenCost = overdrivenCost;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public String toString() {
        return "RentalAgreements{" +
                "rentalId=" + rentalId +
                ", monthsRented=" + monthsRented +
                ", kilometerPerMonth=" + kilometerPerMonth +
                ", kilometersOverDriven=" + kilometersOverDriven +
                ", frameNumber='" + frameNumber + '\'' +
                ", vehicleNumber=" + vehicleNumber +
                ", overdrivenCost=" + overdrivenCost +
                ", customerName='" + customerName + '\'' +
                '}';
    }
}
