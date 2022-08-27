package ua.mk.essur.task4finaltask.weblogic.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping({ "/main_menu"})
public class NavigationController {

    @GetMapping("/add_customer_form")
    public String addCustomerForm(){
        return "forms/customer_form";
    }

    @GetMapping("")
    public String mainMenu(){
        return "index";
    }

}
