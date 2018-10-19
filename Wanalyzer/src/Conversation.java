import java.io.*;

public class Conversation {
    private String filename;
    private User[] userList;
    private int nMsg, nFiles;

    public Conversation(String filename) {
        this.filename = filename;
    }

    public void analise() throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("src/" + filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String line;
        Message msg;
        //reads line by line the txt file
        while ((line = br.readLine()) != null) {
            msg = new Message(line);
            //discards double messages and weird things
            if (line.length() < 1 || line.startsWith("[") || !Character.isDigit(line.charAt(0)) || line.startsWith(" "))
                ;
            else
                msg.analise();
        }
    }
}
