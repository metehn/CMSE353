package sample;




import database.CheckDatabase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LogIn  {

    public LogIn() {

    }

    @FXML
    private TextField userid;

    @FXML
    private PasswordField password;

    @FXML
    private Button loginbutton;

    @FXML
    private Label wronglogin;

    @FXML
    void userLogIn(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
            checkLogin();
    }


    private void checkLogin() throws IOException, SQLException, ClassNotFoundException {
        Main m = new Main();
        CheckDatabase checkDatabase = new CheckDatabase();


/*
        if(userid.getText().toString().equals("123") && password.getText().toString().equals("123")) {
            wronglogin.setText("Success!");

            m.changeScene("adminPanel.fxml");
        }
*/
        ArrayList<String> list = checkDatabase.is_admin_exist(Integer.parseInt(userid.getText()) , password.getText().toString() );
        ArrayList<String> list2 = checkDatabase.is_teacher_exist(Integer.parseInt(userid.getText()) , password.getText().toString());
        ArrayList<String> list3 = checkDatabase.is_student_exist(Integer.parseInt(userid.getText()) , password.getText().toString());

        if(userid.getText().isEmpty() && password.getText().isEmpty()) {
            wronglogin.setText("Please enter your data.");
            wronglogin.setStyle("-fx-text-fill: #ff0000;");
        }
        else if(list.size() > 0){
            //deneme amaçlı yazdığım kod
            //FXMLLoader loader = new FXMLLoader(getClass().getResource("C:\\Users\\meteh\\IdeaProjects\\CMSE353\\src\\sample\\adminPanel.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("adminPanel.fxml"));
            Parent root = loader.load();
            AdminPanel controller = loader.getController();

            controller.setId_inf(userid.getText());
            controller.setName_inf(list.get(1));
            controller.setSurname_inf(list.get(2));



            m.changeScene3(root);
          //  Stage stage = new Stage(StageStyle.DECORATED);
           // stage.setScene(new Scene(root));
           // stage.show();


            wronglogin.setText("Success!");
            wronglogin.setStyle("-fx-text-fill: #00F50D;");
           // m.changeScene("adminPanel.fxml");


        }
        else if( list2.size() >0){

            FXMLLoader loader = new FXMLLoader(getClass().getResource("teacherPanel.fxml"));
            Parent root = loader.load();
            TeacherPanel controller = loader.getController();



            controller.setId_inf(userid.getText());
            controller.setName_inf(list2.get(1));
            controller.setSurname_inf(list2.get(2));
            controller.setPrivate_key(list2.get(3));
            controller.setPublic_key(list2.get(4));

            wronglogin.setText("Success!");
            wronglogin.setStyle("-fx-text-fill: #00F50D;");

            m.changeScene3(root);
        }
        else if(list3.size() >0){

            //deneme amaçlı yazdığım kod
            //FXMLLoader loader = new FXMLLoader(getClass().getResource("C:\\Users\\meteh\\IdeaProjects\\CMSE353\\src\\sample\\adminPanel.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("studentPanel.fxml"));
            Parent root = loader.load();
            StudentPanel controller = loader.getController();


            controller.setId_inf(userid.getText());
            controller.setName_inf(list3.get(1));
            controller.setSurname_inf(list3.get(2));
            controller.setPrivate_key(list3.get(3));
            controller.setPublic_key(list3.get(4));

            wronglogin.setText("Success!");
            wronglogin.setStyle("-fx-text-fill: #00F50D;");

            m.changeScene3(root);
            /*
              Stage stage = new Stage(StageStyle.DECORATED);
             stage.setScene(new Scene(root));
             stage.show();


            wronglogin.setText("Success!");
            wronglogin.setStyle("-fx-text-fill: #00F50D;");

            m.changeScene("studentPanel.fxml");

             */
        }

        else if(userid.getText().isEmpty() && password.getText().isEmpty()) {
            wronglogin.setText("Please enter your data.");
            wronglogin.setStyle("-fx-text-fill: #ff0000;");
        }

        else {
            wronglogin.setText("Wrong username or password!");
            wronglogin.setStyle("-fx-text-fill: #ff0000;");
        }
    }

}
