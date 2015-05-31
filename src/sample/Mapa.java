/**
 * @file Mapa.java
 * @author Oriol Testart
 * @brief La classe Mapa engloba totes les unitats dels jugadors i els terrenys.
 */

package sample;

import sample.terrenys.*;
import sample.unitats.*;

import java.io.*;
import java.util.*;

public class Mapa {
    private String nom;
    private Posicio[][] mapa;
    private Integer MAXH;
    private Integer MAXV;
    private Posicio meta;
    private Boolean end = false;



    private ArrayList<Posicio> forts;
    private Integer [][] costosCamins;
    private ArrayList<Vertex> vertexs;


    public Mapa() {
        MAXH = 0;
        MAXV = 0;
        mapa = new Posicio[MAXH][MAXV];
        costosCamins = new Integer[MAXH][MAXV];
        vertexs = new ArrayList<>();
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

    public Posicio[][] getMapa() {
        return mapa;
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
//todo
    public static Terreny fabricaTerrenys(Integer i) {
        if (i.equals(0)) return new Plain(0);   //plain
        else if (i.equals(1)) return new Forest(1); //forest
        else if (i.equals(2)) return new Mountain(2);   //mountain
        else if (i.equals(3)) return new Fortress(3);   //fortress
        else if (i.equals(4)) return new River(4);  //
        else if (i.equals(5)) return new River(5);
        else if (i.equals(6)) return new River(6);
        else if (i.equals(7)) return new River(7);
        else if (i.equals(8)) return new River(8);
        else if (i.equals(9)) return new Obstacle(9);
        else if (i.equals(10)) return new Plain(10); //cami
        else if (i.equals(11)) return new Plain(11); //maó
        else if (i.equals(12)) return new Plain(12); //pontH
        else if (i.equals(13)) return new Plain(13); //pontV
        else if (i.equals(14)) return new Obstacle(14); //murH
        else if (i.equals(15)) return new Fortress(15); //meta
        else return new Obstacle(14);
    }
    //todo
    private Unitat fabricaUnitats(Integer i) {
        if (i.equals(1)) return new Halberdier();
        else if (i.equals(2)) return new Knight();
        else if (i.equals(3)) return new Bowknight();
        else if (i.equals(4)) return new Marksman();
        else if (i.equals(5)) return new Paladin();
        else if (i.equals(6)) return new Wyvernknight();
        else return null;
    }

    //todo
    //Pre: --
    //Post: Trasllada la unitat de la Posició ori a la Posició fi
    //Excepcions: Desplaçar una unitat a una posició ja ocupada llança una IllegalArgumentException així que la posició ori no tingui cap unitat per desplaçar
    public void desplacar(Posicio ori, Posicio fi) {
        if (ori != fi && !mapa[fi.getX()][fi.getY()].teUnitat() && mapa[ori.getX()][ori.getY()].teUnitat()) {

            Unitat aux = mapa[ori.getX()][ori.getY()].getUnitat();

            aux.restaMov(distanciaRecorreguda(ori, fi));

            mapa[fi.getX()][fi.getY()].setUnitat(aux, true); //La coloquem al destí
            mapa[ori.getX()][ori.getY()].eliminaUnitat(); //Eliminem la unitat del origen

            mapa[fi.getX()][fi.getY()].reset();
            mapa[ori.getX()][ori.getY()].reset();

            if (fi.getX() == meta.getX() && fi.getY() == meta.getY()) end = true;
        }
    }

    //todo
    public Boolean esFinal() {
        return end;
    }

    // CERCA DE LES CASELLES ON ES POT DESPLAÇAR LA UNITAT
    //todo ajuntar els metodes?

    /**
     @pre p es dins del mapa
     @post retorna una llista de posicions en funcio de s
     @return llista de posicions
     @param p es una posicio
     @param s es un string
     */
    public ArrayList<Posicio> getRang(Posicio p, String s) {
        ArrayList<Posicio> posicions = new ArrayList<Posicio>();
        int x = p.getX();
        int y = p.getY();
        int mov = 0;
        if (s.equals("Atac")) mov = p.getUnitat().getRang();
        else if (s.equals("Moure")) mov = p.getUnitat().getMovAct();
        else if (s.equals("Total")) mov = p.getUnitat().getRang() + p.getUnitat().getMovAct();


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

        posicions.remove(p);




        return posicions;
    }


    /**
     @pre p es dins del mapa, mov>0
     @post retorna una llista de posicions dins del rang en funcio de p i mov
     @return llista de posicions
     @param p es una posicio
     @param mov es un enter
     */
    public ArrayList<Posicio> getRang(Posicio p, Integer mov){
        ArrayList<Posicio> posicions = new ArrayList<Posicio>();
        int x = p.getX();
        int y = p.getY();

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

    /**
     @pre o i p es troben dins del mapa
     @post retorna cert si en o hi ha unitat i si en p i ha una unitat enemiga
     @return boolean
     @param o es una posicio
     @param p es una posicio
     */
    public Boolean ComprovaObjectiu (Posicio o, Posicio p){
        return (mapa[o.getX()][o.getY()].teUnitat() && mapa[o.getX()][o.getY()].getUnitat().Enemiga(p.getUnitat()));

    }

    //todo
    private void llegirMapa(String f) throws IOException {

        forts = new ArrayList<>();//LLUIS

        InputStream is = Mapa.class.getResourceAsStream(f);
        Scanner s = new Scanner(is);
        //Llegim el nom del mapa
        nom = s.nextLine().substring(4);

        //Llegim les mides X i Y del mapa
        String[] mida = s.nextLine().substring(5).split("x");

        String [] o = s.nextLine().substring(9).split("x");
        meta = new Posicio(Integer.parseInt(o[0]), Integer.parseInt(o[1]));

        MAXH = Integer.parseInt(mida[0]);
        MAXV = Integer.parseInt(mida[1]);

        mapa = new Posicio[MAXH][MAXV]; //Reservem memoria
        costosCamins = new Integer[MAXH][MAXV];

        Integer j = 0;

        while (s.hasNext()) { //Saltem les possibles linies en blanc
            String aux = s.nextLine();
            if (!aux.isEmpty()) {

                String[] pos = aux.split(" "); //Agafem la linia en questió

                for (int i = 0; i < pos.length; i++) {
                    mapa[i][j] = new Posicio(i, j); //Creem la nova posició
                    mapa[i][j].setTerreny(fabricaTerrenys(Integer.parseInt(pos[i]))); //Inserim el terreny determinat a la posició recent creada.
                    if (mapa[i][j].getTerreny().toString().equals("Fortress")) forts.add(mapa[i][j]);   //LLUIS
                    costosCamins[i][j] = mapa[i][j].getTerreny().getRedDespl();
                }
                j++;
            }
        }
        creaGraf();
        System.out.println("Lectura Correcte");

        s.close();
    }

    /**
     @pre --
     @post crea la llista de vertexs i les seves adjacencies amb els vertexs contigus
     @return void
     */
    private ArrayList<Vertex> creaGraf(){
        vertexs = new ArrayList<Vertex>();
        for (int i=0; i<MAXH; i++) {
            for (int j = 0; j < MAXV; j++) {
                vertexs.add(new Vertex(i + " " + j));
            }
        }
        // llista de tots els vertexs
        for (Vertex v1 : vertexs) {
            String[] s1 = v1.toString().split(" ");
            v1.iniciaAdjacencies();
            for (Vertex v2 : vertexs) {
                if (v1 != v2) {
                    String[] s2 = v2.toString().split(" ");
                    if (((Integer.parseInt(s2[0]) == Integer.parseInt(s1[0]) - 1 || Integer.parseInt(s2[0]) == Integer.parseInt(s1[0]) + 1) &&
                            Integer.parseInt(s2[1]) == Integer.parseInt(s1[1])) ||

                            ((Integer.parseInt(s2[1]) == Integer.parseInt(s1[1]) + 1 || Integer.parseInt(s2[1]) == Integer.parseInt(s1[1]) - 1) &&
                                    Integer.parseInt(s2[0]) == Integer.parseInt(s1[0]))) {

                        v1.getAdjacencies().add(new Relacio(v2, (costosCamins[Integer.parseInt(s2[0])][Integer.parseInt(s2[1])])));
                    }
                }
            }
        }
        return vertexs;
    }

    /**
     @pre p es troba dins del mapa
     @post cerca tots els camins minims desde p
     @return void
     @param p es una posicio
     */
    public void buscaCamiMinim(Posicio p){

        Vertex ori = getVertex(p.getX(), p.getY());
        iniciarAnteriors();
        busca(ori);

    }

    /**
     @pre --
     @post retorna la llista de Vertexs que cal recorrer per arribar a target
     @return llista de vertexs
     @param target es un vertex de la llista de vertexs
     */
    public List<Vertex> getCamiMin(Vertex target) {
        List<Vertex> path = new ArrayList<Vertex>();
        for (Vertex vertex = target; vertex != null; vertex = vertex.getAnterior()) {
            path.add(vertex);
        }
        Collections.reverse(path);
        return path;
    }

    /**
     @pre --
     @post inicialitza el valor de distancia minima a cada vertex i desvincula els camins
     @return void
     */
    private void iniciarAnteriors(){
         for (Vertex v : vertexs){
             v.setAnterior(null);
             v.setDistMin(999);
         }
    }

    /**
     @pre p es troba dins del mapa
     @post retorna el cost per arribar a p
     @return Integer
     @param p es una posicio del mapa
     */
    public Integer ValorCamiMin(Posicio p) {
        return getVertex(p.getX(),p.getY()).getDistMin();
    }

    /**
     @pre graf creat, inici existeix
     @post cerca el cami minim a tots els vertexs començant per inici
     @return void
     @param inici es un Vertex
     */
    public void busca(Vertex inici) {
        inici.setDistMin(0);
        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
        vertexQueue.add(inici);

        while (!vertexQueue.isEmpty()) {
            Vertex u = vertexQueue.poll();

            for (Relacio e : u.getAdjacencies())
            {
                Vertex v = e.getVert();
                Integer pes = e.getCost();
                Integer acumulat = u.getDistMin() + pes;
                if (acumulat < v.getDistMin()) {
                    vertexQueue.remove(v);
                    v.setDistMin(acumulat);
                    v.setAnterior(u);
                    vertexQueue.add(v);
                }
            }
        }
    }

    /**
     @pre x i y compleixen el rang del mapa
     @post retorna el vertex de la posicio x,y
     @return Vertex
     @param x es la component de la fila
     @param y es la component de la columna
     */
    public Vertex getVertex(int x, int y) {
        for (Vertex g : vertexs) {
            String[] str = g.toString().split(" ");
            if (Integer.parseInt(str[0]) == x && Integer.parseInt(str[1]) == y)
                return g;
        }
        return null;
    }



    //todo
    public void llegirUnitats(String u, Jugador a, Jugador b){
        InputStream is = Mapa.class.getResourceAsStream(u);
        Scanner s = new Scanner(is);
        Integer j = 0;
        Integer jugador = 1;
        Unitat aux;
        while (s.hasNext()) { //Saltem les possibles linies en blanc
            String s2 = s.nextLine();
            if (s2.isEmpty()) {
                j = 0;
                jugador++;
            } else {
                String[] pos = s2.split(" "); //Agafem la linia en questió
                for (int i = 0; i < pos.length; i++) {
                    aux = fabricaUnitats(Integer.parseInt(pos[i]));
                    if (aux != null) {
                        aux.setPropietari(jugador);
                        if (jugador == 1) a.getExercit().add(aux);
                        else b.getExercit().add(aux);
                        mapa[i][j].setUnitat(aux, true); //Afegim les unitats corresponents a les posicions
                    }
                }

                j++;
            }
        }
        s.close();
        System.out.println("Lectura d'unitats correcte");
    }

    /**
     @pre a i b son posicions dins del rang del mapa
     @post retorna la distancia entre les posicions a i b
     @return Integer
     @param a es una posicio del mapa
     @param b es una posicio del mapa
     */
    public static Integer distanciaRecorreguda(Posicio a, Posicio b) {
        return (Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY()));
    }


    /**
     @pre --
     @post retorna la llista de fortaleses del mapa
     @return forts es una llista que conte les fortaleses del mapa
     */
    public ArrayList<Posicio> getForts() {
        return forts;
    }


    /**
     @pre --
     @post retorna cert si una unitat del jugador 2 ha arribat a la posicio de la meta
     @return boolean
     */
    public boolean metaAconseguida() {
        if (mapa[meta.getX()][meta.getY()].teUnitat())
            return (mapa[meta.getX()][meta.getY()].getUnitat().getPropietari() == 2);
        return false;
    }
}
