package com.simplebooktracker.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.simplebooktracker.model.Libro;

public class DBManager {

    private static final String DB_URL = "jdbc:sqlite:database.db";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");

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
                   + " letto INTEGER DEFAULT 0,"
                   + " data_inserimento TEXT NOT NULL,"
                   + " data_ultima_modifica TEXT NOT NULL"
                   + ");";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabella 'libri' creata o già esistente.");
        } catch (SQLException e) {
            System.err.println("Errore nella creazione della tabella 'libri': " + e.getMessage());
        }
    }

    public void aggiungiLibro(Libro libro) {
        String sql = "INSERT INTO libri(titolo, autore, letto, data_inserimento, data_ultima_modifica) VALUES(?,?,?,?,?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, libro.getTitolo());
            pstmt.setString(2, libro.getAutore());
            pstmt.setInt(3, libro.isLetto() ? 1 : 0);
            pstmt.setString(4, LocalDateTime.now().format(FORMATTER));
            pstmt.setString(5, LocalDateTime.now().format(FORMATTER));
            
            pstmt.executeUpdate();
            System.out.println("Libro aggiunto: " + libro.getTitolo() + " di " + libro.getAutore() + "; stato: " + (libro.isLetto() ? "letto" : "non letto"));
        } catch (SQLException e) {
            System.err.println("Errore nell'aggiunta del libro: " + e.getMessage());
        }
    }

    // Metodo per ottenere tutti i libri
    public List<Libro> getAllLibri() {
        List<Libro> libri = new ArrayList<>();
        String sql = "SELECT * FROM libri";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String titolo = rs.getString("titolo");
                String autore = rs.getString("autore");
                boolean letto = rs.getInt("letto") == 1;
                String dataInserimento = rs.getString("data_inserimento");
                String dataUltimaModifica = rs.getString("data_ultima_modifica");
                libri.add(new Libro(id, titolo, autore, letto, dataInserimento, dataUltimaModifica));
            }
        } catch (SQLException e) {
            System.err.println("Errore nel recupero dei libri: " + e.getMessage());
        }
        return libri;
    }
    
    /**
     * Recupera un libro specifico dal database tramite il suo ID.
     * Utile per la modifica, per caricare i dati attuali del libro.
     * @param id L'ID del libro da recuperare.
     * @return L'oggetto Libro corrispondente all'ID, o null se non trovato.
     */
    public Libro getLibroById(int id) {
        String sql = "SELECT * FROM libri WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String titolo = rs.getString("titolo");
                String autore = rs.getString("autore");
                boolean letto = rs.getInt("letto") == 1;
                String dataInserimento = rs.getString("data_inserimento");
                String dataUltimaModifica = rs.getString("data_ultima_modifica");
                return new Libro(id, titolo, autore, letto, dataInserimento, dataUltimaModifica);
            }
        } catch (SQLException e) {
            System.err.println("Errore nel recupero del libro con ID " + id + ": " + e.getMessage());
        }
        return null;
    }


    /**
     * Elimina un libro dal database.
     * @param id L'ID del libro da eliminare.
     */
    public void eliminaLibro(int id) {
        String sql = "DELETE FROM libri WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Libro con ID " + id + " eliminato con successo.");
            } else {
                System.out.println("Nessun libro trovato con ID " + id + " per l'eliminazione.");
            }
        } catch (SQLException e) {
            System.err.println("Errore nell'eliminazione del libro con ID " + id + ": " + e.getMessage());
        }
    }

    /**
     * Aggiorna i dati di un libro esistente nel database.
     * @param libro L'oggetto Libro con i dati aggiornati (l'ID deve corrispondere a un libro esistente).
     */
    public void aggiornaLibro(Libro libro) {
        String sql = "UPDATE libri SET titolo = ?, autore = ?, letto = ?, data_ultima_modifica = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, libro.getTitolo());
            pstmt.setString(2, libro.getAutore());
            pstmt.setInt(3, libro.isLetto() ? 1 : 0);
            pstmt.setString(4, LocalDateTime.now().format(FORMATTER));
            pstmt.setInt(5, libro.getId());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Libro con ID " + libro.getId() + " aggiornato con successo.");
            } else {
                System.out.println("Nessun libro trovato con ID " + libro.getId() + " per l'aggiornamento.");
            }
        } catch (SQLException e) {
            System.err.println("Errore nell'aggiornamento del libro con ID " + libro.getId() + ": " + e.getMessage());
        }
    }
}