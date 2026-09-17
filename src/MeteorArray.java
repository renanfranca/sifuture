/*
 * Um Array de meteoros com uma quantidade de meteoros determinada pela variável METEOR_QUANTITY.
 */
import javax.microedition.lcdui.Graphics;

public class MeteorArray {
	/** Quantidade de meteoros.*/
	public final byte METEOR_QUANTITY = 8;

	/** Quantidade de meteoros na direção horizontal*/
	public final byte HORIZONTAL_QUANTITY = 6;

	/** Quantidade de meteoros na direção horizontal*/
	public final byte VERTICAL_QUANTITY = 2;

	/** Ativa os meteoros verticais.*/
	private boolean meteorVerticalActivate;

	/** Vetor que irá conter os meteoros.*/
	public Meteor[] meteor = new Meteor[METEOR_QUANTITY];

	public MeteorArray() {
		try{
		
			for (byte i = 0; i < HORIZONTAL_QUANTITY; i++) {
			
				this.meteor[i] = new Meteor();
				this.meteor[i].restartHorizontal();
			}
			
			for (byte i = HORIZONTAL_QUANTITY; i < METEOR_QUANTITY; i++) {
				
				this.meteor[i] = new Meteor();
				this.meteor[i].restartVertical();
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		this.meteorVerticalActivate = false;
	}

	/**
	 * Reseta os meteoros horizontais como os verticais. Deixando-os pronto para serem lançados.
	 *
	 */
	public void restart() {
		for (byte i = 0; i < METEOR_QUANTITY; i++) {
				if (this.meteor[i].meteorType == 0) {
					this.meteor[i].restartHorizontal();
				}
				if (this.meteor[i].meteorType == 1) {
					this.meteor[i].restartVertical();
				}
		}
	}

	/**
	 * Ativa os meteoros verticais.
	 *
	 */
	public void activateMeteorVertical(){
		//Se não estiverem ativados.
		if (!this.meteorVerticalActivate) {
			this.meteorVerticalActivate = true;
			//Ativando cada um dos meteoros verticais.
			for (byte i = HORIZONTAL_QUANTITY; i < METEOR_QUANTITY; i++) {
				this.meteor[i].verticalMeteorActivate = true;
			}
		}
	}

	/**
	 * Movimenta os meteoros horizontais e os verticais caso estejam ativados.
	 *
	 */
	public void update() {
		//Movimenta os horizontais.
		for (byte i = 0; i < HORIZONTAL_QUANTITY; i++) {
			this.meteor[i].updateHorizontal();
		}
		//Se os meteoros verticais estiverem ativados.
		if (this.meteorVerticalActivate) {
			this.meteorVerticalActivate = false;
			for (byte j = HORIZONTAL_QUANTITY; j < METEOR_QUANTITY; j++) {
				if (!this.meteorVerticalActivate) {
					//Procurando se ainda existem meteoros verticais ativos. 
					this.meteorVerticalActivate = this.meteor[j].updateVertical();
				} else {
					//Existem meteoros verticais ativos e a variável meteorVerticalActivate continua true.
					this.meteor[j].updateVertical();
				}
			}
		}
	}

	/**
	 * Pinta os meteoros horizontais e os verticais caso estejam ativos.
	 * @param g
	 */
	public final void paint(Graphics g) {
		//Testa se os verticais estão ativos.
		if (this.meteorVerticalActivate) {
			//Desenha tanto os horizontais quanto os verticais.
			for (byte i = 0; i < METEOR_QUANTITY; i++) {
				this.meteor[i].paint(g);
			}
		} else {
			//Desenha somente os horizontais.
			for (byte i = 0; i < HORIZONTAL_QUANTITY; i++) {
				this.meteor[i].paint(g);
			}
		}
	}
}
