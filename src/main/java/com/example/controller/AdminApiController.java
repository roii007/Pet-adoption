package com.example.controller;

import com.example.constant.ApplicationStatus;
import com.example.entity.Application;
import com.example.entity.Pet;
import com.example.entity.User;
import com.example.service.ApplicationService;
import com.example.service.PetService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminApiController {

    @Autowired
    private UserService userService;

    @Autowired
    private PetService petService;

    @Autowired
    private ApplicationService applicationService;

    private ResponseEntity<?> checkAdmin(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            Map<String, String> map = new HashMap<>();
            map.put("message", "未登录");
            return ResponseEntity.status(401).body(map);
        }
        if (!"admin".equals(user.getRole())) {
            Map<String, String> map = new HashMap<>();
            map.put("message", "无权访问");
            return ResponseEntity.status(403).body(map);
        }
        return null;
    }

    @GetMapping("/pets")
    public ResponseEntity<?> getAllPets(HttpSession session) {
        ResponseEntity<?> error = checkAdmin(session);
        if (error != null) return error;

        List<Pet> pets = petService.findAll();
        return ResponseEntity.ok(pets);
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(HttpSession session) {
        ResponseEntity<?> error = checkAdmin(session);
        if (error != null) return error;
        
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/users/{id}/status")
    public ResponseEntity<?> updateUserStatus(@PathVariable Integer id, @RequestBody Map<String, Integer> params, HttpSession session) {
        ResponseEntity<?> error = checkAdmin(session);
        if (error != null) return error;
        
        userService.updateStatus(id, params.get("status"));
        Map<String, String> map = new HashMap<>();
        map.put("message", "更新成功");
        return ResponseEntity.ok(map);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id, HttpSession session) {
        ResponseEntity<?> error = checkAdmin(session);
        if (error != null) return error;
        
        userService.deleteById(id);
        Map<String, String> map = new HashMap<>();
        map.put("message", "删除成功");
        return ResponseEntity.ok(map);
    }

    @PostMapping("/pets")
    public ResponseEntity<?> createPet(@RequestBody Map<String, Object> params, HttpSession session) {
        // 打印接收到的参数（调试用）
        System.out.println("===== 接收到的参数 =====");
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue() + " (type: " +
                    (entry.getValue() != null ? entry.getValue().getClass().getSimpleName() : "null") + ")");
        }
        System.out.println("========================");
        ResponseEntity<?> error = checkAdmin(session);
        if (error != null) return error;

        try {
            Pet pet = new Pet();

            // 安全获取字符串值
            pet.setName(getStringValue(params, "name"));
            pet.setType(getStringValue(params, "type"));
            pet.setBreed(getStringValue(params, "breed"));
            pet.setGender(getStringValue(params, "gender"));
            pet.setColor(getStringValue(params, "color"));
            pet.setHealthStatus(getStringValue(params, "healthStatus"));
            pet.setDescription(getStringValue(params, "description"));

            // 安全获取整数
            Integer age = getIntegerValue(params, "age");
            pet.setAge(age != null ? age : 0);

            Integer vaccination = getIntegerValue(params, "vaccination");
            pet.setVaccination(vaccination != null ? vaccination : 0);

            // 安全获取体重
            Object weightObj = params.get("weight");
            if (weightObj != null) {
                try {
                    if (weightObj instanceof Number) {
                        pet.setWeight(new java.math.BigDecimal(((Number) weightObj).doubleValue()));
                    } else if (weightObj instanceof String) {
                        String weightStr = (String) weightObj;
                        if (!weightStr.trim().isEmpty()) {
                            pet.setWeight(new java.math.BigDecimal(weightStr));
                        }
                    }
                } catch (Exception e) {
                    // 体重解析失败，使用默认值
                    pet.setWeight(java.math.BigDecimal.ZERO);
                }
            }

            // 设置默认状态
            String status = getStringValue(params, "status");
            pet.setStatus(status != null && !status.isEmpty() ? status : "上架");

            // 处理图片URLs
            Object imageUrlsObj = params.get("imageUrls");
            if (imageUrlsObj != null) {
                if (imageUrlsObj instanceof String) {
                    pet.setImageUrls((String) imageUrlsObj);
                } else {
                    pet.setImageUrls("[]");
                }
            } else {
                pet.setImageUrls("[]");
            }

            // 验证必填字段
            if (pet.getName() == null || pet.getName().trim().isEmpty()) {
                Map<String, String> map = new HashMap<>();
                map.put("message", "宠物名称不能为空");
                return ResponseEntity.badRequest().body(map);
            }

            petService.create(pet);

            Map<String, String> map = new HashMap<>();
            map.put("message", "创建成功");
            return ResponseEntity.ok(map);

        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> map = new HashMap<>();
            map.put("message", "创建失败: " + e.getMessage());
            return ResponseEntity.status(500).body(map);
        }
    }

    // 辅助方法：安全获取字符串
    private String getStringValue(Map<String, Object> params, String key) {
        Object value = params.get(key);
        if (value == null) return null;
        return value.toString();
    }

    // 辅助方法：安全获取整数
    private Integer getIntegerValue(Map<String, Object> params, String key) {
        Object value = params.get(key);
        if (value == null) return null;
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        try {
            return Integer.parseInt(value.toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }
    @PutMapping("/pets/{id}")
    public ResponseEntity<?> updatePet(@PathVariable Integer id, @RequestBody Map<String, Object> params, HttpSession session) {
        ResponseEntity<?> error = checkAdmin(session);
        if (error != null) return error;

        try {
            Pet pet = petService.findById(id);
            if (pet == null) {
                return ResponseEntity.notFound().build();
            }

            if (params.containsKey("name")) pet.setName(getStringValue(params, "name"));
            if (params.containsKey("type")) pet.setType(getStringValue(params, "type"));
            if (params.containsKey("breed")) pet.setBreed(getStringValue(params, "breed"));
            if (params.containsKey("age")) {
                Integer age = getIntegerValue(params, "age");
                if (age != null) pet.setAge(age);
            }
            if (params.containsKey("gender")) pet.setGender(getStringValue(params, "gender"));
            if (params.containsKey("color")) pet.setColor(getStringValue(params, "color"));
            if (params.containsKey("healthStatus")) pet.setHealthStatus(getStringValue(params, "healthStatus"));
            if (params.containsKey("description")) pet.setDescription(getStringValue(params, "description"));

            // 处理体重
            if (params.containsKey("weight")) {
                Object weightObj = params.get("weight");
                if (weightObj != null) {
                    try {
                        if (weightObj instanceof Number) {
                            pet.setWeight(new java.math.BigDecimal(((Number) weightObj).doubleValue()));
                        } else if (weightObj instanceof String) {
                            String weightStr = (String) weightObj;
                            if (!weightStr.trim().isEmpty()) {
                                pet.setWeight(new java.math.BigDecimal(weightStr));
                            }
                        }
                    } catch (Exception e) {
                        // 忽略体重转换错误
                    }
                }
            }

            if (params.containsKey("vaccination")) {
                Integer vaccination = getIntegerValue(params, "vaccination");
                if (vaccination != null) pet.setVaccination(vaccination);
            }

            if (params.containsKey("status")) pet.setStatus(getStringValue(params, "status"));

            if (params.containsKey("imageUrls")) {
                Object imageUrlsObj = params.get("imageUrls");
                if (imageUrlsObj != null) {
                    pet.setImageUrls(imageUrlsObj.toString());
                }
            }

            petService.update(pet);

            Map<String, String> map = new HashMap<>();
            map.put("message", "更新成功");
            return ResponseEntity.ok(map);

        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> map = new HashMap<>();
            map.put("message", "更新失败: " + e.getMessage());
            return ResponseEntity.status(500).body(map);
        }
    }
    @PutMapping("/pets/{id}/status")
    public ResponseEntity<?> updatePetStatus(@PathVariable Integer id, @RequestBody Map<String, String> params, HttpSession session) {
        ResponseEntity<?> error = checkAdmin(session);
        if (error != null) return error;
        
        petService.updateStatus(id, params.get("status"));
        Map<String, String> map = new HashMap<>();
        map.put("message", "更新成功");
        return ResponseEntity.ok(map);
    }

    @DeleteMapping("/pets/{id}")
    public ResponseEntity<?> deletePet(@PathVariable Integer id, HttpSession session) {
        ResponseEntity<?> error = checkAdmin(session);
        if (error != null) return error;
        
        petService.deleteById(id);
        Map<String, String> map = new HashMap<>();
        map.put("message", "删除成功");
        return ResponseEntity.ok(map);
    }

    @GetMapping("/applications")
    public ResponseEntity<?> getAllApplications(HttpSession session) {
        ResponseEntity<?> error = checkAdmin(session);
        if (error != null) return error;
        
        List<Application> applications = applicationService.findAll();
        applications.forEach(app -> {
            app.setStatus(ApplicationStatus.normalize(app.getStatus()));
            Pet pet = petService.findById(app.getPetId());
            if (pet != null) {
                app.setPetName(pet.getName());
            }
        });
        return ResponseEntity.ok(applications);
    }

    @PutMapping("/applications/{id}/status")
    public ResponseEntity<?> updateApplicationStatus(@PathVariable Integer id, @RequestBody Map<String, String> params, HttpSession session) {
        ResponseEntity<?> error = checkAdmin(session);
        if (error != null) return error;
        
        String status = ApplicationStatus.normalize(params.get("status"));
        String comment = params.get("comment");
        
        Application app = applicationService.findById(id);
        if (app == null) {
            return ResponseEntity.notFound().build();
        }
        
        User admin = (User) session.getAttribute("user");
        
        if (ApplicationStatus.APPROVED.equals(status)) {
            applicationService.approveApplication(id, admin.getId());
            petService.updateStatus(app.getPetId(), "\u5df2\u9886\u517b");
        } else if (ApplicationStatus.REJECTED.equals(status)) {
            applicationService.rejectApplication(id, admin.getId(), comment);
        }
        
        Map<String, String> map = new HashMap<>();
        map.put("message", "更新成功");
        return ResponseEntity.ok(map);
    }
}