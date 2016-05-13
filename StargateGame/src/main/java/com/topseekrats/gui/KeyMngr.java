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
    private boolean keyWPressed = false;
    private boolean keyAPressed = false;
    private boolean keySPressed = false;
    private boolean keyDPressed = false;
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
        setKeyWPressed(false);
        setKeyAPressed(false);
        setKeySPressed(false);
        setKeyDPressed(false);
    }

    /* (non-Javadoc)
     * @see org.eclipse.swt.events.KeyListener#keyPressed(org.eclipse.swt.events.KeyEvent)
     */

    /**
     * Billentyű lenyomása esetén fut le
     * @param e
     */
    public void keyPressed(KeyEvent e) {
        setKeyPressed(true);
        // Console.log("v keyPressed: "+e.getKeyCode());
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            Console.log(" LEFT");
            setKeyLeftPressed(true);
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            Console.log(" RIGHT");
            setKeyRightPressed(true);
        }
        else if (e.getKeyCode() == KeyEvent.VK_UP) {
            Console.log(" UP");
            setKeyUpPressed(true);
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            Console.log(" DOWN");
            setKeyDownPressed(true);
        }
        else if (e.getKeyCode() == KeyEvent.VK_W) {
            Console.log(" W");
            setKeyWPressed(true);
        }
        else if (e.getKeyCode() == KeyEvent.VK_A) {
            Console.log(" A");
            setKeyAPressed(true);
        }
        else if (e.getKeyCode() == KeyEvent.VK_S) {
            Console.log(" S");
            setKeySPressed(true);
        }
        else if (e.getKeyCode() == KeyEvent.VK_D) {
            Console.log(" D");
            setKeyDPressed(true);
        }

    }

    /* (non-Javadoc)
     * @see org.eclipse.swt.events.KeyListener#keyReleased(org.eclipse.swt.events.KeyEvent)
     */

    /**
     * Billentyű elengedése esetén fut le
     * @param e
     */
    public void keyReleased(KeyEvent e) {
        // Console.log("^ keyReleased: "+e.getKeyCode());
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            // Console.log(" LEFT");
            setKeyLeftPressed(false);
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            // Console.log(" RIGHT");
            setKeyRightPressed(false);
        }
        else if (e.getKeyCode() == KeyEvent.VK_UP) {
            // Console.log(" UP");
            setKeyUpPressed(false);
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            // Console.log(" DOWN");
            setKeyDownPressed(false);
        }
        else if (e.getKeyCode() == KeyEvent.VK_W) {
            // Console.log(" W");
            setKeyWPressed(false);
        }
        else if (e.getKeyCode() == KeyEvent.VK_A) {
            // Console.log(" A");
            setKeyAPressed(false);
        }
        else if (e.getKeyCode() == KeyEvent.VK_S) {
            // Console.log(" S");
            setKeySPressed(false);
        }
        else if (e.getKeyCode() == KeyEvent.VK_D) {
            // Console.log(" D");
            setKeyDPressed(false);
        }
    }


    public void keyTyped(KeyEvent e) {
        // Console.log("keyTyped: "+e.getKeyCode());
    }


    public synchronized void setKeyLeftPressed(boolean keyLeftPressed) {
        this.keyLeftPressed = keyLeftPressed;
        if (!keyLeftPressed) {
            Maze maze = Maze.getInstance();
            int x = maze.actorsPosition[0][0];
            int y = maze.actorsPosition[0][1];
            // Console.log("x:"+x+" y:"+y);
            /*if (maze.playField[x][y].getActor(ActorType.COLONEL) != null) {
                Console.log("van COLONEL");
            } else {
                Console.log("NINCS COLONEL!!");
            }*/

            maze.moveDirection[0] = MoveDirection.LEFT;
            if (maze.playField[x][y].getActor(ActorType.COLONEL) != null)
                maze.playField[x][y].getActor(ActorType.COLONEL).move();

            /*x = maze.actorsPosition[0][0];
            y = maze.actorsPosition[0][1];
            // Console.log("ÚJ x:"+x+" y:"+y);
            if (maze.playField[x][y].getActor(ActorType.COLONEL) != null) {
                Console.log("van COLONEL");
            } else {
                Console.log("NINCS COLONEL!!");
            }*/
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
            if (Maze.getInstance().playField[x][y].getActor(ActorType.COLONEL) != null)
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
            if (Maze.getInstance().playField[x][y].getActor(ActorType.COLONEL) != null)
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
            if (Maze.getInstance().playField[x][y].getActor(ActorType.COLONEL) != null)
                Maze.getInstance().playField[x][y].getActor(ActorType.COLONEL).move();
        }
    }

    public synchronized void setKeyWPressed(boolean keyWPressed) {
        this.keyWPressed = keyWPressed;
        if (!keyWPressed) {
            int x = Maze.getInstance().actorsPosition[1][0];
            int y = Maze.getInstance().actorsPosition[1][1];
            Maze.getInstance().moveDirection[1] = MoveDirection.UP;
            if (Maze.getInstance().playField[x][y].getActor(ActorType.JAFFA) != null)
                Maze.getInstance().playField[x][y].getActor(ActorType.JAFFA).move();
        }
    }

    public synchronized void setKeyAPressed(boolean keyAPressed) {
        this.keyAPressed = keyAPressed;
        if (!keyAPressed) {
            int x = Maze.getInstance().actorsPosition[1][0];
            int y = Maze.getInstance().actorsPosition[1][1];
            Maze.getInstance().moveDirection[1] = MoveDirection.LEFT;
            if (Maze.getInstance().playField[x][y].getActor(ActorType.JAFFA) != null)
                Maze.getInstance().playField[x][y].getActor(ActorType.JAFFA).move();
        }
    }

    public synchronized void setKeySPressed(boolean keySPressed) {
        this.keySPressed = keySPressed;
        if (!keySPressed) {
            int x = Maze.getInstance().actorsPosition[1][0];
            int y = Maze.getInstance().actorsPosition[1][1];
            Maze.getInstance().moveDirection[1] = MoveDirection.DOWN;
            if (Maze.getInstance().playField[x][y].getActor(ActorType.JAFFA) != null)
                Maze.getInstance().playField[x][y].getActor(ActorType.JAFFA).move();
        }
    }

    public synchronized void setKeyDPressed(boolean keyDPressed) {
        this.keyDPressed = keyDPressed;
        if (!keyDPressed) {
            int x = Maze.getInstance().actorsPosition[1][0];
            int y = Maze.getInstance().actorsPosition[1][1];
            Maze.getInstance().moveDirection[1] = MoveDirection.RIGHT;
            if (Maze.getInstance().playField[x][y].getActor(ActorType.JAFFA) != null)
                Maze.getInstance().playField[x][y].getActor(ActorType.JAFFA).move();
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
