package compilador;

import java.util.ArrayList;

public class Nodo {
	Token token;
	Nodo current=this;
	int nivel=0;
	public Nodo(Token token) {
		super();
		this.token = token;
	}
	ArrayList <Nodo>hojas =new ArrayList<Nodo>(); 
	public void addNodo(Nodo n){
		hojas.add(n);
	}
	public  Token getToken(){
		return token;
	}

}

