package com.service;

import com.dao.TaskDAO;
import com.domain.Status;
import com.domain.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class TaskService {
    private final TaskDAO taskDAO;

    public TaskService(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    public List<Task> getAll(int offset, int limit) {
        return taskDAO.getAll(offset, limit);
    }

    public int getAllCount() {
        return taskDAO.getAllCount();
    }


    public Task create(String description, Status status) {
        Task task = new Task();
        task.setDescription(description);
        task.setStatus(status);
        taskDAO.saveOrUpdate(task);
        return task;
    }

    @Transactional
    public Task edit(int id, String description, Status status) {
//        Optional<Task> optionalTask = taskDAO.getById(id);
        Task task = taskDAO.getById(id);
        if(isNull(task))
            throw new RuntimeException("   Task not found for id : "+id);
        task.setDescription(description);
        task.setStatus(status);
        return task;
    }

    @Transactional
    public void delete(int id) {
        Task task = taskDAO.getById(id);
        if(isNull(task))
            throw new RuntimeException("   Task not found for id : "+id);
        taskDAO.delete(task);
    }

}
