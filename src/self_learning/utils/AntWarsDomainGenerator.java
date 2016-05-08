/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package self_learning.utils;

import aiantwars.EAction;
import aiantwars.EAntType;
import burlap.oomdp.auxiliary.DomainGenerator;
import burlap.oomdp.core.Attribute;
import burlap.oomdp.core.Domain;
import burlap.oomdp.core.ObjectClass;
import burlap.oomdp.singleagent.SADomain;
import self_learning.actions.Attack;
import self_learning.actions.DigOut;
import self_learning.actions.DropFood;
import self_learning.actions.DropSoil;
import self_learning.actions.EatFood;
import self_learning.actions.LayCarrierEgg;
import self_learning.actions.LayScoutEgg;
import self_learning.actions.LayWarriorEgg;
import self_learning.actions.MoveBackward;
import self_learning.actions.MoveForward;
import self_learning.actions.Pass;
import self_learning.actions.PickUpFood;
import self_learning.actions.TurnLeft;
import self_learning.actions.TurnRight;

/**
 *
 * @author Bancho
 */
public class AntWarsDomainGenerator implements DomainGenerator {
    
    private final int worldSizeX;
    private final int worldSizeY;
    
    public static final String TEAM_MEMBER_ANT = "teamMemberAnt";
    
    public static final String ATT_ANT_ID = "antID";
    public static final String ATT_HEALTH = "health";
    public static final String ATT_FOODLOAD = "foodload";
    public static final String ATT_TYPE = "type";
    public static final String ATT_CARRYING_SOIL = "carryingSoil";
    public static final String ATT_STANDING_ON_FOOD = "standingOnFood";
    public static final String ATT_FACING_ENEMY = "facingEnemy";
    public static final String ATT_FACING_FOOD = "facingFood";
    
//    public static final String TURN_LEFT = "turnLeft";
//    public static final String TURN_RIGHT = "turnRight";
//    public static final String MOVE_FORWARD = "moveForward";
//    public static final String MOVE_BACKWARD = "moveBackward";
//    public static final String ATTACK = "attack";
//    public static final String PICK_UP_FOOD = "pickUpFood";
//    public static final String DROP_FOOD = "dropFood";
//    public static final String EAT_FOOD = "eatFood";
//    public static final String DIG_OUT = "digOut";
//    public static final String DROP_SOIL = "dropSoil";
    public static final String LAY_WARRIOR_EGG = "layWarriorEgg";
    public static final String LAY_CARRIER_EGG = "layCarrierEgg";
    public static final String LAY_SCOUT_EGG = "layScoutEgg";
//    public static final String PASS = "pass";
    
    public AntWarsDomainGenerator(int worldSizeX, int worldSizeY){
        this.worldSizeX = worldSizeX;
        this.worldSizeY = worldSizeY;
    }

    @Override
    public Domain generateDomain() {
        Domain antWarsDomain = new SADomain();
        
//        Attribute antID = new Attribute(antWarsDomain, ATT_ANT_ID, Attribute.AttributeType.INT);
        Attribute health = new Attribute(antWarsDomain, ATT_HEALTH, Attribute.AttributeType.INT);
        health.setLims(0, 100);
        Attribute foodload = new Attribute(antWarsDomain, ATT_FOODLOAD, Attribute.AttributeType.INT);
        foodload.setLims(0, 30);
        Attribute type = new Attribute(antWarsDomain, ATT_TYPE, Attribute.AttributeType.INT);
        type.setLims(0, 3);
        //type.setDiscValues(new String[]{EAntType.QUEEN.getTypeName(), EAntType.CARRIER.getTypeName(), EAntType.SCOUT.getTypeName(), EAntType.WARRIOR.getTypeName()});
//        Attribute carryingSoil = new Attribute(antWarsDomain, ATT_CARRYING_SOIL, Attribute.AttributeType.INT);
//        carryingSoil.setLims(0, 1);
//        Attribute standingOnFood = new Attribute(antWarsDomain, ATT_STANDING_ON_FOOD, Attribute.AttributeType.INT);
//        standingOnFood.setLims(0, 1);
//        Attribute facingEnemy = new Attribute(antWarsDomain, ATT_FACING_ENEMY, Attribute.AttributeType.INT);
//        facingEnemy.setLims(0, 1);
//        Attribute facingFood = new Attribute(antWarsDomain, ATT_FACING_FOOD, Attribute.AttributeType.INT);
//        facingFood.setLims(0, 1);
        
        ObjectClass teamMemberAnt = new ObjectClass(antWarsDomain, TEAM_MEMBER_ANT);
//        teamMemberAnt.addAttribute(antID);
        teamMemberAnt.addAttribute(health);
        teamMemberAnt.addAttribute(foodload);
        teamMemberAnt.addAttribute(type);
//        teamMemberAnt.addAttribute(carryingSoil);
//        teamMemberAnt.addAttribute(standingOnFood);
//        teamMemberAnt.addAttribute(facingEnemy);
//        teamMemberAnt.addAttribute(facingFood);
        
//        new TurnLeft(EAction.TurnLeft.toString(), antWarsDomain);
//        new TurnRight(EAction.TurnRight.toString(), antWarsDomain);
//        new MoveForward(EAction.MoveForward.toString(), antWarsDomain);
//        new MoveBackward(EAction.MoveBackward.toString(), antWarsDomain);
//        new Attack(EAction.Attack.toString(), antWarsDomain);
//        new PickUpFood(EAction.PickUpFood.toString(), antWarsDomain);
//        new DropFood(EAction.DropFood.toString(), antWarsDomain);
//        new EatFood(EAction.EatFood.toString(), antWarsDomain);
//        new DigOut(EAction.DigOut.toString(), antWarsDomain);
//        new DropSoil(EAction.DropSoil.toString(), antWarsDomain);
//        new LayCarrierEgg(LAY_CARRIER_EGG, antWarsDomain);
//        new LayWarriorEgg(LAY_WARRIOR_EGG, antWarsDomain);
//        new LayScoutEgg(LAY_SCOUT_EGG, antWarsDomain);
//        new Pass(EAction.Pass.toString(), antWarsDomain);
        
        
        new AntWarsAction(EAction.TurnLeft.toString(), antWarsDomain);
        new AntWarsAction(EAction.TurnRight.toString(), antWarsDomain);
        new AntWarsAction(EAction.MoveForward.toString(), antWarsDomain);
        new AntWarsAction(EAction.MoveBackward.toString(), antWarsDomain);
        new AntWarsAction(EAction.Attack.toString(), antWarsDomain);
        new AntWarsAction(EAction.PickUpFood.toString(), antWarsDomain);
        new AntWarsAction(EAction.DropFood.toString(), antWarsDomain);
        new AntWarsAction(EAction.EatFood.toString(), antWarsDomain);
        new AntWarsAction(EAction.DigOut.toString(), antWarsDomain);
        new AntWarsAction(EAction.DropSoil.toString(), antWarsDomain);
        new AntWarsAction(LAY_CARRIER_EGG, antWarsDomain);
        new AntWarsAction(LAY_WARRIOR_EGG, antWarsDomain);
        new AntWarsAction(LAY_SCOUT_EGG, antWarsDomain);
        new AntWarsAction(EAction.Pass.toString(), antWarsDomain);

        return antWarsDomain;
    }
    
}
