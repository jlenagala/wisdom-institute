package lk.wisdom_institute.asset.hall.entity;


import com.fasterxml.jackson.annotation.JsonFilter;
import lk.wisdom_institute.asset.batch.entity.Batch;
import lk.wisdom_institute.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("Hall")
public class Hall extends AuditEntity {
    private String hallName;
    private String number;
    private String hallCondition;
    private String seatCount;
    private String hallLocation;

    @ManyToMany(mappedBy = "halls")
    private List<Batch> batches;


}