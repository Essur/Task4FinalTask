package ua.mk.essur.task4finaltask.weblogic.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.mk.essur.task4finaltask.weblogic.entities.Customer;
import ua.mk.essur.task4finaltask.weblogic.repositories.CustomerRepository;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping({"/main_menu/work_with_customer", "/work_with_customer"})
public class DBControllerCustomers {
    CustomerRepository customerRepository;

    @PostMapping("/add_customer")
    public String addCustomer(@RequestParam("customerName") String name,@RequestParam ("customerCompany") String company, Model model){
        Customer customer = new Customer();
        customer.setCustomerName(name);
        customer.setCustomerCompany(company);
        customerRepository.save(customer);
        model.addAttribute("message","added customer");
        return "messages/successfully";
    }

    @PostMapping("/update_customer/{id}")
    public String updateCustomer(@PathVariable("id") int id, Customer customer,Model model){
        Optional<Customer> c = customerRepository.findById(id);
        if(c.isEmpty()){
            model.addAttribute("message", "Customer with id " + id + " does not exist!");
            return "messages/error";
        } else {
            c.get().setCustomerName(customer.getCustomerName());
            c.get().setCustomerCompany(customer.getCustomerCompany());
            customerRepository.save(c.get());
            return "redirect:/work_with_customer/show_all_customers";
        }
    }

    @GetMapping("/show_all_customers")
    public String showAllCustomers(Model model){
        List<Customer> customers = customerRepository.findAll();
        if(!customers.isEmpty()){
            model.addAttribute("customers",customers);
            return "views/customers";
        } else {
            model.addAttribute("message","No one customers to show");
            return "messages/error";
        }
    }

    @GetMapping("/delete_customer/{id}")
    public String deleteCustomer(@PathVariable("id") int id){
        customerRepository.deleteById(id);
        return "redirect:/work_with_customer/show_all_customers";
    }

    @GetMapping("/edit_customer/{id}")
    public String editCustomer(@PathVariable("id") int id, Model model){
        Customer customer = customerRepository.findById(id).get();
        model.addAttribute("customer",customer);
        return "edits/edit_customer";
    }
}
