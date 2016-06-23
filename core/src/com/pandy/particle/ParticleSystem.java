package com.pandy.particle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.Arrays;

/**
 * Created by andreasbrommund on 2016-06-23.
 */
public class ParticleSystem {


    private Particle[] particlePool;
    private int poolSize;
    private int particleIndex;

    private boolean spawn;

    private int max = 4;

    public ParticleSystem () {

        spawn = true;

        poolSize = 1000;
        particleIndex = -1;

        particlePool = new Particle[poolSize]; //TODO set a proper size

        for (int i = 0; i < poolSize; i++) {
            particlePool[i] = new Particle();
        }

    }

    public void update(float delta) {

        while(spawn){
            spawnParticle();
            if(particleIndex == max-1){
                spawn = false;
            }
        }



        int index = 0;
        while(index <= particleIndex){
            if(!particlePool[index].update(delta)){
                System.out.println("----");
                System.out.println(particleIndex);
                killParticle(index);
                System.out.println(particleIndex);
            }else{
                index++;
            }
        }
    }

    public void draw(ShapeRenderer shapeRenderer){
        for(int i = 0; i <= particleIndex; i++){
            particlePool[i].draw(shapeRenderer);
        }
    }

    private void killParticle(int index){
        //Swap the dead with last alive particle
        Particle dead = particlePool[index];
        dead.resetParticle();
        dead.setAlive(false);
        particlePool[index] = particlePool[particleIndex];
        particlePool[particleIndex] = dead;

        particleIndex--;
    }

    private void spawnParticle(){
        Vector2 pos = new Vector2(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
        float lifeTime = (float) (Math.random()*10+1);   //2s to 5s
        float angle = (float) (Math.random()*360);      //0 to 180 deg
        float size = 9;
        float speed = (float) (Math.random()*10+5);    //10 to 100
        Color color = Color.CORAL;

        particleIndex++;

        if(particleIndex == poolSize){
            poolSize *= 2;
            particlePool = Arrays.copyOf(particlePool,poolSize);
            for(int i = poolSize/2;i < poolSize;i++){
                particlePool[i] = new Particle();
            }
        }
        particlePool[particleIndex].updateParticle(pos,lifeTime,angle,size,speed,color);
    }
}