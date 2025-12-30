package org.example.mytestproject.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.mytestproject.entity.Employee;
import org.example.mytestproject.exceptions.EmployeeNotFoundException;
import org.example.mytestproject.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository repository;

    public List<Employee> findAllByFirstName(String firstName) {

        List<Employee> employees = repository.findByFirstName(firstName);
        if (employees.isEmpty()) {
            throw new EmployeeNotFoundException(
                    "Сотрудники с именем " + firstName + " отсутствуют"
            );
        }
        return employees;
    }

    public List<Employee> findAll() {
        return repository.findAll();
    }

    public Employee findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(
                        "Сотрудник с ID " + id + " не найден"
                ));
    }

    @Transactional
    public Employee createEmployee(Employee employee) {

        if (employee.getAge() < 18) {
            throw new IllegalArgumentException("Возраст должен быть не менее 18 лет");
        }
        if (employee.getFirstName() == null || employee.getFirstName().isEmpty()) {
            throw new IllegalArgumentException("Имя обязательно.");
        }
        if (employee.getLastName() == null || employee.getLastName().isEmpty()) {
            throw new IllegalArgumentException("Фамилия обязательна.");
        }
        Optional<Employee> existing = repository.findByFullNameAndAge(
                employee.getFirstName(),
                employee.getLastName(),
                employee.getAge()
        );

        if (existing.isPresent()) {
            throw new IllegalArgumentException(
                    String.format("Сотрудник '%s %s' возраст '%s' уже существует (ID: %d)",
                            employee.getFirstName(),
                            employee.getLastName(),
                            employee.getAge(),
                            existing.get().getId())
            );
        }
        log.info("Создание нового сотрудника: {}", employee);
        return repository.save(employee);
    }

    @Transactional
    public void deleteAllEmployee() {
        Long count = this.employeeCount();
        log.warn("ВНИМАНИЕ: Удаление всех сотрудников. Количество: {}", count);

        repository.deleteAll();
        log.info("Было удалено {} сотрудников. ", count);
    }

    @Transactional
    public void deleteEmployeeById(Long id) {
        Employee employee = this.findById(id);
        if (employee == null) {
            throw new EmployeeNotFoundException("Данный ID не найден.");
        }
        repository.delete(employee);
    }

    @Transactional
    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        log.info("Поиск сотрудника с ID: {}", id);

        Employee existingEmployee = this.findById(id);

        if (existingEmployee != null) {
            existingEmployee.setFirstName(updatedEmployee.getFirstName());
        }
        if (existingEmployee != null) {
            existingEmployee.setLastName(updatedEmployee.getLastName());
        }
        if (existingEmployee != null) {
            existingEmployee.setDepartment(updatedEmployee.getDepartment());
        }
        if (existingEmployee != null && updatedEmployee.getAge() >= 18) {
            existingEmployee.setAge(updatedEmployee.getAge());
        }
        if (existingEmployee != null) {
            existingEmployee.setPost(updatedEmployee.getPost());
        } else {
            throw new EmployeeNotFoundException("Возраст должен быть 18 и более!");
        }
        existingEmployee.setPost(updatedEmployee.getPost());

        return repository.save(existingEmployee);
    }

    public long employeeCount() {
        return repository.count();
    }

}
