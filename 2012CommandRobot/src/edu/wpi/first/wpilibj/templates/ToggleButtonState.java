package edu.wpi.first.wpilibj.templates;

public class ToggleButtonState {

    public final static int OFF = 0;
    public final static int RECENTLY_OFF = 1;
    public final static int ON = 2;
    public final static int RECENTLY_ON = 3;
    private int state = OFF;

    public ToggleButtonState() {
        this.state = OFF;
    }

    public ToggleButtonState(int state) {
        if (state != OFF && state != RECENTLY_OFF && state != ON && state != RECENTLY_ON) {
            state = OFF;
        }
        this.state = state;
    }

    public boolean isOn() {
        if (this.state == ON || this.state == RECENTLY_ON) {
            return true;
        }
        return false;
    }

    public void update(boolean pressed) {
        if (pressed) {
            if (this.state == OFF) {
                this.state = RECENTLY_OFF;
            } else if (this.state == ON) {
                this.state = RECENTLY_ON;
            }
        } else {
            if (this.state == RECENTLY_OFF) {
                this.state = ON;
            } else if (this.state == RECENTLY_ON) {
                this.state = OFF;
            }
        }
    }
}