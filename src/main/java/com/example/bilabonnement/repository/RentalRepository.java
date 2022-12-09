package com.example.bilabonnement.repository;

import com.example.bilabonnement.model.Car;
import com.example.bilabonnement.model.RentalAgreements;
import com.example.bilabonnement.model.enums.Status;
import org.apache.catalina.webresources.CachedResource;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RentalRepository {

    private String onStock = "OnStock";
    private String rented = "Rented";
    private String damaged = "Damaged";

    private Connection conn = DatabaseManager.getConnection();
    private PreparedStatement pst = null;

    public void updateIsOverTraveled(int kilometersOverdriven, int rentalId) {
        try {
            pst = conn.prepareStatement("update rentalagreements set kilometersOverdriven = ? where rentalId = ?");
            pst.setInt(1, kilometersOverdriven);
            pst.setInt(2, rentalId);

            pst.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void createRentalAgreement (Car car, int monthsRented, int kilometersPerMonth, String customerName, LocalDate startLocalDate, LocalDate endLocalDate) {
        CarRepository carRepository = new CarRepository();

        String frameNumber = car.getFrameNumber();
        int vehicleNumber = car.getVehicleNumber();
        Date startDate = Date.valueOf(startLocalDate);
        Date endDate = Date.valueOf(endLocalDate);

        try {
            pst = conn.prepareStatement("insert into rentalagreements (monthsRented, kilometerPerMonth, frameNumber, vehicleNumber, customerName, startDate, endDate) values(?, ?, ?, ?, ?, ?, ?) ");
            pst.setInt(1,monthsRented);
            pst.setInt(2, kilometersPerMonth);
            pst.setString(3,frameNumber);
            pst.setInt(4, vehicleNumber);
            pst.setString(5, customerName);
            pst.setDate(6, startDate);
            pst.setDate(7, endDate);
            pst.executeUpdate();

        }catch (SQLException e){
            System.out.println(e);
        }
        carRepository.changeStatus(Status.RENTED, vehicleNumber);
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
                        resultSet.getInt("kilometersOverdriven"),
                        resultSet.getString("customerName"),
                        resultSet.getInt("vehicleNumber"),
                        resultSet.getString("frameNumber"),
                        resultSet.getDate("startDate"),
                        resultSet.getDate("endDate")
                );

            }
        }catch (SQLException e){
            System.out.println(e);
        }
        return rentalAgreement;
    }

    public List<RentalAgreements> getAllRentalAgreements() {
        List<RentalAgreements> rentalAgreements = new ArrayList<>();
        try {
            pst = conn.prepareStatement("select * from rentalagreements");

            ResultSet resultSet = pst.executeQuery();

            while (resultSet.next()) {
                rentalAgreements.add(new RentalAgreements(
                        resultSet.getInt("rentalId"),
                        resultSet.getInt("monthsRented"),
                        resultSet.getInt("kilometerPerMonth"),
                        resultSet.getInt("kilometersOverDriven"),
                        resultSet.getString("frameNumber"),
                        resultSet.getInt("vehicleNumber"),
                        resultSet.getDouble("overdrivenCost"),
                        resultSet.getString("customerName"),
                        resultSet.getDate("startDate"),
                        resultSet.getDate("endDate")
                ));
            }

        } catch(SQLException e){
            System.out.println(e);
        }
        return rentalAgreements;
    }

    public int getMaxRentalId(int vehicleNumber) {
        int rentalId = 0;
        try {
            pst = conn.prepareStatement("select max(rentalId) from rentalagreements where vehicleNumber = ?");
            pst.setInt(1, vehicleNumber);

            ResultSet resultSet = pst.executeQuery();

            if (resultSet.next())
                rentalId = resultSet.getInt(1);
        } catch (SQLException e){
            System.out.println(e);
        }
        return rentalId;
    }

    public void updateOverdrivenCost(double overdrivenCost, int rentalId) {
        try {
            pst = conn.prepareStatement("update rentalagreements set overdrivenCost = ? where rentalId = ?");
            pst.setDouble(1, overdrivenCost);
            pst.setDouble(2, rentalId);

            pst.executeUpdate();
        } catch(SQLException e){
            System.out.println(e);
        }
    }

    public Date getEndDate(int rentalId) {
        Date endDate = null;
        try {
            pst = conn.prepareStatement("select endDate from rentalagreements where rentalId = ?");
            pst.setInt(1, rentalId);

            ResultSet resultset = pst.executeQuery();

            if (resultset.next())
                endDate = resultset.getDate(1);

        } catch (SQLException e){
            System.out.println(e);
        }
        return endDate;
    }
}
