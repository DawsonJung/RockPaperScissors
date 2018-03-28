/**
 * ClientListener.java
 *
 * By: Dawson Jung and Everett Yee
 *
 * This class runs on the client end and transfers the input
 * from a client to the server, so that the server can then
 * determine a winner.
 *
 */

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.Socket;

import java.util.ArrayList;
import java.util.Scanner;


public class ClientListener implements Runnable {
  private Socket connectionSock = null;

  ClientListener(Socket sock) {
    this.connectionSock = sock;
  }

  /**
   * Repurposed to be used to initialize.
   */
  public void run() {}

  /**
   * Returns data from the server in this method
   * used to send the other user's action to the client.
   * @return String returned from server
   */
  public String dataTransfer() {
    String theData = "";
    try {
      BufferedReader serverInput = new BufferedReader(
          new InputStreamReader(connectionSock.getInputStream()));

      // Get data sent from the server
      String serverText = serverInput.readLine();
      if (serverInput != null) {
        theData = serverText;
      } else {
        // Connection was lost
        System.out.println("Closing connection for: " + connectionSock);
        connectionSock.close();
        //break;
      }

    } catch (Exception e) {
      System.out.println("Error: " + e.toString());
    }

    return theData;
  }
} // ClientListener for MtClient
