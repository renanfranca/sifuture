

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 * O Especial da AirShip.
 * 
 * @author RENAN MENESES DE ANDRADE FRANCA.
 *
 */
public class AirShipEspecialShoot {
	/** Um dos tipos de cor do especial.*/
	private final byte ORANGE = 0;

	/** Um dos tipos de cor do especial.*/
	private final byte LIGHT_BLUE = 1;

	/** Um dos tipos de cor do especial.*/
	private final byte DARK_BLUE = 2;

	/** O especial está em andamento, ou seja, tem pelo ao menos uma vida.*/
	private final byte ALIVE = 0;

	/** O especial não está em andamento, ou seja, não tem nenhuma vida.*/
	private final byte DEAD = 1;

	/** Número de imagens da animação do especial.*/
	private final byte ESPECIAL_QUANTITY = 3;

	/** Posição horizontal do Especial*/
	private int x;

	/** Posição vertical do Especial*/
	private int y;

	/** Velocidade do Especial*/
	private byte speedX;

	/** Vetor que irá conter as imagens do especial.*/
	private Image[] especial = new Image[ESPECIAL_QUANTITY];

	/** True = O especial foi diparado pela nave. | False = O especial não foi diparado pela nave*/
	public boolean shootOn;

	/** True = Colidiu com meteoro. | False = Não colidiu com meteoro.*/
	public boolean collisionMeteor;

	/** True = Esperar para uma nova colisão. | False = Colisão permitida.*/
	public boolean collisionDelay;

	/** True = iniciou a animação de colisão. | False = não iniciou a animação de colisão.*/
	public boolean collisionAnimation;

	/** ORANGE = O especial terá a cor laranja | LIGHT_BLUE = O especial terá a cor azul claro |
	 *  DARK_BLUE = O especial terá a cor azul escuro.*/
	private byte especialColor;

	/** O número de vidas que o especial tem.*/
	private int lives;

	/** Estado do especial ALIVE = vivo | DEAD = morto.*/
	private byte state;

	/** Controla qundo deve mudar a animação de colisão do especial.*/
	private byte especialDraw;

	/** Controla o tempo entre cada animação de colisão do especial.*/
	private byte especialTime;

	/** Controla o tempo entre as colisões do especial com o subchief ou com o bosStage1.*/
	private byte especialDelayTime;

	int i = 0;

	public AirShipEspecialShoot(int type) {
		try{
			//Carregando imagem do especial.

			switch (type) {
			case 0:
				//laranja.
				for (i = 0; i < ESPECIAL_QUANTITY; i++) {
					this.especial[i] = Image.createImage("/especial" + this.i + ".png");
				}
				break;

			case 1:
				//azul claro.
				for (i = 3; i < ESPECIAL_QUANTITY*2; i++) {
					this.especial[i] = Image.createImage("/especial" + this.i + ".png");
				}
				try{
				} catch(Exception e) {
					e.printStackTrace();
				}
				break;

			case 2:
				//azul escuro.
				for (i = 6; i < ESPECIAL_QUANTITY*3; i++) {
					this.especial[i] = Image.createImage("/especial" + this.i + ".png");
				}
				break;
			}

		} catch (Exception e){
			e.printStackTrace();
//			e.notify();
		}
	}

	/**
	 * Reseta as variáveis comuns a todas as cores.
	 *
	 */
	public void allColorsRestart() {
		this.collisionDelay = false;
		this.shootOn 	    = false;	
		this.lives		    = 10;
		this.state			= ALIVE;
		this.especialDraw   = 0;
		this.especialTime   = 0;
	}
	/**
	 * Reseta o tiro laranja.
	 *
	 */
	public void orangeRestart() {
		this.collisionAnimation= false;
		this.collisionDelay    = false;
		this.shootOn 	       = false;	
		this.speedX 	       = 5;
		this.especialColor     = ORANGE;
		this.lives		       = 10;
		this.state			   = ALIVE;
		this.especialDraw      = 0;
		this.especialTime      = 0;
		this.especialDelayTime = 0;
	}

	/**
	 * Reseta o tiro azul claro.
	 *
	 */
	public void lightBlueRestart() {
		this.collisionAnimation= false;
		this.collisionDelay    = false;
		this.shootOn 	       = false;	
		this.speedX 	       = 6;
		this.especialColor     = LIGHT_BLUE;
		this.lives		       = 10;
		this.state			   = ALIVE;
		this.especialDraw      = 0;
		this.especialTime      = 0;
		this.especialDelayTime = 0;
	}

	/**
	 * Reseta o tiro azul escuro.
	 *
	 */
	public void darkBlueRestart() {
		this.collisionAnimation= false;
		this.collisionDelay     = false;
		this.shootOn 	   = false;	
		this.speedX 	   = 5;
		this.especialColor = DARK_BLUE;
		this.lives		   = 10;
		this.especialDraw  = 0;
		this.especialTime  = 0;
		this.especialDelayTime = 0;
	}


	/**
	 * Responsável por disparar o especial utilizando a cor como referência..
	 * @param x
	 * 		posição  horizontal na qual a nave atirou o especial.
	 * @param y
	 * 		posição  vertical na qual a nave atirou o especial.
	 * @param sizey
	 * 		altura da imagem da nave.
	 */
	public final void shoot(int x, int y, int sizey) {
		if (this.shootOn) {
			return;
		}

		switch (this.especialColor) {
		case 0:
			this.shootOn = true;
			this.x = - 2*(this.especial[0].getWidth());
			this.y = y - 3*sizey;
			break;

		case 1:
			this.shootOn = true;
			this.x = -(this.especial[0].getWidth());
			this.y = y + (sizey / 2) - this.especial[0].getHeight()/2;
			break;

		case 2:
			this.shootOn = true;
			this.x = - 2*(this.especial[0].getWidth());
			this.y = y + 3 * (sizey);
			break;
		}

	}

	/**
	 * Movimenta o tiro.
	 *
	 */
	public void update() {
		this.x += this.speedX;
	}

	/**
	 * Muda o estado do especial para morto e reseta as variáveis que controlam animação. 
	 */
	public void changeToDead() {
		this.state = DEAD;
		this.especialTime = 0;
		this.especialDraw = 0;
	}

	/**
	 * Decrementa a vida do especial quando ele colidir com meteoro e Ativa a animação de colisão.
	 *
	 */
	public void decreaseEspecialLife() {
		this.lives --;
		this.collisionAnimation = true;
		if (this.lives <= 0) {
			changeToDead();
		}
	}

	/**
	 * Conta o tempo em que o especial pode colidir com o subchief ou com o bosStage1.
	 */
	public void countCollisionDelay() {
		if (this.collisionDelay) {
			this.especialDelayTime++;
			if (this.especialDelayTime == 6) {
				this.especialDelayTime = 0;
				this.collisionDelay = false;
			}
		}
	}

	/**
	 * Testa se ocorreu a colisão entre o especial e o subchief
	 * @param subchief
	 * Saber a localização do mesmo.
	 * @return
	 * True = colidiu | False = não colidiu.
	 */
	public final boolean colideWithBosStage1(BosStage1 bosStage1) {
		if(this.shootOn){
			//Testa se o bosStage1 esteja no estado normal.
			if (bosStage1.state != 3) {
				if (!this.collisionDelay){
					//Compara se o quadrado imaginário em volta da nave colidiu ou não com o quadrado imaginário em volta do meteoro.
					if (((bosStage1.x >= this.x) && (bosStage1.x <= this.x + this.especial[0].getWidth())) && 
							((bosStage1.y >= this.y) && (bosStage1.y <= this.y + this.especial[0].getHeight())) ||
							((bosStage1.x >= this.x) && (bosStage1.x <= this.x + this.especial[0].getWidth())) &&
							((bosStage1.y + bosStage1.bosStage1.getHeight() >= this.y) && (bosStage1.y + bosStage1.bosStage1.getHeight() <= this.y + this.especial[0].getHeight())) || 
							((bosStage1.x >= this.x) && (bosStage1.x <= this.x + this.especial[0].getWidth())) &&
							((bosStage1.y + bosStage1.bosStage1.getHeight()/2 >= this.y) && (bosStage1.y + bosStage1.bosStage1.getHeight()/2 <= this.y + this.especial[0].getHeight()))){
						//Não tem necessidade de entrar no If caso o meteoro já tenha colidido com o bçaster e a animação esteja em andamento.
						this.collisionDelay = true;
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Decrementa em um a vida do bosStage1 caso o especial tenha colidido com o mesmo.
	 * Caso o bosStage1 esteja com o número de vidas igual a zero, a animação do bosStage1 é alterada
	 * para modo de explosão.
	 * @param bosStage1
	 * Saber a localização do mesmo.
	 * @return
	 * True = Mudou animação | False = Não mudou a animação.
	 */
	public boolean decreaseBosStage1Life(BosStage1 bosStage1) {
		bosStage1.lives --;
		if (bosStage1.lives <= 0) {
			bosStage1.changeToExplosion();
			return true;
		}
		return false;
	}

	/**
	 * Testa se occreu colisão entre o especial e o sbosStage1 
	 * @param subchief
	 * 		O objeto subchief que é utilizado para saber a posição do mesmo.		
	 * @return
	 * 		Retorna true caso ocorra colisão e false caso não ocorra.
	 */
	public final boolean colideWithSubchief(Subchief subchief){
		//Nem é preciso testar caso o especial não tenha sido lançado.
		if(this.shootOn){
			//Testa se o subchief esteja no estado normal.
			if (subchief.state == 0) {
				if (!this.collisionDelay){
					//Compara se o quadrado imaginário em volta do especial colidiu ou não com os pontos em volta do subchief.
					if (((subchief.x >= this.x) && (subchief.x <= this.x + this.especial[0].getWidth())) && 
							((subchief.y >= this.y) && (subchief.y <= this.y + this.especial[0].getHeight())) ||
							((subchief.x >= this.x) && (subchief.x <= this.x + this.especial[0].getWidth())) &&
							((subchief.y + subchief.naveMiddle.getHeight() >= this.y) && (subchief.y + subchief.naveMiddle.getHeight() <= this.y + this.especial[0].getHeight()))){
						//Não tem necessidade de entrar no If caso já tenha colidido.
						this.collisionDelay = true;
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Decrementa em um a vida do subchief caso o especial tenha colidido com o mesmo.
	 * Caso o subchief esteja com o número de vidas igual a zero, a animação do subchief é alterada
	 * para modo de explosão.
	 * @param subchief
	 * Saber a localização do mesmo.
	 * @return
	 * True = Mudou animação | False = Não mudou a animação.
	 */
	public boolean decreaseSubchiefLife(Subchief subchief) {
		subchief.lives --;
		if (subchief.lives <= 0) {
			subchief.changeToExplosion();
			return true;
		}
		return false;
	}

	/**
	 * Testa se o especial colidiu com algum(ns) meteoro(s). Caso colidiu ativa a animação de destruição do meteoro.
	 * @param meteorArray
	 * 		O objeto meteoro que é utilizado para saber a posição do mesmo.		
	 * @return
	 * 		True = Ocorreu colisão | False  = Não ocorreu.
	 */
	public final boolean colideWithMeteor(MeteorArray meteorArray){
		this.collisionMeteor = false;
		//Nem é preciso testar caso o especial não tenha sido lançado.
		if(this.shootOn){
			//Compara se o quadrado imaginário em volta do especial colidiu ou não com os imaginário em volta do meteoro.
			for (byte i = 0; i < meteorArray.METEOR_QUANTITY; i++) {
				if (((meteorArray.meteor[i].x >= this.x) && (meteorArray.meteor[i].x <= this.x + this.especial[0].getWidth())) && 
						((meteorArray.meteor[i].y >= this.y) && (meteorArray.meteor[i].y <= this.y + this.especial[0].getHeight())) ||
						((meteorArray.meteor[i].x >= this.x) && (meteorArray.meteor[i].x <= this.x + this.especial[0].getWidth())) &&
						((meteorArray.meteor[i].y + meteorArray.meteor[i].images[0].getHeight() >= this.y) && (meteorArray.meteor[i].y + meteorArray.meteor[i].images[0].getHeight() <= this.y + this.especial[0].getHeight()))){
					if (!meteorArray.meteor[i].collided) {	
						this.collisionMeteor = true;
						//Esta atribuição é feita para facilitar quando for resetar o meteoro.
						meteorArray.meteor[i].collided = true;
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Desenha o tiro especial utilizando a cor como referência.
	 * @param g
	 */
	public  void paint (Graphics g) {
		//Caso a nave não tenha atirado não faz nada.
		if (!shootOn) {
			return;
		}

		if (this.state == ALIVE) {

			countCollisionDelay();

			update();

			switch (this.especialColor) {
			case 0:
				//COR LARANJA.
				g.drawImage(this.especial[0], this.x, this.y, 0);
				break;

			case 1:
				//COR AZUL CLARO.
				g.drawImage(this.especial[0], this.x, this.y, 0);
				break;

			case 2:
				//AZUL ESCURO.
				g.drawImage(this.especial[0], this.x, this.y, 0);
				break;
			}			

			//Animação de quando especial colidir.
			if (this.collisionAnimation){

				switch (this.especialColor) {
				case 0:
					//COR LARANJA.
					g.drawImage(this.especial[1], this.x, this.y, 0);
					break;

				case 1:
					//COR AZUL CLARO.
					g.drawImage(this.especial[1], this.x, this.y, 0);
					break;

				case 2:
					//AZUL ESCURO.
					g.drawImage(this.especial[1], this.x, this.y, 0);
					break;
				}

				this.especialTime ++;
				if (this.especialTime == 3) {
					this.especialDraw ++;
					this.especialTime = 0;
				}
				if (this.especialDraw == 1) {
					this.collisionAnimation = false;
					this.especialDraw = 0;
					this.especialTime = 0;
				}
			}

			//Condição para finalizar o tiro caso ele não colida.
			if (this.x > Midlet.width) {
				//Se o tiro desaparecer da tela.
				allColorsRestart();
			}
		}else {
			//O Especial morreu.
			switch (this.especialColor) {
			case 0:
				//COR LARANJA.
				switch (this.especialDraw) {
				case 0:
					g.drawImage(this.especial[1], this.x, this.y, 0);
					break;

				case 1:
					g.drawImage(this.especial[2], this.x, this.y, 0);
					break;
				}

				break;

			case 1:
				//COR AZUL CLARO.
				switch (this.especialDraw) {
				case 0:
					g.drawImage(this.especial[1], this.x, this.y, 0);
					break;

				case 1:
					g.drawImage(this.especial[2], this.x, this.y, 0);
					break;
				}

				break;

			case 2:
				//AZUL ESCURO.
				switch (this.especialDraw) {
				case 0:
					g.drawImage(this.especial[1], this.x, this.y, 0);
					break;

				case 1:
					g.drawImage(this.especial[2], this.x, this.y, 0);
					break;
				}

				break;
			}
			this.especialTime ++;
			if (this.especialTime == 3) {
				this.especialDraw ++;
				this.especialTime = 0;
			}
			if (this.especialDraw == 2) {
				this.allColorsRestart();
			}
		}
	}
}
