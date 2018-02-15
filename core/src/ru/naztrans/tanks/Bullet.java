package ru.naztrans.tanks;

import com.badlogic.gdx.math.Vector2;


public class Bullet implements Poolable{
    private Vector2 position;

    public Vector2 getVelocity() {
        return velocity;
    }

    private Vector2 velocity;
    private boolean active;

    private float time;
    private boolean bouncing;

    public boolean isBouncing() {
        return bouncing;
    }

    public boolean isGravity() {
        return gravity;
    }

    private boolean gravity;

    public Vector2 getPosition() {
        return position;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isArmed() {
        return time > 0.2f;
    }

    public Bullet() {
        position = new Vector2(0, 0);
        velocity = new Vector2(0, 0);
        active = false;
        //angle = 0.0f;
        time = 0.0f;
    }

    public void deactivate() {
        active = false;
    }

    public void activate(float x, float y, float vx, float vy, boolean gravity, boolean bouncing) {
        position.set(x, y);
        velocity.set(vx, vy);
        active = true;
        time = 0.0f;
        this.gravity = gravity;
        this.bouncing = bouncing;
    }

    public void addTime(float dt) {
        time += dt;
    }
}