package ua.com.owu.dec2022springboot.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.owu.dec2022springboot.dao.models.User;

public interface UserDAO extends JpaRepository <User,Integer>{
    User findByEmail(String email);
}
