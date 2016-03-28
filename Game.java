import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Game {
	private String command;
	private String idGame;
	private String dtIni;
	private String dtFim;
	private char turno;
	private boolean promove;
	private Tabuleiro mapa;

	private boolean finished;
	
	private Peca toPromove;
	
	private Lista pecasEmJogo;
	private Lista pecasMortas;
	
	private Player playerPreto;
	private Player playerBranco;
	
	private Arquivos arqControl;

	private GUI gui;
	
	Game(){
		promove = false;
		finished = false;
		arqControl = new Arquivos();
	}
	
	//inicializa jogo contra IA
	public void initializeWithIA(){
		playerBranco = new Player('B');
		playerPreto = new IAPlayer('P',this);
		initialize();
	}
	
	//inicializa jogo HumanoXHumano
	public void initializeWithHuman(){
		playerPreto = new Player('P');
		playerBranco = new Player('B');
		initialize();
	}

	//inicializa game
	private void initialize(){
		//inicializa leitura de arquivos
		arqControl.readMat();
		mapa = arqControl.mapa();
		gui = new GUI(mapa, playerBranco, playerPreto);
	}

	//inicializa os players
	private void initializePlayers(){
		gui.showLado(playerBranco.getLado());
		playerBranco.initialize();
		gui.showLado(playerPreto.getLado());
		playerPreto.initialize();
	}
	
	//seta data incial
	public void setInitialDate(String dtIni){
		this.dtIni = dtIni;
	}
	
	//retorna data e hora
	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	// ===== FUNCOES DE CONTROLE BEFORE GAME =====
	
	//inicializa novo game
	public void newGame(String idGame){
		this.idGame = idGame;
		turno = 'B';
		dtIni = getDateTime();
		pecasMortas = new Lista();
		//recebo a lista de pecas em jogo
		pecasEmJogo = arqControl.getPecasNewGame();
		pecasEmJogo.initializePecas(this);
		gui.setIdGame(idGame);
		//Inicializa players
		initializePlayers();
	}
	
	//carrega um game salvo
	public void loadGame(String arquivo){
		arqControl.loadGame(this, arquivo);
	}
	
	//seta a lista de pecas
	public void setPecas(Lista pecasEmJogo, Lista pecasMortas){
		this.pecasEmJogo = pecasEmJogo;
		this.pecasMortas = pecasMortas;
		//inicializa as pecas
		pecasEmJogo.initializePecas(this);
		pecasMortas.initializePecas(this);
		gui.setIdGame(idGame);
	}
	
	//recupera player branco
	public Player getPlayerBranco(){
		return playerBranco;
	}
	
	//recupera player preto
	public Player getPlayerPreto(){
		return playerPreto;
	}
	
	//seta o turno
	public void setTurn(char turn){
		turno = turn;
	}
	
	//seta id da partida
	public void setIdGame(String idGame){
		this.idGame = idGame;
	}
	
	// ============= FUNCOES PARA IA =============
	
	public Lista getPecasEmJogo(){
		return pecasEmJogo;
	}
	
	public Lista getPecasMortas(){
		return pecasMortas;
	}
	
	public Peca getToPromove(){
		return toPromove;
	}
	
	public GUI getGUI(){
		return gui;
	}
	
	// ============= FUNCOES IN GAME ==============
	
	//loop principal do jogo
	public void loop(){
		Player currentPlayer;
		Player enemy;
		Peca currentPeca;
		char proximoTurno = 'P';
		if(turno == 'P')//se o turno atual for do branco
			proximoTurno = 'B';
		while(!finished){
			//atualizo o mapa
			gui.updateMap();
			//verifica de quem e o turno
			if(turno == 'B'){
				currentPlayer = playerBranco;
				enemy = playerPreto;
			}
			else{
				currentPlayer = playerPreto;
				enemy = playerBranco;
			}

			gui.updateTurn(turno);
			//leio a linha de comando
			command = currentPlayer.nextCommand();
						
			//processa o comando
			if(command.contains("x")){ //verifica se é CAPTURA 
				currentPeca = pecasEmJogo.containsPeca(command.substring(0,2));//primeira coordenada
				if(currentPeca == null){ //se nao estiver peca na primeira coordenada
					gui.invalidCommand();
					continue;
				}
				if(currentPeca.getLado() != turno){//tenta mover peca que nao e sua
					gui.invalidMoviment();
					continue;
				}
				//tenta matar a peca da segunda coordenada
				if(!currentPeca.tryKill(command.substring(3,5))){//segunda coordenada
					gui.invalidMoviment();
					continue;			
				}
			}
			else if(command.contains("+")){//verifica se é CHEQUE
				currentPeca = pecasEmJogo.containsPeca(command.substring(0,2));//primeira coordenada
				if(currentPeca == null){ //se nao estiver peca na primeira coordenada
					gui.invalidCommand();
					continue;
				}
				if(currentPeca.getLado() != turno){//tenta mover peca que nao e sua
					gui.invalidMoviment();
					continue;
				}
				if(command.contains("x")){ //verifica se tem CAPTURA 
					//tenta matar a peca da segunda coordenada
					if(!currentPeca.tryKill(command.substring(3,5))){//segunda coordenada
						gui.invalidMoviment();
						continue;			
					}
				}
				else{ //entao e MOVIMENTACAO com cheque
					//tenta se mover para coordenada
					if(!currentPeca.tryMove(command.substring(2,4))){// segunda coordenada
						gui.invalidMoviment();
						continue;
					}
				}
				Peca rei = pecasEmJogo.getPeca('R', enemy.getLado()); //recupera o rei inimigo
				//verifico se o rei inimigo ficou em cheque
				if(!verifyCheque(currentPlayer.getLado(), rei.getX(), rei.getY())){ 
					gui.movedButNotCheque();
				}else
					enemy.setCheque(); //coloca inimigo em cheque
			}
			else if(command.contains("#")){//verifica se é CHEQUE MATE
				//verifico se o inimigo ja estava em cheque
				if(enemy.inCheque())
					finishGame(currentPlayer);
				else{
					currentPeca = pecasEmJogo.containsPeca(command.substring(0,2));//primeira coordenada
					if(currentPeca == null){ //se nao estiver peca na primeira coordenada
						gui.invalidCommand();
						continue;
					}
					if(currentPeca.getLado() != turno){//tenta mover peca que nao e sua
						gui.invalidMoviment();
						continue;
					}
					if(command.contains("x")){ //verifica se tem CAPTURA 
						//tenta matar a peca da segunda coordenada
						if(!currentPeca.tryKill(command.substring(3,5))){//segunda coordenada
							gui.invalidMoviment();
							continue;			
						}
					}
					else{ //entao e MOVIMENTACAO
						//tenta se mover para coordenada
						if(!currentPeca.tryMove(command.substring(2,4))){// segunda coordenada
							gui.invalidMoviment();
							continue;
						}
						Peca rei = pecasEmJogo.getPeca('R', enemy.getLado()); //recupera o rei inimigo
						//verifico se o rei inimigo ficou em cheque
						if(verifyCheque(currentPlayer.getLado(), rei.getX(), rei.getY())){ 
							finishGame(currentPlayer);
						}					
					}				
				}
			}
			else if(command.equals("O-O")){//ROQUE MENOR
				if(!tryRoqueMenor(currentPlayer)){
					gui.invalidPlay();
					continue;
				}
			}
			else if(command.equals("O-O-O")){//ROQUE MAIOR
				if(!tryRoqueMaior(currentPlayer)){
					gui.invalidPlay();
					continue;
				}
			}
			else if(command.equals("salvar")){//salvar o jogo
				arqControl.saveGame(idGame, playerPreto, playerBranco, pecasEmJogo, pecasMortas,turno,dtIni);
				gui.saveData();
				continue;
			}
			else if(command.equals("sair")){//salvar e sair do jogo
				arqControl.saveGame(idGame, playerPreto, playerBranco, pecasEmJogo, pecasMortas,turno,dtIni);
				gui.saveAndExit();
				return;
			}
			else if(command.equals("pontos")){//Exibe pontuacao
				gui.showMsg(pecasMortas.countPontuation(turno));
				continue;
			}
			else if(command.equals("empate")){//Empate
				gui.empate();
				return;
			}
			else {//MOVIMENTACAO comum
				currentPeca = pecasEmJogo.containsPeca(command.substring(0,2));//primeira coordenada
				if(currentPeca == null){ //se nao estiver peca na primeira coordenada
					gui.invalidCommand();
					continue;
				}
				if(currentPeca.getLado() != turno){//tenta mover peca que nao e sua
					gui.invalidMoviment();
					continue;
				}
				//tenta se mover para coordenada
				if(!currentPeca.tryMove(command.substring(2,4))){// segunda coordenada
					gui.invalidMoviment();
					continue;
				}
			}
			
			//verifico se houve promocao
			if(promove){
				gui.msgPromove();
				command = "";
				char idnew = ' ';
				//leio comando
				while(!command.contains("=")){
					command = currentPlayer.getPromote();
					//valido comando
					if(!command.contains("=")){
						gui.invalidCommand();
						continue;
					}
					//verifico se a informada e a mesma que esta apta a ser promovida
					if(!command.substring(0, 2).equals(toPromove.getPos())){
						gui.invalidPlay();
						continue;
					}
					//recupero o nome da nova
					idnew = command.charAt(3);
				}
				//promove a peca
				promove(idnew);
				promove = false;
			}
			
			//verifico se o player estava em cheque
			if(currentPlayer.inCheque()){
				Peca rei = pecasEmJogo.getPeca('R', currentPlayer.getLado());
				//se nao estiver mais em cheque atualizo seu status
				if(!verifyCheque(enemy.getLado(), rei.getX(), rei.getY()))
					currentPlayer.notCheque();
			}
			
			//atualizo as variaveis de controle de turno
			turno = proximoTurno;
			proximoTurno = currentPlayer.getLado();
		}
	}
	
	
	//mata peça
	public void kill(Peca killer, Peca killed){
		Player pKiller;
		//soma pontuacao para o lado correto
		if(killer.getLado() == 'P')
			pKiller = playerPreto;
		else
			pKiller = playerBranco;
		
		pKiller.addPoint(killed.getPontos());
		killed.dead();
		pecasMortas.add(killed);
		pecasEmJogo.remove(killed);
		//verifica se matou o Rei
		if(killed.getId() == 'R')
			finishGame(pKiller);
	}
	
	//marca peça para promocao
	public void setPromove(Peca peao){
		toPromove = peao;
		promove = true;
	}
	
	//finaliza jogo com um vencedor
	private void finishGame(Player winner){
		//sai do loop
		finished = true;
		dtFim = getDateTime();
		arqControl.saveWin(winner.getName(), dtIni, dtFim);
		gui.finishGame(winner.getLado());
	}
	
	// ============== CONTROLE DE CHEQUE ===============
	
	//verifica se a coordenada esta em cheque [lado -> atacante]
	public boolean verifyCheque(char lado,int xi, int yi){
		ArrayList<Peca> pecas = pecasEmJogo.getArrayForSide(lado);
		//percorro as pecas e verifico se alguma tem influencia naquela coordenada
		for(Peca cur: pecas){
			if(cur.canKill(xi, yi)){//se a peca puder matar naquela coordenada, a celula esta em cheque
				return true;
			}
		}
		return false;
	}
	
	// ============== CONTROLE DE JOGADAS PRE DEFINIDAS ===============
	
	//tenta fazer o roque Maior
	private boolean tryRoqueMaior(Player player){
		Peca rei;
		Peca torre;
		char enemy = 'P';
		int xi = 0;
		//nao pode fazer rock se estiver em cheque
		if(player.inCheque())
			return false;

		//recupero o lado do tabuleiro e coloco o x relativo
		if(player.getLado() == 'P'){
			xi = 0; 
			enemy = 'B';
		}
		else 
			xi = 7;
		
		//verifico se a posição final do rei vai estar em cheque
		if(verifyCheque(enemy,2, xi))
			return false;
		//verifico se o caminho esta livre
		if(!( mapa.isEmpty(1, xi) && mapa.isEmpty(2, xi) &&  mapa.isEmpty(3, xi)))
			return false;
		//verifico se as peças estao no lugar certo
		rei = pecasEmJogo.get(4, xi);//lugar do rei 
		if(rei == null)
			return false;
		if(rei.getId() != 'R')//se for Rei
			return false;
		if(!rei.isFirstMove())//se foi movido
			return false;
		torre = pecasEmJogo.get(0, xi);//lugar da torre da direita 
		if(torre == null)
			return false;
		if(torre.getId() != 'T')//se for Torre
			return false;
		if(!torre.isFirstMove())//se foi movido
			return false;
		//faz o roque
		rei.movePeca(2, xi);
		torre.movePeca(3, xi);
		
		return true;
	}
	
	//tenta fazer o Roque Menor
	private boolean tryRoqueMenor(Player player){
		Peca rei;
		Peca torre;
		char enemy = 'P';
		int xi = 0;
		//nao pode fazer rock se estiver em cheque
		if(player.inCheque())
			return false;

		//recupero o lado do tabuleiro e coloco o x relativo
		if(player.getLado() == 'P'){
			xi = 0; 
			enemy= 'B';
		}
		else 
			xi = 7;
		
		//verifico se a posição final do rei vai estar em cheque
		if(verifyCheque(enemy,6, xi))
			return false;
		
		//verifico se o caminho esta livre
		if(!( mapa.isEmpty(5, xi) && mapa.isEmpty(xi, 5)))
			return false;
		
		//verifico se as peças estao no lugar certo
		rei = pecasEmJogo.get(4, xi);//lugar do rei 
		if(rei == null)
			return false;
		if(rei.getId() != 'R')//se for Rei
			return false;
		if(!rei.isFirstMove())//se foi movido
			return false;
		
		torre = pecasEmJogo.get(7, xi);//lugar da torre da direita 
		if(torre == null)
			return false;
		if(torre.getId() != 'T')//se for Torre
			return false;
		if(!torre.isFirstMove())//se foi movido
			return false;
		
		//faz o roque
		rei.movePeca(6, xi);
		torre.movePeca(5, xi);
		
		return true;
	}
	
	//promove a peça
	private boolean promove(char idNew){
		Peca nova;
		switch (idNew){
		case 'D'://dama
			toPromove.dead(); //mato a peca
			pecasEmJogo.remove(toPromove); //removo da lista de pecas em jogo
			nova = new Rainha(mapa, toPromove.getLado()); //crio a nova peca
		break;
		case 'T'://torre
			toPromove.dead(); //mato a peca
			pecasEmJogo.remove(toPromove); //removo da lista de pecas em jogo
			nova = new Torre(mapa, toPromove.getLado()); //crio a nova peca
		break;
		case 'C'://cavalo
			toPromove.dead(); //mato a peca
			pecasEmJogo.remove(toPromove); //removo da lista de pecas em jogo
			nova = new Cavalo(mapa, toPromove.getLado()); //crio a nova peca
		break;
		case 'B'://bispo
			toPromove.dead(); //mato a peca
			pecasEmJogo.remove(toPromove); //removo da lista de pecas em jogo
			nova = new Bispo(mapa, toPromove.getLado()); //crio a nova peca
		break;
		default://peca inexistente ou invalida
			return false;
		}
		nova.setLocation(toPromove.getX(), toPromove.getY()); //coloco ela na posicao certa
		nova.initialize(this, pecasEmJogo); //inicializo peca
		pecasEmJogo.add(nova); //adiciono na lista de pecas em jogo
		promove = false; //desabilito promocao

		return true;
	}

}
