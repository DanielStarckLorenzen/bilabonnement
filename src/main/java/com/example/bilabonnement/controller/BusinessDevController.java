package com.example.bilabonnement.controller;

import com.example.bilabonnement.service.CarService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BusinessDevController {

    private CarService carService = new CarService();

    @GetMapping("/businessData")
    public String seeDataOverview(Model model) {
        model.addAttribute("totalSumOfRentedCars", carService.totalSumOfRentedCars());
        model.addAttribute("amountOfCarsRented", carService.amountOfCarsRented());
        model.addAttribute("amountOfCarsOnStock", carService.amountOfCarsOnStock());
        model.addAttribute("amountOfCarsDamaged", carService.amountOfCarsDamaged());

        return "businessData";
    }
}
