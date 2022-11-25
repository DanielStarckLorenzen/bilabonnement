package com.example.bilabonnement.controller;

import com.example.bilabonnement.model.Car;
import com.example.bilabonnement.model.RentalAgreements;
import com.example.bilabonnement.repository.CarRepository;
import com.example.bilabonnement.repository.DatabaseManager;

import com.example.bilabonnement.service.CarService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import java.sql.Connection;
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
    private List<Car> allCars = repository.getAllCars();


    @GetMapping("/")
    public String index() {
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
        int totalKilometersTraveled = Integer.parseInt(Objects.requireNonNull(dataFromForm.getParameter("overTraveled")));

        carService.calculateOverDrivenKm(vehicleNumber, totalKilometersTraveled);
        repository.changeStatus(onStock, vehicleNumber);

        return "redirect:/dataRegistration";
    }

    @GetMapping("/damageRegistration")
    public String registerDamages(Model model){
        model.addAttribute("allCars", allCars);
        model.addAttribute("damagedCars", repository.getAllCarsStatus(damaged));
        model.addAttribute("carsOnStock", repository.getAllCarsStatus(onStock));


        return "damageRegistration";
    }

    @PostMapping("/registerDamage")
    public String registerDamage(WebRequest dataFromForm, Model model) {
        int vehicleNumber = Integer.parseInt(Objects.requireNonNull(dataFromForm.getParameter("vehicleNumber")));

        Car damagedCar = new Car();

        for (Car car : allCars) {
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
        for (Car car : allCars) {
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
        for (Car car : allCars) {
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

        Car chosenCar = null;

        for (Car car : allCars) {
            if (car.getVehicleNumber() == vehicleNumber) {
                chosenCar = car;
            }
        }

        repository.createRentalAgreement(chosenCar, monthsRented, kilometers);

        return "redirect:/";
    }


}
