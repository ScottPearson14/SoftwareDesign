# 28-22_NetworkedMorseCode_Medium

[Home](Home)

## Problem Statement

For this problem, a server and client applications must be written in Java so that 2 clients are able to send Morse-code messages to each other through a multithreaded server GUI application. The client application should allow users to text English text into a JTextArea. When the user sends a message, the client application encodes the text into Morse code and sends the coded message through the server to the other client.

## Developer Documentation

The program is broken down into 5 different classes. Here is an overview of each class's purpose, attributes, and methods:

1. **MorseCodeClient:** This class represents a client that will be used to communicate Morse Code with another client and extends JFrame.

Attributes of this class consist of:

- enterField: JTextField where users enter information
- displayArea: JTextArea where information is displayed to the user
- output: ObjectOutputStream that acts as an output stream to the server
- input: ObjectInputStream that acts as an input stream from the server
- message: String that is a message from the server
- chatServer: String that is a hose server for this Morse Code application
- client: Socket that allows a connection to the server

Methods of this class consist of:

- MorseCodeClient: constructor that initializes chatServer and sets up the GUI
- runClient: Method that connects the MorseCodeClient to the server and processes messages from the server
- connectToServer: Method that connects the MoreCodeClient to the server
- getStreams: Method that gets streams to send and receive data
- processConnection: Method to process the connection with the server
- closeConnection: Method that closes steams and sockets
- sendData: Method that allows client to send messages to the server
- displayMessage: Method that manipulates displayArea in the event-dispatch thread
- setTextFieldEditable: Method that manipulates the enterField in the event-dispatch thread

2. **MorseCodeServer:** This class represents a server that will create a connection with 2 clients to allow them to be able to communicate with each other via Morse Code and extends JFrame.

Attributes of this class consist of:

- displayArea: JTextArea where information is displayed to the user
- server: ServerSocket for server
- clients: Array to manage connections for 2 clients
- threadPool: ExecutorService thread pool for managing client handlers

Methods of this class consist of:

- MorseCodeServer: constructor that sets up the GUI
- runServer: Method that sets up and runs the server
- displayMessage: Method that manipulates displayArea in the event-dispatch thread
- setTextFieldEditable: Method that manipulates displayArea in the event-dispatch thread

This class also has an anonymous private inner class **Communicator** to manage each Communicator as runnable.

The attributes for this class consist of:

* connection: Socket that is the connection to the client
* communicatorNum: int that tracks which communicator is talking
* input: ObjectInputStream input from the client
* output: ObjectOutputStream output from the client

The methods for this class consist of:

* Communicator: Constructor to initialize client handler
* run: Overriden run method that processes messages from clients
* setupSteams: Method to set up I/O streams for the client
* processConnection: Method to process server connection with the client
* sendToClient: Method that sends messages to client
* sendToOtherClient: Method to allow a client to send a message to the other client
* closeConnection: Method that closes streams and sockets

3. **MorseCodeClientTest:** This class is used to run a MorseCodeClient application. It creates client applications and connects them to the host, so they can connect to the server.
4. **MorseCodeServerTest:** This class is used to run a MorseCodeServer application. It creates a server when run, and clients can connect to it so they can communicate via Morse code.
5. **MorseCodeTranslator:** This class translates plain text to Morse code and Morse code into plain text. This class has a HashMap for translations from plain text to Morse Code, a HashMap for translations from Morse code to plain text, and static Morse code mappings for the alphabet and numerical values. It has a method that converts plain text to Morse Code and another method that converts plain text to Morse Code.

## JavaDocs

[Click here to view JavaDocs](https://class-git.engineering.uiowa.edu/swd2024fall/scottpearson/-/tree/master/oral_exam2/28-22_NetworkedMorseCode_Medium/doc?ref_type=heads)

## UML Diagram

![uml_diagram](https://class-git.engineering.uiowa.edu/swd2024fall/scottpearson/-/raw/master/oral_exam2/28-22_NetworkedMorseCode_Medium/doc/NetworkedMorseCode_UML.png?ref_type=heads)

## User Documentation

1. Run the MorseCodeServerTest program so that the server is set up and waiting for client connections
2. Run the MorseCodeClientTest program twice so that 2 clients are connected to the server. Do not terminate one of the clients. If you terminate one of the clients, you must terminate the other as well as the server. Go back to step 1. if you wish to run the application again.
3. Enter English language prompts into one of the clients in the JTextArea. Select "enter/return" on your keyboard to send the message through the multithreaded server to the other client. The client application encodes the text into Morse code and sends the coded message through the server to the other client.
4. When messages are received, they will be displayed as normal characters and as Morse code in the server. The client that sent the message will have the original message showing in English. The client that receives the message will have the message translated to Morse code.

## Source Code

[Click here to here to view source code](https://class-git.engineering.uiowa.edu/swd2024fall/scottpearson/-/tree/master/oral_exam2/28-22_NetworkedMorseCode_Medium/src?ref_type=heads)
