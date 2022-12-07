package com.example.bilabonnement.service;

import com.example.bilabonnement.model.Car;
import com.example.bilabonnement.model.RentalAgreements;
import com.example.bilabonnement.repository.CarRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class RentalService {

    private String onStock = "OnStock";
    private String rented = "Rented";
    private String damaged = "Damaged";

    private final double OVERDRIVEN_COST = 0.75;

    private CarRepository carRepository = new CarRepository();

    public void calculateOverdrivenCost(int kilometersOverdriven, int maxRentalId) {
        double totalOverdrivenCost = kilometersOverdriven * OVERDRIVEN_COST;

        carRepository.updateOverdrivenCost(totalOverdrivenCost, maxRentalId);
    }
    public LocalDate endDate(LocalDate startDate, int monthsRented) {

        LocalDate endDate = startDate.plusMonths(monthsRented);
        return endDate;
    }

    public int daysLeftToReturn(Date endDate) {
        LocalDate now = LocalDate.now();
        Date date = Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());

        long diffDays = (endDate.getTime() - date.getTime()) / (1000 * 60 * 60 * 24);

        return (int) diffDays;


    }
    public void calculateOverDrivenKm(int vehicleNumber, int kilometersDriven) {
        List<RentalAgreements> allRentalAgreements = carRepository.getAllRentalAgreements();
        int maxRentalId = carRepository.getMaxRentalId(vehicleNumber);
        int kilometersDrivenCustomer = 0;
        int kilometersOverDriven = 0;
        for (Car car : carRepository.getAllCars()) {
            if (car.getVehicleNumber() == vehicleNumber) {
                kilometersDrivenCustomer = kilometersDriven - car.getTotalKilometersDriven();
            }
        }

        for (RentalAgreements rentalAgreements : allRentalAgreements) {
            if (rentalAgreements.getRentalId() == maxRentalId) {
                kilometersOverDriven = kilometersDrivenCustomer - (rentalAgreements.getMonthsRented() * rentalAgreements.getKilometerPerMonth());
                if (kilometersOverDriven < 0)
                    kilometersOverDriven = 0;

                carRepository.updateIsOverTraveled(kilometersOverDriven, maxRentalId);
            }
        }
        calculateOverdrivenCost(kilometersOverDriven, maxRentalId);

    }
}
