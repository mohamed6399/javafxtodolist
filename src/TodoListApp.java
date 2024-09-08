import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TodoListApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Tâche à exécuter");

        // Interface: un champ texte pour entrer la tâche, et des boutons pour ajouter, modifier et supprimer
        TextField taskInput = new TextField();
        taskInput.setPromptText("Nouvelle tâche");

        // Boutons
        Button addButton = new Button("Ajouter");
        addButton.getStyleClass().add("add-button");

        Button editButton = new Button("Modifier");
        editButton.getStyleClass().add("edit-button");

        Button deleteButton = new Button("Supprimer");
        deleteButton.getStyleClass().add("delete-button");

        ListView<String> taskList = new ListView<>();
           // Charger les tâches depuis la base de données
        List<String> tasks = TaskDAO.loadTasks();
        taskList.getItems().addAll(tasks);


        // Ajouter une tâche à la liste
       
        addButton.setOnAction(e -> {
            if (!taskInput.getText().isEmpty()) {
                String task = taskInput.getText();
                TaskDAO.addTask(task); // Enregistrer dans la base de données
                taskList.getItems().add(task); // Ajouter à la liste visuelle
                taskInput.clear();
            }
        });

        // Modifier une tâche sélectionnée
    
        editButton.setOnAction(e -> {
            String selectedTask = taskList.getSelectionModel().getSelectedItem();
            if (selectedTask != null && !taskInput.getText().isEmpty()) {
                int selectedIndex = taskList.getSelectionModel().getSelectedIndex();
                int taskId = getTaskIdByIndex(selectedIndex); // Méthode pour récupérer l'ID de la tâche
                TaskDAO.updateTask(taskId, taskInput.getText()); // Mettre à jour dans la base de données
                taskList.getItems().set(selectedIndex, taskInput.getText()); // Modifier dans la liste visuelle
                taskInput.clear();
            }
        });



        // Supprimer une tâche sélectionnée
      
        deleteButton.setOnAction(e -> {
            String selectedTask = taskList.getSelectionModel().getSelectedItem();
            if (selectedTask != null) {
                int selectedIndex = taskList.getSelectionModel().getSelectedIndex();
                int taskId = getTaskIdByIndex(selectedIndex); // Méthode pour récupérer l'ID de la tâche
                TaskDAO.deleteTask(taskId); // Supprimer de la base de données
                taskList.getItems().remove(selectedTask); // Supprimer de la liste visuelle
            }
        });

        // Disposition des boutons dans une ligne horizontale
        HBox buttonLayout = new HBox(10, addButton, editButton, deleteButton);

        // Disposition de la scène principale
        VBox layout = new VBox(10, taskInput, buttonLayout, taskList);
        Scene scene = new Scene(layout, 800, 600);

        // Charger et appliquer le fichier CSS
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    private int getTaskIdByIndex(int selectedIndex) {
        // Vous devez implémenter cette méthode pour associer les éléments de la liste avec leur ID en base de données
        return selectedIndex + 1; // Exemple simple, à adapter selon votre logique
    }
}
