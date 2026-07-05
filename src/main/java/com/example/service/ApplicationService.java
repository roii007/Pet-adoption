
package com.example.service;

import com.example.entity.Application;

import java.util.List;

public interface ApplicationService {
    Application findById(Integer id);
    List<Application> findAll();
    List<Application> findByUserId(Integer userId);
    List<Application> findByPetId(Integer petId);
    List<Application> findByStatus(String status);
    Application create(Application application);
    Application update(Application application);
    void deleteById(Integer id);
    void cancelApplication(Integer id);
    void approveApplication(Integer id, Integer reviewerId);
    void rejectApplication(Integer id, Integer reviewerId, String comment);
    int countByStatus(String status);
    boolean hasPendingApplication(Integer userId, Integer petId);
}
