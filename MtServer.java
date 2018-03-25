/**
 * MTServer.java
 *
 * By: Dawson Jung and Everett Yee
 * This program implements a multithreaded rock-paper-scissors server.
 *
 * Once a client connects, the server displays it in the server window. When
 * a client enters their input (r,p, or s) it is sent to the server, but
 * not to the other client. Once both clients input their choices,
 * the server then broadcasts to both clients who won the match.
 *
 * The MTServer uses a ClientHandler whose code is in a separate file.
 * When a client connects, the MTServer starts a ClientHandler in a separate thread
 * to receive messages from the client.
 *
 * To test, start the server first, then start multiple clients and type messages
 * in the client windows.
 *
 */

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.ServerSocket;
import java.net.Socket;

import java.util.ArrayList;

public class MtServer {
  // Maintain list of all client sockets for broadcast
  private ArrayList<Socket> socketList;

  public MtServer() {
    socketList = new ArrayList<Socket>();
  }

  private void getConnection() {
    // Wait for a connection from the client
    try {
      System.out.println("Rock-Paper-Scissors server starting...");
      System.out.println("Waiting for client connections on port 7654...");
      ServerSocket serverSock = new ServerSocket(7654);
      // This is an infinite loop, the user will have to shut it down
      // using control-c
      while (true) {
        Socket connectionSock = serverSock.accept();
        // Add this socket to the list
        socketList.add(connectionSock);
        // Send to ClientHandler the socket and arraylist of all sockets
        ClientHandler handler = new ClientHandler(connectionSock, this.socketList);
        Thread theThread = new Thread(handler);
        theThread.start();
      }
      //serverSock.close();
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  public static void main(String[] args) {
    MtServer server = new MtServer();
    server.getConnection();
  }
} // MtServer
