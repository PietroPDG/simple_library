package com.simplebooktracker.model;

public class Libro {
    private int id;
    private String titolo;
    private String autore;
    private boolean letto = false;
    
    private String dataInserimento;
    private String dataUltimaModifica;
    /*
     * TODO
     * isbn
     * autore --> (altra tabella)
     * numero pagine
     * data pubblicazione
     * anno pubblicazione
     * casa editrice --> (altra tabella)
     * dataInizioLettura --> elenco delle letture (altra tabella)
     * dataFineLettura --> elenco delle letture (altra tabella)
     * segnalibro (es. sto a pagina 60)
     * prezzo
     * prestato ---> prestito (altra tabella), da chi (altra tabella), da quando a quando
     * regalo
     * regalato da --> da chi (altra tabella)
     * firmato
     * dedica
     * firmato con dedica
     * luogoAcquisto ---> (altra tabella)
     * dataAcquisto
     * epub
     * cartaceo
     * pagato
     * pagatoConBuono
     * notePagatoConBuono (es pagato con buono regalo, pagato con buono aziendale...)
     * serie --> (altra tabella) (es. Hunger Games)
     * volume
     * valutazione --> es. da 1 a 5 con i mezzi
     * numeroCopie
     * note
     * descrizione
     * formato --> (altra tabella)
     * listaScaffali --> (altra tabella)
     */

    // Costruttore per creare un Libro senza ID
    public Libro(String titolo, String autore, boolean letto) {
        this.titolo = titolo;
        this.autore = autore;
        this.letto = letto;
    }

    // Costruttore per creare un Libro con ID 
    public Libro(int id, String titolo, String autore, boolean letto, String dataInserimento, String dataUltimaModifica) {
        this.id = id;
        this.titolo = titolo;
        this.autore = autore;
        this.letto = letto;
        this.dataInserimento = dataInserimento;
        this.dataUltimaModifica = dataUltimaModifica;
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

	public String getDataInserimento() {
		return dataInserimento;
	}

	public void setDataInserimento(String dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	public String getDataUltimaModifica() {
		return dataUltimaModifica;
	}

	public void setDataUltimaModifica(String dataUltimaModifica) {
		this.dataUltimaModifica = dataUltimaModifica;
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
		builder.append(", dataInserimento=");
		builder.append(dataInserimento);
		builder.append(", dataUltimaModifica=");
		builder.append(dataUltimaModifica);
		builder.append("]");
		return builder.toString();
	}
}