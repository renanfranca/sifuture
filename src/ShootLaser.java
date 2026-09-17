/*
 Tiro básico da AirShip.
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class ShootLaser {
	
	/** Número de imagens da animação do laser.*/
	private final byte LASER_QUANTITY = 4;
	
	/** Posição horizontal do Laser*/
	private int x;

	/** Posição vertical do Laser*/
	private int y;

	/** Velocidade do laser*/
	private byte speedX;

	/** O tiro laser está com a animação mais básica.*/
	private final byte LEVEL0 = 0;

	/** O tiro laser deixou de está no modo mais básico. Sua animação é modificada*/
	private final byte LEVEL1 = 1;
	
	/** Vetor que irá conter as imagens do laser.*/
	private Image[] laser = new Image[LASER_QUANTITY];

	/** O nível do laser | LEVEL0 ou LEVEL1*/
	private byte laserLevel;

	/** True = O laser foi diparado pela nave. | False = O laser não foi diparado pela nave*/
	public boolean shootOn;

	/** True = iniciou o processo de colisão. | False = não iniciou o processo de colisão*/
	public boolean collision;

	/** Controla a animação do laser LEVEL1*/
	private byte laserDraw;

	/** Controla o tempo entre cada animação do laser LEVEL1.*/
	private byte laserTime;
	
	/** O tempo limite de duração de uma imagem na animação*/
	private byte timing;

	public ShootLaser() {
		try{
			//Carregando imagem do especial.
			for (int i = 0; i < LASER_QUANTITY; i++) {
				this.laser[i] = Image.createImage("/laser0" + i + ".png");
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		restart();
	}

	/**
	 * Reseta o tiro.
	 *
	 */
	public void restart() {
		this.laserLevel = LEVEL0;
		this.collision  = false;
		this.shootOn 	= false;	
		this.laserTime  = 0;
		this.laserDraw  = 0;
		this.timing		= 2;
		this.speedX 	= 5;
	}

	/**
	 * Responsável por ativar o processo de animação do laser.
	 * @param x
	 * 		posição  horizontal na qual a nave atirou o laser.
	 * @param y
	 * 		posição  vertical na qual a nave atirou o laser.
	 * @param sizey
	 * 		altura da imagem da nave.
	 */
	public final void shoot(int x, int y, int sizey) {
		if (this.shootOn) {
			return;
		}
		this.shootOn = true;
		this.x = x + (this.laser[0].getWidth()/2);
		this.y = y + (sizey / 2) - 2;
	}

	/**
	 * Movimenta o tiro.
	 *
	 */
	public void update() {
		this.x += this.speedX;
	}

	/**
	 * Evolui o laser.
	 * PS: animação avançada.
	 *
	 */
	public void levelUp() {
		this.laserLevel = LEVEL1;
	}

	/**
	 * Regride o laser para o LEVEL0
	 * PS: animação mais simples.
	 *
	 */
	public void levelDown() {
		this.laserLevel = LEVEL0;
	}

	/**
	 * Testa se ocorreu a colisão entre o laser e o subchief
	 * @param subchief
	 * Saber a localização do mesmo.
	 * @return
	 * True = colidiu | False = não colidiu.
	 */
	public final boolean colideWithBosStage1(BosStage1 bosStage1) {
		if(this.shootOn){
			//Testa se o bosStage1 esteja no estado normal.
			if (bosStage1.state != 3) {
				//Compara se o quadrado imaginário em volta da nave colidiu ou não com o quadrado imaginário em volta do meteoro.
				if (((this.x + laser[0].getWidth() >= bosStage1.x) && (this.x + laser[0].getWidth() <= bosStage1.x + bosStage1.bosStage1.getWidth())) && 
						((this.y >= bosStage1.y) && (this.y <= bosStage1.y + bosStage1.bosStage1.getHeight())) ||
						((this.x + laser[0].getWidth() >= bosStage1.x) && (this.x + laser[0].getWidth() <= bosStage1.x + bosStage1.bosStage1.getWidth())) &&
						((this.y + this.laser[0].getHeight() >= bosStage1.y) && (this.y + this.laser[0].getHeight() <= bosStage1.y + bosStage1.bosStage1.getHeight()))){
					//Não tem necessidade de entrar no If caso o meteoro já tenha colidido com o bçaster e a animação esteja em andamento.
					if (!this.collision){
						this.x = bosStage1.x;
						this.y = bosStage1.y;
						this.collision = true;
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Decrementa em um a vida do bosStage1 caso o laser tenha colidido com o mesmo.
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
	 * Testa se ocorreu a colisão entre o laser e o subchief
	 * @param subchief
	 * Saber a localização do mesmo.
	 * @return
	 * True = colidiu | False = não colidiu.
	 */
	public final boolean colideWithSubchief(Subchief subchief) {
		if(this.shootOn){
			//Testa se o subchief esteja no estado normal.
			if (subchief.state == 0) {
				//Compara se o quadrado imaginário em volta da nave colidiu ou não com o quadrado imaginário em volta do meteoro.
				if (((this.x + laser[0].getWidth() >= subchief.x) && (this.x + laser[0].getWidth() <= subchief.x + subchief.naveMiddle.getWidth())) && 
						((this.y >= subchief.y) && (this.y <= subchief.y + subchief.naveMiddle.getHeight())) ||
						((this.x + laser[0].getWidth() >= subchief.x) && (this.x + laser[0].getWidth() <= subchief.x + subchief.naveMiddle.getWidth())) &&
						((this.y + this.laser[0].getHeight() >= subchief.y) && (this.y + this.laser[0].getHeight() <= subchief.y + subchief.naveMiddle.getHeight()))){
					//Não tem necessidade de entrar no If caso o meteoro já tenha colidido com o bçaster e a animação esteja em andamento.
					if (!this.collision){
						this.x = subchief.x;
						this.y = subchief.y;
						this.collision = true;
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Decrementa em um a vida do subchief caso o laser tenha colidido com o mesmo.
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
	 * Testa o laser colidiu com algum meteoro.
	 * @param meteorArray
	 * Saber a localização de todos os meteoros.
	 * @return
	 * True = Colidiu com algum meteoro. | False = Não colidiu com nenhum meteoro.
	 */
	public final boolean colideWithMeteor(MeteorArray meteorArray){
		//Nem é preciso testar caso o Blaster não tenha sido lançado.
		if(this.shootOn){
			//Compara se o quadrado imaginário em volta da nave colidiu ou não com o quadrado imaginário em volta do meteoro.
			for (byte i = 0; i < meteorArray.METEOR_QUANTITY; i++) {
				if (((this.x + this.laser[0].getWidth() >= meteorArray.meteor[i].x ) && (this.x + this.laser[0].getWidth() <= meteorArray.meteor[i].x + meteorArray.meteor[i].images[0].getWidth())) && 
						((this.y >= meteorArray.meteor[i].y) && (this.y <= meteorArray.meteor[i].y + meteorArray.meteor[i].images[0].getHeight())) ||
						((this.x + this.laser[0].getWidth() >= meteorArray.meteor[i].x) && (this.x <= meteorArray.meteor[i].x + meteorArray.meteor[i].images[0].getWidth())) &&
						((this.y + this.laser[0].getHeight() >= meteorArray.meteor[i].y) && (this.y + this.laser[0].getHeight() <= meteorArray.meteor[i].y + meteorArray.meteor[i].images[0].getHeight()))){
					//Não tem necessidade de entrar no If caso o meteoro já tenha colidido com o bçaster e a animação esteja em andamento.
					if (!this.collision){
						if (!meteorArray.meteor[i].collided) {	
							//Esta atribuição é feita para a animação do laser colidido fique bem em cima do meteoro.
							this.x = meteorArray.meteor[i].x;
							this.y = meteorArray.meteor[i].y;
							this.collision = true;
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * Procura qual o foi o meteoro que colidiu para poder resetar o mesmo.
	 * @param meteorArray
	 * vasculhar qual meteoro foi o que colidiu.
	 */
	public void searchMeteorCreashed(MeteorArray meteorArray) {
		for (byte i = 0; i < meteorArray.METEOR_QUANTITY; i++) {
			if((meteorArray.meteor[i].x == this.x) && (meteorArray.meteor[i].y) == this.y) {
				//Marca o meteoro que colidiu.
				meteorArray.meteor[i].collided = true;
			}
		}
	}

	/**
	 * Desenha o laser de acordo com o seu nível.
	 * @param g
	 */
	public void paint(Graphics g) {
		//Caso a nave não tenha atirado não faz nada.
		if (!shootOn) {
			return;
		}
		if (!this.collision){
			if (this.laserLevel == 0) {
				//Level 0.
				g.drawImage(this.laser[0], this.x, this.y, 0);

				if (this.x > Midlet.width) {
					this.shootOn = false;
				}

			} else {
				//Level 1.
				switch (this.laserDraw) {
				case 0:
					g.drawImage(this.laser[0], this.x, this.y, 0);
					break;
				case 1:
					g.drawImage(this.laser[1], this.x, this.y, 0);						
					break;
				case 2:
					g.drawImage(this.laser[2], this.x, this.y, 0);				
					break;					
				}

				//Controla a animação. Marcador de tempo.
				if (this.laserDraw < 2) {
					this.laserTime++;
					if (this.laserTime == this.timing) {
						this.laserDraw++;
						this.laserTime = 0;
					}
				}

				//Condição para finalizar o tiro caso ele não colida.
				if (this.x > Midlet.width) {
					//Se o tiro desaparecer da tela.
					this.shootOn = false;
					this.laserTime = 0;
					this.laserDraw = 0;
				}			
			}

		} else{
			//O Laser colidiu.
			g.drawImage(this.laser[3], this.x + 5, this.y, 0);
			this.shootOn = false;
			this.collision = false;
			this.laserTime = 0;
			this.laserDraw = 0;
		}
	}
}
