package nl.fontys.scope.core;

public class FocusContainer {

    public static final int FOCUS_SOFT_GAP = 10;

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
        return (float)focus / (float)FOCUS_SOFT_GAP;
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
