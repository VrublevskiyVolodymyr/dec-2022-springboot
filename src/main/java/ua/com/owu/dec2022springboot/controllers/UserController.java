package ua.com.owu.dec2022springboot.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.owu.dec2022springboot.dao.UserDAO;
import ua.com.owu.dec2022springboot.models.User;
import ua.com.owu.dec2022springboot.models.UserDTO;
import ua.com.owu.dec2022springboot.services.UserService;

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

    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
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
