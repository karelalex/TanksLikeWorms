package ru.naztrans.tanks;


import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class AiTank extends Tank {
    private static final float TARGETING_POWER_ERROR = 150.0f;
    private static final float TARGETING_POWER_ANGLE = 20.0f;
    private BulletEmitter.BulletType ammo;
    private Random rnd=new Random();

    public AiTank(GameScreen game, Vector2 position, BulletEmitter.BulletType ammo, Team team) {
        super(game, position, team);
        this.ammo=ammo;
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        if (game.isMyTurn(this) && !makeTurn) {
            float tmpAngle = 0.0f;
            float tmpPower = 0.0f;
            Tank aim = null;

//            for (int i = 0; i < game.getPlayers().size(); i++) {
//                if (game.getPlayers().get(i) != this /*&& game.getPlayers().getClass()!=this.getClass()*/) {
//                    aim = game.getPlayers().get(i);
//                    break;
//                }
//            }
            do  {
                aim=game.getPlayers().get(rnd.nextInt(game.getPlayers().size()));
                if (aim.team==this.team){
                    aim=null;
                }
                }
            while (aim==null);

            boolean ready = false;
            do {
                tmpPower = MathUtils.random(MINIMAL_POWER, maxPower);
                tmpAngle = MathUtils.random(0, 180.0f);

                float ammoPosX = weaponPosition.x + 12 + 28 * (float) Math.cos(Math.toRadians(tmpAngle));
                float ammoPosY = weaponPosition.y + 16 + 28 * (float) Math.sin(Math.toRadians(tmpAngle));

                float ammoVelX = tmpPower * (float) Math.cos(Math.toRadians(tmpAngle));
                float ammoVelY = tmpPower * (float) Math.sin(Math.toRadians(tmpAngle));

                Bullet tmpBullet = game.getBulletEmitter().setup(ammo, ammoPosX, ammoPosY, ammoVelX, ammoVelY);

                do {
                    ready = game.traceCollision(aim, tmpBullet, dt);
                } while (!ready && tmpBullet.isActive());
            } while (!ready);

            game.getBulletEmitter().checkPool();

            turretAngle = tmpAngle + MathUtils.random(-TARGETING_POWER_ANGLE / 2, TARGETING_POWER_ANGLE / 2);
            tmpPower = tmpPower + MathUtils.random(-TARGETING_POWER_ERROR / 2, TARGETING_POWER_ERROR / 2);

            float ammoPosX = weaponPosition.x + 12 + 28 * (float) Math.cos(Math.toRadians(tmpAngle));
            float ammoPosY = weaponPosition.y + 16 + 28 * (float) Math.sin(Math.toRadians(tmpAngle));

            float ammoVelX = tmpPower * (float) Math.cos(Math.toRadians(turretAngle));
            float ammoVelY = tmpPower * (float) Math.sin(Math.toRadians(turretAngle));

            game.getBulletEmitter().setup(ammo, ammoPosX, ammoPosY, ammoVelX, ammoVelY);
            makeTurn = true;
            power = 0.0f;
        }
    }
}