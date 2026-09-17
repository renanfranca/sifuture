import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/*
 * Descrição & Coisas a Fazer.
 * Tiro após adquirir o box que contem o tiro especial.
 * Este tiro é especial porque pode destruir mais de um meteoro ao mesmo tempo.
 * Coisas a Fazer:
 * Botar as imagens do blaster em um vetor e usar o mesmo no paint.
 */

public class ShootBlaster {
	
	/** Número de imagens da animação do laser.*/
	private final byte BLASTER_QUANTITY = 9;
	
	/** Posição horizontal do Blaster*/
	private int x;

	/** Posição vertical do Blaster*/
	private int y;
	
	/** Vetor que irá conter as imagens do laser.*/
	private Image[] blaster = new Image[BLASTER_QUANTITY];

	/** True = Tiro foi ativado | False = Tiro não foi ativado */
	public boolean shootOn;

	/** Gerencia a mudança da imagem do Blaster.*/
	private byte blasterDraw;

	/** Gerencia o tempo de duração de cada imagem do Blaster.*/
	private byte blasterTime;

	/** Até qual animação do blaster vai ser desenhada*/
	private byte collisionDraw;
	
	/** Gerencia o tempo de duração de cada imagem de colisão do Blaster.*/
	private byte collisionTime;
	
	/** O tempo limite de duração de uma imagem na animação*/
	private byte timing;

	/** Quantidade de vida do blaster.*/
	private byte lives;

	public ShootBlaster(){
		try{
			//Carregando imagem do blaster.
			for (int i = 0; i < BLASTER_QUANTITY; i++) {
				this.blaster[i] = Image.createImage("/ylwBlaster0" + i + ".png");
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		restart();
	}

	/**
	 * Reseta o tiro, deixando pronto para ser atirado.
	 *
	 */
	public void restart() {
		this.shootOn  = false;
		this.blasterTime = 0;
		this.blasterDraw = 0;
		this.collisionDraw = 0;	
		this.collisionTime = 0;
		this.timing = 1;
		this.lives = 2;
	}

	/**
	 * Responsável por ativar o processo de animação do blaster.
	 * @param x
	 * 		posição  horizontal na qual a nave atirou o blaster.
	 * @param y
	 * 		posição  vertical na qual a nave atirou o blaster.
	 */
	public final void shoot(int x, int y) {
		if (this.shootOn) {
			return;
		}
		this.shootOn = true;
		this.x = x;
		this.y = y;
	}

	/**
	 * Decrementa a vida do blaster quando ele colidir com meteor.
	 *
	 */
	public void decreaseBlasterLifeAgainstMeteor() {
		this.lives --;
	}

	/**
	 * Decrementa a vida do blaster quando ele colidir com o subchief ou com bosStage1.
	 *
	 */
	public void decreaseBlasterLifeAgainstSubchief() {
		this.lives = 0;
	}

	/**
	 * Decrementa a vida do blaster quando ele colidir com o bosStage1.
	 *
	 */
	public void decreaseBlasterLifeAgainstBosStage1() {
		this.lives = 0;
	}

	/**
	 * Testa se occreu colisão entre o blaster e o bosStage1. 
	 * @param subchief
	 * 		O objeto bosStage1 que é utilizado para saber a posição do mesmo.		
	 * @return
	 * 		Retorna true caso ocorra colisão e false caso não ocorra.
	 */
	public final boolean colideWithBosStage1(BosStage1 bosStage1){
		//Nem é preciso testar caso o Blaster não tenha sido lançado.
		if(this.shootOn){
			//Testa se o bosStage1 esteja no estado normal.
			if (bosStage1.state != 3) {
				//Compara se os pontos em volta do blaster colidiu ou não com o quadrado imaginário em volta do BosStage1.
				if (((this.x + blaster[8].getWidth() >= bosStage1.x) && (this.x + blaster[8].getWidth() <= bosStage1.x + bosStage1.bosStage1.getWidth())) && 
						((this.y >= bosStage1.y) && (this.y <= bosStage1.y + bosStage1.bosStage1.getHeight())) ||
						((this.x + blaster[8].getWidth() >= bosStage1.x) && (this.x + blaster[8].getWidth() <= bosStage1.x + bosStage1.bosStage1.getWidth())) &&
						((this.y + this.blaster[8].getHeight() >= bosStage1.y) && (this.y + this.blaster[8].getHeight() <= bosStage1.y + bosStage1.bosStage1.getHeight()))){
					//Não tem necessidade de entrar no If caso já tenha colidido.
						return true;
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
		bosStage1.lives -= this.lives;
		if (bosStage1.lives <= 0) {
			bosStage1.changeToExplosion();
			return true;
		}
		return false;
	}

	/**
	 * Testa se occreu colisão entre o blaster e o subchief. 
	 * @param subchief
	 * 		O objeto subchief que é utilizado para saber a posição do mesmo.		
	 * @return
	 * 		Retorna true caso ocorra colisão e false caso não ocorra.
	 */
	public final boolean colideWithSubchief(Subchief subchief){
		//Nem é preciso testar caso o Blaster não tenha sido lançado.
		if(this.shootOn){
			//Testa se o subchief esteja no estado normal.
			if (subchief.state == 0) {
				//Compara se o quadrado imaginário em volta do blaster colidiu ou não com os pontos em volta do subchief.
				if (((subchief.x >= this.x) && (subchief.x <= this.x + this.blaster[8].getWidth())) && 
						((subchief.y >= this.y) && (subchief.y <= this.y + this.blaster[8].getHeight())) ||
						((subchief.x >= this.x) && (subchief.x <= this.x + this.blaster[8].getWidth())) &&
						((subchief.y + subchief.naveMiddle.getHeight() >= this.y) && (subchief.y + subchief.naveMiddle.getHeight() <= this.y + this.blaster[8].getHeight()))){
					//Não tem necessidade de entrar no If caso já tenha colidido.
						return true;
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
		subchief.lives -= this.lives;
		if (subchief.lives <= 0) {
			subchief.changeToExplosion();
			return true;
		}
		return false;
	}

	/**
	 * Testa se o blaster colidiu com algum(ns) meteoro(s).
	 * @param meteorArray
	 * 		O objeto meteoro que é utilizado para saber a posição do mesmo.		
	 * @return
	 * 		Retorna true caso ocorra colisão e false caso não ocorra.
	 */
	public final boolean colideWithMeteor(MeteorArray meteorArray){
		//Nem é preciso testar caso o Blaster não tenha sido lançado.
		if(this.shootOn){
			//Compara se o quadrado imaginário em volta do Blaster colidiu ou não com os pontos imaginário em volta do meteoro.
			for (byte i = 0; i < meteorArray.METEOR_QUANTITY; i++) {
				if (((meteorArray.meteor[i].x >= this.x) && (meteorArray.meteor[i].x <= this.x + this.blaster[8].getWidth())) && 
						((meteorArray.meteor[i].y >= this.y) && (meteorArray.meteor[i].y <= this.y + this.blaster[8].getHeight())) ||
						((meteorArray.meteor[i].x >= this.x) && (meteorArray.meteor[i].x <= this.x + this.blaster[8].getWidth())) &&
						((meteorArray.meteor[i].y + meteorArray.meteor[i].images[0].getHeight() >= this.y) && (meteorArray.meteor[i].y + meteorArray.meteor[i].images[0].getHeight() <= this.y + this.blaster[8].getHeight()))){
					//Não tem necessidade de entrar no If caso o meteoro já tenha colidido com o blaster e a animação esteja em andamento.
						if (!meteorArray.meteor[i].collided) {	
							//Esta atribuição é feita para facilitar quando for resetar o meteoro.
//							meteorArray.meteor[i].x = this.x;
//							meteorArray.meteor[i].y = this.y;
							meteorArray.meteor[i].collided = true;
							return true;
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
	 * Desenha o blaster de acordo com a variável collision.
	 * @param g
	 * @param x
	 * Indica posição X inicial do Blaster. 
	 * @param y
	 * Indica posição Y inicial do Blaster
	 * @param drawBlaster
	 * Qual imagem do Blaster a ser chamado.
	 */
	public final void paint (Graphics g) {
		//Caso a nave não tenha atirado não faz nada.
		if (!shootOn) {
			return;
		}
		//Animação chamada caso o blaster tenha sido atirado e não tenha colidido.
		if (this.lives > 0){
			switch (this.blasterDraw) {
			case 0:
				g.drawImage(this.blaster[0], this.x, this.y, 0);
				this.x += 5;
				break;
			case 1:
				g.drawImage(this.blaster[1], this.x, this.y, 0);
				this.x += 5;
				break;
			case 2:
				g.drawImage(this.blaster[2], this.x, this.y, 0);
				this.x += 5;
				break;
			case 3:
				g.drawImage(this.blaster[3], this.x, this.y, 0);
				this.x += 3;
				break;
			case 4:
				g.drawImage(this.blaster[8], this.x, this.y, 0);
				this.x += 5;
				break;
			}

			/* Usado para controlar tempo entre cada animação*/
			if (this.blasterDraw < 4){
				this.blasterTime += 1;
				if (this.blasterTime == this.timing){	
					this.blasterDraw += 1;
					this.blasterTime = 0;
				}			
			}

			/* Fim da movimentação do blaster e normaliza as variáveis.*/
			if (this.x > Midlet.width){
				this.shootOn = false;
				this.blasterTime = 0;
				this.blasterDraw = 0;
				this.lives = 2;
			}
		}else {
			//Animação chamada caso o Blaster tenha colidido com o meteoro.
			switch(this.collisionDraw){
			case 0:
				g.drawImage(this.blaster[4], this.x, this.y, 0);
				this.x += 2;
				break;
			case 1:
				g.drawImage(this.blaster[5], this.x, this.y - 14, 0);
				this.x += 2;			
				break;
			case 2:
				g.drawImage(this.blaster[6], this.x, this.y - 15, 0);
				this.x += 3;
				break;
			case 3:
				g.drawImage(this.blaster[7], this.x, this.y - 15, 0);
				this.x += 4;
				break;
			}
			/* Usado para controlar tempo entre cada animação*/
			if (this.collisionDraw < 4){
				this.collisionTime += 1;
				if (this.collisionTime == this.timing){	
					this.collisionDraw += 1;
					this.collisionTime = 0;
				}
			}

			/* Fim da movimentação do blaster e normaliza todas as variáveis.*/
			if (this.collisionDraw == 4 ){
				restart();		
			}
		}
	}
}
