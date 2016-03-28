import java.util.ArrayList;


public class Lista {
	private ArrayList<Peca> pecas;
	
	Lista(){
		pecas = new ArrayList<Peca>();
	}
	
	//adiciona uma peca
	public void add(Peca peca){
		this.pecas.add(peca);
	}

	//remove peça da lista
	public void remove(String pos){
		Peca aux = containsPeca(pos);
		pecas.remove(aux);
	}

	public void remove(Peca peca){
		pecas.remove(peca);
	}
	
	public void remove(int x, int y){
		remove(x+""+y);
	}
	
	//recupera uma peca
	public Peca get(int x, int y){
		return containsPeca((x+1)+""+(y+1)); //monta string e chama o metodo
	}
	
	//retorna um array com todas as pecas do lado indicado
	public ArrayList<Peca> getArrayForSide(char lado){
		ArrayList<Peca> pack = new ArrayList<Peca>();
		for(Peca cur: pecas){
			if(cur.getLado() == lado){
				pack.add(cur);
			}
		}
		return pack;
	}
	
	//recupera dados das pecas para salvar
	public String getAllPecasForSave(String state){
		String all = "";
		for(Peca cur: pecas){
			all += cur.getLado()+" "+cur.getId()+" "+cur.getX()+" "+cur.getY()+" "+state+"\n";
		}
		return all;
	}
	
	
	//retorna a peca do tipo e lado
	public Peca getPeca(char tipo, char lado){
		for(Peca cur: pecas){
			if(cur.getId() == tipo && cur.getLado() == lado){
				return cur;
			}
		}
		return null;
	}
	
	//retorna a peca que estiver na pos
	public Peca containsPeca(String pos){
		for(Peca cur: pecas){
			if(cur.getPos().equals(pos)){
				return cur;
			}
		}
		return null;
	}
	
	//inicializa as pecas
	public void initializePecas(Game game){
		for(Peca cur: pecas){
			cur.initialize(game, this);
		}
	}
	
	//conta pontuacao 
	public String countPontuation(char lado){
		String pontuacao = "Pecas Capturadas:";
		int pt = 0;
		//pega o lado do inimigo
		if(lado == 'P')
			lado = 'B';
		else
			lado = 'P';
		//busca rainha
		for(Peca cur: pecas)
			if(cur.getLado() == lado && cur.getId() == 'D'){
				pontuacao +="D ";
				pt+= cur.getPontos();
			}		
		//busca Torre
		for(Peca cur: pecas)
			if(cur.getLado() == lado && cur.getId() == 'T'){
				pontuacao +="T ";
				pt+= cur.getPontos();
			}
		//busca Cavalo
		for(Peca cur: pecas)
			if(cur.getLado() == lado && cur.getId() == 'C'){
				pontuacao +="C ";
				pt+= cur.getPontos();
			}
		//busca Bispo
		for(Peca cur: pecas)
			if(cur.getLado() == lado && cur.getId() == 'B'){
				pontuacao +="B ";
				pt+= cur.getPontos();
			}

		//busca Peao
		for(Peca cur: pecas)
			if(cur.getLado() == lado && cur.getId() == 'P'){
				pontuacao +="P ";
				pt+= cur.getPontos();
			}

		return pontuacao+"  Pontuacao : "+pt;
	}

}
