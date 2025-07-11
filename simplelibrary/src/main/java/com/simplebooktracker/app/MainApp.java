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
            menu();

            while (!scanner.hasNextInt()) {
                System.out.println("Input non valido. Inserisci un numero.");
                scanner.next();
                System.out.print("Scegli un'opzione: ");
            }
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                	System.out.println("\n--- LISTA LIBRI ---");
                	int numLibri = visualizzaListaLibri(dbManager);
                	if(numLibri != 0) {
                		System.out.println("\nNumero totale libri in libreria: " + numLibri);
                	}
                	System.out.println("\n--- FINE LISTA LIBRI ---");
                    break;
                case 2:
                	System.out.println("\n--- INSERIMENTO NUOVO LIBRO ---");
                	inserisciLibro(dbManager, scanner);
                	System.out.println("\n--- FINE INSERIMENTO NUOVO LIBRO ---");
                    break;
                case 3:
                	System.out.println("\n--- MODIFICA LIBRO ---");
                	modificaLibro(dbManager, scanner);
                	System.out.println("\n--- FINE MODIFICA LIBRO ---");
                    break;
                case 4:
                	System.out.println("\n--- ELIMINA LIBRO ---");
                	eliminaLibro(dbManager, scanner);
                	System.out.println("\n--- FINE ELIMINA LIBRO ---");
                    break;
                case 5:
                	System.out.println("Funzione disponibile a breve. WORK IN PROGRESS");
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

	private static void menu() {
		System.out.println("\n--- Simple Book Tracker ---");
		System.out.println("1. Visualizza tutti i libri");
		System.out.println("2. Aggiungi un nuovo libro");
		System.out.println("3. Modifica dati di un libro");
		System.out.println("4. Elimina un libro");
		System.out.println("5. Statistiche");
		System.out.println("0. Esci");
		System.out.print("Scegli un'opzione: ");
	}

	private static void eliminaLibro(DBManager dbManager, Scanner scanner) {
		System.out.print("\nScegli un libro da eliminare\n");
		visualizzaListaLibri(dbManager);
		
        System.out.print("\nInserisci l'ID del libro da eliminare: ");
        while (!scanner.hasNextInt()) {
        	System.out.print("ID inserito non valido. Inserisci un ID valido (o -1 per ritornare al menu) : ");
			scanner.next();
        }
        
        int idElimina = scanner.nextInt();
        if (idElimina != -1) {
        	scanner.nextLine();
            dbManager.eliminaLibro(idElimina);
        }
	}

	private static void modificaLibro(DBManager dbManager, Scanner scanner) {
		System.out.print("\nScegli un libro da modificare\n");
		visualizzaListaLibri(dbManager);
		
		System.out.print("\nInserisci l'ID del libro da modificare (o -1 per ritornare al menu) : ");
		while (!scanner.hasNextInt()) {
			System.out.print("ID inserito non valido. Inserisci un ID valido (o -1 per ritornare al menu) : ");
			scanner.next();
		}
        int idModifica = scanner.nextInt();
        
        if (idModifica != -1) {
	        scanner.nextLine();
	        Libro libroDaModificare = dbManager.getLibroById(idModifica);
	        if (libroDaModificare == null) {
	            System.out.println("Libro con ID " + idModifica + " non trovato.");
	        } else {
	            System.out.println("Modifica libro: " + libroDaModificare);
	            System.out.print("Nuovo titolo (lascia vuoto per non modificare): ");
	            String nuovoTitolo = scanner.nextLine();
	            if (!nuovoTitolo.isEmpty()) {
	                libroDaModificare.setTitolo(nuovoTitolo);
	            }
	
	            System.out.print("Nuovo autore (lascia vuoto per non modificare): ");
	            String nuovoAutore = scanner.nextLine();
	            if (!nuovoAutore.isEmpty()) {
	                libroDaModificare.setAutore(nuovoAutore);
	            }
	
	            System.out.print("Il libro è letto? (s/n) [attuale: " + (libroDaModificare.isLetto() ? "s" : "n") + "]: ");
	            String inputLettoModifica = scanner.nextLine().trim().toLowerCase();
	            if (!inputLettoModifica.isEmpty()) {
	                libroDaModificare.setLetto(inputLettoModifica.equals("s"));
	            }
	
	            dbManager.aggiornaLibro(libroDaModificare);
	        }
        }
		
	}

	private static int visualizzaListaLibri(DBManager dbManager) {
		List<Libro> libri = dbManager.getAllLibri();
		if (libri.isEmpty()) {
		    System.out.println("\nNessun libro presente.");
		} else {
		    for (Libro libro : libri) {
		        System.out.println(libro);
		    }
		}
		
		return libri.size();
	}

	private static void inserisciLibro(DBManager dbManager, Scanner scanner) {
		
		System.out.print("Inserisci il titolo del libro: ");
		String titolo = scanner.nextLine();
		System.out.print("Inserisci l'autore del libro: ");
		String autore = scanner.nextLine();
		System.out.print("Il libro è stato letto? (s/n): ");
		String inputLetto = scanner.nextLine().trim().toLowerCase();
		
		while (!inputLetto.equals("s") && !inputLetto.equals("n")) {
			System.out.print("Input non valido. Il libro è stato letto? (s/n): ");
			inputLetto = scanner.nextLine().trim().toLowerCase();
		}
		
		boolean letto = inputLetto.equals("s");
		
		Libro nuovoLibro = new Libro(titolo, autore, letto);
		dbManager.aggiungiLibro(nuovoLibro);
	}
}
