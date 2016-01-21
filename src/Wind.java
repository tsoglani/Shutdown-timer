import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Wind extends Window {
	private Thread usingthread;
	private boolean willClose;

	public Wind(Frame owner, final Fr fr, int timer) {
		super(owner);
		// if(w==null){
		// w=new Window(null)
		// {
		//
		// }
		int width = 500, height = 100;
		setBounds(Toolkit.getDefaultToolkit().getScreenSize().width - width, 0,
				width, height);
		JButton cancel = new JButton("Cancel");
		JPanel pan = new JPanel();
		pan.add(cancel);
		add(pan, BorderLayout.AFTER_LAST_LINE);

		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				hide();
				setVisible(false);
				willClose = false;
				System.gc();
				fr.setVisible(true);

			}
		});
		try {
			shutDownIn(timer*60);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setOpacity(0.5f);
		setAlwaysOnTop(true);
		setFocusable(false);
		setBackground(Color.DARK_GRAY);
		setVisible(true);
	}

	String message = null;

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (message == null) {
			return;
		}
		final Font font = getFont().deriveFont(48f);
		g.setFont(font);
		g.setColor(Color.RED);

		FontMetrics metrics = g.getFontMetrics();
		g.drawString(message, 0, getHeight() / 2);
	}

	int time;

	private void shutDownIn(int maxTime) throws InterruptedException {
		if (usingthread != null && usingthread.isAlive()) {
			return;
		}
		time = maxTime;
		try {
			usingthread = new Thread() {

				@Override
				public void run() {

					try {
						while (time > 0 || !willClose) {
							Thread.sleep(1000);
							time--;
							message=Integer.toString(time);
							// label.setText(Integer.toString(time / 60) + " : "
							// + Integer.toString(time % 60));
							repaint();
							revalidate();
						}
						if (willClose) {
							shutdown();
							willClose = true;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			};
			usingthread.start();

		} catch (RuntimeException ex) {
			ex.printStackTrace();
		}
	}

	public static void shutdown() throws RuntimeException, IOException {
		String shutdownCommand;
		String operatingSystem = System.getProperty("os.name");

		if ("Linux".equals(operatingSystem)
				|| "Mac OS X".equals(operatingSystem)) {
			shutdownCommand = "shutdown -h now";
		} else if ("Windows".equals(operatingSystem)) {
			shutdownCommand = "shutdown -s";
		} else {
			throw new RuntimeException("Unsupported operating system.");
		}
		Runtime.getRuntime().exec(shutdownCommand);
		System.exit(0);
	}

}
