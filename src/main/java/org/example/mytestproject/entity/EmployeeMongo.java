package org.example.mytestproject.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Document("employeeMongo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeMongo {

    @Id
    private String mongoId;

    @Field("pg_id")
    private Long postgresId;

    @Field("first_name")
    private String firstName;

    @Field("last_name")
    private String lastName;

    @Field("age")
    private int age;

    @Field("action")
    private String action;

    @Field("created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
