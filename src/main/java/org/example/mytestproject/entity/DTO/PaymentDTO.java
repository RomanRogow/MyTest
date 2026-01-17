package org.example.mytestproject.entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.mytestproject.entity.Payment;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {

    private Long id;

    private Long amount;

    private Long paymentId;

    public PaymentDTO(Payment payment) {
        this.id = payment.getId();
        this.amount = payment.getAmount();
        this.paymentId = payment.getClientId();
    }
}
