import java.util.Scanner;
import java.util.Calendar;

public class Message {
    private String line, user, msg;
    private int hour;
    private int minute;
    private int day;
    private int month;
    private int year;
    private int dayOfWeek;
    private int words;
    private boolean isFile;

    public Message(String line) {
        this.line = line;
    }

    public void analise() {
        //EXAMPLE ->        24/07/2018, 21:37 - Pedro: Buenas Francisco Aguilera, estoy en un restaurante\n Espero que no te importe
        System.out.println("ANALYSING-> " + line);

        Scanner sc = new Scanner(line).useDelimiter("\\s*/\\s*|\\s*,\\s*|\\s*:\\s*|\\s*-\\s");
        Calendar c = Calendar.getInstance();

        //date
        day = sc.nextInt();
        month = sc.nextInt();
        year = sc.nextInt();
        c.set(year, month, day);
        dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;

        //time
        hour = sc.nextInt();
        minute = sc.nextInt();
        //user
        user = sc.next();
        sc.close();

        //message
        msg = line.substring(line.lastIndexOf(":") + 1);
        msg = msg.replace("\n", ".");
        if (msg.contains("<Media omitted>"))
            isFile = true;

        words = countWords(msg);

    }

    public int getWords() {
        return words;
    }

    private int countWords(String s) {
        int wordCount = 0;
        boolean word = false;
        int endOfLine = s.length() - 1;

        for (int i = 0; i < s.length(); i++) {
            // if the char is a letter, word = true.
            if (Character.isLetter(s.charAt(i)) && i != endOfLine) {
                word = true;
                // if char isn't a letter and there have been letters before,
                // counter goes up.
            } else if (!Character.isLetter(s.charAt(i)) && word) {
                wordCount++;
                word = false;
                // last word of String; if it doesn't end with a non letter, it
                // wouldn't count without this.
            } else if (Character.isLetter(s.charAt(i)) && i == endOfLine) {
                wordCount++;
            }
        }
        return wordCount;
    }

    public String getLine() {
        return line;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public boolean isFile() {
        return isFile;
    }

    @Override
    public String toString() {
        return "Message= {'" + msg + '\'' + '}';
    }

    public String getUser() {
        return user;
    }

    public String getMsg() {
        return msg;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }
}
