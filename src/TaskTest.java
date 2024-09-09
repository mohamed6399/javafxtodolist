import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import connect.DatabaseConnection;

public class TaskTest {

    private Connection connection;

    @BeforeEach
    public void setUp() throws SQLException {
        // Connexion à la base de données avant chaque test
        connection = DatabaseConnection.getConnection();
        assertNotNull(connection, "La connexion à la base de données n'a pas pu être établie.");
    }

    @AfterEach
    public void tearDown() throws SQLException {
        // Fermeture de la connexion après chaque test
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Test
    public void testAddTask() {
        // Ajouter une tâche à la base de données
        String task = "Test Task";
        TaskDAO.addTask(task);
        
        // Vérifier que la tâche a bien été ajoutée
        List<String> tasks = TaskDAO.loadTasks();
        assertTrue(tasks.contains(task), "La tâche n'a pas été correctement ajoutée à la base de données.");
    }

    @Test
    public void testUpdateTask() {
        // Ajouter une tâche, puis la modifier
        String task = "Initial Task";
        TaskDAO.addTask(task);
        List<String> tasks = TaskDAO.loadTasks();
        int taskId = tasks.size(); // On suppose que l'ID de la tâche est basé sur la taille actuelle

        String updatedTask = "Updated Task";
        TaskDAO.updateTask(taskId, updatedTask);

        // Vérifier que la tâche a bien été mise à jour
        tasks = TaskDAO.loadTasks();
        assertTrue(tasks.contains(updatedTask), "La tâche n'a pas été correctement mise à jour.");
        assertFalse(tasks.contains(task), "L'ancienne tâche est toujours présente.");
    }

    @Test
    public void testDeleteTask() {
        // Ajouter une tâche, puis la supprimer
        String task = "Task to be deleted";
        TaskDAO.addTask(task);
        List<String> tasks = TaskDAO.loadTasks();
        int taskId = tasks.size(); // On suppose que l'ID de la tâche est basé sur la taille actuelle

        TaskDAO.deleteTask(taskId);

        // Vérifier que la tâche a bien été supprimée
        tasks = TaskDAO.loadTasks();
        assertFalse(tasks.contains(task), "La tâche n'a pas été correctement supprimée.");
    }
}
