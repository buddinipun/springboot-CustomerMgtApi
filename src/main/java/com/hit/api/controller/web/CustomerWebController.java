package com.hit.api.controller.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.hit.api.dto.CustomerRequestDTO;
import com.hit.api.service.CustomerService;

import java.time.LocalDate;

@Controller
@RequestMapping("/ui/customers")
@RequiredArgsConstructor
public class CustomerWebController {

    private final CustomerService customerService;

    // REGISTER
    @PostMapping
    public String saveCustomer(@ModelAttribute CustomerRequestDTO dto,
                               Model model) {

        try {
            customerService.registerCustomer(dto);
            model.addAttribute("message", "Customer saved successfully");
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
        }

        return "register";
    }

    // SEARCH
    @GetMapping("/search")
    public String search(@RequestParam String nic, Model model) {

        try {
            model.addAttribute("customer",
                    customerService.getCustomerByNic(nic));
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
        }

        return "search";
    }

    // STATS
    @GetMapping("/stats")
    public String stats(@RequestParam(required = false) LocalDate startDate,
                        @RequestParam(required = false) LocalDate endDate,
                        Model model) {

        if (startDate != null && endDate != null) {
            long count = customerService
                    .getRegistrationCount(startDate, endDate);

            model.addAttribute("count", count);
        }

        return "stats";
    }
}