package ua.com.owu.dec2022springboot.services;

import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.com.owu.dec2022springboot.dao.CarMongoDAO;
import ua.com.owu.dec2022springboot.models.Car;
import ua.com.owu.dec2022springboot.models.CarMongo;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import static ua.com.owu.dec2022springboot.utils.Converter.convertToObjectId;

@Service // @Service("two")
@AllArgsConstructor
public class CarServiceImpl2 implements CarService {

    CarMongoDAO carMongoDAO;


    @Override
    public ResponseEntity<String> save(Car car) {
        if (car == null) {
            throw new RuntimeException();
        }
        CarMongo carMongo = new CarMongo(car.getProducer(), car.getModel(), car.getPower(), car.getUserId());
        carMongoDAO.save(carMongo);
        String stringId = carMongo.getId().toString();
        return new ResponseEntity<>(stringId, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<Car>> getAllCars() {
        List<CarMongo> allCarsMongo = carMongoDAO.findAll();
        List<Car> allCars = new ArrayList<>();
        for (CarMongo carMongo : allCarsMongo) {
            Car car = new Car(carMongo.getProducer(), carMongo.getModel(), carMongo.getPower(), carMongo.getUserId());
            allCars.add(car);
        }
        return new ResponseEntity<>(allCars, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Car> getCar(String stringId) {

        CarMongo carMongo = null;
        Car car = null;
        if (stringId != null) {
            ObjectId objectId = convertToObjectId(stringId);
            System.out.println(objectId);
            carMongo = carMongoDAO.findById(objectId).orElse(null);
            assert carMongo != null;
            String id = carMongo.getId().toString();
            car = new Car(carMongo.getProducer(), carMongo.getModel(), carMongo.getPower(), carMongo.getUserId());
            car.setId(id);
        }
        return new ResponseEntity<>(car, HttpStatus.OK);
    }

    @Override
    public void deleteCar(String stringId) {
        if (stringId != null) {
            ObjectId objectId = convertToObjectId(stringId);
            carMongoDAO.deleteById(objectId);
        }
    }

    @Override
    public ResponseEntity<List<Car>> getCarsByPower(int value) {
        List<Car> CarsByPower = carMongoDAO.findByPower(value).stream()
                .map(carMongo -> new Car(carMongo.getProducer(), carMongo.getModel(), carMongo.getPower(), carMongo.getUserId()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(CarsByPower, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<List<Car>> getCarByProducer(String producer) {
        List<Car> CarsByProducer = carMongoDAO.findByProducer(producer).stream()
                .map(carMongo -> new Car(carMongo.getProducer(), carMongo.getModel(), carMongo.getPower(), carMongo.getUserId()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(CarsByProducer, HttpStatus.OK);
    }

    @Override
    public void saveWithPhoto(String producer, String model, int power, int userId, MultipartFile photo) throws IOException {
        CarMongo carMongo = new CarMongo(producer, model,  power, userId);
        String originalFilename = photo.getOriginalFilename();
        carMongo.setPhoto("/photo/" + originalFilename);
        String path = System.getProperty("user.home") + File.separator + "images" + File.separator + originalFilename;
        File file = new File(path);
        photo.transferTo(file);
        carMongoDAO.save(carMongo);
    }
}
