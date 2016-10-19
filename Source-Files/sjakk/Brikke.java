package sjakk;

import javafx.scene.paint.Color;

abstract public class Brikke {

    protected String ruteNavn;
    protected Color farge;
    protected Brett brett;

    // Trekk siden brikken ble siste flyttet.
    private int sisteFlyttet;

    public Brikke(String ruteNavn, Color farge, Brett brett) {

        if (Brett.erLovligRutenavn(ruteNavn)) {
            this.ruteNavn = ruteNavn;
        }
        this.farge = farge;
        this.brett = brett;
    }

    abstract public boolean erLovligTrekk(String ruteNavn);

    abstract public String brikkenavn();

    public boolean flyttTil(String ruteNavn) {
        if (Brett.erLovligRutenavn(ruteNavn) && erLovligTrekk(ruteNavn)) {
            this.setSisteFlyttet(brett.getRundeNr());
            brett.flyttBrikke(this.ruteNavn, ruteNavn);
            this.ruteNavn = ruteNavn;
            return true;
        }
        return false;
    }

    // Sjekker om brikker som flytter i linje kan flytte til rute.
    public boolean erLovligIRetning(String ruteNavn, boolean flytterKors, boolean flytterDiagonalt) {

        int radNr1 = ruteNavn.charAt(0) - 'a';
        int kolNr1 = '8' - ruteNavn.charAt(1);
        int radNr2 = this.ruteNavn.charAt(0) - 'a';
        int kolNr2 = '8' - this.ruteNavn.charAt(1);

        // Viser retning som skal beregnes.
        int xRetningsTall = 0;
        int yRetningsTall = 0;

        if (flytterKors) {
            if (radNr1 == radNr2) {
                yRetningsTall = kolNr1 > kolNr2 ? 1 : -1;

            }
            if (kolNr1 == kolNr2) {
                xRetningsTall = radNr1 > radNr2 ? 1 : -1;
            }
        }
        if (flytterDiagonalt) {
            if (radNr1 != radNr2 && kolNr1 != kolNr2) {
                yRetningsTall = kolNr1 > kolNr2 ? 1 : -1;
                xRetningsTall = radNr1 > radNr2 ? 1 : -1;
            }
        }

        return erKlarVei(ruteNavn, xRetningsTall, yRetningsTall);
    }

    // Sjekker om veien til rute er klar.
    public boolean erKlarVei(String ruteNavn, int xTall, int yTall) {
        int radNr = this.ruteNavn.charAt(0) - 'a';
        int kolNr = '8' - this.ruteNavn.charAt(1);

        String sjekk = "";
        Brikke tilBrikke;

        int xSjekk = radNr + xTall;
        int ySjekk = kolNr + yTall;

        // Følger linjen til all posisjoner mellom de to brikkene har blitt sjekket.
        // Eller til linjen går utenfor brettet.
        while (xSjekk < Brett.BRETTSTORRELSE && ySjekk < Brett.BRETTSTORRELSE) {

            sjekk = (char) (xSjekk + 'a') + "" + (Brett.BRETTSTORRELSE - ySjekk);
            tilBrikke = brett.getBrikke(sjekk);

            // Sjekker først om brikken har fulgt veien til en gyldig plasse.
            if (sjekk.equals(ruteNavn)) {
                if (tilBrikke == null) {

                    //System.out.println(brikke.toString());
                    System.out.println(this.toString());
                    return true;
                } else if (tilBrikke.getFarge() != this.farge) {
                    return true;
                }
            } else if (tilBrikke != null) {
                return false;
            }

            xSjekk += xTall;
            ySjekk += yTall;
        }

        return false;
    }

    public String getRuteNavn() {
        return ruteNavn;
    }

    public void setRuteNavn(String ruteNavn) {
        if (Brett.erLovligRutenavn(ruteNavn)) {
            this.ruteNavn = ruteNavn;
        }
    }

    public Color getFarge() {
        return farge;
    }

    public void setFarge(Color farge) {
        this.farge = farge;
    }

    public int getSisteFlyttet() {
        return sisteFlyttet;
    }

    public void setSisteFlyttet(int sisteFlyttet) {
        this.sisteFlyttet = sisteFlyttet;
    }

    @Override
    public String toString() {
        return "Brikke{" + "ruteNavn=" + ruteNavn + ", farge=" + farge + ", brett=" + brett + ", sisteFlyttet=" + sisteFlyttet + '}';
    }

}
