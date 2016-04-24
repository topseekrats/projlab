package com.topseekrats;

import com.topseekrats.background.Background;
import com.topseekrats.foreground.Foreground;

import java.util.Stack;

public class MazeObjectWrapper {

    private Actor[] actors = new Actor[2];
    private Background background = null;
    private Stack<Foreground> foregrounds = new Stack<Foreground>();
    private Replicator replicator = null;

    public void setActor(int id, Actor actor) { actors[id] = actor; }
    public Actor getActor(ActorType type) { return actors[type.ordinal()]; }

    public void setBackground(Background background) { this.background = background; }
    public Background getBackground() { return background; }

    public void setForegrounds(Stack<Foreground> foregrounds) { this.foregrounds = foregrounds; }
    public Stack<Foreground> getForegrounds() { return foregrounds; }

    public void setReplicator(Replicator replicator) { this.replicator = replicator; }
    public Replicator getReplicator() { return replicator; }

    public void pushForeground(Foreground foreground) { foregrounds.push(foreground); }
    public Foreground popForeground() {
        if (foregrounds.empty()) return null;
        return foregrounds.pop();
    }

}
