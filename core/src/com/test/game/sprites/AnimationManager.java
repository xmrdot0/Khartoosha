package com.test.game.sprites;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.lang.invoke.SwitchPoint;

public class AnimationManager {
    public enum State { JUMPING, STANDING, RUNNING, DEAD };
    public State currentState;
    public State previousState;
    private boolean faceRight;
    private float stateTimer;
    private Array<TextureRegion> frames;
    private Animation tempAnimation;
    private Character player;
    public AnimationManager(boolean faceRight, Texture texture, Character player) {
        this.faceRight = faceRight;
        this.currentState = State.STANDING;
        this.previousState = State.STANDING;
        this.player = player;

    }
    public Animation runAnimation(Texture texture){
        frames = new Array<>();
        for (int i=1;i<5;i++){
            frames.add(new TextureRegion(texture,(i * 95),130,95,130 ));
        }
        tempAnimation = new Animation (0.1f,frames);
        return tempAnimation;
    }
    public TextureRegion getFrame(float delta){
        currentState = getState();
        TextureRegion region;
        switch(currentState){
            case RUNNING:
                region = (TextureRegion) player.runAnimation.getKeyFrame(stateTimer,true);
                break;
            case STANDING:
            default:
                region = player.idle;
        }
        if ((player.physicsBody.getLinearVelocity().x < 0 || !faceRight) && !region.isFlipX()){
            region.flip(true,false);
            faceRight=false;
        }
        else if ((player.physicsBody.getLinearVelocity().x >0 || faceRight) && region.isFlipX()){
            region.flip(true,false);
            faceRight=true;
        }
        stateTimer = currentState == previousState? stateTimer+delta:0;
        previousState = currentState;
        return region;
    }
    public State getState(){
        if (player.physicsBody.getLinearVelocity().y > 0)
            return State.JUMPING;
        if (player.physicsBody.getLinearVelocity().x!= 0)
            return State.RUNNING;
        else return State.STANDING;
    }
    public void clearFrames(){frames.clear(); tempAnimation=null;}
}
