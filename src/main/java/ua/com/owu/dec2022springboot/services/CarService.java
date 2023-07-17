package ua.com.owu.dec2022springboot.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import ua.com.owu.dec2022springboot.models.Car;

import java.util.List;

@Service
public interface CarService {

    void save(Car car);

    ResponseEntity<List<Car>> getAllCars();

    ResponseEntity<Car> getCar(int id);

    void deleteCar(int id);

    ResponseEntity<List<Car>> getCarsByPower(@PathVariable int value);

    ResponseEntity<List<Car>> getCarByProducer(@PathVariable String value);
}
