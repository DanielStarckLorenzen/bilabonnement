package com.example.bilabonnement.controller;

import com.example.bilabonnement.model.Car;
import com.example.bilabonnement.model.enums.Status;
import com.example.bilabonnement.repository.CarRepository;
import com.example.bilabonnement.repository.RentalRepository;
import com.example.bilabonnement.service.CarService;
import com.example.bilabonnement.service.RentalService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;
import java.util.Objects;

@Controller
public class RentalController {

    private CarRepository carRepository = new CarRepository();
    private RentalRepository rentalRepository = new RentalRepository();

    private CarService carService = new CarService();
    private RentalService rentalService = new RentalService();


    //Tager imod værdierne valgt ved oprettelse af en lejeaftale og opretter en lejeaftale i databasen
    @PostMapping("/registrerAgreement")
    public String registrerAgreement(WebRequest dataFromForm) {
        int monthsRented = Integer.parseInt(Objects.requireNonNull(dataFromForm.getParameter("monthsRented")));
        int kilometers = Integer.parseInt(Objects.requireNonNull(dataFromForm.getParameter("kilometers")));
        int vehicleNumber = Integer.parseInt(Objects.requireNonNull(dataFromForm.getParameter("vehicleNumber")));
        String customerName = dataFromForm.getParameter("customerName");

        Car chosenCar = null;

        for (Car car : carRepository.getAllCars()) {
            if (car.getVehicleNumber() == vehicleNumber) {
                chosenCar = car;
            }
        }

        //Hvis brugeren har valgt prisen -1 bliver lejeaftalen ikke oprettet
        if (carService.isCarPriceMinusOne(chosenCar, monthsRented)) {
            return "redirect:/showCar?frameNumber=" + chosenCar.getFrameNumber();
        } else {
            String startDateString = dataFromForm.getParameter("startDate");
            LocalDate startDate = LocalDate.parse(startDateString);
            LocalDate endDate = rentalService.endDate(startDate, monthsRented);

            rentalRepository.createRentalAgreement(chosenCar, monthsRented, kilometers, customerName, startDate, endDate);

            return "redirect:/";
        }
    }

    //Sender den valgte bil der skal oprettes en lejeaftale på
    @PostMapping("/setUpAgreement")
    public String setUpAgreement(WebRequest dataFromForm){
        String frameNumber = dataFromForm.getParameter("frameNumber");

        return "redirect:/showCar?frameNumber=" + frameNumber;
    }

    //Sender de nødvendige lister af biler, så bilerne kan ses
    @GetMapping("/dataRegistration")
    public String registerData(Model model){
        model.addAttribute("carsOnStock", carRepository.getAllCarsStatus(Status.ON_STOCK));
        model.addAttribute("carsRentedOut", carRepository.getAllCarsStatus(Status.RENTED));
        model.addAttribute("rentalAgreements", rentalRepository.getAllRentalAgreements());
        model.addAttribute("carsExpired", carRepository.getAllCarsStatus(Status.EXPIRED));

        return "dataRegistration";
    }

}
