package com.example.bilabonnement.controller;

import com.example.bilabonnement.repository.CarRepository;
import com.example.bilabonnement.repository.RentalRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DataRegController {

    private CarRepository carRepository = new CarRepository();
    private RentalRepository rentalRepository = new RentalRepository();

    private String onStock = "OnStock";
    private String rented = "Rented";
    private String damaged = "Damaged";
    @GetMapping("/dataRegistration")
    public String registerData(Model model){
        model.addAttribute("carsOnStock", carRepository.getAllCarsStatus(onStock));
        model.addAttribute("carsRentedOut", carRepository.getAllCarsStatus(rented));
        model.addAttribute("rentalAgreements", rentalRepository.getAllRentalAgreements());

        return "dataRegistration";
    }
}
