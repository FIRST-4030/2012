package org.usfirst.frc0;

public class GlobalState {
	public enum TOGGLE_BUTTON_STATES {
		OFF, RECENTLY_OFF, ON, RECENTLY_ON
	};

	// Are we driving manually or automatically?
	private TOGGLE_BUTTON_STATES joystickDrive = TOGGLE_BUTTON_STATES.ON;
	
	// Do we have the target in sight?
	private boolean targetVisible = false;
	
	// Are we in position to shoot?
	private boolean targetLocked = false;

	// Require a press-release cycle to toggle the state
	private void updateState(TOGGLE_BUTTON_STATES state, boolean pressed) {
		if (pressed) {
			if (state.equals(TOGGLE_BUTTON_STATES.OFF)) {
				state = TOGGLE_BUTTON_STATES.RECENTLY_OFF;
			} else if (state.equals(TOGGLE_BUTTON_STATES.ON)) {
				state = TOGGLE_BUTTON_STATES.RECENTLY_ON;
			}
		} else {
			if (state.equals(TOGGLE_BUTTON_STATES.RECENTLY_OFF)) {
				state = TOGGLE_BUTTON_STATES.ON;
			} else if (state.equals(TOGGLE_BUTTON_STATES.RECENTLY_ON)) {
				state = TOGGLE_BUTTON_STATES.OFF;
			}
		}
	}
	
	// Collapse the 4-state toggle tracking to a simple boolean
	private boolean isOn(TOGGLE_BUTTON_STATES state) {
		if (state.equals(TOGGLE_BUTTON_STATES.ON)
				|| state.equals(TOGGLE_BUTTON_STATES.RECENTLY_ON)) {
			return true;
		}
		return false;
	}
	
	public void updateJoystickDriveEnabled(boolean pressed) {
		updateState(joystickDrive, pressed);
	}

	public boolean isJoystickDriveEnabled() {
		return isOn(joystickDrive);
	}
	
	public void setTargetVisible(boolean visible) {
		this.targetVisible = visible;
		if (!visible) {
			this.setTargetLocked(false);
		}
	}
	
	public boolean isTargetVisible() {
		return targetVisible;
	}
	
	public void setTargetLocked(boolean locked) {
		this.targetLocked = locked;
	}

	public boolean isTargetLocked() {
		return targetLocked;
	}
}
