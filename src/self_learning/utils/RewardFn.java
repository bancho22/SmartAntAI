/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package self_learning.utils;

import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.GroundedAction;
import burlap.oomdp.singleagent.RewardFunction;

/**
 *
 * @author Bancho
 */
public class RewardFn implements RewardFunction {

    @Override
    public double reward(State s, GroundedAction a, State sprime) {
        //if game won, return +10
        //if game lost, return -10
        //if enemy ant killed, return +1
        //if friendly ant hatched, return +1
        //if friendly ant died, return -1
        //otherwise, return -0.1
        
        return 0; //dummy
    }
    
}
