package com.example.bilabonnement.controller;

import com.example.bilabonnement.model.Car;
import com.example.bilabonnement.repository.CarRepository;

import com.example.bilabonnement.repository.DamageRepository;
import com.example.bilabonnement.repository.RentalRepository;
import com.example.bilabonnement.service.CarService;
import com.example.bilabonnement.service.RentalService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Controller
public class HomeController {

    private String onStock = "OnStock";
    private String rented = "Rented";
    private String damaged = "Damaged";

    private CarRepository carRepository = new CarRepository();
    private RentalRepository rentalRepository = new RentalRepository();
    private DamageRepository damageRepository = new DamageRepository();
    private CarService carService = new CarService();
    private RentalService rentalService = new RentalService();


    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("allCars", carService.amountOfCars());
        model.addAttribute("carsOnStock", carService.amountOfCarsOnStock());
        model.addAttribute("carsRented", carService.amountOfCarsRented());
        model.addAttribute("carsDamaged", carService.amountOfCarsDamaged());

        return "index";
    }

    @PostMapping("/returnCar")
    public String returnCar(WebRequest dataFromForm) {
        int vehicleNumber = Integer.parseInt(Objects.requireNonNull(dataFromForm.getParameter("vehicleNumber")));
        int totalKilometersTraveled = Integer.parseInt(Objects.requireNonNull(dataFromForm.getParameter("totalKilometersDriven")));

        if (carService.isKilometersDrivenNowHigher(vehicleNumber, totalKilometersTraveled)) {
            rentalService.calculateOverDrivenKm(vehicleNumber, totalKilometersTraveled);
            carRepository.changeStatus(onStock, vehicleNumber);
            carRepository.updateKilometersDriven(vehicleNumber, totalKilometersTraveled);
        }

        return "redirect:/dataRegistration";


    }


    @PostMapping("/setUpAgreement")
    public String setUpAgreement(WebRequest dataFromForm){
        String frameNumber = dataFromForm.getParameter("frameNumber");

        return "redirect:/showCar?frameNumber=" + frameNumber;
    }

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

    //Ret registrer til register
    @PostMapping("/registerAgreement")
    public String registerAgreement(WebRequest dataFromForm) {
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

    @GetMapping("/showListOfCarData")
    public String showListOfCars(@RequestParam String status, Model model) {
        List<Car> cars = new ArrayList<>();

        //Ikke nødvendigt at bruge service, da de virker redundant...
        switch (status) {
            case "onStock" -> cars = carRepository.getAllCarsStatus(onStock);
            case "allCars" -> cars = carRepository.getAllCars();
            case "damaged" -> cars = carRepository.getAllCarsStatus(damaged);
            case "rented" -> cars = carRepository.getAllCarsStatus(rented);
        }
        model.addAttribute("cars", cars);


        System.out.println(rentalService.daysLeftToReturn(rentalRepository.getEndDate(rentalRepository.getMaxRentalId(6))));


        return "showListOfCarData";
    }

    @GetMapping("/createCar")
    public String carCreationSite() {
        return "createCar";
    }

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

        carRepository.createCar(new Car(frameNumber, model, manufacturer, isManual, accessories, co2Discharge, onStock, monthsPrice3, monthsPrice6, monthsPrice12, monthsPrice24, monthsPrice36, 0, color));

        return "redirect:/";
    }

    @GetMapping("/sellCarMenu")
    public String sellCarMenu(Model model) {
        model.addAttribute("carsOnStock", carRepository.getAllCarsStatus(onStock));
        return "sellCar";
    }

    @PostMapping("/carToBeSold")
    public String sellingOfCar(WebRequest dataFromForm) {
        String frameNumber = dataFromForm.getParameter("frameNumber");

        return "redirect:/sellCar?frameNumber=" + frameNumber;
    }

    @GetMapping("/sellCar")
    public String sellCar(@RequestParam String frameNumber) {
        carRepository.removeCar(frameNumber);

        return "redirect:/";
    }

}
