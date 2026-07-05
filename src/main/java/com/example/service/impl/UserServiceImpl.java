package com.example.service.impl;

import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String username, String password) {
        if (username == null || password == null) {
            return null;
        }
        User user = userMapper.findByUsername(username);
        if (user != null && user.getStatus() != null && user.getStatus() == 1) {
            String hashedPassword = md5(password);
            if (hashedPassword.equals(user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    @Override
    @Transactional
    public User register(User user) {
        user.setPassword(md5(user.getPassword()));
        user.setRole("user");
        user.setStatus(1);
        userMapper.insert(user);
        return user;
    }

    @Override
    public User findById(Integer id) {
        if (id == null) return null;
        return userMapper.findById(id);
    }

    @Override
    public User findByUsername(String username) {
        if (username == null) return null;
        return userMapper.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        if (email == null) return null;
        return userMapper.findByEmail(email);
    }

    @Override
    @Transactional
    public User update(User user) {
        userMapper.update(user);
        return userMapper.findById(user.getId());
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        userMapper.deleteById(id);
    }

    @Override
    public List<User> findAll() {
        List<User> users = userMapper.findAll();
        return users == null ? new ArrayList<>() : users;
    }

    @Override
    public List<User> findByRole(String role) {
        List<User> users = userMapper.findByRole(role);
        return users == null ? new ArrayList<>() : users;
    }

    @Override
    @Transactional
    public void updateStatus(Integer id, Integer status) {
        userMapper.updateStatus(id, status);
    }

    @Override
    public boolean checkUsernameExists(String username) {
        return userMapper.findByUsername(username) != null;
    }

    @Override
    public boolean checkEmailExists(String email) {
        return userMapper.findByEmail(email) != null;
    }

    private String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}