package ua.mk.essur.task4finaltask.weblogic.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.mk.essur.task4finaltask.logic.services.OrderService;

import java.util.HashMap;
import java.util.Map;

@Controller
@AllArgsConstructor
@RequestMapping("/work_with_layouts")
public class ControllerLayouts {

    /**Methods for creating order*/
    @GetMapping("/creating_form/{id}")
    public String toCreatingForm(@PathVariable("id") int id, Model model){
        model.addAttribute("action","creating");
        model.addAttribute("customerId",id);
        OrderService.getInstance().clearList();
        return "forms/order_form";
    }

    @PostMapping("/create_layouts")
    public String addingLayouts(@RequestParam("layoutName") String layoutName,
                              @RequestParam("pricePerPiece") double pricePerPiece,
                              @RequestParam("countOfMade") int countOfMade,
                              @RequestParam("customerId") int customerId,
                              Model model){
        OrderService.getInstance().addLayout(layoutName,pricePerPiece,countOfMade);
        model.addAttribute("layouts", OrderService.getInstance().getLayouts().getLayoutList());
        model.addAttribute("totalCost",OrderService.getInstance().getLayouts().getTotalCost());
        model.addAttribute("customerId",customerId);
        return "forms/order_form";
    }

    @GetMapping("/delete_layouts")
    public String clearList(){
        OrderService.getInstance().clearList();
        return "forms/order_form";
    }

    @GetMapping("/delete_layout/{name}")
    public String deleteLayout(@PathVariable("name") String name,@RequestParam("customerId") int customerId, Model model){
        OrderService.getInstance().removeLayout(name);
        model.addAttribute("layouts", OrderService.getInstance().getLayouts().getLayoutList());
        model.addAttribute("totalCost", OrderService.getInstance().getLayouts().getTotalCost());
        model.addAttribute("customerId",customerId);
        return "forms/order_form";
    }

    /**Methods for editing order*/
    @PostMapping("/create_layouts/edit")
    public String addToEdit(@RequestParam("layoutName") String layoutName,
                            @RequestParam("pricePerPiece") double pricePerPiece,
                            @RequestParam("countOfMade") int countOfMade,
                            @RequestParam("customerId") int customerId,
                            @RequestParam("orderId") int orderId,
                            @RequestParam("fileName") String fileName,
                            Model model){
        OrderService.getInstance().addLayout(layoutName,pricePerPiece,countOfMade);
        model.addAllAttributes(createAttributeResponse(customerId, orderId, fileName));
        return "edits/edit_order";
    }
    @GetMapping("/edit/delete_layout/{name}")
    public String deleteInEdit(@PathVariable("name") String name,
                               @RequestParam("customerId") int customerId,
                               @RequestParam("orderId") int orderId,
                               @RequestParam("fileName") String fileName, Model model){
        OrderService.getInstance().removeLayout(name);
        model.addAllAttributes(createAttributeResponse(customerId, orderId, fileName));
        return "edits/edit_order";
    }

    private Map<String, Object> createAttributeResponse(int customerId,int orderId, String fileName){
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("layouts", OrderService.getInstance().getLayouts().getLayoutList());
        attributes.put("totalCost",OrderService.getInstance().getLayouts().getTotalCost());
        attributes.put("customerId",customerId);
        attributes.put("orderId", orderId);
        attributes.put("fileName",fileName);
        return attributes;
    }
}
