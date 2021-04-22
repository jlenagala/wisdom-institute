package lk.wisdom_institute.asset.time_table.controller;


import lk.wisdom_institute.asset.batch.entity.Batch;
import lk.wisdom_institute.asset.batch.service.BatchService;
import lk.wisdom_institute.asset.batch_student.service.BatchStudentService;
import lk.wisdom_institute.asset.common_asset.model.DateTimeTable;
import lk.wisdom_institute.asset.common_asset.model.TwoDate;
import lk.wisdom_institute.asset.common_asset.model.enums.LiveDead;
import lk.wisdom_institute.asset.student.entity.Student;
import lk.wisdom_institute.asset.student.service.StudentService;
import lk.wisdom_institute.asset.subject.service.SubjectService;
import lk.wisdom_institute.asset.time_table.entity.*;
import lk.wisdom_institute.asset.time_table.model.TimeTableDate;
import lk.wisdom_institute.asset.time_table.service.TimeTableService;
import lk.wisdom_institute.asset.user_management.entity.User;
import lk.wisdom_institute.asset.user_management.service.*;
import lk.wisdom_institute.util.service.DateTimeAgeService;
import lk.wisdom_institute.util.service.EmailService;
import lk.wisdom_institute.util.service.MakeAutoGenerateNumberService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping( "/timeTable" )
public class TimeTableController {
  private final TimeTableService timeTableService;
  private final SubjectService subjectService;
  private final BatchService batchService;
  private final BatchStudentService batchStudentService;
  private final MakeAutoGenerateNumberService makeAutoGenerateNumberService;
  private final DateTimeAgeService dateTimeAgeService;
  private final UserService userService;
  private final StudentService studentService;
  private final EmailService emailService;


  public TimeTableController(TimeTableService timeTableService,
                             SubjectService subjectService, BatchService batchService,
                             BatchStudentService batchStudentService,
                             MakeAutoGenerateNumberService makeAutoGenerateNumberService,
                             DateTimeAgeService dateTimeAgeService, UserService userService,
                             StudentService studentService, EmailService emailService) {
    this.timeTableService = timeTableService;
    this.subjectService = subjectService;
    this.batchService = batchService;
    this.batchStudentService = batchStudentService;
    this.makeAutoGenerateNumberService = makeAutoGenerateNumberService;
    this.dateTimeAgeService = dateTimeAgeService;
    this.userService = userService;
    this.studentService = studentService;
    this.emailService = emailService;
  }

  @GetMapping
  public String findAll(Model model) {
    LocalDate today = LocalDate.now();
    return findAllCommon(model, today, today.plusDays(7), 0);

  }

  private String findAllCommon(Model model, LocalDate startDate, LocalDate endDate, int id) {
    List< TimeTableDate > timeTableDates = new ArrayList<>();
    List< TimeTable > timeTables = timeTableService.findAll();
    Period difference = Period.between(startDate, endDate);
    for ( int i = 0; i < difference.getDays() + 1; i++ ) {
      TimeTableDate timeTableDate = new TimeTableDate();
      List< TimeTable > timeTableFilterOnDate = new ArrayList<>();
      timeTableDate.setDate(startDate.plusDays(i));
      for ( TimeTable timeTable : timeTables ) {
        if ( id == 0 ) {
          if ( dateTimeAgeService.getLocalDateTImeToLocalDate(timeTable.getEndAt()).equals(startDate.plusDays(i)) ) {
            timeTableFilterOnDate.add(timeTable);
          }
        } else {
          Batch batch = batchService.findById(id);
          if ( dateTimeAgeService.getLocalDateTImeToLocalDate(timeTable.getEndAt()).equals(startDate.plusDays(i)) && timeTable.getBatch().equals(batch) ) {
            timeTableFilterOnDate.add(timeTable);
          }
        }
      }


      timeTableDate.setTimeTables(timeTableFilterOnDate);
      timeTableDate.setStatus(!timeTableFilterOnDate.isEmpty());
      timeTableDates.add(timeTableDate);
    }

    String message = "Show all date time tables from " + startDate + " to " + startDate.plusDays(7);
    model.addAttribute("timeTableDates", timeTableDates);
    model.addAttribute("message", message);
    model.addAttribute("batches", batchService.findAll());
    return "timeTable/timeTable";
  }

  @PostMapping
  public String findAll(@ModelAttribute( "twoDate" ) TwoDate twoDate, Model model) {

    return findAllCommon(model, twoDate.getStartDate(), twoDate.getEndDate(), twoDate.getId());
  }

  @GetMapping( "/byDate" )
  public String byDate(Model model) {
    List< TimeTable > timeTables = timeTableService.findAll();
    return common(timeTables, model);
  }

  @GetMapping( "/teacher" )
  public String byTeacher(Model model) {
    User authUser = userService.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());

    List< TimeTable > timeTables = timeTableService.findAll();

    return common(timeTables, model);
  }

  private String common(List< TimeTable > timeTables, Model model) {
    HashSet< LocalDate > classDates = new HashSet<>();
    timeTables.forEach(x -> classDates.add(x.getStartAt().toLocalDate()));

    List< DateTimeTable > dateTimeTables = new ArrayList<>();

    for ( LocalDate classDate : classDates ) {
      DateTimeTable dateTimeTable = new DateTimeTable();
      dateTimeTable.setDate(classDate);
      dateTimeTable.setTimeTables(timeTables.stream().filter(x -> x.getStartAt().toLocalDate().isEqual(classDate)).collect(Collectors.toList()));
      dateTimeTables.add(dateTimeTable);
    }

    model.addAttribute("timeTableMaps", dateTimeTables);
    return "timeTable/timeTableView";
  }


  @GetMapping( "/add" )
  public String form(Model model) {
    model.addAttribute("batches", batchService.findAll());
    return "timeTable/dateChooser";
  }


  @PostMapping( "/add" )
  public String form(@RequestParam( "date" ) @DateTimeFormat( pattern = "yyyy-MM-dd" ) LocalDate date,
                     @RequestParam( "id" ) Integer id, Model model) {
    Batch batch = addEditPostMethod(date, id, model);
    return commonThing(model, id, true, batch);
  }

  @GetMapping( "/view/{id}" )
  public String findById(@PathVariable Integer id, Model model) {
    TimeTable timeTable = timeTableService.findById(id);
    model.addAttribute("timeTableDetail", timeTable);
    List< Student > students = new ArrayList<>();
    timeTable.getBatch()
        .getBatchStudents()
        .stream()
        .filter(x -> x.getLiveDead().equals(LiveDead.ACTIVE)).collect(Collectors.toList())
        .forEach(x -> students.add(x.getStudent()));
    model.addAttribute("students", students);
    model.addAttribute("studentRemoveBatch", false);
    return "timeTable/timeTable-detail";
  }

  @PostMapping( "/view" )
  public String viewPost(@RequestParam( "date" ) @DateTimeFormat( pattern = "yyyy-MM-dd" ) LocalDate date,
                         @RequestParam( "id" ) Integer id,
                         Model model) {
    Batch batch = batchService.findById(id);

    List< TimeTable > timeTables = new ArrayList<>();
    batch.getTimeTables().forEach(x -> timeTables.add(timeTableService.findById(x.getId())));
    model.addAttribute("timeTables", timeTables);

    List< Student > students = new ArrayList<>();

    batch.getBatchStudents().forEach(x -> students.add(studentService.findById(x.getStudent().getId())));

    model.addAttribute("students", students);
    model.addAttribute("batchDetail", batch);
    model.addAttribute("studentRemoveBatch", true);
    return "timeTable/allTimeDetailDate";
  }

  @PostMapping( "/edit" )
  public String editPost(@RequestParam( "date" ) @DateTimeFormat( pattern = "yyyy-MM-dd" ) LocalDate date,
                         @RequestParam( "id" ) Integer id,
                         Model model) {

    Batch batch = addEditPostMethod(date, id, model);
    return commonThing(model, id, false, batch);
  }

  private Batch addEditPostMethod(@DateTimeFormat( pattern = "yyyy-MM-dd" ) @RequestParam( "date" ) LocalDate date,
                                  @RequestParam( "id" ) Integer id, Model model) {
    model.addAttribute("date", date);
    Batch batch = batchService.findById(id);

    List< TimeTable > timeTables = new ArrayList<>();
    for ( TimeTable timeTable : batch.getTimeTables() ) {
      if ( dateTimeAgeService.getLocalDateTImeToLocalDate(timeTable.getEndAt()).equals(date) ) {
        timeTables.add(timeTableService.findById(timeTable.getId()));
      }
    }
    batch.setTimeTables(timeTables);
    return batch;
  }

  @PostMapping( "/save" )
  public String persist(@Valid @ModelAttribute Batch batch, BindingResult bindingResult, Model model) {
    if ( bindingResult.hasErrors() ) {

      return commonThing(model, batch.getId(), true, batch);
    }

    for ( TimeTable timeTable : batch.getTimeTables() ) {
      if ( timeTable.getId() == null ) {
        TimeTable lastTimeTable = timeTableService.lastTimeTable();
        if ( lastTimeTable == null ) {
          timeTable.setCode("TMTB" + makeAutoGenerateNumberService.numberAutoGen(null).toString());
        } else {
          timeTable.setCode("TMTB" + makeAutoGenerateNumberService.numberAutoGen(lastTimeTable.getCode().substring(4)).toString());
        }
      }
      TimeTable timeTableDb = timeTableService.persist(timeTable);
      if ( timeTableDb.getBatch().getBatchStudents() != null ) {
        timeTableDb.getBatch().getBatchStudents().forEach(x -> {
          Student student = studentService.findById(x.getId());
          if ( student.getEmail() != null ) {
            String message = "Dear " + student.getName() + "\n Your " + timeTableDb.getBatch().getName() + " class " +
                "would be held from " + timeTableDb.getStartAt() + " to " + timeTableDb.getEndAt() + "\n Thanks \n " +
                "Success Student";
            emailService.sendEmail(student.getEmail(), "Time Table - Notification", message);
          }
        });
      }

    }

    return "redirect:/timeTable";

  }

  private String commonThing(Model model, Integer id, boolean addStatus, Batch batch) {

    if ( batch.getEndAt().equals(LocalDate.now()) ) {
      model.addAttribute("message", " There is no requirement to create new time table, because to day is finished " +
          "this course");
      model.addAttribute("batches", batchService.findAll());
      return "timeTable/dateChooser";
    }

    model.addAttribute("batch", batch);
    model.addAttribute("subjects", batch.getSubjects());
    model.addAttribute("batchDetail", batch);
    model.addAttribute("addStatus", addStatus);
    model.addAttribute("liveDeads", LiveDead.values());
    return "timeTable/addTimeTable";
  }


}

