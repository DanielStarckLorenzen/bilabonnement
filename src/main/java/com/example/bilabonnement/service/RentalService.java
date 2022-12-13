package com.example.bilabonnement.service;

import com.example.bilabonnement.model.Car;
import com.example.bilabonnement.model.RentalAgreements;
import com.example.bilabonnement.model.enums.Status;
import com.example.bilabonnement.repository.CarRepository;
import com.example.bilabonnement.repository.RentalRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RentalService {

    private final double OVERDRIVEN_COST = 0.75;

    private CarRepository carRepository = new CarRepository();
    private RentalRepository rentalRepository = new RentalRepository();

    public void calculateOverdrivenCost(int kilometersOverdriven, int maxRentalId) {
        double totalOverdrivenCost = kilometersOverdriven * OVERDRIVEN_COST;

        rentalRepository.updateOverdrivenCost(totalOverdrivenCost, maxRentalId);
    }
    public LocalDate endDate(LocalDate startDate, int monthsRented) {

        return startDate.plusMonths(monthsRented);
    }

    public int daysLeftToReturn(Date endDate) {
        LocalDate now = LocalDate.now();
        Date date = Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());

        long diffDays = (endDate.getTime() - date.getTime()) / (1000 * 60 * 60 * 24);

        return (int) diffDays;
    }
    public void calculateOverDrivenKm(int vehicleNumber, int kilometersDriven) {
        List<RentalAgreements> allRentalAgreements = rentalRepository.getAllRentalAgreements();
        int maxRentalId = rentalRepository.getMaxRentalId(vehicleNumber);
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

                rentalRepository.updateIsOverTraveled(kilometersOverDriven, maxRentalId);
            }
        }
        calculateOverdrivenCost(kilometersOverDriven, maxRentalId);

    }

    public List<RentalAgreements> getActiveRentalAgreements() {
        List<RentalAgreements> activeRentals = new ArrayList<>();
        for (Car car : carRepository.getAllCarsStatus(Status.RENTED)) {
            int rentalId = rentalRepository.getMaxRentalId(car.getVehicleNumber());
            for (RentalAgreements agreement : rentalRepository.getAllRentalAgreements()) {
                if (rentalId == agreement.getRentalId()) {
                    activeRentals.add(agreement);
                }
            }
        }
        return activeRentals;
    }

    public void checkIfExpired() {
        //Instantiere datoen i dag som en LocalDate
        LocalDate today = LocalDate.now();
        Date now = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());

        //Tjekker igennem alle aktive lejeaftaler
        for (RentalAgreements agreements : getActiveRentalAgreements()) {
            int vehicleNumber = 0;

            //Tjekker alle biler der er udlejet så vi kan forbinde den rigtige aktive lejekontrakt til den rigtige bil
            for (Car car : carRepository.getAllCarsStatus(Status.RENTED)) {
                if (car.getFrameNumber().equals(agreements.getFrameNumber())) {
                    vehicleNumber = car.getVehicleNumber();
                }

                //Tjekker at lejeaftalens slutdato eksisterer og om den er udløbet ud fra dagens dato
                if (agreements.getEndDate() != null) {
                    if (agreements.getEndDate().after(now)) {
                        carRepository.changeStatus(Status.EXPIRED, vehicleNumber);
                    }
                }
            }
        }
    }
}
