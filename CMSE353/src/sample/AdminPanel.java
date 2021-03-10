package sample;


//import RSA.RSAUtil;
import RSA.RSAUtil;
import classes.Keys;
import database.DatabaseUtilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.io.File;
import java.util.ArrayList;


public class AdminPanel {



    @FXML
    private Label id_inf;
    @FXML
    private Label name_inf;

    @FXML
    private Label surname_inf;

    @FXML
    private Button deletes_button;

    @FXML
    private Button deletet_button;

    @FXML
    private TextArea text_area;


    @FXML
    private Button teacherl_button;

    @FXML
    private Button studentl_button;

    @FXML
    private Button cfolder_button;
    @FXML
    private Button dfolder_button;

    @FXML
    private Button fol_button;

    @FXML
    private Button addt_button;

    @FXML
    private Button adds_button;

    @FXML
    private Button logouta_button;

    @FXML
    void delete_student(ActionEvent event) {
        delete_student();
    }

    @FXML
    void delete_teacher(ActionEvent event) {
        delete_teacher();
    }

    @FXML
    void logout_admin(ActionEvent event) throws IOException {
        logout_admin();

    }

    @FXML
    void show_Teacher_List(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        show_teacher();
    }

    @FXML
    void show_Student_List(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        show_student();
    }

    @FXML
    void add_student(ActionEvent event) throws SQLException, ClassNotFoundException {

        Stage popupwindow = new Stage();

        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Add Student");

        Label printed_message = new Label();

        Text student_id_text = new Text("Id");
        TextField student_id = new TextField(); //Cascade to int

        Text student_name_text = new Text("Name");
        TextField student_name = new TextField();

        Text student_surname_text = new Text("Surname");
        TextField student_surname = new TextField();

        Text student_password_text = new Text("Password for Student");
        TextField student_password = new TextField();




        Button button1 = new Button("Close");
        Button button2 = new Button("Apply");


        button2.setOnAction(e -> {
            int temp_student_id = 0;

            try {
                temp_student_id = Integer.parseInt(student_id.getText());
            } catch (Exception exc) {

            }

            String temp_student_name = student_name.getText();
            String temp_student_surname = student_surname.getText();
            String temp_student_password = student_password.getText();
            String temp_student_group = "student";

            if (student_id.getText().isEmpty() || student_name.getText().isEmpty() || student_surname.getText().isEmpty() || student_password.getText().isEmpty()) {
                printed_message.setText("You have to enter all data ");
                printed_message.setStyle("-fx-text-fill: #ff0000;");
            } else {

                Connection connection = null;
                try {
                    connection = DatabaseUtilities.getConnection();
                    System.out.println("Veri tabanı " + connection.getMetaData().getDatabaseProductName());

                    // String sql = "SELECT id_teacher from teacher where id_teacher=?";
                    String sql = "select id_admin from admin where id_admin = ? UNION ALL select id_teacher from teacher where id_teacher = ? UNION ALL select id_student from student  where id_student = ?";

                    PreparedStatement statement = connection.prepareStatement(sql);

                    statement.setInt(1, temp_student_id); //önceden set setLong'du setInt yaptım...
                    statement.setInt(2, temp_student_id);
                    statement.setInt(3, temp_student_id);


                    ResultSet resultset = statement.executeQuery();


                    if (resultset.next()) {
                        printed_message.setText("There is already user with this id ");
                        printed_message.setStyle("-fx-text-fill: #ff0000;");
                    } else {
                        String temp_publickey;
                        String temp_privatekey;


                        RSAUtil rsautil = new RSAUtil();
                        try {

                            ArrayList<String> list = new ArrayList<String>();
                            list = RSAUtil.deneme_getkeys();
                            System.out.println("DENEMEKEY");
                            System.out.println(list.get(0));
                            System.out.println(list.get(1));

                            temp_privatekey = list.get(0);
                            temp_publickey= list.get(1);


                            String sql4 = "INSERT INTO student(id_student, name_student, surname_student, password_student,group_student, private_key, public_key ) VALUES(?, ?, ?, ?, ?, ?, ?)";
                            PreparedStatement statement4 = connection.prepareStatement(sql4);
                            statement4.setInt(1, temp_student_id);
                            statement4.setString(2, temp_student_name);
                            statement4.setString(3, temp_student_surname);
                            statement4.setString(4, temp_student_password);
                            statement4.setString(5, temp_student_group);
                            statement4.setString(6, temp_privatekey);
                            statement4.setString(7, temp_publickey);

                            statement4.executeUpdate();


                        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                            noSuchAlgorithmException.printStackTrace();
                        }


                        printed_message.setText("Created successfully");
                        printed_message.setStyle("-fx-text-fill: #00F50D;");


                    }


                    connection.close();

                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            } // else bloğunun bittiği parantez...
        });

        button1.setOnAction(e -> popupwindow.close());


        VBox layout = new VBox(10);


        layout.getChildren().addAll(student_id_text, student_id, student_name_text, student_name, student_surname_text, student_surname, student_password_text, student_password, button1, button2, printed_message);

        layout.setAlignment(Pos.CENTER);

        Scene scene1 = new Scene(layout, 600, 500);

        popupwindow.setScene(scene1);

        popupwindow.showAndWait();

    }

    @FXML
    public void show_folders(ActionEvent event) {

        show_folders();
    }

    @FXML
    public void create_folder(ActionEvent event) {
        create_folder();

    }

    @FXML
    public void delete_folder(ActionEvent event) {
        delete_folder();

    }

    @FXML
    void add_Teacher(ActionEvent event) throws SQLException, ClassNotFoundException {


        Stage popupwindow = new Stage();

        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Add Teacher");

        Label printed_message = new Label();

        Text teacher_id_text = new Text("Id");
        TextField teacher_id = new TextField(); //Cascade to int

        Text teacher_name_text = new Text("Name");
        TextField teacher_name = new TextField();

        Text teacher_surname_text = new Text("Surname");
        TextField teacher_surname = new TextField();

        Text teacher_password_text = new Text("Password for Teacher");
        TextField teacher_password = new TextField();

        //generating private key and public key


        Button button1 = new Button("Close");
        Button button2 = new Button("Apply");


        button2.setOnAction(e -> {
            int temp_teacher_id = 0;

            try {
                temp_teacher_id = Integer.parseInt(teacher_id.getText());
            } catch (Exception exc) {

            }

            String temp_teacher_name = teacher_name.getText();
            String temp_teacher_surname = teacher_surname.getText();
            String temp_teacher_password = teacher_password.getText();
            String temp_teacher_group = "teacher";

            if (teacher_id.getText().isEmpty() || teacher_name.getText().isEmpty() || teacher_surname.getText().isEmpty() || teacher_password.getText().isEmpty()) {
                printed_message.setText("You have to enter all data ");
                printed_message.setStyle("-fx-text-fill: #ff0000;");
            } else {

                Connection connection = null;
                try {
                    connection = DatabaseUtilities.getConnection();
                    System.out.println("Veri tabanı " + connection.getMetaData().getDatabaseProductName());

                    // String sql = "SELECT id_teacher from teacher where id_teacher=?";
                    String sql = "select id_admin from admin where id_admin = ? UNION ALL select id_teacher from teacher where id_teacher = ? UNION ALL select id_student from student  where id_student = ?";

                    PreparedStatement statement = connection.prepareStatement(sql);

                    statement.setInt(1, temp_teacher_id); //önceden set setLong'du setInt yaptım...
                    statement.setInt(2, temp_teacher_id);
                    statement.setInt(3, temp_teacher_id);


                    ResultSet resultset = statement.executeQuery();


                    if (resultset.next()) {
                        printed_message.setText("There is already user with this id ");
                        printed_message.setStyle("-fx-text-fill: #ff0000;");
                    } else {
                        String temp_publickey;
                        String temp_privatekey;


                        RSAUtil rsautil = new RSAUtil();
                        try {

                           Keys keys = rsautil.get_keys();

                            temp_publickey = keys.getPublic_key();
                            temp_privatekey = keys.getPrivate_key();





                            String sql4 = "INSERT INTO teacher(id_teacher, name_teacher, surname_teacher, password_teacher,group_teacher, private_key, public_key ) VALUES(?, ?, ?, ?, ?, ?, ?)";
                            PreparedStatement statement4 = connection.prepareStatement(sql4);
                            statement4.setInt(1, temp_teacher_id);
                            statement4.setString(2, temp_teacher_name);
                            statement4.setString(3, temp_teacher_surname);
                            statement4.setString(4, temp_teacher_password);
                            statement4.setString(5, temp_teacher_group);
                            statement4.setString(6, temp_privatekey);
                            statement4.setString(7, temp_publickey);

                            statement4.executeUpdate();


                        } catch (IllegalBlockSizeException illegalBlockSizeException) {
                            illegalBlockSizeException.printStackTrace();
                        } catch (InvalidKeyException invalidKeyException) {
                            invalidKeyException.printStackTrace();
                        } catch (NoSuchPaddingException noSuchPaddingException) {
                            noSuchPaddingException.printStackTrace();
                        } catch (BadPaddingException badPaddingException) {
                            badPaddingException.printStackTrace();
                        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                            noSuchAlgorithmException.printStackTrace();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }


                        printed_message.setText("Created successfully");
                        printed_message.setStyle("-fx-text-fill: #00F50D;");


                    }


                    connection.close();

                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            } // else bloğunun bittiği parantez...
        });

        button1.setOnAction(e -> popupwindow.close());


        VBox layout = new VBox(10);


        layout.getChildren().addAll(teacher_id_text, teacher_id, teacher_name_text, teacher_name, teacher_surname_text, teacher_surname, teacher_password_text, teacher_password, button1, button2, printed_message);

        layout.setAlignment(Pos.CENTER);

        Scene scene1 = new Scene(layout, 600, 500);

        popupwindow.setScene(scene1);

        popupwindow.showAndWait();
    }

    public void delete_folder() {
        Stage popupwindow = new Stage();

        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Delete Folder");

        Label printed_message = new Label();

        Text folderLocation_text = new Text("Enter Location [path/to/folder_name] note:every thing under that file will be deleted");
        TextField folderLocation_field = new TextField();


        Button button1 = new Button("Close");
        Button button2 = new Button("Apply");


        button2.setOnAction(ee -> {

                    if (folderLocation_field.getText().isEmpty()) {
                        printed_message.setText("You have to enter the location");
                        printed_message.setStyle("-fx-text-fill: #ff0000;");
                    } else {
                        String defaultLocation = "C:/Folders/";

                        defaultLocation += folderLocation_field.getText();


                        //Creating the File object
                        File file = new File(defaultLocation);
                        try {
                            delete_folder2(file);
                            printed_message.setText("Files deleted");
                            printed_message.setStyle("-fx-text-fill: #00F50D;");
                            System.out.println("Files deleted");
                        } catch (Exception e) {
                            printed_message.setText("Files not deleted");
                            printed_message.setStyle("-fx-text-fill: #ff0000;");
                            System.out.println("Files not deleted");
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

    public void delete_folder2(File file) {

        for (File subFile : file.listFiles()) {
            if (subFile.isDirectory()) {
                delete_folder2(subFile);
            } else {
                subFile.delete();
            }
        }
        file.delete();
    }


    public void create_folder() {


        Stage popupwindow = new Stage();

        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Create Folder");

        Label printed_message = new Label();

        Text folderLocation_text = new Text("Enter Location [path/to/folder_name]");
        TextField folderLocation_field = new TextField();


        Button button1 = new Button("Close");
        Button button2 = new Button("Apply");


        button2.setOnAction(ee -> {

                    if (folderLocation_field.getText().isEmpty()) {
                        printed_message.setText("You have to enter the location");
                        printed_message.setStyle("-fx-text-fill: #ff0000;");
                    } else {


                        //String defaultLocation = "C:/Users/meteh/IdeaProjects/CMSE353/src/Folders/"; //location of code source
                        String defaultLocation = "C:/Folders/";
                        File ff = new File("C:/Folders");
                        try {
                            if (ff.mkdirs()) {
                                printed_message.setText("Directory Created");
                                printed_message.setStyle("-fx-text-fill: #00F50D;");
                            } else {
                                printed_message.setText("Directory is not created");
                                printed_message.setStyle("-fx-text-fill: #ff0000;");
                            }
                        } catch (Exception eee) {
                            eee.printStackTrace();
                        }
                        defaultLocation += folderLocation_field.getText();
                        File f = new File(defaultLocation);
                        try {
                            if (f.mkdirs()) {
                                printed_message.setText("Directory Created");
                                printed_message.setStyle("-fx-text-fill: #00F50D;");
                            } else {
                                printed_message.setText("Directory is not created");
                                printed_message.setStyle("-fx-text-fill: #ff0000;");

                            }
                        } catch (Exception eee) {
                            eee.printStackTrace();
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

    public void show_folders() {

        Stage popupwindow = new Stage();

        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Show Folder");

        Label printed_message = new Label();

        Text folderLocation_text = new Text("Enter Location [path/to/folder_name] write '/' to print main folder");
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

    private void show_teacher() throws SQLException, ClassNotFoundException, IOException {

        Connection connection = DatabaseUtilities.getConnection();
        System.out.println("Veri tabanı " + connection.getMetaData().getDatabaseProductName());

        String sql = "SELECT * from teacher";
        PreparedStatement statement = connection.prepareStatement(sql);

        ResultSet resultset = statement.executeQuery();
        String string = "";
        while (resultset.next()) {
            string += String.valueOf(resultset.getInt("id_teacher")) + " " + " " + resultset.getString("name_teacher") + " " + " " + resultset.getString("surname_teacher") + "\n";

            text_area.setText(string);

        }


        connection.close();

    }

    private void show_student() throws SQLException, ClassNotFoundException, IOException {
        Connection connection = DatabaseUtilities.getConnection();
        System.out.println("Veri tabanı " + connection.getMetaData().getDatabaseProductName());

        String sql = "SELECT * from student";
        PreparedStatement statement = connection.prepareStatement(sql);

        ResultSet resultset = statement.executeQuery();
        String string = "";
        while (resultset.next()) {
            string += String.valueOf(resultset.getInt("id_student")) + " " + " " + resultset.getString("name_student") + " " + " " + resultset.getString("surname_student") + "\n";

            text_area.setText(string);

        }


        connection.close();

    }

    public void logout_admin() throws IOException {
        Main m = new Main();
        m.changeScene("sample.fxml");
    }

    public void delete_teacher() {

        Stage popupwindow = new Stage();

        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Delete Teacher");

        Label printed_message = new Label();

        Text teacher_id_text = new Text("Id");
        TextField teacher_id = new TextField();


        Button button1 = new Button("Close");
        Button button2 = new Button("Apply");


        button2.setOnAction(e -> {
            int temp_teacher_id = 0;

            try {
                temp_teacher_id = Integer.parseInt(teacher_id.getText());
            } catch (Exception exc) {

            }


            if (teacher_id.getText().isEmpty()) {
                printed_message.setText("You have to enter all data");
                printed_message.setStyle("-fx-text-fill: #ff0000;");
            } else {

                Connection connection = null;
                try {
                    connection = DatabaseUtilities.getConnection();
                    System.out.println("Veri tabanı " + connection.getMetaData().getDatabaseProductName());

                    // String sql = "SELECT id_teacher from teacher where id_teacher=?";
                    String sql = "select id_teacher from teacher where id_teacher = ?";

                    PreparedStatement statement = connection.prepareStatement(sql);

                    statement.setInt(1, temp_teacher_id);


                    ResultSet resultset = statement.executeQuery();


                    if (resultset.next()) {

                        String sql4 = "DELETE FROM teacher where id_teacher = ?";
                        PreparedStatement statement4 = connection.prepareStatement(sql4);
                        statement4.setInt(1, temp_teacher_id);

                        statement4.executeUpdate();

                        printed_message.setText("Deleted successfully");
                        printed_message.setStyle("-fx-text-fill: #00F50D;");

                    } else {

                        printed_message.setText("No teacher with this id ");
                        printed_message.setStyle("-fx-text-fill: #ff0000;");

                    }


                    connection.close();

                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            } // else bloğunun bittiği parantez...
        });

        button1.setOnAction(e -> popupwindow.close());


        VBox layout = new VBox(10);


        layout.getChildren().addAll(teacher_id_text, teacher_id, button1, button2, printed_message);

        layout.setAlignment(Pos.CENTER);

        Scene scene1 = new Scene(layout, 600, 500);

        popupwindow.setScene(scene1);

        popupwindow.showAndWait();

    }

    public void delete_student() {

        Stage popupwindow = new Stage();

        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Delete Student");

        Label printed_message = new Label();

        Text student_id_text = new Text("Id");
        TextField student_id = new TextField();


        Button button1 = new Button("Close");
        Button button2 = new Button("Apply");


        button2.setOnAction(e -> {
            int temp_student_id = 0;

            try {
                temp_student_id = Integer.parseInt(student_id.getText());
            } catch (Exception exc) {

            }


            if (student_id.getText().isEmpty()) {
                printed_message.setText("You have to enter all data");
                printed_message.setStyle("-fx-text-fill: #ff0000;");
            } else {

                Connection connection = null;
                try {
                    connection = DatabaseUtilities.getConnection();
                    System.out.println("Veri tabanı " + connection.getMetaData().getDatabaseProductName());

                    // String sql = "SELECT id_teacher from teacher where id_teacher=?";
                    String sql = "select id_student from student where id_student = ?";

                    PreparedStatement statement = connection.prepareStatement(sql);

                    statement.setInt(1, temp_student_id);


                    ResultSet resultset = statement.executeQuery();


                    if (resultset.next()) {

                        String sql4 = "DELETE FROM student where id_student = ?";
                        PreparedStatement statement4 = connection.prepareStatement(sql4);
                        statement4.setInt(1, temp_student_id);

                        statement4.executeUpdate();

                        printed_message.setText("Deleted successfully");
                        printed_message.setStyle("-fx-text-fill: #00F50D;");

                    } else {

                        printed_message.setText("No student with this id ");
                        printed_message.setStyle("-fx-text-fill: #ff0000;");

                    }


                    connection.close();

                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            } // else bloğunun bittiği parantez...
        });

        button1.setOnAction(e -> popupwindow.close());


        VBox layout = new VBox(10);


        layout.getChildren().addAll(student_id_text, student_id, button1, button2, printed_message);

        layout.setAlignment(Pos.CENTER);

        Scene scene1 = new Scene(layout, 600, 500);

        popupwindow.setScene(scene1);

        popupwindow.showAndWait();

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
}
