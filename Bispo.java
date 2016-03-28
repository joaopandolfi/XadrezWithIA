
public class Bispo extends Peca{

	Bispo(Tabuleiro mapa, char lado){
		super(mapa, lado, 'B', 3);
	}
	
	protected boolean verificaMovimento(int xi, int yi){
		if(!inMap(xi, yi))
			return false;
		//verifica se o caminho é valido
		if(Math.abs(y-yi) == Math.abs(x-xi)){ //se forma um quadrado entre os pts
			int delta = Math.abs(y-yi)-1;
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
		return false;
	}
	
}
