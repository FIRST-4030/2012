package edu.wpi.first.wpilibj.templates;

public class GlobalState {
        public final int TOGGLE_BUTTON_STATES_OFF = 0;
        public final int TOGGLE_BUTTON_STATES_RECENTLY_OFF = 1;
        public final int TOGGLE_BUTTON_STATES_ON = 2;
        public final int TOGGLE_BUTTON_STATES_RECENTLY_ON = 3;

	// Are we driving manually or automatically?
	private int joystickDrive = TOGGLE_BUTTON_STATES_ON;
	
	// Do we have the target in sight?
	private boolean targetVisible = false;
	
	// Are we in position to shoot?
	private boolean targetLocked = false;

	// Require a press-release cycle to toggle the state
	private void updateState(int state, boolean pressed) {
		if (pressed) {
			if (state == TOGGLE_BUTTON_STATES_OFF) {
				state = TOGGLE_BUTTON_STATES_RECENTLY_OFF;
			} else if (state == TOGGLE_BUTTON_STATES_ON) {
				state = TOGGLE_BUTTON_STATES_RECENTLY_ON;
			}
		} else {
			if (state == TOGGLE_BUTTON_STATES_RECENTLY_OFF) {
				state = TOGGLE_BUTTON_STATES_ON;
			} else if (state == TOGGLE_BUTTON_STATES_RECENTLY_ON) {
				state = TOGGLE_BUTTON_STATES_OFF;
			}
		}
	}
	
	// Collapse the 4-state toggle tracking to a simple boolean
	private boolean isOn(int state) {
		if (state == TOGGLE_BUTTON_STATES_ON
				|| state == TOGGLE_BUTTON_STATES_RECENTLY_ON) {
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
