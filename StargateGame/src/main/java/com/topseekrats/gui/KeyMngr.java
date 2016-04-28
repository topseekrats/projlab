package com.topseekrats.gui;

import com.topseekrats.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Mark-PC10 on 2016. 04. 25..
 */
public class KeyMngr implements KeyListener {
    private boolean keyPressed = false;
    private boolean keyLeftPressed = false;
    private boolean keyRightPressed = false;
    private boolean keyUpPressed = false;
    private boolean keyDownPressed = false;
    private static KeyMngr instance;

    private KeyMngr() {
    }

    public static KeyMngr getInstance() {
        if (instance == null) {
            instance = new KeyMngr();
        }
        return instance;
    }

    public void init() {
        setKeyPressed(false);
        setKeyLeftPressed(false);
        setKeyRightPressed(false);
        setKeyUpPressed(false);
        setKeyDownPressed(false);
    }

    /* (non-Javadoc)
     * @see org.eclipse.swt.events.KeyListener#keyPressed(org.eclipse.swt.events.KeyEvent)
     */
    public void keyPressed(KeyEvent e) {
        setKeyPressed(true);
        Log.log("v keyPressed: "+e.getKeyCode());
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            Log.log(" LEFT");
            setKeyLeftPressed(true);
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            Log.log(" RIGHT");
            setKeyRightPressed(true);
        }
        else if (e.getKeyCode() == KeyEvent.VK_UP) {
            Log.log(" UP");
            setKeyUpPressed(true);
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            Log.log(" DOWN");
            setKeyDownPressed(true);
        }

    }

    /* (non-Javadoc)
     * @see org.eclipse.swt.events.KeyListener#keyReleased(org.eclipse.swt.events.KeyEvent)
     */
    public void keyReleased(KeyEvent e) {
        Log.log("^ keyReleased: "+e.getKeyCode());
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            Log.log(" LEFT");
            setKeyLeftPressed(false);
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            Log.log(" RIGHT");
            setKeyRightPressed(false);
        }
        else if (e.getKeyCode() == KeyEvent.VK_UP) {
            Log.log(" UP");
            setKeyUpPressed(false);
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            Log.log(" DOWN");
            setKeyDownPressed(false);
        }
    }


    public void keyTyped(KeyEvent e) {
        Log.log("keyTyped: "+e.getKeyCode());
    }


    public synchronized void setKeyLeftPressed(boolean keyLeftPressed) {
        this.keyLeftPressed = keyLeftPressed;
        if (!keyLeftPressed) {
            Maze maze = Maze.getInstance();
            int x = maze.actorsPosition[0][0];
            int y = maze.actorsPosition[0][1];
            Log.log("x:"+x+" y:"+y);
            if (maze.playField[x][y].getActor(ActorType.COLONEL) != null) {
                Log.log("van COLONEL");
            } else {
                Log.log("NINCS COLONEL!!");
            }

            maze.moveDirection[0] = MoveDirection.LEFT;
            maze.playField[x][y].getActor(ActorType.COLONEL).move();

            x = maze.actorsPosition[0][0];
            y = maze.actorsPosition[0][1];
            Log.log("ÚJ x:"+x+" y:"+y);
            if (maze.playField[x][y].getActor(ActorType.COLONEL) != null) {
                Log.log("van COLONEL");
            } else {
                Log.log("NINCS COLONEL!!");
            }
        }
    }


    public synchronized boolean isKeyLeftPressed() {
        return keyLeftPressed;
    }


    public synchronized void setKeyRightPressed(boolean keyRightPressed) {
        this.keyRightPressed = keyRightPressed;
        if (!keyRightPressed) {
            int x = Maze.getInstance().actorsPosition[0][0];
            int y = Maze.getInstance().actorsPosition[0][1];
            Maze.getInstance().moveDirection[0] = MoveDirection.RIGHT;
            Maze.getInstance().playField[x][y].getActor(ActorType.COLONEL).move();
        }
    }


    public synchronized boolean isKeyRightPressed() {
        return keyRightPressed;
    }


    public synchronized void setKeyUpPressed(boolean keyUpPressed) {
        this.keyUpPressed = keyUpPressed;
        if (!keyUpPressed) {
            int x = Maze.getInstance().actorsPosition[0][0];
            int y = Maze.getInstance().actorsPosition[0][1];
            Maze.getInstance().moveDirection[0] = MoveDirection.UP;
            Maze.getInstance().playField[x][y].getActor(ActorType.COLONEL).move();
        }
    }


    public synchronized boolean isKeyUpPressed() {
        return keyUpPressed;
    }


    public synchronized void setKeyDownPressed(boolean keyDownPressed) {
        this.keyDownPressed = keyDownPressed;
        if (!keyDownPressed) {
            int x = Maze.getInstance().actorsPosition[0][0];
            int y = Maze.getInstance().actorsPosition[0][1];
            Maze.getInstance().moveDirection[0] = MoveDirection.DOWN;
            Maze.getInstance().playField[x][y].getActor(ActorType.COLONEL).move();
        }
    }


    public synchronized boolean isKeyDownPressed() {
        return keyDownPressed;
    }

    public synchronized boolean isKeyPressed() {
        return keyPressed;
    }

    public synchronized void setKeyPressed(boolean keyPressed) {
        this.keyPressed = keyPressed;
    }

    public synchronized boolean isKeyPressedAndReset() {
        boolean result = keyPressed;
        keyPressed = false;
        return result;
    }
}
