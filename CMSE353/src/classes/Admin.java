package classes;

public class Admin {

    private int id_admin;
    private String name_admin;
    private String surname_admin;
    private String password_admin;
    private String group_admin;

    public Admin() {
    }

    public Admin(int id_admin, String name_admin, String surname_admin, String password_admin, String group_admin) {
        this.id_admin = id_admin;
        this.name_admin = name_admin;
        this.surname_admin = surname_admin;
        this.password_admin = password_admin;
        this.group_admin = group_admin;
    }
}
