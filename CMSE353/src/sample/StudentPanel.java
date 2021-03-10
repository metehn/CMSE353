package sample;

//import RSA.RSAUtil;

import RSA.RSAUtil;
import database.DatabaseUtilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class StudentPanel {

    private String private_key;
    private String public_key;

    @FXML
    private Label id_inf;

    @FXML
    private Label name_inf;

    @FXML
    private Label surname_inf;

    @FXML
    private Button open_file;

    @FXML
    private Button delete_file;

    @FXML
    private Button create_file;

    @FXML
    private Button logouta_button;

    @FXML
    private TextArea text_area;

    @FXML
    private Button show_file;

    @FXML
    void create_file(ActionEvent event) {
        create_file();
    }

    @FXML
    void delete_file(ActionEvent event) {
        delete_file();
    }

    @FXML
    void logout_student(ActionEvent event) throws IOException {
        logout_student();
    }

    @FXML
    void open_file(ActionEvent event) {
        open_file();
    }

    @FXML
    void show_file(ActionEvent event) {
        show_folders();
    }

    public void logout_student() throws IOException {
        Main m = new Main();
        m.changeScene("sample.fxml");
    }

    public void delete_file() {
        Stage popupwindow = new Stage();

        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Delete File");

        Label printed_message = new Label();

        Text folderLocation_text = new Text("Enter file name [file.txt]");
        TextField folderLocation_field = new TextField();


        Button button1 = new Button("Close");
        Button button2 = new Button("Apply");


        button2.setOnAction(ee -> {
                    String defaultLocation = "C:/Folders/Students_materials/";
                    defaultLocation += folderLocation_field.getText();
                    Connection connection = null;
                    try {
                        connection = DatabaseUtilities.getConnection();
                        System.out.println("Veri tabanı " + connection.getMetaData().getDatabaseProductName());

                        // String sql = "SELECT id_teacher from teacher where id_teacher=?";
                        String sql = "select * from file where location_file = ? ";

                        PreparedStatement statement = connection.prepareStatement(sql);

                        statement.setString(1, defaultLocation);

                        ResultSet resultset = statement.executeQuery();


                        if (resultset.next()) {
                            String location_file = resultset.getString("location_file");
                            int ownerid_file = resultset.getInt("ownerid_file");
                            System.out.print(location_file);

                            System.out.println(ownerid_file);
                            if (ownerid_file == Integer.parseInt(getId_inf().getText())) {
                                Files.delete(Paths.get(defaultLocation));
                                printed_message.setText("File Deleted");
                                printed_message.setStyle("-fx-text-fill: #00F50D;");

                                try {
                                    String sql2 = "delete from file where location_file = ? ";

                                    PreparedStatement statement2 = connection.prepareStatement(sql2);
                                    statement2.setString(1, defaultLocation);
                                    statement2.executeUpdate();
                                } catch (Exception eee) {
                                    printed_message.setText("File is not deleted, you may not be file owner or file may not be exist");
                                    printed_message.setStyle("-fx-text-fill: #ff0000;");
                                    eee.printStackTrace();
                                }

                            } else {
                                printed_message.setText("File is not deleted, you may not be file owner or file may not be exist");
                                printed_message.setStyle("-fx-text-fill: #ff0000;");
                            }
                        } else {
                            System.out.println("ELSE");
                            printed_message.setText("File is not deleted, you may not be file owner or file may not be exist");
                            printed_message.setStyle("-fx-text-fill: #ff0000;");

                        }
                    } catch (Exception e) {
                        System.out.println("Catch");
                        printed_message.setText("File is not deleted, you may not be file owner or file may not be exist");
                        printed_message.setStyle("-fx-text-fill: #ff0000;");
                        e.printStackTrace();
                    }

                }


        );


        button1.setOnAction(e -> popupwindow.close());


        VBox layout = new VBox(10);


        layout.getChildren().

                addAll(folderLocation_text, folderLocation_field, button1, button2, printed_message);

        layout.setAlignment(Pos.CENTER);

        Scene scene1 = new Scene(layout, 600, 500);

        popupwindow.setScene(scene1);

        popupwindow.showAndWait();


    }

    public void create_file() {
        //String default_location = "C:/Folders/Students’ materials/";
        Stage popupwindow = new Stage();

        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Create File");

        Label printed_message = new Label();

        Text folderLocation_text = new Text("Enter Location [path/to/folder_name]");
        TextField folderLocation_field = new TextField();


        Button button1 = new Button("Close");
        Button button2 = new Button("Apply");


        button2.setOnAction(ee -> {
                    String defaultLocation = "C:/Folders/Students_materials/";
                    defaultLocation += folderLocation_field.getText();
                    Connection connection = null;
                    try {
                        connection = DatabaseUtilities.getConnection();
                        System.out.println("Veri tabanı " + connection.getMetaData().getDatabaseProductName());

                        // String sql = "SELECT id_teacher from teacher where id_teacher=?";
                        String sql = "select id_student,private_key,public_key from student where id_student = ? ";

                        PreparedStatement statement = connection.prepareStatement(sql);

                        statement.setInt(1, Integer.parseInt(id_inf.getText()));

                        ResultSet resultset = statement.executeQuery();

                        String temp_private_key = " ";
                        String temp_public_key = " ";
                        if (resultset.next()) {
                            temp_private_key = resultset.getString("private_key");
                            temp_public_key = resultset.getString("public_key");

                        } else {
                            System.out.println("Error private key, public key not found");
                        }


                        File myObj = new File(defaultLocation);
                        if (myObj.createNewFile()) {
                            printed_message.setText("File Created");
                            printed_message.setStyle("-fx-text-fill: #00F50D;");
                            System.out.println("File created: " + myObj.getName());
                            try {

                                int ownerid_file = Integer.parseInt(getId_inf().getText());
                                String sql2 = "insert into file values(?,?,?,?,?)";


                                PreparedStatement statement2 = connection.prepareStatement(sql2);
                                statement2.setString(1, defaultLocation);
                                statement2.setInt(2, ownerid_file);
                                statement2.setString(3, "student");
                                statement2.setString(4, temp_private_key);
                                statement2.setString(5, temp_public_key);

                                statement2.executeUpdate();


                                connection.close();
                            } catch (Exception eee) {

                            }


                        } else {
                            printed_message.setText("File is not created, an error occurred. File may be already exist");
                            printed_message.setStyle("-fx-text-fill: #ff0000;");
                            System.out.println("File already exists.");
                        }
                    } catch (Exception e) {
                        printed_message.setText("File is not created, an error occurred. File may be already exist");
                        printed_message.setStyle("-fx-text-fill: #ff0000;");
                        System.out.println("An error occurred.");
                        //e.printStackTrace();
                    }

                }


        );


        button1.setOnAction(e -> popupwindow.close());


        VBox layout = new VBox(10);


        layout.getChildren().

                addAll(folderLocation_text, folderLocation_field, button1, button2, printed_message);

        layout.setAlignment(Pos.CENTER);

        Scene scene1 = new Scene(layout, 600, 500);

        popupwindow.setScene(scene1);

        popupwindow.showAndWait();


    }

    public void show_folders() {

        Stage popupwindow = new Stage();

        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Show Files");

        Label printed_message = new Label();

        Text folderLocation_text = new Text("Enter name of the file [path/to/folder_name] write '/' to print Students_materials");
        TextField folderLocation_field = new TextField();


        Button button1 = new Button("Close");
        Button button2 = new Button("Apply");


        button2.setOnAction(ee -> {

                    if (folderLocation_field.getText().isEmpty()) {
                        printed_message.setText("You have to enter the location");
                        printed_message.setStyle("-fx-text-fill: #ff0000;");
                    } else {
                        String string = "";

                        String defaultLocation = "C:/Folders";
                        //Creating a File object for directory
                        try {
                            if (folderLocation_field.getText().equals("/")) {
                                File directoryPath = new File(defaultLocation);
                                String contents[] = directoryPath.list();

                                text_area.setText(string);
                                System.out.println("List of files and directories in the specified directory:");
                                string = "List of files and directories in the specified directory:\n";
                                for (int i = 0; i < contents.length; i++) {
                                    string += contents[i] + "\n";
                                    System.out.println(contents[i]);
                                }
                                text_area.setText(string);
                                printed_message.setText("Printed");
                                printed_message.setStyle("-fx-text-fill: #00F50D;");

                            } else {
                                File directoryPath = new File(defaultLocation + "/" + folderLocation_field.getText());
                                String contents[] = directoryPath.list();

                                text_area.setText(string);
                                System.out.println("List of files and directories in the specified directory:");
                                string = "List of files and directories in the specified directory:\n";
                                for (int i = 0; i < contents.length; i++) {
                                    string += contents[i] + "\n";
                                    System.out.println(contents[i]);
                                }
                                text_area.setText(string);
                                printed_message.setText("Printed");
                                printed_message.setStyle("-fx-text-fill: #00F50D;");

                            }

                        } catch (Exception e) {
                            printed_message.setText("No directory");
                            printed_message.setStyle("-fx-text-fill: #ff0000;");

                        }


                    }

                }
        );


        button1.setOnAction(e -> popupwindow.close());


        VBox layout = new VBox(10);


        layout.getChildren().addAll(folderLocation_text, folderLocation_field, button1, button2, printed_message);

        layout.setAlignment(Pos.CENTER);

        Scene scene1 = new Scene(layout, 600, 500);

        popupwindow.setScene(scene1);

        popupwindow.showAndWait();

    }

    public void open_file() {


        Stage popupwindow = new Stage();

        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Open File");

        Label printed_message = new Label();

        Text folderLocation_text = new Text("Enter file name [file.txt]");
        TextField folderLocation_field = new TextField();


        Button button1 = new Button("Close");
        Button button2 = new Button("Apply");


        button2.setOnAction(ee -> {
                    //String defaultLocation = "C:/Folders/Students_materials/";
                    String defaultLocation = "C:/Folders/";
                    defaultLocation += folderLocation_field.getText();
                    Connection connection = null;
                    try {
                        connection = DatabaseUtilities.getConnection();
                        System.out.println("Veri tabanı " + connection.getMetaData().getDatabaseProductName());

                        // String sql = "SELECT id_teacher from teacher where id_teacher=?";
                        String sql = "select * from file where location_file = ? ";

                        PreparedStatement statement = connection.prepareStatement(sql);

                        statement.setString(1, defaultLocation);

                        ResultSet resultset = statement.executeQuery();


                        if (resultset.next()) {
                            String location_file = resultset.getString("location_file");
                            String ownergroup_file = resultset.getString("ownergroup_file");
                            int ownerid_file = resultset.getInt("ownerid_file");
                            String private_key = resultset.getString("private_key");
                            String public_key = resultset.getString("public_key");
                            String[] list4 = defaultLocation.split("/");
                            String index2 = list4[2];
                            if (ownerid_file == Integer.parseInt(getId_inf().getText())) {
                                //Read the file

                                String s = Files.readString(Paths.get(defaultLocation));

                                if (!s.isEmpty()) {

                                    try {


                                        RSAUtil rsaUtil = new RSAUtil();
                                        // String message = rsaUtil.decrypt(s,private_key);
                                        String message = rsaUtil.deneme_decrypt(s, private_key);
                                        System.out.print(message);

                                        FXMLLoader loader = new FXMLLoader(getClass().getResource("open_file.fxml"));
                                        Parent root = loader.load();
                                        open_file controller = loader.getController();

                                        controller.setText_area(message);
                                        controller.setId(Integer.parseInt(id_inf.getText()));
                                        controller.setName(name_inf.getText());
                                        controller.setSurname(surname_inf.getText());
                                        controller.setGroup("student");
                                        controller.setPrivate_key(private_key);
                                        controller.setPublic_key(public_key);
                                        controller.setLocation(defaultLocation);
                                        controller.setAccess("student");

                                        Main m = new Main();
                                        popupwindow.close();
                                        m.changeScene3(root);

                                    } catch (Exception eee) {
                                        System.out.println("File is empty");
                                        eee.printStackTrace();
                                    }
                                } else if (s.isEmpty()) {
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("open_file.fxml"));
                                    Parent root = loader.load();
                                    open_file controller = loader.getController();

                                    controller.setText_area("");
                                    controller.setId(Integer.parseInt(id_inf.getText()));
                                    controller.setName(name_inf.getText());
                                    controller.setSurname(surname_inf.getText());
                                    controller.setGroup("student");
                                    controller.setPrivate_key(private_key);
                                    controller.setPublic_key(public_key);
                                    controller.setLocation(defaultLocation);
                                    controller.setAccess("student");

                                    Main m = new Main();
                                    popupwindow.close();
                                    m.changeScene3(root);
                                } else {
                                    System.out.println("File is empty");
                                }


                                // Decript the file

                                //Show the content of file


                            } else if (ownergroup_file.equals("teacher") && index2.equals("Course_materials")) { //(ownergroup_file.equals("teacher")
                                ////!!!!!!
                                String s = Files.readString(Paths.get(defaultLocation));

                                if (!s.isEmpty()) {

                                    try {


                                        RSAUtil rsaUtil = new RSAUtil();
                                        // String message = rsaUtil.decrypt(s,private_key);
                                        String message = rsaUtil.deneme_decrypt(s, private_key);
                                        System.out.print(message);

                                        FXMLLoader loader = new FXMLLoader(getClass().getResource("open_file.fxml"));
                                        Parent root = loader.load();
                                        open_file controller = loader.getController();
/////!!!!!!
                                        controller.setText_area(message);
                                        controller.setId(Integer.parseInt(id_inf.getText()));
                                        ArrayList<String> list2 =  is_teacher_exist(ownerid_file,"");
                                        String temp_name = list2.get(1);
                                        String temp_surname = list2.get(2);


                                        controller.setName(name_inf.getText());
                                        controller.setSurname(surname_inf.getText());
                                        controller.setGroup("student");
                                        controller.setPrivate_key(private_key);
                                        controller.setPublic_key(public_key);
                                        controller.setLocation(defaultLocation);
                                        controller.setAccess("read");

                                        Main m = new Main();
                                        popupwindow.close();
                                        m.changeScene3(root);

                                    } catch (Exception eee) {
                                        System.out.println("File is empty");
                                        eee.printStackTrace();
                                    }
                                } else if (s.isEmpty()) {
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("open_file.fxml"));
                                    Parent root = loader.load();
                                    open_file controller = loader.getController();

                                    controller.setText_area("");
                                    controller.setId(Integer.parseInt(id_inf.getText()));
                                    controller.setName(name_inf.getText());
                                    controller.setSurname(surname_inf.getText());
                                    controller.setGroup("student");
                                    controller.setPrivate_key(private_key);
                                    controller.setPublic_key(public_key);
                                    controller.setAccess("read");

                                    controller.setLocation(defaultLocation);

                                    Main m = new Main();
                                    popupwindow.close();
                                    m.changeScene3(root);
                                } else {
                                    System.out.println("File is empty");
                                }


                            } else if (ownerid_file != Integer.parseInt(id_inf.getText())) {
                                printed_message.setText("File is not opened, you are not  file owner");
                                printed_message.setStyle("-fx-text-fill: #ff0000;");
                            } else {
                                System.out.println(location_file);
                                printed_message.setText("File is not opened, you may not be file owner or file may not be exist");
                                printed_message.setStyle("-fx-text-fill: #ff0000;");
                            }
                        } else {
                            System.out.println("ELSE");
                            printed_message.setText("File is not deleted, you may not be file owner or file may not be exist");
                            printed_message.setStyle("-fx-text-fill: #ff0000;");

                        }
                    } catch (Exception e) {
                        System.out.println("Catch");
                        printed_message.setText("File is not deleted, you may not be file owner or file may not be exist");
                        printed_message.setStyle("-fx-text-fill: #ff0000;");
                        e.printStackTrace();
                    }

                }


        );


        button1.setOnAction(e -> popupwindow.close());


        VBox layout = new VBox(10);


        layout.getChildren().addAll(folderLocation_text, folderLocation_field, button1, button2, printed_message);

        layout.setAlignment(Pos.CENTER);

        Scene scene1 = new Scene(layout, 600, 500);

        popupwindow.setScene(scene1);

        popupwindow.showAndWait();


    }
    public ArrayList<String> is_teacher_exist(Integer id_teacher, String password_teacher) throws SQLException, ClassNotFoundException {

        Connection connection = DatabaseUtilities.getConnection();
        System.out.println("Veri tabanı " + connection.getMetaData().getDatabaseProductName());

        String sql = "SELECT * from teacher where id_teacher =? ";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1,id_teacher);


        ResultSet resultset = statement.executeQuery();

        ArrayList<String> list = new ArrayList<>();

        if(resultset.next()){
            String id = String.valueOf(resultset.getInt("id_teacher"));
            String name = resultset.getString("name_teacher");
            String surname = resultset.getString("surname_teacher");
            String private_key = resultset.getString("private_key");
            String public_key = resultset.getString("public_key");
            list.add(id);
            list.add(name);
            list.add(surname);
            list.add(private_key);
            list.add(public_key);
            connection.close();
            return list;

        }
        else{
            connection.close();
            return list;
        }


    }

    public Label getId_inf() {
        return id_inf;
    }

    public void setId_inf(String a) {
        id_inf.setText(a);
    }

    public Label getName_inf() {
        return name_inf;
    }

    public void setName_inf(String a) {
        name_inf.setText(a);
    }

    public Label getSurname_inf() {
        return surname_inf;
    }

    public void setSurname_inf(String a) {
        surname_inf.setText(a);
    }

    public String getPrivate_key() {
        return private_key;
    }

    public void setPrivate_key(String private_key) {
        this.private_key = private_key;
    }

    public String getPublic_key() {
        return public_key;
    }

    public void setPublic_key(String public_key) {
        this.public_key = public_key;
    }
}
