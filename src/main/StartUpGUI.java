package main;

import domein.DomeinController;
import gui.TaalschermController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartUpGUI extends Application {
	private DomeinController dc;

	@Override
	public void start(Stage primaryStage) {
		try {
			dc = new DomeinController();
			Scene scene = new Scene(new TaalschermController(primaryStage, dc), 600, 400);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}