package database;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class CheckDatabase {
    public CheckDatabase() {
    }

    public ArrayList is_admin_exist(Integer id_admin, String password_admin) throws SQLException, ClassNotFoundException {


    Connection connection = DatabaseUtilities.getConnection();
    System.out.println("Veri tabanı " + connection.getMetaData().getDatabaseProductName());

    String sql = "SELECT * from admin where id_admin =? and password_admin = ?";
    PreparedStatement statement = connection.prepareStatement(sql);

    statement.setInt(1,id_admin);
    statement.setString(2,password_admin);

    ResultSet resultset = statement.executeQuery();

        ArrayList<String> list = new ArrayList<>();

    if(resultset.next()){
        String id = String.valueOf(resultset.getInt("id_admin"));
        String name = resultset.getString("name_admin");
        String surname = resultset.getString("surname_admin");
        list.add(id);
        list.add(name);
        list.add(surname);
        connection.close();
            return list;

    }
    else{
        connection.close();
        return list;
    }



}

    public ArrayList<String> is_teacher_exist(Integer id_teacher,String password_teacher) throws SQLException, ClassNotFoundException {

        Connection connection = DatabaseUtilities.getConnection();
        System.out.println("Veri tabanı " + connection.getMetaData().getDatabaseProductName());

        String sql = "SELECT * from teacher where id_teacher =? and password_teacher = ?";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1,id_teacher);
        statement.setString(2,password_teacher);

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

    public ArrayList<String> is_student_exist(Integer id_student,String password_student) throws SQLException, ClassNotFoundException {

        Connection connection = DatabaseUtilities.getConnection();
        System.out.println("Veri tabanı " + connection.getMetaData().getDatabaseProductName());

        String sql = "SELECT * from student where id_student =? and password_student = ?";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1,id_student);
        statement.setString(2,password_student);

        ResultSet resultset = statement.executeQuery();

        ArrayList<String> list = new ArrayList<>();

        if(resultset.next()){
            String id = String.valueOf(resultset.getInt("id_student"));
            String name = resultset.getString("name_student");
            String surname = resultset.getString("surname_student");
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





}
