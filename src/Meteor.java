/*
 * Coisas a Fazer
 * Redistribuir os meteoros de uma maneira mais uniforme.
 * Quando o meteoro colidir com algo tem que ser feito uma animação de colisão do meteoro. Exemplo quando um tiro acertar o meteoro ele deve partir ao meio e ir desaparecendo.*/

import java.util.Random;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;


public class Meteor {
	/** Altura da área que o meteoro pode se movimentar*/
	private final int BACK_GROUND_HEIGHT;

	/** Posição máxima do lado direito da tela.*/
	private final int MAX_RIGHT_POSITION = Midlet.width;

	/** Posição não visível ao usuário do lado esquerdo da tela.*/
	private final int INVISIBLE_LEFT_POSITION;

	/** Indica que o meteoro se movimenta na horizontal */
	private final byte HORIZONTAL = 0;

	/** Indica que o meteoro se movimenta na direção vertical.*/
	private final byte VERTICAL = 1;

	/** Quantos pontos o meteoro vale caso seja destruido.*/
	public final byte VALUE = 5;

	/** Número de imagens.*/
	private final byte IMAGE_QUANTITY = 6;
	
	/** Número de imagens*/
	private final byte COLLISION_QUANTITY = 3;

	/** Vetor que irá conter todas as imagens do meteoro.*/
	public Image[] images = new Image[IMAGE_QUANTITY];
	
	/** Vetor que irá conter todas as imagens do meteoro colidido.*/
	public Image[] collisions = new Image[COLLISION_QUANTITY];

	/** Randomiza a posição incial do meteoro.*/
	private final Random random;

	/** Gerencia o tempo de duração de cada imagem do meteoro.*/
	private byte meteorTime;
	
	/** O tempo limite de duração de uma imagem na animação*/
	private byte timing;

	/** Gerencia a mudança da imagem do meteoro.*/
	private int meteorDrawr;

	/** Gerencia o tempo de duração de cada imagem do meteoro colidido.*/
	private byte collisionTime;

	/** Gerencia a mudança da imagem do meteoro colidido.*/
	private int collisionDraw;

	/** True = Meteoro colidiu com a nava | False = Meteoro não colidiu.*/
	public boolean collided;

	/** Tipo do meteoro | HORIZONTAL ou VERTICAL*/
	public byte meteorType;

	/** True = Meteoro vertical está visível ao player | False = Meteor vertical não está visível ao player*/
	public boolean verticalMeteorActivate;

	/** Posição horizontal do meteoro.*/
	public int x;

	/** Posição vertical do meteoro*/
	public int y;

	public Meteor() {
		try {
			
			for (byte i = 0; i < IMAGE_QUANTITY; i++) {
				
				this.images[i] = Image.createImage("/meteor00" + i + ".png");
			}
			
			for (byte i = 0; i < COLLISION_QUANTITY; i++) {
			
				this.collisions[i] = Image.createImage("/meteor0C" + i + ".png");
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*O tamanho total da tela - (a altura do cabeçalho + a altura do meteoro)*/
		BACK_GROUND_HEIGHT = Midlet.height - (StageCount.stage1.getHeight() + this.images[0].getHeight());
		INVISIBLE_LEFT_POSITION = -(5 + images[0].getWidth());
		random = new Random();
	}

	/**
	 * Reseta as variáveis do meteoro horizontal.
	 *
	 */
	public final void restartHorizontal(){
		//Atribui valor randomico para a posição do meteoro.
		this.x = Math.abs(random.nextInt() % 200) + MAX_RIGHT_POSITION;
		/*Ao usar nº random mod BACK_GROUND_HIGHT iremos ter um resultado entre  zero e tamanho da variável BACK_GROUND_HIGHT,
		 *assim devemos somar a altura do cabeçalho a este valor, com isso o meteoro estará entre os intervalos.*/
		this.y = Math.abs(random.nextInt() % BACK_GROUND_HEIGHT) + StageCount.stage1.getHeight();		
		this.meteorTime    = 0;
		this.meteorDrawr   = 0;
		this.collisionTime = 0;
		this.collisionDraw = 0;
		this.timing		   = 2;		
		this.collided      = false;
		this.meteorType    = HORIZONTAL;
	}

	/**
	 * Reseta as variáveis do meteoro vertical.
	 *
	 */
	public final void restartVertical(){
		//Atribui valor randomico para a posição do meteoro.
		this.x = Math.abs(random.nextInt() % Midlet.width);
		this.y = -1 * (Math.abs(random.nextInt() % (BACK_GROUND_HEIGHT - this.images[0].getHeight())));		
		this.meteorTime = 0;
		this.meteorDrawr = 0;
		this.collisionTime = 0;
		this.collisionDraw = 0;
		this.timing		   = 1;			
		this.collided   = false;
		this.meteorType = VERTICAL;
		this.verticalMeteorActivate = false;
	}


	/**
	 * Responsável pela movimentação do meteoro horizontal.
	 * Gera a posição de um novo meteoro caso ele saia do campo de visão.
	 *
	 */
	public final void updateHorizontal(){
		this.x --;
		if (this.x < INVISIBLE_LEFT_POSITION){
			this.x = Math.abs(random.nextInt() % 200) + MAX_RIGHT_POSITION;
			this.y = Math.abs(random.nextInt() % BACK_GROUND_HEIGHT) + StageCount.stage1.getHeight();
		}
	}

	/**
	 * Responsável pela movimentação do meteoro vertical.
	 * Gera a posição de um novo meteoro caso ele saia do campo de visão.
	 * @return
	 * True = Este meteoro ainda continua ativo. | False = Este meteoro não está mais ativo.
	 */
	public final boolean updateVertical(){
		if (this.verticalMeteorActivate) {
			this.y ++;

			if (this.y > Midlet.height) {
				this.x = Math.abs(random.nextInt() % Midlet.width);
				this.y = -1 * (Math.abs(random.nextInt() % BACK_GROUND_HEIGHT));
				this.verticalMeteorActivate = false;
			}
		}
		return this.verticalMeteorActivate;
	}

	/**
	 * Desenha a imagem do meteoro.
	 * @param g
	 */
	public final void paint(Graphics g){
		if (!this.collided) {
			
			g.drawImage(this.images[this.meteorDrawr], this.x, this.y, 0);
			//Contador usado para gerenciar a animação.
			this.meteorTime++;
			if (this.meteorTime % 2 == 0) {
				if (this.meteorTime < IMAGE_QUANTITY * 2){
					this.meteorDrawr++;
				} else {
					this.meteorDrawr--;
					if (this.meteorDrawr == -1) {
						this.meteorDrawr = 0;
						this.meteorTime = 0;
					}
				}
			}
		}else {
			
			g.drawImage(this.collisions[this.collisionDraw], this.x, this.y, 0);

			this.collisionTime++;
			if (this.collisionTime == this.timing) {
				this.collisionDraw++;
				this.collisionTime = 0;
			}

			if (this.collisionDraw == 3) {
				switch (this.meteorType) {
				case 0:
					restartHorizontal();
					break;

				case 1:
					restartVertical();
					break;

				}
			}
		}
	}		
}




