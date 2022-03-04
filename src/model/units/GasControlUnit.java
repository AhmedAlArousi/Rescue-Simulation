package model.units;

import exceptions.CannotTreatException;
import exceptions.IncompatibleTargetException;
import model.disasters.GasLeak;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import simulation.Address;
import simulation.Rescuable;

public class GasControlUnit extends FireUnit {
	public boolean clicked = false;
	public boolean free = true;
	public GasControlUnit(String unitID, Address location, int stepsPerCycle,
			WorldListener worldListener) {
		super(unitID, location, stepsPerCycle, worldListener);
	}

	public void treat() {
		getTarget().getDisaster().setActive(false);

		ResidentialBuilding target = (ResidentialBuilding) getTarget();
		if (target.getStructuralIntegrity() == 0) {
			jobsDone();
			return;
		} else if (target.getGasLevel() > 0) 
			target.setGasLevel(target.getGasLevel() - 10);

		if (target.getGasLevel() == 0)
			jobsDone();

	}
	public void respond(Rescuable r) throws IncompatibleTargetException, CannotTreatException
	{
		if(!canTreat(r))
		{
			throw new CannotTreatException(this, r,"The target is already safe");
		}
		else
		{
			
			
			
			if(r instanceof Citizen)
			{
				throw new IncompatibleTargetException(this, r,"That's not the right target");
			}
			else
			{
				if(!(r.getDisaster() instanceof GasLeak))
				{
					throw new CannotTreatException(this, r,"The target is already safe");
				}
				else
				{

					if (getTarget() != null && ((Citizen) getTarget()).getBloodLoss() > 0&& getState() == UnitState.TREATING)
						reactivateDisaster();
					finishRespond(r);
				}
			}
		}
		
	}
	
}
