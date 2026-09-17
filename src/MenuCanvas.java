import java.io.IOException;
import java.util.Timer;

import javax.microedition.lcdui.*;


public class MenuCanvas extends Canvas{

	/** Não está selecionando nenhuma opção*/
	private final byte NONE_OPTION = -1;

	/** Inicia o jogo.*/
	private final byte START_GAME = 0;

	/** Os comandos do jogo.*/
	private final byte CONTROL_GAME = 1;

	/** Continua de uma certa fase. Após cada fase o jogo é salvo.*/
	private final byte LOAD_GAME = 2;

	/** Opções do jogo como o volume do som...*/
	private final byte OPTION_GAME = 3;

	/** Sai do jogo.*/
	private final byte MENU_QUANTITY = 5;

	private Image copyright0;

	private Image copyright1;

	/** Imagem com o nome do jogo*/
	private Image gameName;

	/** Imagens com os nomes das opções.*/
	private Image[] menuOptions = new Image[MENU_QUANTITY];

	/** Respresenta a opção corrente selecionada.*/
	private Image[] arrow = new Image[2];

	/** posição x dos creditos.*/
	private int i;

	private int iMenu;

	/** posição y dos creditos.*/
	private int j0;

	/** posição y dos creditos.*/
	private int j1;

	/** tempo que os creditos vão durar na tela.*/
	private int delay;

	/** Oção corrente do menu*/
	private byte selection;

	/** ok = True ,os creditos vão ser amostrados. | ok = False ,vai passar os creditos sem ser visto.*/
	private boolean creditsOk;

	/** ok = True ,o menu já pode ser utilizado. | ok = False ,esperar a animação.*/
	private boolean menuOk;
	
	/** ok = true, jogo começou | ok = false jogo ainda não começou.*/
	public static boolean gameStart;

	/** A seta do menu vai ficar na posição correta.*/
	private boolean correctPosition;

	/**
	 * 
	 */
	public MenuCanvas() {
		try {
			for (byte i = 0; i < MENU_QUANTITY; i++) {
				this.menuOptions[i] = Image.createImage("/menu" + i + ".png");
			}
			for (byte i = 0; i < 2; i++) {
				this.arrow[i] = Image.createImage("/" + (i+2) + "lives.png");
			}
			Midlet.height 	= this.getHeight();
			Midlet.width  	= this.getWidth();
			this.gameName	= Image.createImage("/sifuture.png");
			this.copyright0 = Image.createImage("/copyright0.png");
			this.copyright1 = Image.createImage("/copyright1.png");
		} catch(Exception e) {
			e.printStackTrace();
		}	
		MenuCanvas.gameStart = false;
		restart();
	}


	
	private void restart() {
		this.iMenu = -this.arrow[1].getWidth();
		this.i = -this.copyright0.getWidth();
		this.delay = 0;
		this.correctPosition = true;
		this.creditsOk	= true;
		this.menuOk	= false;
		this.selection = NONE_OPTION;
		this.j0 = Midlet.height/2;
		this.j1 = Midlet.height/2 + this.copyright0.getHeight();
	}
	
	public static void restartGame() {
		MenuCanvas.gameStart = false;
	}

	private void paintGameMenu(Graphics g) {

		g.drawImage(this.gameName, Midlet.width/5, Midlet.height/5, 0);
		if (MenuCanvas.gameStart) {
			g.drawImage(this.menuOptions[4], Midlet.width/5, Midlet.height/2, 0);	
		} else{
			g.drawImage(this.menuOptions[0], Midlet.width/5, Midlet.height/2, 0);
		}
		g.drawImage(this.menuOptions[1], Midlet.width/5, Midlet.height/2 + this.menuOptions[0].getHeight(), 0);
		g.drawImage(this.menuOptions[2], Midlet.width/5, Midlet.height/2 + this.menuOptions[0].getHeight() * 2, 0);
		g.drawImage(this.menuOptions[3], Midlet.width/5, Midlet.height/2 + this.menuOptions[0].getHeight() * 3, 0);

		if((this.iMenu < Midlet.width/5 + this.menuOptions[0].getWidth()/2) && (this.correctPosition)) {
			g.drawImage(this.arrow[1], this.iMenu, Midlet.height/2, 0);
			this.iMenu += 10;
		} else{
			this.correctPosition = false;
		}

		if (!correctPosition) {
			if (this.iMenu > Midlet.width/5 - this.arrow[0].getWidth()) {
				this.iMenu -= 3;
				g.drawImage(this.arrow[0], this.iMenu, Midlet.height/2, 0);
			} else{
				this.menuOk = true;	
			}	
		}

		if (this.menuOk) {
			switch (this.selection) {
			case NONE_OPTION:
				this.selection++;
				break;

			case START_GAME:
				g.drawImage(this.arrow[0], Midlet.width/5 -  this.arrow[1].getWidth(),  Midlet.height/2, 0	);
				break;

			case CONTROL_GAME:
				g.drawImage(this.arrow[0], Midlet.width/5 -  this.arrow[1].getWidth(),  Midlet.height/2 + this.menuOptions[0].getHeight(), 0	);
				break;

			case LOAD_GAME:
				g.drawImage(this.arrow[0], Midlet.width/5 -  this.arrow[1].getWidth(),  Midlet.height/2 + this.menuOptions[0].getHeight() * 2 , 0	);			
				break;

			case OPTION_GAME:
				g.drawImage(this.arrow[0], Midlet.width/5 - this.arrow[1].getWidth(),  Midlet.height/2 + this.menuOptions[0].getHeight() * 3, 0	);
				break;
			}
		}
	}

	private void gameMenuSelected() {
		switch (this.selection) {
		case NONE_OPTION:
			this.selection++;
			break;

		case START_GAME:
			try {
				MenuCanvas.gameStart = true;
				restart();
				Midlet.instance.changeScreen();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case CONTROL_GAME:

			break;
		case LOAD_GAME:

			break;
		case OPTION_GAME:
			
			break;
		}
	}

	private void paintCredits(Graphics g) {
		if (this.creditsOk) {
			if (this.i < Midlet.width/4) {
				g.drawImage(this.copyright0, i, j0, 0);
				g.drawImage(this.copyright1, i, j1, 0);
				this.i+=5;
			} else{
				if (this.delay < 50) {
					g.drawImage(this.copyright0, i, j0, 0);
					g.drawImage(this.copyright1, i, j1, 0);
					delay++;
				} else {
					if ((this.j1 >= -this.copyright0.getHeight()) || (this.j0 <= Midlet.height)) {
						this.j0+=1;
						this.j1-=1;
						g.drawImage(this.copyright0, this.i, this.j0, 0);
						g.drawImage(this.copyright1, this.i, this.j1, 0);
					} else {	
						this.creditsOk = false;
					}
				}
			}
		} else{
			paintGameMenu(g);
		}
	}

	/**	Captura as Teclas pressionadas */
	protected void keyPressed(int keyCode) {
		switch(getGameAction(keyCode)) {
		case FIRE:
			this.creditsOk = false;
			gameMenuSelected();
			break;

		case UP:
			if (this.selection > 0) {
				this.selection--;	
			}
			break; 

		case DOWN:	
			// MENU_QUANTITY - 1 por causa da opção Resume game.
			if (this.selection < MENU_QUANTITY - 1) {
				this.selection++;	
			}
			break;
		}
	}

	public void paint(Graphics g) {
		g.fillRect(0, 0, Midlet.width, Midlet.height);
		paintCredits(g);

	}
}
