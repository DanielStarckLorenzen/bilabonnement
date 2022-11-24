package com.example.bilabonnement.model;

public class RentalAgreements {

    private int rentalId;
    private int monthsRented;
    private int kilometerPerMonth;
    private boolean isOverTraveled;
    private String frameNumber;
    private int vehicleNumber;

    public RentalAgreements(int rentalId, int monthsRented, int kilometerPerMonth, boolean isOverTraveled, String frameNumber, int vehicleNumber) {
        this.rentalId = rentalId;
        this.monthsRented = monthsRented;
        this.kilometerPerMonth = kilometerPerMonth;
        this.isOverTraveled = isOverTraveled;
        this.frameNumber = frameNumber;
        this.vehicleNumber = vehicleNumber;
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

    public boolean isOverTraveled() {
        return isOverTraveled;
    }

    public void setOverTraveled(boolean overTraveled) {
        isOverTraveled = overTraveled;
    }

    @Override
    public String toString() {
        return "RentalAgreements{" +
                "rentalId=" + rentalId +
                ", monthsRented=" + monthsRented +
                ", kilometerPerMonth=" + kilometerPerMonth +
                ", isOverTraveled=" + isOverTraveled +
                ", frameNumber='" + frameNumber + '\'' +
                ", vehicleNumber=" + vehicleNumber +
                '}';
    }
}
