package com.inkwu.networker.client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SimpleChatClientA {
	
	JTextField outgoing;
	PrintWriter writer;
	Socket sock;
	
	public void go() {
		JFrame frame = new JFrame("Ink - 简单聊天客户端");
		JPanel mainPanel = new JPanel();
		outgoing = new JTextField(20);
		JButton sendButton = new JButton("发送");
		sendButton.addActionListener(new SendButtonListener());
		mainPanel.add(outgoing);
		mainPanel.add(sendButton);
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
//		setupNetworking();
		frame.setSize(400, 500);
		frame.setVisible(true);
	}
	
	private void setupNetworking() {
		try {
			sock = new Socket("127.0.0.1", 4242);
			writer = new PrintWriter(sock.getOutputStream());
			System.out.println("网络已建立");
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public void sendMessage() {
		try {
			setupNetworking();
			writer.println(outgoing.getText());
			writer.flush();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		outgoing.setText("");
		outgoing.requestFocus();
	}
	
	public class SendButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			sendMessage();
		}
	}

	public static void main(String[] args) {
		new SimpleChatClientA().go();
	}

}
