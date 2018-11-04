public class Word {
    private String word;
    private Double value;

    public Word(String w, Double v){
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
}
