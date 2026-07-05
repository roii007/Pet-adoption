
package com.example.controller;

import com.example.entity.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegisterForm() {
        return "user/register";
    }

    @PostMapping("/register")
    public String register(User user, Model model) {
        if (userService.checkUsernameExists(user.getUsername())) {
            model.addAttribute("error", "用户名已存在");
            return "user/register";
        }
        if (userService.checkEmailExists(user.getEmail())) {
            model.addAttribute("error", "邮箱已被注册");
            return "user/register";
        }
        userService.register(user);
        model.addAttribute("success", "注册成功，请登录");
        return "user/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(String username, String password, HttpSession session, Model model) {
        User user = userService.login(username, password);
        if (user != null) {
            session.setAttribute("user", user);
            if (user.isAdmin()) {
                return "redirect:/admin/index";
            }
            return "redirect:/";
        }
        model.addAttribute("error", "用户名或密码错误");
        return "user/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/profile")
    public String showProfile(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/user/login";
        }
        model.addAttribute("user", user);
        return "user/profile";
    }

    @PostMapping("/profile")
    public String updateProfile(User user, HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/user/login";
        }
        user.setId(currentUser.getId());
        user.setPassword(currentUser.getPassword());
        user.setRole(currentUser.getRole());
        userService.update(user);
        session.setAttribute("user", userService.findById(currentUser.getId()));
        model.addAttribute("success", "更新成功");
        return "user/profile";
    }
}
