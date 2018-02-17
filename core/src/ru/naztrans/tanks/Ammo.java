package ru.naztrans.tanks;

public enum  Ammo { NORMAL (true, false), STRAIGHT(false, false), B_STRAIGHT(false, true), BALL(true,true);

    public boolean isGravity() {
        return isGravity;
    }

    public boolean isBounce() {
        return isBounce;
    }

    private boolean isGravity;
    private boolean isBounce;
    Ammo(boolean b, boolean b1) {
        this.isGravity=b;
        this.isBounce=b1;
    }
}
