import java.util.Scanner;

public class Message {
    private String line, user, msg;
    private int hour, minute, day, month, year;

    public Message(String line) {
        this.line = line;
    }

    public void analise() {
        //EXAMPLE ->        24/07/2018, 21:37 - Pedro: Buenas Francisco Aguilera, estoy en un restaurante\n Espero que no te importe

        System.out.println("ANALYSING-> " + line);

        Scanner sc = new Scanner(line).useDelimiter("\\s*/\\s*|\\s*,\\s*|\\s*:\\s*|\\s*-\\s");
        //date
        day = sc.nextInt();
        month = sc.nextInt();
        year = sc.nextInt();

        //time
        hour = sc.nextInt();
        minute = sc.nextInt();
        //user
        user = sc.next();
        sc.close();

        //message
        msg = line.substring(line.lastIndexOf(":") + 1);
        msg = msg.replace("\n", ".");
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
