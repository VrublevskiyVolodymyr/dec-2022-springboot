package ua.com.owu.dec2022springboot.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
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

    void saveWithPhoto( String model,  String producer,  int power,   int userId,   MultipartFile photo);
}
