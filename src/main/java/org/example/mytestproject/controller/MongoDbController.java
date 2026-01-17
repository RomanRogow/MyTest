package org.example.mytestproject.controller;

import lombok.RequiredArgsConstructor;
import org.example.mytestproject.entity.EmployeeMongo;
import org.example.mytestproject.repository.EmployeeMongoRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/mongo")
@RequiredArgsConstructor
public class MongoDbController {

    private final EmployeeMongoRepository employeeMongoRepository;

    @GetMapping("getAll")
    public List<EmployeeMongo> getAllEmployees(){
        return employeeMongoRepository.findAll();
    }
}
