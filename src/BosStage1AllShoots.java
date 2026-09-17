import javax.microedition.lcdui.Graphics;

public class BosStage1AllShoots {
	
	/** Quantidade de tiro do tipo shoot1*/
	public final byte SHOOT1_QUANTITY = 2;
	
	/** Vetor que irá conter os shoot1*/
	public BosStage1Shoot1[] bosStage1Shoot1s = new BosStage1Shoot1[SHOOT1_QUANTITY];

	/** Tiro especial do BosStage1.*/
	public BosStage1Shoot2 bosStage1Shoot2;
	
	/** Só pode atirar o segundo tiro quando o primeiro desaparecer.*/
	private final byte LEVEL0 = 0;

	/** Pode atirar mesmo com o primeiro tiro ainda na tela.*/
	public final byte LEVEL1 = 1;

	/** O nível do tiro do shoot1 - LEVEL0 ou LEVEL1*/
	public byte shoot1ArrayLevel;

	/** Conta o número de tiros já atirados.*/
	private byte shoot1Count;

	/** True = Todos os lasers foram atirados | False = Pelo ao menos um laser não foi atirado.*/
	public boolean allShootOn;

	/** Controla o tempo entre cada tiro.*/
	private byte shootTime;

	/** O tipo do tiro do BosStage1.*/
	private byte shootType;

	public BosStage1AllShoots() {
		try{
			this.bosStage1Shoot2 = new BosStage1Shoot2();
			for (byte i = 0; i < SHOOT1_QUANTITY; i++) {
				this.bosStage1Shoot1s[i] = new BosStage1Shoot1();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		this.shootType = 0;
		this.shoot1ArrayLevel = LEVEL0;
		this.shoot1Count      = 0;	
	}

	/**
	 * Aciona o restart de todos os tiros do bosStage1.
	 */
	public void restart() {
		this.shootType = 0;
		this.shoot1Restart();
		this.bosStage1Shoot2.restart();
	}
	
	/**
	 * Evolui em um o nível do tiro.
	 *
	 */
	public void changeShootType() {
		this.shootType ++;
		if (this.shoot1ArrayLevel == 0) {
			this.shoot1LevelUp();
		}
	}

	/**
	 * Gerencia a evolução do tiro.
	 * @param x
	 * Posição X do BosStage1 no momento em que o tiro é chamado que funciona como posição inicial para o tiro.
	 * @param y
	 * Posição y do BosStage1 no momento em que o tiro é chamado que funciona como posição inicial para o tiro.
	 * @param sizey
	 * Tamanho vertical do BosStage1 o qual será usado para saber a posição do tiro em relação o BosStage1.
	 */
	public void shootType(int x, int y, int sizeY) {
		switch(this.shootType){
		case 0:			
			//Um tiro normal de cada vez.
			this.shootTime += 1;
			if (this.shootTime > 12) {	
				this.shoot1(x, y, sizeY);
				this.shootTime = 0;
			}
			break;
		case 1:
			//Atira de dois em dois tiros normais.
			this.shootTime += 1;
			if (this.shootTime > 30) {	
				this.shoot1(x, y, sizeY);
				this.shootTime = 0;
			}

			break;
		case 2:
			//Atira de dois em dois tiros normais.
			//Atira um especial a cada seis tiros normais e quando o shootTime for maior que 10.
			if (this.shootTime > 10) {
				if (this.allShootOn) {
					this.bosStage1Shoot2.shoot(x, y, sizeY);
				}
			}		
			this.shootTime += 1;
			if (this.shootTime > 30) {	
				this.shoot1(x, y, sizeY);
				this.shootTime = 0;
			}
			break;
		case 3:
			//Atira de dois em dois tiros normais.
			//Atira um especial a cada seis tiros normais.
			if (this.allShootOn) {
				this.bosStage1Shoot2.shoot(x, y, sizeY);
			}		
			this.shootTime += 1;
			if (this.shootTime > 23) {	
				this.shoot1(x, y, sizeY);
				this.shootTime = 0;
			}			
			break;
		case 4:
			//Atira de dois em dois tiros normais.
			//Atira um especial quando o shootTime for maior que 4.
			if (this.shootTime > 4) {
				this.bosStage1Shoot2.shoot(x, y, sizeY);
			}		
			this.shootTime += 1;
			if (this.shootTime > 23) {	
				this.shoot1(x, y, sizeY);
				this.shootTime = 0;
			}			
			break;

		default: 
			this.shootType --;
		}
	}
	
	/**
	 * Pinta de acordo com o nível do tiro.
	 * @param g
	 */
	public void paint(Graphics g) {
		switch(this.shootType){
		case 0:
			//Pinta se o nível do tiro for igual a 0.
			this.shoot1Paint(g);
			this.shoot1Update();
			break;
		case 1:
			//Pinta se o nível do tiro for igual a 1.
			this.shoot1Paint(g);
			this.shoot1Update();
			break;
		case 2:
			//Pinta se o nível do tiro for igual a 2.
			this.shoot1Paint(g);
			this.shoot1Update();
			this.bosStage1Shoot2.paint(g);
			break;
		case 3:
			//Pinta se o nível do tiro for igual a 3.
			this.shoot1Paint(g);
			this.shoot1Update();
			this.bosStage1Shoot2.paint(g);
			break;
		case 4:
			//Pinta se o nível do tiro for igual a 4.
			this.shoot1Paint(g);
			this.shoot1Update();
			this.bosStage1Shoot2.paint(g);
			break;
		}
	}
	
	/* Fim *Métodos relacionados com o tiro BosStage1Shoot1.*/
	/**
	 * Regride todas as evoluções. 
	 * Volta para o tiro mais básico.
	 *
	 */
	public void shoot1Restart() {
		this.shoot1ArrayLevel = LEVEL0;
		for (byte i = 0; i < SHOOT1_QUANTITY; i++) {
			this.bosStage1Shoot1s[i].restart();
		}	
	}
	
	/**
	 *  Gerencia o nível do tiro.
	 * @param x
	 * 		posição x do BosStage1.
	 * @param y
	 * 		posição y da BosStage1.
	 * @param sizey
	 * 		altura da imagem da BosStage1.
	 */
	public void shoot1(int x, int y, int sizey) {
		switch (this.shoot1ArrayLevel) {		
		case 0:
			//Corresponde ao LEVEL0.
			if (!this.bosStage1Shoot1s[0].shootOn) {
				this.bosStage1Shoot1s[0].shoot(x, y, sizey);
			}
			break;

		case 1:
			//Corresponde ao LEVEL1.
			for (byte i = 0; i < SHOOT1_QUANTITY; i++) {
				if (!this.bosStage1Shoot1s[i].shootOn) {
					this.bosStage1Shoot1s[i].shoot(x, y, sizey);
					break;
				}
			}	
			//Observa se já foi atirado 6 tiros para poder soltar o especial caso o BosStage1 já tenha.
			this.shoot1Count++;
			if (this.shoot1Count == SHOOT1_QUANTITY * 2) {
				this.allShootOn = true;
				this.shoot1Count = 0;
			} else{
				this.allShootOn = false;
			}
			break;
		}
	}

	/**
	 * Movimenta o tiro caso o mesmo tenha sido acionado.
	 *
	 */
	public void shoot1Update() {
		for (byte i = 0; i < SHOOT1_QUANTITY; i++) {
			if (this.bosStage1Shoot1s[i].shootOn) {
				this.bosStage1Shoot1s[i].update();	
			}
		}
	}

	/**
	 * Evolui o nível do tiro.
	 *
	 */
	public void shoot1LevelUp() {
		//Atira até três tiros ao mesmo tempo.
		this.shoot1ArrayLevel = LEVEL1;
		//Modifica a animação do tiro.
		for (byte i = 0; i < SHOOT1_QUANTITY; i++) {
			this.bosStage1Shoot1s[i].levelUp();
		}
	}

	/**
	 * Testa se algum(ns) dos tiros colidiu com a AirShip
	 * @param airShip
	 * Saber a localização da mesma.
	 * @return
	 * True = Colidiu | False = Não colidiu.
	 */
	public boolean shoot1ColideWithAirShip(AirShip airShip) {
		for (byte i = 0; i < SHOOT1_QUANTITY; i++) {
			if(this.bosStage1Shoot1s[i].shootOn) {
				if (this.bosStage1Shoot1s[i].colideWithAirShip(airShip)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Desenha o(s) tiro(s).
	 * @param g
	 */
	public void shoot1Paint(Graphics g) {
		for (byte i = 0; i < SHOOT1_QUANTITY; i++) {
			this.bosStage1Shoot1s[i].paint(g);
		}
	}	
	/* Fim *Métodos relacionados com o tiro BosStage1Shoot1.*/
}
