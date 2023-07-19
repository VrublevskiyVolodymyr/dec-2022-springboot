package ua.com.owu.dec2022springboot.services;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import ua.com.owu.dec2022springboot.dao.UserDAO;
import ua.com.owu.dec2022springboot.models.User;
import ua.com.owu.dec2022springboot.models.UserDTO;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserService {
    private UserDAO userDAO;
    private MailService mailService;

    public void saveUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getUsername());
        user.setEmail(userDTO.getUseremail());
        userDAO.save(user);

        String email = user.getEmail();
        String body = "Hello user  " + user.getName() + " to activate your account click <a href='http://localhost:8080/users/activate/" + user.getId() + " '>here</a>";
        mailService.sendEmail(email, body);
    }

    public ResponseEntity<List<User>> getAllUsers() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("customHeader", "custom value");

        return new ResponseEntity<>(userDAO.findAll(), httpHeaders, HttpStatus.ACCEPTED);
    }

    public ResponseEntity<User> getUserById(int id) {
        if (id < 0) {
            throw new RuntimeException();
        }
        return new ResponseEntity<>(userDAO.findById(id).get(), HttpStatusCode.valueOf(200));
    }

    public  void deleteById(int userId) {
        userDAO.deleteById(userId);
    }

    public ResponseEntity<String> activateUser(@PathVariable int id) {
        ResponseEntity<User> userById = this.getUserById(id);
        User user = userById.getBody();
        assert user != null;
        user.setActivated(true);
        userDAO.save(user);
        String massage = "<h1> Your account has been activated :) successfully, you can close this window <h1> ";
        return new ResponseEntity<>(massage, HttpStatus.OK);
    }
}
