package model.units;

import exceptions.CannotTreatException;
import exceptions.IncompatibleTargetException;
import model.disasters.Fire;
import model.disasters.GasLeak;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import simulation.Address;
import simulation.Rescuable;

public class Evacuator extends PoliceUnit {
	public boolean clicked = false;
	public boolean free = true;
	public Evacuator(String unitID, Address location, int stepsPerCycle,
			WorldListener worldListener, int maxCapacity) {
		super(unitID, location, stepsPerCycle, worldListener, maxCapacity);

	}

	@Override
	public void treat() throws CannotTreatException {
		ResidentialBuilding target = (ResidentialBuilding) getTarget();
			if (target.getStructuralIntegrity() == 0
					|| target.getOccupants().size() == 0) {
				jobsDone();
				return;
			}

			for (int i = 0; getPassengers().size() != getMaxCapacity()
					&& i < target.getOccupants().size(); i++) {
				getPassengers().add(target.getOccupants().remove(i));
				i--;
			}

			setDistanceToBase(target.getLocation().getX()
					+ target.getLocation().getY());

	}
	public void respond(Rescuable r) throws IncompatibleTargetException, CannotTreatException
	{
		if(r.getDisaster() instanceof GasLeak || r.getDisaster() instanceof Fire )
		{
			throw new CannotTreatException(this, r,"The target is already safe");
		}
		else
		{
			if(r.getDisaster()==null)
			{
				throw new CannotTreatException(this, r,"The target is already safe");
			}
			else
			{
				if(!canTreat(r))
				{
					throw new CannotTreatException(this, r,"The target is already safe");
				}
				else
				{
					if(r instanceof Citizen)
					{
						throw new IncompatibleTargetException(this, r, "That's not the right target");
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
	
}
