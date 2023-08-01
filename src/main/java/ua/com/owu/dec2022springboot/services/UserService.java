package ua.com.owu.dec2022springboot.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import ua.com.owu.dec2022springboot.dao.UserDAO;
import ua.com.owu.dec2022springboot.dao.models.User;
import ua.com.owu.dec2022springboot.dao.models.UserDTO;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private UserDAO userDAO;
    private MailService mailService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserService(UserDAO userDAO, MailService mailService, @Lazy AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.mailService=mailService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        User byEmail = userDAO.findByEmail(username);
        System.out.println(byEmail);
        return userDAO.findByEmail(username);
    }

    public void saveUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getUsername());
        user.setEmail(userDTO.getUseremail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userDAO.save(user);

        String email = user.getEmail();
        String body = "Hello user  " + user.getName() + " to activate your account click <a href='http://localhost:8080/users/activate/" + user.getId() + " '>here</a>";
        mailService.sendEmail(email, body, Optional.empty());
    }

    public ResponseEntity<String> login(UserDTO userDTO) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword());
        System.out.println(usernamePasswordAuthenticationToken);
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        System.out.println(authenticate);
        if (authenticate != null) {
            String jwtToken = Jwts.builder()
                    .setSubject(authenticate.getName())
                    .signWith(SignatureAlgorithm.HS256, "okten".getBytes(StandardCharsets.UTF_8))
                    .compact();
            System.out.println(jwtToken);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Authorization", "Bearer " + jwtToken);
            return new ResponseEntity<>("login :)", httpHeaders, HttpStatus.OK);
        }
        return new ResponseEntity<>("bad credentials", HttpStatus.FORBIDDEN);
    }

    public ResponseEntity<List<User>> getAllUsers() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("custom header", "custom value");

        return new ResponseEntity<>(userDAO.findAll(), httpHeaders, HttpStatus.ACCEPTED);
    }

    public ResponseEntity<List<User>> getUsers() {
        System.out.println(userDAO.findAll());
        return new ResponseEntity<>(userDAO.findAll(), HttpStatus.OK);

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
