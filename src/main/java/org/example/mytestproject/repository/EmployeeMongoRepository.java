package org.example.mytestproject.repository;

import org.example.mytestproject.entity.EmployeeMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EmployeeMongoRepository extends MongoRepository<EmployeeMongo, String> {
    List<EmployeeMongo> findAll();
}