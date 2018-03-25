/**
 * MTClient.java
 *
 * This program implements a simple multithreaded chat client.  It connects to the
 * server (assumed to be localhost on port 7654) and starts two threads:
 * one for listening for data sent from the server, and another that waits
 * for the user to type something in that will be sent to the server.
 * Anything sent to the server is broadcast to all clients.
 *
 * The MTClient uses a ClientListener whose code is in a separate file.
 * The ClientListener runs in a separate thread, recieves messages form the server,
 * and displays them on the screen.
 *
 * Data received is sent to the output screen, so it is possible that as
 * a user is typing in information a message from the server will be
 * inserted.
 *
 */

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.io.InputStreamReader;

import java.net.Socket;

import java.util.Scanner;

public class MtClient {
  /**
   * main method.
   * @params not used.
   */
  public static void main(String[] args) {
    try {
      String hostname = "localhost";
      int port = 7654;

      System.out.println("Connecting to server on port " + port);
      Socket connectionSock = new Socket(hostname, port);

      DataOutputStream serverOutput = new DataOutputStream(connectionSock.getOutputStream());

      System.out.println("Connection made.");

      // Start a thread to listen and display data sent by the server
      ClientListener listener = new ClientListener(connectionSock);
      Thread theThread = new Thread(listener);
      theThread.start();

      // Read input from the keyboard and send it to everyone else.
      // The only way to quit is to hit control-c, but a quit command
      // could easily be added.

      System.out.println("Hello, welcome to Rock-Paper-Scissors!");
      System.out.println();
      System.out.println("How to play:");
      System.out.println("Press 'r' to play Rock");
      System.out.println("Press 'p' to play Paper");
      System.out.println("Press 's' to play Scissors");
      System.out.println();
      System.out.println("Have fun!");
      System.out.println();

      boolean controller = true;
      int round = 1;

      while (controller == true) {
        System.out.println("Round " + round);
        Scanner keyboard = new Scanner(System.in);
        String data = keyboard.nextLine();
        if (data.equalsIgnoreCase("q")) {
          System.out.println("Exiting Program. Goodbye!");
          controller = false;
          serverOutput.writeBytes(data + "\n");
          break;
        }

        boolean validInput = false;
        while (validInput == false) {
          if (data.equalsIgnoreCase("r") || data.equalsIgnoreCase("p") || data.equalsIgnoreCase("s")) {
            validInput = true;
          }
          else {
            System.out.println("Invalid input, try again:");
            data = keyboard.nextLine();
          }
        }

        serverOutput.writeBytes(data + "\n");

        System.out.println("Please wait for the other player to finish their turn :)");

        String returnedData = listener.dataTransfer();
        //System.out.println("The result: " + returnedData);

        if (returnedData.equalsIgnoreCase("q")) {
          System.out.println();
          System.out.println();
          System.out.println("The other user has disconnected. Thanks for playing!");
          System.out.println("Goodbye!");
          break;
        }

        //Rock and Paper
        if (data.equalsIgnoreCase("r") && returnedData.equalsIgnoreCase("p")) {
          System.out.println("Paper wins!");
        }
        else if (returnedData.equalsIgnoreCase("r") && data.equalsIgnoreCase("p")) {
          System.out.println("Paper wins!");
        }
        //Paper and Scissors
        else if (data.equalsIgnoreCase("p") && returnedData.equalsIgnoreCase("s")) {
          System.out.println("Scissors wins!");
        }
        else if (returnedData.equalsIgnoreCase("p") && data.equalsIgnoreCase("s")) {
          System.out.println("Scissors wins!");
        }
        //Rock and Scissors
        else if (data.equalsIgnoreCase("r") && returnedData.equalsIgnoreCase("s")) {
          System.out.println("Rock wins!");
        }
        else if (returnedData.equalsIgnoreCase("r") && data.equalsIgnoreCase("s")) {
          System.out.println("Rock wins!");
        }
        //Tie
        else if (data.equalsIgnoreCase(returnedData)) {
          System.out.println("It's a tie!");
        }
        round++;
        System.out.println();
      }
      connectionSock.close();
    } catch (SocketException ex) {
      System.out.println("Socket closed, goodbye!");
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }
}
