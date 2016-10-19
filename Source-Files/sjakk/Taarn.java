package sjakk;

import javafx.scene.paint.Color;

public class Taarn extends Offiser {
    
    public Taarn(String ruteNavn, Color farge, Brett brett){
        super(ruteNavn, farge, brett);
    }

    @Override
    public boolean erLovligTrekk(String ruteNavn) {
        return this.erLovligIRetning(ruteNavn, true, false);
    }

    @Override
    public String brikkenavn() {
        return "T";
    }
}
