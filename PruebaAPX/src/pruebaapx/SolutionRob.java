package pruebaapx;

public class SolutionRob {
    public static void main (String [] args){
        int x = 2;
        int y = 1;
        int dx = 1;
        int dy = 1;
       
        Robot fristRobot = new Robot();
        fristRobot.printCurrentCoordinates();
       
        Robot secondRobot = new Robot(x, y);
        secondRobot.printCurrentCoordinates();
       
        for(int i=1; i <3; i++){
            secondRobot.moveX(dx);
            secondRobot.printLastMove();
            secondRobot.printCurrentCoordinates();
            secondRobot.moveY(dy);
            secondRobot.printLastCoordinates();
            dx += i * i;
            dy -= i * i;
        }
    }
}
class Robot{
    private int currentX;
    private int currentY;
    private int previousX;
    private int previousY;
    private boolean lastMovenX;
    private boolean hasMoved;
    public Robot(){
        this.currentX = 0;
        this.currentY = 5;
        this.previousX = 0;
        this.previousY = 5;
        this.hasMoved = false;
    }
    public Robot(int x, int y){
        this.previousX = 0;
        this.previousY = 5;
        this.currentX = x;
        this.currentY = y;
        this.hasMoved = true;
    }
    public void moveX(int dx){
        this.previousX = this.currentX;
        this.currentX += dx;
        this.lastMovenX = true;
        this.hasMoved = true;
   }
   public void moveY(int dy){
        this.previousY = this.currentY;
        this.currentY += dy;
        this.lastMovenX = false;
        this.hasMoved = true;
    }
   public void printCurrentCoordinates(){
        System.out.println(this.currentX + " " + this.currentY);
    }
    public void printLastCoordinates(){
        if(this.hasMoved){
            if(this.lastMovenX){
                System.out.println(this.previousX + " " + this.currentY);
            }
            else{
                System.out.println(this.currentX + " " + this.previousY);
            }
        }
    }
    public void printLastMove(){
        if(this.lastMovenX){
            System.out.println("x "+ (this.currentX - this.previousX));
        }
        else{
            System.out.println("y "+ (this.currentY - this.previousY));
        }
    }
}