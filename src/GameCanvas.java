import java.io.IOException;
import javax.microedition.lcdui.*;
/*
 * Botar intervalo entre tiros dos inimigos!
 * Resetar teclas ao entrar no modo pause;
 */
public class GameCanvas extends Canvas {

	private int RESULT_QUANTITY = 4;

	/** Nave */
	private AirShip airship;

	/** Grupo de meteoros.*/ 
	private MeteorArray meteorArray;

	/** Plano de fundo */
	private BackGround back;

	/** Gerencia os eventos da fase e a vida da nave.*/
	private StageCount stagecount;

	/** Posição X do background*/
	private int backX;	

	/** Mostra o score na tela.*/
	private int totalScore;

	private Image score;

	private boolean ok = false;

	private Image[] result = new Image[RESULT_QUANTITY];

	/**Usado para encontrar erros no programa, faz uma atribuição a esta variável em cada classe!
	só assim saberemos por qual classe está passando sem dá erro e imprimimos está variável na tela/*/
	public static String erro = "Sem Erros";

	/**
	 * @param cor
	 * @throws IOException
	 */
	public GameCanvas(){
		try{
			Midlet.height 	= this.getHeight();
			Midlet.width  	= this.getWidth();
			this.score		= Image.createImage("/score1.png");
			for (int i = 0; i < RESULT_QUANTITY; i++) {
				this.result[i] = Image.createImage("/result" + i + ".png");
			}
			this.stagecount  = new StageCount();
		
			this.back     	 = new BackGround();
		
			this.airship  	 = new AirShip();
		
			this.meteorArray = new MeteorArray();
		

		} catch (Exception e){
			e.printStackTrace();
		}
		this.totalScore = 0;
		this.backX = 5;

	}

	/**	Captura as Teclas pressionadas */
	protected void keyPressed(int keyCode) {
		if (keyCode == KEY_NUM1) {
			this.airship.fireEspecial();
		}

		switch(getGameAction(keyCode)) {
		case FIRE:
			if ((!this.stagecount.stageEnd)||(!this.ok)) {
				try {
					Midlet.instance.changeScreen();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			} else{
				this.totalScore = 0;
				airship.restart();
				this.stagecount.restart();
				this.meteorArray.restart();
				MenuCanvas.restartGame();
				try {
					Midlet.instance.changeScreen();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
			break;

		case UP:
			airship.upPressed();
			break; 

		case RIGHT:
			airship.rightPressed();
			break;

		case DOWN:
			airship.downPressed();
			break;

		case LEFT:	
			airship.leftPressed();
			break;
		}
	}



	/** Responsável por finalizar uma ação executada pelo usuário
	 e desenhar a imagem default da nave. */
	protected void keyReleased(int keyCode) {
		switch(getGameAction(keyCode)) {
		case UP:
			airship.upReleased();
			break;

		case DOWN:
			airship.downReleased();
			break;

		case RIGHT:
			airship.rightReleased();
			break;

		case LEFT:
			airship.leftReleased();
			break;
		}
	}

	/**
	 *Desenha o background e a nova imagem da nave. 
	 */
	public final void paint(Graphics g) {
		//Movimenta o backGround!
		if (backX >= 55) {
			this.backX = 0;
		} else{ 
			this.backX += 5;
		}
		back.paint(g, this.backX, 0);

		//Meteoros.
		meteorArray.update();			
		meteorArray.paint(g);


		//Testa caso o Bos foi acionado.
		if (this.stagecount.bosStage1.testActivation()) {
			//Testa se o stagio não acabou.
			if (!this.stagecount.stageEnd) {		

				//Testa colisão entre a nave e o subchief.
				if (airship.airshipBosStage1Collision(this.stagecount.bosStage1)) {
					//Se o subchief morreu chama um item como recompensa.
					this.stagecount.itemArray.cast((byte) 0);
					this.stagecount.score += this.stagecount.bosStage1.reward();
				}

				//Testa colisão entre o laser e o subchefe.
				if (airship.laserBosStage1Collision(this.stagecount.bosStage1)) {
					//Se o subchief morreu chama um item como recompensa.
					this.stagecount.itemArray.cast((byte) 0);
					this.stagecount.score += this.stagecount.bosStage1.reward();
				}

				//Testa a colisão entre o blaster e o subchief.
				if (airship.blasterBosStage1Collision(this.stagecount.bosStage1)) {
					//Se o subchief morreu chama um item como recompensa.
					this.stagecount.itemArray.cast((byte) 0);
					this.stagecount.score += this.stagecount.bosStage1.reward();
				}

				//Testa a colisão entre o blaster e o subchief.
				if (airship.especialBosStage1Collision(this.stagecount.bosStage1)) {
					//Se o subchief morreu chama um item como recompensa.
					this.stagecount.itemArray.cast((byte) 0);
					this.stagecount.score += this.stagecount.bosStage1.reward();
				}

				//Testa colisão do bosStage1 com o meteoro.
				this.stagecount.bosStage1.meteorCollision(meteorArray);

				//Testa se o Bos acertou o tiro tipo 1 na Airship.
				this.stagecount.bosStage1.shoot1Collision(airship);
				//Testa se o Bos acertou o tiro tipo 2 na Airship
				this.stagecount.bosStage1.shoot2Collision(airship);
			}

			//Faz o Update do BosStage1.
			this.stagecount.bosStage1Update(g);
		}

		//Testa caso o subchief foi acionado.
		if (this.stagecount.subChief.testActivation()) {
			//Testa se o stagio não acabou.
			if (!this.stagecount.stageEnd) {	

				//Testa colisão entre a nave e o subchief.
				if (airship.airshipSubchiefCollision(this.stagecount.subChief)) {
					//Se o subchief morreu chama os item como recompensa.
					this.stagecount.itemArray.cast((byte) 0);
					this.stagecount.itemArray.cast((byte) 1);
					this.stagecount.score += this.stagecount.subChief.reward();
				}

				//Testa colisão entre o laser e o subchefe.
				if (airship.laserSubchiefCollision(this.stagecount.subChief)) {
					//Se o subchief morreu chama os item como recompensa.
					this.stagecount.itemArray.cast((byte) 0);
					this.stagecount.itemArray.cast((byte) 1);
					this.stagecount.score += this.stagecount.subChief.reward();
				}

				//Testa a colisão entre o blaster e o subchief.
				if (airship.blasterSubchiefCollision(this.stagecount.subChief)) {
					//Se o subchief morreu chama os item como recompensa.
					this.stagecount.itemArray.cast((byte) 0);
					this.stagecount.itemArray.cast((byte) 1);
					this.stagecount.score += this.stagecount.subChief.reward();
				}

				//Testa a colisção entre o especiao e o subchief.
				if (airship.especialSubchiefCollision(this.stagecount.subChief)) {
					//Se o subchief morreu chama os item como recompensa.
					this.stagecount.itemArray.cast((byte) 0);
					this.stagecount.itemArray.cast((byte) 1);
					this.stagecount.score += this.stagecount.subChief.reward();
				}

				//Testa se o subcheif acertou o tiro na nave.
				this.stagecount.subChief.laserCollision(airship);
			}

			//Faz o Update do Subchief.
			this.stagecount.subchiefUpdate(g);
		}

		//Ativa os meteoros na direção vertical.
		this.stagecount.meteorVertical(meteorArray);

		//Testa caso algum item foi ativado ou seja está visível ao player.
		if (this.stagecount.itemArray.castOn) {
			//Testa se o stagio não acabou.  
			if (!this.stagecount.stageEnd) {
				//Testa se a nave pegou o item.
				if (stagecount.itemArray.colideWith(airship)) {
					//Recompensa caso consiga obter o item.
					this.stagecount.score += this.stagecount.itemArray.item[0].VALUE;
					if (this.stagecount.itemArray.CollidedItemType() == 0) {
						this.airship.allShoots.changeShootType();
					}else {
						this.airship.lifeIncrease();
					}				
				}
			}
		}

		//A AirShip fica na frente de qualquer animação.
		airship.update();
		airship.fire();
		airship.paint(g);

		//Testa colisão da AirShip com o meteoro.
		if (!this.stagecount.stageEnd) {
			if(airship.airshipMeteorCollision(meteorArray)) {
				this.stagecount.score += meteorArray.meteor[0].VALUE;
			}

			//Testa colisão do laser com o meteoro..
			if(airship.laserMeteorCollision(meteorArray)) {
				this.stagecount.score += meteorArray.meteor[0].VALUE;
			}

			//Testa colisão do blaster com o meteoro..
			if(airship.blasterMeteorCollision(meteorArray)) {
				this.stagecount.score += meteorArray.meteor[0].VALUE;
			}

			//Testa a colisão do especial com o meteoro.
			if (airship.especialMeteorCollision(meteorArray)) {
				this.stagecount.score += meteorArray.meteor[0].VALUE;
			}
		}

		/* Se o número de vidas for igual a 0 ocorre um restart no jogo*/
		if ((airship.lives == 0)||(this.stagecount.stageEnd)) {
			if (!this.stagecount.stageEnd) {
				this.stagecount.stageEnd = true;	
			} else{
				if (this.totalScore < stagecount.score) {
					g.drawImage(this.score, Midlet.width/4, Midlet.height/2, 0);
					this.stagecount.numberDraw(g, this.totalScore, Midlet.width/4 + this.score.getWidth(), Midlet.height/2, false);
					totalScore += 5;
				} else{
					stagecount.score = 0;
					if (this.totalScore < 1500) {
						g.drawImage(this.score, Midlet.width/4, Midlet.height/2, 0);
						this.stagecount.numberDraw(g, this.totalScore, Midlet.width/4 + this.score.getWidth(), Midlet.height/2, false);
						g.drawImage(this.result[0], Midlet.width/4, Midlet.height/2 + this.score.getHeight(), 0);
					}

					if ((this.totalScore > 1500) && (this.totalScore < 2200)) {
						g.drawImage(this.score, Midlet.width/4, Midlet.height/2, 0);
						this.stagecount.numberDraw(g, this.totalScore, Midlet.width/4 + this.score.getWidth(), Midlet.height/2, false);
						g.drawImage(this.result[1], Midlet.width/4, Midlet.height/2 + this.score.getHeight(), 0);
					}

					if ((this.totalScore > 2200) && (this.totalScore < 3300)) {
						g.drawImage(this.score, Midlet.width/4, Midlet.height/2, 0);
						this.stagecount.numberDraw(g, this.totalScore, Midlet.width/4 + this.score.getWidth(), Midlet.height/2, false);
						g.drawImage(this.result[2], Midlet.width/4, Midlet.height/2 + this.score.getHeight(), 0);
					}

					if (this.totalScore >= 3300) {
						g.drawImage(this.score, Midlet.width/4, Midlet.height/2, 0);
						this.stagecount.numberDraw(g, this.totalScore, Midlet.width/4 + this.score.getWidth(), Midlet.height/2, false);
						g.drawImage(this.result[3], Midlet.width/4, Midlet.height/2 + this.score.getHeight(), 0);
					}
					this.ok = true;
				}
			}
		}

		//Desenha as vidas da nave e controla os eventos do stage1.
		stagecount.paint(g, airship.lives);

		//Se o especial da airship estiver ativado.
		airship.paintEspecialSymbol(g);		
	}
}

