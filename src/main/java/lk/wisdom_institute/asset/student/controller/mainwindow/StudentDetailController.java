package lk.wisdom_institute.asset.student.controller.mainwindow;

import lk.wisdom_institute.asset.batch.entity.Batch;
import lk.wisdom_institute.asset.batch.service.BatchService;
import lk.wisdom_institute.asset.batch_student.entity.BatchStudent;
import lk.wisdom_institute.asset.batch_student.service.BatchStudentService;
import lk.wisdom_institute.asset.common_asset.model.enums.LiveDead;
import lk.wisdom_institute.asset.instalment_date.entity.InstalmentDate;
import lk.wisdom_institute.asset.instalment_date.service.InstalmentDateService;
import lk.wisdom_institute.asset.payment.entity.Payment;
import lk.wisdom_institute.asset.payment.entity.enums.PaymentStatus;
import lk.wisdom_institute.asset.payment.service.PaymentService;
import lk.wisdom_institute.asset.student.entity.Student;
import lk.wisdom_institute.asset.student.service.StudentService;
import lk.wisdom_institute.asset.user_management.entity.User;
import lk.wisdom_institute.asset.user_management.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping( "/studentDetail" )
public class StudentDetailController {
  private final StudentService studentService;
  private final BatchService batchService;
  private final UserService userService;
  private final PaymentService paymentService;
  private final InstalmentDateService instalmentDateService;
  private final BatchStudentService batchStudentService;

  public StudentDetailController(StudentService studentService, BatchService batchService, UserService userService,
                                 PaymentService paymentService, BatchStudentService batchStudentService,InstalmentDateService instalmentDateService) {
    this.studentService = studentService;
    this.batchService = batchService;
    this.userService = userService;
    this.paymentService = paymentService;
    this.batchStudentService = batchStudentService;
    this.instalmentDateService = instalmentDateService;
  }

  @GetMapping
  public String studentMainwindow(Model model) {
    User user = userService.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
    Student student = studentService.findById(user.getStudent().getId());

    List< Batch > registeredBatches = new ArrayList<>();

    student.getBatchStudents().forEach(x-> registeredBatches.add(batchService.findById(x.getBatch().getId())));

    model.addAttribute("batches",registeredBatches);

    List<Batch> unRegisteredBatches = batchService.findAll().stream().filter(x->x.getEndAt().isAfter(LocalDate.now())).collect(Collectors.toList());
    unRegisteredBatches.removeAll(registeredBatches);

    model.addAttribute("unRegisteredBatches",unRegisteredBatches);


    List< BatchStudent > batchStudents = batchStudentService.findByStudent(student)
        .stream()
        .filter(x -> x.getLiveDead().equals(LiveDead.ACTIVE))
        .collect(Collectors.toList());


    List< BatchStudent > batchStudentPayment = new ArrayList<>();


    batchStudents.forEach(y -> {
      List< Payment > payments = new ArrayList<>();
      y.getBatch().getInstalmentDates().forEach(x -> {
        Payment payment = paymentService.findByInstalmentDateAndBatchStudent(x, y);
        if ( payment == null ) {
          Payment newPayment = new Payment();
          newPayment.setPaymentStatus(PaymentStatus.NO_PAID);
          newPayment.setLiveDead(LiveDead.STOP);
          newPayment.setBatchStudent(y);
          InstalmentDate instalmentDate = instalmentDateService.findById(x.getId());
          newPayment.setInstalmentDate(instalmentDate);
          newPayment.setAmount(instalmentDate.getAmount());
          payments.add(newPayment);
        } else {
          payments.add(payment);
        }
      });
      y.setPayments(payments.stream().distinct().collect(Collectors.toList()));
      batchStudentPayment.add(y);
    });

    student.setBatchStudents(batchStudentPayment);
    model.addAttribute("paymentStatuses", PaymentStatus.values());
    model.addAttribute("student", student);
    model.addAttribute("addStatus", true);
    model.addAttribute("studentDetail", student);

    return "student/mainWindow";
  }
}
