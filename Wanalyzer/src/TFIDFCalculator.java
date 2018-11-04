import java.util.Arrays;
import java.util.List;

/**
 * @author Mohamed Guendouz
 */
public class TFIDFCalculator {
    /**
     * @param doc  list of strings
     * @param term String represents a term
     * @return term frequency of term in document
     */
    public double tf(List<String> doc, String term) {
        double result = 0;
        for (String word : doc) {
            if (term.equalsIgnoreCase(word))
                result++;
        }
        return result / doc.size();
    }

    /**
     * @param docs list of list of strings represents the dataset
     * @param term String represents a term
     * @return the inverse term frequency of term in documents
     */
    public double idf(List<List<String>> docs, String term) {
        double n = 0;
        for (List<String> doc : docs) {
            for (String word : doc) {
                if (term.equalsIgnoreCase(word)) {
                    n++;
                    break;
                }
            }
        }
        return Math.log(docs.size() / n);
    }

    /**
     * @param doc  a text document
     * @param docs all documents
     * @param term term
     * @return the TF-IDF of term
     */
    public double tfIdf(List<String> doc, List<List<String>> docs, String term) {
        return tf(doc, term) * idf(docs, term);

    }

    public static void main(String[] args) {
        Word[] wordList = new Word[20];
        int m = 0;

        List<String> words = Arrays.asList("Lorem", "dolor", "sit", "ipsum", "Vituperata", "incorrupte", "at", "pro", "quo"
                , "Has", "persius", "disputationi", "id", "simul");

        List<String> doc1 = Arrays.asList("Lorem", "ipsum", "dolor", "ipsum", "sit", "ipsum");
        List<String> doc2 = Arrays.asList("Vituperata", "incorrupte", "at", "ipsum", "pro", "quo");
        List<String> doc3 = Arrays.asList("Has", "persius", "disputationi", "id", "simul");
        List<List<String>> documents = Arrays.asList(doc1, doc2, doc3);

        TFIDFCalculator calculator = new TFIDFCalculator();
        double tfidf = calculator.tfIdf(doc1, documents, "ipsum");
        //System.out.println("TF-IDF (ipsum) = " + tfidf);

        for (String s : words) {
            double avg = 0;
            int n = 0;
            for (List doc : documents) {
                double total = calculator.tfIdf(doc, documents, s);

                n++;
                avg += total;

            }

            wordList[m] = new Word(s, avg / n);
            m++;
        }

        for (int i = 0; i < m - 1; i++) {
            for (int j = 0; j < m - i - 1; j++) {
                if (wordList[j].getValue() < wordList[j + 1].getValue()) {
                    Word foo = wordList[j];
                    wordList[j] = wordList[j + 1];
                    wordList[j + 1] = foo;
                }
            }
        }

        for (int i = 0; i < 5; i++)
            System.out.println(wordList[i].toString());

    }

}
