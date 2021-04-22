package lk.wisdom_institute.asset.student.controller;


import lk.wisdom_institute.asset.batch.controller.BatchController;
import lk.wisdom_institute.asset.batch.entity.Batch;
import lk.wisdom_institute.asset.batch.service.BatchService;
import lk.wisdom_institute.asset.batch_student.service.BatchStudentService;
import lk.wisdom_institute.asset.common_asset.model.enums.Gender;
import lk.wisdom_institute.asset.common_asset.model.enums.LiveDead;
import lk.wisdom_institute.asset.student.entity.Student;
import lk.wisdom_institute.asset.student.service.StudentService;
import lk.wisdom_institute.util.interfaces.AbstractController;
import lk.wisdom_institute.util.service.MakeAutoGenerateNumberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping( "/student" )
public class StudentController implements AbstractController< Student, Integer > {
  private final StudentService studentService;
  private final BatchService batchService;
  private final BatchStudentService batchStudentService;
  private final MakeAutoGenerateNumberService makeAutoGenerateNumberService;

  public StudentController(StudentService studentService, BatchService batchService,
                           BatchStudentService batchStudentService,
                           MakeAutoGenerateNumberService makeAutoGenerateNumberService) {
    this.studentService = studentService;
    this.batchService = batchService;
    this.batchStudentService = batchStudentService;
    this.makeAutoGenerateNumberService = makeAutoGenerateNumberService;
  }


  @GetMapping
  public String findAll(Model model) {
    model.addAttribute("students", studentService.findAll()
        .stream()
        .filter(x -> x.getLiveDead().equals(LiveDead.ACTIVE))
        .collect(Collectors.toList()));
    model.addAttribute("studentRemoveBatch", false);
    return "student/student";
  }

  private String commonThing(Model model, Student student, boolean addStatus) {
    model.addAttribute("student", student);
    model.addAttribute("addStatus", addStatus);
    model.addAttribute("liveDeads", LiveDead.values());
    List< Batch > batches = batchService.findAll().stream().filter(x->x.getEndAt().isAfter(LocalDate.now())).collect(Collectors.toList());
    if ( student.getBatchStudents() == null ) {
      model.addAttribute("batches", batches);
    } else {
      student.getBatchStudents().forEach(x -> batches.remove(x.getBatch()));
      model.addAttribute("batches", batches);
    }

    model.addAttribute("gender", Gender.values());
    model.addAttribute("batchUrl", MvcUriComponentsBuilder
        .fromMethodName(BatchController.class, "findByBatchId", "")
        .build()
        .toString());
    return "student/addStudent";
  }

  @GetMapping( "/add" )
  public String form(Model model) {

    return commonThing(model, new Student(), true);
  }

  @GetMapping( "/view/{id}" )
  public String findById(@PathVariable Integer id, Model model) {
    model.addAttribute("studentDetail", studentService.findById(id));
    return "student/student-detail";
  }

  @GetMapping( "/edit/{id}" )
  public String edit(@PathVariable Integer id, Model model) {
    return commonThing(model, studentService.findById(id), false);
  }

  @PostMapping( "/save" )
  public String persist(@Valid @ModelAttribute Student student, BindingResult bindingResult,
                        RedirectAttributes redirectAttributes, Model model) {
    if ( bindingResult.hasErrors() ) {
      return commonThing(model, student, true);
    }

//there are two different situation
    //1. new Student -> need to generate new number
    //2. update student -> no required to generate number
    if ( student.getId() == null ) {
      // need to create auto generated registration number
      Student lastStudent = studentService.lastStudentOnDB();
      if ( lastStudent != null ) {
        String lastNumber = lastStudent.getRegNo().substring(3);
        student.setRegNo("SSS" + makeAutoGenerateNumberService.numberAutoGen(lastNumber));
      } else {
        student.setRegNo("SSS" + makeAutoGenerateNumberService.numberAutoGen(null));
      }
    }
    studentService.persist(student);
    student.getBatchStudents().forEach(x -> {
      if ( x.getBatch() != null && x.getStudent() == null ) {
        x.setStudent(student);
        batchStudentService.persist(x);
      }
    });
    return "redirect:/student";

  }

  @GetMapping( "/delete/{id}" )
  public String delete(@PathVariable Integer id, Model model) {
    studentService.delete(id);
    return "redirect:/student";
  }

  @PostMapping( "/search" )
  public String search(@ModelAttribute Student student, Model model) {
    List< Student > students = studentService.search(student);

    if ( students.isEmpty() ) {
      model.addAttribute("student", true);
      return "student/studentChooser";
    } else if ( students.size() == 1 ) {
      return "redirect:/payment/add/" + students.get(0).getId();
    } else {
      model.addAttribute("students", students);
      return "student/studentChooser";
    }
  }
}
