import java.io.*;

public class Conversation {
    private String filename;
    private User[] userList;
    private Message[] msgList;
    private int nUsers = 0;
    private int nMsg, nFiles;

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

                //calc response time


            }
        }
        //print conversation msg list
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n-------\n");
        sb.append("USERS= ");
        for (int i = 0; i < nUsers; i++)
            sb.append(userList[i].getName() + ", ");
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
        for (int i = 0; i < nUsers; i++) {
            sb.append("N files by " + userList[i].getName() + "= ");
            sb.append(userList[i].getnFile());
            sb.append("\n");
        }
        for (int i = 0; i < nUsers; i++) {
            sb.append("Total msgs per hour by " + userList[i].getName() + ":\n");
            for (int j = 0; j <= 23; j++)
                sb.append("\t" + j + "= " + userList[i].getMsgPerHour()[j] + "\n");
            sb.append("\n");
        }
        for (int i = 0; i < nUsers; i++) {
            userList[i].calcAvg();
            sb.append("Total msgs per day of week by " + userList[i].getName() + ":\n");
            for (int j = 0; j < 7; j++)
                sb.append("\t" + (j+1) + "= " + userList[i].getAvgMsgPerDay()[j] + "\n");
            sb.append("\n");
        }
        for (int i = 0; i < nUsers; i++) {
            userList[i].calcAvg();
            sb.append("Avg msgs per hour of day by " + userList[i].getName() + ":\n");
            for (int j = 0; j < 24; j++)
                sb.append("\t" + j + "= " + userList[i].getAvgMsgPerHour()[j] + "\n");
            sb.append("\n");
        }
        for (int i = 0; i < nUsers; i++) {
            userList[i].calcAvg();
            sb.append("Avg words per msg by " + userList[i].getName() + "= ");
            sb.append(userList[i].getAvgWordsPerMsg());
            sb.append("\n");
        }



        return sb.toString();
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

}
