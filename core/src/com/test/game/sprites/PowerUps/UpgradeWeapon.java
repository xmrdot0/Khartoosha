package com.test.game.sprites.PowerUps;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.test.game.Khartoosha;
import com.test.game.Weapons.Weapon;
import com.test.game.screens.PlayScreen;
import com.test.game.sprites.Character;


/**
 *   Upgrades Character's weapon
 *   if the character has the max weapon it's ammo is refilled instead
 */
public class UpgradeWeapon extends PowerUp {

    private TextureRegion powerupTexture;
    public UpgradeWeapon(World world, PlayScreen screen) {

        super(world,screen.GetAtlas().findRegion("armorPowerup"), 9995, 10000, 0);

        this.powerupTexture = new TextureRegion(getTexture(),0,0, 100, 100);
        setBounds(0,0, 35 / Khartoosha.PPM, 35 /Khartoosha.PPM);
        setRegion(powerupTexture);


        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(10 / Khartoosha.PPM,10 / Khartoosha.PPM);

        fdef.shape = shape;

        pupBody.createFixture(fdef).setUserData(this);
    }



    @Override
    public void effect(Character c) {

        // if the character has the max weapon type refill ammo instead
        if (attachedChar.currentWeapon.type == Weapon.MAX_TYPE)
            attachedChar.currentWeapon.refillAmmo();
        else
        {
            attachedChar.currentWeapon.type++;
            attachedChar.currentWeapon.switchWeapon();

        }
        resetPupPosition();

        reset();
    }


}