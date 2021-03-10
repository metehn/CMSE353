package classes;

public class Keys {

    private String public_key;
    private String private_key;


    public Keys() {
    }

    public Keys(String public_key, String private_key) {
        this.public_key = public_key;
        this.private_key = private_key;
    }

    public String getPublic_key() {
        return public_key;
    }

    public String getPrivate_key() {
        return private_key;
    }

    public void setPublic_key(String public_key) {
        this.public_key = public_key;
    }

    public void setPrivate_key(String private_key) {
        this.private_key = private_key;
    }
}
