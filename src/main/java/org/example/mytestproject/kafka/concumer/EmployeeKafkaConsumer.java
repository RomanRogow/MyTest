package org.example.mytestproject.kafka.concumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.mytestproject.entity.Employee;
import org.example.mytestproject.repository.EmployeeRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmployeeKafkaConsumer {

    private final EmployeeRepository employeeRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    // ЕДИНСТВЕННЫЙ listener на этот топик
    @KafkaListener(
            topics = "${app.kafka.topic.employee-bulk-sync}",
            groupId = "employee-consumer-group"  // Новый уникальный group-id
    )
    @Transactional
    public void consumeEmployeeMessage(String rawMessage) {
        try {
            log.info("=== ПОЛУЧЕНО СООБЩЕНИЕ ИЗ KAFKA ===");

            // 1. Логируем сырое сообщение
            log.info("Сырое сообщение (первые 300 символов): {}",
                    rawMessage.length() > 300 ?
                            rawMessage.substring(0, 300) + "..." : rawMessage);

            // 2. Парсим как Map для просмотра структуры
            Map<String, Object> messageMap = objectMapper.readValue(rawMessage, Map.class);
            log.info("Структура JSON:");
            messageMap.forEach((key, value) -> {
                if (value instanceof Map) {
                    log.info("  {}: [вложенный объект]", key);
                } else {
                    log.info("  {}: {}", key, value);
                }
            });

            // 3. Извлекаем данные о сотруднике
            Map<String, Object> employeeData = extractEmployeeData(messageMap);

            if (employeeData == null || employeeData.isEmpty()) {
                log.error("Не удалось найти данные о сотруднике в сообщении!");
                return;
            }

            // 4. Создаем и сохраняем сотрудника
            Employee employee = createEmployeeFromMap(employeeData);
            Employee savedEmployee = employeeRepository.save(employee);

            log.info("✅ УСПЕШНО сохранен сотрудник: ID={}, ФИО={}",
                    savedEmployee.getId(), savedEmployee.getFullName());

        } catch (Exception e) {
            log.error("❌ ОШИБКА обработки сообщения: {}", e.getMessage());
            log.error("Сырое сообщение: {}", rawMessage);
            e.printStackTrace();
        }
    }

    private Map<String, Object> extractEmployeeData(Map<String, Object> messageMap) {
        // Вариант 1: Если данные в поле "employee"
        if (messageMap.containsKey("employee") && messageMap.get("employee") instanceof Map) {
            log.info("Данные сотрудника найдены в поле 'employee'");
            return (Map<String, Object>) messageMap.get("employee");
        }

        // Вариант 2: Если данные непосредственно в корне
        if (messageMap.containsKey("firstName") || messageMap.containsKey("id")) {
            log.info("Данные сотрудника найдены в корне сообщения");
            return messageMap;
        }

        // Вариант 3: Ищем в других возможных полях
        for (Map.Entry<String, Object> entry : messageMap.entrySet()) {
            if (entry.getValue() instanceof Map) {
                Map<String, Object> nestedMap = (Map<String, Object>) entry.getValue();
                if (nestedMap.containsKey("firstName") || nestedMap.containsKey("id")) {
                    log.info("Данные сотрудника найдены в поле '{}'", entry.getKey());
                    return nestedMap;
                }
            }
        }

        return null;
    }

    private Employee createEmployeeFromMap(Map<String, Object> employeeData) {
        Employee employee = new Employee();

        // Извлекаем данные с проверкой на null
        employee.setId(getLongValue(employeeData, "id"));
        employee.setPersonalCode(getStringValue(employeeData, "personalCode"));
        employee.setFirstName(getStringValue(employeeData, "firstName"));
        employee.setLastName(getStringValue(employeeData, "lastName"));
        employee.setAge(getIntValue(employeeData, "age"));
        employee.setDepartment(getStringValue(employeeData, "department", "Не указан"));
        employee.setPost(getStringValue(employeeData, "post"));
        employee.setCreatedAt(LocalDateTime.now());
        employee.setUpdatedAt(LocalDateTime.now());

        return employee;
    }

    private Long getLongValue(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value == null) return null;
        if (value instanceof Number) return ((Number) value).longValue();
        if (value instanceof String) {
            try {
                return Long.parseLong((String) value);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    private String getStringValue(Map<String, Object> map, String key) {
        return getStringValue(map, key, null);
    }

    private String getStringValue(Map<String, Object> map, String key, String defaultValue) {
        Object value = map.get(key);
        if (value == null) return defaultValue;
        return value.toString();
    }

    private int getIntValue(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value == null) return 0;
        if (value instanceof Number) return ((Number) value).intValue();
        if (value instanceof String) {
            try {
                return Integer.parseInt((String) value);
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        return 0;
    }
}
