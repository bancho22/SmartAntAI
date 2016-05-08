/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package self_learning.map;

/**
 *
 * @author Bancho
 */
public class TileInfo {
    
    private boolean hasEnemyAnt;
    private boolean hasFood;

    public TileInfo() {
    }

    public boolean hasEnemyAnt() {
        return hasEnemyAnt;
    }

    public void setHasEnemyAnt(boolean hasEnemyAnt) {
        this.hasEnemyAnt = hasEnemyAnt;
    }

    public boolean hasFood() {
        return hasFood;
    }

    public void setHasFood(boolean hasFood) {
        this.hasFood = hasFood;
    }
    
}
