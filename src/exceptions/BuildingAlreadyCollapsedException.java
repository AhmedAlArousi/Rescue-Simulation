package exceptions;

import model.disasters.Disaster;

public class BuildingAlreadyCollapsedException extends DisasterException {

	public BuildingAlreadyCollapsedException(Disaster disaster, String message) {
		super(disaster, "Building already Collapsed");
	}
	public BuildingAlreadyCollapsedException(Disaster disaster) {
		super(disaster);
	}
}
