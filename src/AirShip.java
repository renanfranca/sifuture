import java.io.IOException;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class AirShip {
	/** Limite a direita que a nave pode chegar.*/
	private final int MAX_RIGHT; 
	
	/** Limite a direita que a nave pode chegar.*/
	private final int MAX_LEFT; 
	
	/** Limite a direita que a nave pode chegar.*/
	private final int MAX_UP; 
	
	/** Limite a direita que a nave pode chegar.*/
	private final int MAX_DOWN; 
	
	/** Posicao da nave no eixo x*/
	public int x;

	/** Posicao da nave no eixo y*/
	public int y;

	/** Posição inicial da nave no eixo y.*/
	private final int yStart;

	/** valor 1*/
	private final byte m = 1;

	/** valor 2*/
	private final byte j = 2;

	/** Etado da nave normal = nave está viva.*/
	private final byte NORMAL = 0;

	/** Estado da nave explosão = nave explodiu.*/
	private final byte EXPLOSION = 1;

	/** Estado da nave restart = após a nave explodir.*/
	private final byte RESTART = 2;

	/** Número de imagens da animação de explosão.*/
	private final byte EXPLOSION_QUANTITY = 10;

	/** Imagem default da nave*/
	public Image naveMiddle;

	/** Imagem nave para direita*/
	private Image naveJetsMiddle;   

	/** Nova imagem a ser desenhada a depender do tipo de colisão*/
	public char newDraw;

	/** Imagem da nave a ser chamada */
	private byte drawAirship;

	/** Velocidade Horizontal */
	private int speedX;

	/** Velocidade Vertical*/
	private int speedY;

	/** Verifica se a tecla UP foi pressionado ou não*/
	private boolean up;

	/** Verifica se a tecla Down foi pressionado ou não*/
	private boolean down;

	/** Verifica se a tecla Right foi pressionado ou não*/
	private boolean right;

	/** Verifica se a tecla Left foi pressionado ou não*/
	private boolean left;

	/** Caso Up e Down sejam True, verifica qual foi a última a ser pressionada*/
	private char lastPressedY;

	/** Caso Up e Down sejam True, verifica qual foi a última a ser pressionada*/
	private char lastPressedX;

	/** True = Ocorreu uma colisão. | False = Não ocorreu colisão.*/
	private boolean collision;

	/** Gerencia o tempo de duração de cada imagem.*/
	private byte timeDraw;

	/** O tempo limite de duração de uma imagem na animação*/
	private byte timing;

	/** Gerencia a mudança da imagem da explosão*/
	private byte drawExplosion;

	/** Gerencia o pisca pisca do restart*/
	private byte drawRestart;

	/** Posição da explosão no eixo x*/
	private int explosionX;

	/** Posição da explosão no eixo y*/
	private int explosionY;	

	/** Tempo de duração do restart.*/
	private byte restartTime;

	/** Todos os tipos de tiuro da nave.*/
	public AirShipAllShoots allShoots;

	/** Número de vidas da nave.*/
	public int lives;

	/** Estado da nave| state = normal ou state = explosion*/
	public byte state;

	/** Imagem do Especial quando ele estiver ativado.*/
	public Image especial;

	/** Vetor que irá conter as imagens da explosão.*/
	private Image[] explosion = new Image[EXPLOSION_QUANTITY];

	/**
	 * 
	 * @param cor
	 * @throws IOException
	 */
	public AirShip(){		
		try{
			//Carregado imagem da nave.
			this.allShoots = new AirShipAllShoots();
			this.naveMiddle = Image.createImage("/Middle.png");     
			this.naveJetsMiddle = Image.createImage("/Middle2.png");
			//Simbolo do especial da nave.
			this.especial = Image.createImage("/especialActivated1.png");

			//Carregando imagem da explosão.
			for (int i = 0; i < EXPLOSION_QUANTITY; i++) {
			
				this.explosion[i] = Image.createImage("/explosion" + i + ".png");
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		MAX_RIGHT = Midlet.width - this.naveJetsMiddle.getWidth();
		MAX_LEFT  = 0;
		MAX_UP	  = StageCount.stage1.getHeight();
		MAX_DOWN  = Midlet.height - this.naveJetsMiddle.getHeight();
		this.yStart = (Midlet.height / 2) - this.naveJetsMiddle.getHeight() / 2;
		restart();
	}

	public void restart() {
		this.newDraw   = 'x';
		this.drawAirship = m;
		this.speedX    =  5;
		this.speedY    =  5;
		this.up        = false;
		this.down      = false;
		this.right     = false;
		this.left      = false;
		this.lastPressedX = 'x';
		this.lastPressedY = 'y';
		this.timeDraw = 0;
		this.timing   = 3;
		this.drawExplosion = 0;
		this.drawRestart = 0;
		this.collision = false;
		this.lives = 3;
		this.state = RESTART;
		this.x = 0;
		this.y = this.yStart;	
		this.allShoots.restart();
	}

	/**
	 * Determina qual imagem da nave deve ser desenhada.
	 *
	 */
	public final void updateIMG(){
		if ((this.right) && (this.left)){
			if (this.lastPressedX == 'r'){
				this.drawAirship = j;
			} else{
				this.drawAirship = m;
			}	
		}else{/*Somente uma das duas teclas pressionadas Right ou Left. */
			if (this.right){
				this.drawAirship = j;
			}		
			if (this.left){
				this.drawAirship = m;
			}		
		}
	}
	/**
	 * Movimenta a nave.
	 *
	 */
	public final void move (){
		/*Caso as teclas Up e Down estiverem pressionadas ao mesmo tempo. */
		if ((this.up) && (this.down)){
			if (this.lastPressedY == 'u') {
				if (this.y <= MAX_UP){
					this.y = MAX_UP; 
				} else {
					this.y -= this.speedY; 
				}	
			} else{
				if (this.y >= MAX_DOWN ){
					this.y = MAX_DOWN;
				} else{
					this.y += this.speedY;
				}		
			}
		} else{/*Somente uma das duas teclas pressionadas Up ou Down. */
			if (this.up) {
				if (this.y <= MAX_UP){
					this.y = MAX_UP; 
				} else {
					this.y -= this.speedY; 
				}	
			}
			if (this.down){
				if (this.y >= MAX_DOWN ){
					this.y = MAX_DOWN;
				} else{
					this.y += this.speedY;
				}		
			}
		}

		/*Caso as teclas Right e Left estiverem pressionadas ao mesmo tempo. */
		if ((this.right) && (this.left)){
			if (this.lastPressedX == 'r'){
				if (x >= MAX_RIGHT ){
					this.x = MAX_RIGHT;
				} else{
					this.x += this.speedX;
				}	
			} else{
				if (this.x <= MAX_LEFT){
					this.x = MAX_LEFT;
				} else{
					this.x -= this.speedX;
				}		
			}
		} else{/*Somente uma das duas teclas pressionadas Right ou Left. */
			if (this.right){
				if (x >= MAX_RIGHT ){
					this.x = MAX_RIGHT;
				} else{
					this.x += this.speedX;
				}	
			}		
			if (this.left){
				if (this.x <= MAX_LEFT){
					this.x = MAX_LEFT;
				} else{
					this.x -= this.speedX;
				}		
			}		
		}
	}

	/**
	 * Responsável por atualizar a posição e a imagem da nave.
	 *
	 */
	public final void update(){
		if (this.state != EXPLOSION) {
			this.move();
			this.updateIMG();
		}
	}

	/**
	 * Muda o estado da nave para EXPLOSION, ou seja, chama a animação da nave explodindo.
	 * @return
	 * True = Mudança do estado foi realizada | False = Mudança do estado não foi realizada.
	 */
	public final boolean changeToExplosion() {
		if (this.state != RESTART){
			this.state = this.EXPLOSION;
			return true;
		}
		return false;
	}

	/**
	 * Testa colisão da nave com o bosStage1.
	 * @param bosStage1
	 * Usado para saber a localização do mesmo.
	 * @return
	 * True = Colidiu com o bosStage1| False = Não colidiu com o bosStage1.
	 */
	public final boolean colideWithBosStage1(BosStage1 bosStage1) {
		if (bosStage1.state == 0) {
			//Utiliza os dois pontos posteriosres do quadrado. Pois caso use um posterior e um ponto do final ocorrerá um atrazo na animação.
			if ((((this.x + this.naveJetsMiddle.getWidth()>= bosStage1.x) && (this.x <= bosStage1.x + bosStage1.bosStage1.getWidth())) && 
					((this.y >= bosStage1.y) && (this.y <= bosStage1.y + bosStage1.bosStage1.getHeight()))) ||
					(((this.x + this.naveJetsMiddle.getWidth() >= bosStage1.x) && (this.x /*+ bosStage1.images[0].getWidth()*/ <= bosStage1.x + bosStage1.bosStage1.getWidth())) && 
							((this.y + this.naveJetsMiddle.getHeight() >= bosStage1.y) && (this.y + this.naveJetsMiddle.getHeight() <= bosStage1.y + bosStage1.bosStage1.getHeight()))) ||
							((this.x >= bosStage1.x) && (this.x + this.naveJetsMiddle.getWidth() <= bosStage1.x + bosStage1.bosStage1.getWidth())) &&
							((this.y >= bosStage1.y) && (this.y + this.naveJetsMiddle.getHeight() <= bosStage1.y))){
				if (!this.collision){
					this.collision = true;
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 
	 * @param bosStage1
	 * Usado para saber a localização do mesmo.
	 * @return
	 * True = bosStage1 morreu| False = bosStage1 não morreu.
	 */
	public boolean decreaseBosStage1Lives(BosStage1 bosStage1) {
		bosStage1.lives --;
		if (bosStage1.lives <= 0) {
			bosStage1.changeToExplosion();
			return true;
		}
		return false;
	}

	/**
	 * Testa colisão da nave com o subchief.
	 * @param subchief
	 * Usado para saber a localização do mesmo.
	 * @return
	 * True = Colidiu com o subchief| False = Não colidiu com o subchief.
	 */
	public final boolean colideWithSubchief(Subchief subchief) {
		if (subchief.state == 0) {
			//Utiliza os dois pontos posteriosres do quadrado. Pois caso use um posterior e um ponto do final ocorrerá um atrazo na animação.
			if ((((subchief.x >= this.x) && (subchief.x <= this.x + this.naveJetsMiddle.getWidth())) && 
					((subchief.y >= this.y) && (subchief.y <= this.y + this.naveJetsMiddle.getHeight()))) ||
					(((subchief.x /*+ subchief.images[0].getWidth()*/ >= this.x) && (subchief.x /*+ subchief.images[0].getWidth()*/ <= this.x + this.naveJetsMiddle.getWidth())) && 
							((subchief.y + subchief.naveMiddle.getHeight() >= this.y) && (subchief.y + subchief.naveMiddle.getHeight() <= this.y + this.naveJetsMiddle.getHeight()))) ||
							((subchief.x + subchief.naveMiddle.getWidth() >= this.x) && (subchief.x + subchief.naveMiddle.getWidth() <= this.x + this.naveJetsMiddle.getWidth())) &&
							((subchief.y /*- (subchief.naveMiddle.getHeight()/2)*/ >= this.y) && (subchief.y /*- (subchief.naveMiddle.getHeight()/2)*/ <= this.y + this.naveJetsMiddle.getHeight()))){
				if (!this.collision){
					this.collision = true;
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 
	 * @param subchief
	 * Usado para saber a localização do mesmo.
	 * @return
	 * True = subchief morreu| False = subchief não morreu.
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
	 * Testa colisão da nave com um meteóro
	 * @param meteorArray | O objeto da classe meteor.
	 * @return | Volta True = Colidiu. | False = não colidiu.
	 */
	public final boolean colideWithMeteor(MeteorArray meteorArray) {
		for (byte i = 0; i < meteorArray.METEOR_QUANTITY; i++) {
			//Utiliza os dois pontos posteriosres do quadrado. Pois caso use um posterior e um ponto do final ocorrerá um atrazo na animação.
			if ((((meteorArray.meteor[i].x >= this.x) && (meteorArray.meteor[i].x <= this.x + this.naveJetsMiddle.getWidth())) && 
					((meteorArray.meteor[i].y >= this.y) && (meteorArray.meteor[i].y <= this.y + this.naveJetsMiddle.getHeight()))) ||
					(((meteorArray.meteor[i].x /*+ meteorArray.meteor[i].images[0].getWidth()*/ >= this.x) && (meteorArray.meteor[i].x /*+ meteorArray.meteor[i].images[0].getWidth()*/ <= this.x + this.naveJetsMiddle.getWidth())) && 
							((meteorArray.meteor[i].y + meteorArray.meteor[i].images[0].getHeight() >= this.y) && (meteorArray.meteor[i].y + meteorArray.meteor[i].images[0].getHeight() <= this.y + this.naveJetsMiddle.getHeight())))){
				if (!this.collision){
					if (!meteorArray.meteor[i].collided) {
						this.collision = true;
						return true;
					}
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
			if ((((meteorArray.meteor[i].x >= this.x) && (meteorArray.meteor[i].x <= this.x + this.naveJetsMiddle.getWidth())) && 
					((meteorArray.meteor[i].y >= this.y) && (meteorArray.meteor[i].y <= this.y + this.naveJetsMiddle.getHeight()))) ||
					(((meteorArray.meteor[i].x >= this.x) && (meteorArray.meteor[i].x <= this.x + this.naveJetsMiddle.getWidth())) && 
							((meteorArray.meteor[i].y + meteorArray.meteor[i].images[0].getHeight()>= this.y) && (meteorArray.meteor[i].y + meteorArray.meteor[i].images[0].getHeight() <= this.y + this.naveJetsMiddle.getHeight())))){
				meteorArray.meteor[i].collided = true;

			}
		}	
	}

	/**
	 * Usado para tratar a colisão entre a airship e o bosStage1.
	 * @param bosStage1
	 * Usado para saber a localização do bosStage1
	 * @return
	 * True = bosStage1 morreu | False = bosStage1 não morreu.
	 */
	public final boolean airshipBosStage1Collision(BosStage1 bosStage1) {
		if (colideWithBosStage1(bosStage1)) {
			changeToExplosion();
			if (decreaseBosStage1Lives(bosStage1)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Usado para tratar a colisão entre a airship e o subchief.
	 * @param subchief
	 * Usado para saber a localização do subchief
	 * @return
	 * True = Subchief morreu | False = subchief não morreu.
	 */
	public final boolean airshipSubchiefCollision(Subchief subchief) {
		if (colideWithSubchief(subchief)) {
			changeToExplosion();
			if (decreaseSubchiefLife(subchief)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Trata a colisão entre a airship e meteoro.
	 * @param meteorArray
	 * Usado para saber a localização dos meteoros.
	 */
	public final boolean airshipMeteorCollision(MeteorArray meteorArray) {
		if (colideWithMeteor(meteorArray)) {
			if (changeToExplosion()){
				searchMeteorCrashed(meteorArray);
				return true;
			}
		}
		return false;
	}

	/**
	 * Tratamento da colisão do laser com o bosStage1.
	 * Caso o bosStage1 esteja com o número de vidas igual a zero, a animação do bosStage1 é alterada
	 * para modo de explosão. Além disso verifica se o bosStage1 deve evoluir ou não.
	 * @param bosStage1
	 * Usado para saber a localização do bosStage1
	 * @return
	 * True = BosStage1 morreu | False = BosStage1 não morreu.
	 */
	public final boolean laserBosStage1Collision(BosStage1 bosStage1) {
		if (this.allShoots.shootLaserArray.colideWithBosStage1(bosStage1)) {
			if(this.allShoots.shootLaserArray.decreaseBosStage1(bosStage1)) {
				return true;
			}
			//Controla o nível de dificuldade do BosStage1.
			bosStage1.shootControl();
		}
		return false;
	}

	/**
	 * Tratamento da colisão do laser com o subchief
	 * Caso o subchief esteja com o número de vidas igual a zero, a animação do subchief é alterada
	 * para modo de explosão.
	 * @param subChief
	 * Usado para saber a localização do subchief
	 * @return
	 * True = Subchief morreu | False = subchief não morreu.
	 */
	public final boolean laserSubchiefCollision(Subchief subChief) {
		if (this.allShoots.shootLaserArray.colideWithSubchief(subChief)) {
			return this.allShoots.shootLaserArray.decreaseSubchiefLife(subChief);
		}
		return false;
	}

	/**
	 * Tratamento da colisão do laser com o meteoro.
	 * @param meteorArray
	 * Usado para saber a localização dos meteoros.
	 */
	public final boolean laserMeteorCollision(MeteorArray meteorArray) {
		if(this.allShoots.shootLaserArray.colideWithMeteor(meteorArray)) {
			this.allShoots.shootLaserArray.searchMeteorCrashed(meteorArray);
			return true;
		}
		return false;
	}

	/**
	 * Testa colisão do blaster com o bosStage1 e faz o devido tratamento.
	 * Caso o bosStage1 esteja com o número de vidas igual a zero, a animação do bosStage1 é alterada
	 * para modo de explosão.
	 * @param bosStage1
	 * Usado para saber a localização do bosStage1
	 * @return
	 * True = bosStage1 morreu | False = bosStage1 não morreu.
	 */
	public final boolean blasterBosStage1Collision(BosStage1 bosStage1){
		if (this.allShoots.shootblaster.colideWithBosStage1(bosStage1)) {
			if(this.allShoots.shootblaster.decreaseBosStage1Life(bosStage1)) {
				//tem outra maneira para não repetir código. Porém perderá o padrão.
				this.allShoots.shootblaster.decreaseBlasterLifeAgainstBosStage1();
				return true;
			}
			this.allShoots.shootblaster.decreaseBlasterLifeAgainstBosStage1();
			//Controla o nível de dificuldade do BosStage1.
			bosStage1.shootControl();
		}
		return false;
	}

	/**
	 * Testa colisão do blaster com o subchief.
	 * Caso o subchief esteja com o número de vidas igual a zero, a animação do subchief é alterada
	 * para modo de explosão.
	 * @param subchief
	 * Usado para saber a localização do subchief
	 * @return
	 * True = Subchief morreu | False = subchief não morreu.
	 */
	public final boolean blasterSubchiefCollision(Subchief subchief){
		if (this.allShoots.shootblaster.colideWithSubchief(subchief)) {
			if (this.allShoots.shootblaster.decreaseSubchiefLife(subchief)) {
				this.allShoots.shootblaster.decreaseBlasterLifeAgainstSubchief();
				return true;
			}
			this.allShoots.shootblaster.decreaseBlasterLifeAgainstSubchief();
		}
		return false;
	}

	/**
	 * Testa colisão do blaster com o meteoro
	 * @param Meteor | O objeto da classe meteor.
	 */
	public final boolean blasterMeteorCollision(MeteorArray meteorArray){
		if (this.allShoots.shootblaster.colideWithMeteor(meteorArray)) {
			this.allShoots.shootblaster.decreaseBlasterLifeAgainstMeteor();
			return true;
		}
		return false;
	}

	/**
	 * Testa colisão do especial com o bosStage1 e faz o devido tratamento.
	 * Caso o bosStage1 esteja com o número de vidas igual a zero, a animação do bosStage1 é alterada
	 * para modo de explosão.
	 * @param bosStage1
	 * Usado para saber a localização do bosStage1
	 * @return
	 * True = bosStage1 morreu | False = bosStage1 não morreu.
	 */
	public final boolean especialBosStage1Collision(BosStage1 bosStage1){
		if (this.allShoots.especialColideWithBosStage1(bosStage1)) {
			if(this.allShoots.especialDecreaseBosStage1Life(bosStage1)) {
				//tem outra maneira para não repetir código. Porém perderá o padrão.
				this.allShoots.especialDecreaseEspecialLife();
				return true;
			}
			this.allShoots.especialDecreaseEspecialLife();
			//Controla o nível de dificuldade do BosStage1.
			bosStage1.shootControl();
		}
		return false;
	}

	/**
	 * Testa colisão do especial com o subchief.
	 * Caso o subchief esteja com o número de vidas igual a zero, a animação do subchief é alterada
	 * para modo de explosão.
	 * @param subchief
	 * Usado para saber a localização do subchief
	 * @return
	 * True = Subchief morreu | False = subchief não morreu.
	 */
	public final boolean especialSubchiefCollision(Subchief subchief){
		if (this.allShoots.especialColideWithSubchief(subchief)) {
			if (this.allShoots.especialDecreaseSubchiefLife(subchief)) {
				this.allShoots.especialDecreaseEspecialLife();
				return true;
			}
			this.allShoots.especialDecreaseEspecialLife();
		}
		return false;
	}

	/**
	 * Testa colisão do especial com o meteoro.
	 * @param Meteor | O objeto da classe meteor.
	 */
	public final boolean especialMeteorCollision(MeteorArray meteorArray){
		if (this.allShoots.especialColideWithMeteor(meteorArray)) {
			this.allShoots.especialDecreaseEspecialLifeAgainstMeteor();
			return true;
		}
		return false;
	}	

	/**
	 * Desenha o simbolo que indica que o especial está ativado.
	 */
	public void paintEspecialSymbol(Graphics g) {
		if (this.allShoots.especialActivated()) {
			g.drawImage(this.especial, 50, 21, 0);
		}
	}

	/**
	 * Desenha a animação Restart ou seja faz com que a nave fique piscando.
	 * @param g
	 */
	public final void paintRestart(Graphics g) {
		switch(this.drawRestart) {
		case 0:
			g.drawImage(this.naveMiddle, this.x, this.y, 0);
			break;
		}
		//Início. Animação do restart, a nave fica piscando e invencível ao mesmo tempo.
		this.timeDraw += 1;
		if (this.timeDraw == this.timing){	
			if (this.drawRestart == 1){
				this.drawRestart = 0;
			} else{
				this.drawRestart = 1;
			}					
			this.restartTime += 1;
			this.timeDraw = 0;
		}
		//Fim. Animação restart.
		if (this.restartTime == 15){
			this.state 		 = NORMAL;
			this.drawRestart = 0;
			this.drawAirship = m;
			this.timeDraw	 = 0;
			this.collision   = false;
		}
	}

	/**
	 * Desenha a explosão.
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
			this.timeDraw = 0;
			this.drawExplosion = 0;
			this.x = 5;
			this.y = this.yStart;
			this.restartTime = 0;
			this.drawAirship = 0;
			this.lives -= 1;
			this.allShoots.decreaseShootType();
			this.state = this.RESTART;
		}
	}

	/**
	 * Gerencia toda e qualquer animação da nave.
	 * @param g
	 */
	public final void paint(Graphics g) {	
		//Desenha o tiro e movimenta o mesmo.
		this.allShoots.paint(g);

		switch (this.state){
		//estado NORMAL.
		case 0:
			switch (this.drawAirship) {
			case m:
				g.drawImage(naveMiddle, this.x, this.y, 0);
				break;
			case j:
				g.drawImage(naveJetsMiddle, this.x, this.y, 0);
				break;
			}
			break;
		case 1:
			//estado EXPLOSION
			paintExplosion(g);
			break;						
		case 2:
			//estado RESTART.
			paintRestart(g);
			break;
		}
	}
	/**
	 * Responsável por chamar o tiro caso o estado da nave seja NORMAL.
	 *
	 */
	public final void fire() {
		if (this.state == NORMAL) {
			this.allShoots.shootType(this.x, this.y, this.naveMiddle.getHeight());
		}
	}

	/**
	 * Dispara o tiro especial da nave.
	 *
	 */
	public void fireEspecial() {
		if (this.state == NORMAL) {
			this.allShoots.fireEspecialShoot(this.x, this.y, this.naveMiddle.getHeight());
		}
	}

	/**
	 * Aumenta em um a vida da nave.
	 */
	public void lifeIncrease() {
		this.lives++;
	}

	/**
	 * Caso seja pressionado para cima esta função é chamada.
	 *
	 */
	public final void upPressed (){
		this.up = true;
		this.lastPressedY = 'u';
	}

	/**
	 * Caso seja pressionado para baixo esta função é chamada.
	 *
	 */
	public final void downPressed(){
		this.down = true;
		this.lastPressedY = 'd';
	}

	/**
	 * Caso seja pressionado para direita esta função é chamada.
	 *
	 */
	public final void rightPressed(){
		this.right = true;
		this.lastPressedX = 'r';
	}

	/**
	 * Caso seja pressionado para esquerda esta função é chamada. 
	 *
	 */
	public final void leftPressed(){
		this.left = true;
		this.lastPressedX = 'l';
	}

	/**
	 * Caso seja despressionado para cima esta função é chamada.
	 *
	 */
	public final void upReleased(){
		this.up = false;
		this.drawAirship = m;
	}

	/**
	 * Caso seja despressionado para baixo esta função é chamada.
	 *
	 */
	public final void downReleased(){
		this.down = false;
		this.drawAirship = m;
	}

	/**
	 * Caso seja despressionado para direita esta função é chamada.
	 *
	 */
	public final void rightReleased(){
		this.right = false;
		this.drawAirship = m;
	}

	/**
	 * Caso seja despressionado para esquerda esta função é chamada.
	 *
	 */
	public final void leftReleased(){
		this.left = false;
		this.drawAirship = m;
	}



}
