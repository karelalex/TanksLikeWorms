package ru.naztrans.tanks;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen {
    private SpriteBatch batch;
    private TextureRegion textureBackground;
    private Map map;
    private BulletEmitter bulletEmitter;
    private ShapeRenderer shapeRenderer;
    private List<Tank> players;
    private int currentPlayerIndex;
    private BitmapFont font12;

    private Stage stage;
    private Skin skin;
    private Group playerJoystick;
    private ParticleEmitter particleEmitter;
    private BitmapFont font32;

    public List<Tank> getPlayers() {
        return players;
    }

    public BulletEmitter getBulletEmitter() {
        return bulletEmitter;
    }

    public Map getMap() {
        return map;
    }

    public static final float GLOBAL_GRAVITY = 300.0f;

    public boolean isMyTurn(Tank tank) {
        return tank == players.get(currentPlayerIndex);
    }

    public GameScreen(SpriteBatch batch) {
        this.batch = batch;
    }

    public Tank getCurrentTank() {
        return players.get(currentPlayerIndex);
    }

    public void checkNextTurn() {
        if (!players.get(currentPlayerIndex).makeTurn) {
            return;
        }
        if (!bulletEmitter.empty()) {
            return;
        }
        do {
            currentPlayerIndex++;
            if (currentPlayerIndex >= players.size()) {
                currentPlayerIndex = 0;
            }
        } while (!players.get(currentPlayerIndex).isAlive());
        players.get(currentPlayerIndex).takeTurn();
    }

    public void createGUI() {
        stage = new Stage(ScreenManager.getInstance().getViewport(), batch);
        skin = new Skin(Assets.getInstance().getAtlas());
        playerJoystick = new Group();
        Gdx.input.setInputProcessor(stage);
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("menuBtn");
        textButtonStyle.font = font32;
        skin.add("tbs", textButtonStyle);

        TextButton btnLeft = new TextButton("LEFT", skin, "tbs");
        TextButton btnRight = new TextButton("RIGHT", skin, "tbs");
        TextButton btnUp = new TextButton("UP", skin, "tbs");
        TextButton btnDown = new TextButton("DOWN", skin, "tbs");
        TextButton btnFire = new TextButton("FIRE", skin, "tbs");
//        TextButton btnExit = new TextButton("EXIT", skin, "tbs");

        btnLeft.setPosition(20, 140);
        btnRight.setPosition(300, 140);
        btnUp.setPosition(140, 240);
        btnDown.setPosition(140, 40);
        btnFire.setPosition(1000, 140);

        playerJoystick.addActor(btnLeft);
        playerJoystick.addActor(btnRight);
        playerJoystick.addActor(btnUp);
        playerJoystick.addActor(btnDown);
        playerJoystick.addActor(btnFire);

        stage.addActor(playerJoystick);

        btnLeft.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (getCurrentTank() instanceof PlayerTank) {
                    ((PlayerTank) getCurrentTank()).setCurrentAction(PlayerTank.Action.MOVE_LEFT);
                }
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (getCurrentTank() instanceof PlayerTank) {
                    ((PlayerTank) getCurrentTank()).setCurrentAction(PlayerTank.Action.IDLE);
                }
            }
        });
        btnRight.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (getCurrentTank() instanceof PlayerTank) {
                    ((PlayerTank) getCurrentTank()).setCurrentAction(PlayerTank.Action.MOVE_RIGHT);
                }
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (getCurrentTank() instanceof PlayerTank) {
                    ((PlayerTank) getCurrentTank()).setCurrentAction(PlayerTank.Action.IDLE);
                }
            }
        });
        btnLeft.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (getCurrentTank() instanceof PlayerTank) {
                    ((PlayerTank) getCurrentTank()).setCurrentAction(PlayerTank.Action.MOVE_LEFT);
                }
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (getCurrentTank() instanceof PlayerTank) {
                    ((PlayerTank) getCurrentTank()).setCurrentAction(PlayerTank.Action.IDLE);
                }
            }
        });
        btnUp.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (getCurrentTank() instanceof PlayerTank) {
                    ((PlayerTank) getCurrentTank()).setCurrentAction(PlayerTank.Action.TURRET_UP);
                }
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (getCurrentTank() instanceof PlayerTank) {
                    ((PlayerTank) getCurrentTank()).setCurrentAction(PlayerTank.Action.IDLE);
                }
            }
        });
        btnDown.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (getCurrentTank() instanceof PlayerTank) {
                    ((PlayerTank) getCurrentTank()).setCurrentAction(PlayerTank.Action.TURRET_DOWN);
                }
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (getCurrentTank() instanceof PlayerTank) {
                    ((PlayerTank) getCurrentTank()).setCurrentAction(PlayerTank.Action.IDLE);
                }
            }
        });
        btnFire.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (getCurrentTank() instanceof PlayerTank) {
                    ((PlayerTank) getCurrentTank()).setCurrentAction(PlayerTank.Action.FIRE);
                }
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (getCurrentTank() instanceof PlayerTank) {
                    ((PlayerTank) getCurrentTank()).setCurrentAction(PlayerTank.Action.IDLE);
                }
            }
        });
    }

    Vector2 v2tmp = new Vector2(0, 0);
    public void update(float dt) {
        playerJoystick.setVisible(getCurrentTank() instanceof PlayerTank);

        stage.act(dt);
        map.update(dt);
        for (int i = 0; i < players.size(); i++) {
            players.get(i).update(dt);
        }
        bulletEmitter.update(dt);
        checkCollisions();
        checkNextTurn();
        bulletEmitter.checkPool();


        particleEmitter.update(dt);
        particleEmitter.checkPool();
    }

    public void checkCollisions() {
        List<Bullet> b = bulletEmitter.getActiveList();
        for (int i = 0; i < b.size(); i++) {
            for (int j = 0; j < players.size(); j++) {
                particleEmitter.setup(b.get(i).getPosition().x, b.get(i).getPosition().y, 0, 0, 0.4f, 1.5f, 0.4f, 1, 0.2f, 0, 1, 1, 1f, 0, 0.5f);

                if (b.get(i).isArmed() && players.get(j).getHitArea().contains(b.get(i).getPosition())) {
                    b.get(i).deactivate();

                    float t=players.get(j).getHitArea().x-b.get(i).getPosition().x;
                    players.get(j).takeDamage(5, t);
                    map.clearGround(b.get(i).getPosition().x, b.get(i).getPosition().y, 8);

                    for (int k = 0; k < 25; k++) {
                        v2tmp.set(MathUtils.random(-1f, 1f), MathUtils.random(-1f, 1f));
                        v2tmp.nor();
                        v2tmp.scl(MathUtils.random(50f, 120f));
                        v2tmp.mulAdd(b.get(i).getVelocity(), 0.3f);
                        particleEmitter.setup(b.get(i).getPosition().x, b.get(i).getPosition().y, v2tmp.x, v2tmp.y, 0.4f, 1.8f, 0.4f, 1, 0, 0, 1, 1, 0.6f, 0, 1);
                    }
                    continue;
                }
            }
            if (map.isGround(b.get(i).getPosition().x, b.get(i).getPosition().y)) {
                b.get(i).deactivate();
                map.clearGround(b.get(i).getPosition().x, b.get(i).getPosition().y, 8);
                continue;
            }
            Bullet bullet = b.get(i);
            if (bullet.getPosition().x < 0 || bullet.getPosition().x > 1280 || bullet.getPosition().y > 720) {
                if (!bullet.isBouncing()) {
                    bullet.deactivate();
                } else {
                    if(bullet.getPosition().x < 0 && bullet.getVelocity().x < 0) {
                        bullet.getVelocity().x *= -1;
                    }
                    if(bullet.getPosition().x > 1280 && bullet.getVelocity().x > 0) {
                        bullet.getVelocity().x *= -1;
                    }
                    if(bullet.getPosition().y > 720 && bullet.getVelocity().y > 0) {
                        bullet.getVelocity().y *= -1;
                    }
                }
            }
        }
    }

    public boolean traceCollision(Tank aim, Bullet bullet, float dt) {
        if (bullet.isActive()) {
            bulletEmitter.updateBullet(bullet, dt);
            if (bullet.isArmed() && aim.getHitArea().contains(bullet.getPosition())) {
                bullet.deactivate();
                return true;
            }
            if (map.isGround(bullet.getPosition().x, bullet.getPosition().y)) {
                bullet.deactivate();
                return false;
            }
            if (bullet.getPosition().x < 0 || bullet.getPosition().x > 1280 || bullet.getPosition().y > 720) {
                if (!bullet.isBouncing()) {
                    bullet.deactivate();
                } else {
                    if(bullet.getPosition().x < 0 && bullet.getVelocity().x < 0) {
                        bullet.getVelocity().x *= -1;
                    }
                    if(bullet.getPosition().x > 1280 && bullet.getVelocity().x > 0) {
                        bullet.getVelocity().x *= -1;
                    }
                    if(bullet.getPosition().y > 720 && bullet.getVelocity().y > 0) {
                        bullet.getVelocity().y *= -1;
                    }
                }
                return false;
            }
        }
        return false;
    }
    @Override
    public void show() {
        font12 = Assets.getInstance().getAssetManager().get("zorque12.ttf", BitmapFont.class);
        font32 = Assets.getInstance().getAssetManager().get("zorque32.ttf", BitmapFont.class);
        textureBackground = Assets.getInstance().getAtlas().findRegion("background");
        map = new Map();
        players = new ArrayList<Tank>();
        players.add(new PlayerTank(this, new Vector2(400, 450), Ammo.BALL));
        players.add(new AiTank(this, new Vector2(800, 450), Ammo.B_STRAIGHT));
        //for (int i = 0; i < 10; i++) {
        //    players.add(new AiTank(this, new Vector2(MathUtils.random(0, 1000), 380)));
        //}
        currentPlayerIndex = 0;
        players.get(currentPlayerIndex).takeTurn();
        bulletEmitter = new BulletEmitter(50);
        particleEmitter = new ParticleEmitter();
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        createGUI();

        InputProcessor ip = new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                if (getCurrentTank() instanceof PlayerTank && keycode == Input.Keys.SPACE) {
                    ((PlayerTank) getCurrentTank()).setCurrentAction(PlayerTank.Action.FIRE);
                    return true;
                }
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                if (getCurrentTank() instanceof PlayerTank && keycode == Input.Keys.SPACE) {
                    ((PlayerTank) getCurrentTank()).setCurrentAction(PlayerTank.Action.IDLE);
                    return true;
                }
                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                return false;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                return false;
            }

            @Override
            public boolean scrolled(int amount) {
                return false;
            }
        };

        InputMultiplexer im = new InputMultiplexer(stage, ip);
        Gdx.input.setInputProcessor(im);
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(textureBackground, 0, 0);
        map.render(batch);
        for (int i = 0; i < players.size(); i++) {
            players.get(i).render(batch);
        }
        bulletEmitter.render(batch);
        for (int i = 0; i < players.size(); i++) {
            players.get(i).renderHUD(batch, font12);
        }
        particleEmitter.render(batch);
        batch.end();
//        shapeRenderer.begin();
//        for (int i = 0; i < players.size(); i++) {
//            shapeRenderer.circle(players.get(i).getHitArea().x, players.get(i).getHitArea().y, players.get(i).getHitArea().radius);
//        }
//        shapeRenderer.end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        ScreenManager.getInstance().onResize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    }
}