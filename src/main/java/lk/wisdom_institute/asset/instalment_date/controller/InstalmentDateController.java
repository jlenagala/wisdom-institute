package lk.wisdom_institute.asset.instalment_date.controller;

import lk.wisdom_institute.asset.common_asset.model.enums.LiveDead;
import lk.wisdom_institute.asset.instalment_date.entity.InstalmentDate;
import lk.wisdom_institute.util.interfaces.AbstractController;
import lk.wisdom_institute.util.service.MakeAutoGenerateNumberService;
import lk.wisdom_institute.asset.instalment_date.service.InstalmentDateService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping( "/instalmentDate" )
public class InstalmentDateController implements AbstractController< InstalmentDate, Integer > {
  private final InstalmentDateService instalmentDateService;
  private final MakeAutoGenerateNumberService makeAutoGenerateNumberService;

  public InstalmentDateController(InstalmentDateService instalmentDateService, MakeAutoGenerateNumberService makeAutoGenerateNumberService) {
    this.instalmentDateService = instalmentDateService;
    this.makeAutoGenerateNumberService = makeAutoGenerateNumberService;
  }

  @GetMapping
  public String findAll(Model model) {
    model.addAttribute("addStatus", false);
    model.addAttribute("instalmentDates",
                       instalmentDateService.findAll());
    return "instalmentDate/instalmentDate";
  }

  @GetMapping("/delete")
  public String findAllDeleted(Model model) {
    model.addAttribute("addStatus", true);
    model.addAttribute("instalmentDates",
                       instalmentDateService.findAllDeleted());
    return "instalmentDate/instalmentDate";
  }

  @GetMapping( "/add" )
  public String form(Model model) {
    model.addAttribute("instalmentDate", new InstalmentDate());
    model.addAttribute("addStatus", true);
    return "instalmentDate/addInstalmentDate";
  }

  @GetMapping( "/view/{id}" )
  public String findById(@PathVariable Integer id, Model model) {
    model.addAttribute("instalmentDateDetail", instalmentDateService.findById(id));
    return "instalmentDate/instalmentDate-detail";
  }

  @GetMapping( "/edit/{id}" )
  public String edit(@PathVariable Integer id, Model model) {
    model.addAttribute("instalmentDate", instalmentDateService.findById(id));
    model.addAttribute("addStatus", false);
    return "instalmentDate/addInstalmentDate";
  }

  @PostMapping( "/save" )
  public String persist(@Valid @ModelAttribute InstalmentDate instalmentDate, BindingResult bindingResult,
                        RedirectAttributes redirectAttributes, Model model) {
    if ( bindingResult.hasErrors() ) {
      model.addAttribute("instalmentDate", instalmentDate);
      model.addAttribute("addStatus", true);
      return "instalmentDate/addInstalmentDate";
    }

    try {
      instalmentDateService.persist(instalmentDate);
    } catch ( Exception e ) {
      ObjectError error = new ObjectError("instalmentDate",
                                          "Please fix following errors which you entered .\n System message -->" + e.getCause().getCause().getMessage());
      bindingResult.addError(error);
      model.addAttribute("instalmentDate", instalmentDate);
      model.addAttribute("addStatus", true);
      return "instalmentDate/addInstalmentDate";
    }
    return "redirect:/instalmentDate";

  }

  @GetMapping( "/delete/{id}" )
  public String delete(@PathVariable Integer id, Model model) {
    instalmentDateService.delete(id);
    return "redirect:/instalmentDate";
  }

  @GetMapping( "/active/{id}" )
  public String active(@PathVariable Integer id) {
    InstalmentDate instalmentDate = instalmentDateService.findById(id);
    instalmentDate.setLiveDead(LiveDead.ACTIVE);
    instalmentDateService.persist(instalmentDate);
    return "redirect:/instalmentDate/delete";
  }
}
