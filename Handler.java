/**
 * Handler class ensures all objects are rendered on screen.
 */

package com.main;

import com.main.GameObject;

import java.util.*;
import java.awt.*;

public class Handler
{
    LinkedList<GameObject> object = new LinkedList<>();

    public void tick()
    {
        for(int i = 0; i < object.size(); i++)
        {
            GameObject tempObject = object.get(i);
            tempObject.tick();
        }
    }

    public void render(Graphics g)
    {
        for(int i = 0; i < object.size(); i++)
        {
            GameObject tempObject = object.get(i);
            tempObject.render(g);
        }
    }

    public void addObject(GameObject object)
    {
        this.object.add(object);
    }

    public void removeObject(GameObject object)
    {
        this.object.remove(object);
    }
}