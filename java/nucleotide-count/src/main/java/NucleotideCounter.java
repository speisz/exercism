import java.util.Map;
import java.util.Optional;

import static java.util.Arrays.stream;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

class NucleotideCounter {
    private String dna;
    private Map<Character, Integer> nucleotideCounts;

    NucleotideCounter(String dna) {
        this.dna = dna;
        computeNucleotideCounts();
    }

    int count(char dnaPart) {
        return Optional.ofNullable(nucleotideCounts.get(dnaPart)).orElseThrow(IllegalArgumentException::new);
    }

    Map<Character, Integer> nucleotideCounts() {
        return nucleotideCounts;
    }

    private void computeNucleotideCounts() {
        nucleotideCounts = stream(Nucleotide.values())
                .map(Nucleotide::asChar)
                .collect(toMap(identity(), this::countOccurencesInDna));
    }

    private int countOccurencesInDna(char nucleotideToCount) {
        return (int) dna.chars()
                .filter(nucleotide -> nucleotide == nucleotideToCount)
                .count();
    }

    enum Nucleotide {
        A, C, G, T;

        Character asChar() {
            return name().charAt(0);
        }
    }
}
