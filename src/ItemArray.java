import javax.microedition.lcdui.Graphics;

public class ItemArray {
	/** Quantidade de item.*/
	public final byte ITEM_QUANTITY = 2;

	/** True = Algum elemento do array colidiu com a nave. | False = Algum elemento do array não colidiu com a nave.*/
	public boolean collision;

	/** True = Se algum item do array foi ativado | False = Se nenhum item do array foi ativado.*/
	public boolean castOn;

	/** Vetor que irá conter os item.*/
	public Item[] item = new Item[ITEM_QUANTITY];

	public ItemArray() {
		try{
			for (byte i = 0; i < ITEM_QUANTITY; i++) {
				
				this.item[i] = new Item();
			}		
		} catch (Exception e){
			e.printStackTrace();
		}
		restart();
	}

	/**
	 * Reseta todos os itens, deixando prontos para serem lançados.
	 *
	 */
	public void restart() {
		for (byte i = 0; i < ITEM_QUANTITY/2; i++) {
			this.item[i].restartShootType();
		}	
		for (byte i = ITEM_QUANTITY/2; i < ITEM_QUANTITY; i++) {
			this.item[i].restartLifeType();
		}
	}

	/**
	 * Retorna o tipo de item colidido.
	 * @return
	 * 0 = o tipo do item é tiro | 1 = o tipo de item é vida.
	 */
	public int CollidedItemType() {
		for (byte i = 0; i < ITEM_QUANTITY; i++) {
			if (item[i].collision) {
				if (item[i].type == 0) {
					return 0;
				} 
			}
		}			
		return 1;
	}

	/**
	 * Lança o item de acordo com seu tipo.
	 * @param type
	 * 0 = lança o item de tiro. | 1= lança o item de vida.
	 */
	public void cast(byte type) {
		switch (type) {
		case 0:
			//Itens de tiro.
			for (byte i = 0; i < ITEM_QUANTITY/2; i++) {
				if (!item[i].castOn) {
					//se não foi lançado ainda.
					item[i].cast();
				}
			}			
			break;
		case 1:
			//Itens de vida.
			for (byte i = ITEM_QUANTITY/2; i < ITEM_QUANTITY; i++) {
				if (!item[i].castOn) {
					//se não foi lançado ainda.
					item[i].cast();
				}
			}			
			break;
		}
	}

	/**
	 * Movimenta o item caso ele tenha sido lançado.
	 *
	 */
	public void update() {
		this.castOn = false;
		for (byte i = 0; i < ITEM_QUANTITY; i++) {
			if (this.item[i].castOn) {
				this.castOn = true;
				this.item[i].update(); 
			}
		}
	}

	/**
	 * Testa se o item colidiu com a nave.
	 * @param airShip
	 * Saber a localização da mesma.
	 * @return
	 */
	public final boolean colideWith(AirShip airShip){
		for (byte i = 0; i < ITEM_QUANTITY; i++) {
			if (this.item[i].castOn) {
				if (this.item[i].colideWith(airShip)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Desenha o item que foi lançado.
	 * @param g
	 */
	public final void paint(Graphics g) {	
		for (byte i = 0; i < ITEM_QUANTITY; i++) {
			if (this.item[i].castOn) {
				this.item[i].paint(g);
			}
		}
	}
}
