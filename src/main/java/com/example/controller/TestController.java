package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Controller
public class TestController {

    @GetMapping("/raw-test")
    public void rawTest(HttpServletResponse response) throws Exception {
        // 强制设置响应头
        response.reset();
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();
        out.print("中文测试：宠物领养系统");
        out.flush();
    }
}