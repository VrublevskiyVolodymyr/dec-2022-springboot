package ua.com.owu.dec2022springboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.owu.dec2022springboot.models.Car;

import java.util.List;

public interface CarDAO extends JpaRepository<Car, Integer> {
    List<Car> findByProducer(String value);
    List<Car> findByPower(int value);
}
