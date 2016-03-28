import java.util.ArrayList;


public class IAPlayer extends Player{
	
	private Game game;
	private Lista pecasEmJogo;
	private GUI gui;
	
	private char enemy;
	
	private int methodCurrent;
	
	public IAPlayer(char lado,Game game) {
		super(lado);
		this.game = game;
		name ="ZEUS";
		methodCurrent = 0;
	}
	
	public void initialize(){
		this.gui = game.getGUI();
		//recupera o inimigo
		if(lado == 'P')
			enemy = 'B';
		else
			enemy = 'P';
	}
	
	//responde o comando a ser executado
	public String nextCommand(){
		String command = "";
		Peca peca;
		//recupero pecas em jogo
		pecasEmJogo = game.getPecasEmJogo();
		//tenta matar alguma peca
		// ==== TENTA FUGIR DO CHEQUE ====
		if(cheque){
			//recupera o rei
			peca = pecasEmJogo.getPeca('R', lado);
			//tenta sair do cheque se movendo
			int x,y;
			//tento os movimentos ao redor do REI
			for(x = peca.getX()-1;x<peca.getX()+1;x++)
				for(y = peca.getY()-1;y<peca.getY()+1;y++){
					if(peca.canKill(x, y))
						return peca.getPos()+"x"+y+x;
					else if(peca.canMove(x, y))
						return peca.getPos()+""+y+x;
				}
			//se nao conseguir, prossegue codigo tentanto matar alguem
		}
		// =====  tres metodos diferentes somente mudando a varredura de matriz para dar uma diferenciada ====
		switch(methodCurrent){
			case 0:
				command = method1();
				methodCurrent++;
			break;
			case 1:
				command = method2();
				methodCurrent++;
			break;
			case 2:
				command = method3();
				methodCurrent = 0;
			break;
		}
		
		gui.showCommand(command);//mostra o comando na tela
		return command;
	}
	
	//responde peca a ser promovida
	public String getPromote(){
		String command;
		Peca peca = game.getToPromove();
		command = peca.getPos()+"=D";
		return command;
	}

	private String method1(){
		String command = "";
		ArrayList<Peca> pecas;
		Peca peca;
		// ==== TENTA MATAR ====
		pecas = pecasEmJogo.getArrayForSide(lado);
		//percorro todas as casas
		for(int i=8;i>0;i--){
			for(int j=0;j<8;j++){
			//percorro as pecas e verifico se alguma tem influencia naquela coordenada
				for(Peca cur: pecas){
					if(cur.getId() != 'R' && cur.canKill(i, j)){//se a peca puder matar naquela coordenada [NAO MOVIMENTO REI]
						//verifico se tem alguem pra matar la
						peca = pecasEmJogo.get(i,j);
						if(peca != null)
							if(peca.getLado() == enemy) //se tiver inimigo, dispara comando
								return cur.getPos()+"x"+(i+1)+(j+1);
					}
				}
			}
		}
		// ==== SE NAO PODE MATAR TENTA SE MOVER ====
		for(int i=8;i>0;i--){
			for(int j=0;j<8;j++){
			//percorro as pecas e verifico se alguma tem influencia naquela coordenada
				for(Peca cur: pecas){
					if(cur.getId() != 'R' && cur.canMove(i, j)){//se a peca puder se mover naquela coordenada
						return cur.getPos()+""+(i+1)+(j+1);
					}
				}
			}
		}
		return command;
	}

	
	private String method2(){
		String command = "";
		ArrayList<Peca> pecas;
		Peca peca;
		// ==== TENTA MATAR ====
		pecas = pecasEmJogo.getArrayForSide(lado);
		//percorro todas as casas
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
			//percorro as pecas e verifico se alguma tem influencia naquela coordenada
				for(Peca cur: pecas){
					if(cur.getId() != 'R' && cur.canKill(i, j)){//se a peca puder matar naquela coordenada [NAO MOVIMENTO REI]
						//verifico se tem alguem pra matar la
						peca = pecasEmJogo.get(i,j);
						if(peca != null)
							if(peca.getLado() == enemy) //se tiver inimigo, dispara comando
								return cur.getPos()+"x"+(i+1)+(j+1);
					}
				}
			}
		}
		// ==== SE NAO PODE MATAR TENTA SE MOVER ====
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
			//percorro as pecas e verifico se alguma tem influencia naquela coordenada
				for(Peca cur: pecas){
					if(cur.getId() != 'R' && cur.canMove(i, j)){//se a peca puder se mover naquela coordenada
						return cur.getPos()+""+(i+1)+(j+1);
					}
				}
			}
		}
		return command;
	}

	private String method3(){
		String command = "";
		ArrayList<Peca> pecas;
		Peca peca;
		// ==== TENTA MATAR ====
		pecas = pecasEmJogo.getArrayForSide(lado);
		//percorro todas as casas
		for(int i=0;i<8;i++){
			for(int j=8;j>0;j--){
			//percorro as pecas e verifico se alguma tem influencia naquela coordenada
				for(Peca cur: pecas){
					if(cur.getId() != 'R' && cur.canKill(i, j)){//se a peca puder matar naquela coordenada [NAO MOVIMENTO REI]
						//verifico se tem alguem pra matar la
						peca = pecasEmJogo.get(i,j);
						if(peca != null)
							if(peca.getLado() == enemy) //se tiver inimigo, dispara comando
								return cur.getPos()+"x"+(i+1)+(j+1);
					}
				}
			}
		}
		// ==== SE NAO PODE MATAR TENTA SE MOVER ====
		for(int i=0;i<8;i++){
			for(int j=8;j>0;j--){
			//percorro as pecas e verifico se alguma tem influencia naquela coordenada
				for(Peca cur: pecas){
					if(cur.getId() != 'R' && cur.canMove(i, j)){//se a peca puder se mover naquela coordenada
						return cur.getPos()+""+(i+1)+(j+1);
					}
				}
			}
		}
		return command;
	}

}
