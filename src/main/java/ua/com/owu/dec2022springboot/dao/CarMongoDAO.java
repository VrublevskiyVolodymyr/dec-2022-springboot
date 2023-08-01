package ua.com.owu.dec2022springboot.dao;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import ua.com.owu.dec2022springboot.dao.models.CarMongo;
import java.util.List;

public interface CarMongoDAO extends MongoRepository<CarMongo, ObjectId> {
    List<CarMongo> findByProducer(String producer);
    List<CarMongo> findByPower(int value);
}
