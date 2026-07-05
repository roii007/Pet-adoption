
package com.example.controller;

import com.example.entity.Pet;
import com.example.entity.User;
import com.example.service.ApplicationService;
import com.example.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private PetService petService;

    @Autowired
    private ApplicationService applicationService;
    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "中文测试：宠物领养系统";
    }
    @GetMapping("/list")
    public String list(@RequestParam(defaultValue = "") String type,
                       @RequestParam(defaultValue = "") String breed,
                       @RequestParam(defaultValue = "") String gender,
                       @RequestParam(defaultValue = "0") Integer vaccination,
                       Model model) {
        Map<String, Object> params = new HashMap<>();
        if (!type.isEmpty()) params.put("type", type);
        if (!breed.isEmpty()) params.put("breed", breed);
        if (!gender.isEmpty()) params.put("gender", gender);
        if (vaccination != null && vaccination >= 0) params.put("vaccination", vaccination);

        List<Pet> pets;
        if (params.isEmpty()) {
            pets = petService.findOnSale();
        } else {
            pets = petService.search(params);
        }
        model.addAttribute("pets", pets);
        model.addAttribute("types", new String[]{"狗", "猫", "兔", "其他"});
        model.addAttribute("genders", new String[]{"公", "母"});
        model.addAttribute("type", type);
        model.addAttribute("breed", breed);
        model.addAttribute("gender", gender);
        model.addAttribute("vaccination", vaccination);
        return "pet/list";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Integer id, HttpSession session, Model model) {
        Pet pet = petService.findById(id);
        if (pet == null) {
            model.addAttribute("error", "宠物不存在");
            return "error";
        }
        petService.incrementViewCount(id);

        User user = (User) session.getAttribute("user");
        boolean canApply = false;
        boolean hasApplied = false;

        if (user != null && pet.isOnSale()) {
            canApply = true;
            hasApplied = applicationService.hasPendingApplication(user.getId(), id);
        }

        model.addAttribute("pet", pet);
        model.addAttribute("canApply", canApply);
        model.addAttribute("hasApplied", hasApplied);
        return "pet/detail";
    }
 
    @GetMapping("/apply/{petId}")
    public String showApplyForm(@PathVariable Integer petId, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/user/login";
        }
        Pet pet = petService.findById(petId);
        if (pet == null || !pet.isOnSale()) {
            model.addAttribute("error", "宠物不存在或已被领养");
            return "error";
        }
        if (applicationService.hasPendingApplication(user.getId(), petId)) {
            model.addAttribute("error", "您已提交过申请");
            return "error";
        }
        model.addAttribute("pet", pet);
        model.addAttribute("user", user);
        return "pet/apply";
    }
}
