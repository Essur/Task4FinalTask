package ua.mk.essur.task4finaltask.weblogic.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.mk.essur.task4finaltask.logic.converter.FileNameConverter;
import ua.mk.essur.task4finaltask.logic.layouts.Layouts;
import ua.mk.essur.task4finaltask.weblogic.entities.Customer;
import ua.mk.essur.task4finaltask.weblogic.entities.Order;
import ua.mk.essur.task4finaltask.weblogic.repositories.CustomerRepository;
import ua.mk.essur.task4finaltask.weblogic.repositories.OrderRepository;
import ua.mk.essur.task4finaltask.logic.services.OrderService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/work_with_orders")
public class DBControllerOrders {
    CustomerRepository customerRepository;
    OrderRepository orderRepository;

    @GetMapping("/add_order/{id}")
    public String addOrder(@PathVariable("id") int id, Model model){
        Customer customer = customerRepository.findById(id).get();
        model.addAttribute("customer",customer);
        model.addAttribute("customerId", id);
        return "/views/orders_of_customer";
    }

    @GetMapping("/save_layouts/{customerId}")
    public String saveOrder(@PathVariable("customerId") int id, @RequestParam("fileName") String fileName, Model model) {
        return saveOrderToFileAndDB(id, fileName, model);
    }

    private String saveOrderToFileAndDB(int id, String fileName, Model model) {
        String fullFileName = FileNameConverter.makeFullName(fileName);
        String result = OrderService.getInstance().saveOrder(customerRepository.findById(id).get().getCustomerName(), fileName + '_' + LocalDate.now());
        if(!"I/O Exception".equalsIgnoreCase(result)) {
            Order order = new Order(fullFileName, LocalDateTime.now());
            orderRepository.save(order);
            Customer customer = customerRepository.findById(id).get();
            customer.setOrders(order);
            customerRepository.save(customer);
            OrderService.getInstance().clearList();
            model.addAttribute("message", result);
            return "messages/successfully";
        } else {
            model.addAttribute("message", result);
            return "messages/error";
        }
    }

    @PostMapping("/delete_order")
    public String deleteOrder(@RequestParam("orderId") int orderId, @RequestParam("customerId") int customerId, Model model){
        Collection<Order> orders = customerRepository.findById(customerId).get().getOrders();
        String fileName = customerRepository.findById(customerId).get().getCustomerName() + '/' +
                orderRepository.findById(orderId).get().getFileOrderReport();
        boolean result = orders.removeIf(order -> order.getId() == orderId);
        if(result) {
            Customer customer = customerRepository.findById(customerId).get();
            customer.setOrdersList(orders);
            customerRepository.save(customer);
            orderRepository.deleteById(orderId);
            OrderService.getInstance().removeFile(fileName);
                return "redirect:/work_with_orders/add_order/" + customerId;
        } else {
            model.addAttribute("message", "Order does not exist!");
            return "/messages/error";
        }
    }

    @PostMapping("/edit_order")
    public String editOrderForm(@RequestParam("orderId") int orderId, @RequestParam("customerId") int customerId, Model model){
        String fileName = customerRepository.findById(customerId).get().getCustomerName() + '/'
                + orderRepository.findById(orderId).get().getFileOrderReport();
        Layouts layouts = OrderService.getInstance().loadOrder(fileName);
        model.addAllAttributes(createResponseAttributes(orderId, customerId, fileName, layouts));
        return "edits/edit_order";
    }

    @PostMapping("/update_order/")
    public String updateOrder(@RequestParam("customerId") int customerId,
                              @RequestParam("orderId") int orderId,
                              @RequestParam("fileName") String fileName, Model model){
        return editOrderInFileAndDB(customerId,orderId,fileName,model);
    }

    private String editOrderInFileAndDB(int customerId, int orderId, String fileName, Model model) {
        String fullFileName = FileNameConverter.makeFullName(fileName);
        String result = OrderService.getInstance().saveOrder(customerRepository.findById(customerId).get().getCustomerName(), fileName + '_' + LocalDate.now());
        if(!"I/O Exception".equalsIgnoreCase(result)) {
            Order order = orderRepository.findById(orderId).get();
            order.setFileOrderReport(fullFileName);
            order.setOrderCreatingDate(LocalDateTime.now());
            orderRepository.save(order);
            OrderService.getInstance().clearList();
            model.addAttribute("message", result);
            return "messages/successfully";
        } else {
            model.addAttribute("message", result);
            return "messages/error";
        }
    }

    private Map<String, Object> createResponseAttributes(int orderId, int customerId, String fileName, Layouts layouts) {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("layouts", layouts.getLayoutList());
        attributes.put("totalCost", layouts.getTotalCost());
        attributes.put("fileName", FileNameConverter.convertFileName(fileName));
        attributes.put("customer", customerRepository.findById(customerId).get());
        attributes.put("orderId", orderId);
        attributes.put("customerId", customerId);
        return attributes;
    }

}
