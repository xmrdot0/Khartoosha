package com.test.game.screens.play_screen.Powerups;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.test.game.Khartoosha;
import com.test.game.screens.play_screen.PlayScreen;
import com.test.game.screens.play_screen.Character;

/**
 * Add Extra life to character if not at max lives
 */
public class ExtraLife extends PowerUp {

    private TextureRegion powerupTexture;

    public ExtraLife(World world, PlayScreen screen) {

        super(world,screen.GetAtlas().findRegion("armorPowerup"), 9975, 10000, 0);

        this.powerupTexture = new TextureRegion(getTexture(),0*434 ,0, 434, 418);
        //setOrigin(1.0f* getTexture().getWidth() / 2,1.0f * getTexture().getHeight() / 2);
        setBounds(0,0, 35 / Khartoosha.PPM, 35 /Khartoosha.PPM);
        setRegion(powerupTexture);
        //setOrigin(1.0f* getTexture().getWidth() / 2,1.0f * getTexture().getHeight() / 2);


        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(15 / Khartoosha.PPM);

        fdef.shape = shape;

        pupBody.createFixture(fdef).setUserData(this);
    }



    @Override
    public void effect(Character c) {
        if (c.current_lives < c.getMAX_LIVES())
            c.current_lives++;

        resetPupPosition();

        reset();
    }

}
