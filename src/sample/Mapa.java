package sample;

import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import sample.terrenys.*;
import sample.unitats.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by OriolTestart on 18/4/15.
 */

public class Mapa {
    private String nom;
    private Posicio[][] mapa;
    private Integer MAXH;
    private Integer MAXV;
    private Boolean correcte;

    private Posicio seleccionada;
    private Posicio actual;


    public Mapa() {
        MAXH = 0;
        MAXV = 0;
        correcte = false;
        mapa = new Posicio[MAXH][MAXV];
        nom = "Mapa no construit";
    }

    public Mapa(File f) {
        BufferedReader br;

        try {
            br = new BufferedReader(new FileReader(f));

            //Llegim el nom del mapa
            String sCurrentLine = br.readLine();
            nom = sCurrentLine.substring(4);

            //Llegim les mides X i Y del mapa
            sCurrentLine = br.readLine();
            String[] mida = sCurrentLine.substring(5).split("x");

            MAXH = Integer.parseInt(mida[0]);
            MAXV = Integer.parseInt(mida[1]);

            mapa = new Posicio[MAXH][MAXV];

            Integer j = 0;
            while ((sCurrentLine = br.readLine()) != null) { //Saltem les possibles linies en blanc
                if (!sCurrentLine.isEmpty()) {
                    String[] pos = sCurrentLine.split(" "); //Agafem la linia en questió

                    if (j < MAXV) {
                        for (int i = 0; i < pos.length; i++) {

                            mapa[i][j] = new Posicio(i, j); //Creem la nova posició
                            mapa[i][j].setTerreny(fabricaTerrenys(Integer.parseInt(pos[i]))); //Inserim el terreny determinat a la posició recent creada.

                            mapa[i][j].setOnMouseEntered(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent mouseEvent) {
                                    actual = (Posicio) mouseEvent.getTarget();
                                    if (actual.isMasked()) {
                                        actual.setSeleccionat();
                                    }
                                }
                            });

                            mapa[i][j].setOnMouseExited(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent mouseEvent) {
                                    Posicio aux = (Posicio) mouseEvent.getTarget();
                                    if (aux.isMasked()) {
                                        aux.eliminaSeleccio();
                                    }
                                }
                            });


                            mapa[i][j].setOnMouseClicked(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent mouseEvent) {
                                    try {
                                        Posicio aux = (Posicio) mouseEvent.getTarget();
                                        ArrayList<Posicio> posicions;

                                        if (seleccionada != aux) {
                                            if (seleccionada != null) {
                                                posicions = getRangMoviment(seleccionada);
                                                for (Posicio i : posicions) i.unMask();
                                            }

                                            seleccionada = aux;
                                            posicions = getRangMoviment(seleccionada);
                                            for (Posicio i : posicions) i.setMasked();
                                        }


                                    } catch (IllegalArgumentException e) {
                                        System.out.println(e.getMessage());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                        }
                    } else {
                        for (int i = 0; i < pos.length; i++) {
                            mapa[i][j].setUnitat(fabricaUnitats(Integer.parseInt(pos[i])));
                        }
                    }
                    j++;
                } else j = 0;
            }
            System.out.println("Lectura Correcte");
            correcte = true;
            mapa[10][10].setUnitat(new Halberdier());
            mapa[11][11].setUnitat(new Halberdier());
            br.close();

        } catch (NumberFormatException e) {
            System.out.println("Error: Format del mapa Incorrecte");
            correcte = false;
        } catch (IllegalArgumentException e) {
            System.out.print(e.getMessage());
            correcte = false;
        } catch (IOException e) {
            System.out.println("Error: Lectura Fallida");
            e.printStackTrace();
            correcte = false;
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

    public boolean esCorrecte() {
        return correcte;
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


    //Pre: --
    //Post: Mostra per consola el Mapa en format de matriu
    public void mostrar() {
        for (int i = 0; i < MAXH; i++) {
            for (int j = 0; j < MAXV; j++) {
                System.out.print(mapa[i][j]);
            }
            System.out.println();
        }
    }

    private Terreny fabricaTerrenys(Integer i) {
        if (i.equals(0)) return new Plain();
        else if (i.equals(1)) return new Forest();
        else if (i.equals(2)) return new Mountain();
        else if (i.equals(3)) return new Fortress();
        else if (i.equals(4)) return new River();
        else if (i.equals(5)) return new River();
        else if (i.equals(6)) return new River();
        else if (i.equals(7)) return new River();
        else if (i.equals(8)) return new River();
        else if (i.equals(9)) return new Plain();
        else return new Terreny();
    }

    private Unitat fabricaUnitats(Integer i) {
        if (i.equals(0)) return new Halberdier();
        else if (i.equals(1)) return new Knight();
        else if (i.equals(2)) return new Bowknight();
        else if (i.equals(3)) return new Marksman();
        else if (i.equals(4)) return new Paladin();
        else if (i.equals(5)) return new Wyvernknight();
        else return new Halberdier(); //Todo
    }


    //Pre: --
    //Post: Trasllada la unitat de la Posició ori a la Posició fi
    //Excepcions: Desplaçar una unitat a una posició ja ocupada llança una IllegalArgumentException així que la posició ori no tingui cap unitat per desplaçar
    public void desplacar(Posicio ori, Posicio fi) throws IllegalArgumentException {
        try {
            if (mapa[ori.getX()][ori.getY()].getUnitat() == null)
                throw new IllegalArgumentException("Aquesta posició no té cap unitats dins");
            if (mapa[fi.getX()][fi.getY()].getUnitat() != null)
                throw new IllegalArgumentException("Aquesta posició de destí ja està ocupada per un altre unitats");

            Unitat aux = mapa[ori.getX()][ori.getY()].getUnitat();

            mapa[ori.getX()][ori.getY()].eliminaUnitat(); //Eliminem la unitat del origen
            mapa[fi.getX()][fi.getY()].setUnitat(aux); //La coloquem al destí

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Posicio> getRangMoviment(Posicio p) {
        ArrayList<Posicio> posicions = new ArrayList<Posicio>();
        if (p.getUnitat() != null) {
            Posicio iniciQ = new Posicio(seleccionada.getX() - seleccionada.getUnitat().getMOV(), seleccionada.getY() - seleccionada.getUnitat().getMOV());
            Posicio finalQ = new Posicio(seleccionada.getX() + seleccionada.getUnitat().getMOV(), seleccionada.getY() + seleccionada.getUnitat().getMOV());


            for (int i = iniciQ.getX(); i <= finalQ.getX(); i++) {
                for (int j = iniciQ.getY(); j <= finalQ.getY(); j++) {
                    Posicio val = new Posicio(Math.abs(seleccionada.getX() - i), Math.abs(seleccionada.getY() - j));

                    if (i >= 0 && i < MAXH && j >= 0 && j < MAXV) {
                        if (val.getX() + val.getY() <= seleccionada.getUnitat().getMOV() && (mapa[i][j] != seleccionada))
                            posicions.add(mapa[i][j]);
                    }
                }
            }
        }
        return posicions;
    }

    //Provant 222

}
