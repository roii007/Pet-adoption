package com.example.controller;

import com.example.constant.ApplicationStatus;
import com.example.entity.Application;
import com.example.entity.Pet;
import com.example.entity.User;
import com.example.service.ApplicationService;
import com.example.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/applications", produces = "application/json;charset=UTF-8")
public class ApplicationApiController {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private PetService petService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Map<String, Object> params, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            Map<String, String> map = new HashMap<>();
            map.put("message", "未登录");
            return ResponseEntity.status(401).body(map);
        }

        Application application = new Application();
        application.setUserId(user.getId());
        application.setPetId((Integer) params.get("petId"));
        application.setApplicantName((String) params.get("applicantName"));
        application.setApplicantPhone((String) params.get("applicantPhone"));
        application.setApplicantAddress((String) params.get("applicantAddress"));
        application.setHouseType((String) params.get("houseType"));
        application.setHasExperience((Integer) params.get("hasExperience"));
        application.setReason((String) params.get("reason"));

        applicationService.create(application);
        Map<String, String> map = new HashMap<>();
        map.put("message", "申请提交成功");
        return ResponseEntity.ok(map);
    }

    @GetMapping("/my")
    public ResponseEntity<?> getByUser(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            Map<String, String> map = new HashMap<>();
            map.put("message", "未登录");
            return ResponseEntity.status(401).body(map);
        }

        List<Application> applications = applicationService.findByUserId(user.getId());
        applications.forEach(app -> {
            app.setStatus(ApplicationStatus.normalize(app.getStatus()));
            Pet pet = petService.findById(app.getPetId());
            if (pet != null) {
                app.setPetName(pet.getName());
            }
        });
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        Application application = applicationService.findById(id);
        if (application == null) {
            return ResponseEntity.notFound().build();
        }
        application.setStatus(ApplicationStatus.normalize(application.getStatus()));
        Pet pet = petService.findById(application.getPetId());
        if (pet != null) {
            application.setPetName(pet.getName());
        }
        return ResponseEntity.ok(application);
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancel(@PathVariable Integer id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            Map<String, String> map = new HashMap<>();
            map.put("message", "未登录");
            return ResponseEntity.status(401).body(map);
        }

        Application application = applicationService.findById(id);
        if (application == null) {
            return ResponseEntity.notFound().build();
        }
        if (!application.getUserId().equals(user.getId())) {
            Map<String, String> map = new HashMap<>();
            map.put("message", "无权操作");
            return ResponseEntity.status(403).body(map);
        }

        applicationService.cancelApplication(id);
        Map<String, String> map = new HashMap<>();
        map.put("message", "取消成功");
        return ResponseEntity.ok(map);
    }
}