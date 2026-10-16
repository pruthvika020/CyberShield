package cybershield.main;

import cybershield.co1.SignatureSearch;
import cybershield.co2.LogPhraseAnalysis;
import cybershield.co3.SignatureSimilarity;
import cybershield.co4.MaxFlow;
import cybershield.co5.VertexCoverApprox;
import cybershield.co6.ReservoirSampler;
import cybershield.data.CsvDataLoader;
import cybershield.model.SecurityEvent;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Main {
    private static final Scanner INPUT = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println(" CYBERSHIELD - SECURITY ANALYTICS");
        System.out.println("========================================");
        try {
            List<SecurityEvent> events = CsvDataLoader.loadEvents();
            System.out.println("Dataset loaded successfully: " + events.size() + " events");
            runMenu(events);
        } catch (IOException ex) {
            System.out.println("ERROR: " + ex.getMessage());
            System.out.println("Run the program from the project root folder.");
        } catch (NumberFormatException ex) {
            System.out.println("ERROR: Dataset contains a number in an invalid format.");
        }
    }

    private static void runMenu(List<SecurityEvent> events) {
        while (true) {
            System.out.println("\n--------------- MAIN MENU ---------------");
            System.out.println("1. Malware Signature Search       [CO1]");
            System.out.println("2. Repeated Log Phrase Analysis   [CO2]");
            System.out.println("3. Similar Signature Detection    [CO3]");
            System.out.println("4. Network Flow Analysis          [CO4]");
            System.out.println("5. Security Rule Conflict Analysis[CO5]");
            System.out.println("6. Event Stream Sampling          [CO6]");
            System.out.println("7. View All Security Logs");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            String choice = INPUT.nextLine().trim();

            try {
                switch (choice) {
                    case "1": malwareSearch(events); break;
                    case "2": phraseAnalysis(events); break;
                    case "3": similarityCheck(); break;
                    case "4": networkFlow(); break;
                    case "5": conflictAnalysis(); break;
                    case "6": sampleEvents(events); break;
                    case "7": viewLogs(events); break;
                    case "0":
                        System.out.println("CyberShield closed. Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid choice. Please enter 0-7.");
                }
            } catch (IOException | IllegalArgumentException ex) {
                System.out.println("Unable to complete operation: " + ex.getMessage());
            }
        }
    }

    private static void malwareSearch(List<SecurityEvent> events) {
        System.out.println("\n========== MALWARE SIGNATURE SEARCH ==========");
        System.out.print("Enter signature or text to search: ");
        String pattern = INPUT.nextLine().trim();
        if (pattern.isEmpty()) {
            System.out.println("Search text cannot be empty.");
            return;
        }
        List<SecurityEvent> matches = SignatureSearch.searchKmp(events, pattern);
        if (matches.isEmpty()) {
            System.out.println("No matching events found.");
        } else {
            for (SecurityEvent event : matches) {
                System.out.println("---------------------------------------------");
                System.out.println(event);
            }
        }
        System.out.println("---------------------------------------------");
        System.out.println("Total matches: " + matches.size());
        System.out.println("Algorithm: KMP");
    }

    private static void phraseAnalysis(List<SecurityEvent> events) {
        System.out.println("\n========== REPEATED LOG PHRASE ANALYSIS ==========");
        StringBuilder text = new StringBuilder();
        for (SecurityEvent event : events) {
            text.append(event.getEventType()).append(' ')
                .append(event.getSignature()).append(' ')
                .append(event.getSeverity()).append(" | ");
        }
        LogPhraseAnalysis.analyze(text.toString());
    }

    private static void similarityCheck() {
        System.out.println("\n========== SIGNATURE SIMILARITY ==========");
        System.out.print("Enter first signature: ");
        String first = INPUT.nextLine().trim();
        System.out.print("Enter second signature: ");
        String second = INPUT.nextLine().trim();
        if (first.isEmpty() || second.isEmpty()) {
            System.out.println("Both signatures are required.");
            return;
        }
        int distance = SignatureSimilarity.levenshtein(first, second);
        System.out.println("First signature : " + first);
        System.out.println("Second signature: " + second);
        System.out.println("Edit distance   : " + distance);
        System.out.println("Interpretation  : " +
                (distance == 0 ? "Exact match" :
                 distance <= 2 ? "Similar strings (review manually)" :
                                  "Different strings"));
        System.out.println("Algorithm: Levenshtein Edit Distance");
    }

    private static void networkFlow() throws IOException {
        System.out.println("\n========== NETWORK FLOW ANALYSIS ==========");
        List<String[]> edges = CsvDataLoader.loadEdges("dataset/network.csv", 3);
        System.out.print("Enter source node (A/B/C/D): ");
        String source = INPUT.nextLine().trim();
        System.out.print("Enter sink node (A/B/C/D): ");
        String sink = INPUT.nextLine().trim();
        if (source.isEmpty() || sink.isEmpty() || source.equals(sink)) {
            System.out.println("Source and sink must be different non-empty nodes.");
            return;
        }
        int flow = MaxFlow.calculate(edges, source, sink);
        System.out.println("Source node : " + source);
        System.out.println("Sink node   : " + sink);
        System.out.println("Maximum flow: " + flow);
        System.out.println("Algorithm   : Edmonds-Karp");
        System.out.println("Note: Flow uses the capacities in dataset/network.csv.");
    }

    private static void conflictAnalysis() throws IOException {
        System.out.println("\n========== SECURITY RULE CONFLICTS ==========");
        List<String[]> edges = CsvDataLoader.loadEdges("dataset/security_conflicts.csv", 2);
        Set<String> cover = VertexCoverApprox.approximate(edges);
        System.out.println("Conflict edges loaded: " + edges.size());
        System.out.println("Selected covering rules/assets: " + cover);
        System.out.println("Cover size: " + cover.size());
        System.out.println("Algorithm: Vertex Cover 2-Approximation");
        System.out.println("Note: Approximation selects both endpoints of each uncovered edge.");
    }

    private static void sampleEvents(List<SecurityEvent> events) {
        System.out.println("\n========== EVENT STREAM SAMPLING ==========");
        System.out.print("How many events should be sampled? ");
        String value = INPUT.nextLine().trim();
        int count;
        try {
            count = Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            System.out.println("Enter a whole number.");
            return;
        }
        if (count < 0) {
            System.out.println("Sample size cannot be negative.");
            return;
        }
        List<SecurityEvent> sample = ReservoirSampler.sample(events, count, new Random());
        System.out.println("Events available: " + events.size());
        System.out.println("Events selected : " + sample.size());
        for (SecurityEvent event : sample) {
            System.out.println("---------------------------------------------");
            System.out.println(event);
        }
        System.out.println("Algorithm: Reservoir Sampling (random sample)");
    }

    private static void viewLogs(List<SecurityEvent> events) {
        System.out.println("\n========== ALL SECURITY LOGS ==========");
        if (events.isEmpty()) {
            System.out.println("No valid events found in dataset.");
            return;
        }
        for (SecurityEvent event : events) {
            System.out.println("---------------------------------------------");
            System.out.println(event);
        }
        System.out.println("---------------------------------------------");
        System.out.println("Total valid records: " + events.size());
    }
}
