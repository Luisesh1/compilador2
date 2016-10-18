package compilador;

import java.util.ArrayList;

public class Parser {
	public class Scanner {
		String code="";
		Boolean finish=false;
		ArrayList <String> errors = new ArrayList<String>();
		ArrayList <Token> reservados = new ArrayList<Token>();
		int pointer=0;
		
		Scanner(String code){
			this.code=code+" ";
			reservados.add(new Token(1,"if"));
			reservados.add(new Token(2,"while"));
			reservados.add(new Token(3,"Int"));
			reservados.add(new Token(3,"Boolean"));
			reservados.add(new Token(4,"="));
			reservados.add(new Token(5,"=="));
			reservados.add(new Token(5,"+"));
			reservados.add(new Token(6,";"));
			reservados.add(new Token(7,"("));
			reservados.add(new Token(8,")"));		
		}
		public Token getToken(){
			String cat="";
			Token aux = null;
			while (!finish)
			{	
				finish=(pointer==code.length()-1)
						||(code.substring(pointer,code.length()-1).trim().length()==0)?true:false;
				if((" ".charAt(0)==code.charAt(pointer)&&cat.length()==0)||"\n".charAt(0)==code.charAt(pointer)){
					pointer++;
					continue;
				}
				cat=cat+code.charAt(pointer);
				if (generateToken(cat)!=null){
					aux=generateToken(cat);
					pointer++;
				}
				else{
					if (generateToken(cat.trim())!=null){
						cat=cat.trim();
						pointer++;
						break;
					}
					if (generateToken(code.charAt(pointer)+"")==null){
						pointer++;
						errors.add("Error lexico: "+cat);
						return new Token(-1,cat);
					}
					break;
				}
			}
			
			return aux;
		}
		public Token generateToken(String v){
			for (int x =0 ; x<reservados.size();x++)
				if (reservados.get(x).value.compareTo(v)==0)
					return reservados.get(x);
			if (isIdentifier(v))
				return new Token(9,v);
			return null ;
		}
		public boolean isIdentifier(String v){
			if (!isLetter(v.charAt(0)))
				return false;
			for(int x = 1; x<v.length();x++)
			{
				if(!(isLetter(v.charAt(x))||isNumber(v.charAt(x))))
					return false;
			}
			return true;
		}	
		public boolean isLetter(char v){
			return ((v>=97&&v<=122)||(v>=65&&v<=90));
		}
		public boolean isNumber(char v){
			return (v>=48&&v<=57);
		}
	}
	ArrayList <String>errors= new ArrayList<String>();
	ArrayList <String>Mensajes= new ArrayList<String>();
	Nodo root=new Nodo(new Token(-1,"program"));
	Nodo current=root;
	Scanner scanner; 
	Boolean error=false;
	Token tok=new Token(-1,"inicializar");
	
	Parser(String code){
		scanner= new Scanner(code);
		advance();
	}
	
	public void compilar(){
		Token token;
		System.out.println(tok);
		do{
			token=scanner.getToken();
			System.out.println(token);
		}while (!scanner.finish);
	}
	public void program(){
		if (tok==null){
			Mensajes.add("compilado finalizado Exito!!");
			return;
		}
		while(tok.id==3&&!error){
			varDeclaration();
			current=root;
			if(tok==null)
				break;
		}
		while(tok!=null&&!error){
			statement();
			current=root;
		}
		
		Mensajes.add("compilado finalizado");
		if (!error)
			Mensajes.add("Exito!!");
	}
	public void varDeclaration(){
		if (tok==null)
			return;
		Nodo aux=current;
		eat(3);eat(9);eat(6);
		current=aux;
	}
	public void statement(){
		if (tok==null||error){
			error();
			return;
		}
			
		Nodo aux=current;
		switch ( tok.id ) { 
		case 9: 
			eat(9);eat(4);expresion();eat(6); 
		break; 
		case 1: 
			eat(1);eat(7);expresion();eat(8);statement();
		break; 
		case 2:
			eat(2);eat(7);expresion();eat(8);statement();
		break;
		default: error ();
		}	
		current=aux;
	}
	public void expresion(){
		if (tok==null||error)
			return;
		Nodo aux=current;
		switch ( tok.id ) { 
		case 9: 
			eat(9);
			if(tok==null)
				return;
			if(tok.id==8)
				return;
			eat(5);	eat(9); 
		break; 
		
		default: error ();
		}
		current=aux;
	}
	public void advance(){
		if (tok==null)
			return;
		if (!(tok.id==6||tok.id==7||tok.id==8||tok.id==-1)){
			Nodo n=new Nodo(tok);
			current.addNodo(n); 
			current = n;
		}
		tok =scanner.getToken();	
	}
	public void eat ( int t) { 
		if (tok==null){
			error();
			return;
			}
		if(error)
			return;
		if ( tok.id == t) 
			advance ( ); 
		else 
			error (); 
	} 
	public void error(){
		String cat="";
		if (tok==null)
			cat="sentencia imcompleta";
		else
			cat="error cerca de: "+tok.value;
		errors.add(cat);
		error=true;
	}
}
