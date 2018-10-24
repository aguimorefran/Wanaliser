import java.io.IOException;

public class Testing {
    public static void main(String[] args) throws IOException {
        Conversation c = new Conversation("conver.txt");
        c.analise();
        c.toString();
    }
}
