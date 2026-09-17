import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;


public class SubchiefShoot {
	/** Posição horizontal do Laser*/
	private int x;

	/** Posição vertical do Laser*/
	private int y;

	/** Velocidade do laser*/
	private byte speedX;

	/** Tiro mais básico da nave.*/
	private Image laser;

	/** True = O laser foi diparado pela nave. | False = O laser não foi diparado pela nave*/
	public boolean shootOn;

	/** True = iniciou o processo de colisão. | False = não iniciou o processo de colisão*/
	public boolean collision;

	public SubchiefShoot() {
		try{
			this.laser = Image.createImage("/laser0.png");
		} catch (Exception e){
			e.printStackTrace();
		}
		this.restart();
	}

	/**
	 * Reseta as variáveis do tiro.
	 *
	 */
	public void restart() {
		this.collision = false;
		this.shootOn = false;	
		this.speedX = 4;
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
		this.x = x  - (this.laser.getWidth() / 4);
		this.y = y + (sizey / 2)  - (this.laser.getHeight() / 2) + 2;
	}

	/**
	 * Movimenta o tiro.
	 *
	 */
	public void update() {
		this.x -=this.speedX;
	}

	/**
	 * Resposnável por testar colisão entre o tiro e a nave.
	 * @param airShip
	 * Usada para saber a localização da nave.
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
						((this.y + this.laser.getHeight() >= airShip.y) && (this.y + this.laser.getHeight() <= airShip.y + airShip.naveMiddle.getHeight()))){
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
	 * Desenha o tiro caso ele esteja ativado, ou seja, shootOn = true.
	 * @param g
	 */
	public void paint(Graphics g) {
		//Caso a nave não tenha atirado não faz nada.
		if (!shootOn) {
			return;
		}
		
		if (!this.collision){
			g.drawImage(this.laser, this.x, this.y, 0);

			//Condição para parar o tiro caso ele não colida com nada.
			if (this.x < -this.laser.getWidth()) {
				this.shootOn = false;
			}
		}else {
			//Cancela o tiro.
			this.shootOn = false;
			this.collision = false;
		}
	}
}
