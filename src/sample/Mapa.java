package sample;

import sample.unitats.Unitat;
import sample.unitats.arqueria.Bowknight;
import sample.unitats.infanteria.Halberdier;
import sample.unitats.infanteria.Knight;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by OriolTestart on 18/4/15.
 */

public class Mapa {
    private String nom;
    private Posicio[][] mapa;
    private Integer MAXH;
    private Integer MAXV;
    private Boolean correcte;


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
            MAXH = Integer.parseInt(sCurrentLine.substring(5, 6));
            MAXV = Integer.parseInt(sCurrentLine.substring(7, 8));

            //Saltem les linies en blanc
            Integer j = 0;
            mapa = new Posicio[MAXH][MAXV];
            while ((sCurrentLine = br.readLine()) != null) { //Saltem les possibles linies en blanc
                if (!sCurrentLine.isEmpty()) { //Si no és una linia en blanc
                    String[] pos = sCurrentLine.split(" "); //Agafem la linia en questió

                    try {
                        Unitat aux;
                        for (int i = 0; i < pos.length; i++) {

                            int item = Integer.parseInt(pos[i]); //Intentem Convertir el caràcter en un enter, si no ho és pertany al mapa i llança una Number format exception
                            switch (item) {
                                case 1:
                                    aux = new Bowknight();
                                    break;
                                case 2:
                                    aux = new Halberdier();
                                    break;
                                case 3:
                                    aux = new Knight();
                                    break;
                                default:
                                    throw new IllegalArgumentException("Error: Mapa unitat a la posicio: [" + i + "," + j + "] no existeix");
                            }

                            //mapa[i][j].setUnitat(aux);
                        }
                    } catch (NumberFormatException e) { //Si ha fallat la conversió a enter, significa que pertany al mapa.
                        for (int i = 0; i < pos.length; i++) { //Recorrem la linia
                            //Terreny aux;
                            switch (pos[i]) { //Depenent del caràcter que sigui instanciem el que correspongui
                                case "p":
                                    System.out.println("Afegim PLANA");
                                    //aux = new Plana();
                                    break;
                                case "M":
                                    System.out.println("Afegim MUNTANYA");
                                    //aux = new Muntanya();
                                    break;
                                case "m":
                                    System.out.println("Afegim MAR");
                                    //aux = new Mar();
                                    break;
                                default:
                                    throw new IllegalArgumentException("Error: Mapa terreny a la posicio: [" + i + "," + j + "] no existeix");
                            }
                            //mapa[i][j] = new Posicio(i, j); //Creem la nova posició
                            //mapa[i][j].setTerreny(new Terreny("Terreny Pos: [" + i + "," + j + "]")); //Inserim el terreny determinat a la posició recent creada.
                        }
                    }
                    j++;
                }
            }
            correcte = true;
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
    /*

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
    */

    //Provant 222

}
