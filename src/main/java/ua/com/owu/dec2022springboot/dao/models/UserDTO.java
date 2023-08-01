package ua.com.owu.dec2022springboot.dao.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private String username;
    private String useremail;
    private String password;

    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
