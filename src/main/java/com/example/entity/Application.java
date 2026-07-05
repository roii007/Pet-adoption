package com.example.entity;

import com.example.constant.ApplicationStatus;

import java.util.Date;

public class Application {
    private Integer id;
    private Integer userId;
    private Integer petId;
    private String applicantName;
    private String applicantPhone;
    private String applicantAddress;
    private String houseType;
    private Integer hasExperience;
    private String reason;
    private String status;
    private Integer reviewerId;
    private String reviewComment;
    private Date reviewedAt;
    private Date createdAt;

    private User user;
    private Pet pet;
    private User reviewer;
    private String petName;

    public Application() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPetId() {
        return petId;
    }

    public void setPetId(Integer petId) {
        this.petId = petId;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getApplicantPhone() {
        return applicantPhone;
    }

    public void setApplicantPhone(String applicantPhone) {
        this.applicantPhone = applicantPhone;
    }

    public String getApplicantAddress() {
        return applicantAddress;
    }

    public void setApplicantAddress(String applicantAddress) {
        this.applicantAddress = applicantAddress;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public Integer getHasExperience() {
        return hasExperience;
    }

    public void setHasExperience(Integer hasExperience) {
        this.hasExperience = hasExperience;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(Integer reviewerId) {
        this.reviewerId = reviewerId;
    }

    public String getReviewComment() {
        return reviewComment;
    }

    public void setReviewComment(String reviewComment) {
        this.reviewComment = reviewComment;
    }

    public Date getReviewedAt() {
        return reviewedAt;
    }

    public void setReviewedAt(Date reviewedAt) {
        this.reviewedAt = reviewedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public User getReviewer() {
        return reviewer;
    }

    public void setReviewer(User reviewer) {
        this.reviewer = reviewer;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public boolean isPending() {
        return ApplicationStatus.isPending(status);
    }

    public boolean isApproved() {
        return ApplicationStatus.isApproved(status);
    }

    public boolean isRejected() {
        return ApplicationStatus.isRejected(status);
    }

    public boolean isCancelled() {
        return ApplicationStatus.isCancelled(status);
    }

    public String getStatusText() {
        return ApplicationStatus.toText(status);
    }
}