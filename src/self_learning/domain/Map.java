/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package self_learning.domain;

import aiantwars.IAntInfo;
import aiantwars.ILocationInfo;
import java.util.HashMap;
import self_learning.utils.Helper;

/**
 *
 * @author Bancho
 */
public class Map {
    
    private HashMap<String, TileInfo> mapInfo;
    private final int teamID;
    //private SharedConsciousness omni;
    
    public Map(int worldSizeX, int worldSizeY, int teamID){
        mapInfo = new HashMap<>();
        for (int x = 0; x < worldSizeX; x++) {
            for (int y = 0; y < worldSizeY; y++) {
                mapInfo.put(Helper.toStringCoords(x, y), new TileInfo());
            }
        }
        this.teamID = teamID;
    }
    
    public TileInfo getTileInfo(int x, int y){
        return mapInfo.get(Helper.toStringCoords(x, y));
    }
    
    public void update(ILocationInfo location){
        int x = location.getX();
        int y = location.getY();
        TileInfo tileInfo = getTileInfo(x, y);
        IAntInfo ant = location.getAnt();
        if (ant != null) {
            if (ant.getTeamInfo().getTeamID() != teamID) {
                tileInfo.setHasEnemyAnt(true);
            }
            tileInfo.setHasEnemyAnt(false);
        }else{
            tileInfo.setHasEnemyAnt(false);
        }
        if (location.getFoodCount() > 0) {
            tileInfo.setHasFood(true);
        }else{
            tileInfo.setHasFood(false);
        }
    }

}
