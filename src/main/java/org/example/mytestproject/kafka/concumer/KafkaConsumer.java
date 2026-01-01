//package org.example.mytestproject.kafka.concumer;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.example.mytestproject.entity.DTO.KafkaEmployeeEventDTO;
//import org.example.mytestproject.entity.Employee;
//import org.example.mytestproject.repository.EmployeeRepository;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class KafkaConsumer {
//
//    private final EmployeeRepository employeeRepository;
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    // ОСНОВНОЙ МЕТОД - оставьте только его
//    @KafkaListener(
//            topics = "${app.kafka.topic.employee-bulk-sync}",
//            groupId = "my-group",
//            containerFactory = "kafkaListenerContainerFactory"
//    )
//    @Transactional
//    public void processEmployeeEvent(KafkaEmployeeEventDTO event) {
//        try {
//            log.info("=== ПОЛУЧЕНО СОБЫТИЕ ИЗ KAFKA ===");
//            log.info("Тип: {}, Источник: {}", event.getEventType(), event.getSourceService());
//
//            if (event.getEmployee() == null) {
//                log.error("Событие не содержит данных о сотруднике!");
//                return;
//            }
//
//            var employeeData = event.getEmployee();
//            log.info("Сотрудник: {} {}", employeeData.getFirstName(), employeeData.getLastName());
//            log.info("Департамент: {}, Возраст: {}", employeeData.getDepartment(), employeeData.getAge());
//
//            // Преобразуем в Entity и сохраняем
//            Employee employee = convertToEntity(employeeData);
//            Employee saved = employeeRepository.save(employee);
//
//            log.info("✅ Сохранен сотрудник: ID={}, ФИО={}",
//                    saved.getId(), saved.getFullName());
//
//        } catch (Exception e) {
//            log.error("❌ Ошибка обработки: {}", e.getMessage(), e);
//        }
//    }
//
//    private Employee convertToEntity(KafkaEmployeeEventDTO.EmployeeDataDTO dto) {
//        Employee employee = new Employee();
//        employee.setId(dto.getId());
//        employee.setPersonalCode(dto.getPersonalCode());
//        employee.setFirstName(dto.getFirstName());
//        employee.setLastName(dto.getLastName());
//        employee.setAge(dto.getAge() != null ? dto.getAge() : 0);
//        employee.setDepartment(dto.getDepartment() != null ? dto.getDepartment() : "Не указан");
//        employee.setPost(dto.getPost());
//        employee.setCreatedAt(LocalDateTime.now());
//        employee.setUpdatedAt(LocalDateTime.now());
//
//        return employee;
//    }
//}