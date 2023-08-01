package ua.com.owu.dec2022springboot.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.owu.dec2022springboot.dao.UserDAO;
import ua.com.owu.dec2022springboot.dao.models.User;
import ua.com.owu.dec2022springboot.dao.models.UserDTO;
import ua.com.owu.dec2022springboot.services.UserService;
import ua.com.owu.dec2022springboot.views.Views;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/users")
public class UserController {

    private UserDAO userDAO;
    private UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/save")
    public void saveClient(@RequestBody UserDTO userDTO) {
        userService.saveUser(userDTO);
    }
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDTO userDTO) {
        return userService.login(userDTO);
    }

    //ADMIN
    @GetMapping("/admin/all")
    @JsonView(value = Views.Level1.class)
    public ResponseEntity<List<User>> getAllClients() {
        System.out.println("gone to users/admin/all");
        return userService.getUsers();
    }
    //OPEN
    @GetMapping()
    @JsonView(value = Views.Level3.class)
    public ResponseEntity<List<User>> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    @JsonView(value = Views.Level1.class)
    public ResponseEntity<User> findUserById(@PathVariable() int id) {
        return userService.getUserById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping()
    public void deleteUser(@RequestParam("id") int userId) {
        userService.deleteById(userId);
    }

    @GetMapping("/activate/{id}")
    public ResponseEntity<String> activateUser(@PathVariable int id) {
       return userService.activateUser(id);
    }
}
