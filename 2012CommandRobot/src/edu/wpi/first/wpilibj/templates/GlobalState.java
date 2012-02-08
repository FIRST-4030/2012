package edu.wpi.first.wpilibj.templates;

public class GlobalState {

    // Are we driving manually or automatically?
    private ToggleButtonState joystickDrive = new ToggleButtonState(ToggleButtonState.ON);
    // Do we have the target in sight?
    private boolean targetVisible = false;
    // Are we in position to shoot?
    private boolean targetLocked = false;
    // Is either arm switch closed
    private boolean armSwitch = false;
    // Track loaded balls
    private int numBalls = 0;
    // Is there a ball at the ready-to-shoot elevator switch?
    private boolean readyToShoot = false;
    // Is there a ball at the the read-load-load elevator switch?
    private boolean readyToLoad = false;

    public void updateJoystickDriveEnabled(boolean pressed) {
        joystickDrive.update(pressed);
    }

    public boolean isJoystickDriveEnabled() {
        return joystickDrive.isOn();
    }

    public void setTargetVisible(boolean visible) {
        this.targetVisible = visible;
        if (!visible) {
            this.setTargetLocked(false);
        }
    }

    public boolean targetVisible() {
        return this.targetVisible;
    }

    public void setTargetLocked(boolean locked) {
        this.targetLocked = locked;
    }

    public boolean targetLocked() {
        return this.targetLocked;
    }

    public boolean readyToShoot() {
        return this.readyToShoot;
    }

    public boolean readyToLoad() {
        return this.readyToLoad;
    }

    public boolean ballLoaded() {
        if (this.numBalls > 0) {
            return true;
        }
        return false;
    }

    public boolean loaderReady() {
        if (this.numBalls < RobotMap.MAX_BALLS) {
            return true;
        }
        return false;
    }

    public boolean armSwitch() {
        return this.armSwitch;
    }

    public void loadedBall() {
        this.numBalls++;
    }

    public void shotBall() {
        this.numBalls--;
    }
}
