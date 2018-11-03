import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;

import java.io.*;

public class Conversation {
    private String filename;
    private User[] userList;
    private Message[] msgList;
    private int nUsers = 0;
    private int nMsg, nFiles;
    private String mostActiveDay;
    private int nDays;

    public Conversation(String filename) {
        this.filename = filename;
        userList = new User[2];
        msgList = new Message[100];
    }

    public void analise() throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("src/" + filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String line;
        Message msg, anterior;
        //reads line by line the txt file

        while ((line = br.readLine()) != null) {
            msg = new Message(line);
            //discards null msgs and rubbish msgs
            if (line.length() < 1 || line.charAt(2) != '/' || line.charAt(0) == '[')
                ;
            else {
                //analise the message
                msg.analise();
                //add user to conver
                if (!userExists(msg.getUser())) {
                    addUser(msg.getUser());
                }
                //add analised msg to conversation
                addMsg(msg);
                //add analised msg to user
                addMsgToUser(msg.getUser(), msg);
                //sum files
                if (msg.isFile()) {
                    addFileToUser(msg.getUser());
                    nFiles++;
                }
            }
        }

        //calc most active day
        calcMostActiveDay();

        //calc nDays
        nDays = Days.daysBetween(msgList[0].getDate(), msgList[nMsg - 1].getDate()).getDays();

        //print conversation msg list
        System.out.println(this.toString());

        //idf
        //first organize wordlist in everyuser
        for (User u : userList)
            u.createWordList();

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n-------\n");
        sb.append("USERS= ");
        for (int i = 0; i < nUsers; i++)
            sb.append(userList[i].getName() + ", ");
        sb.append("\n\n");
        sb.append("Conversation taken beetween \n" + msgList[0].getDate().toString(DateTimeFormat.fullDate()) +
                "\n" + msgList[nMsg - 1].getDate().toString(DateTimeFormat.fullDate()));
        sb.append("\n");
        sb.append("N total msgs= " + nMsg);
        sb.append("\n\n");
        for (int i = 0; i < nUsers; i++) {
            sb.append("N messages by " + userList[i].getName() + "= ");
            sb.append(userList[i].getnMsg());
            sb.append("\n");
        }
        sb.append("N total files= ");
        sb.append(nFiles);
        sb.append("\n");

        for (User u : userList) {
            sb.append("N files by " + u.getName() + "= ");
            sb.append(u.getnFile());
            sb.append("\n");
        }
        for (int i = 0; i < nUsers; i++) {
            sb.append("Total msgs per hour by " + userList[i].getName() + ":\n");
            for (int j = 0; j <= 23; j++)
                sb.append("\t" + j + "h= " + userList[i].getMsgPerHour()[j] + "\n");
            sb.append("\n");
        }
        for (int i = 0; i < nUsers; i++) {
            userList[i].calcAvg();
            sb.append("Total msgs per day of week by " + userList[i].getName() + ":\n");
            for (int j = 0; j < 7; j++)
                sb.append("\t" + getDoW(j) + "= " + userList[i].getAvgMsgPerDay()[j] + "\n");
            sb.append("\n");
        }
        for (int i = 0; i < nUsers; i++) {
            userList[i].calcAvg();
            sb.append("Avg msgs per hour of day by " + userList[i].getName() + ":\n");
            for (int j = 0; j < 24; j++)
                sb.append("\t" + j + "h= " + userList[i].getAvgMsgPerHour()[j] + "\n");
            sb.append("\n");
        }
        for (int i = 0; i < nUsers; i++) {
            userList[i].calcAvg();
            sb.append("Avg words per msg by " + userList[i].getName() + "= ");
            sb.append(userList[i].getAvgWordsPerMsg());
            sb.append("\n");
        }
        sb.append("\nMost active day: " + mostActiveDay + "\n");


        return sb.toString();
    }

    public void toFile() {
        try {
            PrintWriter out = new PrintWriter("output.txt");
            out.print(toString());
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void calcMostActiveDay() {
        int max = 0;
        int count = 0;
        String date = null;
        for (int i = 0; i < nMsg; i++) {
            if (i > 0) {
                if (msgList[i - 1].getDay() == msgList[i].getDay())
                    count++;
                else {
                    //when day changes
                    if (count > max) {
                        max = count;
                        count = 0;
                        date = msgList[i - 1].getDate().toString(DateTimeFormat.fullDate());
                    }
                }
            }
        }
        mostActiveDay = date;
    }

    private void addUser(String username) {
        if (nUsers == userList.length) {
            userList = java.util.Arrays.copyOf(userList, userList.length + (userList.length / 2));
        }
        userList[nUsers] = new User(username);
        nUsers++;
    }

    private boolean userExists(String username) {
        boolean exists = false;
        for (int i = 0; i < nUsers; i++) {
            if (username.equals(userList[i].getName())) {
                exists = true;
                break;
            }
        }
        return exists;
    }

    private void addMsg(Message msg) {
        //check if array is full. if so, make it bigger
        if (msgList.length == nMsg)
            msgList = java.util.Arrays.copyOf(msgList, msgList.length + (msgList.length / 2));
        msgList[nMsg] = msg;
        nMsg++;
    }

    private void addMsgToUser(String username, Message m) {
        for (int i = 0; i < nUsers; i++)
            if (username.equals(userList[i].getName()))
                userList[i].addMsg(m);
    }

    private void addFileToUser(String username) {
        for (int i = 0; i < nUsers; i++)
            if (username.equals(userList[i].getName()))
                userList[i].addFile();
    }

    private String getDoW(int n) {
        String date = null;
        if (n == 0)
            date = "MONDAY";
        if (n == 1)
            date = "TUESDAY";
        if (n == 2)
            date = "WEDNESDAY";
        if (n == 3)
            date = "THURSDAY";
        if (n == 4)
            date = "FRIDAY";
        if (n == 5)
            date = "SATURDAY";
        if (n == 6)
            date = "SUNDAY";
        return date;
    }
}