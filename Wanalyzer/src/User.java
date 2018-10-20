public class User {
    private String name;
    private Message[] msgList;
    private int nMsg = 0;
    private int nFile = 0;
    private int msgPerHour[];
    private int filePerHour[];
    private int msgPerDay[];
    private int responseTime[];

    public User(String name) {
        this.name = name;
        msgList = new Message[50];
        msgPerHour = new int[24];
        filePerHour = new int[24];
        responseTime = new int[5];
        msgPerDay = new int[7];
        for (int i = 0; i < msgPerHour.length; i++)
            msgPerHour[i] = 0;
        for (int i = 0; i < filePerHour.length; i++)
            filePerHour[i] = 0;
        for (int i = 0; i < responseTime.length; i++)
            responseTime[i] = 0;
        for (int i = 0; i < msgPerDay.length; i++)
            msgPerDay[i] = 0;

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
        //1. see which day of the week is by date dd/mm/yyyy
        //TODO: HOLA
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

    public void printAllMessages() {
        for (int i = 0; i < nMsg; i++)
            System.out.println(msgList[i].toString());
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
}
