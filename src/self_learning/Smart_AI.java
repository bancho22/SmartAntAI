/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package self_learning;

import aiantwars.EAction;
import aiantwars.EAntType;
import aiantwars.IAntAI;
import aiantwars.IAntInfo;
import aiantwars.IEgg;
import aiantwars.ILocationInfo;
import burlap.behavior.singleagent.learning.lspi.SARSData;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.Action;
import burlap.oomdp.singleagent.GroundedAction;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import self_learning.actions.EatFood;
import self_learning.domain.AntWarsDomainGenerator;
import static self_learning.domain.AntWarsDomainGenerator.LAY_SCOUT_EGG;
import static self_learning.domain.AntWarsDomainGenerator.LAY_WARRIOR_EGG;
import self_learning.domain.Map;
import self_learning.utils.MDP_Utils;
import self_learning.utils.RewardFn;

/**
 *
 * @author Bancho
 */
public class Smart_AI implements IAntAI {

    private MDP_Utils mdpUtils;
    private HashMap<Integer, IAntInfo> friendlyAnts;
    private Map map;
    private EAntType nextEggToLay;

    
    public Smart_AI(){
        mdpUtils = new MDP_Utils();
        friendlyAnts = new HashMap<>();
    }
    
    @Override
    public void onHatch(IAntInfo thisAnt, ILocationInfo thisLocation, int worldSizeX, int worldSizeY) {
        if (thisAnt.getAntType() == EAntType.QUEEN) {
            mdpUtils.initAntWarsDomain(worldSizeX, worldSizeY);
            map = new Map(worldSizeX, worldSizeY, thisAnt.getTeamInfo().getTeamID());
        }
        friendlyAnts.put(thisAnt.antID(), thisAnt);
        map.update(thisLocation);
    }

    @Override
    public void onStartTurn(IAntInfo thisAnt, int turn) {
        friendlyAnts.put(thisAnt.antID(), thisAnt);
        map.update(thisAnt.getLocation());
        if (turn == 69) {
//            State curState = mdpUtils.constructState(friendlyAnts.values(), map);
//            System.out.println("turn" + turn);
//            System.out.println(curState.getCompleteStateDescription());
        }
    }

    @Override
    public EAction chooseAction(IAntInfo thisAnt, ILocationInfo thisLocation, List<ILocationInfo> visibleLocations, List<EAction> possibleActions) {
        //update the info about our surroundings
        friendlyAnts.put(thisAnt.antID(), thisAnt);
        map.update(thisLocation);
        for (ILocationInfo visibleLocation : visibleLocations) {
            map.update(visibleLocation);
        }
        
        //collect SARS data
        mdpUtils.collectSARSData(friendlyAnts.values(), map);

        //proceed with random action picking
        EAction action = null;
        if (possibleActions.contains(EAction.LayEgg)) {
            if (new Random().nextBoolean()) {
                nextEggToLay = EAntType.WARRIOR;
                mdpUtils.setLastActionPerformed(LAY_WARRIOR_EGG);
            }else{
                nextEggToLay = EAntType.SCOUT;
                mdpUtils.setLastActionPerformed(LAY_SCOUT_EGG);
            }
            action = EAction.LayEgg;
        }
        action = possibleActions.get(new Random().nextInt(possibleActions.size()));
        mdpUtils.setLastActionPerformed(action.toString());
        return action;
    }

    @Override
    public void onLayEgg(IAntInfo thisAnt, List<EAntType> types, IEgg egg) {
        friendlyAnts.put(thisAnt.antID(), thisAnt);
        map.update(thisAnt.getLocation());
        egg.set(nextEggToLay, this);
    }

    @Override
    public void onAttacked(IAntInfo thisAnt, int dir, IAntInfo attacker, int damage) {
        friendlyAnts.put(thisAnt.antID(), thisAnt);
        map.update(thisAnt.getLocation());
    }

    @Override
    public void onDeath(IAntInfo thisAnt) {
        friendlyAnts.remove(thisAnt.antID());
//        if(thisAnt.getAntType() == EAntType.QUEEN){
//            mdpUtils.printShit();
//        }
    }

}
