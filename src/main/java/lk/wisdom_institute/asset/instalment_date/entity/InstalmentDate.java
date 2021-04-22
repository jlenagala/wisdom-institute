package lk.wisdom_institute.asset.instalment_date.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.wisdom_institute.asset.batch.entity.Batch;
import lk.wisdom_institute.asset.common_asset.model.enums.LiveDead;
import lk.wisdom_institute.asset.payment.entity.Payment;
import lk.wisdom_institute.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter( "InstalmentDate" )
public class InstalmentDate extends AuditEntity {

  @Enumerated( EnumType.STRING )
  private LiveDead liveDead;
  @DateTimeFormat( pattern = "yyyy-MM-dd" )
  private LocalDate date;

  private BigDecimal amount;

  @ManyToOne
  private Batch batch;

  @OneToMany
  private List<Payment> payments;

}
