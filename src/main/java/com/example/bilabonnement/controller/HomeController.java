package com.example.bilabonnement.controller;

import com.example.bilabonnement.model.Car;
import com.example.bilabonnement.repository.CarRepository;
import com.example.bilabonnement.repository.DatabaseManager;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;


@Controller
public class HomeController {

    private CarRepository repository = new CarRepository();
    private Car car = new Car();

    @GetMapping("/")
    public String index() {
        return "index";
    }
    @GetMapping("/dataRegistration")
    public String registerData(){
        return "dataRegistration";
    }

    @GetMapping("/damageRegistration")
    public String registerDamages(){
         Connection con = DatabaseManager.getConnection();
        return "damageRegistration";
    }

    @GetMapping("/businessData")
    public String seeDataOverview(Model model) {
        List<Car> cars = repository.getAllCars();
        model.addAttribute("cars", cars);

        return "businessData";
    }
}
