
package model.terrain;

import java.util.Random;

/**
 *
 * @author kovko
 */
public class Map {
    private int map[][];
    private final int size = 40;
    
    public Map(){
        this.generateMap();  
    }
    
    /**
     * sequence of method calls, that by the end of the day generates 2D map
     */
    private void generateMap(){
        this.prepareMap();
        //this.print("This is just the begining!");
        this.finalizeMap();
        //this.print();
    }
    
    /**
     * sets reference int map[][] and fills with control signs
     * 0 - empty
     * 1 - wall
     * 2 - wall precursor
     * 3 - team colonel 1 spawn points
     * 4 - team colonel 2 spawn points
     */
    private void prepareMap(){
        this.map = new int[this.size][this.size];
        for (int i=0; i<this.size; i++){
            for(int j=0; j<this.size; j++){
                //stena okolo mapky
                if(i==0 || i==(this.size-1) || j==0 || j==(this.size-1)){
                    this.map[i][j]=1;
                }
                //prekurzory stien
                else if((i%6==0)&&(j%6==0)){
                    this.map[i][j]=2;
                }
                //spawn pointy plukovnika 1
                else if((j==1)&&(i%3==1)){
                    this.map[i][j]=3;
                }
                //spawn pointy plukovnika 2
                else if((j==this.size-3)&&(i%3==1)){
                    this.map[i][j]=4;
                }
                //spawn ciela
                else if(((i==this.size-3)||(i==this.size-2))&&((j==this.size/2-1)||(j==this.size/2))){
                    this.map[i][j]='C'-'0';
                }
                //spawn prazdneho priestoru
                else
                    this.map[i][j]=0;
            }
        }
    }
    
    /**
     * procedurally generates walls in map precursor
     */
    private void finalizeMap(){
        int poi = 2;
        int pocet = this.countPOI(poi);
        Random randomGenerator = new Random();
        //pokial mam este spawn pointy
        while(pocet>0){
            //nahodne urcim od ktoreho zacnem;
            int ktore = randomGenerator.nextInt(pocet)+1;
            //zistim si suradnice toho nahodneho
            int suradnice[] = this.findCoords(poi, ktore);
            //urcim si smer, ktorym zacnem betonovat
            int smer = randomGenerator.nextInt(4);
            //nastavim vektory smeru
            int dx, dy;
            dy=dx=0;
            switch (smer){
                //nahor
                case 0: dy=-1; break;
                //vpravo
                case 1: dx=1; break;
                //nadol
                case 2: dy=1; break;
                //vlavo
                case 3: dx=-1; break;
            }
            //vybetonujem danym smerom z danej suradnice
            this.makeWall(dx, dy, suradnice);
            pocet = this.countPOI(poi);
        }
    }
    
    /**
     * @param int poi in map to count
     * @return int count of poi in map
     */
    private int countPOI(int poi){
        int count=0;
        for(int i=0; i<this.size; i++){
            for(int j=0; j<this.size; j++){
                if (this.map[i][j]==poi){
                    count++;
                }
            }
        }
        return count;
    }
    
    /**
     * Finds coords of value in field at certain count
     * @param value
     * @param count
     * @return coords
     */
    private int[] findCoords(int value, int count){
        int coords[] = {0,0};
        int pocitadlo=0;
        for(int y=0; y<this.size; y++){
            for(int x=0; x<this.size; x++){
                if(this.map[y][x]==value){
                    pocitadlo++;
                }
                if(pocitadlo==count){
                    coords[0]=x;
                    coords[1]=y;
                    return coords;
                }
            }
        }
        return coords;
    }
    
    /**
     * creates wall from coords in given direction, until reaches another wall
     * @param dx
     * @param dy
     * @param coords 
     */
    private void makeWall(int dx, int dy, int coords[]){
        int x,y;
        x = coords[0];
        y = coords[1];
        while(this.map[y][x]!=1){
            this.map[y][x]=1;
            y+=dy;
            x+=dx;
        }
    }
    
    /**
     * prints map interpretation to console
     */
    public void print(){
        for(int i=0; i<this.size; i++){
            for(int j=0; j<this.size; j++){
                System.out.printf("%c ", this.map[i][j]+'0');
            }
            System.out.printf("\n");
        }
        System.out.printf("\n\n");
    }
    
    /**
     * prints map interpretation to console
     * @param write String to print before map
     */
    public void print(String write){
        System.out.println(this + " says: " +write);
        this.print();
    }
    
    /**
     * Returns 2D terrain interpretation held by instance of map
     * @return int 2D field with map
     */
    public int[][] getMap() {
        return map;
    }
    
    /**
     * 
     * @param ID
     * @return Coords of spawnpoint for ID
     */
    public int[] requestSpawnPoint(int ID){
        int count = this.countPOI(ID);
        int rand = new Random().nextInt(count)+1;
        int coords[]=this.findCoords(ID, rand);
        this.map[coords[1]][coords[0]]=0;
        return coords;
    }
    
    /**
     * clears all objects from map
     * text interface function - will be deleted
     */
    public void clear(){
        for(int y=0; y<this.size; y++){
            for(int x=0; x<this.size; x++){
                if (this.map[y][x]!=0 && this.map[y][x]!=1 && this.map[y][x]!='C'-'0'){
                    this.map[y][x]=0;
                }
            }
        }
    }
    
    /**
     * draws object representation to map
     * @param x
     * @param y
     * @param size 
     * @param placeHolder 
     */
    public void animateObject(int x, int y, int size, char placeHolder){
        for(int ysize=0; ysize<size+1; ysize++){
            for(int xsize=0; xsize<size+1; xsize++){
                this.map[y+ysize][x+xsize]=placeHolder-'0';
            }
        }
    }
    
    /**
     * draws object representation to map
     * @param x
     * @param y
     * @param placeHolder 
     */
    public void animateObject(int x, int y, char placeHolder){
        this.animateObject(x, y, 1, placeHolder);
    }
    
    /**
     * getter for exit coordinates
     * @return coords[]
     */
    public int[] getExit(){
        int coords[]={this.size/2-1, this.size-3};
        return coords;
    }
}
