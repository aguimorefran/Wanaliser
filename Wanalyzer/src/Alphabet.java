import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.Collator;
import java.text.Normalizer;
import java.util.*;

public class Alphabet {
    public List<String> dictionary;

    public Alphabet() throws IOException {
        dictionary = new ArrayList<String>();
        initializeDictionary();
    }

    private void initializeDictionary() throws IOException {
        BufferedReader br = null;
        String line;
        try {
            br = new BufferedReader(new FileReader("src/alphabet.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while ((line = br.readLine()) != null) {
            dictionary.add(stripAccents(line.toLowerCase()));
        }
        Collections.sort(dictionary);
        System.out.println("Dictionary created [" + dictionary.size() + "]");
        br.close();
    }

    public int checkWord(String word) {
        return Collections.binarySearch(dictionary, stripAccents(word));
    }

    public static String stripAccents(String string) {
        return Normalizer.normalize(string, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    public static void main(String[] args) throws IOException {
        Alphabet a = new Alphabet();
        System.out.println(a.checkWord("abas"));
    }
}
