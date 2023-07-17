package ua.com.owu.dec2022springboot.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.owu.dec2022springboot.dao.CarDAO;
import ua.com.owu.dec2022springboot.models.Car;
import ua.com.owu.dec2022springboot.services.CarService;
import ua.com.owu.dec2022springboot.views.Views;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/cars")
public class CarController {
    private CarDAO carDAO;
    private CarService carService;

    @GetMapping("")
    @JsonView(value = Views.Level3.class)
    public ResponseEntity<List<Car>> getCars() {
        return carService.getAllCars();
    }

    @GetMapping("/{id}")
    @JsonView(value = Views.Level1.class)
    public ResponseEntity<Car> getCars(@PathVariable int id) {
        return carService.getCar(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("")
    public void save(@RequestBody @Valid Car car) {
       carService.save(car);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void deleteCar(@PathVariable int id) {
        carService.deleteCar(id);
    }

    @GetMapping("/power/{value}")
    @JsonView(value = Views.Level2.class)
    public ResponseEntity<List<Car>> getCarsByPower(@PathVariable int value) {
        return carService.getCarsByPower(value);
    }

    @GetMapping("/producer/{value}")
    @JsonView(value = Views.Level2.class)
    public ResponseEntity<List<Car>> getCarByProducer(@PathVariable String value) {
        return carService.getCarByProducer(value);
    }
}
