import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class BosStage1Shoot2 {
	
	/** Número de imagens da animação do especial.*/
	private final byte ESPECIAL_QUANTITY = 7;
	
	/** Posição horizontal do especial*/
	private int x;

	/** Posição vertical do especial*/
	private int y;
	
	/** Velocidade do especial*/
	private byte speedX;
	
	/** Vetor que irá conter as imagens do especial.*/
	private Image[] especial = new Image[ESPECIAL_QUANTITY];

	/** True = O especial foi diparado pelo BosStage1. | False = O especial não foi diparado pelo BosStage1*/
	public boolean shootOn;

	/** True = iniciou o processo de colisão. | False = não iniciou o processo de colisão*/
	public boolean collision;

	/** Controla a animação do especial*/
	private byte especialDraw;

	/** Controla o tempo entre cada animação do especial.*/
	private byte especialTime;
	
	/** O tempo limite de duração de uma imagem na animação*/
	private byte timing;

	public BosStage1Shoot2() {
		try{
			//Carregando imagem do especial.
			for (int i = 0; i < ESPECIAL_QUANTITY; i++) {
				this.especial[i] = Image.createImage("/esp" + i + ".png");
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		this.restart();
	}

	public void restart() {
		this.speedX = 10;
		this.collision = false;
		this.shootOn = false;	
		this.especialDraw  = 0;
		this.especialTime  = 0;
		this.timing		   = 3;	
	}

	/**
	 * Responsável por ativar o processo de animação do especial.
	 * @param x
	 * 		posição  horizontal na qual o BosStage1 atirou.
	 * @param y
	 * 		posição  vertical na qual o BosStage1 atirou.
	 * @param sizey
	 * 		altura da imagem do BosStage1.
	 */
	public final void shoot(int x, int y, int sizey) {
		if (this.shootOn) {
			return;
		}
		this.shootOn = true;
		this.x = x  - (this.especial[0].getWidth() / 4);
		this.y = y + (sizey)  - (this.especial[0].getHeight() / 2);
	}

	/**
	 * Resposnável por testar colisão entre o especial e a nave.
	 * @param airShip
	 * Nave usada para a comparação.
	 * @return
	 * True = Colidiu | False = Não colidiu.
	 */	
	public final boolean colideWithAirShip(AirShip airShip){
		//So verifica colisão após o especial ter sido atirado.
		if (this.especialDraw == 6) {
		//Se o laser estiver em andamento.
		if(this.shootOn){
			//Se a nave estiver normal testa.
			if (airShip.state == 0) {
				//Compara se o quadrado imaginário em volta da nave colidiu ou não com o quadrado imaginário em volta do meteoro.
				if (((this.x >= airShip.x) && (this.x <= airShip.x + airShip.naveMiddle.getWidth())) && 
						((this.y >= airShip.y) && (this.y <= airShip.y + airShip.naveMiddle.getHeight())) ||
						((this.x >= airShip.x) && (this.x <= airShip.x + airShip.naveMiddle.getWidth())) &&
						((this.y + this.especial[6].getHeight() >= airShip.y) && (this.y + this.especial[6].getHeight() <= airShip.y + airShip.naveMiddle.getHeight()))){
					//Não tem necessidade de entrar no If caso o meteoro já tenha colidido com o bçaster e a animação esteja em andamento.
					if (!this.collision){
						this.x = airShip.x;
						this.y = airShip.y;
						this.collision = true;
					}
				}
			}
		}
		}
		return this.collision;
	}

	/**
	 * Desenha o especial.
	 * @param g
	 */
	public void paint(Graphics g) {
		//Caso a nave não tenha atirado não faz nada.
		if (!shootOn) {
			return;
		}
		if (!this.collision){
			switch (this.especialDraw) {
			case 0:
				g.drawImage(this.especial[0], this.x, this.y, 0);
				break;
			case 1:
				g.drawImage(this.especial[1], this.x, this.y, 0);						
				break;
			case 2:
				g.drawImage(this.especial[2], this.x, this.y, 0);				
				break;			
			case 3:
				g.drawImage(this.especial[3], this.x, this.y, 0);				
				break;		
			case 4:
				g.drawImage(this.especial[4], this.x, this.y, 0);				
				break;		
			case 5:
				g.drawImage(this.especial[5], this.x, this.y, 0);				
				break;		
			case 6:
				g.drawImage(this.especial[6], this.x, this.y, 0);	
				this.x -= this.speedX;
				break;		
			}

			if (this.especialDraw < 6) {
				this.especialTime++;
				if (this.especialTime == this.timing) {
					this.especialDraw++;
					this.especialTime = 0;
				}
			}

			if (this.x < -especial[6].getWidth()) {
				this.shootOn = false;
				this.especialDraw = 0;
				this.especialTime = 0;
			}		
			
		}else {
			this.shootOn = false;
			this.collision = false;
			this.especialDraw = 0;
			this.especialTime = 0;
		}
	}
}
