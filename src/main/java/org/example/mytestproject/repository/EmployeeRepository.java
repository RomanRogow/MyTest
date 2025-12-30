package org.example.mytestproject.repository;

import org.example.mytestproject.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByFirstName(String firstName);

    @Query("SELECT e FROM Employee e WHERE e.firstName = :firstName " +
            "AND e.lastName = :lastName " +
            "AND e.age = :age")
    Optional<Employee> findByFullNameAndAge(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("age") int age);
}
