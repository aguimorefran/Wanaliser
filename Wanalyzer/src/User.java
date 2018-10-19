public class User {
    private String name;
    private Message[] msgList;
    private int nMsg = 0;
    private int nFile = 0;
    private int avgMsgPerHour[];

    public User(String name) {
        this.name = name;
        msgList = new Message[50];
        avgMsgPerHour = new int[23];
        for (int i = 0; i < avgMsgPerHour.length; i++)
            avgMsgPerHour[i] = 0;
    }

    public void addMsg(Message msg) {
        //check if array is full. if so, make it bigger
        if (msgList.length == nMsg)
            msgList = java.util.Arrays.copyOf(msgList, msgList.length + (msgList.length / 2));

        msgList[nMsg] = msg;
        nMsg++;
    }

    public void addFile() {
        nFile++;
    }

    public void printAllMessages() {
        for (int i = 0; i < nMsg; i++)
            System.out.println(msgList[i].toString());
    }
}
