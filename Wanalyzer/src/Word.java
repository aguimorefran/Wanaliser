import java.util.Objects;

public class Word {
    private String word;
    private Double value;

    public Word(String w, Double v) {
        this.word = w;
        this.value = v;
    }

    public String getWord() {
        return word;
    }

    public Double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return word + " -> " + value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word1 = (Word) o;
        return Objects.equals(word, word1.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word);
    }
}
