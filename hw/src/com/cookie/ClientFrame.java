package com.cookie;




import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

class Id extends JFrame implements ActionListener{
	
	
	static JTextField tf=new JTextField(8);
	JButton btn = new JButton("입력");	
	
	WriteThread wt;	
	ClientFrame cf;
	public Id(){}
	public Id(WriteThread wt, ClientFrame cf) {
		super("아이디");		
		this.wt = wt;
		this.cf = cf;
		
		
		setLayout(new FlowLayout());
		add(new JLabel("아이디"));
		add(tf);
		add(btn);
		
		btn.addActionListener(this);
		
		setBounds(300, 300, 250, 100);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {		
		wt.sendMsg();	
		cf.isFirst = false;
		cf.setVisible(true);
		this.dispose();
	}
	static public String getId(){
		return tf.getText();
	}
}




public class ClientFrame extends JFrame implements ActionListener{
	JTextArea txtA = new JTextArea();
	JTextField txtF = new JTextField(15);
	JButton btnTransfer = new JButton("전송");
	JButton btnExit = new JButton("닫기");
	JButton btnsend = new JButton("뜻 보내기");
	boolean isFirst=true;
	JPanel p1 = new JPanel();
	Socket socket;
	WriteThread wt;
	ArrayList<Data> list;
	boolean  tfc = true;
		
	public ClientFrame(Socket socket) {
		
		
		super("<채팅>");
		
		wt = new WriteThread(this);
		new Id(wt, this);
		
		add("Center", txtA);
		txtA.setEditable(false);
		p1.add(txtF);
		p1.add(btnTransfer);
		p1.add(btnsend);
		p1.add(btnExit);
		add("South", p1);
		
		//메세지를 전송하는 클래스 생성.
		
		btnTransfer.addActionListener(this);
		btnsend.addActionListener(this);
		btnExit.addActionListener(this);
		txtF.addActionListener(this);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(300, 300, 470, 300);
		setVisible(false);
		setResizable(false);
	}
	
	public void actionPerformed(ActionEvent e){
		list = new data_UI().getdatas();
		String id = Id.getId();
		if(e.getSource()==btnTransfer || e.getSource() == txtF){//전송버튼 눌렀을 경우
			//메세지 입력없이 전송버튼만 눌렀을 경우
			if(txtF.getText().equals("")){
				return;
			}			
			txtA.append("["+id+"] "+ txtF.getText()+"\n");
			wt.sendMsg();
			txtF.setText("");
		}
		else if(e.getSource() == btnsend){
			txtA.append("["+id+"] "+ list.get(0).getKeyword()+"  "+list.get(1).getKeyword()+"  "+list.get(2).getKeyword()+"\n");
			
		}else{
			tfc=true;
			this.dispose();
			  
		}
	}
	
	public boolean getClose() {
		return tfc;
	}
}
