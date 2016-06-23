package com.pandy.particle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by andreasbrommund on 2016-06-23.
 */
public class Particle {

    private Vector2 pos,vel;
    private float originalLifeTime, lifeTime;
    private float originalSize, size;
    private float angle;
    private Color color;
    private boolean alive;

    public float getLifeTime() {
        return lifeTime;
    }

    public Particle(){
        resetParticle();
    }

    public Particle(Vector2 pos, float lifeTime, float angle, float size, float speed, Color color){
        updateParticle(pos,lifeTime,angle,size,speed,color);
    }

    public void resetParticle(){
        updateParticle(null,0,0,0,0,null);
    }

    public void updateParticle(Vector2 pos, float lifeTime, float angle, float size,float speed, Color color){
        alive = true;

        this.pos = pos;

        this.lifeTime = lifeTime;
        originalLifeTime = lifeTime;

        this.originalSize = size;

        this.angle = (float)Math.toRadians(angle);

        float velx = speed * (float) Math.cos(this.angle);
        float vely = speed * (float) Math.sin(this.angle);
        vel = new Vector2(velx,vely);

        this.color = color;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean update(float delta) {
        lifeTime -= delta;

        if (lifeTime > 0){

            float ageRation = lifeTime/originalLifeTime;
            size = originalSize;
            size = originalSize * ageRation;
            color.set(color.r,color.g,color.b,ageRation);

            pos.x += vel.x * delta;
            pos.y += vel.y * delta;
            return true; //Alive
        }else{
            return false; //Dead
        }
    }

    public void draw(ShapeRenderer shapeRenderer){
        if (alive) {
            shapeRenderer.setColor(color);
            shapeRenderer.circle(pos.x, pos.y, size);
        }
    }
}
