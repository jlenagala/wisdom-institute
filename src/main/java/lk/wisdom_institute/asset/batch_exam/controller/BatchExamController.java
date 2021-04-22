package lk.wisdom_institute.asset.batch_exam.controller;


import lk.wisdom_institute.asset.batch.entity.Batch;
import lk.wisdom_institute.asset.batch.service.BatchService;
import lk.wisdom_institute.asset.batch_exam.entity.BatchExam;
import lk.wisdom_institute.asset.batch_exam.service.BatchExamService;
import lk.wisdom_institute.asset.common_asset.model.enums.LiveDead;
import lk.wisdom_institute.asset.employee.service.EmployeeService;
import lk.wisdom_institute.asset.student.entity.Student;
import lk.wisdom_institute.asset.student.service.StudentService;
import lk.wisdom_institute.asset.user_management.entity.User;
import lk.wisdom_institute.asset.user_management.service.UserService;
import lk.wisdom_institute.util.service.EmailService;
import lk.wisdom_institute.util.service.MakeAutoGenerateNumberService;
import org.springframework.mail.MailException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping( "/batchExam" )
public class BatchExamController {
  private final BatchService batchService;
  private final BatchExamService batchExamService;
  private final MakeAutoGenerateNumberService makeAutoGenerateNumberService;
  private final UserService userService;
  private final EmployeeService employeeService;
  private final StudentService studentService;
  private final EmailService emailService;

  public BatchExamController(BatchService batchService, BatchExamService batchExamService,
                             MakeAutoGenerateNumberService makeAutoGenerateNumberService, UserService userService,
                             EmployeeService employeeService, StudentService studentService,
                             EmailService emailService) {
    this.batchService = batchService;
    this.batchExamService = batchExamService;
    this.makeAutoGenerateNumberService = makeAutoGenerateNumberService;
    this.userService = userService;
    this.employeeService = employeeService;
    this.studentService = studentService;
    this.emailService = emailService;
  }


  @GetMapping
  public String findAll(Model model) {
    model.addAttribute("batchExams",
                       batchExamService.findAll().stream().filter(x -> x.getLiveDead().equals(LiveDead.ACTIVE)).collect(Collectors.toList()));
    return "batchExam/batchExam";
  }

  @GetMapping( "/teacher" )
  public String findByTeacher(Model model) {
    User user = userService.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());


    model.addAttribute("batchExams",
                       batchExamService.findAll()
                           .stream()
                           .filter(x -> x.getLiveDead().equals(LiveDead.ACTIVE))
                           .collect(Collectors.toList()));

    return "batchExam/batchExam";
  }

  @GetMapping( "/add/{id}" )
  public String addForm(@PathVariable Integer id, Model model) {
    Batch batch = batchService.findById(id);
    model.addAttribute("batchDetail", batch);
    BatchExam batchExam = new BatchExam();
    batchExam.setBatch(batch);
    batchExam.setStartAt(LocalDateTime.now());
    model.addAttribute("batchExams", batch.getBatchExams());
    model.addAttribute("batchExam", batchExam);
    return "batchExam/addBatchExam";
  }

  @GetMapping( "/view/{id}" )
  public String findById(@PathVariable Integer id, Model model) {
    BatchExam batchExam = batchExamService.findById(id);
    model.addAttribute("employeeDetail", employeeService.findById(batchExam.getBatch().getEmployee().getId()));
    model.addAttribute("batchExamDetail", batchExam);
    model.addAttribute("subjectDetail", batchExam.getSubject());
    return "batchExam/batchExam-detail";
  }

  @GetMapping( "/edit/{id}" )
  public String edit(@PathVariable Integer id, Model model) {
    BatchExam batchExam = batchExamService.findById(id);

    List< BatchExam > batchExams = batchService.findById(batchExam.getBatch().getId()).getBatchExams();
    batchExams.remove(batchExam);

    model.addAttribute("batchExams", batchExams);
    model.addAttribute("batchDetail", batchExam.getBatch());
    model.addAttribute("batchExam", batchExam);
    model.addAttribute("addStatus", true);
    return "batchExam/addBatchExam";
  }


  @PostMapping
  public String save(@ModelAttribute BatchExam batchExam, BindingResult bindingResult) {
    if ( bindingResult.hasErrors() ) {
      return "redirect:/batchExam/add" + batchExam.getBatch().getId();
    }
    if ( batchExam.getId() == null ) {
      BatchExam lastBatchExam = batchExamService.lastBatchExamDB();
      if ( lastBatchExam != null ) {
        String lastNumber = lastBatchExam.getCode().substring(3);
        batchExam.setCode("BEX" + makeAutoGenerateNumberService.numberAutoGen(lastNumber));
      } else {
        batchExam.setCode("BEX" + makeAutoGenerateNumberService.numberAutoGen(null));
      }
    }

    BatchExam batchExamDb = batchExamService.persist(batchExam);
    batchService.findById(batchExamDb.getBatch().getId()).getBatchStudents().forEach(x -> {
      Student student = studentService.findById(x.getStudent().getId());
      if ( student.getEmail() != null ) {
        String message =
            "Dear " + student.getName() + "\n Your batch " + batchService.findById(batchExamDb.getBatch().getId()).getName() + " subject " + batchExamDb.getSubject().getName() + "'s exam " +
                "would be held from " + batchExamDb.getStartAt() + " to " + batchExamDb.getEndAt() + ".\n Thanks \n " +
                "You";
        try {
          emailService.sendEmail(student.getEmail(), "Exam - Notification", message);
        } catch ( MailException e ) {
          e.printStackTrace();
        }
      }
    });
    return "redirect:/batchExam/teacher";
  }

  @GetMapping( "/delete/{id}" )
  public String delete(@PathVariable Integer id) {
    batchExamService.delete(id);
    return "redirect:/batchExam/teacher";
  }
}
