
public class Rainha extends Peca{

	Rainha(Tabuleiro mapa, char lado){
		super(mapa, lado, 'D', 9);
	}
	
	//verifica se o movimento é valido [RAINHA = BISPO + TORRE]
	protected boolean verificaMovimento(int xi, int yi){
		if(!inMap(xi, yi))
			return false;
		// ===== MOVIMENTO DO BISPO =====
		//verifica se o caminho é valido
		if(Math.abs(x-xi) == Math.abs(y-yi)){ //se forma um quadrado entre os pts
			int delta = Math.abs(x-xi)-1;
			int xa,ya;
			xa = x+1;
			ya = y+1;
			//percorro até o xi yi
			for(int i = 0 ; i<delta;i++){
				if(ocupado(xa,ya))
					return false;
				xa++;
				ya++;
			}
			//se chegar aqui é pq n tem ngm no caminho
			return true;
		}
		
		// ===== TESTA MOVIMENTO DA TORRE =====
		if(xi != x && yi == y){//verifico se andou pela linha
			if(xi >= x){//direita
				//verifico se o caminho esta vazio
				for(int i = (x+1); i <xi;i++){
					if(ocupado(i,yi))
						return false;
				}
			}else{//esquerda
				//verifico se o caminho esa vazio
				for(int i = (xi-1); i>x;i--){
					if(ocupado(i,yi))
						return false;
				}
			}
			return true;
		}else if(xi == x && yi != y){ //verifico se andou pela coluna
			if(yi>=y){//para baixo
				//verifico se o caminho esta vazio
				for(int i = (y+1); i <yi;i++){
					if(ocupado(x,i))
						return false;
				}
			}else{//para cima
				//verifico se o caminho esa vazio
				for(int i = (yi-1); i>y;i--){
					if(ocupado(x,i))
						return false;
				}				
			}
			return true;
		}
		return false;
	}
}
