
package com.example.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Pet {
    private Integer id;
    private String name;
    private String type;
    private String breed;
    private Integer age;
    private String gender;
    private String color;
    private BigDecimal weight;
    private String healthStatus;
    private Integer vaccination;
    private String description;
    private String imageUrls;
    private String status;
    private Integer viewCount;
    private Integer userId;
    private Date createdAt;

    public Pet() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    public Integer getVaccination() {
        return vaccination;
    }

    public void setVaccination(Integer vaccination) {
        this.vaccination = vaccination;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(String imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isOnSale() {
        return "上架".equals(status);
    }

    public boolean isAdopted() {
        return "已领养".equals(status);
    }

    public String getAgeText() {
        if (age == null || age <= 0) {
            return "未知";
        }
        if (age < 12) {
            return age + "个月";
        } else {
            return (age / 12) + "岁" + (age % 12 > 0 ? (age % 12) + "个月" : "");
        }
    }
}
