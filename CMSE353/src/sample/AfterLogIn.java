package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class AfterLogIn {


    @FXML
    private Button logobutton;

    @FXML
    void userLogOut(ActionEvent event) throws IOException {
        userLogOut();
    }

    public void userLogOut() throws IOException {
            Main m = new Main();
            m.changeScene("sample.fxml");

        }


    }

