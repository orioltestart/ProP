package sample;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by lluis on 27/05/15.
 */
public class Matriu {

    String [][] matriu;
    int midaH;
    int midaV;

    public Matriu(){
        midaH = 0;
        midaV = 0;
        matriu = null;
    }

    public Matriu (String f)throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(f));

        //Llegim les mides X i Y del mapa
        String[] mida = br.readLine().split("x");

        midaV = Integer.parseInt(mida[0]);
        midaH = Integer.parseInt(mida[1]);

        matriu = new String[midaH][midaV]; //Reservem memoria

        String sCurrentLine;
        Integer j = 0;

        while ((sCurrentLine = br.readLine()) != null) { //Saltem les possibles linies en blanc
            if (!sCurrentLine.isEmpty()) {

                String[] pos = sCurrentLine.split(" "); //Agafem la linia en questió

                for (int i = 0; i < pos.length; i++) {
                    matriu[j][i] = pos[i]; //Creem la nova posició
                }
                j++;
            }
        }

        System.out.println("Lectura Correcte");
        br.close();
    }

    public void mostrar(){
        System.out.println("FANTASTIC matriu");

        for (int i=0; i<midaH; i++){
            for (int j=0; j<midaV; j++){
                System.out.print(matriu[i][j] + "   ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public ArrayList<Vertex> creaGraf(){
        ArrayList<Vertex> v = new ArrayList<Vertex>();
        for (int i=0; i<midaH; i++) {
            for (int j = 0; j < midaV; j++) {
                v.add(new Vertex(i + " " + j));
            }
        }
        // llista de tots els vertexs
        for (Vertex v1 : v) {
            String[] s1 = v1.toString().split(" ");
            v1.iniciaAdjacencies();
            for (Vertex v2 : v) {
                if (v1 != v2) {
                    String[] s2 = v2.toString().split(" ");
                    if (((Integer.parseInt(s2[0]) == Integer.parseInt(s1[0]) - 1 || Integer.parseInt(s2[0]) == Integer.parseInt(s1[0]) + 1) &&
                                    Integer.parseInt(s2[1]) == Integer.parseInt(s1[1])) ||

                            ((Integer.parseInt(s2[1]) == Integer.parseInt(s1[1]) + 1 || Integer.parseInt(s2[1]) == Integer.parseInt(s1[1]) - 1) &&
                            Integer.parseInt(s2[0]) == Integer.parseInt(s1[0]))) {

                        v1.getAdjacencies().add(new Edge(v2, Integer.parseInt(matriu[Integer.parseInt(s2[0])][Integer.parseInt(s2[1])])));
                    }
                }
            }
        }
        return v;
    }

    public void assignaPosicio (int x, int y, String s){

        matriu[x][y]=s;
    }
}
