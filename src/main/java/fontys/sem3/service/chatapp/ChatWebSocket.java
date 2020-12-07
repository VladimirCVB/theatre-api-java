package fontys.sem3.service.chatapp;

import org.glassfish.grizzly.websockets.DataFrame;
import org.glassfish.grizzly.websockets.WebSocket;
import org.glassfish.grizzly.websockets.WebSocketApplication;

import java.util.LinkedList;
import java.util.Queue;

public class ChatWebSocket extends WebSocketApplication {

    private Queue<WebSocket> queuedSockets = new LinkedList<>();

    @Override
    public void onConnect(WebSocket socket) {

        if(super.getWebSockets().size() >= 2){
            queuedSockets.add(socket);
            return;
        }

        if(queuedSockets.size() < 1){
            super.onConnect(socket);
            super.add(socket);
        }
    }

    @Override
    public void onMessage(WebSocket socket, String text) {

        if(super.getWebSockets().contains(socket)){
            for(WebSocket webSocket : super.getWebSockets()){
                if(webSocket != socket)
                    webSocket.send(text);
            }
        }
    }

    private void addFromQueue(){
        if(queuedSockets.size() > 0){
            WebSocket queuedSocket = queuedSockets.peek();
            super.onConnect(queuedSocket);
            super.add(queuedSocket);

            return;
        }
    }

    /*@Override
    public void onMessage(WebSocket socket, byte[] bytes) {
        socket.send(bytes);
    }*/

    @Override
    public void onClose(WebSocket socket, DataFrame frame) {
        for(WebSocket webSocket : super.getWebSockets()){
            if(webSocket == socket){
                super.remove(webSocket);
                webSocket.close();
            }
        }

        addFromQueue();
    }
}
