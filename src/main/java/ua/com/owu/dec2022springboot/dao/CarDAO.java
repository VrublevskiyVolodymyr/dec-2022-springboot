package ua.com.owu.dec2022springboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.owu.dec2022springboot.dao.models.Car;
import java.util.List;

public interface CarDAO extends JpaRepository<Car, String> {
    List<Car> findByProducer(String producer);
    List<Car> findByPower(int value);
}
