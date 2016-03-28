

public class Peca {
	protected int x;
	protected int y;
	protected int pontos; //pontuacao da peca
	protected char lado; //lado da peca [preto ou branco]
	protected char id; //id da peca
	protected Tabuleiro mapa; //mapa
	protected char lastColor; //ultima cor da celula do mapa
	protected boolean first; //primeira movimentação da peça
	protected Lista pecas; // lista de pecas em jogo
	protected Game game; //game
	
	Peca(Tabuleiro mapa, char lado, char id, int pontos){
		this.id = id;
		this.mapa = mapa;
		this.lado = lado;
		this.pontos = pontos;
		this.first = true;
	}
	
	//passa as classes de controle
	public void initialize(Game game,Lista pecas){
		this.pecas = pecas;
		this.game = game;
	}
	
	//retorna localização x
	public int getX(){
		return x;
	}
	
	//retorna localização y
	public int getY(){
		return y;
	}
	
	//retorna o lado
	public char getLado(){
		return lado;
	}
	
	//retorna o valor da peça
	public int getPontos(){
		return pontos;
	}
	
	//retorna identificador da peça
	public char getId(){
		return id;
	}
	
	//informa se a peca foi movida
	public boolean isFirstMove(){
		return first;
	}
	
	//retorna posicao em formato de string XY
	public String getPos(){
		return (x+1)+""+(y+1);
	}
	
	//peça morre
	public void dead(){
		mapa.setPosition(lastColor, y, x);
	}
	
	/* ======== CONTROLE DE MOVIMENTACAO ========== */
	
	//seta localizacao
	public void setLocation(int x, int y){
		this.x = y;
		this.y = x;
		//guardo cor do quadrado
		lastColor = mapa.getPosition(x, y);
		//atualizo mapa
		mapa.setPosition(id, x, y);
	}
	
	//tenta se mover
	public boolean tryMove(String xy){
		int xi, yi;
		xi = Integer.parseInt(xy.substring(0,1)) -1; //primeiro caracter 
		yi = Integer.parseInt(xy.substring(1,2)) -1; //segundo caracter 
		//verifica se está no mapa
		if(!inMap(xi,yi))
			return false;

		//verifica se está ocupado
		if(ocupado(xi,yi))
			return false;
		
		//verifica movimento
		if(verificaMovimento(xi,yi)){
			movePeca(xi, yi);
			return true;
		}
			
		return false;
	}	
	
	//tenta matar
	public boolean tryKill(String xy){
		int xi, yi;
		xi = Integer.parseInt(xy.substring(0,1)) -1; //primeiro caracter X
		yi = Integer.parseInt(xy.substring(1,2)) -1; //segundo caracter  Y
		
		//verifica se está no mapa
		if(!inMap(xi,yi))
			return false;

		//verifica se está ocupado
		if(!ocupado(xi,yi))
			return false;
		
		//verifica movimento
		if(verificaMovimento(xi,yi)){
			if(tryKill(xi,yi)){
				movePeca(xi, yi);
				return true;
			}
		}
		return false;
	}
	
	//verifica se a peca pode matar alguem naquela coordenada
	public boolean canKill(String xy){
		int xi, yi;
		xi = Integer.parseInt(xy.substring(0,1)) -1; //primeiro caracter X
		yi = Integer.parseInt(xy.substring(1,2)) -1; //segundo caracter  Y
		
		return canKill(xi, yi);
	}
	
	//verifica se a peca pode se mover para aquela coordenada
	public boolean canMove(String xy){
		int xi, yi;
		xi = Integer.parseInt(xy.substring(0,1)) -1; //primeiro caracter X
		yi = Integer.parseInt(xy.substring(1,2)) -1; //segundo caracter  Y
		
		return canMove(xi, yi);
		
	}

	//verifica se a peca pode se mover para aquela coordenada
	public boolean canMove(int xi, int yi){
		//verifica se está no mapa
		if(!inMap(xi,yi))
			return false;

		//verifica se esta ocupado
		if(ocupado(xi,yi))
			return false;
		
		//verifica movimento
		if(verificaMovimento(xi,yi))
			return true;
	
		return false;
		
	}
	
	//verifica se a peca pode matar alguem naquela coordenada
	public boolean canKill(int xi, int yi){
		//verifica se está no mapa
		if(!inMap(xi,yi))
			return false;

		//verifica movimento
		if(verificaMovimento(xi,yi))
			return true;
	
		return false;
	}
	
	/* ======== CONTROLE INTERNO DE MOVIMENTACAO ========== */	
	
	//verifica se a coordenada está no mapa
	protected boolean inMap(int x, int y){
		return mapa.inMap(x, y);
	}
	
	//verifica se a coordenada está ocupada [branco, preto] = [" ","."]
	protected boolean ocupado(int x, int y){
		if(!mapa.isEmpty(x, y))
			return true;
		else 
			return false;
	}
	
	//move peça para coordenada
	protected void movePeca(int x, int y){
		first = false;
		//atualiza celula atual
		mapa.setPosition(lastColor, this.y, this.x);
		//atualiza celula destino
		this.x = x;
		this.y = y;
		lastColor = mapa.getPosition(this.y, this.x);
		mapa.setPosition(id, this.y, this.x);		
	}
	
	//tenta comer efetivamente. se sim, informa ao game
	protected boolean tryKill(int x, int y){
		Peca target = pecas.get(x, y); 
		if(target.getLado() != lado){ //se for do outro time
			game.kill(this, target);
			return true;
		}
		return false;
	}
	
	//verifica se o movimento e valido
	protected boolean verificaMovimento(int xi, int yi){
		return false;
	}
}
