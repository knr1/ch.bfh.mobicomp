/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerview;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class MainApp extends Application {

    public static void main(String[] args) {
	launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
	Parent root = FXMLLoader.load(getClass().getResource("/fxml/TinkerforgeViewLED.fxml"));

	Scene scene = new Scene(root);
	scene.getStylesheets().add("/styles/Styles.css");

	stage.setTitle("LED-View");
	stage.setScene(scene);
	stage.show();
    }
}
