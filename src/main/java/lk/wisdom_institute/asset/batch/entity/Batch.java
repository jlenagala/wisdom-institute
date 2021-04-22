package lk.wisdom_institute.asset.batch.entity;


import com.fasterxml.jackson.annotation.JsonFilter;

import lk.wisdom_institute.asset.batch.entity.enums.ClassDay;
import lk.wisdom_institute.asset.batch.entity.enums.Grade;
import lk.wisdom_institute.asset.batch_exam.entity.BatchExam;
import lk.wisdom_institute.asset.batch_student.entity.BatchStudent;
import lk.wisdom_institute.asset.common_asset.model.enums.LiveDead;
import lk.wisdom_institute.asset.employee.entity.Employee;
import lk.wisdom_institute.asset.instalment_date.entity.InstalmentDate;
import lk.wisdom_institute.asset.subject.entity.Subject;
import lk.wisdom_institute.asset.time_table.entity.TimeTable;
import lk.wisdom_institute.util.audit.AuditEntity;
import lombok.*;
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
@ToString
@JsonFilter( "Batch" )
public class Batch extends AuditEntity {

  @Column( unique = true )
  private String code;

  @Column( unique = true )
  private String name;

  @Enumerated( EnumType.STRING )
  private LiveDead liveDead;

  @DateTimeFormat( pattern = "yyyy-MM-dd" )
  private LocalDate startAt;

  @DateTimeFormat( pattern = "yyyy-MM-dd" )
  private LocalDate endAt;

  private int installmentCount;

  private BigDecimal courseFee;

  @ManyToOne
  private  Employee employee;

  @OneToMany( mappedBy = "batch", cascade = CascadeType.ALL)
  private List< InstalmentDate > instalmentDates;

  @OneToMany( mappedBy = "batch" )
  private List< BatchStudent > batchStudents;

  @OneToMany( mappedBy = "batch" )
  private List< TimeTable > timeTables;

  @OneToMany( mappedBy = "batch" )
  private List< BatchExam > batchExams;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "batch_subject",
      joinColumns = @JoinColumn(name = "batch_id"),
      inverseJoinColumns = @JoinColumn(name = "subject_id"))
  private List< Subject > subjects;

  @Enumerated( EnumType.STRING )
  private Grade grade;
  @Enumerated( EnumType.STRING )
  private ClassDay classDay;

  @Transient
  private int count;

  @Transient
  @DateTimeFormat( pattern = "yyyy-MM-dd" )
  private LocalDate date;
}
