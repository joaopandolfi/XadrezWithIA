
public class Tabuleiro {
	char[][] mapa;
	
	Tabuleiro(char[][] mapa){
		this.mapa = mapa;
	}
	
	public char getPosition(int x, int y){
		return mapa[y][x];
	}
	
	public void setPosition(char val, int x, int y){
		mapa[y][x] = val;
	}
	
	public boolean inMap(int x, int y){
		boolean s = true;
		if(x >= 8 || x<0)
			s = false;
		if(y>=8 || y<0)
			s = false;
		return s;
	}
	
	public boolean isEmpty(int x, int y){
		if(!inMap(x, y))
			return false;
		return (mapa[x][y] == ' ' || mapa[x][y] == '#');
	}
}
