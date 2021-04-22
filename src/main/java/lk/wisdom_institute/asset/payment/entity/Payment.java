package lk.wisdom_institute.asset.payment.entity;


import com.fasterxml.jackson.annotation.JsonFilter;
import lk.wisdom_institute.asset.batch_student.entity.BatchStudent;
import lk.wisdom_institute.asset.common_asset.model.enums.LiveDead;
import lk.wisdom_institute.asset.instalment_date.entity.InstalmentDate;
import lk.wisdom_institute.asset.payment.entity.enums.PaymentStatus;
import lk.wisdom_institute.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter( "Payment" )
public class Payment extends AuditEntity {

    @Column(unique = true)
    private String code;

    private BigDecimal amount;


    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;


    @Enumerated( EnumType.STRING )
    private LiveDead liveDead;

    @ManyToOne
    private BatchStudent batchStudent;

    @ManyToOne
    private InstalmentDate instalmentDate;


}
