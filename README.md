# Rock-Paper-Scissors: Network Game Assignment
#### Everett Yee and Dawson Jung

This repo contains programs to implement a Rock-Paper-Scissors game.

* MtClient.java handles user input, users can enter r, p, s, or q.
* ClientListener.java receives responses from the server and transfers them to clients.
* MtServer.java listens for client connections and creates a ClientHandler for each new client.
* ClientHandler.java receives messages from a client and relays it to the other clients.

Contributions:

* Dawson: data transfer method, round counter, input validation  
* Everett: game outcomes, quit function, small "friendliness" improvements
