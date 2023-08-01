package ua.com.owu.dec2022springboot.dao.models;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ua.com.owu.dec2022springboot.views.Views;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Data
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(value = Views.Level1.class)
    private int id;

    @JsonView(value={Views.Level3.class,Views.Level1.class })
    private String name;

    @JsonView(value={Views.Level3.class,Views.Level1.class })
    private String password;

    @Column(unique = true)
    @JsonView(value={Views.Level3.class,Views.Level1.class })
    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @JsonView(value = Views.Level1.class)
    private List<Role> roles = List.of(Role.USER);

    @JsonView(value = Views.Level1.class)
    private boolean isActivated = false;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
            List<GrantedAuthority> authorities = new ArrayList<>();
            this.roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.name())));
            return authorities;
        }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    @Data
    @AllArgsConstructor
    @Builder
    public static class UserDTO {
        private String username;
        private String useremail;
        private String password;
    }
}
