package cz.uhk.pro2.flappy.game;

import java.awt.Graphics;

import cz.uhk.pro2.flappy.game.tiles.WallTile;

public class GameBoard implements TickAware {
	Tile[][] tiles; // matice dlazdic na herni plose
	int shiftX; // o kolik pixelu uz svet ubehl doleva
	int widthPix; // sirka hraci plochy v pixelech
	Bird bird;
	
	public GameBoard() {
		tiles = new Tile[20][10];
		tiles[2][1] = new WallTile();
		bird = new Bird(100, 100); /// TODO umistit do stredu okna?
	}
	
	public GameBoard(Tile[][] tiles) {
		this.tiles = tiles;
		bird = new Bird(100, 100); /// TODO umistit do stredu okna?
	}
	
	public void setWidthPix(int widthPix) {
		this.widthPix = widthPix;
	}
	
	/**
	 * Vykresli celou herni plochu (sloupy, bonusy, ptak) na platno g.
	 * @param g
	 */
	public void draw(Graphics g) {
		// j-souradnice prvni dlazdice vlevo, kterou je nutne kreslit
		int minJ = shiftX/Tile.SIZE;
		// pocet dlazdic (na sirku), kolik je nutne kreslit (do viewportu)
		// + 2 protoze muze chybet cast bunky vlevo a vpravo
		// kvuli obema celeciselnum delenim 
		int countJ = widthPix/Tile.SIZE + 2;
		for (int i = 0; i < tiles.length; i++) {			
			for (int j = minJ; j < minJ+countJ; j++) {
				// chceme, aby level bezel porad dokola, takze modJ se
				// na konci pole vraci zase na 0; tiles[0].length je pocet sloupcu
				int modJ = j % tiles[0].length;
				Tile t = tiles[i][modJ];
				if (t != null) {
					// v bunce je nejaka dlazdice
					// vykreslime ji
					int viewportX = j*Tile.SIZE - shiftX;
					int viewportY = i*Tile.SIZE;
					t.draw(g, viewportX, viewportY);
				}
			}
		}
		// vykreslit ptaka
		bird.draw(g);
	}

	@Override
	public void tick(long ticksSinceStart) {
		//s kazdym tickem ve hre posuneme hru o jeden pixel
		//tj. pocet ticku a pixelu se rovnaji
		shiftX = (int)ticksSinceStart;
		
		// dame vedet ptakovi, ze hodiny tickly
		bird.tick(ticksSinceStart);
	}
	
	public void kickTheBird() {
		bird.kick();
	}
	
	
}
