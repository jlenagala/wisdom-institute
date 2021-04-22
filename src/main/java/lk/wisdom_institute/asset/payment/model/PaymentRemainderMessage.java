package lk.wisdom_institute.asset.payment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRemainderMessage {
  private List< PaymentRemainder > paymentRemainders;

}

