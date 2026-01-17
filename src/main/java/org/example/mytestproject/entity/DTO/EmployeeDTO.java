package org.example.mytestproject.entity.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("personalCode")
    private String personalCode;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("department")
    private String department;

    @JsonProperty("age")
    private Integer age;

    @JsonProperty("post")
    private String post;

    @JsonProperty("qrCodeBase64")
    private String qrCodeBase64;

    @JsonProperty("qrCodeData")
    private String qrCodeData;

    @JsonProperty("createdAt")
    private java.time.LocalDateTime createdAt;

    @JsonProperty("updatedAt")
    private java.time.LocalDateTime updatedAt;

    @JsonProperty("syncedToKafka")
    private Boolean syncedToKafka;

    @JsonProperty("kafkaSyncDate")
    private java.time.LocalDateTime kafkaSyncDate;

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
