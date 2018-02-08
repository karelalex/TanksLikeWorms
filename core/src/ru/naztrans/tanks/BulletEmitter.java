package ru.naztrans.tanks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BulletEmitter {
    private TextureRegion bulletTexture;
    private Bullet[] bullets;

    public Bullet[] getBullets() {
        return bullets;
    }

    public BulletEmitter() {
        bulletTexture = Assets.getInstance().getAtlas().findRegion("ammo");
        bullets = new Bullet[250];
        for (int i = 0; i < bullets.length; i++) {
            bullets[i] = new Bullet();
        }
    }

    public boolean empty() {
        for (int i = 0; i < bullets.length; i++) {
            if (bullets[i].isActive()) {
                return false;
            }
        }
        return true;
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < bullets.length; i++) {
            if (bullets[i].isActive()) {
                batch.draw(bulletTexture, bullets[i].getPosition().x - 8, bullets[i].getPosition().y - 8);
            }
        }
    }

    public void update(float dt) {
        for (int i = 0; i < bullets.length; i++) {
            if (bullets[i].isActive()) {
                bullets[i].update(dt);
            }
        }
    }

    public Bullet setup(float x, float y, float vx, float vy) {
        for (int i = 0; i < bullets.length; i++) {
            if (!bullets[i].isActive()) {
                bullets[i].activate(x, y, vx, vy);
                return bullets[i];
            }
        }
        return null;
    }
}
