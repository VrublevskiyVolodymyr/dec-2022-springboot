package ua.com.owu.dec2022springboot.services;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ua.com.owu.dec2022springboot.models.Car;

import java.util.List;

@Service // @Service("two")
@AllArgsConstructor
public class CarServiceImpl2 implements CarService{
    @Override
    public void save(Car car) {

    }

    @Override
    public ResponseEntity<List<Car>> getAllCars() {
        return null;
    }

    @Override
    public ResponseEntity<Car> getCar(int id) {
        return null;
    }

    @Override
    public void deleteCar(int id) {

    }

    @Override
    public ResponseEntity<List<Car>> getCarsByPower(int value) {
        return null;
    }

    @Override
    public ResponseEntity<List<Car>> getCarByProducer(String value) {
        return null;
    }
}
