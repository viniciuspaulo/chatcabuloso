/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;


import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
 
/**
 *
 * @author Krzysztof Jelonek
 */
@ManagedBean(name = "server")
public class Server {

    @PostConstruct
    public void init() {
        Configuration config = new Configuration();
        config.setHostname("172.20.21.73");
        config.setPort(3701);
        final SocketIOServer server = new SocketIOServer(config);
        server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient client) {
                System.out.println("onConnected");
                client.sendEvent("message", new Message("", "Welcome to the chat!"));
            }
        });
        server.addDisconnectListener(new DisconnectListener() {
            @Override
            public void onDisconnect(SocketIOClient client) {
                System.out.println("onDisconnected");
            }
        });
        server.addEventListener("send", Message.class, new DataListener<Message>() {
 
            @Override
            public void onData(SocketIOClient client, Message data, AckRequest ackSender) throws Exception {
                System.out.println("onSend: " + data.toString());
                server.getBroadcastOperations().sendEvent("message", data);
            }
        });
        System.out.println("Starting server...");
        server.start();
        System.out.println("Server started");
 
    }
    
    public void teste(){
        System.out.println("chamou");
    }
}