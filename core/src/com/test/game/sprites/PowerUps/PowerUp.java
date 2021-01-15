package com.test.game.sprites.PowerUps;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.test.game.Khartoosha;
import com.test.game.screens.PlayScreen;
import com.test.game.sprites.Character;

import java.util.Random;

public abstract class PowerUp extends Sprite {
    public World world;
    public Body pupBody;
    protected Random rand = new Random(); // Random generator

    public static final int MAXPUPS = 6; // max allowed spawned pups
    public static int currentPups = 0; //number of spawned pups at any moment

    public Character attachedChar;

    public float active_time = 0;
    private boolean isSpawned = false;
    private boolean isActive = false;
    protected boolean isContacted = false;
    public final int MAX_PLATFORMS = 6;
    public int platforms_To_Skip = rand.nextInt(MAX_PLATFORMS);


    public PowerUp(World world, TextureAtlas.AtlasRegion region)
    {
        super(region);
        this.world = world;
        initPup();
    }

    private void initPup()
    {
        BodyDef bdef = new BodyDef();
        //initial position is set randomly in game world
        bdef.position.set(rand.nextInt((int)Khartoosha.Gwidth - 150) / Khartoosha.PPM + (100 / Khartoosha.PPM) , Khartoosha.Gheight / Khartoosha.PPM + (300 / Khartoosha.PPM));
        bdef.type = BodyDef.BodyType.StaticBody;
        pupBody = world.createBody(bdef);
    }

    /**
     *Returns true if spawned
     */
    public boolean isSpawned(){return isSpawned;}

    public void setSpawned(boolean spawned) {
        isSpawned = spawned;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isContacted() { return isContacted; }

    public void setContacted(boolean contacted) { isContacted = contacted; }

    /**
     Function handles the spawn of any pup by checking conditions
     */
    public abstract void spawn();

    /**
       Applies effect of the powerup  to the game
     */
    public abstract void effect(Character c);

    /**
     * Update timers of pup
     */
    public abstract void update();

    /**
        Reset effects of the powerup
     */
    public abstract void reset();

    public void resetPupPosition()
    {
        pupBody.setTransform(rand.nextInt((int)Khartoosha.Gwidth - 100) / Khartoosha.PPM + 2,
                Khartoosha.Gheight / Khartoosha.PPM + 3,0);
        pupBody.setType(BodyDef.BodyType.StaticBody);
    }




}
