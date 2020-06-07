import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientHandler extends Thread {
    DateFormat fordate=new SimpleDateFormat("yyyy/MM/dd");
    DateFormat fortime=new SimpleDateFormat("hh:mm:ss");
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;

    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos){
        this.s=s;
        this.dis=dis;
        this.dos=dos;
    }
    @Override
    public void run(){

        String received;
        String toreturn;
        while(true){
            try{
                dos.writeUTF("What do you want?[Date|Time]..\n"+"Type Exit to terminate connection.");
                received=dis.readUTF();
                if(received.equals("Exit")){
                    System.out.println("Client"+this.s+"sends exit...");
                    System.out.println("Closing this connection");
                    this.s.close();
                    System.out.println("Connection closed");
                    break;
                }

                Date date=new Date();
                switch(received){
                    case "Date":
                        toreturn=fordate.format(date);
                        dos.writeUTF(toreturn);
                        break;
                    case "Time":
                        toreturn=fortime.format(date);
                        dos.writeUTF(toreturn);
                        break;
                    default:
                        dos.writeUTF("Invalid input");
                        break;
                }
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        try{
            this.dis.close();
            this.dos.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public DateFormat getFordate() {
        return fordate;
    }

    public void setFordate(DateFormat fordate) {
        this.fordate = fordate;
    }

    public DateFormat getFortime() {
        return fortime;
    }

    public void setFortime(DateFormat fortime) {
        this.fortime = fortime;
    }

    public DataInputStream getDis() {
        return dis;
    }

    public DataOutputStream getDos() {
        return dos;
    }

    public Socket getS() {
        return s;
    }
}