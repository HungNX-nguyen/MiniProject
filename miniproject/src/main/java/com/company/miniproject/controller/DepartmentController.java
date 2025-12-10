package com.company.miniproject.controller;

import com.company.miniproject.dto.DtoRequest.DepartmentRequest;
import com.company.miniproject.dto.DtoRespone.DepartmentRespone;
import com.company.miniproject.service.impl.DepartmentServiceImpl;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/departments")
@Slf4j
public class DepartmentController {
    @Autowired
    private DepartmentServiceImpl departmentService;

    @GetMapping
    public String listDepartment(Model model) {
        log.debug("listDepartment");
        List<DepartmentRespone> listDepartments = departmentService.getAllDepartments();
        model.addAttribute("departments", listDepartments);
        return "department/list";
    }

    @PostMapping("/new")
    public String createDepartment(
            @Valid @ModelAttribute DepartmentRequest request,
            BindingResult result,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "department/form";
        }

        try {
            departmentService.createDepartment(request);
            redirectAttributes.addFlashAttribute("success", "Tạo phòng ban thành công!");
            log.info("Tạo phòng ban thành công!");
            return "redirect:/admin/departments";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/departments/new";
        }
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("departmentRequest", new DepartmentRequest());
        model.addAttribute("isEdit", false);
        return "department/form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        try {
            DepartmentRespone departmentRespone = departmentService.getDepartmentById(id);
            DepartmentRequest departmentRequest = DepartmentRequest.builder()
                    .departmentName(departmentRespone.getDepartmentName())
                    .description(departmentRespone.getDescription()).build();

            model.addAttribute("departmentRequest", departmentRequest);
            model.addAttribute("departmentId", id);
            model.addAttribute("isEdit", true);

            return "department/form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/departments";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateDepartment(
            @PathVariable int id,
            @Valid @ModelAttribute DepartmentRequest request,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("departmentId", id);
            model.addAttribute("isEdit", true);
            return "department/form";
        }

        try {
            departmentService.updateDepartment(id, request);
            redirectAttributes.addFlashAttribute("success", "Cập nhật phòng ban thành công!");
            return "redirect:/admin/departments";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/departments/edit/" + id;
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteDepartment(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            departmentService.deleteDepartment(id);
            redirectAttributes.addFlashAttribute("sucess", "Xóa phòng ban thành công");


        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/departments";
    }
}
