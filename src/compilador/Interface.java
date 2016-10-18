package compilador;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class Interface extends JFrame implements ActionListener{
	JButton compilar;
	JTextArea codigo;
	JTextArea consola;
	Parser p;
	Interface(){
		
		createInterface();
		createListeners();
	}
	public void createInterface(){
		this.setLayout(new GridLayout(1,3));
		this.setSize(600, 500);
		this.setLocationRelativeTo(null);
		add(codigo=new JTextArea());
		add(compilar=new JButton("Compilar"));
		compilar.setPreferredSize(new Dimension(100,500));
		add(consola=new JTextArea());
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public void createListeners(){
		compilar.addActionListener(this);
	}
	public static void main(String []sss){
		new Interface();
	}
	 public void actionPerformed(ActionEvent e) {
		 p=new Parser(codigo.getText());
		 p.program();
		 String cat="";
		for(int x=0;x<p.scanner.errors.size();x++)
			cat=cat+(p.scanner.errors.get(x))+"\n";
		for(int x=0;x<p.errors.size();x++)
			cat=cat+(p.errors.get(x)+"\n");
		for(int x=0;x<p.Mensajes.size();x++)
			cat=cat+(p.Mensajes.get(x)+"\n");
		consola.setText(cat);
	 }

}
