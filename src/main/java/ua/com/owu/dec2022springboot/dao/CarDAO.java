package ua.com.owu.dec2022springboot.dao;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.com.owu.dec2022springboot.models.Car;

import java.util.List;

public interface CarDAO extends JpaRepository<Car,Integer> {

    List<Car> findByProducer(String value);
    List<Car> findByPower(int value);

}
