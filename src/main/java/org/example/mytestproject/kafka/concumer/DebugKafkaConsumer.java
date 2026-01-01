//package org.example.mytestproject.kafka.concumer;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//@Component
//public class DebugKafkaConsumer {
//
//    // Используйте ДРУГОЙ group-id для отладки
//    @KafkaListener(
//            topics = "${app.kafka.topic.employee-bulk-sync}",
//            groupId = "debug-group"  // Другой group-id!
//    )
//    public void debugRawMessage(String rawMessage) {
//        log.info("=== DEBUG RAW MESSAGE ===");
//        log.info("Длина: {} символов", rawMessage.length());
//
//        // Выводим первые 500 символов
//        if (rawMessage.length() > 500) {
//            log.info("Сообщение (первые 500 символов): {}", rawMessage.substring(0, 500));
//        } else {
//            log.info("Сообщение: {}", rawMessage);
//        }
//
//    }
//}