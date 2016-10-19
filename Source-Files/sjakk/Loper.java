package sjakk;

import javafx.scene.paint.Color;

public class Loper extends Offiser {
    
    public Loper(String ruteNavn, Color farge, Brett brett){
        super(ruteNavn, farge, brett);
    }

    @Override
    public boolean erLovligTrekk(String ruteNavn) {
        return this.erLovligIRetning(ruteNavn, false, true);
    }

    @Override
    public String brikkenavn() {
        return "L";
    }
}
