package com.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/api/upload")
public class UploadController {

    // 图片存储目录
    private static final String UPLOAD_DIR = "D:/JavaEE期末项目/img/";

    @PostMapping("/image")
    @ResponseBody
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file, HttpSession session) {
        Map<String, Object> result = new HashMap<>();

        // 检查登录
        if (session.getAttribute("user") == null) {
            result.put("message", "请先登录");
            return ResponseEntity.status(401).body(result);
        }

        // 检查文件
        if (file.isEmpty()) {
            result.put("message", "请选择文件");
            return ResponseEntity.badRequest().body(result);
        }

        // 检查文件类型
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        if (!extension.matches("\\.(jpg|jpeg|png|gif|webp)$")) {
            result.put("message", "只支持图片格式: jpg, jpeg, png, gif, webp");
            return ResponseEntity.badRequest().body(result);
        }

        try {
            // 创建目录（如果不存在）
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // 生成唯一文件名
            String datePath = new SimpleDateFormat("yyyy/MM/dd/").format(new Date());
            File dateDir = new File(UPLOAD_DIR + datePath);
            if (!dateDir.exists()) {
                dateDir.mkdirs();
            }

            String newFilename = UUID.randomUUID().toString().replace("-", "") + extension;
            String relativePath = datePath + newFilename;
            File destFile = new File(uploadDir, relativePath);

            // 保存文件
            file.transferTo(destFile);

            // 返回可访问的URL
            String fileUrl = "/uploads/" + relativePath.replace("\\", "/");
            result.put("success", true);
            result.put("url", fileUrl);
            result.put("message", "上传成功");
            return ResponseEntity.ok(result);

        } catch (IOException e) {
            e.printStackTrace();
            result.put("message", "上传失败: " + e.getMessage());
            return ResponseEntity.status(500).body(result);
        }
    }

    // 批量上传
    @PostMapping("/images")
    @ResponseBody
    public ResponseEntity<?> uploadImages(@RequestParam("files") MultipartFile[] files, HttpSession session) {
        Map<String, Object> result = new HashMap<>();

        if (session.getAttribute("user") == null) {
            result.put("message", "请先登录");
            return ResponseEntity.status(401).body(result);
        }

        java.util.List<String> urls = new java.util.ArrayList<>();
        java.util.List<String> errors = new java.util.ArrayList<>();

        for (MultipartFile file : files) {
            if (file.isEmpty()) continue;

            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();

            try {
                File uploadDir = new File(UPLOAD_DIR);
                if (!uploadDir.exists()) uploadDir.mkdirs();

                String datePath = new SimpleDateFormat("yyyy/MM/dd/").format(new Date());
                File dateDir = new File(UPLOAD_DIR + datePath);
                if (!dateDir.exists()) dateDir.mkdirs();

                String newFilename = UUID.randomUUID().toString().replace("-", "") + extension;
                String relativePath = datePath + newFilename;
                File destFile = new File(uploadDir, relativePath);

                file.transferTo(destFile);
                urls.add("/uploads/" + relativePath.replace("\\", "/"));

            } catch (IOException e) {
                errors.add(originalFilename + ": " + e.getMessage());
            }
        }

        result.put("success", true);
        result.put("urls", urls);
        if (!errors.isEmpty()) {
            result.put("errors", errors);
        }
        return ResponseEntity.ok(result);
    }
}