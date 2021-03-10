package RSA;

import database.DatabaseUtilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

public class RSAKeyPairGenerator2 {


    private PrivateKey privateKey;
    private PublicKey publicKey;


    public RSAKeyPairGenerator2() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(1024);
        KeyPair pair = keyGen.generateKeyPair();
        this.privateKey = pair.getPrivate();
        this.publicKey = pair.getPublic();
    }

    public void writeToFile(String path, byte[] key) throws IOException, IOException {
        File f = new File(path);
        f.getParentFile().mkdirs();

        FileOutputStream fos = new FileOutputStream(f);
        fos.write(key);
        fos.flush();
        fos.close();
    }
    //ben yazdım
    public void writeToDatabase(String location, byte[] key) throws IOException {

        Connection connection = null;
        try {
            connection = DatabaseUtilities.getConnection();
            System.out.println("Veri tabanı " + connection.getMetaData().getDatabaseProductName());

            // String sql = "SELECT id_teacher from teacher where id_teacher=?";
            String sql = "insert into file values('deneme4',0000,'student','privatekey', ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            System.out.println("Key: "+ key);
            statement.setString(1, String.valueOf(key));

            System.out.print("String key::::");
            for(int i=0; i< key.length ; i++) {
                System.out.print (key[i] +" ");
            }

            statement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
        public PrivateKey getPrivateKey() {
            return privateKey;
        }

        public PublicKey getPublicKey() {
            return publicKey;
        }

        public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
            RSAKeyPairGenerator2 keyPairGenerator = new RSAKeyPairGenerator2();
           // keyPairGenerator.writeToFile("C:/Users/meteh/Desktop/RSA/publicKey", keyPairGenerator.getPublicKey().getEncoded());
          //  keyPairGenerator.writeToFile("C:/Users/meteh/Desktop/RSA/privateKey", keyPairGenerator.getPrivateKey().getEncoded());
            keyPairGenerator.writeToDatabase("C:/Users/meteh/Desktop/RSA/publicKey", keyPairGenerator.getPublicKey().getEncoded());

            System.out.println(Base64.getEncoder().encodeToString(keyPairGenerator.getPublicKey().getEncoded()));
            System.out.println(Base64.getEncoder().encodeToString(keyPairGenerator.getPrivateKey().getEncoded()));

        }



}
