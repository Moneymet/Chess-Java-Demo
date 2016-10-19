package sjakk;

import javafx.scene.paint.Color;

public class Dronning extends Offiser {
    
    public Dronning(String ruteNavn, Color farge, Brett brett){
        super(ruteNavn, farge, brett);
    }

    @Override
    public boolean erLovligTrekk(String ruteNavn) {
        return this.erLovligIRetning(ruteNavn, true, true);
    }

    @Override
    public String brikkenavn() {
        return "D";
    }
}
