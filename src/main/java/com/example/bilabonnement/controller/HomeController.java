package com.example.bilabonnement.controller;

import com.example.bilabonnement.model.Car;
import com.example.bilabonnement.repository.CarRepository;

import com.example.bilabonnement.service.CarService;
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
public class HomeController {

    private String onStock = "OnStock";
    private String rented = "Rented";
    private String damaged = "Damaged";

    private CarRepository repository = new CarRepository();
    private CarService carService = new CarService();
    private Car car = new Car();


    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("allCars", carService.amountOfCars());
        model.addAttribute("carsOnStock", carService.amountOfCarsOnStock());
        model.addAttribute("carsRented", carService.amountOfCarsRented());
        model.addAttribute("carsDamaged", carService.amountOfCarsDamaged());

        return "index";
    }
    @GetMapping("/dataRegistration")
    public String registerData(Model model){
        model.addAttribute("carsOnStock", repository.getAllCarsStatus(onStock));
        model.addAttribute("carsRentedOut", repository.getAllCarsStatus(rented));

        return "dataRegistration";
    }

    @PostMapping("/returnCar")
    public String returnCar(WebRequest dataFromForm) {
        int vehicleNumber = Integer.parseInt(Objects.requireNonNull(dataFromForm.getParameter("vehicleNumber")));
        int totalKilometersTraveled = Integer.parseInt(Objects.requireNonNull(dataFromForm.getParameter("totalKilometersDriven")));

        if (carService.isKilometersDrivenNowHigher(vehicleNumber, totalKilometersTraveled)) {
            carService.calculateOverDrivenKm(vehicleNumber, totalKilometersTraveled);
            repository.changeStatus(onStock, vehicleNumber);
            repository.updateKilometersDriven(vehicleNumber, totalKilometersTraveled);
        }

        return "redirect:/dataRegistration";


    }

    @GetMapping("/damageRegistration")
    public String registerDamages(Model model){
        model.addAttribute("allCars", repository.getAllCars());
        model.addAttribute("damagedCars", repository.getAllCarsStatus(damaged));
        model.addAttribute("carsOnStock", repository.getAllCarsStatus(onStock));


        return "damageRegistration";
    }

    @PostMapping("/registerDamage")
    public String registerDamage(WebRequest dataFromForm, Model model) {
        int vehicleNumber = Integer.parseInt(Objects.requireNonNull(dataFromForm.getParameter("vehicleNumber")));

        Car damagedCar = new Car();

        for (Car car : repository.getAllCars()) {
            if (vehicleNumber == car.getVehicleNumber()) {
                damagedCar = car;
            }
        }
        model.addAttribute("damagedCar", damagedCar);

        return "damageReport";
    }

    @PostMapping("/createDamageReport")
    public String createDamageReport(WebRequest dataFromForm) {
        int damagedCarNumber = Integer.parseInt(Objects.requireNonNull(dataFromForm.getParameter("damagedCar")));
        Car damagedCar = new Car();
        for (Car car : repository.getAllCars()) {
            if (damagedCarNumber == car.getVehicleNumber()) {
                damagedCar = car;
            }
        }
        String problem = dataFromForm.getParameter("problem");
        double costs = Double.parseDouble(Objects.requireNonNull(dataFromForm.getParameter("costs")));
        repository.createDamageReport(damagedCar, problem, costs);

        return "redirect:/damageRegistration";
    }

    @PostMapping("/returnFromRepair")
    public String returnFromRepair(WebRequest dataFromForm) {
        int vehicleNumber = Integer.parseInt(Objects.requireNonNull(dataFromForm.getParameter("vehicleNumber")));
        repository.changeStatus(onStock, vehicleNumber);

        return "redirect:/damageRegistration";
    }

    @GetMapping("/businessData")
    public String seeDataOverview(Model model) {
        model.addAttribute("totalSumOfRentedCars", carService.totalSumOfRentedCars());
        model.addAttribute("amountOfCarsRented", carService.amountOfCarsRented());
        model.addAttribute("amountOfCarsOnStock", carService.amountOfCarsOnStock());
        model.addAttribute("amountOfCarsDamaged", carService.amountOfCarsDamaged());

        return "businessData";
    }

    @PostMapping("/setUpAgreement")
    public String setUpAgreement(WebRequest dataFromForm){
        String carModel = dataFromForm.getParameter("model");

        return "redirect:/showCar?carModel=" + carModel;
    }

    @GetMapping("/showCar")
    public String showCar(@RequestParam String carModel, Model model) {
        System.out.println(carModel);

        Car chosenCar = null;
        for (Car car : repository.getAllCars()) {
            if (car.getModel().equals(carModel)) {
                chosenCar = car;
            }
        }
        model.addAttribute("kilometersPrice", carService.kilometersPrice());
        model.addAttribute("chosenCar", chosenCar);

        return "showCar";
    }

    //Ret registrer til register
    @PostMapping("/registrerAgreement")
    public String registrerAgreement(WebRequest dataFromForm) {
        int monthsRented = Integer.parseInt(Objects.requireNonNull(dataFromForm.getParameter("monthsRented")));
        int kilometers = Integer.parseInt(Objects.requireNonNull(dataFromForm.getParameter("kilometers")));
        int vehicleNumber = Integer.parseInt(Objects.requireNonNull(dataFromForm.getParameter("vehicleNumber")));
        String customerName = dataFromForm.getParameter("customerName");

        Car chosenCar = null;

        for (Car car : repository.getAllCars()) {
            if (car.getVehicleNumber() == vehicleNumber) {
                chosenCar = car;
            }
        }

        repository.createRentalAgreement(chosenCar, monthsRented, kilometers);

        return "redirect:/";
    }

    @GetMapping("/showListOfCarData")
    public String showListOfCars(@RequestParam String status, Model model) {
        List<Car> cars = new ArrayList<>();

        //Ikke nødvendigt at bruge service, da de virker redundant...
        switch (status) {
            case "onStock" -> cars = repository.getAllCarsStatus(onStock);
            case "allCars" -> cars = repository.getAllCars();
            case "damaged" -> cars = repository.getAllCarsStatus(damaged);
            case "rented" -> cars = repository.getAllCarsStatus(rented);
        }
        model.addAttribute("cars", cars);

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

        repository.createCar(new Car(frameNumber, model, manufacturer, isManual, accessories, co2Discharge, onStock, monthsPrice3, monthsPrice6, monthsPrice12, monthsPrice24, monthsPrice36, 0));

        return "redirect:/";
    }

    @GetMapping("/sellCarMenu")
    public String sellCarMenu(Model model) {
        model.addAttribute("carsOnStock", repository.getAllCarsStatus(onStock));
        return "sellCar";
    }

    @PostMapping("/carToBeSold")
    public String sellingOfCar(WebRequest dataFromForm) {
        String frameNumber = dataFromForm.getParameter("frameNumber");

        return "redirect:/sellCar?frameNumber=" + frameNumber;
    }

    @GetMapping("/sellCar")
    public String sellCar(@RequestParam String frameNumber) {
        repository.removeCar(frameNumber);

        return "redirect:/";
    }

}
