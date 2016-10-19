package sjakk;

import javafx.scene.paint.Color;

public class Bonde extends Brikke {

    //protected int sisteFlyttet;

    public Bonde(String ruteNavn, Color farge, Brett brett) {
        super(ruteNavn, farge, brett);
    }

    @Override
    public boolean erLovligTrekk(String ruteNavn) {
        int radNr1 = ruteNavn.charAt(0) - 'a';
        int kolNr1 = '8' - ruteNavn.charAt(1);
        int radNr2 = this.ruteNavn.charAt(0) - 'a';
        int kolNr2 = '8' - this.ruteNavn.charAt(1);

        String sjekk = null;

        // Bestemmer retning som skal sjekkes basert på farge.
        int fargeMod = farge == Color.WHITE ? 1 : -1;

        // Sjekk for enkelthopp.
        sjekk = (char) (radNr2 + 'a') + "" + (Brett.BRETTSTORRELSE - kolNr2 + fargeMod);

        // Sjekker om det er enkelflytt og om det er ledig plass.
        if (erLedig(sjekk, ruteNavn)) {
            return true;
        }

        // Sjekker om der er 2 rutersflytt og om det er ledig plass.
        if (this.getSisteFlyttet() == 0) {
            sjekk = (char) (radNr2 + 'a') + "" + (Brett.BRETTSTORRELSE - kolNr2 + fargeMod * 2);
            
            // Sjekk for rute mellom bondens starthopp.
            String forSjekk = (char) (radNr2 + 'a') + "" + (Brett.BRETTSTORRELSE - kolNr2 + fargeMod);
            
            if (erLedig(sjekk, ruteNavn) && erLedig(forSjekk, forSjekk)) {
                return true;
            }
        }
        
        //if()
        
        //sjekk = (char) (radNr2 + 'a') + "" + (Brett.BRETTSTORRELSE - kolNr2 + fargeMod);

        return false;
    }

    // Sjekker om til ruten er det samme som fra ruten, samt om en brikke allerede står der.
    private boolean erLedig(String sjekkRute, String ruteNavn) {
        // Sjekker om brikken 
        Brikke tilBrikke = brett.getBrikke(sjekkRute);

        if (tilBrikke == null) {

            //System.out.println(brikke.toString());
            System.out.println(this.toString());
            if (sjekkRute.equals(ruteNavn)) {
                return true;
            }
        }

        return false;
    }
    
    private boolean bondeKanTa(int fargeMod){
        return false;
    }

    @Override
    public String brikkenavn() {
        return "B";
    }
}
