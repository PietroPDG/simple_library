package com.simplebooktracker.app;

import java.util.List;
import java.util.Scanner;

import com.simplebooktracker.database.DBManager;
import com.simplebooktracker.model.Libro;

public class MainApp {

    public static void main(String[] args) {
        DBManager dbManager = new DBManager();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Simple Book Tracker ---");
            System.out.println("1. Aggiungi un nuovo libro");
            System.out.println("2. Visualizza tutti i libri");
            System.out.println("0. Esci");
            System.out.print("Scegli un'opzione: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Input non valido. Inserisci un numero.");
                scanner.next();
                System.out.print("Scegli un'opzione: ");
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // Consuma il resto della riga

            switch (choice) {
                case 1:
                    System.out.print("Inserisci il titolo del libro: ");
                    String titolo = scanner.nextLine();
                    System.out.print("Inserisci l'autore del libro: ");
                    String autore = scanner.nextLine();
                    System.out.print("Il libro è stato letto? (s/n): ");
                    String inputLetto = scanner.nextLine().trim().toLowerCase();
                    boolean letto = inputLetto.equals("s");
                    
                    Libro nuovoLibro = new Libro(titolo, autore, letto);
                    dbManager.aggiungiLibro(nuovoLibro);
                    break;
                case 2:
                    System.out.println("\n--- Lista dei Libri ---");
                    List<Libro> libri = dbManager.getAllLibri();
                    if (libri.isEmpty()) {
                        System.out.println("Nessun libro presente.");
                    } else {
                        for (Libro libro : libri) {
                            System.out.println(libro);
                        }
                    }
                    break;
                case 0:
                    System.out.println("Uscita dall'applicazione. Arrivederci!");
                    break;
                default:
                    System.out.println("Opzione non valida. Riprova.");
            }
        } while (choice != 0);

        scanner.close();
    }
}
