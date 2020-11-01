package lk.wisdom.institute.asset.discount.entity;


import com.fasterxml.jackson.annotation.JsonFilter;
import lk.wisdom.institute.asset.payment.entity.Payment;
import lk.wisdom.institute.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("Discount")
public class Discount extends AuditEntity {
    private String discountName;
    private String discountType;
    private String discountAmount;
    private String approvedBy;
    private String discountStatus;

    @OneToMany(mappedBy = "discount")
    private List<Payment> payments;





}