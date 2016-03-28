
public class Torre extends Peca{

	Torre(Tabuleiro mapa,char lado){
		super(mapa,lado,'T',5);
	}
	
	//verifica se o movimento é valido
	protected boolean verificaMovimento(int xi, int yi){
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
