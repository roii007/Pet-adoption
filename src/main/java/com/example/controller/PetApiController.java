package com.example.controller;

import com.example.entity.Pet;
import com.example.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/pets", produces = "application/json;charset=UTF-8")
public class PetApiController {

    @Autowired
    private PetService petService;

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(required = false) String type,
                                    @RequestParam(required = false) String breed,
                                    @RequestParam(required = false) String gender,
                                    @RequestParam(required = false) Integer vaccination,
                                    @RequestParam(required = false) String status) {
        try {
            Map<String, Object> params = new HashMap<>();

            if (type != null && !type.trim().isEmpty()) {
                params.put("type", type);
            }
            if (breed != null && !breed.trim().isEmpty()) {
                params.put("breed", breed);
            }
            if (gender != null && !gender.trim().isEmpty()) {
                params.put("gender", gender);
            }
            if (vaccination != null) {
                params.put("vaccination", vaccination);
            }
            if (status != null && !status.trim().isEmpty()) {
                params.put("status", status);
            } else {
                params.put("status", "上架");
            }

            List<Pet> pets = petService.search(params);
            if (pets == null) {
                pets = new ArrayList<>();
            }
            return ResponseEntity.ok(pets);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        try {
            Pet pet = petService.findById(id);
            if (pet == null) {
                return ResponseEntity.notFound().build();
            }
            petService.incrementViewCount(id);
            return ResponseEntity.ok(pet);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }
}