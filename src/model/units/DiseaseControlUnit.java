package model.units;

import exceptions.CannotTreatException;
import exceptions.IncompatibleTargetException;
import model.disasters.Infection;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Address;
import simulation.Rescuable;

public class DiseaseControlUnit extends MedicalUnit {
	public boolean clicked = false;
	public boolean free = true;
	public DiseaseControlUnit(String unitID, Address location,
			int stepsPerCycle, WorldListener worldListener) {
		super(unitID, location, stepsPerCycle, worldListener);
	}

	@Override
	public void treat() {
		getTarget().getDisaster().setActive(false);
		Citizen target = (Citizen) getTarget();
		if (target.getHp() == 0) {
			jobsDone();
			return;
		} else if (target.getToxicity() > 0) {
			target.setToxicity(target.getToxicity() - getTreatmentAmount());
			if (target.getToxicity() == 0)
				target.setState(CitizenState.RESCUED);
		}

		else if (target.getToxicity() == 0)
			heal();

	}

	public void respond(Rescuable r) throws IncompatibleTargetException, CannotTreatException {
		if(!canTreat(r))
		{
			
				throw new CannotTreatException(this, r,"The target is already safe");
			
		}
		else
		{
			if(r instanceof ResidentialBuilding)
			{
				throw new IncompatibleTargetException(this, r, "That's not the right target");
			}
			else
			{
				if(!(r.getDisaster() instanceof Infection))
				{
					throw new CannotTreatException(this, r,"The target is already safe");
				}
				else
				{
					if (getTarget() != null && ((Citizen) getTarget()).getToxicity() > 0
							&& getState() == UnitState.TREATING)
						reactivateDisaster();
					finishRespond(r);
				}
			}
		}
	}
	
}
