import javax.microedition.lcdui.Graphics;

public class ShootLaserArray {
	/** Quantidade de Lasers*/
	public final byte LASER_QUANTITY = 3;

	/** Só pode atirar o segundo tiro quando o primeiro desaparecer.*/
	private final byte LEVEL0 = 0;

	/** Pode atirar mesmo com o primeiro e o segundo tiro ainda na tela.*/
	public final byte LEVEL1 = 1;

	/** O nível do laser - LEVEL0 ou LEVEL1*/
	public byte laserArrayLevel;

	/** Conta o número de laser já atirados.*/
	private byte laserCount;

	/** True = Todos os lasers foram atirados | False = Pelo ao menos um laser não foi atirado.*/
	public boolean allShootOn;

	/** Vetor que irá conter os Lasers*/
	public ShootLaser[] shootLaser = new ShootLaser[LASER_QUANTITY];

	public ShootLaserArray() {
		try{
			for (byte i = 0; i < LASER_QUANTITY; i++) {
				this.shootLaser[i] = new ShootLaser();
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		this.laserArrayLevel = LEVEL0;
		this.laserCount      = 0;
	}

	/**
	 * Regride todas as evoluções. 
	 * Volta para o tiro mais básico.
	 *
	 */
	public void restart() {
		this.laserArrayLevel = LEVEL0;
		this.laserCount      = 0;
		for (byte i = 0; i < LASER_QUANTITY; i++) {
			this.shootLaser[i].restart();
		}
	}
	/**
	 * Gerencia o nível do tiro.
	 * @param x
	 * 		posição x da AirShip.
	 * @param y
	 * 		posição y da AirShip.
	 * @param sizey
	 * 		altura da imagem da AirShip.
	 */
	public void shoot(int x, int y, int sizey) {
		switch (this.laserArrayLevel) {
		//Corresponde ao LEVEL0.
		case 0:
			if (!this.shootLaser[0].shootOn) {
				this.shootLaser[0].shoot(x, y, sizey);
			}
			break;
			//Corresponde ao LEVEL1.
		case 1:
			for (byte i = 0; i < LASER_QUANTITY; i++) {
				if (!this.shootLaser[i].shootOn) {
					this.shootLaser[i].shoot(x, y, sizey);
					break;
				}
			}	
			//Observa se já foi atirado 6 lasers para poder soltar o especial( ShootBlaster) caso o player já tenha.
			this.laserCount++;
			if (this.laserCount == LASER_QUANTITY * 2) {
				this.allShootOn = true;
				this.laserCount = 0;
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
	public void update() {
		for (byte i = 0; i < LASER_QUANTITY; i++) {
			if (this.shootLaser[i].shootOn) {
				this.shootLaser[i].update();	
			}
		}
	}

	/**
	 * Evolui o nível do laser.
	 *
	 */
	public void LevelUp() {
		//Atira até três lasers ao mesmo tempo.
		this.laserArrayLevel = LEVEL1;
		//Modifica a animação do laser.
		for (byte i = 0; i < LASER_QUANTITY; i++) {
			this.shootLaser[i].levelUp();
		}
	}

	/**
	 * Regride o nível do laser.
	 *
	 */
	public void LevelDown() {
		this.laserArrayLevel = LEVEL0;
		for (byte i = 0; i < LASER_QUANTITY; i++) {
			this.shootLaser[i].levelDown();
		}
	}

	/**
	 * Testa se algum(ns) dos lasers colidiu com o BosStage1
	 * @param bosStage1
	 * Saber a localização do mesmo.
	 * @return
	 * True = Colidiu | False = Não colidiu.
	 */
	public boolean colideWithBosStage1(BosStage1 bosStage1) {
		for (byte i = 0; i < LASER_QUANTITY; i++) {
			if(this.shootLaser[i].shootOn) {
				if (this.shootLaser[i].colideWithBosStage1(bosStage1)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Decrementa a vida do bosStage1 para cada laser colidido.
	 * Caso o bosStage1 esteja com o número de vidas igual a zero, a animação do bosStage1 é alterada
	 * para modo de explosão.
	 * @param bosStage1
	 * Saber a localização do mesmo.
	 * @return
	 * True = Decementou | False = Não decrementou.
	 */
	public boolean decreaseBosStage1(BosStage1 bosStage1) {
		for (byte i = 0; i < LASER_QUANTITY; i++) {
			if (this.shootLaser[i].collision) {
				if(this.shootLaser[i].decreaseBosStage1Life(bosStage1)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Testa se algum(ns) dos lasers colidiu com o subchief
	 * @param subChief
	 * Saber a localização do mesmo.
	 * @return
	 * True = Colidiu | False = Não colidiu.
	 */
	public boolean colideWithSubchief(Subchief subChief) {
		for (byte i = 0; i < LASER_QUANTITY; i++) {
			if(this.shootLaser[i].shootOn) {
				if (this.shootLaser[i].colideWithSubchief(subChief)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Decrementa a vida do subchief para cada laser colidido.
	 * Caso o subchief esteja com o número de vidas igual a zero, a animação do subchief é alterada
	 * para modo de explosão.
	 * @param subChief
	 * Saber a localização do mesmo.
	 * @return
	 * True = Decrementou | False = Não decrementou.
	 */
	public boolean decreaseSubchiefLife(Subchief subChief) {
		for (byte i = 0; i < LASER_QUANTITY; i++) {
			if (this.shootLaser[i].collision) {
				if (this.shootLaser[i].decreaseSubchiefLife(subChief)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Testa se algum(ns) do(s) laser(s) colidiu com o(s) meteoro(s).
	 * @param meteorArray
	 * Saber a localização de todos os meteoros.
	 * @return
	 * True = Colidiu com algum(ns) meteoro(s). | False = Não colidiu com nenhum meteoro.
	 */
	public boolean colideWithMeteor(MeteorArray meteorArray) {
		for (byte i = 0; i < LASER_QUANTITY; i++) {
			if(this.shootLaser[i].shootOn) {
				if (this.shootLaser[i].colideWithMeteor(meteorArray)) {
					return true;
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
	public void searchMeteorCrashed(MeteorArray meteorArray) {
		for (byte i = 0; i < LASER_QUANTITY; i++) {
			if (this.shootLaser[i].collision) {
				this.shootLaser[i].searchMeteorCreashed(meteorArray);
			}
		}
	}

	/**
	 * Desenha o(s) laser(s).
	 * @param g
	 */
	public void paint(Graphics g) {
		for (byte i = 0; i < LASER_QUANTITY; i++) {
			this.shootLaser[i].paint(g);
		}
	}
}
