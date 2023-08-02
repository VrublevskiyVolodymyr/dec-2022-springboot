package ua.com.owu.dec2022springboot.models;

import jakarta.persistence.Id;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document
public class CarMongo {
    @Id
    private ObjectId id;

    private String producer;

    private String model;

    private int power;

    private int userId;

    private String photo;


    public CarMongo(String producer, String model,  int power, int userId) {
        this.producer = producer;
        this.model = model;
        this.power = power;
        this.userId = userId;
    }
}
