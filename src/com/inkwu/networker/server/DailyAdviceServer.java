package com.inkwu.networker.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class DailyAdviceServer {
	
	String[] adviceList = {
			"Take smaller bites",
			"Go for the tight jeans. No they do NOT make you look fat.",
			"One word: inappropriate",
			"Just for today, be honest. Tell your boss what you *really* think",
			"Just do it"
	};
	
	public void go() {
		ServerSocket serverSock = null;
		try {
			serverSock = new ServerSocket(4242);
			
			while (true) {
				Socket sock = serverSock.accept();
				
				if (sock.getInputStream() != null) {
					InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());
					BufferedReader reader = new BufferedReader(streamReader);
					String message = reader.readLine();
					System.out.println("message from client : " + message);
				}
				
				
				PrintWriter writer = new PrintWriter(sock.getOutputStream());
				String advice = getAdvice();
				writer.println(advice);
				writer.close();
				System.out.println(advice);
			}
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
		finally {
			if (serverSock != null) {
				try {
					serverSock.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private String getAdvice() {
		int random = (int)(Math.random() * adviceList.length);
		return adviceList[random];
	}

	public static void main(String[] args) {
		DailyAdviceServer server = new DailyAdviceServer();
		server.go();
	}

}
