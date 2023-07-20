package ua.com.owu.dec2022springboot.services;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import ua.com.owu.dec2022springboot.dao.CarDAO;
import ua.com.owu.dec2022springboot.models.Car;
import ua.com.owu.dec2022springboot.models.User;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Service // @Service("one")
@AllArgsConstructor

public class CarServiceImpl1 implements CarService {
    private CarDAO carDAO;
    private UserService userService;
    private MailService mailService;

    public void save(Car car) {
        if (car == null) {
            throw new RuntimeException();
        }
        carDAO.save(car);

        User user = userService.getUserById(car.getUserId()).getBody();
        assert user != null;
        String email = user.getEmail();
        String body = "Hello user " + user.getName() + " car " + car.toString() + " is created :)";
        mailService.sendEmail(email, body, Optional.empty());
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

            Car car = carDAO.findById(id).get();
            carDAO.deleteById(id);

            User user = userService.getUserById(car.getUserId()).getBody();
            assert user != null;
            String email = user.getEmail();
            String body = "Hello user  " + user.getName() + " car id " + id + "  is deleted";
            mailService.sendEmail(email, body,Optional.empty());
        }
    }

    public ResponseEntity<List<Car>> getCarsByPower(@PathVariable int value) {
        return new ResponseEntity<>(carDAO.findByPower(value), HttpStatus.OK);
    }

    public ResponseEntity<List<Car>> getCarByProducer(@PathVariable String value) {
        return new ResponseEntity<>(carDAO.findByProducer(value), HttpStatus.OK);
    }

    @SneakyThrows
    @Override
    public void saveWithPhoto(String producer, String model,  int power, int userId, MultipartFile photo) {
        Car car = new Car(producer, model,  power, userId);
        String originalFilename = photo.getOriginalFilename();
        car.setPhoto("/photo/" + originalFilename);
        String path = System.getProperty("user.home") + File.separator + "images" + File.separator + originalFilename;
        File file = new File(path);
        photo.transferTo(file);
        carDAO.save(car);

        User user = userService.getUserById(userId).getBody();
        assert user != null;
        String email = user.getEmail();
        String body = "Hello user " + user.getName() + " car " + car.toString() + " is created :)";
        mailService.sendEmail(email, body, Optional.of(file));
    }
}
