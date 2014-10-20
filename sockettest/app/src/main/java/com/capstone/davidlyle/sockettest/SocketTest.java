package com.capstone.davidlyle.sockettest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_10;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.HashMap;


public class SocketTest  {


    protected void setUpSocket()
    {
        HashMap<String, String > protocol = new HashMap<String, String>(){{
            put("Sec-WebSocket-Protocol","echo-protocol");
        }};
        try {
            WebSocketClient client = new EmptyClient(new URI("http://130.64.221.161:8080"), new Draft_10(), protocol, 10000);
            client.connect();
            int i=1;
            while(!client.isOpen()){
                i *= i;
                i ++;
            }
            client.send("Hi Momo");
        } catch (Exception e) {
            System.out.println("Exception Caught");
            e.printStackTrace();
        }
    }



    public class EmptyClient extends WebSocketClient {

        public EmptyClient(URI serverUri, Draft draft, HashMap<String, String > protocol, int timeout) {
            super(serverUri, draft, protocol, timeout);
        }
        public EmptyClient(URI serverUri, Draft draft) {
            super(serverUri, draft);
        }
        public EmptyClient(URI serverURI) {
            super(serverURI);
        }
//        public boolean isOpen = false;

        @Override
        public void onOpen(ServerHandshake handshakedata) {
            System.out.println("new connection opened");
//            isOpen = true;
        }

/*        public boolean safeSend(String message) {
            if (isOpen){
                this.send(message);
                return true;
            }
            return false;
        }*/

        @Override
        public void onClose(int code, String reason, boolean remote) {
            System.out.println("closed with exit code " + code + " additional info: " + reason);
 //           isOpen = false;
        }

        @Override
        public void onMessage(String message) {
            System.out.println("received message: " + message);
        }

        @Override
        public void onError(Exception ex) {
            System.err.println("an error occured:" + ex);
        }
    }


}


