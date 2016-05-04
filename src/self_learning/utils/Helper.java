/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package self_learning.utils;

/**
 *
 * @author Bancho
 */
public class Helper {
    
    public static int[] findCoordsInFrontOf(int x, int y, int dir){
        int[] coords = new int[2]; //coords[0] = x; coords[1] = y
        switch(dir){
            case 0:
                coords[0] = x;
                coords[1] = y + 1;
                break;
            case 1:
                coords[0] = x + 1;
                coords[1] = y;
                break;
            case 2:
                coords[0] = x;
                coords[1] = y - 1;
                break;
            case 3:
                coords[0] = x - 1;
                coords[1] = y;
                break;
        }
        return coords;
    }
    
    public static String toStringCoords(int x,  int y){
        return "(" + x + ":" + y + ")";
    }
    
}
