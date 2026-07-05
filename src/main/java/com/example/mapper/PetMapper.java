
package com.example.mapper;

import com.example.entity.Pet;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PetMapper {
    Pet findById(Integer id);
    List<Pet> findAll();
    List<Pet> findByStatus(@Param("status") String status);
    List<Pet> search(Map<String, Object> params);
    int insert(Pet pet);
    int update(Pet pet);
    int deleteById(Integer id);
    int updateStatus(@Param("id") Integer id, @Param("status") String status);
    int incrementViewCount(Integer id);
    int countByStatus(@Param("status") String status);
    List<Pet> findByUserId(@Param("userId") Integer userId);
}
