import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class BosStage1Shoot1 {
	
	/** Número de imagens da animação do tiro.*/
	private final byte SHOOT_QUANTITY = 4;
	
	/** Posição horizontal do tiro*/
	private int x;

	/** Posição vertical do tiro*/
	private int y;
	
	/** Velocidade do especial*/
	private byte speedX;

	/** O tiro está no modo mais básico.*/
	private final byte LEVEL0 = 0;

	/** O tiro deixou de está no modo mais básico. Sua animação é modificada*/
	private final byte LEVEL1 = 1;
	
	/** Vetor que irá conter as imagens do tiro.*/
	private Image[] shoot = new Image[SHOOT_QUANTITY];

	/** O nível do tiro | LEVEL0 ou LEVEL1*/
	private byte shootLevel;

	/** True = O tiro foi diparado pelo BosStage1. | False = O tiro não foi diparado pelo BosStage1*/
	public boolean shootOn;

	/** True = Iniciou o processo de colisão. | False = Não iniciou o processo de colisão*/
	public boolean collision;

	/** Controla a animação do tiro LEVEL1*/
	private byte shootDraw;

	/** Controla o tempo entre cada animação do tiro LEVEL1.*/
	private byte shootTime;
	
	/** O tempo limite de duração de uma imagem na animação*/
	private byte timing;

	public BosStage1Shoot1() {
		try{
			//Carregando imagem do tiro.
			for (int i = 0; i < SHOOT_QUANTITY; i++) {
				this.shoot[i] = Image.createImage("/shoot" + i + ".png");
			}

		} catch (Exception e){
			e.printStackTrace();
		}
		this.restart();
	}

	/**
	 * Reseta o tiro.
	 *
	 */
	public void restart() {
		this.shootLevel = LEVEL0;
		this.collision = false;
		this.shootOn = false;	
		this.shootTime  = 0;
		this.shootDraw  = 0;
		this.timing     = 2;
		this.speedX     = 4;
	}

	/**
	 * Responsável por ativar o processo de animação do tiro
	 * @param x
	 * 		posição  horizontal na qual a nave atirou.
	 * @param y
	 * 		posição  vertical na qual a nave atirou.
	 * @param sizey
	 * 		altura da imagem do Bos.
	 */
	public final void shoot(int x, int y, int sizey) {
		if (this.shootOn) {
			return;
		}
		this.shootOn = true;
		this.x = x  - (this.shoot[0].getWidth() / 4);
		this.y = y + (sizey / 4)  - (this.shoot[0].getHeight() / 2);
	}

	/**
	 * Movimenta o tiro.
	 *
	 */
	public void update() {
		this.x -= this.speedX;
	}

	/**
	 * Evolui o tiro.
	 * PS: animação avançada.
	 *
	 */
	public void levelUp() {
		this.shootLevel = LEVEL1;
	}

	/**
	 * Resposnável por testar colisão entre o tiro e a nave.
	 * @param airShip
	 * Nave usada para a comparação.
	 * @return
	 * True = Colidiu | False = Não colidiu.
	 */	
	public final boolean colideWithAirShip(AirShip airShip){
		//Se o laser estiver em andamento.
		if(this.shootOn){
			//Se a nave estiver normal testa.
			if (airShip.state == 0) {
				//Compara se o quadrado imaginário em volta da nave colidiu ou não com o quadrado imaginário em volta do meteoro.
				if (((this.x >= airShip.x) && (this.x <= airShip.x + airShip.naveMiddle.getWidth())) && 
						((this.y >= airShip.y) && (this.y <= airShip.y + airShip.naveMiddle.getHeight())) ||
						((this.x >= airShip.x) && (this.x <= airShip.x + airShip.naveMiddle.getWidth())) &&
						((this.y + this.shoot[3].getHeight() >= airShip.y) && (this.y + this.shoot[3].getHeight() <= airShip.y + airShip.naveMiddle.getHeight()))){
					//Não tem necessidade de entrar no If caso o meteoro já tenha colidido com o bçaster e a animação esteja em andamento.
					if (!this.collision){
						this.x = airShip.x;
						this.y = airShip.y;
						this.collision = true;
					}
				}
			}
		}
		return this.collision;
	}

	/**
	 * Desenha o tiro de acordo com o seu nível.
	 * @param g
	 */
	public void paint(Graphics g) {
		//Caso o BosStage1 não tenha atirado não faz nada.
		if (!shootOn) {
			return;
		}
		if (!this.collision){
			if (this.shootLevel == 0) {
				//Level 0.
				g.drawImage(this.shoot[0], this.x, this.y, 0);

				if (this.x < -1 * this.shoot[0].getWidth()) {
					this.shootOn = false;
				}

			} else {
				//Level 1.
				switch (this.shootDraw) {
				case 0:
					g.drawImage(this.shoot[0], this.x, this.y, 0);
					break;
				case 1:
					g.drawImage(this.shoot[1], this.x, this.y, 0);						
					break;
				case 2:
					g.drawImage(this.shoot[2], this.x, this.y, 0);				
					break;		
				case 3:
					g.drawImage(this.shoot[3], this.x, this.y, 0);				
					break;	
				}

				//Controla a animação. Marcador de tempo.
				if (this.shootDraw < 2) {
					this.shootTime++;
					if (this.shootTime == this.timing) {
						this.shootDraw++;
						this.shootTime = 0;
					}
				}

				//Condição para finalizar o tiro caso ele não colida.
				if (this.x < -1 * this.shoot[0].getWidth()) {
					//Se o tiro desaparecer da tela.
					this.shootOn = false;
					this.shootTime = 0;
					this.shootDraw = 0;
				}			
			}

		} else{
			//O tiro colidiu.
			this.shootOn = false;
			this.collision = false;
			this.shootTime = 0;
			this.shootDraw = 0;
		}
	}
}
