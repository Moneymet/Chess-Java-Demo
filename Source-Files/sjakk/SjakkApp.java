package sjakk;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author ChristerGumø
 */
public class SjakkApp extends Application {

    final int RUTESTOR = 80;

    BorderPane root = new BorderPane();
    FlowPane knappePane = new FlowPane();
    VBox infoPane = new VBox();
    GridPane brettPane = new GridPane();
    Label infoLabel;
    Label turLabel;
    Button flytt;
    Button lagre;
    Button nyttSpill;
    TextField fraRute;
    TextField tilRute;
    Brett brett;
    int spillNummer = 0;
    Label brikkeTegn = null;
    Label[][] lblBrikkeArr = null;

    @Override
    public void start(Stage primaryStage) {

        lagGUI();

        nyttSpillLytter();

        //brett.flyttBrikke("a7", "a6");
        oppdaterBrett();

        Scene scene = new Scene(root);

        primaryStage.setTitle("Sjakk");
        //primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void lagGUI() {

        // setter knapper i bunn, tilbakemelding i topp, og spillbrett i senter.
        root.setCenter(brettPane);
        root.setBottom(knappePane);
        root.setTop(infoPane);

        // Øker mellomrom mellom knapper.
        knappePane.setHgap(5);
        
        // Øker mellomrom mellom knappepanelet til rammen og brettet.
        knappePane.setPadding(new Insets(5));

        // Lager labeltabell med (8*8)=64 plasser.
        lblBrikkeArr = new Label[Brett.BRETTSTORRELSE][Brett.BRETTSTORRELSE];

        // Lager 64 rektangler for ruter, samt 64 labels for brikker.
        for (int i = 0; i < Brett.BRETTSTORRELSE; i++) {
            for (int j = 0; j < Brett.BRETTSTORRELSE; j++) {
                // Lager ett raktangel for hver rute og legger det ut til brett.
                Color ruteFarge = (j + i) % 2 == 0 ? Color.BROWN : Color.CADETBLUE;
                brettPane.add(new Rectangle(RUTESTOR, RUTESTOR, ruteFarge), i, j);

                // Ny label lages, endres innstillinger på, og legges ut til brett.
                lblBrikkeArr[i][j] = new Label();
                lblBrikkeArr[i][j].setFont(Font.font("Veranda", 30));
                GridPane.setValignment(lblBrikkeArr[i][j], VPos.CENTER);
                GridPane.setHalignment(lblBrikkeArr[i][j], HPos.CENTER);
                brettPane.add(lblBrikkeArr[i][j], i, j);

            }
        }

        // Legger ut knapper og informasjonslabels i infoPane og knappePane.
        // Samt legger til lyttere til knapper.
        
        infoLabel = new Label("Skriv inn trekk.");
        infoPane.getChildren().add(infoLabel);

        turLabel = new Label();
        infoPane.getChildren().add(turLabel);

        knappePane.getChildren().add(new Label("Fra: "));

        fraRute = new TextField();
        fraRute.setPrefColumnCount(2);
        knappePane.getChildren().add(fraRute);

        knappePane.getChildren().add(new Label("Til: "));

        tilRute = new TextField();
        tilRute.setPrefColumnCount(2);
        knappePane.getChildren().add(tilRute);

        flytt = new Button("Flytt");
        knappePane.getChildren().add(flytt);
        flytt.setOnAction(event -> flyttLytter());

        lagre = new Button("Lagre");
        knappePane.getChildren().add(lagre);
        lagre.setOnAction(event -> lagreLytter());

        nyttSpill = new Button("Nytt spill");
        knappePane.getChildren().add(nyttSpill);
        nyttSpill.setOnAction(event -> nyttSpillLytter());

    }

    // Lytter på flyttknappen.
    public void flyttLytter() {
        String fra = fraRute.getText();
        String til = tilRute.getText();
        if (!brett.flyttBrikke(fra, til)) {
            infoLabel.setText("Ugyldig trekk: " + fra + " -> " + til);
        } else {
            infoLabel.setText("Gyldig trekk: " + fra + " -> " + til);
        }
        oppdaterBrett();
    }

    // Lager nytt spill ved å lage ett nytt brett.
    // Det grafiske på visningsbrettet forblir.
    public void nyttSpillLytter() {
        // Lager nytt brett og oppdaterer spillnummeret.
        spillNummer++;
        brett = new Brett(spillNummer);
        infoLabel.setText("Skriv inn trekk.");
        oppdaterBrett();
    }

    // Skal lagre brutale sjakkslag.
    public void lagreLytter() {
        // TODO: få knapp til å lagre.
    }

    // Oppdaterer de grafiske komponentene i forhold til brettobjektet.
    public void oppdaterBrett() {

        // Viser hvilken farge som skal flytte.
        // Hvit starter alltid.
        turLabel.setText((brett.getHvitTur() ? "Hvit" : "Svart") + " sin tur.");

        // Oppdaterer grafiske brikker ved å gå gjennom alle de 64 plassene.
        for (int i = 0; i < Brett.BRETTSTORRELSE; i++) {
            for (int j = 0; j < Brett.BRETTSTORRELSE; j++) {
                
                // Brikke som prøver å ta data fra brikke på gitt plass.
                Brikke brikke = brett.getBrikke((char) (i + 'a') + "" + (8 - j));
                
                // Hvis brikken over faktisk er en brikke.
                if (brikke != null) {
                    if (brikke.getFarge() == Color.WHITE) {
                        lblBrikkeArr[i][j].setTextFill(Color.WHITE);
                    } else if (brikke.getFarge() == Color.BLACK) {
                        lblBrikkeArr[i][j].setTextFill(Color.BLACK);
                    }
                    // Grafisk brikke får sin brikkebokstav som grafisk symbol.
                    lblBrikkeArr[i][j].setText(brikke.brikkenavn());

                } else {
                    // Plasser som ikke har en brikke blir satt til en tom String.
                    lblBrikkeArr[i][j].setText("");

                }
            }
        }

    }
}
