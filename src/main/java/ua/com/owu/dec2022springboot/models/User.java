package ua.com.owu.dec2022springboot.models;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.*;
import ua.com.owu.dec2022springboot.views.Views;

@Entity
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(value = Views.Level1.class)
    private int id;

    @JsonView(value={Views.Level3.class,Views.Level1.class })
    private String name;

    @Column(unique = true)
    @JsonView(value={Views.Level3.class,Views.Level1.class })
    private String email;

    @JsonView(value = Views.Level1.class)
    private boolean isActivated = false;
}
