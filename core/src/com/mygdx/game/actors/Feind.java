package com.mygdx.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.Random;

public class Feind extends at.sma.screengame.actors.SpielObjekt {

    private int acceleration = (int) 0.1f;
    private float geschwindigkeit = 2;
    private int pixel;
    private Rectangle boundary;
    private ArrayList<Feind> fList;


    public Feind(int x, int y, Texture image, int pixel,ArrayList<Feind>fList) {
        super(x, y, image);
        boundary = new Rectangle();
        this.pixel = pixel;

        // setze gleich den Delay
        this.fList =fList;
        this.setRandomPosition();
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

        this.setY((int)(this.getY()- pixel));

        if (this.getY() < 0) {
            setRandomPosition();
        }
        this.setBoundary();
    }
    public boolean collideRectangle(Rectangle shape) {
        if(Intersector.overlaps(this.boundary, shape)){
            return true;
        }else {
            return false;
        }
    }

    public void setRandomPosition(){
        Random r = new Random();
        Boolean collision = true;
        int ry = 0;
        int rx = 0;
        while(collision){
            int minY = Gdx.graphics.getHeight() + (int) this.getHeight();
            int maxW = (int) ((int) Gdx.graphics.getWidth() - this.getHeight());
            rx = r.nextInt(maxW + 1 - 0)+ 0;
            ry = r.nextInt(2000 + 1 - minY) + minY;

            Rectangle rect = new Rectangle(rx, ry, this.getWidth(), this.getHeight());
            collision = false;
            for (Feind f: this.fList){
                if (Intersector.overlaps(rect, f.getBoundary())){
                    collision = true;
                    break;
                }
            }
        }
        this.setY(ry);
        this.setX(rx);

    }


    @Override
    public void act(float delta) {
        super.act(delta);
        this.update(delta);
    }

    public Rectangle getBoundary() {
        return boundary;
    }


}
