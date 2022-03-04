package model.units;

import exceptions.CannotTreatException;
import exceptions.IncompatibleTargetException;
import model.disasters.Fire;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import simulation.Address;
import simulation.Rescuable;

public class FireTruck extends FireUnit {
	public boolean clicked = false;
	public boolean free = true;
	public FireTruck(String unitID, Address location, int stepsPerCycle,
			WorldListener worldListener) {
		super(unitID, location, stepsPerCycle, worldListener);
	}

	@Override
	public void treat() {
		getTarget().getDisaster().setActive(false);

		ResidentialBuilding target = (ResidentialBuilding) getTarget();
		if (target.getStructuralIntegrity() == 0) {
			jobsDone();
			return;
		} else if (target.getFireDamage() > 0)

			target.setFireDamage(target.getFireDamage() - 10);

		if (target.getFireDamage() == 0)

			jobsDone();

	}
	public void respond(Rescuable r) throws IncompatibleTargetException, CannotTreatException
	{
		if(!canTreat(r))
		{
			
			throw new CannotTreatException(this,r,"The target is already safe");
		}
		else
		{
			if(r instanceof Citizen)
			{
				throw new IncompatibleTargetException(this, r,"That's not the right target");
			}
			else
			{
				if(!(r.getDisaster() instanceof Fire))
				{
					throw new CannotTreatException(this,r,"The target is already safe");
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
