package sample.terrenys;

import javafx.scene.image.Image;

import java.io.InputStream;

/**
 * Created by OriolTestart on 18/4/15.
 */
public class Terreny extends Image {
    private String nom;

    public Terreny(String s) {
        super("sample/terrenys/flatheightmap.jpg");
        nom = s;
    }

    public Terreny(String s, boolean b) {
        super(s, b);
    }

    public Terreny(String s, double v, double v1, boolean b, boolean b1) {
        super(s, v, v1, b, b1);
    }

    public Terreny(String s, double v, double v1, boolean b, boolean b1, boolean b2) {
        super(s, v, v1, b, b1, b2);
    }

    public Terreny(InputStream inputStream) {
        super(inputStream);
    }

    public Terreny(InputStream inputStream, double v, double v1, boolean b, boolean b1) {
        super(inputStream, v, v1, b, b1);
    }
}
