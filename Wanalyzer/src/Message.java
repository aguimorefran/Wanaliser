import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.Scanner;
import java.util.Calendar;

public class Message {
    private String line, user, msg;
    private DateTime date;
    private int words;
    private boolean isFile;

    public Message(String line) {
        this.line = line;
    }

    public void analise() {
        //EXAMPLE ->        24/07/2018, 21:37 - Pedro: Buenas Francisco Aguilera, estoy en un restaurante\n Espero que no te importe
        //System.out.println("ANALYSING-> " + line);

        Scanner sc = new Scanner(line).useDelimiter("\\s*/\\s*|\\s*,\\s*|\\s*:\\s*|\\s*-\\s");

        //date
        int day = sc.nextInt();
        int month = sc.nextInt();
        int year = sc.nextInt();

        //time
        int hour = sc.nextInt();
        int minute = sc.nextInt();

        date = new DateTime(year, month, day, hour, minute);

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

    public DateTime getDate(){
        return date;
    }


    public int getDayOfWeek() {
        return date.getDayOfWeek()-1;
    }


    public boolean isFile() {
        return isFile;
    }

    @Override
    public String toString() {
        return printDate() + "Message= {'" + msg + '}';
    }

    private String printDate(){
        return date.toString();
    }

    public String getUser() {
        return user;
    }

    public String getMsg() {
        return msg;
    }

    public int getHour() {
        return date.getHourOfDay();
    }

    public int getMinute() {
        return date.getMinuteOfHour();
    }

    public int getDay() {
        return date.getDayOfMonth();
    }

    public int getMonth() {
        return date.getMonthOfYear();
    }

    public int getYear() {
        return date.getYear();
    }

    public int getWords() {
        return words;
    }
}
