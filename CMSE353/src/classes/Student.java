package classes;

public class Student {

    private int id_student;
    private String name_student;
    private String surname_student;
    private String password_student;
    private String group_student;
    private String private_key;
    private String public_key;

    public Student() {
    }

    public Student(int id_student, String name_student, String surname_student, String password_student, String group_student, String private_key, String public_key) {
        this.id_student = id_student;
        this.name_student = name_student;
        this.surname_student = surname_student;
        this.password_student = password_student;
        this.group_student = group_student;
        this.private_key = private_key;
        this.public_key = public_key;
    }

    public int getId_student() {
        return id_student;
    }

    public void setId_student(int id_student) {
        this.id_student = id_student;
    }

    public String getName_student() {
        return name_student;
    }

    public void setName_student(String name_student) {
        this.name_student = name_student;
    }

    public String getSurname_student() {
        return surname_student;
    }

    public void setSurname_student(String surname_student) {
        this.surname_student = surname_student;
    }

    public String getPassword_student() {
        return password_student;
    }

    public void setPassword_student(String password_student) {
        this.password_student = password_student;
    }

    public String getGroup_student() {
        return group_student;
    }

    public void setGroup_student(String group_student) {
        this.group_student = group_student;
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
