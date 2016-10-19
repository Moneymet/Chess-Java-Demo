package sjakk;

import javafx.scene.paint.Color;

class Brett {

    public static final int BRETTSTORRELSE = 8;
    private int spillNr;
    private Brikke[][] brikkene;
    private int rundeNr = 1;
    private boolean hvitTur = true;

    public Brett(int nyttSpillNr) {
        // Initierer brettet for et nytt spill.
        this.spillNr = nyttSpillNr;
        brikkene = new Brikke[BRETTSTORRELSE][BRETTSTORRELSE];
        leggUtBrikker();
    }

    public static boolean erLovligRutenavn(String rutenavn) {
        // Sjekk om rutenavnet er lovlig.
        // Må være 2 tegn langt.
        if (rutenavn.length() == 2) {

            // Lagrer Stringen som 2 chars.
            char bokstav = rutenavn.charAt(0);
            char tall = rutenavn.charAt(1);

            // Sjekker om det første tegnet er mellom 'a' og 'h', så om  tallet er mellom '1' og '8'.
            if (bokstav >= 'a' && bokstav <= 'h' && tall >= '1' && tall <= '8') {
                return true;
            }
        }

        return false;
    }

    public Brikke getBrikke(String rutenavn) {
        // Leverer brikken på gitt rute.

        int radNr = rutenavn.charAt(0) - 'a';
        int kolNr = '8' - rutenavn.charAt(1);

        //System.out.println(radNr +" "+ kolNr);
        return brikkene[radNr][kolNr];
    }

    public int getRundeNr() {
        return rundeNr;
    }

    public boolean getHvitTur() {
        return hvitTur;
    }

    public boolean flyttBrikke(String fraRute, String tilRute) {
        // Flytter brikke hvis lovlig/mulig.
        // Sjekker om fra og til posisjon ikke er det samme.
        if (fraRute.equals(tilRute)) {
            return false;
        }

        // Sjekker om begge rutenavnene er skrevet riktig
        if (!erLovligRutenavn(fraRute) || !erLovligRutenavn(tilRute)) {
            return false;
        }

        // Oversetter posisjon til tabell.
        int radNr1 = fraRute.charAt(0) - 'a';
        int kolNr1 = '8' - fraRute.charAt(1);
        int radNr2 = tilRute.charAt(0) - 'a';
        int kolNr2 = '8' - tilRute.charAt(1);
        
        Brikke fraBrikke = getBrikke(fraRute);

        //Brikke brikke = brikkene[radNr1][kolNr1];
        // Hvis trekket er gyldig returneres true, og brikken blir flyttet.
        if (fraBrikke != null) {
            if (fraBrikke.erLovligTrekk(tilRute) && (hvitTur == (fraBrikke.getFarge() == Color.WHITE))) {
                brikkene[radNr2][kolNr2] = brikkene[radNr1][kolNr1];
                brikkene[radNr1][kolNr1] = null;
                brikkene[radNr2][kolNr2].setRuteNavn(tilRute);
                brikkene[radNr2][kolNr2].setSisteFlyttet(rundeNr);
                if (!hvitTur) {
                    rundeNr++;
                }
                hvitTur = !hvitTur;
                return true;
            }
        }

        return false;
    }

    // Returnerer alle brikkers bokstav på brettet.
    @Override
    public String toString() {
        String alleNavn = "";
        for (int j = 0; j < 8; j++) {
            for (int i = 0; i < 8; i++) {
                if (this.brikkene[i][j] != null) {
                    alleNavn += this.brikkene[i][j].brikkenavn();
                } else {
                    alleNavn += 'X';
                }
            }
            alleNavn += "\n";
        }

        return alleNavn;
    }

    private void leggUtBrikker() {

        for (int i = 0; i < BRETTSTORRELSE; i++) {
            brikkene[i][1] = new Bonde((char) (i + 97) + "" + 7, Color.BLACK, this);
            brikkene[i][BRETTSTORRELSE - 2] = new Bonde((char) (i + 97) + "" + 2, Color.WHITE, this);
        }

        brikkene[0][0] = new Taarn("a8", Color.BLACK, this);
        brikkene[7][0] = new Taarn("h8", Color.BLACK, this);
        brikkene[0][7] = new Taarn("a1", Color.WHITE, this);
        brikkene[7][7] = new Taarn("h1", Color.WHITE, this);

        brikkene[1][0] = new Springer("b8", Color.BLACK, this);
        brikkene[6][0] = new Springer("g8", Color.BLACK, this);
        brikkene[1][7] = new Springer("b1", Color.WHITE, this);
        brikkene[6][7] = new Springer("g1", Color.WHITE, this);

        brikkene[2][0] = new Loper("c8", Color.BLACK, this);
        brikkene[5][0] = new Loper("f8", Color.BLACK, this);
        brikkene[2][7] = new Loper("c1", Color.WHITE, this);
        brikkene[5][7] = new Loper("f1", Color.WHITE, this);

        brikkene[3][0] = new Dronning("d8", Color.BLACK, this);
        brikkene[3][7] = new Dronning("d1", Color.WHITE, this);

        brikkene[4][0] = new Konge("e8", Color.BLACK, this);
        brikkene[4][7] = new Konge("e1", Color.WHITE, this);
    }
}
