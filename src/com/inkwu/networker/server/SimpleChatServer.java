package com.inkwu.networker.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class SimpleChatServer {
	
	ArrayList<PrintWriter> clientOutputStreams;
	
	public class ClientHandler implements Runnable {
		BufferedReader reader;
		Socket sock;
		
		public ClientHandler(Socket clientSocket) {
			try {
				sock = clientSocket;
				InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
				reader = new BufferedReader(isReader);
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
		public void run() {
			String message;
			try {
				while ((message = reader.readLine()) != null) {
					System.out.println("读取 - " + message);
					tellEveryone(message);
				}
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new SimpleChatServer().go();
	}

	public void go() {
		clientOutputStreams = new ArrayList<>();
		try {
			ServerSocket serverSock = new ServerSocket(4242);
			
			while (true) {
				Socket clientSocket = serverSock.accept();
				PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
				clientOutputStreams.add(writer);
				
				Thread t = new Thread(new ClientHandler(clientSocket));
				t.start();
				System.out.println("得到一个连接");
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void tellEveryone(String message) {
		for (PrintWriter writer : clientOutputStreams) {
			try {
				writer.println(message);
				writer.flush();
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
