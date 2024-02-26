package com.mygdx.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class Spieler extends at.sma.screengame.actors.SpielObjekt {

    private Rectangle boundary;
    private float accelleration = 0.3f;
    private float speed = 2;

    private int direction = 0;

    public Spieler(int x, int y, Texture image) {
        super(x, y, image);
        boundary = new Rectangle();
        this.setBoundary();

    }

    public void setBoundary(){
        this.boundary.set(this.getX(),this.getY(),this.getWidth(),this.getHeight());
    }

    @Override
    public void draw(Batch b, float parentaplha) {
        this.getImage().draw(b);
    }

    public void update(float delta){

    }

    public void move(int direction){

        if (direction != this.direction){
            speed = 2;
        }
        speed +=accelleration;
        //Gdx.app.log("spieler.move","move spieler");
        if(direction == 1){
            this.setX(this.getX()-speed);
        }else {
            this.setX(this.getX()+speed);
        }
        //muss Grafikposition neu berechnen!!
        this.setBoundary();
        this.direction = direction;
    }
    public void act(float delta){
        super.act(delta);
        this.update(delta);
    }


    public boolean collideRectangle(Rectangle shape) {
        if(Intersector.overlaps(this.boundary, shape)){
            return true;
        }else {
            return false;
        }
    }
}
