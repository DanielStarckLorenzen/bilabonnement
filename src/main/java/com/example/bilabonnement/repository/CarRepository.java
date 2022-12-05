package com.example.bilabonnement.repository;

import com.example.bilabonnement.model.Car;
import com.example.bilabonnement.model.RentalAgreements;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CarRepository {

    private String onStock = "OnStock";
    private String rented = "Rented";
    private String damaged = "Damaged";

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
                        resultSet.getInt("totalKilometersDriven")
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
                        resultSet.getInt("36MonthsPrice"),
                        resultSet.getInt("totalKilometersDriven")
                ));
            }

        } catch (SQLException e) {
            System.out.println("SQL exception");
        }

        return cars;
    }

    public void changeStatus(String status, int vehicleNumber) {
        try {
            pst = conn.prepareStatement("update car set status = ? where vehicleNumber = ?");
            pst.setString(1, status);
            pst.setInt(2, vehicleNumber);

            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

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
        changeStatus(rented, vehicleNumber);
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
        changeStatus(damaged, vehicleNumber);
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
                        resultSet.getString("customerName")
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
