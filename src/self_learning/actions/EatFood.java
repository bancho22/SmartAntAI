/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package self_learning.actions;

import burlap.oomdp.core.Domain;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.GroundedAction;
import burlap.oomdp.singleagent.common.SimpleAction;

/**
 *
 * @author Bancho
 */
public class EatFood extends SimpleAction {
    
    public EatFood(String name, Domain domain){
        super(name, domain);
    }
    
    @Override
    protected State performActionHelper(State state, GroundedAction action) {
        //turns left
        return state;
    }
    
}
