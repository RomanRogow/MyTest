package org.example.mytestproject.entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.mytestproject.entity.Client;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {

    private Long id;

    private String firstName;

    private String LastName;

    private List<PaymentDTO> payments;

    public ClientDTO(Client client) {
        this.id = client.getId();
        this.firstName = client.getFirstName();
        LastName = client.getLastName();
        this.payments = client.getPayments() != null ?
                client.getPayments().stream()
                        .map(PaymentDTO::new)
                        .collect(Collectors.toList())
        : null;
    }
}
