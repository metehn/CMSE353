package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.sql.Connection;
import java.sql.SQLException;

import database.DatabaseUtilities;

import java.io.IOException;

public class Main extends Application {

    private static Stage stg;

    @Override
    public void start(Stage primaryStage) throws Exception{
        stg = primaryStage;
        primaryStage.setResizable(false);
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("CMSE Office 353");
        primaryStage.setScene(new Scene(root, 960, 600));
        primaryStage.show();
    }


    public void changeScene(String fxml) throws IOException{
        Parent pane =  FXMLLoader.load(getClass().getResource(fxml));
        stg.getScene().setRoot(pane);
    }
    public void changeScene2(Parent pane) throws IOException{
        stg.setScene(new Scene(pane));
    }
    public void changeScene3(Parent pane) throws IOException{

        stg.getScene().setRoot(pane);
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Connection connection = DatabaseUtilities.getConnection();
        System.out.println("Veri tabanı " + connection.getMetaData().getDatabaseProductName());

        launch(args);


    }
}
