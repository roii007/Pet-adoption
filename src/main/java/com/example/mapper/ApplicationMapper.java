
package com.example.mapper;

import com.example.entity.Application;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ApplicationMapper {
    Application findById(Integer id);
    List<Application> findAll();
    List<Application> findByUserId(@Param("userId") Integer userId);
    List<Application> findByPetId(@Param("petId") Integer petId);
    List<Application> findByStatus(@Param("status") String status);
    Application findPendingApplication(@Param("userId") Integer userId, @Param("petId") Integer petId);
    int insert(Application application);
    int update(Application application);
    int updateStatus(@Param("id") Integer id, @Param("status") String status);
    int deleteById(Integer id);
    int countByStatus(@Param("status") String status);
}
