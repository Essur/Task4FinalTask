package ua.mk.essur.task4finaltask.weblogic.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.mk.essur.task4finaltask.logic.services.OrderService;

@Controller
@AllArgsConstructor
@RequestMapping({ "/main_menu"})
public class NavigationController {

    @GetMapping("/add_customer_form")
    public String addCustomerForm(){
        OrderService.getInstance().clearList();
        return "forms/customer_form";
    }

    @GetMapping("")
    public String mainMenu(){
        OrderService.getInstance().clearList();
        return "index";
    }
    @GetMapping("/about_web_app")
    public String aboutIt(){
        return "info/about_web_app";
    }
}
