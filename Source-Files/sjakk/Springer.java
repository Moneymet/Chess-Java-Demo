package sjakk;

import javafx.scene.paint.Color;

public class Springer extends Offiser {
    
    public Springer(String ruteNavn, Color farge, Brett brett){
        super(ruteNavn, farge, brett);
    }

    @Override
    public boolean erLovligTrekk(String ruteNavn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String brikkenavn() {
        return "S";
    }
}
