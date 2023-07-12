package ua.com.owu.dec2022springboot.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.owu.dec2022springboot.dao.CarDAO;
import ua.com.owu.dec2022springboot.models.Car;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/cars")
public class CarController {
        private CarDAO carDAO;

        @GetMapping("")
        public ResponseEntity<List<Car>> getCar() {
            return new ResponseEntity<>(carDAO.findAll(), HttpStatus.OK);
        }

        @GetMapping("/{id}")
        public ResponseEntity<Car> getCars(@PathVariable int id) {
            Car car = carDAO.findById(id).get();
            return new ResponseEntity<>( car, HttpStatus.OK);
        }

        @ResponseStatus(HttpStatus.CREATED)
        @PostMapping("")
        public void save(@RequestBody Car car) {
            carDAO.save(car);
        }

        @DeleteMapping("")
        public ResponseEntity<List<Car>>  deleteCar(@RequestParam int id) {
            carDAO.deleteById(id);
            return new ResponseEntity<>(carDAO.findAll(), HttpStatus.OK);
        }


        @GetMapping("/power/{value}")
        public ResponseEntity<List<Car>> getCarsByPower(@PathVariable int value) {
            return new ResponseEntity<>(carDAO.findByPower(value), HttpStatus.OK);
        }

        @GetMapping("/producer/{value}")
        public ResponseEntity<List<Car>> getCarByProducer(@PathVariable String value) {
            return new ResponseEntity<>(carDAO.findByProducer(value), HttpStatus.OK);
        }
}
