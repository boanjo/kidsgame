package play;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.print.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Multiplication implements Printable, ActionListener {

	static long x = 1;
	static long y = 10;
	JTextField xText = null;
	JTextField yText = null;

	public Multiplication() {
		UIManager.put("swing.boldMetal", Boolean.FALSE);
		JFrame f = new JFrame("Mutiplikation");
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {System.exit(0);}
		});
		JLabel xLab = new JLabel("Från och med:");
		xText = new JTextField("" +x);

		JLabel yLab = new JLabel("Upp till och med:");
		yText = new JTextField("" +y);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{119, 0};
		gridBagLayout.rowHeights = new int[]{23, 0};
		gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		f.getContentPane().setLayout(gridBagLayout);
		JButton printButton = new JButton("Print Multiplication");
		printButton.addActionListener(this);



		GridBagConstraints gbc_xLab = new GridBagConstraints();
		gbc_xLab.insets = new Insets(12, 12, 0, 12);
		gbc_xLab.anchor = GridBagConstraints.NORTHWEST;
		gbc_xLab.gridx = 0;
		gbc_xLab.gridy = 0;
		f.getContentPane().add(xLab, gbc_xLab);

		GridBagConstraints gbc_xText = new GridBagConstraints();
		gbc_xText.insets = new Insets(0, 12, 0, 12);
		gbc_xText.fill = GridBagConstraints.HORIZONTAL;
		gbc_xText.anchor = GridBagConstraints.NORTH;
		gbc_xText.gridx = 0;
		gbc_xText.gridy = 1;
		f.getContentPane().add(xText, gbc_xText);

		GridBagConstraints gbc_yLab = new GridBagConstraints();
		gbc_yLab.insets = new Insets(12, 12, 0, 12);
		gbc_yLab.anchor = GridBagConstraints.NORTHWEST;
		gbc_yLab.gridy = 2;
		f.getContentPane().add(yLab, gbc_yLab);

		GridBagConstraints gbc_yText = new GridBagConstraints();
		gbc_yText.insets = new Insets(0, 12, 0, 12);
		gbc_yText.fill = GridBagConstraints.HORIZONTAL;
		gbc_yText.anchor = GridBagConstraints.NORTH;
		gbc_yText.gridx = 0;
		gbc_yText.gridy = 3;
		f.getContentPane().add(yText, gbc_yText);

		GridBagConstraints gbc_printButton = new GridBagConstraints();
		gbc_printButton.insets = new Insets(12, 12, 12, 12);
		gbc_printButton.anchor = GridBagConstraints.CENTER;
		gbc_printButton.gridx = 0;
		gbc_printButton.gridy = 5;
		f.getContentPane().add(printButton, gbc_printButton);


		f.pack();
		f.setVisible(true);	
	}

	public int print(Graphics g, PageFormat pf, int page) throws
	PrinterException {

		if (page > 0) { /* We have only one page, and 'page' is zero-based */
			return NO_SUCH_PAGE;
		}

		/* User (0,0) is typically outside the imageable area, so we must
		 * translate by the X and Y values in the PageFormat to avoid clipping
		 */
		Graphics2D g2d = (Graphics2D)g;
		g2d.translate(pf.getImageableX(), pf.getImageableY());



		ArrayList<String> list = new ArrayList<String>();
		for(long i = x; i <= y; i++) {
			for(long j = 1; j <= 10; j++) {
				list.add(i + " x " + j + " = _____");
			}
		}

		long seed = System.nanoTime();
		Collections.shuffle(list, new Random(seed));


		g.setFont(new Font("Arial", Font.BOLD, 15));

		int xPos = 0;
		int yPos = 0;
		for(int i = 0; i < list.size(); i++) {
			//		System.out.println(list.get(i));

			if(yPos >= 30) {
				xPos++;
				yPos = 0;
			}
			g.drawString(list.get(i), 40 + (xPos * 130), 40 + (yPos * 25));

			yPos ++;
		}

		/* tell the caller that this page is part of the printed document */
		return PAGE_EXISTS;
	}

	public void actionPerformed(ActionEvent e) {

		x = Long.parseLong(xText.getText());
		y = Long.parseLong(yText.getText());

		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(this);
		boolean ok = job.printDialog();
		if (ok) {
			try {
				job.print();
			} catch (PrinterException ex) {
				/* The job did not successfully complete */
			}
		}
	}
	public static void main(String[] args) {

		if(args.length == 2) {
			x = Long.parseLong(args[0]);
			y = Long.parseLong(args[1]);
		}
		new Multiplication();

	}
}
