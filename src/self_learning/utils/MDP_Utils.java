/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package self_learning.utils;

import aiantwars.EAntType;
import aiantwars.IAntInfo;
import burlap.behavior.policy.Policy;
import burlap.behavior.singleagent.learning.lspi.LSPI;
import burlap.behavior.singleagent.learning.lspi.SARSData;
import burlap.behavior.singleagent.learning.lspi.SARSData.SARS;
import burlap.behavior.singleagent.vfa.common.ConcatenatedObjectFeatureVectorGenerator;
import burlap.behavior.singleagent.vfa.fourier.FourierBasis;
import burlap.oomdp.core.Domain;
import burlap.oomdp.core.objects.MutableObjectInstance;
import burlap.oomdp.core.objects.ObjectInstance;
import burlap.oomdp.core.states.MutableState;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.GroundedAction;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Scanner;
import static self_learning.utils.AntWarsDomainGenerator.TEAM_MEMBER_ANT;
import static self_learning.utils.AntWarsDomainGenerator.ATT_ANT_ID;
import static self_learning.utils.AntWarsDomainGenerator.ATT_HEALTH;
import static self_learning.utils.AntWarsDomainGenerator.ATT_FOODLOAD;
import static self_learning.utils.AntWarsDomainGenerator.ATT_TYPE;
import static self_learning.utils.AntWarsDomainGenerator.ATT_CARRYING_SOIL;
import static self_learning.utils.AntWarsDomainGenerator.ATT_STANDING_ON_FOOD;
import static self_learning.utils.AntWarsDomainGenerator.ATT_FACING_FOOD;
import static self_learning.utils.AntWarsDomainGenerator.ATT_FACING_ENEMY;
import self_learning.map.Map;
import self_learning.map.TileInfo;

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

    public MDP_Utils() {
        rewardFn = new RewardFn();
        dataset = new SARSData();
    }

    public void initAntWarsDomain(int worldSizeX, int worldSizeY) {
        antWarsDomain = new AntWarsDomainGenerator(worldSizeX, worldSizeY).generateDomain();
    }

    private State constructState(Collection<IAntInfo> friendlyAnts, Map map) {
        State s = new MutableState();
        int counter = 0;
        for (IAntInfo friendlyAnt : friendlyAnts) {
            int x = friendlyAnt.getLocation().getX();
            int y = friendlyAnt.getLocation().getY();
            TileInfo tileOfAnt = map.getTileInfo(x, y);
            int[] coordsOfTileInFrontOfAnt = Helper.findCoordsInFrontOf(x, y, friendlyAnt.getDirection());
            TileInfo tileInFrontOfAnt = map.getTileInfo(coordsOfTileInFrontOfAnt[0], coordsOfTileInFrontOfAnt[1]);
            ObjectInstance oi = new MutableObjectInstance(antWarsDomain.getObjectClass(TEAM_MEMBER_ANT), TEAM_MEMBER_ANT + "_" + counter);
//            oi.setValue(ATT_ANT_ID, friendlyAnt.antID());
            oi.setValue(ATT_HEALTH, (int) friendlyAnt.getHealth() * 100);
            oi.setValue(ATT_FOODLOAD, friendlyAnt.getFoodLoad());
            switch(friendlyAnt.getAntType().getTypeName()){
                case "Queen":
                    oi.setValue(ATT_TYPE, 0);
                    break;
                case "Carrier":
                    oi.setValue(ATT_TYPE, 1);
                    break;
                case "Scout":
                    oi.setValue(ATT_TYPE, 2);
                    break;
                case "Warrier":
                    oi.setValue(ATT_TYPE, 3);
                    break;
            }
            //oi.setValue(ATT_TYPE, friendlyAnt.getAntType().getTypeName());
//            oi.setValue(ATT_CARRYING_SOIL, friendlyAnt.carriesSoil());
//            oi.setValue(ATT_STANDING_ON_FOOD, tileOfAnt.hasFood());
//            if (tileInFrontOfAnt != null) {
////                if (tileInFrontOfAnt.hasFood()) {
////                    oi.setValue(ATT_FACING_FOOD, 1);
////                } else {
////                    oi.setValue(ATT_FACING_FOOD, 0);
////                }
//                if (tileInFrontOfAnt.hasEnemyAnt()) {
//                    oi.setValue(ATT_FACING_ENEMY, 1);
//                } else {
//                    oi.setValue(ATT_FACING_ENEMY, 0);
//                }
//            } else {
////                oi.setValue(ATT_FACING_FOOD, 0);
//                oi.setValue(ATT_FACING_ENEMY, 0);
//            }
            s.addObject(oi);
            counter++;
        }
        return s;
    }

    public void collectSARSData(Collection<IAntInfo> friendlyAnts, Map map) {
        currentState = constructState(friendlyAnts, map);
        if (lastActionPerformed != null && lastRecordedState != null) {
            double r = rewardFn.reward(lastRecordedState, lastActionPerformed, currentState);
            dataset.add(lastRecordedState, lastActionPerformed, r, currentState);
        }
        lastRecordedState = currentState;
    }

    //this one is only used at the en of rounds
    public void collectSARSData(Collection<IAntInfo> friendlyAnts, Map map, int roundResult) {
        currentState = constructState(friendlyAnts, map);
        if (lastActionPerformed != null && lastRecordedState != null) {
            //BYPASSING REWAD FUNCTION, TODO: FIX THAT
            //double r = rewardFn.reward(lastRecordedState, lastActionPerformed, currentState);
            double r;
            switch (roundResult) {
                case 1:
                    r = 10;
                    break;
                case -1:
                    r = -10;
                    break;
                case 0:
                    r = -1;
                    break;
                default:
                    r = 0;
                    break;
            }
            dataset.add(lastRecordedState, lastActionPerformed, r, currentState);
        }
        lastRecordedState = null;
        lastActionPerformed = null;
    }

    public GroundedAction getLastActionPerformed() {
        return lastActionPerformed;
    }

    public void setLastActionPerformed(String lastActionPerformed) {
        this.lastActionPerformed = antWarsDomain.getAction(lastActionPerformed).getAssociatedGroundedAction();
    }

    public void doMagic() {
        System.out.println("Creating new Concatenated Object Feature Vector Generator...");
        ConcatenatedObjectFeatureVectorGenerator featureVectorGenerator = new ConcatenatedObjectFeatureVectorGenerator(true, TEAM_MEMBER_ANT);
        System.out.println("Initializing Fourier Basis function...");
        FourierBasis fb = new FourierBasis(featureVectorGenerator, 4);

        System.out.println("Setting up Least Squares Policy Iteration...");
        LSPI lspi = new LSPI(antWarsDomain, 0.99, fb, dataset);
        System.out.println("Launching policy iteration...");
        Policy p = lspi.runPolicyIteration(30, 1e-6);
        Scanner sc = new Scanner(System.in);
        System.out.println("Press Enter to exit...");
        sc.nextLine();
//        try {
//            FileOutputStream fileOut
//                    = new FileOutputStream("/tmp/policy.ser");
//            ObjectOutputStream out = new ObjectOutputStream(fileOut);
//            out.writeObject(p);
//            out.close();
//            fileOut.close();
//            System.out.printf("Serialized data is saved in /tmp/employee.ser");
//        } catch (IOException i) {
//            i.printStackTrace();
//        }
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
