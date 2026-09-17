import javax.microedition.lcdui.Graphics;

public class AirShipAllShoots {

	/** O especial são três tiros especiais ao mesmo tempo.*/
	public final byte ESPECIAL_QUANTITY = 3;

	/** Número de vezes que o especial pode ser acionado.*/
	private byte activationTimes;

	/** Vetor que irá conter os tiros especiais.*/
	public AirShipEspecialShoot[] airShipEspecialShoots = new AirShipEspecialShoot[ESPECIAL_QUANTITY];

	/** Tiro avançado0 da nave.*/
	public ShootLaserArray shootLaserArray;

	/** Tiro avançado1 da nave.*/
	public ShootBlaster shootblaster;

	/** Controla o tempo entre cada tiro.*/
	private byte shootTime;

	/** O tipo do tiro da nave.*/
	private byte shootType;

	public AirShipAllShoots() {
		try{
			
			this.shootLaserArray = new ShootLaserArray();
	
			this.shootblaster = new ShootBlaster();
			
			/* Construtor dos especiais.*/
			for (byte i = 0; i < ESPECIAL_QUANTITY; i++) {
			
//				this.airShipEspecialShoots[i] = new AirShipEspecialShoot();
				switch (i) {
				case 0:
					this.airShipEspecialShoots[i] = new AirShipEspecialShoot(i);
					this.airShipEspecialShoots[i].orangeRestart();
					break;
				case 1:
					this.airShipEspecialShoots[i] = new AirShipEspecialShoot(i);
					this.airShipEspecialShoots[i].lightBlueRestart();
					break;					
				case 2:
					this.airShipEspecialShoots[i] = new AirShipEspecialShoot(i);
					this.airShipEspecialShoots[i].darkBlueRestart();
					break;
				}
			}
		
		} catch(Exception e) {
			e.printStackTrace();
		}
		this.shootType = 0;
		this.activationTimes = 0;
	}

	/**
	 * Aciona o restart de todos os tiros da AirShip. 
	 */
	public void restart() {
		this.shootType = 0;
		this.shootLaserArray.restart();
		this.shootblaster.restart();
		this.especialRestart();
	}

	/**
	 * Evolui em um o nível do tiro.
	 *
	 */
	public void changeShootType() {
		this.shootType ++;
		if (this.shootLaserArray.laserArrayLevel == 0) {
			this.shootLaserArray.LevelUp();

		}
	}

	/**
	 * Regride em um o nível do tiro. 
	 *
	 */
	public void decreaseShootType() {
		if (this.shootType == 0) {
			return;
		}

		if (this.especialActivated()) {
			this.especialDisable();
		}else {
			this.shootType--;
			if (this.shootType == 0) {
				this.shootLaserArray.LevelDown();
			}
		}
	}

	/**
	 * Gerencia a evolução do tiro.
	 * @param x
	 * Posição X da AirShip no momento em que o tiro é chamado que funciona como posição inicial para o tiro.
	 * @param y
	 * Posição y da AirShip no momento em que o tiro é chamado que funciona como posição inicial para o tiro.
	 * @param sizey
	 * Tamanho vertical da AirShip o qual será usado para saber a posição do tiro em relação a AirShip.
	 */
	public void shootType(int x, int y, int sizey) {
		switch(this.shootType){
		//Atira um laser de cada vez.
		case 0:
			this.shootTime += 1;
			if (this.shootTime > 12) {	
				this.shootLaserArray.shoot(x, y, sizey);
				this.shootTime = 0;
			}
			break;
			//Atira de três em três lasers.
		case 1: 
			this.shootTime += 1;
			if (this.shootTime > 12) {	
				this.shootLaserArray.shoot(x, y, sizey);
				this.shootTime = 0;
			}

			break;
			//Atira de três em três lasers
			//Atira um blaster a cada seis tiros do laser e quando o shootTime for maior que 10.
		case 2:
			if (this.shootTime > 10) {
				if (this.shootLaserArray.allShootOn) {
					this.shootblaster.shoot(x, y);
				}
			}		
			this.shootTime += 1;
			if (this.shootTime > 12) {	
				this.shootLaserArray.shoot(x, y, sizey);
				this.shootTime = 0;
			}
			break;
			//Atira de três em três lasers.
			//Atira um blaster a cada vez que o shootime seja igual a 4.
		case 3:
			if (this.shootTime > 4) {
				this.shootblaster.shoot(x, y);
			}		
			this.shootTime += 1;
			if (this.shootTime > 12) {	
				this.shootLaserArray.shoot(x, y, sizey);
				this.shootTime = 0;
			}			
			break;

		default: 
			/* Caso o player colete um item do tipo tiro após obter todas as evoluções do tiro,
			 * ele receberá o especial.
			 * 
			 */
			this.especialEnable();
		this.shootType--;
		break;
		}
	}

	public void fireEspecialShoot(int x, int y, int sizey) {
		if (!this.especialShootTest()) {
			this.especialShoot(x, y, sizey);
		}
	}

	/**
	 * Pinta de acordo com o nível do tiro.
	 * @param g
	 */
	public void paint(Graphics g) {
		switch(this.shootType){
		//Pinta se o nível do tiro for igual a 0.
		case 0:
			this.shootLaserArray.update();
			this.shootLaserArray.paint(g);
			break;
			//Pinta se o nível do tiro for igual a 1.
		case 1:
			this.shootLaserArray.update();
			this.shootLaserArray.paint(g);
			break;
			//Pinta se o nível do tiro for igual a 2.
		case 2:
			this.shootLaserArray.update();
			this.shootLaserArray.paint(g);
			this.shootblaster.paint(g);
			this.especialPaint(g);
			break;
			//Pinta se o nível do tiro for igual a 3.
		case 3:
			this.shootLaserArray.update();
			this.shootLaserArray.paint(g);
			this.shootblaster.paint(g);
			this.especialPaint(g);
			break;
		}
	}
	
	/* Início *Métodos relacionados com o vetor de especiais da nave.*/
	
	/**
	 * Reseta as variáveis do Especial.
	 *
	 */
	public void especialRestart() {
		this.activationTimes = 0;
		for (byte i = 0; i < ESPECIAL_QUANTITY; i++) {
			this.airShipEspecialShoots[i].allColorsRestart();
		}	
	}

	/**
	 * Habilita o tiro especial.
	 *
	 */
	public void especialEnable() {
		this.activationTimes++;
	}

	/**
	 * Desabilita o tiro especial.
	 *
	 */
	public void especialDisable() {
		this.activationTimes--;
	}

	/**
	 * Testa se o tiro especial pode ser disparado ou não.
	 * @return
	 * True = Está ativado. | False = Não está ativado.
	 */
	public boolean especialActivated() {
		if (this.activationTimes > 0) {
			return true;
		}
		return false;
	}

	/**
	 * Testa se o especial está em andamento ou não.
	 * @return
	 */
	public boolean especialShootTest() {
		for (byte i = 0; i < ESPECIAL_QUANTITY; i++) {
			if(this.airShipEspecialShoots[i].shootOn) {
				return true;
			}
		}	
		return false;
	}
	/**
	 * Dispara o especial.
	 *
	 */
	public void especialShoot (int x, int y, int sizey) {
		if (this.activationTimes <= 0) {
			return;
		}

		for (byte i = 0; i < ESPECIAL_QUANTITY; i++) {
			this.airShipEspecialShoots[i].shoot(x, y, sizey);
		}
		this.activationTimes--;
	}

	/**
	 * Movimenta o tiro caso o mesmo tenha sido acionado.
	 *
	 */
	public void especialUpdate() {
		for (byte i = 0; i < ESPECIAL_QUANTITY; i++) {
			if (this.airShipEspecialShoots[i].shootOn) {
				this.airShipEspecialShoots[i].update();	
			}
		}
	}

	/**
	 * Testa se algum(ns) dos especiais colidiu com o BosStage1
	 * @param bosStage1
	 * Saber a localização do mesmo.
	 * @return
	 * True = Colidiu | False = Não colidiu.
	 */
	public boolean especialColideWithBosStage1(BosStage1 bosStage1) {
		for (byte i = 0; i < ESPECIAL_QUANTITY; i++) {
			if(this.airShipEspecialShoots[i].shootOn) {
				if (this.airShipEspecialShoots[i].colideWithBosStage1(bosStage1)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 *  Decrementa a vida do bosStage1 enquanto ele estiver colidindo com o especial.
	 * Caso o bosStage1 esteja com o número de vidas igual a zero, a animação do bosStage1 é alterada
	 * para modo de explosão.
	 * @param bosStage1
	 * Saber a localização do mesmo.
	 * @return
	 * True = Decementou | False = Não decrementou.
	 */
	public boolean especialDecreaseBosStage1Life(BosStage1 bosStage1) {
		for (byte i = 0; i < ESPECIAL_QUANTITY; i++) {
			if (this.airShipEspecialShoots[i].collisionDelay) {
				if(this.airShipEspecialShoots[i].decreaseBosStage1Life(bosStage1)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Testa se algum(ns) dos especiais colidiu com o subchief
	 * @param subChief
	 * Saber a localização do mesmo.
	 * @return
	 * True = Colidiu | False = Não colidiu.
	 */
	public boolean especialColideWithSubchief(Subchief subChief) {
		for (byte i = 0; i < ESPECIAL_QUANTITY; i++) {
			if(this.airShipEspecialShoots[i].shootOn) {
				if (this.airShipEspecialShoots[i].colideWithSubchief(subChief)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Decrementa a vida do subchief enquanto ele estiver colidindo com o especial.
	 * Caso o subchief esteja com o número de vidas igual a zero, a animação do subchief é alterada
	 * para modo de explosão.
	 * @param subChief
	 * Saber a localização do mesmo.
	 * @return
	 * True = Decrementou | False = Não decrementou.
	 */
	public boolean especialDecreaseSubchiefLife(Subchief subChief) {
		for (byte i = 0; i < ESPECIAL_QUANTITY; i++) {
			if (this.airShipEspecialShoots[i].collisionDelay) {
				if (this.airShipEspecialShoots[i].decreaseSubchiefLife(subChief)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Decrementa em um a vida do especial quando colidida com o subchief ou com o bosStage1.
	 *
	 */
	public void especialDecreaseEspecialLife() {
		for (byte i = 0; i < ESPECIAL_QUANTITY; i++) {
			//Condição para saber de qual especial deve ser decrementado uma vida.
			if (this.airShipEspecialShoots[i].collisionDelay) {
				this.airShipEspecialShoots[i].decreaseEspecialLife();
			}
		}
	}

	/**
	 * Testa se algum(ns) do(s) especial(ais) colidiu com o(s) meteoro(s).
	 * @param meteorArray
	 * Saber a localização de todos os meteoros.
	 * @return
	 * True = Colidiu com algum(ns) meteoro(s). | False = Não colidiu com nenhum meteoro.
	 */
	public boolean especialColideWithMeteor(MeteorArray meteorArray) {
		for (byte i = 0; i < ESPECIAL_QUANTITY; i++) {
			if(this.airShipEspecialShoots[i].shootOn) {
				if (this.airShipEspecialShoots[i].colideWithMeteor(meteorArray)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Decrementa em um a vida do especial.
	 *
	 */
	public void especialDecreaseEspecialLifeAgainstMeteor() {
		for (byte i = 0; i < ESPECIAL_QUANTITY; i++) {
			if (this.airShipEspecialShoots[i].collisionMeteor) {
				this.airShipEspecialShoots[i].decreaseEspecialLife();
			}
		}
	}

	/**
	 * Desenha os tiros especiais.
	 * @param g
	 */
	public void especialPaint(Graphics g) {
		for (byte i = 0; i < ESPECIAL_QUANTITY; i++) {
			this.airShipEspecialShoots[i].paint(g);
		}
	}
	/* Fim *Métodos relacionados com o vetor de especiais da nave.*/
}
