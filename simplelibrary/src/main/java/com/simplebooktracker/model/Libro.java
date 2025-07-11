package com.simplebooktracker.model;

public class Libro {
    private int id;
    private String titolo;
    private String autore;
    private boolean letto = false;

    // Costruttore per creare un Libro senza ID
    public Libro(String titolo, String autore, boolean letto) {
        this.titolo = titolo;
        this.autore = autore;
        this.letto = letto;
    }

    // Costruttore per creare un Libro con ID 
    public Libro(int id, String titolo, String autore, boolean letto) {
        this.id = id;
        this.titolo = titolo;
        this.autore = autore;
        this.letto = letto;
    }

    public int getId() {
        return id;
    }

    public String getTitolo() {
        return titolo;
    }

    public String getAutore() {
        return autore;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    
    public boolean isLetto() {
		return letto;
	}

	public void setLetto(boolean letto) {
		this.letto = letto;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Libro [id=");
		builder.append(id);
		builder.append(", titolo=");
		builder.append(titolo);
		builder.append(", autore=");
		builder.append(autore);
		builder.append(", letto=");
		builder.append(letto);
		builder.append("]");
		return builder.toString();
	}
}