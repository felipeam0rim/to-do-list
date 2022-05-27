package todoList;

import controller.ProjectController;
import controller.TaskController;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import model.Project;
import model.Task;
import util.ConnectionFactory;

public class App {

    public static void main(String[] args) throws SQLException {
        
        ProjectController projectController = new ProjectController();
        
//        Project project = new Project();
//        project.setName("Projeto teste 2");
//        project.setDescription("description");
//        projectController.save(project);
        
        List<Project> projects = projectController.getAll();
        System.out.println("Total de projetos = " + projects.size());
        
        TaskController taskController = new TaskController();
        
        Task task = new Task();
        task.setIdProject(1);
        task.setName("Criar telas da aplicação");
        task.setDescription("bebebebe");
        task.setIsCompleted(false);
        task.setDeadline(new Date());
        
        taskController.save(task);
        
        List<Task> tasks = taskController.getAll(1);
        System.out.println("Total de tasks = " + tasks.size());
        
        
    }
}
