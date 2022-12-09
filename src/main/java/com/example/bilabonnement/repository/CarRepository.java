package com.example.bilabonnement.repository;

import com.example.bilabonnement.model.Car;
import com.example.bilabonnement.model.RentalAgreements;
import com.example.bilabonnement.model.enums.Status;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;


public class CarRepository {

    private Connection conn = DatabaseManager.getConnection();
    private PreparedStatement pst = null;

    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();

        try {
            pst = conn.prepareStatement("SELECT * FROM car");

            ResultSet resultSet = pst.executeQuery();

            while (resultSet.next()) {
                cars.add(new Car(
                        resultSet.getInt("vehicleNumber"),
                        resultSet.getString("frameNumber"),
                        resultSet.getString("model"),
                        resultSet.getString("manufacturer"),
                        resultSet.getBoolean("isManual"),
                        resultSet.getString("accessories"),
                        resultSet.getDouble("CO2discharge"),
                        resultSet.getString("status"),
                        resultSet.getInt("3MonthsPrice"),
                        resultSet.getInt("6MonthsPrice"),
                        resultSet.getInt("12MonthsPrice"),
                        resultSet.getInt("24MonthsPrice"),
                        resultSet.getInt("36MonthsPrice"),
                        resultSet.getInt("totalKilometersDriven"),
                        resultSet.getString("color")
                ));
            }

        } catch (SQLException e) {
            System.out.println("SQL exception");
        }

        return cars;
    }

    public List<Car> getAllCarsStatus(Status status) {
        List<Car> cars = new ArrayList<>();

        try {
            pst = conn.prepareStatement("SELECT * FROM car WHERE status like ?");
            pst.setString(1, status.toString());

            ResultSet resultSet = pst.executeQuery();

            while (resultSet.next()) {
                cars.add(new Car(
                        resultSet.getInt("vehicleNumber"),
                        resultSet.getString("frameNumber"),
                        resultSet.getString("model"),
                        resultSet.getString("manufacturer"),
                        resultSet.getBoolean("isManual"),
                        resultSet.getString("accessories"),
                        resultSet.getDouble("CO2discharge"),
                        resultSet.getString("status"),
                        resultSet.getInt("3MonthsPrice"),
                        resultSet.getInt("6MonthsPrice"),
                        resultSet.getInt("12MonthsPrice"),
                        resultSet.getInt("24MonthsPrice"),
                        resultSet.getInt("36MonthsPrice"),
                        resultSet.getInt("totalKilometersDriven"),
                        resultSet.getString("color")
                ));
            }

        } catch (SQLException e) {
            System.out.println("SQL exception");
        }

        return cars;
    }

    public void changeStatus(Status status, int vehicleNumber) {
        try {
            pst = conn.prepareStatement("update car set status = ? where vehicleNumber = ?");
            pst.setString(1, status.toString());
            pst.setInt(2, vehicleNumber);

            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }



    public void createCar(Car car) {
        try {
            pst = conn.prepareStatement("insert into car (frameNumber, model, manufacturer, isManual, accessories, CO2discharge, status, `3MonthsPrice`, `6MonthsPrice`, `12MonthsPrice`, `24MonthsPrice`, `36MonthsPrice`, totalKilometersDriven)" +
                    " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            pst.setString(1, car.getFrameNumber());
            pst.setString(2, car.getModel());
            pst.setString(3, car.getManufacturer());
            pst.setBoolean(4, car.isManual());
            pst.setString(5, car.getAccessories());
            pst.setDouble(6,car.getCO2discharge());
            pst.setString(7, car.getStatus());
            pst.setInt(8, car.getMonthsPrice3());
            pst.setInt(9, car.getMonthsPrice6());
            pst.setInt(10, car.getMonthsPrice12());
            pst.setInt(11, car.getMonthsPrice24());
            pst.setInt(12, car.getMonthsPrice36());
            pst.setInt(13, car.getTotalKilometersDriven());

            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void removeCar(String frameNumber) {
        try {
            pst = conn.prepareStatement("delete from car where frameNumber = ?");
            pst.setString(1, frameNumber);

            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void updateKilometersDriven(int vehicleNumber, int totalKilometersDriven) {
        try{
            pst = conn.prepareStatement("update car set totalKilometersDriven = ? where vehicleNumber = ?");
            pst.setInt(1, totalKilometersDriven);
            pst.setInt(2, vehicleNumber);
            pst.executeUpdate();
        } catch (SQLException e){
            System.out.println(e);
        }
    }

}
