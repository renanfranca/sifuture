import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class BackGround {
	/** Altura da imagem stars.*/
	private final int Hstars;
	
	/** Comprimento da imagem stars.*/
	private final int Wstars;
	
	/** Pixel do plano de fundo de estrelas.*/
	private Image stars;
	
	/** Criada para evitar que seja contruida toda vez no for.*/
	private int i;
	
	/** Criada para evitar que seja contruida toda vez no for.*/
	private int j;

	public BackGround(){
		try{
			this.stars = Image.createImage("/background.png");
		} catch (Exception e){
			e.printStackTrace();
		}
		this.Hstars = this.stars.getHeight();
		this.Wstars = this.stars.getWidth();
	}

	/**
	 *Desenha um novo plano de fundo baseado nas coordenadas x & y.
	 */
	public final void paint(Graphics g, int x, int y) {
		//Desenha o fundo estrelado.
		for (i = 30; i < Midlet.height ; i += this.Hstars) {
			for (j = - x; j < Midlet.width + x; j += this.Wstars) {
				g.drawImage(stars, j, i, 0);
			}
		}
	}
}
