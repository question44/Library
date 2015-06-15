package com.limanowa.library;

import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/* Pierwszy test GIT-a */

public class MainApp extends Application {
    @Override
   public void start(Stage stage) throws Exception {
       
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainPrimaryWindow.fxml"));
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        
        stage.setTitle("Aplikacja domowej biblioteki");
        stage.setScene(scene);
        stage.show();
        
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
