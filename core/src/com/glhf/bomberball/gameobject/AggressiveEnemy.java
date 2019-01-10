package com.glhf.bomberball.gameobject;

import com.glhf.bomberball.maze.cell.Cell;
import com.glhf.bomberball.utils.Directions;
import com.glhf.bomberball.utils.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.zip.DeflaterInputStream;

public class AggressiveEnemy extends Enemy {

    private int hunting_range;

    public AggressiveEnemy(String skin, int life, int initial_moves, int strength, int hunting_range) {
        super(skin, life, initial_moves, strength);
        // active mode when created
        this.hunting_range = hunting_range;
    }

    @Override
    public void createAI() {
        this.way = this.longest_way_moves_sequence(new Node(null, this.getCell()));
    }

    /**
     * this methods give to the agressif enemy the way he has to follow if the player is in his area given by range
     * this method is to be called at the beginning of the enemy's turn
     * @param cell_origin
     * @param range
     * @return ArrayList<Directions>
     */
    public static ArrayList<Directions> depth_graph_transversal(Cell cell_origin, int range) {
        ArrayList<Cell> cells = new ArrayList<Cell>();
        LinkedList<Cell> active_queue = new LinkedList<Cell>();
        LinkedList<Cell> inactive_queue = new LinkedList<Cell>();
        HashMap<Cell, ArrayList<Directions>> paths = new HashMap<>();
        int depth = 0;
        paths.put(cell_origin, new ArrayList<>());
        cells.add(cell_origin);
        active_queue.add(cell_origin);
        ArrayList<Directions> path_bis;
        // Invariant : Distance to all cells in the active queue is depth
        while (depth < range) {
            while (!active_queue.isEmpty()) {
                Cell c = active_queue.poll();
                for (Cell other : c.getAdjacentCells()) {
                    if (!cells.contains(other) && other.isWalkable()) {
                        inactive_queue.add(other);
                        path_bis = (ArrayList<Directions>) paths.get(c).clone();
                        path_bis.add(c.getCellDir(other));
                        paths.put(other, path_bis);
                        cells.add(other);
                    }
                }
            }
            depth++;

            active_queue = inactive_queue;
            inactive_queue = new LinkedList<Cell>();
        }
        for(Cell c : cells){
            if(!c.getInstancesOf(Player.class).isEmpty()){
                return paths.get(c);
            }
        }
        return null;
    }
}