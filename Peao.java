
public class Peao extends Peca{
	private int delta;
	
	Peao(Tabuleiro mapa, char lado){
		super(mapa, lado, 'P', 1);
	}
	
	/* Peão é diferente por ter restrições de movimento */
	
	//tenta matar
	public boolean tryKill(String xy){
		int xi, yi;
		xi = Integer.parseInt(xy.substring(0,1)) -1; //primeiro caracter 
		yi = Integer.parseInt(xy.substring(1,2)) -1; //segundo caracter 
		
		if(!inMap(xi,yi))
			return false;
		
		if(tryBack(xi, yi))
			return false;
		//verifica se a casa de destino esta ocupada
		if(!ocupado(xi,yi)){
			return false;
		}
		
		if(xi == (x+1) || xi == (x-1)){ //laterais validas
			if(y+delta == yi){ //uma casa para frente
				if(tryKill(xi,yi)){ //tenta comer
					movePeao(xi,yi);
					return true;
				}
			}
		}
		return false;
	}
	
	//tenta se mover
	public boolean tryMove(String xy){
		int xi, yi;
		xi = Integer.parseInt(xy.substring(0,1)) -1; //primeiro caracter
		yi = Integer.parseInt(xy.substring(1,2)) -1; //segundo caracter

		if(!inMap(xi,yi))
			return false;

		//verifica se a casa de destino esta vazia
		if(ocupado(xi,yi)){
			return false;
		}
		
		if(tryBack(xi, yi))
			return false;
		else{
			if(xi != x){ //tentou ir para o lado
				return false; //não pode se MOVER para lá
			}else{//tentou ir para frente
				if(Math.abs(y - yi) <= 1){ //uma casa
					movePeao(xi,yi);
					return true;
				}
				else if(Math.abs(y - yi) == 2 && first){ //duas casas no primeiro movimento
					//se o caminho não estiver ocupado
					if(!ocupado(xi,yi) && !ocupado(x,y+delta)){ 
						movePeao(xi,yi);
						return true;
					}
				}
				else //mais de uma casa
					return false;
			}			
		}
		return false;
	}
	
	//verifica movimento [Atacar]
	public boolean verificaMovimento(int xi, int yi){
		tryBack(xi, yi);
		if(xi == (x+1) || xi == (x-1)){ //laterais validas
			if(y+delta == yi){ //uma casa para frente
					return true;
			}
		}
		return false;
	}
	
	//verifica se a peca pode se mover para la
	public boolean canMove(int xi, int yi){
		if(!inMap(xi,yi))
			return false;

		//verifica se a casa de destino esta vazia
		if(ocupado(xi,yi))
			return false;
		
		if(tryBack(xi, yi))
			return false;
		
		if(xi != x){ //tentou ir para o lado
			return false; //não pode se MOVER para lá
		}else{//tentou ir para frente
			if(Math.abs(y - yi) <= 1){ //uma casa
				movePeao(xi,yi);
				return true;
			}
			else if(Math.abs(y - yi) == 2 && first){ //duas casas no primeiro movimento
				//se o caminho não estiver ocupado
				if(!ocupado(xi,yi) && !ocupado(x,y+delta)){ 
					return true;
				}
			}
			else //mais de uma casa
				return false;
		}				
		return false;
	}
	
	//move a peça e verifica se precisa promover 
	private void movePeao(int xi, int yi){
		movePeca(xi,yi);
		if(lado == 'P' && yi == 7)
			game.setPromove(this);
		else if(lado == 'B' && yi == 0)
			game.setPromove(this);
	}
	
	//tenta voltar
	public boolean tryBack(int x,int y){	
		if(lado == 'P'){
			delta = 1;
			return this.y>y;
		}
		else{
			delta = -1;
			return this.y<y;
		}
	}
}
