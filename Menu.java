import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


public class Menu {
	private Game game;
	private Arquivos arqControl;
	private GUI gui;
	
	private Scanner teclado;
	
	Menu(){
		game = new Game();
		arqControl = new Arquivos();
		gui = new GUI();
		teclado = new Scanner(System.in);
	}
	
	public void startMenu(){
		String idGame="0";
		boolean ct = false;
		int choose = 0;
		while(!ct){
			//exibe menu principal
			gui.showMainMenu();
			choose = Integer.parseInt(teclado.nextLine());
			switch(choose){
			case 1:
				//recupera o idGame
				idGame = arqControl.loadidGame();
				//exibo o menu de novo jogo
				showNewGameMenu(idGame);
				ct = true;
			break;
			case 2:
				//exibe o menu de load
				showLoadMenu();
				ct = true;
			break;
			case 3:
				//exibo os dados dos jogos
				gamesData();
			break;
			case 4:
				//saio do programa
				gui.exit();
				ct = true;
			break;
			default:
				//informo que o item selecionado e invalido
				gui.invalidItem();
			}
		}
	}
	
	//exibe o menu de new game
	private void showNewGameMenu(String idGame){
		boolean ct = false;
		int choose = 0;
		while(!ct){
			gui.showNewGameMenu();
			choose = Integer.parseInt(teclado.nextLine());
			switch(choose){
			case 1:
				newGameWithHuman(idGame);
				ct = true;
			break;
			case 2:
				newGameWithIA(idGame);
				ct = true;
			break;
			default:
				gui.invalidItem();
			}
		}
	}
	
	//Menu de load de jogos
	private void showLoadMenu(){
		boolean ct = false;
		int tam;
		int choose = 0;
		String aux1[];
		String aux2;
		//recupera as partidas salvas
		ArrayList<String> itensMenu = arqControl.loadLogSaves();
		tam = itensMenu.size();
		while(!ct){
			//exibe menu
			gui.showLoadMenu(itensMenu);
			choose = Integer.parseInt(teclado.nextLine());
			if(choose < tam && choose >=-1)//verifica se a escolha e valida
				ct = true;
		}
		//recupero o nome do arquivo
		aux2 = itensMenu.get(choose);
		aux1 = aux2.split(" ");
		loadGame(aux1[0]); //carrego o jogo selecionado
	}
	
	//inicializa um jogo HxH
	private void newGameWithHuman(String idGame){
		game.initializeWithHuman();
		game.newGame(idGame);
		game.loop();
	}

	//inicia um jogo HxIA
	private void newGameWithIA(String idGame){
		game.initializeWithIA();
		game.newGame(idGame);
		game.loop();
	}
	
	//carrega um jogo salvo
	private void loadGame(String arq){
		game.initializeWithHuman();
		game.loadGame(arq);
		game.loop();
	}

	//imprime os dados da lista
	private void gamesData(){
		//ordena lista
		List<String> wins = arqControl.loadWins();
		List<String> subList = new ArrayList<String>();
		if(wins.size() != 0){ //se tiver vitorias
			subList = wins.subList(1, wins.size());
			Collections.sort(subList);
		}
		//imprime lista
		gui.showGameData(subList);
		//imprime vitorias por player
		gui.showWins(subList);
	}
}
