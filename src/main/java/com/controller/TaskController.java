package com.controller;

import com.config.AppConfig;
import com.domain.Task;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.service.TaskService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.rmi.server.LogStream.log;
import static java.util.Objects.isNull;

@Controller
@RequestMapping("/")
public class TaskController {

    @PostConstruct
    public void init(){
        Logger.getLogger(TaskController.class.getName()).log(Level.INFO, "Luntik rodilsya");
    }
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping(value = "/hello")
    public @ResponseBody String getSmntg(){
        return "blabla";
    }

    @GetMapping(value = "/")
    public String tasks(@RequestParam(value = "pageNo", required = false, defaultValue = "1") int pageNo,
                        @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                        Model model) {
        List<Task>  taskList = taskService.getAll((pageNo-1) * pageSize, pageSize);
        model.addAttribute("taskList", taskList);
        return "tasks";
    }

    @PostMapping("/{id}")
    public String edit(@PathVariable Integer id,
                     @RequestBody TaskInfo info,
                     Model model) {
        if (isNull(id) || id <=0)
            throw new RuntimeException("Invalid id");
        Task task = taskService.edit(id, info.getDescription(), info.getStatus());
        return tasks(1, 10, model);
    }

    @PostMapping("/")
    public String add(@RequestBody TaskInfo info,
                      Model model) {
        Task task = taskService.create(info.getDescription(), info.getStatus());
        return tasks(1, 10, model);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id,
                         Model model) {
        if (isNull(id) || id <=0)
            throw new RuntimeException("Invalid id");
        taskService.delete(id);
        return tasks(1, 10, model);
    }
}
