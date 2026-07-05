
package com.example.service;

import com.example.entity.Pet;

import java.util.List;
import java.util.Map;

public interface PetService {
    Pet findById(Integer id);
    List<Pet> findAll();
    List<Pet> findOnSale();
    List<Pet> search(Map<String, Object> params);
    Pet create(Pet pet);
    Pet update(Pet pet);
    void deleteById(Integer id);
    void updateStatus(Integer id, String status);
    void incrementViewCount(Integer id);
    int countByStatus(String status);
    List<Pet> findByUserId(Integer userId);
}
