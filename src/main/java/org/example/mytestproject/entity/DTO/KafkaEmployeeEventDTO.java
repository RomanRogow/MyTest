package org.example.mytestproject.entity.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class KafkaEmployeeEventDTO {

    @JsonProperty("eventId")
    private String eventId;

    @JsonProperty("eventType")
    private String eventType;

    @JsonProperty("timestamp")
    private LocalDateTime timestamp;

    @JsonProperty("employee")
    private EmployeeDataDTO employee;

    @JsonProperty("sourceService")
    private String sourceService;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class EmployeeDataDTO {
        @JsonProperty("id")
        private Long id;

        @JsonProperty("personalCode")
        private String personalCode;

        @JsonProperty("firstName")
        private String firstName;

        @JsonProperty("lastName")
        private String lastName;

        @JsonProperty("age")
        private Integer age;

        @JsonProperty("department")
        private String department;

        @JsonProperty("post")
        private String post;

        @JsonProperty("qrCodeBase64")
        private String qrCodeBase64;

        @JsonProperty("createdAt")
        private LocalDateTime createdAt;

        @JsonProperty("updatedAt")
        private LocalDateTime updatedAt;

        public String getFullName() {
            return firstName + " " + lastName;
        }
    }
}
