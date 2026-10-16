# CyberShield — Java Security Analytics Platform

CyberShield is a Java-based security analytics simulator designed to analyze security events, identify suspicious patterns, and explore network security using Data Structures and Algorithms.

## Features

- **Signature Search:** Find security patterns in event data using the Knuth–Morris–Pratt (KMP) algorithm.
- **Repeated Pattern Analysis:** Analyze repeated substrings using Suffix Arrays and the Kasai LCP algorithm.
- **Fuzzy Signature Matching:** Compare text patterns using Levenshtein Edit Distance.
- **Network Flow Analysis:** Calculate maximum flow in a network using the Edmonds–Karp algorithm.
- **Conflict Analysis:** Apply a Vertex Cover approximation algorithm to identify related nodes in a conflict graph.
- **Random Sampling:** Select representative security events using Reservoir Sampling.
- **CSV Data Processing:** Load and analyze security events from CSV datasets.

## Technologies Used

- Java 8+
- Data Structures and Algorithms
- CSV File Handling
- Eclipse IDE

## Project Structure

```text
CyberShield/
├── dataset/
│   ├── security_logs.csv
│   ├── network.csv
│   └── security_conflicts.csv
├── src/
│   └── cybershield/
└── README.md
```

## Getting Started

1. Install Java JDK 8 or later and Eclipse IDE.
2. Clone or download this repository.
3. Import the project into Eclipse as an existing Java project, or create a Java project and copy the `src` and `dataset` folders into its root.
4. Ensure `src` is configured as a source folder.
5. Run `src/cybershield/main/Main.java`.
6. Keep the `dataset` folder in the project root so the application can locate its CSV files.

## Dataset

The project includes demonstration datasets for security logs, network capacities, and conflict relationships. All records are fictional and intended for educational and testing purposes.

## Future Improvements

- Extend signature matching to support multiple patterns.
- Add improved security event filtering and reporting.
- Introduce a graphical dashboard for visualizing analysis results.

## Disclaimer

CyberShield is an educational security analytics simulator. It is not a production-grade security monitoring system.

## Author

Pruthvika Pappula
