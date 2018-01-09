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
	JButton btn = new JButton("�Է�");	
	
	WriteThread wt;	
	ClientFrame cf;
	public Id(){}
	public Id(WriteThread wt, ClientFrame cf) {
		super("���̵�");		
		this.wt = wt;
		this.cf = cf;
		
		
		setLayout(new FlowLayout());
		add(new JLabel("���̵�"));
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
	JButton btnTransfer = new JButton("����");
	JButton btnExit = new JButton("�ݱ�");
	JButton btnsend = new JButton("�� ������");
	boolean isFirst=true;
	JPanel p1 = new JPanel();
	Socket socket;
	WriteThread wt;
	ArrayList<Data> list;
	boolean  tfc = true;
		
	public ClientFrame(Socket socket) {
		
		
		super("<ä��>");
		
		wt = new WriteThread(this);
		new Id(wt, this);
		
		add("Center", txtA);
		txtA.setEditable(false);
		p1.add(txtF);
		p1.add(btnTransfer);
		p1.add(btnsend);
		p1.add(btnExit);
		add("South", p1);
		
		//�޼����� �����ϴ� Ŭ���� ����.
		
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
		if(e.getSource()==btnTransfer || e.getSource() == txtF){//���۹�ư ������ ���
			//�޼��� �Է¾��� ���۹�ư�� ������ ���
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
