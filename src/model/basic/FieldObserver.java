package model.basic;

import model.abstractObjects.DynamicGameObject;
import java.util.ArrayList;
import java.util.List;
import model.terrain.Map;

/**
 * Central model observer
 * @author kovko
 */
public final class FieldObserver {

    private static final List<DynamicGameObject> DynamicObjects = new ArrayList<>();
    private static Map map;
    
    /**
     * Utility class is not ment to be instanced!!!
     */
    private FieldObserver() throws Exception{
        throw new Exception("Utility class is not ment to be instanced!!!");
    }
    
    /**
     * Setter for {@link FieldObserver#map}
     * @param map instance of map
     */
    static void setMap(Map map){
        FieldObserver.map=map;
        System.out.println("Observer has received :"+map);
    }
    
    /**
     * Getter for {@link FieldObserver#map}
     * @return {@link FieldObserver#map}
     */
    static Map getMap(){
        return map;
    }
    
    /**
     * Getter for integer representation of map
     * @return int[][] mapArray
     */
    public static int[][] getMapArray(){
        return map.getMap();
    }
    
    /**
     * Adds new observable to {@link FieldObserver#DynamicObjects}
     * @param newDGO object to be added
     */
    public static void addDynamicGameObject(DynamicGameObject newDGO){
        DynamicObjects.add(newDGO);
        System.out.println("Observer is aware of :"+newDGO);
    }
    
    /**
     * Removes destroyed observable from {@link FieldObserver#DynamicObjects}
     * @param toDestroy  object to be removed
     */
    public static void removeDynamicGameObject(DynamicGameObject toDestroy){
        System.out.println("Observer lost track of: "+toDestroy);
        DynamicObjects.remove(toDestroy);
    }
    
    /**
     * request to clear all objects from map
     */
    static void clearMap(){
        map.clear();
    }
    
    /**
     * event handler for new tick
     * every observed DynamicGameObject has method logic() called
     */
    static void moveLoop(){
        try{DynamicObjects.stream().forEach(dgo -> dgo.logic());}
        catch(Exception e){}
    }
    
    /**
     * checks if object hit the wall
     * @param x
     * @param y
     * @param size
     * @return true on hit
     */
    public static boolean isWall(int x, int y, int size){
        for(int i=0; i<=size; i++){
            for (int j=0; j<=size; j++){
                if(getMapArray()[y+i][x+j]==1){
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * checks if object hit anything
     * @param x
     * @param y
     * @return 
     */
    public static boolean isSomething(int x, int y){
        return(getMapArray()[y][x]>0);
    }
    
    /**
     * checks if object hit the wall
     * @param x
     * @param y
     * @return true on hit
     */
    public static boolean isWall(int x, int y){
        return isWall(x, y, 0);
    }
    
    /**
     * Checks if requested coordinates are clear to go
     * @param coords
     * @return true if collision occured false otherwise
     */
    public static boolean positionClear(int[] coords){
        return DynamicObjects.stream().anyMatch((dynamicObject) -> (dynamicObject.isCollision(coords[0], coords[1])));
    }
    
    /**
     * Scans map for in line of sight objects
     * @param requestCoords
     * @param ID
     * @return int[] coordinates of first spotted object or null if nothing is in LOS
     */
    public static int[] inLOS(int[] requestCoords, int ID){
        int mapArray[][] = getMapArray();
        //kontrola nahor
        for(int y=-1; mapArray[requestCoords[1]+y][requestCoords[0]]!=1; y--){
            if(mapArray[requestCoords[1]+y][requestCoords[0]]>1 && mapArray[requestCoords[1]+y][requestCoords[0]]!=ID && mapArray[requestCoords[1]+y][requestCoords[0]]!='C'-'0'){
                requestCoords[1]+=y;
                return requestCoords;
            }
        }
        //kontrola nadol
        for(int y=2; mapArray[requestCoords[1]+y][requestCoords[0]]!=1; y++){
            if(mapArray[requestCoords[1]+y][requestCoords[0]]>1 && mapArray[requestCoords[1]+y][requestCoords[0]]!=ID && mapArray[requestCoords[1]+y][requestCoords[0]]!='C'-'0'){
                requestCoords[1]+=y;
                return requestCoords;
            }
        }
        //kontrola vlavo
        for(int x=-1; mapArray[requestCoords[1]][requestCoords[0]+x]!=1; x--){
            if(mapArray[requestCoords[1]][requestCoords[0]+x]>1 && mapArray[requestCoords[1]][requestCoords[0]+x]!=ID && mapArray[requestCoords[1]][requestCoords[0]+x]!='C'-'0'){
                requestCoords[0]+=x;
                return requestCoords;
            }
        }
        //kontrola vpravo
        for(int x=2; mapArray[requestCoords[1]][requestCoords[0]+x]!=1; x++){
            if(mapArray[requestCoords[1]][requestCoords[0]+x]>1 && mapArray[requestCoords[1]][requestCoords[0]+x]!=ID && mapArray[requestCoords[1]][requestCoords[0]+x]!='C'-'0'){
                requestCoords[0]+=x;
                return requestCoords;
            }
        }
        return null;
    }
    
    /**
     * event handler of projectile explosion
     * every observed DynamicGameObject has method onProjectileHit() called
     * @param projectilePosition 
     */
    public static void onProjectileExplosion(int projectilePosition[]){
        try{DynamicObjects.stream().forEach(dgo -> dgo.onProjectileHit(projectilePosition));}
        catch(Exception e){}
    }
    
    /**
     * Simple getter for {@link FieldObserver#DynamicObjects}
     * @return 
     */
    public static List<DynamicGameObject> getDynamicObjectsArrayList(){
        return DynamicObjects;
    }
    
    /**
     * animate every DynamicGameObject on map
     */
    static void animateMap(){
        DynamicObjects.stream().forEach((dynamicObject) -> map.animateObject(dynamicObject.getX(), dynamicObject.getY(), dynamicObject.getSize(), dynamicObject.getPlaceHolder()));
    }
}

