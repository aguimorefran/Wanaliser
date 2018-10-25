import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class User {
    private String name;
    private Message[] msgList;
    private int nMsg = 0;
    private int nFile = 0;
    private int msgPerHour[];
    private int filePerHour[];
    private int msgPerDay[];
    private int responseTime[];
    private long avgMsgPerDay[];
    private long avgMsgPerHour[];
    private int avgWordsPerMsg;
    private HashMap<String, Integer> wordList;

    public User(String name) {
        this.name = name;
        msgList = new Message[50];
        msgPerHour = new int[24];
        filePerHour = new int[24];
        responseTime = new int[5];
        msgPerDay = new int[7];
        avgMsgPerDay = new long[7];
        avgMsgPerHour = new long[24];

        for (int i = 0; i < msgPerHour.length; i++) {
            msgPerHour[i] = 0;
            avgMsgPerHour[i] = 0;
        }
        for (int i = 0; i < filePerHour.length; i++)
            filePerHour[i] = 0;
        for (int i = 0; i < responseTime.length; i++)
            responseTime[i] = 0;
        for (int i = 0; i < msgPerDay.length; i++) {
            msgPerDay[i] = 0;
            avgMsgPerDay[i] = 0;
        }
    }

    public void calcAvg() {
        //Avg msgs per day of week
        //get first and last day, get n of days, get n of weeks (days/7) and do simple math
        Message m = msgList[0];
        Date first = new Date(m.getYear(), m.getMonth(), m.getDay());
        m = msgList[nMsg - 1];
        Date last = new Date(m.getYear(), m.getMonth(), m.getDay());
        long days = TimeUnit.DAYS.convert(last.getTime() - first.getTime(), TimeUnit.MILLISECONDS);
        for (int i = 0; i < 7; i++) {
            if (days > 0 && msgPerDay[i] > 0)
                avgMsgPerDay[i] = msgPerDay[i] / (days / 7);
        }

        //Avg msgs per active hour of the day
        for (int i = 0; i < 24; i++)
            avgMsgPerHour[i] = msgPerHour[i] / days;

        //avg words per message
        int sum = 0;
        for (int i = 0; i < nMsg; i++)
            sum += msgList[i].getWords();
        avgWordsPerMsg = sum / nMsg;
    }

    public void addMsg(Message msg) {
        //check if array is full. if so, make it bigger
        if (msgList.length == nMsg)
            msgList = java.util.Arrays.copyOf(msgList, msgList.length + (msgList.length / 2));
        msgList[nMsg] = msg;
        nMsg++;
        for (int i = 0; i < 24; i++) {
            if (i == msg.getHour()) {
                if (msg.isFile())
                    filePerHour[i]++;
                else
                    msgPerHour[i]++;
            }
        }
        //add msg to day of the week
        for (int i = 0; i < 7; i++)
            if (i == msg.getDayOfWeek())
                msgPerDay[i]++;
    }

    public void printAllMessages() {
        for (int i = 0; i < nMsg; i++)
            System.out.println(msgList[i].toString());
    }

    boolean checkWord(String word) throws IOException {
        boolean ok = false;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("src/alphabet.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String line;
        //reads line by line the txt file

        while ((line = br.readLine()) != null && !ok) {
            if (word.equalsIgnoreCase(line))
                ok = true;
        }

        return ok;
    }

    private static HashMap sortByValues(HashMap map) {
        List list = new LinkedList(map.entrySet());
        // Defined Custom Comparator here
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o1)).getValue())
                        .compareTo(((Map.Entry) (o2)).getValue());
            }
        });

        HashMap sortedHashMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext(); ) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }
        return sortedHashMap;
    }

    public int getnWords() {
        return wordList.size();
    }

    public long[] getAvgMsgPerHour() {
        return avgMsgPerHour;
    }

    public String getName() {
        return name;
    }

    public Message[] getMsgList() {
        return msgList;
    }

    public int getnMsg() {
        return nMsg;
    }

    public int getnFile() {
        return nFile;
    }

    public int[] getMsgPerHour() {
        return msgPerHour;
    }

    public int[] getFilePerHour() {
        return filePerHour;
    }

    public int getAvgWordsPerMsg() {
        return avgWordsPerMsg;
    }

    public long[] getAvgMsgPerDay() {
        return avgMsgPerDay;
    }

    public int[] getMsgPerDay() {
        return msgPerDay;
    }

    public int[] getResponseTime() {
        return responseTime;
    }

    public void addFile() {
        nFile++;
    }
}
