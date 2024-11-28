package it.epicode;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Prodotti> prodotti = inizializzaProdotti();
        List<Ordini> ordini = inizializzaOrdini(prodotti);

        // Esercizio #1: Raggruppare ordini per cliente
        Map<Clienti, List<Ordini>> mappaClientiOrdini = raggruppaOrdiniPerCliente(ordini);
        System.out.println("Esercizio #1 - Mappa clienti -> ordini: " + mappaClientiOrdini);

        // Esercizio #2: Totale vendite per cliente
        Map<Clienti, Double> totaleVenditePerCliente = calcolaTotaleVenditePerCliente(ordini);
        System.out.println("Esercizio #2 - Totale vendite per cliente: " + totaleVenditePerCliente);

        // Esercizio #3: Trovare il prodotto più costoso
        Prodotti prodottoPiuCostoso = trovaProdottoPiuCostoso(prodotti);
        System.out.println("Esercizio #3 - Prodotto più costoso: " + prodottoPiuCostoso);

        // Esercizio #4: Calcolare la media degli importi degli ordini
        double mediaImportiOrdini = calcolaMediaImportiOrdini(ordini);
        System.out.println("Esercizio #4 - Media degli importi degli ordini: " + mediaImportiOrdini);

        // Esercizio #5: Raggruppare prodotti per categoria e sommare importi
        Map<String, Double> sommaImportiPerCategoria = calcolaSommaImportiPerCategoria(prodotti);
        System.out.println("Esercizio #5 - Somma degli importi per categoria: " + sommaImportiPerCategoria);
    }

    // Esercizio #1: Raggruppare gli ordini per cliente
    private static Map<Clienti, List<Ordini>> raggruppaOrdiniPerCliente(List<Ordini> ordini) {
        // Utilizza Collectors.groupingBy per raggruppare gli ordini in base al cliente
        return ordini.stream()
                .collect(Collectors.groupingBy(Ordini::getCustomer));
    }

    // Esercizio #2: Calcolare il totale delle vendite per ogni cliente
    private static Map<Clienti, Double> calcolaTotaleVenditePerCliente(List<Ordini> ordini) {
        // Raggruppa per cliente e calcola la somma dei prezzi dei prodotti per ogni cliente
        return ordini.stream()
                .collect(Collectors.groupingBy(
                        Ordini::getCustomer,
                        Collectors.summingDouble(o -> o.getProducts().stream()
                                .mapToDouble(Prodotti::getPrezzo)
                                .sum())
                ));
    }

    // Esercizio #3: Trovare il prodotto più costoso
    private static Prodotti trovaProdottoPiuCostoso(List<Prodotti> prodotti) {
        // Utilizza max per trovare il prodotto con il prezzo più alto
        return prodotti.stream()
                .max((p1, p2) -> Double.compare(p1.getPrezzo(), p2.getPrezzo()))
                .orElse(null); // Restituisce null se la lista è vuota
    }

    // Esercizio #4: Calcolare la media degli importi degli ordini
    private static double calcolaMediaImportiOrdini(List<Ordini> ordini) {
        // Calcola la media sommando i prezzi di tutti i prodotti di ogni ordine
        return ordini.stream()
                .mapToDouble(o -> o.getProducts().stream()
                        .mapToDouble(Prodotti::getPrezzo)
                        .sum())
                .average()
                .orElse(0); // Restituisce 0 se la lista è vuota
    }

    // Esercizio #5: Raggruppare prodotti per categoria e calcolare la somma degli importi
    private static Map<String, Double> calcolaSommaImportiPerCategoria(List<Prodotti> prodotti) {
        // Raggruppa i prodotti per categoria e calcola la somma dei prezzi per categoria
        return prodotti.stream()
                .collect(Collectors.groupingBy(
                        Prodotti::getCategoria,
                        Collectors.summingDouble(Prodotti::getPrezzo)
                ));
    }

    // Metodo di supporto per inizializzare i prodotti
    private static List<Prodotti> inizializzaProdotti() {
        Prodotti p1 = new Prodotti("libro1", "book", 150, 1);
        Prodotti p2 = new Prodotti("libro2", "book", 30, 2);
        Prodotti p3 = new Prodotti("libro3", "book", 300, 3);
        Prodotti p4 = new Prodotti("gioco", "baby", 50, 4);
        Prodotti p5 = new Prodotti("ps5", "boys", 500, 5);

        List<Prodotti> prodotti = new ArrayList<>();
        prodotti.add(p1);
        prodotti.add(p2);
        prodotti.add(p3);
        prodotti.add(p4);
        prodotti.add(p5);

        return prodotti;
    }

    // Metodo di supporto per inizializzare gli ordini
    private static List<Ordini> inizializzaOrdini(List<Prodotti> prodotti) {
        Clienti c1 = new Clienti("Andrea", 2, 1);
        Clienti c2 = new Clienti("Luca", 2, 2);
        Clienti c3 = new Clienti("Michele", 2, 3);

        Ordini ordine1 = new Ordini(c2);
        ordine1.setOrderDate(LocalDate.of(2021, Month.FEBRUARY, 15));
        ordine1.setProducts(List.of(prodotti.get(0), prodotti.get(1)));

        Ordini ordine2 = new Ordini(c1);
        ordine2.setOrderDate(LocalDate.of(2021, Month.MARCH, 10));
        ordine2.setProducts(List.of(prodotti.get(2)));

        Ordini ordine3 = new Ordini(c3);
        ordine3.setOrderDate(LocalDate.of(2021, Month.JANUARY, 25));
        ordine3.setProducts(List.of(prodotti.get(3), prodotti.get(4)));

        List<Ordini> ordini = new ArrayList<>();
        ordini.add(ordine1);
        ordini.add(ordine2);
        ordini.add(ordine3);

        return ordini;
    }
}
