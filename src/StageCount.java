import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class StageCount {
	/** Ponto X a extrema direita*/
	private final int MAX_RIGHT = Midlet.width; 

	/** Momento em que o Bos irá surgir.*/
	private final int BOS_TIME;

	/** Momento em que o Bos irá surgir.*/
	private final int SUB_CHIEF_TIME;

	/** Posição x da nave miniatura.*/
	private  int x;

	/** Posição y da nave miniatura.*/
	private  int y;

	/** Imagem da nave enquanto ela tiver três vidas ou mais.*/
	private Image smallAirship3;

	/** Imagem da nave enquanto ela tiver duas vidas.*/
	private Image smallAirship2;

	/** Imagem da nave enquanto ela tiver uma vida.*/
	private Image smallAirship1;

	/** Início do controlador da fase.*/
	private Image stage0;

	/** Pixel do raio do controlador do tamanho do stage.*/
	public static Image stage1;
	
	/** Imagem com o nome score*/
	private Image imgScore;
	
	/** Numeros de 0 à 9.*/
	private Image[] number = new Image[10]; 
	
	/** Letras de a..z*/
	private Image[] word = new Image[1];

	/** Velocidade da nave*/
	private int stageSize;

	/** Usado para Controlar o tempo entre cada movimento da nave*/
	private byte stageTime;

	/** True = Fim da fase. | False = Ainda não chegou no fim da fase.*/
	public boolean stageEnd;

	/** Itens que a nave pode pegar.*/
	public ItemArray itemArray;

	/** O subchefe*/
	public Subchief subChief;

	/** O chefe do stagio1*/
	public BosStage1 bosStage1;

	/** Armazena os pontos acumulados pelo player ao destruir os objetos.*/
	public int score;
	
	/** Armazena o valor do score e o número de vidas da nave.*/
	private String drawString;

	public StageCount() {
		try{
			this.smallAirship3 = Image.createImage("/3lives.png");
			this.smallAirship2 = Image.createImage("/2lives.png");
			this.smallAirship1 = Image.createImage("/1lives.png");
			this.imgScore      = Image.createImage("/score.png");
			this.stage0 	   = Image.createImage("/StageBeginer.png");
			StageCount.stage1  = Image.createImage("/StageMiddle.png");
			for (int i = 0; i < 10; i++) {
				number[i] = Image.createImage("/" + i + ".png");
			}
			
			for (int i = 0; i < 1; i++) {
				word[i] = Image.createImage("/x.png");
			}
			
			this.itemArray 	   = new ItemArray();
			this.bosStage1 	   = new BosStage1();
			this.subChief      = new Subchief();
		} catch(Exception e) {
			e.printStackTrace();
		}
		this.SUB_CHIEF_TIME = MAX_RIGHT / 2;
		this.BOS_TIME  = MAX_RIGHT - this.smallAirship3.getWidth();
		restart();
	}

	/**
	 * Reseta todas as classes utilizadas no Stage1 
	 * com exceção da classe AirShip e de classes relacionadas a mesma.
	 *
	 */
	public void restart() {
		this.x = 5;
		this.y = ((StageCount.stage1.getHeight() / 2) - (this.smallAirship3.getHeight() / 2));
		this.stageSize = 10;
		this.stageTime = 0;
		this.stageEnd  = false;
		this.itemArray.restart();
		this.subChief.restart();
		this.bosStage1.restart();
		this.score = 0;
	}

	/**
	 * Controla quando o meteoro vertical deve ser lançado ou não.
	 * @param meteorArray
	 */
	public void meteorVertical(MeteorArray meteorArray) {
		if ((!this.subChief.testActivation()) && (!this.bosStage1.testActivation())) {
			//Momento em que o meteoro vertical irá ser ativado.
			if (this.x > 30) {
				//Meteoro vertical é ativado.
				meteorArray.activateMeteorVertical();
			}
		}
	}

	/**
	 * Controla o movimento e a pintura do item e também quando o 
	 * item deve aparecer.
	 * @param g
	 */
	public void item(Graphics g) {
		if (this.x == (30)){
			this.itemArray.cast((byte)0);
		}

		if (this.x > 4){
			this.itemArray.cast((byte)0);
			this.itemArray.cast((byte)1);
		}

		if (this.x ==  (MAX_RIGHT/4)){
			this.itemArray.cast((byte)0);
			this.itemArray.cast((byte)1);
		}

		if (this.x ==  (125)){
			this.itemArray.cast((byte)0);
		}

		if (this.x ==  (155)){
			this.itemArray.cast((byte)0);
			this.itemArray.cast((byte)1);
		}

		if (this.x ==  (190)){
			this.itemArray.cast((byte)0);
		}

		if ((this.bosStage1.lives == 80)||(this.bosStage1.lives == 20)) {
			this.itemArray.cast((byte)0);	
		}

		if (this.subChief.lives == 15) {
			this.itemArray.cast((byte)0);	
		}

		if (this.bosStage1.lives == 40) {
			this.itemArray.cast((byte)0);	
			this.itemArray.cast((byte)1);	
		}

		this.itemArray.update();
		this.itemArray.paint(g);
	}

	/**
	 * Ativa o BosStage1.
	 * @param g
	 */
	public void bosStage1Activation(Graphics g) {
		//Condição para ativar BosStage1.
		if (this.x == BOS_TIME) {
			this.bosStage1.activate();
			this.itemArray.cast((byte)0);
			this.x ++;
		}

//		if (this.x == 7/*(Midlet.width/5) + 3*/) {
//		this.bosStage1.activate();
//		this.itemArray.cast((byte)0);
//		this.x ++;
//		}
	}

	/**
	 * Realiza o update dos movimentos e do tiro do BosStage1. Além de pintar os mesmos.
	 * @param g
	 */
	public void bosStage1Update(Graphics g) {
		this.bosStage1.update();
		this.bosStage1.fire();
		this.bosStage1.paint(g);
	}

	/**
	 * Ativa o subchief.
	 *
	 */
	public void subchiefActivation() {
		//Condição para ativar SubChief
		if (this.x == SUB_CHIEF_TIME) {
			this.subChief.activate();
			this.x ++;
		}

//		if (this.x == 8/*(Midlet.width/5) + 3*/) {
//		this.subChief.activate();
//		this.x ++;
//		}
	}

	/**
	 * Realiza o update dos movimentos e do tiro do subchief. Além de pintar os mesmos.
	 * @param g
	 */
	public void subchiefUpdate(Graphics g) {
		this.subChief.update();
		this.subChief.fire();
		this.subChief.paint(g);
	}

	/**
	 * 
	 * @param g
	 * @param number, numero a ser desenhado.
	 * @param x, posição x.
	 * @param y, posição y.
	 */
	public void numberDraw(Graphics g, int value, int x, int y, boolean esquerda) {
		this.drawString = "" + value;
		if (esquerda) {
			x = x - this.number[0].getWidth() * this.drawString.length();
		}
		
		for (int i = 0; i < this.drawString.length(); i++) {
			g.drawImage(this.number[Integer.parseInt("" + this.drawString.charAt(i))], 
					x + (this.number[0].getWidth()*i) , y, 0);
		}
	}
	/**
	 * Desenha o escore no canto superior direito da tela.
	 * @param g
	 */
	public void drawScore(Graphics g) {
		this.drawString = "" + this.score;
		for (int i = 0; i < this.drawString.length(); i++) {
			g.drawImage(this.number[Integer.parseInt("" + this.drawString.charAt(i))], 
					Midlet.width - ((this.drawString.length())*this.number[0].getWidth()) + (this.number[0].getWidth()*i) , 0, 0);
		}
		g.drawImage(this.imgScore, Midlet.width - ((this.drawString.length())*this.number[0].getWidth() + this.imgScore.getWidth()), 
				this.stage0.getHeight()/4, 0);
	}

	/**
	 * Desenha o contador do Stage1. Que se localiza na parte superior da tela.
	 * @param g
	 * @param airShipLives
	 */
	public final void paint (Graphics g, int airShipLives){		
		//Desenha o controlador do tamanho do stage.
		for (byte i = 0; i < 1; i ++) {
			for (int j = 8; j < Midlet.width; j += 18) {
				g.drawImage(StageCount.stage1, j, i, 0);	
			}
		}
		if (!this.subChief.testActivation() && (!this.bosStage1.testActivation()) && (!this.stageEnd)) {
			//Controla o tempo entree cada movimento da nave.
			this.stageTime += 1;
			if ((this.stageTime >= this.stageSize) && (this.x < MAX_RIGHT)){
				this.x += 1;
				this.stageTime = 0;
			}
			if (this.x >= MAX_RIGHT) {
				this.stageEnd = true;
			}
		}

		//Desenha as naves.
		switch (airShipLives) {
		case 1:
			g.drawImage(this.smallAirship1, this.x, this.y, 0);
			break;
		case 2:
			g.drawImage(this.smallAirship2, this.x, this.y, 0);
			break;
		case 3:
			g.drawImage(this.smallAirship3, this.x, this.y, 0);
			break;
		default:
			g.drawImage(this.smallAirship3, this.x, this.y, 0);
		break;
		}

		g.drawImage(stage0, 0, 0, 0);

		/* Desenha as vidas da nave| início.*/
		g.drawImage(this.smallAirship2, Midlet.width - this.smallAirship2.getWidth() ,  StageCount.stage1.getWidth()/2 + this.smallAirship2.getHeight(), 0);
		g.drawImage(this.word[0], Midlet.width - (this.smallAirship2.getWidth() + this.word[0].getWidth()/2), StageCount.stage1.getWidth()/2 + this.smallAirship2.getHeight(), 0);
		numberDraw(g, airShipLives, Midlet.width - (this.smallAirship2.getWidth() + this.word[0].getWidth()/3), StageCount.stage1.getWidth()/2 + this.smallAirship2.getHeight(), true);
//		g.setColor(0xffffff);
//		this.drawString = airShipLives + "x";
//		g.drawString(this.drawString, Midlet.width - this.smallAirship2.getWidth() - this.drawString.length()*5, StageCount.stage1.getWidth()/2 + this.smallAirship2.getHeight(), 0);
		/* Desenha as vidas da nave| fim.*/

		drawScore(g);

		//Testa o momento para ativação.
		item(g);
		subchiefActivation();
		bosStage1Activation(g);	
	}
}