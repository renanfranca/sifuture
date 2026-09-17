/*
 * Exemplo de movimentação de Objeto, no caso um retângulo, untilizando
 * as teclas 4/8/6/2 do Numpad.
 */


/**
 * 1. Testar colisão com as bordas da tela
 * 2. Utilizar estrelas no fundo
 * 3. faz as estrelas se movimentar (scrolling)
 */

import java.io.IOException;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.MIDlet;

public class Midlet extends MIDlet implements Runnable,CommandListener {
	/** Button for exiting the game. */
	private Command exitCmd  = new Command("Exit", Command.EXIT, 3);
	
	/** Menu do jogo.*/
	private static MenuCanvas canvasAzul;

	/** O jogo.*/
	private static GameCanvas canvasVermelho;

	/** Diz qual o canvas atual: 1: Azul, 2: Vermelho */
	private byte canvasAtual;

	private static boolean executando;//Determina se o programa está sendo executado ou não.

	public static Midlet instance;

	public static int width;
	public static int height;

	/**
	 * Construtor.
	 */
	public Midlet() {
		instance = this;
	}   

	public void startApp() {
		canvasAtual = 1;
		if (canvasAzul == null) {
			try{
//				canvasVermelho = new GameCanvas();
				canvasAzul = new MenuCanvas();
				canvasAzul.addCommand(exitCmd);
				canvasAzul.setCommandListener(this);
				canvasVermelho = new GameCanvas();
			} catch(Exception e){
				e.printStackTrace();
			}
			inicia();
		}

		//Mostra na tela as alterações feita pela classe RetanguloCanvas.
		Display.getDisplay(this).setCurrent(canvasAzul);

	}

	public void changeScreen() throws IOException {
		if (canvasAtual == 1) {
			/* Muda para o canvasVermelho */
			canvasAtual = 2;
			if (canvasVermelho == null) {
				canvasVermelho = new GameCanvas();
			}
			Display.getDisplay(this).setCurrent(canvasVermelho);        
		} else {

			/* Muda para o canvasAzul */
			canvasAtual = 1;
			if (canvasAzul == null) {
				canvasAzul = new MenuCanvas();
			}
			Display.getDisplay(this).setCurrent(canvasAzul);
		}
	}

	public void pauseApp() {
	}

	// Finaliza o programa.
	public final void destroyApp(boolean unconditional) {
		Midlet.stop();
		notifyDestroyed();
		canvasAzul = null;
		canvasVermelho = null;
	}

	public  final void inicia() {
		executando = true;
		Thread t = new Thread(this);
		t.start();
	}

	public static final void stop() {
		executando = false;
	}


	public static final void exit() {
		Midlet.stop();
		canvasAzul = null;
		canvasVermelho = null;	
	}


	public void run() {
		while (executando) {
			try {
				if (canvasAtual == 1) {
					canvasAzul.repaint();//Redesenha o retângulo com a nova posição.
				} else {
					canvasVermelho.repaint();//Redesenha o retângulo com a nova posição.
				}
				Thread.sleep(30);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void commandAction(Command c, Displayable d) {
		if (c == exitCmd) {
			destroyApp(false);
			notifyDestroyed();
			// TODO Auto-generated method stub
		}
	}
}