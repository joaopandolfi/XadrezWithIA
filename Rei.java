
public class Rei extends Peca{
	
	Rei(Tabuleiro mapa, char lado){
		super(mapa, lado, 'R', 0);
	}
	
	/* Com o rei e diferente pos ele nao pode se colocar em cheque */
	
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
		
		//verifica se esta se colocando em cheque
		if(game.verifyCheque(ladoEnemy(), xi, yi))
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

		//verifica se esta se colocando em cheque
		if(game.verifyCheque(ladoEnemy(), xi, yi))
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

	//verifica se o movimento é valido
	protected boolean verificaMovimento(int xi, int yi){
		if((x+1) == xi){//#Lado direito
			if((y+1) == yi){//superior
				return true;
			}else if((y-1) == yi){//inferior
				return true;				
			}else if(y == yi) //meio
				return true;
		}else if(x == xi){//#Meio
			if((y+1) == yi){//superior
				return true;
			}else if((y-1) == yi){//inferior
				return true;				
			}else if(y == yi) //meio
				return true;
		}
		else if((x-1) == xi){//#Lado Esquerdo
			if((y+1) == yi){//superior
				return true;
			}else if((y-1) == yi){//inferior
				return true;				
			}else if(y == yi) //meio
				return true;
		}
		return false;
	}

	//verifica se pode matar naquela coordenada
	public boolean canKill(int xi, int yi){
		//se for ele mesmo
		if(x == xi && y == yi)
			return false;
		
		//verifica se está no mapa
		if(!inMap(xi,yi))
			return false;

		//verifica se está ocupado
		if(!ocupado(xi,yi))
			return false;
		
		//verifica se esta se colocando em cheque
		if(game.verifyCheque(ladoEnemy(), xi, yi))
			return false;
		
		//verifica movimento
		if(verificaMovimento(xi,yi))
			return true;
		
		return false;

	}
	
	//verifica se pode se mover para a coordenada
	public boolean canMove(int xi, int yi){		
		//se for ele mesmo
		if(x == xi && y == yi)
			return false;
		
		//verifica se está no mapa
		if(!inMap(xi,yi))
			return false;

		//verifica se está ocupado
		if(ocupado(xi,yi))
			return false;
		
		//verifica se esta se colocando em cheque
		if(game.verifyCheque(ladoEnemy(), xi, yi))
			return false;
		
		//verifica movimento
		if(verificaMovimento(xi,yi))
			return true;
		
		return false;
	}
	
	private char ladoEnemy(){
		char ladoEnemy;
		//pego o lado do inimigo
		if(lado == 'P')
			ladoEnemy = 'B';
		else
			ladoEnemy = 'P';
		return ladoEnemy;
	}
}
