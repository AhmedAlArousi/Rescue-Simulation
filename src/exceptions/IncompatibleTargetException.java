package exceptions;

import model.units.Unit;
import simulation.Rescuable;

public class IncompatibleTargetException extends UnitException{

	public IncompatibleTargetException(Unit unit, Rescuable target, String message) {
		super(unit, target, "This is not the right target");
	}
	public IncompatibleTargetException(Unit unit , Rescuable target)
	{
		super(unit , target);
	}
}
