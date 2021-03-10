package classes;

public class File {

    private String location_file;
    private String name_file;
    private int ownerid_file;
    private String private_key;
    private String public_key;


    public File() {
    }

    public File(String location_file, String name_file, int ownerid_file, String private_key, String public_key) {
        this.location_file = location_file;
        this.name_file = name_file;
        this.ownerid_file = ownerid_file;
        this.private_key = private_key;
        this.public_key = public_key;
    }

    public String getLocation_file() {
        return location_file;
    }

    public void setLocation_file(String location_file) {
        this.location_file = location_file;
    }

    public String getName_file() {
        return name_file;
    }

    public void setName_file(String name_file) {
        this.name_file = name_file;
    }

    public int getOwnerid_file() {
        return ownerid_file;
    }

    public void setOwnerid_file(int ownerid_file) {
        this.ownerid_file = ownerid_file;
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
