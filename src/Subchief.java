import java.util.Random;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class Subchief {
	/** Altura da área que o subchief pode se movimentar*/
	private final int BACK_GROUND_HEIGHT;
	
	/** Posição máxima do lado direito da tela.*/
	private final int MAX_RIGHT_POSITION = Midlet.height;

	/** Etado da nave normal = nave está viva.*/
	private final byte NORMAL = 0;

	/** Estado da nave explosão = nave explodiu.*/
	private final byte EXPLOSION = 1;

	/** Estado da nave off = a nave não está no jogo.*/
	private final byte OFF = 2;

	/** Número de imagens da animação de explosão.*/
	private final byte EXPLOSION_QUANTITY = 10;

	/** Quantidade de Lasers*/
	public final byte LASER_QUANTITY = 3;

	/** Quantos pontos o subchief vale caso seja destruido.*/
	private final int VALUE = 300;

	/** Posicao da nave no eixo x*/
	public int x;

	/** Posicao da nave no eixo y*/
	public int y;

	/** Imagem default da nave*/
	public Image naveMiddle; 

	/** Nova imagem a ser desenhada a depender do tipo de colisão*/
	public char newDraw;

	/** Velocidade Horizontal */
	private int speedX;

	/** Velocidade Vertical*/
	private int speedY;

	/** Gerencia o tempo de duração de cada imagem.*/
	private byte timeDraw;
	
	/** O tempo limite de duração de uma imagem na animação*/
	private byte timing;

	/** Gerencia a mudança da imagem da explosão*/
	private byte drawExplosion;

	/** Posição da explosão no eixo x*/
	private int explosionX;

	/** Posição da explosão no eixo y*/
	private int explosionY;	

	/** Tempo de duração do restart.*/
	private byte shootTime;

	/** Vetor que irá conter os Lasers do subchief*/
	public SubchiefShoot[] subchiefShoots = new SubchiefShoot[LASER_QUANTITY];

	/** Número de vidas da nave.*/
	public byte lives;

	/** Tempo de vida do subchief*/
	private int lifeTime;

	/** Ponto X final que a nave subchief deve chegar após isso é gerado um novo ponto final até que ela seja destruida.*/
	private int endX;

	/** Ponto Y final que a nave subchief deve chegar após isso é gerado um novo ponto final até que ela seja destruida.*/
	private int endY;

	/** Randomiza o destino do enime.*/
	private final Random random;

	/** Estado da nave| state = normal ou state = explosion*/
	public byte state;

	/** Vetor que irá conter as imagens da explosão.*/
	private Image[] explosion = new Image[EXPLOSION_QUANTITY];

	public Subchief() {
		try {
			for (byte i = 0; i < LASER_QUANTITY; i++) {
				this.subchiefShoots[i] = new SubchiefShoot();
			}
			//Carregado imagem da nave.
			this.naveMiddle = Image.createImage("/subchief.png");     

			//Carregando imagem da explosão.
			for (int i = 0; i < EXPLOSION_QUANTITY; i++) {
				this.explosion[i] = Image.createImage("/explosion" + i + ".png");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		/*O tamanho total da tela - (a altura do cabeçalho + a altura do subchief)*/
		BACK_GROUND_HEIGHT = Midlet.height - (StageCount.stage1.getHeight() + this.naveMiddle.getHeight());
		random = new Random();
		restart();
	}
	
	/**
	 * Reseta as variáveis do subchief.
	 *
	 */
	public void restart() {
		this.speedX = 1;
		this.speedY = 1;
		this.timeDraw = 0;
		this.timing = 3;
		this.drawExplosion = 0;
		this.lives = 30;
		this.lifeTime = 0;
		this.state = OFF;
		//Posição inicial.
		this.x =  Math.abs(random.nextInt() % 200) + MAX_RIGHT_POSITION;
		/*Ao usar nº random mod BACK_GROUND_HEIGHT iremos ter um resultado entre  zero e tamanho da variável BACK_GROUND_HEIGHT,
		 *assim devemos somar a altura do cabeçalho a este valor, com isso o subchief estará entre os intervalos.*/
		this.y = Math.abs(random.nextInt() % BACK_GROUND_HEIGHT) + StageCount.stage1.getHeight();	
		this.shootRestart();
		newEndPoint();
	}

	/**
	 * Ativa o subchief mudando seu estado de OFF para NORMAL.
	 *
	 */
	public void activate() {
		this.state = NORMAL;
	}

	/**
	 * Testa se o Subchief está em ação ou não
	 * @return
	 * True = Subchief em ação | False = Subchief não está em ação.
	 */
	public boolean testActivation() {
		if (this.state != OFF) {
			return true;
		}
		return false;
	}

	/**
	 * Os pontos que o player ganhará depende do tempo gasto para matar o subchief.
	 * @return
	 */
	public int reward() {
		if(this.lifeTime <= 30) {
			return VALUE*2;
		}
		if(this.lifeTime <= 60) {
			return VALUE;
		}
		return VALUE/2;
	}

	/**
	 * Gera um novo ponto para o qual o subchief deve se deslocar.
	 *
	 */
	public void newEndPoint() {
		this.endX = Midlet.width/2 + (Math.abs(random.nextInt() % (Midlet.width/2 - this.naveMiddle.getWidth())));
		this.endY = Math.abs(random.nextInt() % BACK_GROUND_HEIGHT) + StageCount.stage1.getHeight();
	}

	/**
	 * Movimenta a nave.
	 *
	 */
	public final void move (){
		if (this.x != this.endX ) {
			if (this.x < this.endX) {
				this.x += this.speedX;
			} else{
				this.x -= this.speedX;
			}
		}
		if (this.y != this.endY) {
			if (this.y < this.endY) {
				this.y += this.speedY;
			} else{
				this.y -= this.speedY;
			}
		}
		if ((this.x == this.endX) && (this.y == this.endY)) {
			newEndPoint();
		}
	}

	/**
	 * Responsável por atualizar a posição do subchief.
	 *
	 */
	public final void update(){
		if (this.state == NORMAL) {
			this.move();
		}
	}

	/**
	 * Muda o estado do subchief para EXPLOSION, ou seja, chama a animação do subchief explodindo.
	 */
	public final void changeToExplosion() {
		this.state = this.EXPLOSION;
	}

	/**
	 * Testa a colisão do laser do subchief com a AirShip.
	 * @param airShip
	 * Usado para saber a localização da mesma.
	 */
	public final void laserCollision(AirShip airShip) {
		if(this.colideWithAirShip(airShip)) {
			airShip.changeToExplosion();
		}
	}


	/**
	 * Desenha a explosão e reseta o subchief.
	 * @param g
	 */
	public final void paintExplosion(Graphics g){
		switch (this.drawExplosion) {
		case 0:
			g.drawImage(this.explosion[this.drawExplosion], this.x, this.y, 0);
			this.explosionX = this.x;
			this.explosionY = this.y; 
			this.explosionX -= 1;
			break;
		case 1:
			g.drawImage(this.explosion[this.drawExplosion], this.explosionX, this.explosionY, 0);
			this.explosionX -= 1;
			break;
		case 2:
			g.drawImage(this.explosion[this.drawExplosion], this.explosionX, this.explosionY, 0);
			this.explosionX -= 1;
			break;
		case 3:
			g.drawImage(this.explosion[this.drawExplosion], this.explosionX, this.explosionY, 0);
			this.explosionX--;
			break;
		case 4:
			g.drawImage(this.explosion[this.drawExplosion], this.explosionX, this.explosionY, 0);
			this.explosionX -= 2;
			break;
		case 5:
			g.drawImage(this.explosion[this.drawExplosion], this.explosionX, this.explosionY, 0);
			this.explosionX -= 2;			
			break;
		case 6:
			g.drawImage(this.explosion[this.drawExplosion], this.explosionX, this.explosionY, 0);
			this.explosionX -= 3;
			break;
		case 7:
			g.drawImage(this.explosion[this.drawExplosion], this.explosionX, this.explosionY, 0);
			this.explosionX -= 3;
			break;
		case 8:
			g.drawImage(this.explosion[this.drawExplosion], this.explosionX, this.explosionY, 0);
			this.explosionX -= 5;
			break;
		case 9:
			g.drawImage(this.explosion[this.drawExplosion], this.explosionX, this.explosionY, 0);
			this.explosionX -= 5;
			break;
		}

		/* Usado para controlar tempo entre cada animação*/
		this.timeDraw += 1;
		if (this.timeDraw == this.timing){	
			this.drawExplosion += 1;
			this.timeDraw = 0;
		}

		/* Fim da Explosão*/
		if (this.drawExplosion == 10){
			this.shootTime = 0;
			restart();
		}
	}

	/**
	 * Dispara os tiros do subchief de acordo com um marcador de tempo.
	 *
	 */
	public final void fire() {
		if (this.state == NORMAL) {
			this.shootTime += 1;
			if (this.shootTime % 12 == 0) {	
				this.shoot(this.x, this.y, this.naveMiddle.getHeight());
			}
			if (this.shootTime == 36) {
				this.lifeTime ++;
				this.shootTime = 0;
			}
		}
	}

	/**
	 * Desenha o subchief baseado em seu Estato e desenha o tiro da mesma.
	 * @param g
	 */
	public final void paint(Graphics g) {

		//Movimenta o tiro.
		this.shootUpdate();

		//Desenha o tiro. 
		this.shootPaint(g);

		switch (this.state){
		case 0:
			//NORMAL.
			g.drawImage(naveMiddle, this.x, this.y, 0);
			break;
		case 1:
			//EXPLODINDO.
			paintExplosion(g);
			break;
		}

	}
	
	/* Início *Métodos relacionados com o tiro do subchief.*/
	
	/**
	 * Aciona o restart de cada tiro do vetor.
	 */
	public void shootRestart() {
		for (byte i = 0; i < LASER_QUANTITY; i++) {
			this.subchiefShoots[i].restart();	
		}
	}
	
	/**
	 * Atira os lasers do array.
	 * @param x
	 * 		posição x da nave.
	 * @param y
	 * 		posição y da nave.
	 * @param sizey
	 * 		altura da imagem da nave.
	 */
	public void shoot(int x, int y, int sizey) {
		for (byte i = 0; i < LASER_QUANTITY; i++) {
			if (!this.subchiefShoots[i].shootOn) {
				this.subchiefShoots[i].shoot(x, y, sizey);
				break;
			}
		}	
	}
	
	/**
	 * Movimenta os lasers do array que estiverem ativados.
	 *
	 */
	public void shootUpdate() {
		for (byte i = 0; i < LASER_QUANTITY; i++) {
			if (this.subchiefShoots[i].shootOn) {
				this.subchiefShoots[i].update();	
			}
		}
	}
	
	/**
	 * Testa colisão dos laser que estiverem ativados com a nave.
	 * @param airShip
	 * Usado para saber a localização da mesma.
	 * @return
	 * True = Colidiu | False = Não colidiu. 
	 */
	public boolean colideWithAirShip(AirShip airShip) {
		for (byte i = 0; i < LASER_QUANTITY; i++) {
			if(this.subchiefShoots[i].shootOn) {
				if (this.subchiefShoots[i].colideWithAirShip(airShip)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Desenha os lasers do array.
	 * @param g
	 */
	public void shootPaint(Graphics g) {
		for (byte i = 0; i < LASER_QUANTITY; i++) {
			this.subchiefShoots[i].paint(g);
		}
	}
		
	/* Fim *Métodos relacionados com o tiro do subchief.*/
}
