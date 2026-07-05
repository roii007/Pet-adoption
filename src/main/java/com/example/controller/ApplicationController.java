
package com.example.controller;

import com.example.entity.Application;
import com.example.entity.Pet;
import com.example.entity.User;
import com.example.service.ApplicationService;
import com.example.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/application")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private PetService petService;

    @PostMapping("/submit")
    public String submit(Application application, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/user/login";
        }

        Pet pet = petService.findById(application.getPetId());
        if (pet == null || !pet.isOnSale()) {
            model.addAttribute("error", "宠物不存在或已被领养");
            return "error";
        }

        if (applicationService.hasPendingApplication(user.getId(), application.getPetId())) {
            model.addAttribute("error", "您已提交过申请");
            return "error";
        }

        application.setUserId(user.getId());
        application.setApplicantName(user.getUsername());
        applicationService.create(application);

        model.addAttribute("success", "申请提交成功，请等待管理员审核");
        return "application/success";
    }

    @GetMapping("/my")
    public String myApplications(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/user/login";
        }
        List<Application> applications = applicationService.findByUserId(user.getId());
        for (Application app : applications) {
            Pet pet = petService.findById(app.getPetId());
            app.setPet(pet);
        }
        model.addAttribute("applications", applications);
        return "application/my";
    }

    @GetMapping("/cancel/{id}")
    public String cancel(@PathVariable Integer id, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/user/login";
        }
        Application application = applicationService.findById(id);
        if (application == null || !application.getUserId().equals(user.getId())) {
            model.addAttribute("error", "无权操作");
            return "error";
        }
        if (!application.isPending()) {
            model.addAttribute("error", "只能取消待审核的申请");
            return "error";
        }
        applicationService.cancelApplication(id);
        return "redirect:/application/my";
    }
}
