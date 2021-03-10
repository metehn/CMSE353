package sample;


import RSA.RSAUtil;
import database.DatabaseUtilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class open_file {
    private int id;
    private String name;
    private String surname;
    private String group;
    private String private_key;
    private String public_key;
    private String location;
    private String teacher_id;
    private String teacher_name;
    private String teacher_surname;

    private String access = " ";

    @FXML
    private Label wrong;

    @FXML
    private TextArea text_area;

    @FXML
    private Button save_button;

    @FXML
    private Button cancel_button;

    @FXML
    void cancel(ActionEvent event) throws IOException {
        cancel();
    }

    @FXML
    void save(ActionEvent event) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, IOException {
            save();
    }
    public void save() throws IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IOException {

        if(text_area.getText().isEmpty()){
            wrong.setText("It can't be empty");
            wrong.setStyle("-fx-text-fill: #ff0000;");
            System.out.println("It can't be empty");

        }/*
        Connection connection = null;

        try {
            connection = DatabaseUtilities.getConnection();
            System.out.println("Veri tabanı " + connection.getMetaData().getDatabaseProductName());
            String sql = "select * from file where location_file = ? ";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, location);

            ResultSet resultset = statement.executeQuery();


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        else if(group=="student" && ){


            //database git file owner ile
        }*/
        else if (getAccess().equals("read")){

            wrong.setText("You are not allowed to save this file");
            wrong.setStyle("-fx-text-fill: #ff0000;");
            System.out.println("You are not allowed to save this file");
        }
        else if(access.equals("teacher")){

            String message = text_area.getText();
            // String message = "METEHAN";

            RSAUtil rsaUtil = new RSAUtil();

            //String encryptedString = Base64.getEncoder().encodeToString(encrypt("DENEME DENEME", public_key));
            //System.out.println(encryptedString);
            //String encryptedString = RSAUtil.deneme_encrypt(message, public_key);
            String encripted = rsaUtil.deneme_encrypt(message, public_key);
            System.out.println(encripted);

            // PublicKey: "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC809lIgQcpPEB79ncKbbfjyRCYIVUpqAKZK09Wv8AAbHpxC4pc0twcEqFnXGSs""
            // PrivateKey: "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALzT2UiBByk8QHv2dwptt+PJEJghVSmoApkrT1a/wABsenELilzS"


            //String data = new String(encrypt(message, public_key), StandardCharsets.UTF_8);

            FileWriter fw = new FileWriter(location, false);
            fw.write(encripted);
            fw.flush();
            fw.close();
            wrong.setText("Success!");
            wrong.setStyle("-fx-text-fill: #00F50D;");




        }
        else if(access.equals("student")){

            String message = text_area.getText();
           // String message = "METEHAN";

            RSAUtil rsaUtil = new RSAUtil();

            //String encryptedString = Base64.getEncoder().encodeToString(encrypt("DENEME DENEME", public_key));
            //System.out.println(encryptedString);
            //String encryptedString = RSAUtil.deneme_encrypt(message, public_key);
            String encripted = rsaUtil.deneme_encrypt(message, public_key);
            System.out.println(encripted);

           // PublicKey: "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC809lIgQcpPEB79ncKbbfjyRCYIVUpqAKZK09Wv8AAbHpxC4pc0twcEqFnXGSs""
           // PrivateKey: "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALzT2UiBByk8QHv2dwptt+PJEJghVSmoApkrT1a/wABsenELilzS"


            //String data = new String(encrypt(message, public_key), StandardCharsets.UTF_8);

            FileWriter fw = new FileWriter(location, false);
            fw.write(encripted);
            fw.flush();
            fw.close();
            wrong.setText("Success!");
            wrong.setStyle("-fx-text-fill: #00F50D;");

        }else if(access.equals("teacher_student")){
            String message = text_area.getText();
            // String message = "METEHAN";

            RSAUtil rsaUtil = new RSAUtil();

            //String encryptedString = Base64.getEncoder().encodeToString(encrypt("DENEME DENEME", public_key));
            //System.out.println(encryptedString);
            //String encryptedString = RSAUtil.deneme_encrypt(message, public_key);
            String encripted = rsaUtil.deneme_encrypt(message, public_key);
            System.out.println(encripted);

            // PublicKey: "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC809lIgQcpPEB79ncKbbfjyRCYIVUpqAKZK09Wv8AAbHpxC4pc0twcEqFnXGSs""
            // PrivateKey: "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALzT2UiBByk8QHv2dwptt+PJEJghVSmoApkrT1a/wABsenELilzS"


            //String data = new String(encrypt(message, public_key), StandardCharsets.UTF_8);

            FileWriter fw = new FileWriter(location, false);
            fw.write(encripted);
            fw.flush();
            fw.close();
            wrong.setText("Success!");
            wrong.setStyle("-fx-text-fill: #00F50D;");

        }
        else{
            System.out.println("ELSE BLOGU");
        }

    }

    public void cancel() throws IOException {

    if(access.equals("student") || access.equals("read") ) {
        Main m = new Main();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("studentPanel.fxml"));
        Parent root = loader.load();
        StudentPanel controller = loader.getController();


        controller.setId_inf(String.valueOf(id));
        controller.setName_inf(name);
        controller.setSurname_inf(surname);

        m.changeScene3(root);
    }
    else if(access.equals("teacher")){
        Main m = new Main();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("teacherPanel.fxml"));
        Parent root = loader.load();
        TeacherPanel controller = loader.getController();


        controller.setId_inf(String.valueOf(id));
        controller.setName_inf(name);
        controller.setSurname_inf(surname);

        m.changeScene3(root);
    }
    else if(access.equals("teacher_student")){
        Main m = new Main();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("teacherPanel.fxml"));
        Parent root = loader.load();
        TeacherPanel controller = loader.getController();
System.out.println("---------------");
System.out.println(String.valueOf(teacher_id));
System.out.println(teacher_id);
System.out.println(teacher_surname);
        System.out.println("---------------");

        controller.setId_inf(String.valueOf(teacher_id));
        controller.setName_inf(teacher_name);
        controller.setSurname_inf(teacher_surname);

        m.changeScene3(root);

    }
    else{
        System.out.println("ELSE, ERROR");
    }

    }

    public TextArea getText_area() {
        return text_area;
    }

    public void setText_area(String a) {
        text_area.setText(a);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getWrong() {
        return wrong.getText();
    }

    public void setWrong(Label wrong) {
        this.wrong = wrong;
    }

    public String getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public String getTeacher_surname() {
        return teacher_surname;
    }

    public void setTeacher_surname(String teacher_surname) {
        this.teacher_surname = teacher_surname;
    }
}
