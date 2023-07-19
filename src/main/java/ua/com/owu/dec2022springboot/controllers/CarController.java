package ua.com.owu.dec2022springboot.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.com.owu.dec2022springboot.dao.CarDAO;
import ua.com.owu.dec2022springboot.models.Car;
import ua.com.owu.dec2022springboot.services.CarService;
import ua.com.owu.dec2022springboot.views.Views;

import java.io.IOException;
import java.util.List;

//@AllArgsConstructor
@RestController
@RequestMapping(value = "/cars")
public class CarController {

    private final CarService carService;

    public CarController( @Qualifier("carServiceImpl1") CarService carService) {  //@Qualifier("one")  or @Qualifier("two") if watch CarServiceImpl1,CarServiceImpl2
        this.carService = carService;
    }

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
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/saveWithPhoto")
    @JsonView(value = Views.Level1.class)
    public void saveWithPhoto(@RequestParam String model, @RequestParam String producer, @RequestParam int power,int userId, @RequestParam MultipartFile photo)
            throws IOException {
        carService.saveWithPhoto(model, producer, power, userId, photo);
    }
}
