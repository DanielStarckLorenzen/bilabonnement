package com.example.bilabonnement.repository;

import com.example.bilabonnement.model.Car;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
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
                        resultSet.getBoolean("isOnStock")
                ));
            }

        } catch (Exception e) {
            System.out.println("SQL exception");
        }

        return cars;
    }

}
