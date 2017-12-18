package carsFX.model.enums;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.Serializable;

public enum Marca implements Serializable {

    VOLKSWAGEN("Volkswagen", "volkswagen.png"),
    CITROEN("Citroën", "citroen.png"),
    PEUGEOT("Peugeot", "peugeot.png"),
    DACIA("Dacia", "dacia.png"),
    RENAULT("Renault", "renault.png"),
    BMW("BMW", "bmw.png"),
    MERCEDES_BENZ("Mercedes-Benz", "mercedes-benz.png"),
    TESLA("Tesla", "tesla.png");

    private static final String PATH = "/logo/";
    private String nome, path;
    //private ImageView logo;

    Marca(String nome, String path) {
        this.nome = nome;
        this.path = path;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return nome;
    }

    public ImageView getLogo() {
        return new ImageView(new Image(getClass().getResourceAsStream(PATH + path)));
    }

}
