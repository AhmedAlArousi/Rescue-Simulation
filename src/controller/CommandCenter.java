package controller;

import java.awt.Image;
import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import exceptions.BuildingAlreadyCollapsedException;
import exceptions.CannotTreatException;
import exceptions.CitizenAlreadyDeadException;
import exceptions.IncompatibleTargetException;
import model.disasters.Collapse;
import model.disasters.Fire;
import model.disasters.GasLeak;

import model.events.SOSListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;

import model.units.Ambulance;
import model.units.DiseaseControlUnit;
import model.units.Evacuator;
import model.units.FireTruck;
import model.units.GasControlUnit;
import model.units.Unit;
import model.units.UnitState;
import simulation.Rescuable;
import simulation.Simulator;
import view.GameView;

public class CommandCenter implements SOSListener, ActionListener {
	private Simulator engine;
	private ArrayList<ResidentialBuilding> visibleBuildings;
	private ArrayList<Citizen> visibleCitizens;
	private static GameView vieww;
	public static ArrayList<Unit> emergencyUnits;
	static String log = "";
	static boolean flagxx = false;
	private int causal = 0;

	public CommandCenter() {
		try {
			engine = new Simulator(this);
		} catch (Exception e) {

			e.printStackTrace();
		}
		engine.setEmergencyService(this);
		visibleBuildings = new ArrayList<ResidentialBuilding>();
		visibleCitizens = new ArrayList<Citizen>();
		emergencyUnits = engine.getEmergencyUnits();

	}

	public void actionPerformed(ActionEvent e) {

		for (int i = 0; i < visibleBuildings.size(); i++) {
			ResidentialBuilding b1 = visibleBuildings.get(i);

			if (b1.getDisaster() instanceof Fire) {
				if (b1.getOccupants().size() == 0) {
					JButton jb = vieww.getCellsHolder()[b1.getLocation().getX()][b1.getLocation().getY()];
					ImageIcon b = new ImageIcon("src/pics/buildingwithfire.jpg");
					Image img1 = b.getImage();
					Image newback_1 = img1.getScaledInstance(95, 55, Image.SCALE_SMOOTH);
					ImageIcon newback1 = new ImageIcon(newback_1);
					jb.setIcon(newback1);
				} else {
					JButton jb = vieww.getCellsHolder()[b1.getLocation().getX()][b1.getLocation().getY()];
					ImageIcon b = new ImageIcon("src/pics/buildingwithcitizenandfire.jpg");
					Image img1 = b.getImage();
					Image newback_1 = img1.getScaledInstance(95, 55, Image.SCALE_SMOOTH);
					ImageIcon newback1 = new ImageIcon(newback_1);
					jb.setIcon(newback1);
				}
			}
			if (b1.getDisaster() instanceof Collapse) {
				if (b1.getOccupants().size() == 0) {
					JButton jb = vieww.getCellsHolder()[b1.getLocation().getX()][b1.getLocation().getY()];
					ImageIcon b = new ImageIcon("src/pics/buildingwithcollapseandnocitizen.jpg");
					Image img1 = b.getImage();
					Image newback_1 = img1.getScaledInstance(95, 55, Image.SCALE_SMOOTH);
					ImageIcon newback1 = new ImageIcon(newback_1);
					jb.setIcon(newback1);
				} else {
					JButton jb = vieww.getCellsHolder()[b1.getLocation().getX()][b1.getLocation().getY()];
					ImageIcon b = new ImageIcon("src/pics/buildingwithcitizenandcollapse.jpg");
					Image img1 = b.getImage();
					Image newback_1 = img1.getScaledInstance(95, 55, Image.SCALE_SMOOTH);
					ImageIcon newback1 = new ImageIcon(newback_1);
					jb.setIcon(newback1);
					vieww.frame.revalidate();
				}
			}
			if (b1.getDisaster() instanceof GasLeak) {
				if (b1.getOccupants().size() == 0) {
					JButton jb = vieww.getCellsHolder()[b1.getLocation().getX()][b1.getLocation().getY()];
					ImageIcon b = new ImageIcon("src/pics/buildingwithgasleak.jpg");
					Image img1 = b.getImage();
					Image newback_1 = img1.getScaledInstance(95, 55, Image.SCALE_SMOOTH);
					ImageIcon newback1 = new ImageIcon(newback_1);
					jb.setIcon(newback1);
				} else {
					JButton jb = vieww.getCellsHolder()[b1.getLocation().getX()][b1.getLocation().getY()];
					ImageIcon b = new ImageIcon("src/pics/buildingwithcitizenandgasleak.jpg");
					Image img1 = b.getImage();
					Image newback_1 = img1.getScaledInstance(95, 55, Image.SCALE_SMOOTH);
					ImageIcon newback1 = new ImageIcon(newback_1);
					jb.setIcon(newback1);
					vieww.frame.revalidate();
				}
			}
			if (visibleBuildings.get(i).getStructuralIntegrity() == 0) {
				ImageIcon b = new ImageIcon("src/pics/buildingcollapsedwithcitizen.jpg");
				Image img1 = b.getImage();
				Image newback_1 = img1.getScaledInstance(95, 55, Image.SCALE_SMOOTH);
				ImageIcon newback1 = new ImageIcon(newback_1);
				vieww.CellsHolder[visibleBuildings.get(i).getLocation().getX()][visibleBuildings.get(i).getLocation()
						.getY()].setIcon(newback1);
			}
			if (visibleBuildings.get(i).getOccupants().size() == 0
					&& visibleBuildings.get(i).getStructuralIntegrity() == 0) {
				ImageIcon b = new ImageIcon("src/pics/buildingcollapsedwithnocitizens.jpg");
				Image img1 = b.getImage();
				Image newback_1 = img1.getScaledInstance(95, 55, Image.SCALE_SMOOTH);
				ImageIcon newback1 = new ImageIcon(newback_1);
				vieww.CellsHolder[visibleBuildings.get(i).getLocation().getX()][visibleBuildings.get(i).getLocation()
						.getY()].setIcon(newback1);
			}
		}
		for (int i = 0; i < visibleCitizens.size(); i++) {
			if (visibleCitizens.get(i).isAlive() == false && visibleCitizens.get(i).getHp() == 0) {
				vieww.Logtext.setText(
						Update1("\n" + "A Citizen is deceased at " + "\n" + visibleCitizens.get(i).getLocation()));

				ImageIcon b = new ImageIcon("src/pics/dead.png");
				Image img1 = b.getImage();
				Image newback_1 = img1.getScaledInstance(95, 55, Image.SCALE_SMOOTH);
				ImageIcon newback1 = new ImageIcon(newback_1);
				vieww.CellsHolder[visibleCitizens.get(i).getLocation().getX()][visibleCitizens.get(i).getLocation()
						.getY()].setIcon(newback1);

				visibleCitizens.get(i).setAlive(true);
			}
		}
		if (engine.checkGameOver()) {
			JOptionPane.showMessageDialog(null, "The Game is Over" + "\n" + "Number of Causalties = " + causal);
		} else {
			if (((JButton) e.getSource()).getText().equals("0")) {
				String unitss = "";
				for (int k = 0; k < emergencyUnits.size(); k++) {

					if (emergencyUnits.get(k) instanceof Evacuator && emergencyUnits.get(k).getLocation().getX() == 0
							&& emergencyUnits.get(k).getLocation().getY() == 0) {
						Evacuator evac = (Evacuator) emergencyUnits.get(k);
						unitss += "\n" + "Unit's ID : " + evac.getUnitID() + "\n" + "Evacuator Unit" + "\n"
								+ "Location : " + evac.getLocation() + "\n" + "StepsPerCycle = "
								+ evac.getStepsPerCycle() + "\n" + "Unit State : " + evac.getState() + "\n"
								+ "Number of passengers = " + evac.getPassengers().size() + "\n";

					}
					if (emergencyUnits.get(k) instanceof FireTruck && emergencyUnits.get(k).getLocation().getX() == 0
							&& emergencyUnits.get(k).getLocation().getY() == 0) {
						FireTruck evac = (FireTruck) emergencyUnits.get(k);
						unitss += "\n" + "Unit's ID : " + evac.getUnitID() + "\n" + "Fire Unit" + "\n" + "Location : "
								+ evac.getLocation() + "\n" + "StepsPerCycle = " + evac.getStepsPerCycle() + "\n"
								+ "Unit State : " + evac.getState() + "\n";

					}
					if (emergencyUnits.get(k) instanceof GasControlUnit
							&& emergencyUnits.get(k).getLocation().getX() == 0
							&& emergencyUnits.get(k).getLocation().getY() == 0) {
						GasControlUnit evac = (GasControlUnit) emergencyUnits.get(k);
						unitss += "\n" + "Unit's ID : " + evac.getUnitID() + "\n" + "Fire Unit" + "\n" + "Location : "
								+ evac.getLocation() + "\n" + "StepsPerCycle = " + evac.getStepsPerCycle() + "\n"
								+ "Unit State : " + evac.getState() + "\n";

					}
					if (emergencyUnits.get(k) instanceof Ambulance && emergencyUnits.get(k).getLocation().getX() == 0
							&& emergencyUnits.get(k).getLocation().getY() == 0) {
						Ambulance evac = (Ambulance) emergencyUnits.get(k);
						unitss += "\n" + "Unit's ID : " + evac.getUnitID() + "\n" + "Fire Unit" + "\n" + "Location : "
								+ evac.getLocation() + "\n" + "StepsPerCycle = " + evac.getStepsPerCycle() + "\n"
								+ "Unit State : " + evac.getState() + "\n";

					}
					if (emergencyUnits.get(k) instanceof DiseaseControlUnit
							&& emergencyUnits.get(k).getLocation().getX() == 0
							&& emergencyUnits.get(k).getLocation().getY() == 0) {
						DiseaseControlUnit evac = (DiseaseControlUnit) emergencyUnits.get(k);
						unitss += "\n" + "Unit's ID : " + evac.getUnitID() + "\n" + "Fire Unit" + "\n" + "Location : "
								+ evac.getLocation() + "\n" + "StepsPerCycle = " + evac.getStepsPerCycle() + "\n"
								+ "Unit State : " + evac.getState() + "\n";

					}
				}
				for (int c = 0; c < visibleCitizens.size(); c++) {
					Citizen c1 = visibleCitizens.get(c);
					if (c1.getLocation().getX() == 0 && c1.getLocation().getY() == 0) {
						unitss += "\n" + "Citizen name = " + c1.getName() + "\n" + "Age = " + c1.getAge() + "\n"
								+ "ID = " + c1.getNationalID() + "\n" + "HP = " + c1.getHp() + "\n" + "Blood Loss = "
								+ c1.getBloodLoss() + "\n" + "Toxicity = " + c1.getToxicity() + "\n" + "State : "
								+ c1.getState();
					}
				}
				vieww.textArea.setText(unitss);

			}
			if (((JButton) e.getSource()).getText().equals("NextCycle")) {

				try {
					engine.nextCycle();
					engine.finished();
					vieww.TextCurrent.setText(engine.getCurrentCycle() + "");
					causal = engine.calculateCasualties();
					vieww.CausalArea.setText(causal + "");
				} catch (CitizenAlreadyDeadException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				} catch (BuildingAlreadyCollapsedException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				} catch (CannotTreatException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
				vieww.Logtext.setText(Update1(engine.Update()));
				String available = "";

				for (int k = 0; k < emergencyUnits.size(); k++) {

					if (isAvailable() == true) {
						available = "There are no available Units";
					} else {
						if (emergencyUnits.get(k) instanceof Evacuator
								&& emergencyUnits.get(k).getState() == UnitState.IDLE) {
							available += "Evacuator Unit is available" + "\n";

						}
						if (emergencyUnits.get(k) instanceof FireTruck
								&& emergencyUnits.get(k).getState() == UnitState.IDLE) {
							available += "Fire Truck is available" + "\n";

						}
						if (emergencyUnits.get(k) instanceof GasControlUnit
								&& emergencyUnits.get(k).getState() == UnitState.IDLE) {
							available += "GasCu Unit is available" + "\n";

						}
						if (emergencyUnits.get(k) instanceof Ambulance
								&& emergencyUnits.get(k).getState() == UnitState.IDLE) {
							available += "Ambulance is available" + "\n";

						}
						if (emergencyUnits.get(k) instanceof DiseaseControlUnit
								&& emergencyUnits.get(k).getState() == UnitState.IDLE) {
							available += "DiseaseCu Unit is available" + "\n";

						}
					}

				}
				vieww.availableText.setText(available);
			}
			for (int l = 0; l < emergencyUnits.size(); l++) {
				String k = emergencyUnits.get(l).getUnitID();

				if (((JButton) e.getSource()).getText().equals("Evacuator" + k)) {

					// flag = true;
					flagxx = true;

					for (int i = 0; i < emergencyUnits.size(); i++) {
						if (emergencyUnits.get(i) != null) {
							if (emergencyUnits.get(i) instanceof Evacuator
									&& emergencyUnits.get(i).getUnitID().equals(k)) {
								Evacuator evac = (Evacuator) emergencyUnits.get(i);
								evac.clicked = true;
								if (evac.getTarget() == null) {
									String inf = "Unit's ID : " + evac.getUnitID() + "\n" + "Evacuator Unit" + "\n"
											+ "Location : " + evac.getLocation() + "\n" + "StepsPerCycle = "
											+ evac.getStepsPerCycle() + "\n" + "No Target" + "\n" + "Unit State : "
											+ evac.getState() + "\n" + "Number of passengers = "
											+ evac.getPassengers().size();
									vieww.textArea.setText(inf);
								} else {
									String inf = "Unit's ID : " + evac.getUnitID() + "\n" + "Evacuator Unit" + "\n"
											+ "Location : " + evac.getLocation() + "\n" + "StepsPerCycle = "
											+ evac.getStepsPerCycle() + "\n" + "Traget: " + evac.getTarget().toString()
											+ "\n" + "Unit State : " + evac.getState() + "\n"
											+ "Number of passengers = " + evac.getPassengers().size();
									if (evac.getPassengers().size() != 0) {
										for (int zz = 0; zz < evac.getPassengers().size(); zz++) {
											Citizen c1 = evac.getPassengers().get(zz);
											String inf2 = "\n" + "\n" + "Citizen name = " + c1.getName() + "\n"
													+ "Age =" + c1.getAge() + "\n" + "ID = " + c1.getNationalID() + "\n"
													+ "HP = " + c1.getHp() + "\n" + "Blood Loss =" + c1.getBloodLoss()
													+ "\n" + "Toxicity = " + c1.getToxicity() + "\n" + "State : "
													+ c1.getState();
											inf += inf2;
										}
									}
									vieww.textArea.setText(inf);
								}
							}
						}
					}

				}
				if (((JButton) e.getSource()).getText().equals("FireTruck" + k)) {
					// flag1 = true;
					flagxx = true;
					for (int i = 0; i < emergencyUnits.size(); i++) {
						if (emergencyUnits.get(i) != null) {
							if (emergencyUnits.get(i) instanceof FireTruck
									&& emergencyUnits.get(i).getUnitID().equals(k)) {
								FireTruck evac = (FireTruck) emergencyUnits.get(i);
								evac.clicked = true;
								if (evac.getTarget() == null) {
									String inf = "Unit's ID : " + evac.getUnitID() + "\n" + "FireTruck Unit" + "\n"
											+ "Location : " + evac.getLocation() + "\n" + "StepsPerCycle = "
											+ evac.getStepsPerCycle() + "\n" + "No Target" + "\n" + "Unit State : "
											+ evac.getState();
									vieww.textArea.setText(inf);
								} else {
									String inf = "Unit's ID : " + evac.getUnitID() + "\n" + "FireTruck Unit" + "\n"
											+ "Location : " + evac.getLocation() + "\n" + "StepsPerCycle = "
											+ evac.getStepsPerCycle() + "\n" + "Traget: " + evac.getTarget().toString()
											+ "\n" + "Unit State : " + evac.getState();
									vieww.textArea.setText(inf);
								}
							}
						}
					}
				}
				if (((JButton) e.getSource()).getText().equals("Ambulance" + k)) {
					// flag2 = true;
					flagxx = true;
					for (int i = 0; i < emergencyUnits.size(); i++) {
						if (emergencyUnits.get(i) != null) {
							if (emergencyUnits.get(i) instanceof Ambulance
									&& emergencyUnits.get(i).getUnitID().equals(k)) {
								Ambulance amb = (Ambulance) emergencyUnits.get(i);
								amb.clicked = true;
								if (amb.getTarget() == null) {
									String inf = "Unit's ID : " + amb.getUnitID() + "\n" + "Ambulance Unit" + "\n"
											+ "Location : " + amb.getLocation() + "\n" + "StepsPerCycle = "
											+ amb.getStepsPerCycle() + "\n" + "No Target" + "\n" + "Unit State : "
											+ amb.getState();
									vieww.textArea.setText(inf);
								} else {
									String inf = "Unit's ID : " + amb.getUnitID() + "\n" + "Ambulance Unit" + "\n"
											+ "Location : " + amb.getLocation() + "\n" + "StepsPerCycle = "
											+ amb.getStepsPerCycle() + "\n" + "Traget: " + amb.getTarget().toString()
											+ "\n" + "Unit State : " + amb.getState();
									vieww.textArea.setText(inf);
								}
							}
						}
					}
				}
				if (((JButton) e.getSource()).getText().equals("GasControlUnit" + k)) {
					// flag3 = true;
					flagxx = true;
					for (int i = 0; i < emergencyUnits.size(); i++) {
						if (emergencyUnits.get(i) != null) {
							if (emergencyUnits.get(i) instanceof GasControlUnit
									&& emergencyUnits.get(i).getUnitID().equals(k)) {
								GasControlUnit evac = (GasControlUnit) emergencyUnits.get(i);
								evac.clicked = true;
								if (evac.getTarget() == null) {
									String inf = "Unit's ID : " + evac.getUnitID() + "\n" + "Gas Control Unit" + "\n"
											+ "Location : " + evac.getLocation() + "\n" + "StepsPerCycle = "
											+ evac.getStepsPerCycle() + "\n" + "No Target" + "\n" + "Unit State : "
											+ evac.getState();
									vieww.textArea.setText(inf);
								} else {
									String inf = "Unit's ID : " + evac.getUnitID() + "\n" + "Gas Control Unit Unit"
											+ "\n" + "Location : " + evac.getLocation() + "\n" + "StepsPerCycle = "
											+ evac.getStepsPerCycle() + "\n" + "Traget: " + evac.getTarget().toString()
											+ "\n" + "Unit State : " + evac.getState();
									vieww.textArea.setText(inf);
								}
							}
						}
					}

				}
				if (((JButton) e.getSource()).getText().equals("DiseaseCU" + k)) {
					// flag4 = true;
					flagxx = true;
					for (int i = 0; i < emergencyUnits.size(); i++) {
						if (emergencyUnits.get(i) != null) {
							if (emergencyUnits.get(i) instanceof DiseaseControlUnit
									&& emergencyUnits.get(i).getUnitID().equals(k)) {
								DiseaseControlUnit evac = (DiseaseControlUnit) emergencyUnits.get(i);
								evac.clicked = true;
								if (evac.getTarget() == null) {
									String inf = "Unit's ID : " + evac.getUnitID() + "\n" + "Disease control Unit"
											+ "\n" + "Location : " + evac.getLocation() + "\n" + "StepsPerCycle = "
											+ evac.getStepsPerCycle() + "\n" + "No Target" + "\n" + "Unit State : "
											+ evac.getState();
									vieww.textArea.setText(inf);
								} else {
									String inf = "Unit's ID : " + evac.getUnitID() + "\n" + "Disease control Unit"
											+ "\n" + "Location : " + evac.getLocation() + "\n" + "StepsPerCycle = "
											+ evac.getStepsPerCycle() + "\n" + "Traget: " + evac.getTarget().toString();
									vieww.textArea.setText(inf);
								}
							}
						}
					}
				}
			}
			if (((JButton) e.getSource()).getText().length() <= 2) {
				int x = Integer.parseInt(((JButton) e.getSource()).getText()) / 10;
				int y = Integer.parseInt(((JButton) e.getSource()).getText()) % 10;

				for (int i = 0; i < visibleBuildings.size(); i++) {
					ResidentialBuilding b1 = visibleBuildings.get(i);
					if (b1.getLocation().getX() == x && b1.getLocation().getY() == y) {
						if (flagxx) {
							for (int h = 0; h < emergencyUnits.size(); h++) {
								if (emergencyUnits.get(h) instanceof Evacuator) {
									if (((Evacuator) emergencyUnits.get(h)).clicked == true
											&& ((Evacuator) emergencyUnits.get(h)).free == true) {
										try {
											((Evacuator) emergencyUnits.get(h)).respond(b1);
										} catch (IncompatibleTargetException | CannotTreatException e1) {
											JOptionPane.showMessageDialog(null, e1.getMessage());
										}
										((Evacuator) emergencyUnits.get(h)).clicked = false;
									}
								}
								if (emergencyUnits.get(h) instanceof FireTruck) {
									if (((FireTruck) emergencyUnits.get(h)).clicked == true
											&& ((FireTruck) emergencyUnits.get(h)).free == true) {
										try {
											((FireTruck) emergencyUnits.get(h)).respond(b1);
										} catch (IncompatibleTargetException | CannotTreatException e1) {
											JOptionPane.showMessageDialog(null, e1.getMessage());
										}
										((FireTruck) emergencyUnits.get(h)).clicked = false;
									}
								}
								if (emergencyUnits.get(h) instanceof DiseaseControlUnit) {
									if (((DiseaseControlUnit) emergencyUnits.get(h)).clicked == true
											&& ((DiseaseControlUnit) emergencyUnits.get(h)).free == true) {
										try {
											((DiseaseControlUnit) emergencyUnits.get(h)).respond(b1);
										} catch (IncompatibleTargetException | CannotTreatException e1) {
											JOptionPane.showMessageDialog(null, e1.getMessage());
										}
										((DiseaseControlUnit) emergencyUnits.get(h)).clicked = false;
									}
								}
								if (emergencyUnits.get(h) instanceof GasControlUnit) {
									if (((GasControlUnit) emergencyUnits.get(h)).clicked == true
											&& ((GasControlUnit) emergencyUnits.get(h)).free == true) {
										try {
											((GasControlUnit) emergencyUnits.get(h)).respond(b1);
										} catch (IncompatibleTargetException | CannotTreatException e1) {
											JOptionPane.showMessageDialog(null, e1.getMessage());
										}
										((GasControlUnit) emergencyUnits.get(h)).clicked = false;
									}
								}
								if (emergencyUnits.get(h) instanceof Ambulance) {
									if (((Ambulance) emergencyUnits.get(h)).clicked == true
											&& ((Ambulance) emergencyUnits.get(h)).free == true) {
										try {
											((Ambulance) emergencyUnits.get(h)).respond(b1);
										} catch (IncompatibleTargetException | CannotTreatException e1) {
											JOptionPane.showMessageDialog(null, e1.getMessage());
										}
										((Ambulance) emergencyUnits.get(h)).clicked = false;
									}
								}
							}
							flagxx = false;
						}
						if (b1.getDisaster() instanceof Fire) {
							if (b1.getOccupants().size() == 0) {
								JButton jb = vieww.getCellsHolder()[b1.getLocation().getX()][b1.getLocation().getY()];
								ImageIcon b = new ImageIcon("src/pics/buildingwithfire.jpg");
								Image img1 = b.getImage();
								Image newback_1 = img1.getScaledInstance(95, 55, Image.SCALE_SMOOTH);
								ImageIcon newback1 = new ImageIcon(newback_1);
								jb.setIcon(newback1);
							} else {
								JButton jb = vieww.getCellsHolder()[b1.getLocation().getX()][b1.getLocation().getY()];
								ImageIcon b = new ImageIcon("src/pics/buildingwithcitizenandfire.jpg");
								Image img1 = b.getImage();
								Image newback_1 = img1.getScaledInstance(95, 55, Image.SCALE_SMOOTH);
								ImageIcon newback1 = new ImageIcon(newback_1);
								jb.setIcon(newback1);
							}
							String inf = "The building's Location is " + b1.getLocation() + "\n"
									+ "StructuralIntegrity = " + b1.getStructuralIntegrity() + "\n" + "Fire Damgae = "
									+ b1.getFireDamage() + "\n" + "GasLevel = " + b1.getGasLevel() + "\n"
									+ "Foundation Damge = " + b1.getFoundationDamage() + "\n" + "Number of occupants = "
									+ b1.getOccupants().size() + "\n" + "Disaster is a " + "Fire disaster" + "\n"
									+ "\n";
							for (int j = 0; j < b1.getOccupants().size(); j++) {
								Citizen c7 = b1.getOccupants().get(j);
								inf += "Citizen name = " + c7.getName() + "\n" + "Age = " + c7.getAge() + "\n" + "ID = "
										+ c7.getNationalID() + "\n" + "HP = " + c7.getHp() + "\n" + "Blood Loss = "
										+ c7.getBloodLoss() + "\n" + "Toxicity = " + c7.getToxicity() + "\n"
										+ "State : " + c7.getState() + "\n" + "Increases fire damgae by 10" + "\n"
										+ "\n";
							}
							vieww.textArea.setText(inf);

						}
						if (b1.getDisaster() instanceof GasLeak) {
							if (b1.getOccupants().size() == 0) {
								JButton jb = vieww.getCellsHolder()[b1.getLocation().getX()][b1.getLocation().getY()];
								ImageIcon b = new ImageIcon("src/pics/buildingwithgasleak.jpg");
								Image img1 = b.getImage();
								Image newback_1 = img1.getScaledInstance(95, 55, Image.SCALE_SMOOTH);
								ImageIcon newback1 = new ImageIcon(newback_1);
								jb.setIcon(newback1);
							} else {
								JButton jb = vieww.getCellsHolder()[b1.getLocation().getX()][b1.getLocation().getY()];
								ImageIcon b = new ImageIcon("src/pics/buildingwithcitizenandgasleak.jpg");
								Image img1 = b.getImage();
								Image newback_1 = img1.getScaledInstance(95, 55, Image.SCALE_SMOOTH);
								ImageIcon newback1 = new ImageIcon(newback_1);
								jb.setIcon(newback1);
								vieww.frame.revalidate();
							}
							String inf = "The building's Location is " + b1.getLocation() + "\n"
									+ "StructuralIntegrity = " + b1.getStructuralIntegrity() + "\n"
									+ "GasLeak disaster = " + b1.getFireDamage() + "\n" + "GasLevel ="
									+ b1.getGasLevel() + "\n" + "Foundation Damge = " + b1.getFoundationDamage() + "\n"
									+ "Number of occupants = " + b1.getOccupants().size() + "\n" + "Disaster is a "
									+ "GasLeak disaster" + "\n" + "Increases gas level by 10";

							for (int j = 0; j < b1.getOccupants().size(); j++) {
								Citizen c7 = b1.getOccupants().get(j);
								inf += "\n" + "Citizen name = " + c7.getName() + "\n" + "Age = " + c7.getAge() + "\n"
										+ "ID = " + c7.getNationalID() + "\n" + "HP = " + c7.getHp() + "\n"
										+ "Blood Loss = " + c7.getBloodLoss() + "\n" + "Toxicity = " + c7.getToxicity()
										+ "\n" + "State : " + c7.getState();
							}
							vieww.textArea.setText(inf);

						}
						if (b1.getDisaster() instanceof Collapse) {
							if (b1.getOccupants().size() == 0) {
								JButton jb = vieww.getCellsHolder()[b1.getLocation().getX()][b1.getLocation().getY()];
								ImageIcon b = new ImageIcon("src/pics/buildingwithcollapseandnocitizen.jpg");
								Image img1 = b.getImage();
								Image newback_1 = img1.getScaledInstance(95, 55, Image.SCALE_SMOOTH);
								ImageIcon newback1 = new ImageIcon(newback_1);
								jb.setIcon(newback1);
							} else {
								JButton jb = vieww.getCellsHolder()[b1.getLocation().getX()][b1.getLocation().getY()];
								ImageIcon b = new ImageIcon("src/pics/buildingwithcitizenandcollapse.jpg");
								Image img1 = b.getImage();
								Image newback_1 = img1.getScaledInstance(95, 55, Image.SCALE_SMOOTH);
								ImageIcon newback1 = new ImageIcon(newback_1);
								jb.setIcon(newback1);
								vieww.frame.revalidate();
							}
							String inf = "The building's Location is " + b1.getLocation() + "\n"
									+ "StructuralIntegrity = " + b1.getStructuralIntegrity() + "\n" + "Fire Damgae = "
									+ b1.getFireDamage() + "\n" + "GasLevel = " + b1.getGasLevel() + "\n"
									+ "Foundation Damge = " + b1.getFoundationDamage() + "\n" + "Number of occupants = "
									+ b1.getOccupants().size() + "\n" + "Disaster is a " + "Collapse disaster" + "\n"
									+ "Increases Foundation damage by 10" + "\n" + "\n";
							for (int j = 0; j < b1.getOccupants().size(); j++) {
								Citizen c7 = b1.getOccupants().get(j);
								inf += "\n" + "Citizen name = " + c7.getName() + "\n" + "Age = " + c7.getAge() + "\n"
										+ "ID = " + c7.getNationalID() + "\n" + "HP = " + c7.getHp() + "\n"
										+ "Blood Loss = " + c7.getBloodLoss() + "\n" + "Toxicity = " + c7.getToxicity()
										+ "\n" + "State : " + c7.getState();
							}
							vieww.textArea.setText(inf);

						}
					}

				}
				for (int j = 0; j < visibleCitizens.size(); j++) {
					Citizen c1 = visibleCitizens.get(j);
					if (c1.getLocation().getX() == x && c1.getLocation().getY() == y) {
						String inf = "Citizen name = " + c1.getName() + "\n" + "Age =" + c1.getAge() + "\n" + "ID = "
								+ c1.getNationalID() + "\n" + "HP = " + c1.getHp() + "\n" + "Blood Loss ="
								+ c1.getBloodLoss() + "\n" + "Toxicity = " + c1.getToxicity() + "\n" + "State : "
								+ c1.getState();
						vieww.textArea.setText(inf);
						if (flagxx) {
							for (int h = 0; h < emergencyUnits.size(); h++) {
								if (emergencyUnits.get(h) instanceof Evacuator) {
									if (((Evacuator) emergencyUnits.get(h)).clicked == true
											&& ((Evacuator) emergencyUnits.get(h)).free == true) {
										try {
											((Evacuator) emergencyUnits.get(h)).respond(c1);
										} catch (IncompatibleTargetException | CannotTreatException e1) {
											JOptionPane.showMessageDialog(null, e1.getMessage());
										}
										((Evacuator) emergencyUnits.get(h)).clicked = false;
									}
								}
								if (emergencyUnits.get(h) instanceof FireTruck) {
									if (((FireTruck) emergencyUnits.get(h)).clicked == true
											&& ((FireTruck) emergencyUnits.get(h)).free == true) {
										try {
											((FireTruck) emergencyUnits.get(h)).respond(c1);
										} catch (IncompatibleTargetException | CannotTreatException e1) {
											JOptionPane.showMessageDialog(null, e1.getMessage());
										}
										((FireTruck) emergencyUnits.get(h)).clicked = false;
									}
								}
								if (emergencyUnits.get(h) instanceof DiseaseControlUnit) {
									if (((DiseaseControlUnit) emergencyUnits.get(h)).clicked == true
											&& ((DiseaseControlUnit) emergencyUnits.get(h)).free == true) {
										try {
											((DiseaseControlUnit) emergencyUnits.get(h)).respond(c1);
										} catch (IncompatibleTargetException | CannotTreatException e1) {
											JOptionPane.showMessageDialog(null, e1.getMessage());
										}
										((DiseaseControlUnit) emergencyUnits.get(h)).clicked = false;
									}
								}
								if (emergencyUnits.get(h) instanceof GasControlUnit) {
									if (((GasControlUnit) emergencyUnits.get(h)).clicked == true
											&& ((GasControlUnit) emergencyUnits.get(h)).free == true) {
										try {
											((GasControlUnit) emergencyUnits.get(h)).respond(c1);
										} catch (IncompatibleTargetException | CannotTreatException e1) {
											JOptionPane.showMessageDialog(null, e1.getMessage());
										}
										((GasControlUnit) emergencyUnits.get(h)).clicked = false;
									}
								}
								if (emergencyUnits.get(h) instanceof Ambulance) {
									if (((Ambulance) emergencyUnits.get(h)).clicked == true
											&& ((Ambulance) emergencyUnits.get(h)).free == true) {
										try {
											((Ambulance) emergencyUnits.get(h)).respond(c1);
										} catch (IncompatibleTargetException | CannotTreatException e1) {
											JOptionPane.showMessageDialog(null, e1.getMessage());
										}
										((Ambulance) emergencyUnits.get(h)).clicked = false;
									}
								}
							}
							flagxx = false;
						}
					}

				}
			}
		}
	}

	public void receiveSOSCall(Rescuable r) {

		if (r != null) {
			if (r instanceof ResidentialBuilding) {

				if (!visibleBuildings.contains(r)) {
					ResidentialBuilding x = (ResidentialBuilding) r;
					visibleBuildings.add(x);
					if (vieww != null) {
						JButton jb = vieww.getCellsHolder()[x.getLocation().getX()][x.getLocation().getY()];

						jb.addActionListener(this);
						if (x.getOccupants().size() != 0) {
							ImageIcon b = new ImageIcon("src/pics/buildingwithcitizen.jpg");
							Image img1 = b.getImage();
							Image newback_1 = img1.getScaledInstance(85, 55, Image.SCALE_SMOOTH);
							ImageIcon newback1 = new ImageIcon(newback_1);
							jb.setIcon(newback1);
						} else {
							ImageIcon b = new ImageIcon("src/pics/building.jpg");
							Image img1 = b.getImage();
							Image newback_1 = img1.getScaledInstance(85, 55, Image.SCALE_SMOOTH);
							ImageIcon newback1 = new ImageIcon(newback_1);
							jb.setIcon(newback1);
						}
						vieww.getCellsHolder()[x.getLocation().getX()][x.getLocation().getY()] = jb;
					}
				}
			} else {

				if (!visibleCitizens.contains(r)) {
					visibleCitizens.add((Citizen) r);

					if (vieww != null) {
						JButton xx = vieww.CellsHolder[r.getLocation().getX()][r.getLocation().getY()];
						xx.addActionListener(this);
						ImageIcon b = new ImageIcon("src/pics/citizen.png");
						Image img1 = b.getImage();
						Image newback_1 = img1.getScaledInstance(75, 45, Image.SCALE_SMOOTH);
						ImageIcon newback1 = new ImageIcon(newback_1);
						xx.setIcon(newback1);
						vieww.CellsHolder[r.getLocation().getX()][r.getLocation().getY()] = xx;
					}

				}
			}
		}

	}

	public void addUnits() {

		// vieww.frame.add(junits);
		// vieww.frame.revalidate();
		// vieww.frame.repaint();

	}

	public boolean isAvailable() {

		for (int i = 0; i < emergencyUnits.size(); i++) {
			if (emergencyUnits.get(i).getState() == UnitState.IDLE) {
				return false;
			}
		}
		return true;
	}

	public String Update1(String mssg) {
		return (log += mssg);
	}

	public static void main(String[] args) throws Exception {
		vieww = new GameView();
	}
}
