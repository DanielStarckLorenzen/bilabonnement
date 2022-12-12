package com.example.bilabonnement.service;


import com.example.bilabonnement.model.Car;
import com.example.bilabonnement.model.RentalAgreements;
import com.example.bilabonnement.model.enums.Status;
import com.example.bilabonnement.repository.CarRepository;
import com.example.bilabonnement.repository.RentalRepository;

import java.util.*;

public class CarService {

    private CarRepository carRepository = new CarRepository();
    private RentalRepository rentalRepository = new RentalRepository();

    public Map<String, Integer> kilometersPrice() {
        Map<String,Integer> kmPrice = new HashMap<>();
        kmPrice.put("1.500 km.", 0);
        kmPrice.put("1.750 km.", 300);
        kmPrice.put("2.000 km.", 590);
        kmPrice.put("2.500 km.", 1160);
        kmPrice.put("3.000 km.", 1710);
        kmPrice.put("3.500 km.", 2240);
        kmPrice.put("4.000 km.", 2750);
        kmPrice.put("4.500 km.", 3240);

        return kmPrice;
    }
    
    public int calculateTotalPrice(Car car) {
        int vehicleNumber = car.getVehicleNumber();
        CarRepository carRepository = new CarRepository();
        RentalAgreements rentalCar = rentalRepository.getMonthsAndKilometers(vehicleNumber);

        int monthsRented = rentalCar.getMonthsRented();
        int kilometersPerMonth = rentalCar.getKilometerPerMonth();

        int monthlyPrice = 0;
        switch (monthsRented) {
            case 3 -> monthlyPrice = car.getMonthsPrice3();
            case 6 -> monthlyPrice = car.getMonthsPrice6();
            case 12 -> monthlyPrice = car.getMonthsPrice12();
            case 24 -> monthlyPrice = car.getMonthsPrice24();
            case 36 -> monthlyPrice = car.getMonthsPrice36();
        }

        int kilometerPrice = 0;
        switch (kilometersPerMonth) {
            case 1500 -> kilometerPrice = kilometersPrice().get("1.500 km.");
            case 1750 -> kilometerPrice = kilometersPrice().get("1.750 km.");
            case 2000 -> kilometerPrice = kilometersPrice().get("2.000 km.");
            case 2500 -> kilometerPrice = kilometersPrice().get("2.500 km.");
            case 3000 -> kilometerPrice = kilometersPrice().get("3.000 km.");
            case 3500 -> kilometerPrice = kilometersPrice().get("3.500 km.");
            case 4000 -> kilometerPrice = kilometersPrice().get("4.000 km.");
            case 4500 -> kilometerPrice = kilometersPrice().get("4.500 km.");
        }

        return monthlyPrice + kilometerPrice;
    }

    public int amountOfCars() {
        return carRepository.getAllCars().size();
    }
    public int amountOfCarsDamaged() {
        return carRepository.getAllCarsStatus(Status.DAMAGED).size();
    }

    public int amountOfCarsOnStock() {
        return carRepository.getAllCarsStatus(Status.ON_STOCK).size();
    }

    public int amountOfCarsRented() {
        return carRepository.getAllCarsStatus(Status.RENTED).size();
    }

    public int totalSumOfRentedCars() {
        List<Car> rentedCars = carRepository.getAllCarsStatus(Status.RENTED);

        int totalSum = 0;

        for (Car rentedCar : rentedCars) {
            totalSum += calculateTotalPrice(rentedCar);
        }

        return totalSum;
    }




    public boolean isKilometersDrivenNowHigher(int vehicleNumber, int totalKilometersTraveled) {

        //Tjekker om bilen der bliver sendt fra controlleren eksister listen af udlejet biler
        for (Car car : carRepository.getAllCarsStatus(Status.RENTED)) {

            //Hvis bilens vehicleNumber stemmer overens med en af bilerne i listen af udlejet biler så tjekker vi antal kilometer kørt
            if (car.getVehicleNumber() == vehicleNumber) {
                //Hvis de indtastede kilometer fra brugeren er højere end de nuværende kørte kilometer returnere vi true. Hvis ikke returnere vi false
                return car.getTotalKilometersDriven() < totalKilometersTraveled;
            }
        }

        //Tjekker om bilen der bliver sendt fra controlleren eksister listen af udløbet biler
        for (Car car : carRepository.getAllCarsStatus(Status.EXPIRED)) {
            //Hvis bilens vehicleNumber stemmer overens med en af bilerne i listen af udløbet biler så tjekker vi antal kilometer kørt
            if (car.getVehicleNumber() == vehicleNumber) {
                //Hvis de indtastede kilometer fra brugeren er højere end de nuværende kørte kilometer returnere vi true. Hvis ikke returnere vi false
                return car.getTotalKilometersDriven() < totalKilometersTraveled;
            }
        }

        //Hvis bilen ikke eksistere i nogle af listerne returnere vi false
        return false;
    }

    public boolean isCarPriceMinusOne(Car car, int monthsRented){

        int monthlyPrice = 0;
        switch (monthsRented) {
            case 3 -> monthlyPrice = car.getMonthsPrice3();
            case 6 -> monthlyPrice = car.getMonthsPrice6();
            case 12 -> monthlyPrice = car.getMonthsPrice12();
            case 24 -> monthlyPrice = car.getMonthsPrice24();
            case 36 -> monthlyPrice = car.getMonthsPrice36();
        }
        return monthlyPrice == -1;
    }




}
