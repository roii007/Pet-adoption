package com.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/images")
public class ImageController {

    // 本地图片目录路径
//    private static final String IMAGE_DIR = "D:/JavaEE期末项目/img/";
    // 本地图片目录路径
    private static final String IMAGE_DIR = "D:/JavaEE期末项目/img/";


    @GetMapping("/list")
    @ResponseBody
    public ResponseEntity<?> listImages() {
        List<Map<String, String>> images = new ArrayList<>();

        File dir = new File(IMAGE_DIR);
        if (!dir.exists() || !dir.isDirectory()) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "图片目录不存在");
            return ResponseEntity.badRequest().body(error);
        }

        File[] files = dir.listFiles((d, name) -> {
            String lower = name.toLowerCase();
            return lower.endsWith(".jpg") || lower.endsWith(".jpeg") ||
                    lower.endsWith(".png") || lower.endsWith(".gif") ||
                    lower.endsWith(".bmp") || lower.endsWith(".webp");
        });

        if (files != null) {
            for (File file : files) {
                Map<String, String> imgInfo = new HashMap<>();
                imgInfo.put("name", file.getName());
                imgInfo.put("url", "/uploads/" + file.getName());
                imgInfo.put("size", String.format("%.2f KB", file.length() / 1024.0));
                images.add(imgInfo);
            }
        }

        return ResponseEntity.ok(images);
    }
}