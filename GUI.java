import java.util.ArrayList;
import java.util.List;


public class GUI {
	private Tabuleiro mapa;
	private Player playerBranco;
	private Player playerPreto;
	
	private String idGame;
	
	GUI(){
		playerBranco = null;
		playerPreto = null;
	}
	
	GUI(Tabuleiro mapa, Player playerBranco, Player playerPreto){
		this.mapa = mapa;
		this.playerBranco = playerBranco;
		this.playerPreto = playerPreto;
	}
	
	public void setIdGame(String idGame){
		this.idGame = idGame;
	}
	
	// ========== PEQUENAS MENSAGENS ========
	public void showMsg(String msg){
		System.out.println("\n == \n"+msg+"\n == ");
	}
	
	public void empate(){
		System.out.println("\nOMG voces decidiram por um Empate!!!");
	}
	
	public void saveData(){
		System.out.println("\n==\n Dados salvos com sucesso!\n==");
	}
	
	public void saveAndExit(){
		System.out.println("\nDados salvos e infelizmente voce saiu desse jogo maravilhoso =(");		
	}
	
	public void exit(){
		System.out.println("\nSaindo...");		
	}
	
	public void invalidMoviment(){
		System.out.println("Movimento invalido ");
	}
	
	public void invalidCommand(){
		System.out.println("Comando invalido ");
	}
	
	public void invalidPlay(){
		System.out.println("Jogada invalida ");	
	}
	
	public void invalidItem(){
		System.out.println("\nItem Invalido ");	
	}
	
	public void movedButNotCheque(){
		System.out.println("Peca movida porem nao causou cheque ");
	}
	
	public void msgPromove(){
		System.out.println("Informe a peca a ser promovida ");
	}
	
	public void showCommand(String command){
		System.out.println(command);		
	}
	
	// ============== MENSAGENS MAIORES ================
	
	//mostra o turno
	public void updateTurn(char lado){
		if(lado == 'P')
			System.out.print("Jogador "+playerPreto.getName()+": ");
		else
			System.out.print("Jogador "+playerBranco.getName()+": ");
	}
	
	//informa que o jogo terminou
	public void finishGame(char lado){
		if(lado == 'P')
			System.out.println("O Jogador "+playerPreto.getName()+" Venceu!");
		else
			System.out.println("O Jogador "+playerBranco.getName()+" Venceu!");
	}
	
	//mostra o lado atual
	public void showLado(char lado){
		if(lado == 'P')
			System.out.println("\nJogador das pecas PRETAS ");
		else
			System.out.println("\nJogador das pecas BRANCAS ");
	}
	
	//mostra pergunta do jogo
	public void askName(){
		System.out.print("Informe seu nome: ");		
	}
	
	// ================= MENSAGENS DO MENU ===================
	
	//exibe menu principal
	public void showMainMenu(){
		System.out.println("\n\n===== MENU PRINCIPAL =====\n");
		System.out.println("1 - Iniciar nova partida");
		System.out.println("2 - Retornar uma partida");
		System.out.println("3 - Dados das partidas");
		System.out.println("4 - Sair");
		System.out.print("\n==> ");
		
	}
	
	//exibe menu de new game
	public void showNewGameMenu(){
		System.out.println("\n\n===== NOVO JOGO =====\n");
		System.out.println("1 - HxH");
		System.out.println("2 - HxIA");
		System.out.print("\n==> ");
	}
	
	//exibe menu de load
	public void showLoadMenu(ArrayList<String> lista){
		String aux[];
		int i=0;
		System.out.println("\n\n===== PARTIDAS PARA CARREGAR =====");
		//percorro a lista
		for(String cur: lista){
			aux = cur.split(" ");//separo nome do status
			System.out.println(i+" - "+aux[0]);
			i++;
		}
		System.out.print("\n==> ");
	}
	
	//mostra dados das partidas
	public void showGameData(List<String> wins){
		System.out.println("\n==== DADOS DAS PARTIDAS ====\n");
		for(String cur: wins)
			System.out.println(cur);
		System.out.println("\n============================\n");
	}
	
	//mostra vitorias por players
	public void showWins(List<String> wins){
		String aux1[];
		String aux2 = "";
		int count = 0;
		System.out.println("\n===== VITORIAS POR PLAYER =====\n");
		//conta vitorias de cada um
		for(String cur: wins){
			aux1 = cur.split(" ");
			if(aux2 == ""){//primeira passada
				aux2 = aux1[0]; //recebe nome
				count = 1;
			}else if(aux2 != aux1[0]){//se for diferente significa outra pessoa
				System.out.println("Nome: "+aux2+" Wins: "+count);
				aux2 = aux1[0];
				count = 1;
			}else //se for igual so adiciona o contador
				count++;
		}
		//imprime o ultimo
		if(aux2 != "")
			System.out.println("Nome: "+aux2+" Wins: "+count);
		System.out.println("\n============================\n");
	}
	
	// ============ TABULEIRO ============
	
	//atualiza o tabuleiro
	public void updateMap(){
		//percorro o tabuleiro
		System.out.println("\n==================== "+playerPreto.getName()+" ===== <ID DA PARTIDA: "+idGame+">");
		System.out.println("  12345678\n");
		for(int i= 0 ; i<8;i++){
			System.out.print((i+1)+" ");
			for(int j=0;j<8;j++)
				System.out.print(mapa.getPosition(i, j));
			System.out.println("");
		}
		System.out.println("==================== "+playerBranco.getName()+"\n");
	}
}
