package com.example.controller;

import com.example.entity.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/user", produces = "application/json;charset=UTF-8")
public class UserApiController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> params, HttpSession session) {
        try {
            String username = params.get("username");
            String password = params.get("password");

            User user = userService.login(username, password);
            if (user != null) {
                session.setAttribute("user", user);
                String token = java.util.UUID.randomUUID().toString();
                session.setAttribute("token", token);

                user.setPassword(null);

                Map<String, Object> result = new HashMap<>();
                result.put("token", token);
                result.put("user", user);
                return ResponseEntity.ok(result);
            }

            Map<String, String> error = new HashMap<>();
            error.put("message", "用户名或密码错误");
            return ResponseEntity.status(401).body(error);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("message", "登录失败: " + e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            if (userService.checkUsernameExists(user.getUsername())) {
                Map<String, String> error = new HashMap<>();
                error.put("message", "用户名已存在");
                return ResponseEntity.badRequest().body(error);
            }
            userService.register(user);
            Map<String, String> result = new HashMap<>();
            result.put("message", "注册成功");
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("message", "注册失败: " + e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }

    @GetMapping("/info")
    public ResponseEntity<?> getInfo(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "未登录");
            return ResponseEntity.status(401).body(error);
        }
        user.setPassword(null);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody Map<String, Object> params, HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                Map<String, String> error = new HashMap<>();
                error.put("message", "未登录");
                return ResponseEntity.status(401).body(error);
            }

            if (params.containsKey("email")) {
                user.setEmail((String) params.get("email"));
            }
            if (params.containsKey("phone")) {
                user.setPhone((String) params.get("phone"));
            }
            if (params.containsKey("password")) {
                user.setPassword((String) params.get("password"));
            }

            userService.update(user);
            session.setAttribute("user", userService.findById(user.getId()));

            Map<String, String> result = new HashMap<>();
            result.put("message", "更新成功");
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("message", "更新失败: " + e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        Map<String, String> result = new HashMap<>();
        result.put("message", "退出成功");
        return ResponseEntity.ok(result);
    }
}