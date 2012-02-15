package edu.wpi.first.wpilibj.templates;

public class GlobalState {

    // Is driving enabled?
    private ToggleButtonState drive = new ToggleButtonState(ToggleButtonState.OFF);
    // Is balance mode enabled
    private ToggleButtonState balanceMode = new ToggleButtonState(ToggleButtonState.OFF);
    // Is ball handling enabled
    private ToggleButtonState ballHandling = new ToggleButtonState(ToggleButtonState.OFF);
    // Are we shooting or loading
    private ToggleButtonState shootMode = new ToggleButtonState(ToggleButtonState.OFF);
    // Do we have the target in sight?
    private boolean targetVisible = false;
    // Are we in position to shoot?
    private boolean targetLocked = false;
    // Is either arm switch closed
    private boolean armSwitch = false;
    // Is the arm up?
    private boolean armUp = true;
    // Track loaded balls
    private int ballsInControl = 0;
    private int ballsInLoader = 0;
    private int ballsAboveBottomOfElevator = 0;
    // Is there a ball at the ready-to-shoot (elevator top) switch?
    private boolean readyToShoot = false;
    // Is there a ball at the the read-to-queue (elevator bottom) switch?
    private boolean readyToRaise = false;
    // Is there a ball at the done-raising switch?
    private boolean doneRaising = false;
    // Is there a ball at the read-to-load (loader front) switch
    private boolean readyToLoad = false;
    // Which way is down?
    private double gravity = 0;
    // Which way is north?
    private double heading = 0;
    // Reference voltage for analog sensors
    private double vin = 0;
    //hood angle?
    private double hoodAngle = 0;

    public double getHoodAngle() {
        return hoodAngle;
    }

    public void setHoodAngle(double hoodAngle) {
        this.hoodAngle = hoodAngle;
    }

    public boolean isDoneRaising() {
        return doneRaising;
    }

    public void setDoneRaising(boolean doneRaising) {
        this.doneRaising = doneRaising;
    }

    public int getBallsAboveBottomOfElevator() {
        return ballsAboveBottomOfElevator;
    }

    public void ballsAboveBottomOfElevatorPlus() {
        ballsAboveBottomOfElevator++;
    }

    public void ballsAboveBottomOfElevatorMinus() {
        ballsAboveBottomOfElevator--;
    }

    public void setArmSwitch(boolean armSwitch) {
        this.armSwitch = armSwitch;
    }

    public boolean getArmSwtich() {
        return armSwitch;
    }

    public void updateDriveEnabled(boolean pressed) {
        drive.update(pressed);
    }

    public boolean isDriveEnabled() {
        return drive.isOn();
    }

    public void updateBallHandlingEnabled(boolean pressed) {
        ballHandling.update(pressed);
    }

    public boolean isBallHandlingEnabled() {
        return ballHandling.isOn();
    }

    public void updateShootMode(boolean pressed) {
        shootMode.update(pressed);
    }

    public void setShootMode(boolean enable) {
        if (enable) {
            shootMode.set(ToggleButtonState.ON);
        } else {
            shootMode.set(ToggleButtonState.OFF);
        }
    }

    public boolean isShootMode() {
        return shootMode.isOn();
    }

    public void updateBalanceEnabled(boolean pressed) {
        balanceMode.update(pressed);
    }

    public void setBalanceEnabled(boolean enable) {
        if (enable) {
            balanceMode.set(ToggleButtonState.ON);
        } else {
            balanceMode.set(ToggleButtonState.OFF);
        }

    }

    public boolean isBalanceEnabled() {
        return balanceMode.isOn();
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

    public boolean readyToRaise() {
        return this.readyToRaise;
    }

    public boolean readyToLoad() {
        return this.readyToLoad;
    }

    public int ballsInControl() {
        return this.ballsInControl;
    }

    public boolean loaderReady() {
        if (ballsInControl() < RobotMap.MAX_BALLS) {
            return true;
        }
        return false;
    }

    public int getBallsInLoader() {
        return ballsInLoader;
    }

    public void loadedBall() {
        this.ballsInControl++;
        this.ballsInLoader++;
    }

    public void raisedBall() {
        this.ballsInLoader--;
    }

    public void unloadedBall() {
        this.ballsInControl--;
    }

    public void setGravity(double gravity) {
        this.gravity = gravity;
    }

    public double getGravity() {
        return gravity;
    }

    public void setHeading(double heading) {
        this.heading = heading;
    }

    public double getHeading() {
        return heading;
    }

    public void setReadyToLoad(boolean ready) {
        this.readyToLoad = ready;
    }

    public void setReadyToRaise(boolean ready) {
        this.readyToRaise = ready;
    }

    public void setReadyToShoot(boolean ready) {
        this.readyToShoot = ready;
    }

    public boolean isArmUp() {
        return armUp;
    }

    public void setArmUp(boolean armUp) {
        this.armUp = armUp;
    }

    public double getVin() {
        return vin;
    }

    public void setVin(double vin) {
        this.vin = vin;
    }
}
