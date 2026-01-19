package org.example.mytestproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "clients")
//@NamedEntityGraph(
//        name = "graph_clients_payments",
//        attributeNodes = {
//                @NamedAttributeNode("payments")
//        }
//)
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private List<Payment> payments = new ArrayList<>();
}
