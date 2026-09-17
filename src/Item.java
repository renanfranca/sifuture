import java.util.Random;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class Item {
	/** Altura da área que o subchief pode se movimentar*/
	private final int BACK_GROUND_HEIGHT;
	
	/** Posição máxima do lado direito da tela.*/
	private final int MAX_RIGHT_POSITION = Midlet.height;

	/** Posição não visível ao usuário do lado esquerdo da tela.*/
	private final int INVISIBLE_LEFT_POSITION;

	/** Quantos pontos o item vale.*/
	public final byte VALUE = 10;

	/** Número de imagens.*/
	private final byte IMAGE_QUANTITY = 5;

	/** Número de imagens.*/
	private final byte LIFE_QUANTITY = 11;

	/** Número de imagens.*/
	private final byte EFFECT_QUANTITY = 3;

	/** Este tipo de item faz um level up no tiro da nave.*/
	private final byte SHOOT = 0;

	/** Este tipo de item dar mais uma vida a nave.*/
	private final byte LIFE = 1;

	/** Vetor que irá conter todas as imagens do item tiro.*/
	public Image[] shoots = new Image[IMAGE_QUANTITY];

	/** Vetor que irá conter todas as imagens do item vida.*/
	public Image[] lifes = new Image[LIFE_QUANTITY];

	/** Vetor que irá conter todas as imagens do efeito ao pegar o item.*/
	public Image[] effect = new Image[EFFECT_QUANTITY];

	/** Randomiza a posição incial do item.*/
	private final Random random;

	/** Gerencia o tempo de duração de cada imagem do item.*/
	private byte itemTime;

	/** Gerencia a mudança da imagem do item.*/
	private int itemDraw;
	
	/** O tempo limite de duração de uma imagem na animação*/
	private byte timing;

	/** Gerencia o tempo de duração de cada imagem do meteoro colidido.*/
	private byte collisionTime;

	/** Gerencia a mudança da imagem do meteoro colidido.*/
	private int collisionDraw;

	/** True = item colidiu com a nava | False = item não colidiu.*/
	public boolean collision;

	/** True = Está ao alcance da nave | False = Não está ao alcance da nave.*/
	public boolean castOn;

	/** Tipo do item SHOOT ou LIFE*/
	public byte type;

	/** Posição horizontal do item.*/
	public int x;

	/** Posição vertical do item*/
	public int y;

	public Item () {
		try {
			for (byte i = 0; i < IMAGE_QUANTITY; i++) {
				this.shoots[i] = Image.createImage("/iten" + i + ".png");
			}
			
			for (byte i = 0; i < LIFE_QUANTITY; i++) {
				this.lifes[i] = Image.createImage("/life" + i + ".png");
			}
			
			for (byte i = 0; i < EFFECT_QUANTITY; i++) {
				this.effect[i] = Image.createImage("/effect" + i + ".png");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		/*O tamanho total da tela - (a altura do cabeçalho + a altura do item)*/
		BACK_GROUND_HEIGHT = Midlet.height - (StageCount.stage1.getHeight() + this.shoots[0].getHeight());
		INVISIBLE_LEFT_POSITION = -(5 + shoots[0].getWidth());
		random = new Random();
	}

	/**
	 * Reseta o item normalizando as variáveis para que o item possa ser lançado.
	 *
	 */
	public final void restartShootType(){
		this.itemTime 	   = 0;
		this.itemDraw 	   = 0;
		this.collisionDraw = 0;
		this.collisionTime = 0;
		this.timing 	   = 2;
		this.collision	   = false;
		this.castOn  	   = false;
		this.type	 	   = SHOOT;
	}

	public final void restartLifeType(){
		//Atribui valor randomico para a posição do item.
		this.itemTime = 0;
		this.itemDraw = 0;
		this.collisionDraw = 0;
		this.collisionTime = 0;
		this.timing 	   = 2;
		this.collision= false;
		this.castOn  = false;
		this.type	 = LIFE;
	}

	/**
	 * Lança o tiro.
	 *
	 */
	public void cast() {
		if (this.castOn) {
			return;
		}
		this.castOn = true;
		this.x = Math.abs(random.nextInt() % 200) + MAX_RIGHT_POSITION;
		/*Ao usar nº random mod BACK_GROUND_HEIGHT iremos ter um resultado entre  zero e tamanho da variável BACK_GROUND_HEIGHT,
		 *assim devemos somar a altura do cabeçalho a este valor, com isso o item estará entre os intervalos.*/
		this.y = Math.abs(random.nextInt() % BACK_GROUND_HEIGHT) + StageCount.stage1.getHeight();	
	}

	/**
	 * Responsável pela movimentação do item
	 * Gera a posição de um novo item caso ele saia do campo de visão.
	 *
	 */
	public final void update(){
		this.x --;
		if (this.x < INVISIBLE_LEFT_POSITION){
			this.castOn = false;
		}
	}

	/**
	 * Resposnável por testar colisão entre o item e a nave.
	 * @param airShip
	 * Nave usada para a comparação.
	 * @return
	 * True = Colidiu | False = Não colidiu.
	 */	
	public final boolean colideWith(AirShip airShip){
		if (airShip.state == 0) {
			//Compara se o quadrado imaginário em volta da nave colidiu ou não com o quadrado imaginário em volta do meteoro.
			if (((this.x >= airShip.x) && (this.x <= airShip.x + airShip.naveMiddle.getWidth())) && 
					((this.y >= airShip.y) && (this.y <= airShip.y + airShip.naveMiddle.getHeight())) ||
					((this.x >= airShip.x) && (this.x <= airShip.x + airShip.naveMiddle.getWidth())) &&
					((this.y + this.shoots[0].getHeight() >= airShip.y) && (this.y + this.shoots[0].getHeight() <= airShip.y + airShip.naveMiddle.getHeight()))){
				//Não tem necessidade de entrar no If caso o meteoro já tenha colidido com o bçaster e a animação esteja em andamento.
				if (!this.collision) {
					this.collision = true;
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * Desenha o item de acordo com seu tipo.
	 * @param g
	 */
	public final void paint(Graphics g){
		if (!this.collision) {
			if (this.type == SHOOT) {
				g.drawImage(this.shoots[this.itemDraw], this.x, this.y, 0);
				//Contador usado para gerenciar a animação.
				this.itemTime++;
				if (this.itemTime == this.timing) {
					this.itemDraw++;
					this.itemTime = 0;
					if (this.itemDraw == 5) {
						this.itemDraw = 0;
					}
				} 
			}else {
				g.drawImage(this.lifes[this.itemDraw], this.x, this.y, 0);
				//Contador usado para gerenciar a animação.
				this.itemTime++;
				if (this.itemTime % 2 == 0) {
					if (this.itemTime < LIFE_QUANTITY * 2) {
						this.itemDraw++;
					}else {
						this.itemDraw--;
						if (this.itemDraw == -1) {
							this.itemDraw = 0;
							this.itemTime = 0;
						}
					}
				} 	
			}
		}else {

			g.drawImage(this.effect[this.collisionDraw], this.x, this.y, 0);

			this.collisionTime++;
			if (this.collisionTime == this.timing) {
				this.collisionDraw++;
				this.collisionTime = 0;
			}

			if (this.collisionDraw == 3) {
				switch (this.type) {
				case 0:
					restartShootType();
					break;

				case 1:
					restartLifeType();
					break;

				}
			}
		}
	}
}
