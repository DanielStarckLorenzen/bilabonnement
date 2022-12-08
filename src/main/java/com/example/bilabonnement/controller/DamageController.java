package com.example.bilabonnement.controller;


import com.example.bilabonnement.model.Car;
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

    private String onStock = "OnStock";
    private String rented = "Rented";
    private String damaged = "Damaged";

    @GetMapping("/damageRegistration")
    public String registerDamages(Model model){
        model.addAttribute("allCars", carRepository.getAllCars());
        model.addAttribute("damagedCars", carRepository.getAllCarsStatus(damaged));
        model.addAttribute("carsOnStock", carRepository.getAllCarsStatus(onStock));


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
        int damagedCarNumber = Integer.parseInt(Objects.requireNonNull(dataFromForm.getParameter("damagedCar")));
        Car damagedCar = new Car();
        for (Car car : carRepository.getAllCars()) {
            if (damagedCarNumber == car.getVehicleNumber()) {
                damagedCar = car;
            }
        }
        String problem = dataFromForm.getParameter("problem");
        double costs = Double.parseDouble(Objects.requireNonNull(dataFromForm.getParameter("costs")));
        damageRepository.createDamageReport(damagedCar, problem, costs);

        return "redirect:/damageRegistration";
    }

    @PostMapping("/returnFromRepair")
    public String returnFromRepair(WebRequest dataFromForm) {
        int vehicleNumber = Integer.parseInt(Objects.requireNonNull(dataFromForm.getParameter("vehicleNumber")));
        carRepository.changeStatus(onStock, vehicleNumber);

        return "redirect:/damageRegistration";
    }
}
