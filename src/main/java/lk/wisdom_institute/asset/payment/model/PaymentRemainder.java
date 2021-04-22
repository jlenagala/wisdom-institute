package lk.wisdom_institute.asset.payment.model;

import lk.wisdom_institute.asset.student.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentRemainder {
  private Student student;
  private String batchName;
  private BigDecimal amount;

  @DateTimeFormat( pattern = "yyyy-MM-dd" )
  private LocalDate date;

  private String message;

}
