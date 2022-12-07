package com.example.bilabonnement.repository;

import com.example.bilabonnement.model.Car;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DamageRepository {

    private String onStock = "OnStock";
    private String rented = "Rented";
    private String damaged = "Damaged";

    private Connection conn = DatabaseManager.getConnection();
    private PreparedStatement pst = null;

    public void createDamageReport(Car car, String problem, double costs) {
        CarRepository carRepository = new CarRepository();
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
        carRepository.changeStatus(damaged, vehicleNumber);
    }
}
