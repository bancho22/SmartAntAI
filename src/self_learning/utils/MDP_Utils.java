/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package self_learning.utils;

import aiantwars.EAction;
import aiantwars.IAntInfo;
import burlap.behavior.singleagent.learning.lspi.SARSData;
import self_learning.domain.AntWarsDomainGenerator;
import burlap.oomdp.core.Domain;
import burlap.oomdp.core.objects.MutableObjectInstance;
import burlap.oomdp.core.objects.ObjectInstance;
import burlap.oomdp.core.states.MutableState;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.Action;
import burlap.oomdp.singleagent.GroundedAction;
import burlap.oomdp.singleagent.common.SimpleGroundedAction;
import java.util.Collection;
import java.util.List;
import static self_learning.domain.AntWarsDomainGenerator.TEAM_MEMBER_ANT;
import static self_learning.domain.AntWarsDomainGenerator.ATT_ANT_ID;
import static self_learning.domain.AntWarsDomainGenerator.ATT_HEALTH;
import static self_learning.domain.AntWarsDomainGenerator.ATT_FOODLOAD;
import static self_learning.domain.AntWarsDomainGenerator.ATT_TYPE;
import static self_learning.domain.AntWarsDomainGenerator.ATT_CARRYING_SOIL;
import static self_learning.domain.AntWarsDomainGenerator.ATT_STANDING_ON_FOOD;
import static self_learning.domain.AntWarsDomainGenerator.ATT_FACING_FOOD;
import static self_learning.domain.AntWarsDomainGenerator.ATT_FACING_ENEMY;
import self_learning.domain.Map;
import self_learning.domain.TileInfo;
import self_learning.utils.Helper;

/**
 *
 * @author Bancho
 */
public class MDP_Utils {
    
    private Domain antWarsDomain;
    private RewardFn rewardFn;
    private SARSData dataset;
    private State lastRecordedState;
    private State currentState;
    private GroundedAction lastActionPerformed;
    
    public MDP_Utils(){
        rewardFn = new RewardFn();
        dataset = new SARSData();
    }
    
    public void initAntWarsDomain(int worldSizeX, int worldSizeY){
        antWarsDomain = new AntWarsDomainGenerator(worldSizeX, worldSizeY).generateDomain();
    }
    
    private State constructState(Collection<IAntInfo> friendlyAnts, Map map){
        State s = new MutableState();
        int counter = 0;
        for (IAntInfo friendlyAnt : friendlyAnts) {
            int x = friendlyAnt.getLocation().getX();
            int y = friendlyAnt.getLocation().getY();
            TileInfo tileOfAnt = map.getTileInfo(x, y);
            int[] coordsOfTileInFrontOfAnt = Helper.findCoordsInFrontOf(x, y, friendlyAnt.getDirection());
            TileInfo tileInFrontOfAnt = map.getTileInfo(coordsOfTileInFrontOfAnt[0], coordsOfTileInFrontOfAnt[1]);
            ObjectInstance oi = new MutableObjectInstance(antWarsDomain.getObjectClass(TEAM_MEMBER_ANT), TEAM_MEMBER_ANT + "_" + counter);
            oi.setValue(ATT_ANT_ID, friendlyAnt.antID());
            oi.setValue(ATT_HEALTH, friendlyAnt.getHealth());
            oi.setValue(ATT_FOODLOAD, friendlyAnt.getFoodLoad());
            oi.setValue(ATT_TYPE, friendlyAnt.getAntType().getTypeName());
            oi.setValue(ATT_CARRYING_SOIL, friendlyAnt.carriesSoil());
            oi.setValue(ATT_STANDING_ON_FOOD, tileOfAnt.hasFood());
            if (tileInFrontOfAnt != null) {
                if (tileInFrontOfAnt.hasFood()) {
                    oi.setValue(ATT_FACING_FOOD, true);
                }else{
                    oi.setValue(ATT_FACING_FOOD, false);
                }
                if (tileInFrontOfAnt.hasEnemyAnt()) {
                    oi.setValue(ATT_FACING_ENEMY, true);
                }else{
                    oi.setValue(ATT_FACING_ENEMY, false);
                }
            }else{
                oi.setValue(ATT_FACING_FOOD, false);
                oi.setValue(ATT_FACING_ENEMY, false);
            }
            s.addObject(oi);
            counter++;
        }
        return s;
    }
    
    public void collectSARSData(Collection<IAntInfo> friendlyAnts, Map map){
        currentState = constructState(friendlyAnts, map);
        if(lastActionPerformed != null && lastRecordedState != null){
            double r = rewardFn.reward(lastRecordedState, lastActionPerformed, currentState);
            dataset.add(lastRecordedState, lastActionPerformed, r, currentState);
        }
        lastRecordedState = currentState;
    }
    
//    public void printShit(){
//        for (int i = 0; i < dataset.size(); i++) {
//            System.out.println(dataset.get(i).a);
//        }
//    }
    
//    public List<Action> getActions(){
//        return antWarsDomain.getActions();
//    }
//    
//    public GroundedAction getAction(String actionName){
//        return antWarsDomain.getAction(actionName).getAssociatedGroundedAction();
//    }

    public GroundedAction getLastActionPerformed() {
        return lastActionPerformed;
    }

    public void setLastActionPerformed(String lastActionPerformed) {
        this.lastActionPerformed = antWarsDomain.getAction(lastActionPerformed).getAssociatedGroundedAction();
    }
    
    
    
    //just for testing
    public static void main(String[] args) {
//        MDP_Utils utils = new MDP_Utils();
//        utils.initAntWarsDomain(16, 9);
//        Action a = utils.antWarsDomain.getAction(TURN_LEFT);
//        State res = a.performAction(new MutableState(), new SimpleGroundedAction(a));
//        System.out.println(res.getCompleteStateDescription());
    }
}
