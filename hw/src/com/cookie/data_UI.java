package com.cookie;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import javax.management.monitor.Monitor;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class data_UI extends JFrame implements ActionListener {

	private JTextField title = new JTextField();
	private JTextArea out = new JTextArea();
	private JButton okbutton = new JButton("확인");
	private JButton chatbutton = new JButton("채팅하기");
	private JLabel re1Button = new JLabel();
	private JLabel re2Button = new JLabel();
	private JLabel re3Button = new JLabel();
	private JLabel re4Button = new JLabel();
	private JLabel re5Button = new JLabel();
	private JLabel re6Button = new JLabel();
	private JLabel barr[] = new JLabel[6];
	private JLabel ser = new JLabel(" 검색어 :");
	private JLabel exl = new JLabel(" 예문 :");
	private JLabel rel = new JLabel(" 관련 :");
	private JList<Data> list = null;
	private JScrollPane sc;
	private DefaultListModel m;

	private JList<Data> exlist = null;
	private JScrollPane exsc;
	private DefaultListModel exm;
	private ArrayList<Data> lists;	

	public int res = 0;
	public boolean tfc = true;

	public data_UI() {
		setTitle("U-Network");
		setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(100, 100, 440, 500);

		list = new JList(new DefaultListModel());
		sc = new JScrollPane(list);
		m = (DefaultListModel) list.getModel();

		exlist = new JList(new DefaultListModel());
		exsc = new JScrollPane(exlist);
		exm = (DefaultListModel) exlist.getModel();
		
		//okbutton.setBackground(Color.BLACK);
		ser.setBounds(10, 20, 50, 30);
		exl.setBounds(10, 184, 50, 30);
		rel.setBounds(10, 350, 50, 30);
		title.setBounds(60, 20, 150, 30);
		sc.setBounds(60, 80, 350, 100);
		exsc.setBounds(60, 190, 350, 150);
		okbutton.setBounds(230, 20, 80, 30);
		chatbutton.setBounds(320, 20, 90, 30);
		re1Button.setBounds(60, 350, 110, 30);
		re2Button.setBounds(180, 350, 110, 30);
		re3Button.setBounds(300, 350, 110, 30);
		re4Button.setBounds(60, 390, 110, 30);
		re5Button.setBounds(180, 390, 110, 30);
		re6Button.setBounds(300, 390, 110, 30);

		re1Button.setEnabled(false);
		re2Button.setEnabled(false);
		re3Button.setEnabled(false);
		re4Button.setEnabled(false);
		re5Button.setEnabled(false);
		re6Button.setEnabled(false);

		re1Button.setVisible(false);
		re2Button.setVisible(false);
		re3Button.setVisible(false);
		re4Button.setVisible(false);
		re5Button.setVisible(false);
		re6Button.setVisible(false);
		barr[0] = re1Button;
		barr[1] = re2Button;
		barr[2] = re3Button;
		barr[3] = re4Button;
		barr[4] = re5Button;
		barr[5] = re6Button;

		// moButton.setBounds(320, 20,100,30);

		okbutton.addActionListener(this);
		chatbutton.addActionListener(this);
		// moButton.addActionListener(this);
		add(ser);
		add(exl);
		add(rel);
		add(title);
		add(sc);
		add(exsc);
		add(okbutton);
		add(chatbutton);
		add(re1Button);
		add(re2Button);
		add(re3Button);
		add(re4Button);
		add(re5Button);
		add(re6Button);

		// add(moButton);

		setVisible(true);
		setResizable(false);

	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String in = e.getActionCommand();
		Parser test = new Parser();
		String input = title.getText();
		if (in == "확인") {
			
			list.setVisible(true);
			m.removeAllElements();

			exlist.setVisible(true);
			exm.removeAllElements();

			lists = test.goodsSearch(input);
			//test.goodsSearch(input);
			test.relativeword(input);
			test.exsentance(input);
			for (int i = 0; i < lists.size(); i++) {
				m.addElement(lists.get(i).getKeyword());
			}

			for (int i = 0; i < test.exsentance(input).size(); i++) {
				exm.addElement(test.exsentance(input).get(i).getKeyword());
			}

			if (test.getrelsize() < 6) {
				res = test.getrelsize();
				System.out.println(res);
				for (int i = 0; i < res; i++) {
					barr[i].setText(test.relativeword(input).get(i).getKeyword());
					barr[i].setVisible(true);
					barr[i].setEnabled(true);

				}

			} else {
				res = test.getrelsize();
				System.out.println("res");
				for (int i = 0; i < test.getrelsize(); i++) {
					barr[i].setText(test.relativeword(input).get(i).getKeyword());
					barr[i].setVisible(true);
					barr[i].setEnabled(true);

				}

			}

		} else if (in == "채팅하기") {
			
			Socket socket = null;
			ClientFrame cf;

			try {
				socket = new Socket("210.115.228.190", 3000);
				System.out.println("연결성공!");
				cf = new ClientFrame(socket);
				tfc = cf.getClose();
				new ReadThread(socket, cf).start();
			} catch (IOException ie) {
				System.out.println(ie.getMessage());
			}
			if(!tfc){
				chatbutton.setEnabled(false);
			}
		}

	}
	public ArrayList<Data> getdatas(){
		return lists;
	}

}
