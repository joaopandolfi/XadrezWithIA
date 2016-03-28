import java.util.Scanner;


public class Player {
	private int pontos;
	protected char lado;
	protected String name;
	protected boolean cheque;
	
	private Scanner teclado;
	
	private GUI gui;
	
	Player(char lado){
		this.pontos = 0;
		this.lado = lado;
		this.cheque = false;
		gui = new GUI();
		teclado = new Scanner(System.in);
	}
	
	//inicializa
	public void initialize(){
		gui.askName();
		name = teclado.nextLine();
	}
	
	//pergunta ao usuario o comando a ser executado
	public String nextCommand(){
		String comando = "";
		comando = teclado.nextLine();
		return comando;
	}
	
	//pergunta ao usuario o comando de promocao
	public String getPromote(){
		String comando;
		comando = teclado.nextLine();
		return comando;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void addPoint(int point){
		pontos += point;
	}
	
	public void setPoint(int point){
		pontos = point;
	}
	
	public void setCheque(){
		cheque = true;
	}
	
	public void notCheque(){
		cheque = false;
	}
	
	//informa se o player esta em cheque
	public boolean inCheque(){
		return cheque;
	}
	
	public String getName(){
		return name;
	}
	
	public int getPoints(){
		return pontos;
	}
	
	public char getLado(){
		return lado;
	}
}
