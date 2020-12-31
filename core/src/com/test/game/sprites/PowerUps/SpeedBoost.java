package com.test.game.sprites.PowerUps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.test.game.Khartoosha;
import com.test.game.screens.PlayScreen;
import com.test.game.sprites.Character;

public class SpeedBoost  extends PowerUp
{
    public final int type = 0;


    // a random number less than max_rate is generated if it's larger than spawn_rate then it's spawned
    // probability of spawn = (maxrate - spawnrate) / max_rate
    private final int spawnRate = 9980, maxRate = 10000;

    // the scale which the speed of character is multiplied by
    private final float speedBoost = 2.0f;

    private final float MAX_TIME = 10;
    private TextureRegion powerupTexture;




    public SpeedBoost(World world, PlayScreen screen)
    {

        super(world,screen.GetAtlas().findRegion("armorPowerup"));

        this.powerupTexture = new TextureRegion(getTexture(),100,0, 100, 100);
        setBounds(0,0, 35 /Khartoosha.PPM, 35 /Khartoosha.PPM);
        setRegion(powerupTexture);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(10 / Khartoosha.PPM);

        fdef.shape = shape;

        pupBody.createFixture(fdef).setUserData(this);


    }


    @Override
    public void spawn() {

        // if not spawned and not active spawn it
        if (!isSpawned() && !isActive() && rand.nextInt(maxRate) > spawnRate)
        {
            pupBody.setType(BodyDef.BodyType.DynamicBody);
            setSpawned(true);
            currentPups++;
        }
    }



    @Override
    public void effect(Character player) {
        //activate
        setActive(true);
        // reset pup Position
        resetPupPosition();

        // increase player speed
        player.setSpeedCap(speedBoost);

    }


    @Override
    public void update()
    {
        if (pupBody.getPosition().y < - 2)
            resetPupPosition();
        setPosition(pupBody.getPosition().x-getWidth()/5, pupBody.getPosition().y-getHeight()/3);

        if (isContacted)
        {
            effect(attachedChar);
            isContacted = false;
        }
        if (isActive())
        {
            active_time += Gdx.graphics.getDeltaTime();
            if (active_time > MAX_TIME)
            {
                reset();
            }

        }

    }

    @Override
    public void reset() {
        active_time = 0;
        setSpawned(false);
        setActive(false);
        if (attachedChar != null)
            attachedChar.resetSpeedCap();
        attachedChar = null;
        currentPups--;
        platforms_To_Skip = rand.nextInt(MAX_PLATFORMS);
    }








}
