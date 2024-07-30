package com.register.application.service;

import com.register.application.dao.UserDAO;
import com.register.application.dto.UserDTO;

import com.register.application.entity.User;
import com.register.application.exception.UserAlreadyExistsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void createUser(UserDTO userDTO) {
        UserDetails foundedUser = userDAO.findByUsername(userDTO.username());
        if (foundedUser != null) {
            throw new UserAlreadyExistsException();
        }
        User user = new User();
        user.setUsername(userDTO.username());
        user.setPassword(encodePassword(userDTO.password()));
        user.setRole(userDTO.role());
        userDAO.save(user);
    }

    public User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userDAO.findByUsername(username);
    }

    private String encodePassword(String rawPassword) {
        return new BCryptPasswordEncoder().encode(rawPassword);
    }
}
