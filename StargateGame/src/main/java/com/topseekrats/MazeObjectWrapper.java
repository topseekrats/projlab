package com.topseekrats;

import com.sun.org.apache.regexp.internal.RE;
import com.topseekrats.background.Background;
import com.topseekrats.foreground.Foreground;

import java.util.Stack;

import static javafx.scene.input.KeyCode.R;

public class MazeObjectWrapper {

    private Actor[] actors;
    private Background background;
    private Stack<Foreground> foregrounds;
    private Replicator replicator;

    public void setActor(Actor actor) { actors[actor.getType().ordinal()] = actor; }
    public Actor getActor(ActorType type) { return actors[type.ordinal()]; }

    public void setBackground(Background background) { this.background = background; }
    public Background getBackground() { return background; }

    public void setForegrounds(Stack<Foreground> foregrounds) { this.foregrounds = foregrounds; }
    public Stack<Foreground> getForegrounds() { return foregrounds; }

    public void setReplicator(Replicator replicator) { this.replicator = replicator; }
    public Replicator getReplicator() { return replicator; }

    public void pushForeground(Foreground foreground) { foregrounds.push(foreground); }
    public Foreground popForeground() { return foregrounds.pop(); }

    public void setBackground(Background background){ this.background = background;}

}
