package com.example.service.impl;

import com.example.entity.Pet;
import com.example.mapper.PetMapper;
import com.example.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PetServiceImpl implements PetService {

    @Autowired
    private PetMapper petMapper;

    @Override
    public Pet findById(Integer id) {
        if (id == null) return null;
        return petMapper.findById(id);
    }

    @Override
    public List<Pet> findAll() {
        List<Pet> pets = petMapper.findAll();
        return pets == null ? new ArrayList<>() : pets;
    }

    @Override
    public List<Pet> findOnSale() {
        List<Pet> pets = petMapper.findByStatus("上架");
        return pets == null ? new ArrayList<>() : pets;
    }

    @Override
    public List<Pet> search(Map<String, Object> params) {
        if (params == null) {
            params = new HashMap<>();
        }
        List<Pet> pets = petMapper.search(params);
        return pets == null ? new ArrayList<>() : pets;
    }

    @Override
    @Transactional
    public Pet create(Pet pet) {
        if (pet.getStatus() == null) {
            pet.setStatus("上架");
        }
        if (pet.getImageUrls() == null) {
            pet.setImageUrls("[]");
        }
        petMapper.insert(pet);
        return petMapper.findById(pet.getId());
    }

    @Override
    @Transactional
    public Pet update(Pet pet) {
        petMapper.update(pet);
        return petMapper.findById(pet.getId());
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        petMapper.deleteById(id);
    }

    @Override
    @Transactional
    public void updateStatus(Integer id, String status) {
        petMapper.updateStatus(id, status);
    }

    @Override
    @Transactional
    public void incrementViewCount(Integer id) {
        petMapper.incrementViewCount(id);
    }

    @Override
    public int countByStatus(String status) {
        Integer count = petMapper.countByStatus(status);
        return count == null ? 0 : count;
    }

    @Override
    public List<Pet> findByUserId(Integer userId) {
        List<Pet> pets = petMapper.findByUserId(userId);
        return pets == null ? new ArrayList<>() : pets;
    }
}