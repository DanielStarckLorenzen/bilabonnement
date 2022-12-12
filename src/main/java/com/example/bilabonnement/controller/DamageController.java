package com.example.bilabonnement.controller;

import com.example.bilabonnement.model.Car;
import com.example.bilabonnement.model.enums.Status;
import com.example.bilabonnement.repository.CarRepository;
import com.example.bilabonnement.repository.DamageRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import java.util.Objects;

@Controller
public class DamageController {

    private CarRepository carRepository = new CarRepository();
    private DamageRepository damageRepository = new DamageRepository();


    @GetMapping("/damageRegistration")
    public String registerDamages(Model model){
        model.addAttribute("allCars", carRepository.getAllCars());
        model.addAttribute("damagedCars", carRepository.getAllCarsStatus(Status.DAMAGED));
        model.addAttribute("carsOnStock", carRepository.getAllCarsStatus(Status.ON_STOCK));


        return "damageRegistration";
    }

    @PostMapping("/registerDamage")
    public String registerDamage(WebRequest dataFromForm, Model model) {
        int vehicleNumber = Integer.parseInt(Objects.requireNonNull(dataFromForm.getParameter("vehicleNumber")));

        Car damagedCar = new Car();

        for (Car car : carRepository.getAllCars()) {
            if (vehicleNumber == car.getVehicleNumber()) {
                damagedCar = car;
            }
        }
        model.addAttribute("damagedCar", damagedCar);

        return "damageReport";
    }

    @PostMapping("/createDamageReport")
    public String createDamageReport(WebRequest dataFromForm) {
        //Hent bilen der er blevet valgt ud fra "vehicleNumber"
        int damagedCarNumber = Integer.parseInt(Objects.requireNonNull(dataFromForm.getParameter("damagedCar")));

        //Find den rigtige bil ud fra det data vi får fra formen i HTML
        Car damagedCar = new Car();
        for (Car car : carRepository.getAllCars()) {
            if (damagedCarNumber == car.getVehicleNumber()) {
                damagedCar = car;
            }
        }

        //Hent hvad der blevet indtastet som problemet fra formen
        String problem = dataFromForm.getParameter("problem");
        double costs = Double.parseDouble(Objects.requireNonNull(dataFromForm.getParameter("costs")));

        //Opret skades rapporten i databasen
        damageRepository.createDamageReport(damagedCar, problem, costs);

        //Redirecter tilbage til oversigten over skader
        return "redirect:/damageRegistration";
    }

    @PostMapping("/returnFromRepair")
    public String returnFromRepair(WebRequest dataFromForm) {
        int vehicleNumber = Integer.parseInt(Objects.requireNonNull(dataFromForm.getParameter("vehicleNumber")));
        carRepository.changeStatus(Status.ON_STOCK, vehicleNumber);

        return "redirect:/damageRegistration";
    }
}
