package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import controller.CommandCenter;
import model.units.Ambulance;
import model.units.DiseaseControlUnit;
import model.units.Evacuator;
import model.units.FireTruck;
import model.units.GasControlUnit;

@SuppressWarnings("serial")
public class GameView extends JFrame {
	public JFrame frame = new JFrame();
	public JLabel back = new JLabel();
	public JLabel forth = new JLabel();
	public JPanel Info = new JPanel();
	public JPanel Log = new JPanel();
	public JPanel Rescue = new JPanel(new GridLayout(10, 10));
	public JPanel Current = new JPanel();
	public JPanel junits;
	public JPanel Causalities = new JPanel();
	public JTextArea CausalArea = new JTextArea();
	public JTextArea textArea = new JTextArea(25, 17);
	public JTextArea TextCurrent = new JTextArea();
	public JTextArea Logtext = new JTextArea(13, 17);
	public JButton[][] CellsHolder = new JButton[10][10];
	public JButton[][] unnits;
	// ImageIcon background = new ImageIcon("src/pics/Units.jpg");
	ImageIcon newback1;;
	public JButton truck;
	public JButton evac;
	public JButton ambulance;
	public JButton disease;
	public JButton gas;
	public JPanel available = new JPanel();
	public JTextArea availableText = new JTextArea(13, 17);
	public CommandCenter xxx = new CommandCenter();

	public GameView() throws Exception {
		TitledBorder borderava = new TitledBorder("AvailableUnits");
		borderava.setTitleFont(new Font("Arial", Font.BOLD, 18));
		borderava.setTitleJustification(TitledBorder.CENTER);
		borderava.setTitlePosition(TitledBorder.TOP);
		available.setBorder(borderava);
		available.setBounds(1200, 420, 400, 420);
		available.setBackground(Color.BLUE);
		available.setVisible(true);
		availableText.setFont(new Font("Serif", Font.BOLD, 22));
		availableText.setOpaque(false);
		availableText.setEditable(false);
		availableText.setBackground(Color.RED);
		// JScrollPane scroll2 = new JScrollPane(availableText);
		available.add(availableText);

		TitledBorder border = new TitledBorder("Rescue");
		border.setTitleJustification(TitledBorder.CENTER);
		border.setTitlePosition(TitledBorder.TOP);
		Rescue.setBorder(border);
		TitledBorder border1 = new TitledBorder("Game Info");
		border1.setTitleJustification(TitledBorder.CENTER);
		border1.setTitlePosition(TitledBorder.TOP);
		border1.setTitleFont(new Font("Arial", Font.BOLD, 16));
		TitledBorder borderlog = new TitledBorder("Log");
		borderlog.setTitleJustification(TitledBorder.CENTER);
		borderlog.setTitlePosition(TitledBorder.TOP);
		borderlog.setTitleFont(new Font("Arial", Font.BOLD, 16));
		Log.setBorder(borderlog);
		Info.setBorder(border1);
		textArea.setFont(new Font("Serif", Font.BOLD, 22));
		textArea.setOpaque(false);
		textArea.setEditable(false);
		Logtext.setFont(new Font("Serif", Font.BOLD, 22));
		Logtext.setOpaque(false);
		Logtext.setEditable(false);
		JScrollPane scroll = new JScrollPane(Logtext);
		Causalities.setBounds(906, 0, 299, 213);
		Causalities.setVisible(true);
		Current.setBounds(400, 0, 305, 213);
		Current.setBackground(Color.cyan);
		CausalArea.setFont(new Font("Serif", Font.BOLD, 22));
		CausalArea.setOpaque(false);
		CausalArea.setEditable(false);
		Causalities.add(CausalArea);
		TitledBorder border3 = new TitledBorder("Number of causalities");
		border3.setTitleJustification(TitledBorder.CENTER);
		border3.setTitlePosition(TitledBorder.TOP);
		border3.setTitleFont(new Font("Arial", Font.BOLD, 16));
		Causalities.setBorder(border3);
		Causalities.setBackground(Color.CYAN);
		Current.setVisible(true);
		TextCurrent.setFont(new Font("Serif", Font.BOLD, 22));
		TextCurrent.setOpaque(false);
		TextCurrent.setEditable(false);
		Current.add(TextCurrent);
		TitledBorder border2 = new TitledBorder("CURRENT CYCLE");
		border2.setTitleJustification(TitledBorder.CENTER);
		border2.setTitlePosition(TitledBorder.TOP);
		border2.setTitleFont(new Font("Arial", Font.BOLD, 16));
		Current.setBorder(border2);
		JScrollPane scroll2 = new JScrollPane(textArea);

		Info.setBounds(0, 0, 400, 1000);
		Info.setBackground(Color.gray);
		Info.setVisible(true);
		Info.add(scroll2);
		// Image img1 = background.getImage();
		// Image newback_1 = img1.getScaledInstance(junits.getWidth(),
		// junits.getHeight(), Image.SCALE_SMOOTH);
		// newback1 = new ImageIcon(newback_1);
		// back.setIcon(newback1);
		// back.setVisible(true);
		Rescue.setBounds(400, 100, 800, 631);
		Rescue.setVisible(true);
		// Rescue.setBackground(Color.red);

		Log.setBounds(1200, 0, 400, 420);
		Log.setVisible(true);
		Log.setBackground(Color.gray);
		Log.add(scroll);
		// Log.add(forth);
		// junits.add(back);

		/*
		 * JButton evac = new JButton("Evacuator"); evac.setBounds(420, 750, 100, 100);
		 * // evac.setBackground(Color.black); evac.setVisible(true);
		 * evac.addActionListener(new CommandCenter()); frame.add(evac); JButton truck =
		 * new JButton();
		 * 
		 * // junits.add(back); // junits.setVisible(true); truck.setText("FireTruck");
		 * truck.setVisible(true); truck.setBounds(570, 750, 100, 100);
		 * truck.addActionListener(new CommandCenter()); frame.add(truck); JButton
		 * ambulance = new JButton("Ambulance"); ambulance.setBounds(730, 750, 100,
		 * 100); ambulance.addActionListener(new CommandCenter()); frame.add(ambulance);
		 * 
		 * JButton disease = new JButton(); disease.setText("DiseaseCU");
		 * disease.setBounds(900, 750, 100, 100); disease.setVisible(true);
		 * disease.addActionListener(new CommandCenter()); frame.add(disease);
		 * 
		 * JButton gas = new JButton("GasCu"); gas.setBounds(1070, 750, 100, 100);
		 * gas.setVisible(true); gas.addActionListener(new CommandCenter());
		 * frame.add(gas);
		 */
		JButton NxtCycle = new JButton("NextCycle");
		NxtCycle.setBackground(Color.MAGENTA);
		NxtCycle.setBounds(705, 20, 200, 70);
		NxtCycle.addActionListener(xxx);
		int x = 0;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				JButton jb = new JButton();
				jb.setText(x + "");
				CellsHolder[i][j] = jb;
				CellsHolder[i][j].setVisible(true);

				// CellsHolder[i][j].setBackground(Color.black);

				// CellsHolder[i][j].addActionListener(new CommandCenter());
				Rescue.add(CellsHolder[i][j]);
				x++;
			}
		}
		JButton jb = getCellsHolder()[0][0];

		ImageIcon b = new ImageIcon("src/pics/Units.jpg");
		Image img3 = b.getImage();
		Image newback_4 = img3.getScaledInstance(85, 55, Image.SCALE_SMOOTH);
		ImageIcon newback3 = new ImageIcon(newback_4);
		jb.setIcon(newback3);
		jb.addActionListener(xxx);

		int x1 = CommandCenter.emergencyUnits.size();
		junits = new JPanel(new GridLayout(1, x1));
		junits.setBounds(405, 730, 790, 140);
		unnits = new JButton[1][x1];

		for (int j = 0; j < x1; j++) {
			if (CommandCenter.emergencyUnits.get(j) instanceof Evacuator) {

				JButton d = new JButton();
				d.setText("Evacuator" + CommandCenter.emergencyUnits.get(j).getUnitID());
				ImageIcon b1 = new ImageIcon("src/pics/Evacuator.png");
				Image img31 = b1.getImage();
				Image newback_41 = img31.getScaledInstance(165, 200, Image.SCALE_SMOOTH);
				ImageIcon newback31 = new ImageIcon(newback_41);

				d.setIcon(newback31);
				unnits[0][j] = d;
				unnits[0][j].setVisible(true);
				unnits[0][j].addActionListener(xxx);
				junits.add(unnits[0][j]);
			}
			if (CommandCenter.emergencyUnits.get(j) instanceof Ambulance) {

				Ambulance a = (Ambulance) CommandCenter.emergencyUnits.get(j);
				JButton d = new JButton("Ambulance" + a.getUnitID());
				ImageIcon b1 = new ImageIcon("src/pics/Ambulance.jpg");
				Image img31 = b1.getImage();
				Image newback_41 = img31.getScaledInstance(165, 160, Image.SCALE_SMOOTH);
				ImageIcon newback31 = new ImageIcon(newback_41);
				d.setIcon(newback31);

				unnits[0][j] = d;
				unnits[0][j].setVisible(true);
				unnits[0][j].addActionListener(xxx);
				junits.add(unnits[0][j]);
			}
			if (CommandCenter.emergencyUnits.get(j) instanceof DiseaseControlUnit) {
				DiseaseControlUnit d = (DiseaseControlUnit) CommandCenter.emergencyUnits.get(j);
				JButton jj = new JButton("DiseaseCU" + d.getUnitID());
				ImageIcon b1 = new ImageIcon("src/pics/GasLeak.jpg");
				Image img31 = b1.getImage();
				Image newback_41 = img31.getScaledInstance(165, 180, Image.SCALE_SMOOTH);
				ImageIcon newback31 = new ImageIcon(newback_41);
				jj.setIcon(newback31);
				unnits[0][j] = jj;
				unnits[0][j].setVisible(true);
				unnits[0][j].addActionListener(xxx);
				junits.add(unnits[0][j]);
			}
			if (CommandCenter.emergencyUnits.get(j) instanceof GasControlUnit) {
				GasControlUnit g = (GasControlUnit) CommandCenter.emergencyUnits.get(j);
				JButton d = new JButton();
				d.setText("GasControlUnit" + g.getUnitID());
				ImageIcon b1 = new ImageIcon("src/pics/Diseaseunit.jpg");
				Image img31 = b1.getImage();
				Image newback_41 = img31.getScaledInstance(165, 160, Image.SCALE_SMOOTH);
				ImageIcon newback31 = new ImageIcon(newback_41);
				d.setIcon(newback31);

				unnits[0][j] = d;
				unnits[0][j].setVisible(true);
				unnits[0][j].addActionListener(xxx);
				junits.add(unnits[0][j]);
			}
			if (CommandCenter.emergencyUnits.get(j) instanceof FireTruck) {

				FireTruck f = (FireTruck) CommandCenter.emergencyUnits.get(j);
				JButton d = new JButton();

				d.setText("FireTruck" + f.getUnitID());
				ImageIcon b1 = new ImageIcon("src/pics/FireTruck.jpg");
				Image img31 = b1.getImage();
				Image newback_41 = img31.getScaledInstance(165, 140, Image.SCALE_SMOOTH);
				ImageIcon newback31 = new ImageIcon(newback_41);
				d.setIcon(newback31);
				unnits[0][j] = d;
				unnits[0][j].setVisible(true);
				unnits[0][j].addActionListener(xxx);
				junits.add(d);
			}
		}
		junits.setVisible(true);
		// junits.setBackground(Color.RED);

		frame.setTitle("Balabizo Disasters");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(150, 60, 1000, 1000);
		frame.setSize(new Dimension(1580, 900));
		frame.setLayout(null);

		frame.add(NxtCycle);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.add(junits);
		frame.add(Log, BorderLayout.EAST);
		frame.add(Info, BorderLayout.WEST);
		frame.add(Rescue);
		frame.add(Causalities);
		frame.add(Current);
		frame.add(available);
		frame.validate();
	}

	public JButton[][] getCellsHolder() {
		return CellsHolder;
	}

	public void setCellsHolder(JButton[][] cellsHolder) {
		CellsHolder = cellsHolder;
	}

	public void addInfobut(JButton b) {
		Rescue.add(b);
	}

}
