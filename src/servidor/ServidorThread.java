package servidor;

import java.net.Socket;
import java.util.HashMap;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class ServidorThread extends Thread{

    protected HashMap<String,Utilizador> tabel;
    private Socket client;

    public ServidorThread(HashMap<String,Utilizador> newtabel,Socket newclient){
      this.tabel=newtabel;
      this.client=newclient;
    }

    public void run(){
      try{
        InputStream in = client.getInputStream();

        byte[] pdu = new byte[50];

        in.read(pdu);
        
        String nome = "";
        String ip = "";
        int i;

        for(i=8; (char)pdu[i] != '\0'; i++){
            nome += (char)pdu[i];
        }

        i++;

        for(; (char)pdu[i] != '\0'; i++){
            ip += (char)pdu[i];
        }        
        i++;
        
        byte[] portaArray = Arrays.copyOfRange(pdu, i, i+4);        
        final ByteBuffer bb = ByteBuffer.wrap(portaArray);
        
        int porta = bb.getInt();

        System.out.println("User: " + nome + ", ip: " + ip + ", porta: " + porta);

        OutputStream out = client.getOutputStream();
        out.write(pdu);
      }catch(Exception e){}//POR AQUI ALGUMA COISA
    }
}