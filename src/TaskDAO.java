import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connect.DatabaseConnection;

public class TaskDAO {

    // Ajouter une tâche dans la base de données
    public static void addTask(String task) {
        String query = "INSERT INTO tasks (task) VALUES (?);";
    
    // Imprimez le message avant d'exécuter la requête pour vérifier que la méthode est appelée et que la tâche est correcte
    System.out.println("Tentative d'ajout de la tâche : " + task);

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {

        // Affichez un message pour vérifier si la connexion a bien été établie
        System.out.println("Connexion établie avec succès.");

        statement.setString(1, task);

        // Imprimez le message juste avant d'exécuter la mise à jour pour vous assurer que la requête est préparée
        System.out.println("Exécution de la requête : " + statement.toString());

        statement.executeUpdate();

        // Si tout fonctionne, imprimer un message indiquant que l'ajout a réussi
        System.out.println("Tâche ajoutée avec succès à la base de données.");

    } catch (SQLException e) {
        // Imprimez l'exception en cas de problème
        System.out.println("Erreur lors de l'ajout de la tâche : " + e.getMessage());
        e.printStackTrace();
    }
    }

    // Mettre à jour une tâche
    public static void updateTask(int id, String newTask) {
        String query = "UPDATE tasks SET task = ? WHERE id = ?;";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, newTask);
            statement.setInt(2, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Supprimer une tâche
    public static void deleteTask(int id) {
        String query = "DELETE FROM tasks WHERE id = ?;";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Charger les tâches depuis la base de données
    public static List<String> loadTasks() {
        List<String> tasks = new ArrayList<>();
        String query = "SELECT task FROM tasks";

        try (Connection connection = DatabaseConnection.getConnection();
             var statement = connection.createStatement();
             var resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                tasks.add(resultSet.getString("task"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }
}
