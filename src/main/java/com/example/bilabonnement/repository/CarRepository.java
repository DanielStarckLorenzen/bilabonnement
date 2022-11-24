package com.example.bilabonnement.repository;

import com.example.bilabonnement.model.Car;
import com.example.bilabonnement.model.RentalAgreements;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                        resultSet.getInt("36MonthsPrice")
                ));
            }

        } catch (SQLException e) {
            System.out.println("SQL exception");
        }

        return cars;
    }

    public List<Car> getAllCarsStatus(String status) {
        List<Car> cars = new ArrayList<>();

        try {
            pst = conn.prepareStatement("SELECT * FROM car WHERE status like ?");
            pst.setString(1, status);

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
                        resultSet.getInt("36MonthsPrice")
                ));
            }

        } catch (SQLException e) {
            System.out.println("SQL exception");
        }

        return cars;
    }

    public void createRentalAgreement (Car car, int monthsRented, int kilometersPerMonth) {
        String frameNumber = car.getFrameNumber();
        int vehicleNumber = car.getVehicleNumber();
        try {
            pst = conn.prepareStatement("insert into rentalagreements (monthsRented, kilometerPerMonth, frameNumber, vehicleNumber) values(?, ?, ?, ?) ");
            pst.setInt(1,monthsRented);
            pst.setInt(2, kilometersPerMonth);
            pst.setString(3,frameNumber);
            pst.setInt(4, vehicleNumber);

            pst.executeUpdate();

        }catch (SQLException e){
            System.out.println(e);
        }
        try {
            pst = conn.prepareStatement("update car set status = 'Rented' where vehicleNumber = ?");
            pst.setInt(1,vehicleNumber);

            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);;
        }
    }

    public RentalAgreements getMonthsAndKilometers(int vehicleNumber) {
        RentalAgreements rentalAgreement = new RentalAgreements();
        try {
            pst = conn.prepareStatement("select * from rentalagreements where vehicleNumber = ?");
            pst.setInt(1, vehicleNumber);

            ResultSet resultSet = pst.executeQuery();
            while(resultSet.next()) {
                rentalAgreement = new RentalAgreements(
                        resultSet.getInt("rentalId"),
                        resultSet.getInt("monthsRented"),
                        resultSet.getInt("kilometerPerMonth"),
                        resultSet.getBoolean("isOverTraveled"),
                        resultSet.getString("frameNumber"),
                        resultSet.getInt("vehicleNumber")
                );

            }
        }catch (SQLException e){
            System.out.println(e);
        }
        return rentalAgreement;
    }

    public void createDamageReport(Car car, String problem, double costs) {
        String frameNumber = car.getFrameNumber();
        int vehicleNumber = car.getVehicleNumber();

        try {
            pst = conn.prepareStatement("insert into damages (problem, frameNumber, vehicleNumber, damageCosts) values (?, ?, ?, ?)");
            pst.setString(1, problem);
            pst.setString(2, frameNumber);
            pst.setInt(3, vehicleNumber);
            pst.setDouble(4, costs);

            pst.executeUpdate();
        }catch(SQLException e){
            System.out.println(e);
        }
        try {
            pst = conn.prepareStatement("update car set status = 'Damaged' where vehicleNumber = ?");
            pst.setInt(1, vehicleNumber);

            pst.executeUpdate();
        } catch (SQLException e){
            System.out.println(e);
        }
    }

}
