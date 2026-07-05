
package com.example.mapper;

import com.example.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    User findById(Integer id);
    User findByUsername(String username);
    User findByEmail(String email);
    int insert(User user);
    int update(User user);
    int deleteById(Integer id);
    List<User> findAll();
    List<User> findByRole(@Param("role") String role);
    int updateStatus(@Param("id") Integer id, @Param("status") Integer status);
}
