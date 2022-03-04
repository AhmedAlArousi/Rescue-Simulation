package model.disasters;

import exceptions.BuildingAlreadyCollapsedException;
import exceptions.CitizenAlreadyDeadException;
import model.people.Citizen;
import simulation.Rescuable;
import simulation.Simulatable;

public abstract class Disaster implements Simulatable{
	private int startCycle;
	private Rescuable target;
	private boolean active;
	public Disaster(int startCycle, Rescuable target) {
		this.startCycle = startCycle;
		this.target = target;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public int getStartCycle() {
		return startCycle;
	}
	public Rescuable getTarget() {
		return target;
	}
	public void strike() throws CitizenAlreadyDeadException, BuildingAlreadyCollapsedException 
	{
		
		target.struckBy(this);
		active=true;
	}
	public String toString()
	{
		if(this instanceof Fire)
		{
			return "Fire Disaster at " + getTarget().getLocation();
		}
		if(this instanceof GasLeak)
		{
			return "Gas Leak Disaster at " + getTarget().getLocation();
		}
		if(this instanceof Collapse)
		{
			return "Collapse Disaster at " + getTarget().getLocation();
		}
		if(this instanceof Infection)
		{
			return "Infection Disaster on " + ((Citizen) getTarget()).getName() + "At Location " + getTarget().getLocation() ;
		}
		if(this instanceof Injury)
		{
			return "Injury Disaster on " + ((Citizen) getTarget()).getName() +"\n" + "At Location " + getTarget().getLocation() ;
		}
		return "";
	}
}
