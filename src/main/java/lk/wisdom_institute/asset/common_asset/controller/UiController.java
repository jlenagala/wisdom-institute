package lk.wisdom_institute.asset.common_asset.controller;


import lk.wisdom_institute.asset.user_management.entity.User;
import lk.wisdom_institute.asset.user_management.service.UserService;
import lk.wisdom_institute.util.service.DateTimeAgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class UiController {
  private final UserService userService;
  private final DateTimeAgeService dateTimeAgeService;

  @Autowired
  public UiController(UserService userService, DateTimeAgeService dateTimeAgeService) {
    this.userService = userService;
    this.dateTimeAgeService = dateTimeAgeService;
  }

  @GetMapping( value = {"/", "/index"} )
  public String index() {
    return "index";
  }

  @GetMapping( value = {"/home", "/mainWindow"} )
  public String getHome(Model model) {
    User user = userService.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
    if ( user.getStudent() != null ) {
      return "redirect:/studentDetail";
    }
    return "mainWindow";
  }

}

