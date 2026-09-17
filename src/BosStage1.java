import java.util.Random;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class BosStage1 {
	/** Altura da área que o subchief pode se movimentar*/
	private final int BACK_GROUND_HEIGHT;

	/** Posição máxima do lado direito da tela.*/
	private final int MAX_RIGHT_POSITION = Midlet.height;

	/** Estado da nave off = a nave não está no jogo.*/
	private final byte OFF = -1;

	/** Etado da nave normal = nave está viva.*/
	private final byte NORMAL = 0;

	/** Etado da nave normal = nave está viva.*/
	private final byte FURY = 1;

	/** Etado da nave normal = nave está viva.*/
	private final byte FRENZY = 2;	

	/** Estado da nave explosão = nave explodiu.*/
	private final byte EXPLOSION = 3;

	/** Número de imagens da animação de explosão.*/
	private final byte EXPLOSION_QUANTITY = 10;

	/** Quantos pontos o BosStage1 vale caso seja destruido.*/
	private final int VALUE = 550;

	/** Posicao da nave no eixo x*/
	public int x;

	/** Posicao da nave no eixo y*/
	public int y;

	/** Todos os tiros do Bos*/
	public BosStage1AllShoots bosStage1AllShoots;

	/** Imagem default da nave*/
	public Image bosStage1; 

	/** Imagem fury da nave*/
	public Image bos1Stage1; 

	/** Imagem frenzy da nave*/
	public Image bos2Stage1; 

	/** Velocidade Horizontal */
	private int speedX;

	/** Velocidade Vertical*/
	private int speedY;

	/** Gerencia o tempo de duração de cada imagem.*/
	private byte timeDraw;
	
	/** O tempo limite de duração de uma imagem na animação*/
	private byte timing;

	/** As imagens do bos.*/
	private byte bosDraw;

	/** Gerencia a mudança da imagem da explosão*/
	private byte drawExplosion;

	/** Posição da explosão no eixo x*/
	private int explosionX;

	/** Posição da explosão no eixo y*/
	private int explosionY;	

	/** Número de vidas da nave.*/
	public byte lives;

	/** Auxiliar usado para contar o tempo de vida do BosStage1.*/
	private int countTime;

	/** Tempo de vida do BosStage1.*/
	private int lifeTime;

	/** O level do tiro*/
	private byte shootLevel;

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

	public BosStage1() {
		try {
			this.bosStage1AllShoots = new BosStage1AllShoots();
			//Carregado imagem da nave.

			this.bosStage1 = Image.createImage("/bos.png");   
			this.bos1Stage1 = Image.createImage("/bos1.png"); 
			this.bos2Stage1 = Image.createImage("/bos2.png"); 			

			//Carregando imagem da explosão.
			for (int i = 0; i < EXPLOSION_QUANTITY; i++) {
				this.explosion[i] = Image.createImage("/explosion" + i + ".png");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		/*O tamanho total da tela - (a altura do cabeçalho + a altura do BosStage1)*/
		BACK_GROUND_HEIGHT = Midlet.height - (StageCount.stage1.getHeight() + this.bosStage1.getHeight());
		random = new Random();
		restart();
	}

	/**
	 * Reseta as variáveis e as classes relacionadas ao BosStage1.
	 *
	 */
	public void restart() {
		this.speedX 	   = 1;
		this.speedY 	   = 1;
		this.timeDraw 	   = 0;
		this.timing	       = 3;
		this.bosDraw	   = 0;
		this.drawExplosion = 0;
		this.lives  	   = 100;
		this.lifeTime      = 0;
		this.countTime 	   = 0;
		this.shootLevel    = 0;
		this.state 		   = OFF;
		this.x = Math.abs(random.nextInt() % 200) + MAX_RIGHT_POSITION;
		this.y = Math.abs(random.nextInt() % BACK_GROUND_HEIGHT) + 30;	
		this.bosStage1AllShoots.restart();
		newEndPoint();
	}

	/**
	 * Ativa o BosStage1 mudando seu estado de OFF para NORMAL.
	 *
	 */
	public void activate() {
		this.state = NORMAL;
	}

	/**
	 * Testa se o BosStage1 está em ação ou não
	 * @return
	 * True = BosStage1 em ação | False = BosStage1 não está em aação.
	 */
	public boolean testActivation() {
		if (this.state != OFF) {
			return true;
		}
		return false;
	}

	/**
	 * Os pontos que o player ganhará depende do tempo gasto para matar o Bosstage1.
	 * @return
	 */
	public int reward() {
		if(this.lifeTime <= 30) {
			return VALUE*3;
		}
		if(this.lifeTime <= 60) {
			return VALUE*2;
		}
		if(this.lifeTime <= 120) {
			return VALUE;
		}
		return VALUE/2;
	}

	/**
	 * Gera um novo ponto para o qual o BosStage1 deve se deslocar.
	 *
	 */
	public void newEndPoint() {
		this.endX = (Midlet.width/2) + (Math.abs(random.nextInt() % (Midlet.width/2 - this.bosStage1.getWidth())));
		/*Ao usar nº random mod BACK_GROUND_HEIGHT iremos ter um resultado entre  zero e tamanho da variável BACK_GROUND_HEIGHT,
		 *assim devemos somar a altura do cabeçalho a este valor, com isso o BosStage1 estará entre os intervalos.*/
		this.endY = Math.abs(random.nextInt() % BACK_GROUND_HEIGHT) + StageCount.stage1.getHeight();
	}

	/**
	 * Movimenta o Bos.
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
	 * Responsável por atualizar a posição do BosStage1.
	 *
	 */
	public final void update(){
		if (this.state != OFF) {
			this.move();
		}
	}

	/**
	 * Muda o estado do BosStage1 para EXPLOSION, ou seja, chama a animação do BosStage1 explodindo.
	 */
	public final void changeToExplosion() {
		this.state = this.EXPLOSION;
	}

	/**
	 * Testa colisão da nave com um meteóro
	 * @param meteorArray | O objeto da classe meteor.
	 * @return | Volta True = Colidiu. | False = não colidiu.
	 */
	public final boolean colideWithMeteor(MeteorArray meteorArray) {
		for (byte i = 0; i < meteorArray.METEOR_QUANTITY; i++) {
			//Utiliza os dois pontos posteriosres do quadrado. Pois caso use um posterior e um ponto do final ocorrerá um atrazo na animação.
			if ((((meteorArray.meteor[i].x >= this.x) && (meteorArray.meteor[i].x <= this.x + this.bosStage1.getWidth())) && 
					((meteorArray.meteor[i].y >= this.y) && (meteorArray.meteor[i].y <= this.y + this.bosStage1.getHeight()))) ||
					(((meteorArray.meteor[i].x /*+ meteorArray.meteor[i].images[0].getWidth()*/ >= this.x) && (meteorArray.meteor[i].x /*+ meteorArray.meteor[i].images[0].getWidth()*/ <= this.x + this.bosStage1.getWidth())) && 
							((meteorArray.meteor[i].y + meteorArray.meteor[i].images[0].getHeight() >= this.y) && (meteorArray.meteor[i].y + meteorArray.meteor[i].images[0].getHeight() <= this.y + this.bosStage1.getHeight())))){
				if (!meteorArray.meteor[i].collided) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Localiza o meteoro que colidiu com a nave e reseta este meteoro.
	 * @param meteorArray
	 * Usado para saber a localização dos meteoros.
	 */
	public void searchMeteorCrashed(MeteorArray meteorArray) {
		for (byte i = 0; i < meteorArray.METEOR_QUANTITY; i++) {
			if ((((meteorArray.meteor[i].x >= this.x) && (meteorArray.meteor[i].x <= this.x + this.bosStage1.getWidth())) && 
					((meteorArray.meteor[i].y >= this.y) && (meteorArray.meteor[i].y <= this.y + this.bosStage1.getHeight()))) ||
					(((meteorArray.meteor[i].x >= this.x) && (meteorArray.meteor[i].x <= this.x + this.bosStage1.getWidth())) && 
							((meteorArray.meteor[i].y + meteorArray.meteor[i].images[0].getHeight()>= this.y) && (meteorArray.meteor[i].y + meteorArray.meteor[i].images[0].getHeight() <= this.y + this.bosStage1.getHeight())))){
				meteorArray.meteor[i].collided = true;
			}
		}	
	}

	/**
	 * Trata a colisão entre a airship e meteoro.
	 * @param meteorArray
	 * Usado para saber a localização dos meteoros.
	 */
	public final void meteorCollision(MeteorArray meteorArray) {
		if (colideWithMeteor(meteorArray)) {
			searchMeteorCrashed(meteorArray);
		}
	}

	/**
	 * Testa se o tiro BosStage1Shoot1 colidiu com a nave ou não. 
	 * Caso tenha colidido é chamada a animação de explosão da nave.
	 * @param airShip
	 * Usado para saber a localização da AirShip.
	 */
	public final void shoot1Collision(AirShip airShip) {
		if(this.bosStage1AllShoots.shoot1ColideWithAirShip(airShip)) {
			//chama a animação de explosão da nave.
			airShip.changeToExplosion();
		}
	}

	/**
	 * Testa se o tiro BosStage1Shoot2 colidiu com a nave ou não. 
	 * Caso tenha colidido é chamada a animação de explosão da nave.
	 * @param airShip
	 * Usado para saber a localização da AirShip.
	 */
	public final void shoot2Collision(AirShip airShip) {
		if (this.bosStage1AllShoots.bosStage1Shoot2.colideWithAirShip(airShip)) {
			airShip.changeToExplosion();
		}
	}

	/**
	 * Desenha a explosão e reseta o BosStage1.
	 * @param g
	 */
	public final void paintExplosion(Graphics g){
		switch (this.drawExplosion) {
		case 0:
			g.drawImage(this.explosion[this.drawExplosion], this.x, this.y, 0);
			//para caso o timeDraw não esteja zerado, ele irá ser zerado em seguida.
			this.timeDraw = (byte)(this.timing - 1);
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
			restart();
		}
	}

	/**
	 * Dispara os tiros do BosStage1.
	 *
	 */
	public final void fire() {
		if (this.state != EXPLOSION) {
			this.bosStage1AllShoots.shootType(this.x, this.y, this.bosStage1.getHeight());
			this.countTime ++;
			if (this.countTime == 36) {
				this.lifeTime ++;
				this.countTime = 0;
			}
		}
	}

	/**
	 * Controla quando o tiro do BosStage1 deve evoluir. Usando a vida do BosStage1 como referência.
	 *
	 */
	public final void shootControl() {
		if ((this.shootLevel == 0)&&(this.lives < 80)) {
			bosStage1AllShoots.changeShootType();
			this.shootLevel++;
		}
		if ((this.shootLevel == 1)&&(this.lives < 70)) {
			bosStage1AllShoots.changeShootType();
			this.shootLevel++;
		}
		if ((this.shootLevel == 2)&&(this.lives < 45)) {
			bosStage1AllShoots.changeShootType();
			this.state = FURY;
			this.shootLevel++;
		}
		if ((this.shootLevel == 3)&&(this.lives < 20)) {
			bosStage1AllShoots.changeShootType();
			this.state = FRENZY;			
			this.shootLevel++;
		}
	}

	/**
	 * Desenha os tiros e o BosStage1.
	 * @param g
	 */
	public final void paint(Graphics g) {
		//Desenha o tiro e movimenta o mesmo.
		this.bosStage1AllShoots.paint(g);

		switch (this.state){
		case 0:
			g.drawImage(bosStage1, this.x, this.y, 0);
			break;
		case 1:
			g.drawImage(bos1Stage1, this.x, this.y, 0);
			break;
		case 2:
			switch (this.bosDraw) {
			case 0:
				g.drawImage(bosStage1, this.x, this.y, 0);
				break;
			case 1:
				g.drawImage(bos1Stage1, this.x, this.y, 0);
				break;

			case 2:
				g.drawImage(bos2Stage1, this.x, this.y, 0);
				break;				
			}
			//Controla a animação. Marcador de tempo.
			if (this.bosDraw < 3) {
				this.timeDraw++;
				if (this.timeDraw > 3) {
					this.bosDraw++;
					if (this.bosDraw > 2) {
						this.bosDraw = 0;
					}
					this.timeDraw = 0;
				}
			}
			break;			
		case 3:
			paintExplosion(g);
			break;
		}

	}
}
