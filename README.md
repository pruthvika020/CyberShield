# CyberShield — Java DSA Project

A console-based educational security analytics simulator using Java and CSV datasets.

## Modules
- CO1: KMP signature search
- CO2: Suffix Array + Kasai LCP repeated-substring analysis
- CO3: Levenshtein edit distance
- CO4: Edmonds-Karp maximum flow
- CO5: Vertex Cover 2-approximation
- CO6: Reservoir sampling
- Shared: CSV data loader and SecurityEvent model

## Eclipse setup
1. Extract the ZIP.
2. In Eclipse choose **File → Import → Existing Projects into Workspace** if the project was imported as a project. If Eclipse doesn't recognize it, create a Java Project named `CyberShield_Java_Project`, then copy `src` and `dataset` into the project root.
3. Ensure `src` is marked as the source folder.
4. Run `src/cybershield/main/Main.java`.
5. Keep the `dataset` directory directly under the project root because the program reads relative paths like `dataset/security_logs.csv`.

## Dataset files
- `dataset/security_logs.csv`: used by CO1, CO2, CO3 inputs (CO3 compares user-provided signatures), CO6.
- `dataset/network.csv`: directed graph capacities for CO4.
- `dataset/security_conflicts.csv`: conflict graph edges for CO5.

All included records are fictional demonstration data, not real security incidents.

## Notes
- Java 8+ compatible; no external dependencies.
- CO1 currently uses KMP for single-pattern matching. The signature list is exposed for extension to full Aho-Corasick multi-pattern matching if required by the course rubric.
- CO2 analyzes the combined event-type/signature/severity text from the loaded CSV rows.
- CO4 expects a directed graph; enter nodes that appear in `network.csv`.
- CO5 is an approximation algorithm and does not guarantee the minimum vertex cover.
- CO6 samples randomly; results may differ between runs.
