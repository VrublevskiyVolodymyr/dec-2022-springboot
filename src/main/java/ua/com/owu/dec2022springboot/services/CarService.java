package ua.com.owu.dec2022springboot.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import ua.com.owu.dec2022springboot.dao.models.Car;
import java.io.IOException;
import java.util.List;


@Service
public interface CarService {

    ResponseEntity<String> save(Car car);

    ResponseEntity<List<Car>> getAllCars();

    ResponseEntity<Car> getCar(String stringId);

    void deleteCar(String stringId);

    ResponseEntity<List<Car>> getCarsByPower(@PathVariable int value);

    ResponseEntity<List<Car>> getCarByProducer(@PathVariable String producer);

    void saveWithPhoto( String producer, String model,    int power,   int userId,   MultipartFile photo) throws IOException;
}
