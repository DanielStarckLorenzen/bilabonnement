package com.example.bilabonnement.service;


import com.example.bilabonnement.model.Car;
import com.example.bilabonnement.model.RentalAgreements;
import com.example.bilabonnement.repository.CarRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarService {

    private CarRepository carRepository = new CarRepository();

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
        RentalAgreements rentalCar = carRepository.getMonthsAndKilometers(vehicleNumber);

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

        int sum = monthlyPrice + kilometerPrice;

        return sum;
    }

    public int totalSumOfRentedCars() {
        List<Car> rentedCars = carRepository.getAllCarsStatus("Rented");

        int totalSum = 0;

        for (int i=0; i < rentedCars.size(); i++) {
            totalSum += calculateTotalPrice(rentedCars.get(i));
        }

        return totalSum;
    }

    public int amountOfCarsRented() {
        return carRepository.getAllCarsStatus("Rented").size();
    }


}
