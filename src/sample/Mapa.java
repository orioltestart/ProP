package sample;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import sample.terrenys.*;
import sample.unitats.*;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by OriolTestart on 18/4/15.
 */

public class Mapa {
    private String nom;
    private Posicio[][] mapa;
    private Integer MAXH;
    private Integer MAXV;

    private Posicio seleccionada;
    private Posicio actual;


    public Mapa() {
        MAXH = 0;
        MAXV = 0;
        mapa = new Posicio[MAXH][MAXV];
        nom = "Mapa no construit";
    }

    public Mapa(String f) {
        try {
            llegirMapa(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Integer getMidaH() {
        return MAXH;
    }

    public Integer getMidaV() {
        return MAXV;
    }

    public Posicio getPos(int x, int y) {
        return mapa[x][y];
    }

    //Pre: --
    //Post: Retorna un string amb la matriu del mapa a dins
    public String toString() {
        String aux = "Nom: " + nom + "\n";
        for (int i = 0; i < MAXH; i++) {
            for (int j = 0; j < MAXV; j++) {
                aux += mapa[i][j].toString() + " ";
            }
            aux += "\n";
        }
        return aux;
    }

    //FABRIQUES D'OBJECTES

    private Terreny fabricaTerrenys(Integer i) {
        if (i.equals(0)) return new Plain();
        else if (i.equals(1)) return new Forest();
        else if (i.equals(2)) return new Mountain();
        else if (i.equals(3)) return new Fortress();
        else if (i.equals(4)) return new River();
        else if (i.equals(5)) return new River(5);
        else if (i.equals(6)) return new River(6);
        else if (i.equals(7)) return new River(7);
        else if (i.equals(8)) return new River(8);
        else if (i.equals(9)) return new Obstacle();
        else if (i.equals(10)) return new Plain(1); //cami
        else if (i.equals(11)) return new Plain(2); //maó
        else if (i.equals(12)) return new Plain(3); //pontH
        else if (i.equals(13)) return new Plain(4); //pontV
        else if (i.equals(14)) return new Obstacle(1);
        else return new Terreny();
    }

    private Unitat fabricaUnitats(Integer i) {
        if (i.equals(0)) return new Halberdier();
        else if (i.equals(1)) return new Knight();
        else if (i.equals(2)) return new Bowknight();
        else if (i.equals(3)) return new Marksman();
        else if (i.equals(4)) return new Paladin();
        else if (i.equals(5)) return new Wyvernknight();
        else return null;
    }


    //Pre: --
    //Post: Trasllada la unitat de la Posició ori a la Posició fi
    //Excepcions: Desplaçar una unitat a una posició ja ocupada llança una IllegalArgumentException així que la posició ori no tingui cap unitat per desplaçar
    public void desplacar(Posicio ori, Posicio fi) throws IllegalArgumentException {
        try {
            if (mapa[ori.getX()][ori.getY()].teUnitat())
                throw new IllegalArgumentException("Aquesta posició no té cap unitats dins");
            if (mapa[fi.getX()][fi.getY()].teUnitat())
                throw new IllegalArgumentException("Aquesta posició de destí ja està ocupada per un altre unitats");

            Unitat aux = mapa[ori.getX()][ori.getY()].getUnitat();

            mapa[ori.getX()][ori.getY()].eliminaUnitat(); //Eliminem la unitat del origen
            mapa[fi.getX()][fi.getY()].setUnitat(aux); //La coloquem al destí

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    // CERCA DE LES CASELLES ON ES POT DESPLAÇAR LA UNITAT

    private ArrayList<Posicio> getRangMoviment(Posicio p) {
        ArrayList<Posicio> posicions = new ArrayList<Posicio>();
        int x = p.getX();
        int y = p.getY();
        int mov = p.getUnitat().getMOV();

        for (int i = 0; i <= mov; i++) {
            if ((y - mov + i) >= 0) {
                posicions.add(mapa[x][y - mov + i]);
                for (int j = 1; j <= i; j++) {
                    if ((x + j) < MAXH) posicions.add(mapa[x + j][y - mov + i]);
                    if ((x - j) >= 0) posicions.add(mapa[x - j][y - mov + i]);
                }
            }
            if ((y + mov - i) < MAXV && i < mov) {
                posicions.add(mapa[x][y + mov - i]);
                for (int j = 1; j <= i; j++) {
                    if ((x + j) < MAXH) posicions.add(mapa[x + j][y + mov - i]);
                    if ((x - j) >= 0) posicions.add(mapa[x - j][y + mov - i]);
                }
            }
        }
        return posicions;
    }

    //ASSIGNACIO DE PROPIETATS INTERACTIVES A LES POSICIONS

    private void assignarHandlers(int x, int y) {
        mapa[x][y].setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                actual = (Posicio) mouseEvent.getTarget();
                if (actual.isMasked() && actual != seleccionada)
                    actual.setMasked(Color.GREEN);

            }
        });

        mapa[x][y].setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Posicio aux = (Posicio) mouseEvent.getTarget();
                if (aux.isMasked() && aux != seleccionada)
                    aux.eliminaSeleccio();

            }
        });


        mapa[x][y].setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Posicio aux = (Posicio) mouseEvent.getTarget();
                ArrayList<Posicio> posicions;

                if (seleccionada != aux) { //Si no selecciono la que ja tinc seleccionada
                    if (seleccionada != null) { //Si ja en tenia una de seleccionada
                        seleccionada.unMask(); //La desenmascaro
                        if (seleccionada.teUnitat()) {
                            //todo Implementacio del moviment aqui
                            posicions = getRangMoviment(seleccionada); //Agafo totes les caselles per desenmascarar
                            for (Posicio i : posicions) i.unMask(); //Les desenmascaro
                        }
                    }

                    seleccionada = aux; //Si no en tenia cap de seleccionada poso l'actual
                    seleccionada.setMasked(Color.YELLOW);  //La pinto de groc
                    if (seleccionada.teUnitat()) { //Si te una unitat
                        posicions = getRangMoviment(seleccionada); //Agafo totes les caselles per enmascarar
                        for (Posicio i : posicions) i.setMasked(Color.RED); //Les pinto de vermell
                    }
                }
            }
        });
    }

    // METODES DE LECTURA DES DE FITXER

    private void llegirMapa(String f) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(f));
        //Llegim el nom del mapa
        nom = br.readLine().substring(4);

        //Llegim les mides X i Y del mapa
        String[] mida = br.readLine().substring(5).split("x");

        MAXH = Integer.parseInt(mida[0]);
        MAXV = Integer.parseInt(mida[1]);

        mapa = new Posicio[MAXH][MAXV]; //Reservem memoria

        String sCurrentLine;
        Integer j = 0;

        while ((sCurrentLine = br.readLine()) != null) { //Saltem les possibles linies en blanc
            if (!sCurrentLine.isEmpty()) {

                String[] pos = sCurrentLine.split(" "); //Agafem la linia en questió

                for (int i = 0; i < pos.length; i++) {
                    mapa[i][j] = new Posicio(i, j); //Creem la nova posició
                    mapa[i][j].setTerreny(fabricaTerrenys(Integer.parseInt(pos[i]))); //Inserim el terreny determinat a la posició recent creada.
                    assignarHandlers(i, j);
                }
                j++;
            }
        }
        System.out.println("Lectura Correcte");
        mapa[10][10].setUnitat(new Halberdier());
        mapa[11][11].setUnitat(new Halberdier());
        mapa[11][11].getUnitat().reduirPV(89);
        mapa[11][11].actualitzar();
        br.close();
    }

    public void llegirUnitats(String u) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(u));

            String sCurrentLine;
            Integer j = 0;
            while ((sCurrentLine = br.readLine()) != null) { //Saltem les possibles linies en blanc
                if (!sCurrentLine.isEmpty()) {

                    String[] pos = sCurrentLine.split(" "); //Agafem la linia en questió

                    for (int i = 0; i < pos.length; i++)
                        mapa[i][j].setUnitat(fabricaUnitats(Integer.parseInt(pos[i]))); //Afegim les unitats corresponents a les posicions

                    j++;
                }
            }
            System.out.println("Lectura d'unitats correcte");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
