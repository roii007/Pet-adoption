
package com.example.service;

import com.example.entity.User;

import java.util.List;

public interface UserService {
    User login(String username, String password);
    User register(User user);
    User findById(Integer id);
    User findByUsername(String username);
    User findByEmail(String email);
    User update(User user);
    void deleteById(Integer id);
    List<User> findAll();
    List<User> findByRole(String role);
    void updateStatus(Integer id, Integer status);
    boolean checkUsernameExists(String username);
    boolean checkEmailExists(String email);
}
