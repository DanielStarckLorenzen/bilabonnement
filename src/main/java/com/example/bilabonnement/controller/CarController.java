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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Controller
public class CarController {

    private CarRepository carRepository = new CarRepository();
    private RentalRepository rentalRepository = new RentalRepository();
    private CarService carService = new CarService();
    private RentalService rentalService = new RentalService();


    //Start siden der opdatere udløbne biler ved åbning og viser hvor mange biler hver "status" har
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("allCars", carService.amountOfCars());
        model.addAttribute("carsOnStock", carService.amountOfCarsOnStock());
        model.addAttribute("carsRented", carService.amountOfCarsRented());
        model.addAttribute("carsDamaged", carService.amountOfCarsDamaged());
        rentalService.checkIfExpired();

        return "index";
    }

    //Retunere en bil ved at opdatere bilens status og lejeaftalen
    @PostMapping("/returnCar")
    public String returnCar(WebRequest dataFromForm) {
        int vehicleNumber = Integer.parseInt(Objects.requireNonNull(dataFromForm.getParameter("vehicleNumber")));
        int totalKilometersTraveled = Integer.parseInt(Objects.requireNonNull(dataFromForm.getParameter("totalKilometersDriven")));

        if (carService.isKilometersDrivenNowHigher(vehicleNumber, totalKilometersTraveled)) {
            rentalService.calculateOverDrivenKm(vehicleNumber, totalKilometersTraveled);
            carRepository.changeStatus(Status.ON_STOCK, vehicleNumber);
            carRepository.updateKilometersDriven(vehicleNumber, totalKilometersTraveled);
        }

        return "redirect:/dataRegistration";

    }


    //Viser forretningsiden med tilhørende data
    @GetMapping("/businessData")
    public String seeDataOverview(Model model) {
        model.addAttribute("totalSumOfRentedCars", carService.totalSumOfRentedCars());
        model.addAttribute("amountOfCarsRented", carService.amountOfCarsRented());
        model.addAttribute("amountOfCarsOnStock", carService.amountOfCarsOnStock());
        model.addAttribute("amountOfCarsDamaged", carService.amountOfCarsDamaged());

        return "businessData";
    }

    //Viser bilen der er blevet valgt ved oprettelse af lejeaftale
    @GetMapping("/showCar")
    public String showCar(@RequestParam String frameNumber, Model model) {
        System.out.println(frameNumber);

        Car chosenCar = null;
        for (Car car : carRepository.getAllCars()) {
            if (car.getFrameNumber().equals(frameNumber)) {
                chosenCar = car;
            }
        }
        model.addAttribute("kilometersPrice", carService.kilometersPrice());
        model.addAttribute("chosenCar", chosenCar);

        return "showCar";
    }

    //Viser alle bilerne ud fra den pågældende status der er blevet valgt
    @GetMapping("/showListOfCarData")
    public String showListOfCars(@RequestParam String status, Model model) {
        List<Car> cars = new ArrayList<>();

        //Ikke nødvendigt at bruge service, da de virker redundant...
        switch (status) {
            case "onStock" -> cars = carRepository.getAllCarsStatus(Status.ON_STOCK);
            case "allCars" -> cars = carRepository.getAllCars();
            case "damaged" -> cars = carRepository.getAllCarsStatus(Status.DAMAGED);
            case "rented" -> cars = carRepository.getAllCarsStatus(Status.RENTED);
        }
        model.addAttribute("cars", cars);


        System.out.println(rentalService.daysLeftToReturn(rentalRepository.getEndDate(rentalRepository.getMaxRentalId(6))));


        return "showListOfCarData";
    }

    @GetMapping("/createCar")
    public String carCreationSite() {
        return "createCar";
    }

    //Opretter en ny bil i databasen
    @PostMapping("/carCreated")
    public String createCar(WebRequest dataFromForm) {
        String frameNumber = dataFromForm.getParameter("frameNumber");
        String model = dataFromForm.getParameter("model");
        String manufacturer = dataFromForm.getParameter("manufacturer");
        boolean isManual = Boolean.parseBoolean(dataFromForm.getParameter("isManual"));
        String accessories = dataFromForm.getParameter("accessories");
        int co2Discharge = Integer.parseInt(Objects.requireNonNull(dataFromForm.getParameter("CO2discharge")));
        int monthsPrice3 = Integer.parseInt(Objects.requireNonNull(dataFromForm.getParameter("3months")));
        int monthsPrice6 = Integer.parseInt(Objects.requireNonNull(dataFromForm.getParameter("6months")));
        int monthsPrice12 = Integer.parseInt(Objects.requireNonNull(dataFromForm.getParameter("12months")));
        int monthsPrice24 = Integer.parseInt(Objects.requireNonNull(dataFromForm.getParameter("24months")));
        int monthsPrice36 = Integer.parseInt(Objects.requireNonNull(dataFromForm.getParameter("36months")));
        String color = dataFromForm.getParameter("color");

        carRepository.createCar(new Car(frameNumber, model, manufacturer, isManual, accessories, co2Discharge, Status.ON_STOCK.toString(), monthsPrice3, monthsPrice6, monthsPrice12, monthsPrice24, monthsPrice36, 0, color));

        return "redirect:/";
    }

    //Viser biler der kan sælges
    @GetMapping("/sellCarMenu")
    public String sellCarMenu(Model model) {
        model.addAttribute("carsOnStock", carRepository.getAllCarsStatus(Status.ON_STOCK));
        return "sellCar";
    }

    //Videresender bilen der skal sælges
    @PostMapping("/carToBeSold")
    public String sellingOfCar(WebRequest dataFromForm) {
        String frameNumber = dataFromForm.getParameter("frameNumber");

        return "redirect:/sellCar?frameNumber=" + frameNumber;
    }

    //Fjerner den valgte bil fra databasen
    @GetMapping("/sellCar")
    public String sellCar(@RequestParam String frameNumber) {
        carRepository.removeCar(frameNumber);

        return "redirect:/";
    }

}
