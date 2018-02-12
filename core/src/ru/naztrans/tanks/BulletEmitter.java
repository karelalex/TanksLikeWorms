package ru.naztrans.tanks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BulletEmitter extends ObjectPool<Bullet>{
    private TextureRegion bulletTexture;

    @Override
    protected Bullet newObject() {
        return new Bullet();
    }

    public BulletEmitter(int size) {
        super(size);
        bulletTexture = Assets.getInstance().getAtlas().findRegion("ammo");
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < activeList.size(); i++) {
            batch.draw(bulletTexture, activeList.get(i).getPosition().x - 8, activeList.get(i).getPosition().y - 8);
        }
    }

    public void update(float dt) {
        for (int i = 0; i < activeList.size(); i++) {
            activeList.get(i).update(dt);
        }
    }

    public boolean empty() {
        return getActiveList().size() == 0;
    }

    public Bullet setup(float x, float y, float vx, float vy) {
        Bullet b = getActiveElement();
        b.activate(x, y, vx, vy);
        return b;
    }
}
