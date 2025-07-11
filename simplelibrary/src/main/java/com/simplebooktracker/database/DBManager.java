package com.simplebooktracker.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.simplebooktracker.model.Libro;

public class DBManager {

    private static final String DB_URL = "jdbc:sqlite:database.db";

    public DBManager() {
        try {
            // Carica il driver JDBC di SQLite (non strettamente necessario con JDBC 4.0+, ma buona pratica)
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("Errore: Driver SQLite non trovato. Assicurati che la dipendenza sia corretta.");
            e.printStackTrace();
        }
        createNewDatabase();
        creaTabeallaLibro();
    }

    // Metodo per creare il database se non esiste
    private void createNewDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            if (conn != null) {
                System.out.println("Database creato o connesso con successo.");
            }
        } catch (SQLException e) {
            System.err.println("Errore nella creazione/connessione al database: " + e.getMessage());
        }
    }

    private void creaTabeallaLibro() {
        String sql = "CREATE TABLE IF NOT EXISTS libri ("
                   + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                   + " titolo TEXT NOT NULL,"
                   + " autore TEXT NOT NULL,"
                   + " letto INTEGER DEFAULT 0"
                   + ");";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabella 'libri' creata o già esistente.");
        } catch (SQLException e) {
            System.err.println("Errore nella creazione della tabella 'libri': " + e.getMessage());
        }
    }

    // Metodo per aggiungere un nuovo libro
    public void aggiungiLibro(Libro libro) {
        String sql = "INSERT INTO libri(titolo, autore, letto) VALUES(?,?,?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, libro.getTitolo());
            pstmt.setString(2, libro.getAutore());
            pstmt.setInt(3, libro.isLetto() ? 1 : 0);
            pstmt.executeUpdate();
            String letto = libro.isLetto() ? "letto" : "non letto";
            System.out.println("Libro aggiunto: " + libro.getTitolo() + " di " + libro.getAutore() + "; stato: " + letto);
        } catch (SQLException e) {
            System.err.println("Errore nell'aggiunta del libro: " + e.getMessage());
        }
    }

    // Metodo per ottenere tutti i libri
    public List<Libro> getAllLibri() {
        List<Libro> libri = new ArrayList<>();
        String sql = "SELECT id, titolo, autore, letto FROM libri";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String titolo = rs.getString("titolo");
                String autore = rs.getString("autore");
                boolean letto = rs.getInt("letto") == 1;
                libri.add(new Libro(id, titolo, autore, letto));
            }
        } catch (SQLException e) {
            System.err.println("Errore nel recupero dei libri: " + e.getMessage());
        }
        return libri;
    }
}