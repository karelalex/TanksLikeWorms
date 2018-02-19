package ru.naztrans.tanks;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexk on 18.02.2018.
 */

public class Team {
    private int numberOfPlayerTanks, numberOfBotTanks;
    private String name;

    public String getName() {
        return name;
    }
    public void minusTank() {
        alive--;
    }

    public int getAlive() {
        return alive;
    }

    public List<Tank> getTanks() {
        return tanks;
    }
    public void reset(){
        alive=tanks.size();
    }
    private int alive;
    List<Tank> tanks;
    public Team(String name, int pt, int bt, GameScreen game, BulletEmitter.BulletType ... bulletTypes)
    {
        this.name=name;
        this.numberOfPlayerTanks=pt;
        this.numberOfBotTanks=bt;
        alive=pt+bt;
        tanks = new ArrayList<Tank>();

        BulletEmitter.BulletType bull;
        for (int i=0; i<pt; i++) {
            if (bulletTypes.length <= i) {
                bull = bulletTypes[bulletTypes.length - 1];
            } else {
                bull = bulletTypes[i];
            }
            tanks.add(new PlayerTank(game, new Vector2().setZero(), bull, this));
        }
        for (int i=pt; i<(pt+bt); i++) {
            if (bulletTypes.length <= i) {
                bull = bulletTypes[bulletTypes.length - 1];
            } else {
                bull = bulletTypes[i];
            }
            tanks.add(new AiTank(game, new Vector2().setZero(), bull, this));
        }
    }

}
