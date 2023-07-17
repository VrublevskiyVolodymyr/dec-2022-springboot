package ua.com.owu.dec2022springboot.services;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import ua.com.owu.dec2022springboot.dao.CarDAO;
import ua.com.owu.dec2022springboot.models.Car;

import java.util.List;

@Service // @Service("one")
@AllArgsConstructor
public class CarServiceImpl1 implements CarService{
    private CarDAO carDAO;

    public  void save(Car car) {
        if (car == null) {
            throw new RuntimeException();
        }
        carDAO.save(car);
    }

    public ResponseEntity<List<Car>> getAllCars() {
        return new ResponseEntity<>(carDAO.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Car> getCar(int id) {
        Car car = null;
        if (id > 0) {
            car = carDAO.findById(id).get();
        }
        return new ResponseEntity<>(car, HttpStatus.OK);
    }

    public void deleteCar(int id) {
        if (id > 0) {
            carDAO.deleteById(id);
        }
    }

    public ResponseEntity<List<Car>> getCarsByPower(@PathVariable int value) {
        return new ResponseEntity<>(carDAO.findByPower(value), HttpStatus.OK);
    }

    public ResponseEntity<List<Car>> getCarByProducer(@PathVariable String value) {
        return new ResponseEntity<>(carDAO.findByProducer(value), HttpStatus.OK);
    }
}
