package com.example.bilabonnement.repository;

import com.example.bilabonnement.model.Car;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarRepository implements ICarRepository {

    private Connection conn = DatabaseManager.getConnection();
    private PreparedStatement pst = null;
    private List<Car> cars = new ArrayList<>();

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

        } catch (Exception e) {
            System.out.println("SQL exception");
        }

        return cars;
    }


    @Override
    public List<Car> getAllRentedCars() {
        try {
            pst = conn.prepareStatement("SELECT * FROM car WHERE status LIKE '%Rented%'");

            ResultSet resultSet = pst.executeQuery();

            while (resultSet.next()) {
                cars.add(new Car(
                        resultSet.getInt("vehicleNumber"),
                        resultSet.getString("frameNumber"),
                        resultSet.getString("model"),
                        resultSet.getString("manufacturer")

                ));
            }

        } catch (Exception e) {
            System.out.println("SQL exception");
        }

        return cars;
    }
}
