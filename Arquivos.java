import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Responsável por ler o Arquivos
 * Estrutura -> data: partidas.txt, vitorias.txt, tabuleiro.txt, pecas.txt, id.txt /games/
 * 				data/games: idgame.txt
 */

public class Arquivos {
	private Tabuleiro matMapa;
	
	Arquivos(){
		//checa se o diretorio existe
		checkDir();
	}
	
	public Tabuleiro mapa(){
		return matMapa;
	}
	
	// =============== 	FUNÇAO PARA CONTROLE DE DIRETORIO ============
	
	private void checkDir(){
		//diretorio
		  File dir = new File("data/");
		    if(!dir.exists()){
		        dir.mkdirs();
		        System.out.println("Copie o conteudo da pasta data para o diretorio do game /data");
		    }
	}
	
	// =============== FUNÇOES PARA SALVAMENTO DE DADOS ==============
	
	//salva log da partida status:[A,F] {Andamento, Finalizado}
	public void saveLogGame(String idGame, char status){
		ArrayList<String> oldLog = loadLogSaves();
		if(oldLog.contains(idGame+" "+status))//se ja existir não adiciona novamente
			return;
		File arquivo = new File("data/partidas.txt");
		FileWriter escritor;
		try {
			//verifica se existe
			if (!arquivo.exists()) {
				//cria um arquivo (vazio)
				arquivo.createNewFile();
			}
			escritor = new FileWriter(arquivo,true); 
			BufferedWriter bw = new BufferedWriter(escritor);
			//escreve no arquivo
			bw.append(idGame+" "+status);
			bw.newLine();//cria nova linha para o prox
			bw.close();
			escritor.close();
				
		} catch (FileNotFoundException e) {
			System.out.println("Falha ao escrever o aquivo");
			System.exit(0);
		} catch (IOException e1) {
			System.out.println("Falha ao escrever o aquivo");
			System.exit(0);
		}
		//salva id do proximo game
		updateIdGame(Integer.parseInt(idGame)+1);
	}

	
	//salva id do proximo game
	public void updateIdGame(int idGame){
		File arquivo = new File("data/id.txt");
		FileWriter escritor;
		try {
			//verifica se existe
			if (!arquivo.exists()) {
				//cria um arquivo (vazio)
				arquivo.createNewFile();
			}
			escritor = new FileWriter(arquivo); 
			BufferedWriter bw = new BufferedWriter(escritor);
			//escreve no arquivo
			bw.write(idGame+"");
			bw.close();
			escritor.close();
				
		} catch (FileNotFoundException e) {
			System.out.println("Falha ao salvar IDGAME");
			System.exit(0);
		} catch (IOException e1) {
			System.out.println("Falha ao salvar IDGAME");
			System.exit(0);
		}
	}
	
	//salva usuario na lista
	public void saveUser(String name){
		File arquivo = new File("data/usuarios.txt");
		FileWriter escritor;
		try {
			//verifica se existe
			if (!arquivo.exists()) {
				//cria um arquivo (vazio)
				arquivo.createNewFile();
			}
			escritor = new FileWriter(arquivo); 
			BufferedWriter bw = new BufferedWriter(escritor);
			//escreve no arquivo
			bw.append(name);
			bw.newLine();//cria nova linha para o prox
			bw.close();
			escritor.close();
				
		} catch (FileNotFoundException e) {
			System.out.println("Falha ao escrever o aquivo");
			System.exit(0);
		} catch (IOException e1) {
			System.out.println("Falha ao escrever o aquivo");
			System.exit(0);
		}
	}
	
	//salva o game em um arquivo
	public void saveGame(String idGame,Player pPreto, Player pBranco, Lista pcsEmJogo, Lista pcsMortas, char turno, String dtIni){
		//salva o log do game
		saveLogGame(idGame, 'A');
		File arquivo = new File("data/games/"+idGame+".txt");
		FileWriter escritor;
		try {
			//verifica se existe
			if (!arquivo.exists()) {
				//cria um arquivo (vazio)
				arquivo.createNewFile();
			}
			escritor = new FileWriter(arquivo); 
			BufferedWriter bw = new BufferedWriter(escritor);
			String aux;
			//escreve no arquivo
			bw.write(dtIni);
			bw.newLine();
			//salva o turno 
			bw.append(turno);
			bw.newLine();
			//Salva dados PlayerPreto
			bw.append(pPreto.getName());
			bw.newLine();
			aux = pPreto.getPoints()+"";
			bw.append(aux);
			bw.newLine();
			if(pPreto.inCheque()) 
				aux = "C";
			else
				aux="N";
			bw.append(aux);
			bw.newLine();
			//Salva dados PlayerBranco
			bw.append(pBranco.getName());
			bw.newLine();
			aux = pBranco.getPoints()+"";
			bw.append(aux);
			bw.newLine();
			if(pBranco.inCheque()) 
				aux = "C";
			else
				aux="N";
			bw.append(aux);
			bw.newLine();
			//Salva Peças em Jogo
			bw.append(pcsEmJogo.getAllPecasForSave("V"));
			//Salva Pecas Mortas
			bw.append(pcsEmJogo.getAllPecasForSave("M"));
			bw.newLine();
			
			bw.close();
			escritor.close();	
		} catch (FileNotFoundException e) {
			System.out.println("Falha ao escrever o aquivo");
			System.exit(0);
		} catch (IOException e1) {
			System.out.println("Falha ao escrever o aquivo");
			System.exit(0);
		}

	}
	
	
	//salva a vitoria em um arquivo
	public void saveWin(String vencedor, String dtIni, String dtFim){
		File arquivo = new File("data/vitorias.txt");
		FileWriter escritor;
		try {
			//verifica se existe
			if (!arquivo.exists()) {
				//cria um arquivo (vazio)
				arquivo.createNewFile();
			}
			escritor = new FileWriter(arquivo); 
			BufferedWriter bw = new BufferedWriter(escritor);
			//escreve no arquivo
			bw.append(vencedor+" "+dtIni+" "+dtFim);
			bw.newLine();//cria nova linha para o prox
			bw.close();
			escritor.close();
				
		} catch (FileNotFoundException e) {
			System.out.println("Falha ao escrever o aquivo");
			System.exit(0);
		} catch (IOException e1) {
			System.out.println("Falha ao escrever o aquivo");
			System.exit(0);
		}

	}
	
	
	// ================ FUNÇOES PARA LEITURA DOS DADOS SALVOS =====================
	
	//load idGame
	public String loadidGame(){
		File arquivo = new File("data/id.txt");
		FileReader arqLeitura;
		BufferedReader leitor;
		String linha = "0";
		try {
			//verifica se existe
			if (!arquivo.exists()) {
				updateIdGame('0');
				return linha;
			}
			arqLeitura = new FileReader(arquivo);
			leitor = new BufferedReader(arqLeitura);
			
			try {
				linha = leitor.readLine();
				//fecho o arquivo
				leitor.close();				
			} catch (IOException e) {
				System.out.println("Falha ao ler o arquivo de IDGAME");
				System.exit(0);
			}
		} catch (FileNotFoundException e) {
			System.out.println("Falha ao ler o aquivo de IDGAME");
			System.exit(0);
		}
		return linha;
	}
	
	//load Log salvos
	public ArrayList<String> loadLogSaves(){
		ArrayList<String> logs = new ArrayList<String>();
		File arquivo = new File("data/partidas.txt");
		FileReader arqLeitura;
		BufferedReader leitor;
		String linha;
		try {
			arqLeitura = new FileReader(arquivo);
			leitor = new BufferedReader(arqLeitura);
			
			try {

				linha = leitor.readLine();
				while(linha != null){
					logs.add(linha);
					linha = leitor.readLine();
				}

				//fecho o arquivo
				leitor.close();				
			} catch (IOException e) {
				System.out.println("Falha ao ler o arquivo de Partidas");
				System.exit(0);
			}
		} catch (FileNotFoundException e) {
			System.out.println("Falha ao ler o aquivo de Partidas");
			System.exit(0);
		}
		return logs;
	}
	
	//carrega vitorias
	public ArrayList<String> loadWins(){
		ArrayList<String> wins = new ArrayList<String>();
		File arquivo = new File("data/vitorias.txt");
		FileReader arqLeitura;
		BufferedReader leitor;
		String linha;
		try {
			arqLeitura = new FileReader(arquivo);
			leitor = new BufferedReader(arqLeitura);
			
			try {
				linha = leitor.readLine();
				while(linha != null){
					wins.add(linha);
					linha = leitor.readLine();
				}
				//fecho o arquivo
				leitor.close();				
			} catch (IOException e) {
				System.out.println("Falha ao ler o arquivo de Vitorias");
				System.exit(0);
			}
		} catch (FileNotFoundException e) {
			System.out.println("Falha ao ler o aquivo de Vitorias");
			System.exit(0);
		}
		return wins;
	}
	
	/*carrega jogo salvo
	 	Estrutura: dataInicial
	 			   turno
	 			   nomePlayerPreto
	 				pontuacao
	 				C ->cheque / N ->Não Cheque
	 			   nomePlayerBranco
	 			   	pontuacao
	 				C ->cheque / N ->Não Cheque
	 			  Lado Idpeca x y status
	 */
	public void loadGame(Game game, String arq){
		Lista pecasEmJogo = new Lista();
		Lista pecasMortas = new Lista();
		Peca aux = null;
		File arquivo = new File("data/games/"+arq+".txt");
		FileReader arqLeitura;
		BufferedReader leitor;
		String linha;
		String[] val;
		try {
			arqLeitura = new FileReader(arquivo);
			leitor = new BufferedReader(arqLeitura);
			
			try {
				
				//seta o id do game
				game.setIdGame(arq);
				//le data inicial
				linha = leitor.readLine();
				game.setInitialDate(linha);
				//le o turno
				linha = leitor.readLine();
				game.setTurn(linha.charAt(0));
				//leio os dados dos players
				//dados do player PRETO
				linha = leitor.readLine();
				game.getPlayerPreto().setName(linha);
				linha = leitor.readLine();
				game.getPlayerPreto().setPoint(Integer.parseInt(linha));
				linha = leitor.readLine();
				if(linha == "C")//em cheque
					game.getPlayerPreto().setCheque();
				//dados do player BRANCO
				linha = leitor.readLine();
				game.getPlayerBranco().setName(linha);
				linha = leitor.readLine();
				game.getPlayerBranco().setPoint(Integer.parseInt(linha));
				linha = leitor.readLine();
				if(linha == "C")//em cheque
					game.getPlayerBranco().setCheque();

				//as 32 pecas -[lado id x y status]
				for(int i=0;i<32;i++){
					linha = leitor.readLine();
					val = linha.split(" ");	//quebro os dados
					
					//cria peca respectiva ao id
					aux = criaPeloId(val[1].charAt(0), val[0].charAt(0));
					
					//adiciono as pecas na lista correta
					if(val[4] == "M") //se esta morta
						pecasMortas.add(aux);
					else{ //se esta viva
						aux.setLocation(Integer.parseInt(val[3]), Integer.parseInt(val[2]));// posiciono no tabuleiro
						pecasEmJogo.add(aux);
					}
				}
			//fecho o arquivo
			leitor.close();
			} catch (IOException e) {
				System.out.println("Falha ao ler o arquivo de Game");
				System.exit(0);
			}
		} catch (FileNotFoundException e) {
			System.out.println("Falha ao ler o aquivo de Game");
			System.exit(0);
		}
		//atualizo as listas de pecas do game
		game.setPecas(pecasEmJogo, pecasMortas);
	}
		
	//le as pecas de new game
	public Lista getPecasNewGame(){
		Lista pecas = new Lista();
		Peca aux = null;
		File arquivo = new File("data/pecas.txt");
		FileReader arqLeitura;
		BufferedReader leitor;
		String linha;
		String[] val;
		try {
			arqLeitura = new FileReader(arquivo);
			leitor = new BufferedReader(arqLeitura);
			
			try {
								
				//as 32 pecas -[lado id x y]
				for(int i=0;i<32;i++){
					linha = leitor.readLine();
					val = linha.split(" ");	//quebro os dados
					
					//cria peca respectiva ao id
					aux = criaPeloId(val[1].charAt(0), val[0].charAt(0));
					// posiciono no tabuleiro
					aux.setLocation(Integer.parseInt(val[2]), Integer.parseInt(val[3]));
					
					pecas.add(aux);
				}
			//fecho o arquivo
			leitor.close();				
			} catch (IOException e) {
				System.out.println("Falha ao ler o arquivo de Pecas");
				System.exit(0);
			}
		} catch (FileNotFoundException e) {
			System.out.println("Falha ao ler o aquivo de Pecas");
			System.exit(0);
		}
		return pecas;
	}
	
	
	//le tabuleiro
	public void readMat(){
		File arquivo = new File("data/tabuleiro.txt");
		FileReader arqLeitura;
		BufferedReader leitor;
		String linha;
		String[] val;
		try {
			arqLeitura = new FileReader(arquivo);
			leitor = new BufferedReader(arqLeitura);
			
			try {
				//crio a matriz
				char[][] mapa = new char[8][8];									
				for(int i=0;i<8;i++){
					linha = leitor.readLine();
					val = linha.split("l");	//quebro os dados
					for(int j=0;j<8;j++){
						mapa[i][j] = val[j].charAt(0);
					}
				}
			//crio o mapa
			matMapa = new Tabuleiro(mapa);
			//fecho o arquivo
			leitor.close();				
			} catch (IOException e) {
				System.out.println("Falha ao ler o Tabuleiro");
				System.exit(0);
			}
		} catch (FileNotFoundException e) {
			System.out.println("Falha ao ler o Tabuleiro");
			System.exit(0);
		}
	}

	//cria peca respectiva ao id
	public Peca criaPeloId(char id,char lado){
		Peca aux = null;
		switch(id){
			case 'B':
				aux = new Bispo(matMapa,lado);
			break;
			case 'T':
				aux = new Torre(matMapa,lado);
			break;
			case 'C':
				aux = new Cavalo(matMapa,lado);
			break;
			case 'R':
				aux = new Rei(matMapa,lado);
			break;
			case 'D':
				aux = new Rainha(matMapa,lado);
			break;
			case 'P':
				aux = new Peao(matMapa,lado);
			break;
		}
		return aux;
	}

}
