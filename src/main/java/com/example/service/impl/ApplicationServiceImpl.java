
package com.example.service.impl;

import com.example.constant.ApplicationStatus;
import com.example.entity.Application;
import com.example.entity.Pet;
import com.example.mapper.ApplicationMapper;
import com.example.mapper.PetMapper;
import com.example.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private ApplicationMapper applicationMapper;

    @Autowired
    private PetMapper petMapper;

    @Override
    public Application findById(Integer id) {
        return applicationMapper.findById(id);
    }

    @Override
    public List<Application> findAll() {
        return applicationMapper.findAll();
    }

    @Override
    public List<Application> findByUserId(Integer userId) {
        return applicationMapper.findByUserId(userId);
    }

    @Override
    public List<Application> findByPetId(Integer petId) {
        return applicationMapper.findByPetId(petId);
    }

    @Override
    public List<Application> findByStatus(String status) {
        return applicationMapper.findByStatus(status);
    }

    @Override
    @Transactional
    public Application create(Application application) {
        application.setStatus(ApplicationStatus.PENDING);
        applicationMapper.insert(application);
        return applicationMapper.findById(application.getId());
    }

    @Override
    @Transactional
    public Application update(Application application) {
        applicationMapper.update(application);
        return applicationMapper.findById(application.getId());
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        applicationMapper.deleteById(id);
    }

    @Override
    @Transactional
    public void cancelApplication(Integer id) {
        applicationMapper.updateStatus(id, ApplicationStatus.CANCELLED);
    }

    @Override
    @Transactional
    public void approveApplication(Integer id, Integer reviewerId) {
        Application application = applicationMapper.findById(id);
        if (application != null && ApplicationStatus.isPending(application.getStatus())) {
            application.setStatus(ApplicationStatus.APPROVED);
            application.setReviewerId(reviewerId);
            application.setReviewedAt(new Date());
            applicationMapper.update(application);

            petMapper.updateStatus(application.getPetId(), "\u5df2\u9886\u517b");
        }
    }

    @Override
    @Transactional
    public void rejectApplication(Integer id, Integer reviewerId, String comment) {
        Application application = applicationMapper.findById(id);
        if (application != null && ApplicationStatus.isPending(application.getStatus())) {
            application.setStatus(ApplicationStatus.REJECTED);
            application.setReviewerId(reviewerId);
            application.setReviewComment(comment);
            application.setReviewedAt(new Date());
            applicationMapper.update(application);
        }
    }

    @Override
    public int countByStatus(String status) {
        return applicationMapper.countByStatus(status);
    }

    @Override
    public boolean hasPendingApplication(Integer userId, Integer petId) {
        return applicationMapper.findPendingApplication(userId, petId) != null;
    }
}
