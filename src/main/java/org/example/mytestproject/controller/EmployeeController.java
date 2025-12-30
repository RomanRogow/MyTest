package org.example.mytestproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.mytestproject.entity.DTO.EmployeeDTO;
import org.example.mytestproject.entity.Employee;
import org.example.mytestproject.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService service;

    @GetMapping("/by-name")
    public List<EmployeeDTO> getEmployeesByName(@RequestParam String firstName) {
        List<Employee> emp = service.findAllByFirstName(firstName);

        return emp.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping
    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> emp = service.findAll();

        return emp.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Employee getEmpioyeeById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {

        Employee emp = service.createEmployee(employee);

        return ResponseEntity.status(HttpStatus.CREATED).body(emp);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllEmployees() {
        long count = service.employeeCount();
        if (count <= 0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        service.deleteAllEmployee();
        return ResponseEntity.ok("Удалено "+count+" запписей.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployeeById(
            @PathVariable Long id) {
       service.deleteEmployeeById(id);
       return ResponseEntity.ok("Удалена запись с ID: "+id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> uptadeEmployee(
            @PathVariable Long id,
            @RequestBody Employee employee
    ){
        log.info("Обновление сотрудника с ID: {}", id);

        Employee emp = service.updateEmployee(id,employee);
        return ResponseEntity.ok(emp);
    }

    // Вспомогательный метод: Entity -> DTO
    private EmployeeDTO convertToDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setAge(employee.getAge());
        dto.setDepartment(employee.getDepartment());
        dto.setPost((employee.getPost() == null) ? "" : employee.getPost());
        dto.setCreatedAt(employee.getCreateAt() == null ? null : employee.getCreateAt());
        return dto;
    }
}
