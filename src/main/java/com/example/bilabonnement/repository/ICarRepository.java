package com.example.bilabonnement.repository;


import com.example.bilabonnement.model.Car;

import java.util.List;

public interface ICarRepository  {

    List<Car> getAllRentedCars();
}
