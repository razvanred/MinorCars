package it.minoranza.minorgroup.commons.model.enums;

public enum Filiale{

    TREVISO("Treviso", "3viso"),
    ODERZO("Oderzo", "orz"),
    VITTORIO_VNT("Vittorio Veneto", "vittVeneto"),
    MOGLIANO_VNT("Mogliano Veneto", "moglVeneto");

    private String nome, password;

    Filiale(String nome, String password) {
        this.nome = nome;
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public String getPassword() {
        return password;
    }
}
