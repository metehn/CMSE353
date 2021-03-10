package classes;

public class Teacher {


    private int id_teacher;
    private String name_teacher;
    private String surname_teacher;
    private String password_teacher;
    private String group_teacher;
    private String private_key;
    private String public_key;

    public Teacher() {
    }

    public Teacher(int id_teacher, String name_teacher, String surname_teacher, String password_teacher, String group_teacher, String private_key, String public_key) {
        this.id_teacher = id_teacher;
        this.name_teacher = name_teacher;
        this.surname_teacher = surname_teacher;
        this.password_teacher = password_teacher;
        this.group_teacher = group_teacher;
        this.private_key = private_key;
        this.public_key = public_key;
    }



    public int getId_teacher() {
        return id_teacher;
    }

    public void setId_teacher(int id_teacher) {
        this.id_teacher = id_teacher;
    }

    public String getName_teacher() {
        return name_teacher;
    }

    public void setName_teacher(String name_teacher) {
        this.name_teacher = name_teacher;
    }

    public String getSurname_teacher() {
        return surname_teacher;
    }

    public void setSurname_teacher(String surname_teacher) {
        this.surname_teacher = surname_teacher;
    }

    public String getPassword_teacher() {
        return password_teacher;
    }

    public void setPassword_teacher(String password_teacher) {
        this.password_teacher = password_teacher;
    }

    public String getGroup_teacher() {
        return group_teacher;
    }

    public void setGroup_teacher(String group_teacher) {
        this.group_teacher = group_teacher;
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
