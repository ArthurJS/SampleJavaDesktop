import domein.DomeinController;
import CUI.SokobanApplicatie;
import gui.HoofdPaneelController;
import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class StartUp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        DomeinController dc = new DomeinController();
        //
        new SokobanApplicatie().start(dc);

        HoofdPaneelController root = new HoofdPaneelController(dc);

        Scene scene = new Scene(root);

        primaryStage.setTitle("Sokémon - Gotta push 'em all!");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("gui/images/S-logo.png")));
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
        /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
