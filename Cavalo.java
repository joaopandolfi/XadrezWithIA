
public class Cavalo extends Peca{
	
	Cavalo(Tabuleiro mapa, char lado){
		super(mapa, lado, 'C', 3);
	}
		
	//verifica se o movimento é valido
	protected boolean verificaMovimento(int xi, int yi){
		if((x+1) == xi){//primeira sequencia para direita
			if((y+2) == yi){//superior
				return true;
			}else if((y-2) == yi){//inferior
				return true;				
			}
		}else if((x+2) == xi){//segunda sequencia p direita
			if((y+1) == yi){//superior 
				return true;
			}else if((y-1) == yi){//inferior
				return true;
			}
		}
		else if((x-1) == xi){//primeira sequencia para Esquerda
			if((y+2) == yi){//superior
				return true;
			}else if((y-2) == yi){//inferior
				return true;
			}
		}else if((x-2) == xi){//segunda sequencia p Esquerda
			if((y+1) == yi){//superior 
				return true;
			}else if((y-1) == yi){//inferior
				return true;
			}
		}
		return false;
	}
}
