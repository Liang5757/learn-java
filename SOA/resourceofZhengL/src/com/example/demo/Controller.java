package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@SpringBootApplication
@RestController
public class Controller {
    private static HashMap<String, Student> data = new HashMap<>();

    @RequestMapping("/get")
    public String get(String sno) {
        return data.get(sno).toString();
    }

    @PostMapping("/add")
    public boolean add(Student student) {
        data.put(student.getSno(), student);
        System.out.println(data.values());
        return true;
    }

    public static void main(String[] args) {
        SpringApplication.run(Controller.class, args);
    }
}