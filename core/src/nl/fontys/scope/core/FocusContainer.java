package nl.fontys.scope.core;

import nl.fontys.scope.Config;

public class FocusContainer {

    private int focus;

    public void addFocus() {
        focus++;
    }

    public void reduce() {
        if (hasFocus()) {
            focus--;
        }
    }

    public float getProgress() {
        return (float)focus / (float) Config.FOCUS_SOFT_GAP;
    }

    public boolean hasFocus() {
        return focus > 0;
    }

    public void clear() {
        this.focus = 0;
    }

    public int getFocus() {
        return focus;
    }
}
