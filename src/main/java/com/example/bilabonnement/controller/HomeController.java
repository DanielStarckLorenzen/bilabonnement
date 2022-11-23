package com.example.bilabonnement.controller;

import com.example.bilabonnement.model.Car;
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
import java.util.ArrayList;
import java.util.List;


@Controller
public class HomeController {

    private CarRepository repository = new CarRepository();
    private CarService carService = new CarService();
    private Car car = new Car();
    private List<Car> cars = repository.getAllCars();
    private List<Car> rentedCars = repository.getAllRentedCars();

    @GetMapping("/")
    public String index() {
        return "index";
    }
    @GetMapping("/dataRegistration")
    public String registerData(Model model){
        model.addAttribute("cars", cars);

        return "dataRegistration";
    }

    @GetMapping("/damageRegistration")
    public String registerDamages(Model model){
         Connection con = DatabaseManager.getConnection();

         //Test model
         model.addAttribute("rentedCars",rentedCars);

        return "damageRegistration";
    }

    @GetMapping("/businessData")
    public String seeDataOverview(Model model) {
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
        for (Car car : cars) {
            if (car.getModel().equals(carModel)) {
                chosenCar = car;
            }
        }

        model.addAttribute("kilometersPrice", carService.kilometersPrice());
        model.addAttribute("chosenCar", chosenCar);

        return "showCar";
    }

    @PostMapping("/registrerAgreement")
    public String registrerAgreement(WebRequest dataFromForm) {


        return "redirect:/";
    }
}
