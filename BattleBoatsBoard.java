import java.util.Scanner;

public class BattleBoatsBoard{
    private int[][] battleBoard;//debug
    private int[][] playerBoard;//player's board
    private int[] shotCoordinates = new int[2];// keeps track of shot coordinates with indexing first index is Y and second index is X used with hitOrMiss()
    private int[] sunkBoats;//unique boat hit counter
    private int rowsY;//rows
    private int columnsX;//columns
    private int boatQuantity;//How many boats are left (decrement each time)
    private int boatNumbers;//Each boat has a unique number
    private int totalTurns;//Turn counter
    private int totalShots;//Shot counter


    public void printBoard(){
        for (int i = 0; i < rowsY; i++){
            System.out.println();
            for (int j = 0; j < columnsX; j++){
                System.out.print(battleBoard[i][j]);//Prints board with ship locations
            }
        }
    }//debug

    public void printGameBoard(){
        for (int i = 0; i < rowsY; i++){
            System.out.println();
            for (int j = 0; j < columnsX; j++){
                System.out.print(playerBoard[i][j]);//Board filled with zeros
            }
        }
        System.out.println();
    }//player

    public BattleBoatsBoard(int m, int n){
        if (m >= 3 && n >= 3 && m <= 12 && n <= 12){
            battleBoard = new int[m][n];
            playerBoard = new int[m][n];
            this.rowsY = m;
            this.columnsX = n;
        }
        else{
            throw new ArithmeticException();
        }
    }//constructor

    public int getBoatQuantity(){
        int minimumBoats = Math.min(rowsY,columnsX);

        if (rowsY != columnsX){
            if (minimumBoats == 3){
                boatQuantity = 1;
                boatNumbers = 1;
                return 1;
            }
            else if(3 < minimumBoats && minimumBoats <= 5 ){
                boatQuantity = 2;
                boatNumbers = 2;
                return 2;
            }
            else if(5 < minimumBoats && minimumBoats <= 7 ){
                boatQuantity = 3;
                boatNumbers = 3;
                return 3;
            }
            else if(7 < minimumBoats && minimumBoats <= 9 ){
                boatQuantity = 4;
                boatNumbers = 4;
                return 4;
            }
            else if(9 < minimumBoats && minimumBoats <= 12 ){
                boatQuantity = 6;
                boatNumbers = 6;
                return 6;
            }
        }
        else{
            if ( rowsY== 3 && columnsX == 3){
                boatQuantity = 1;
                boatNumbers = 1;
                return 1;
            }
            else if (3 < columnsX && columnsX <= 5 || 3 < rowsY && rowsY <= 5){
                boatQuantity = 2;
                boatNumbers = 2;
                return 2;
            }
            else if (5 < columnsX && columnsX <= 7 || 5 < rowsY && rowsY <= 7){
                boatQuantity = 3;
                boatNumbers = 3;
                return 3;
            }
            else if (7 < columnsX && columnsX <= 9 || 7 < rowsY && rowsY <= 9){
                boatQuantity = 4;
                boatNumbers = 4;
                return 4;
            }
            else if (9 < columnsX && columnsX <= 12 || 9 < rowsY && rowsY <= 12){
                boatQuantity = 6;
                boatNumbers = 6;
                return 6;
            }
        }
        return 0;
    }//How many boats to place depending on size of board

    public int getRandomNumY(int number){
        double x = Math.random();
        boolean inRange = false;
        int returnNum = 0;
        while(!inRange){
            int randomNum = (int) Math.floor(x * rowsY);
            if (randomNum+2 >= (rowsY)){
                x = Math.random();
                inRange = false;
            }
            else{
                returnNum = randomNum;
                inRange = true;
            }
        }
        return returnNum;
    }//random row in range, if boat is vertical setupBoard() will use this

    public int getY(int number){
        double x = Math.random();
        boolean good = false;
        int randomY = 0;
        while(!good){
            int rNumber = (int) Math.floor(x*rowsY);
            if (rNumber > rowsY){
                x = Math.random();
                good = false;
            }
            else{
                randomY = rNumber;
                good = true;
            }
        }
        return randomY;
    }//any column for vertical orientation, if boat is horizontal setupBoard() will use this

    public int getRandomNumX(int number){
        double x = Math.random();
        boolean inRange = false;
        int returnNum = 0;
        while(!inRange){
            int randomNum = (int) Math.floor(x * columnsX);
            if (randomNum+2 >= (columnsX)){
                x = Math.random();
                inRange = false;
            }
            else{
                returnNum = randomNum;
                inRange = true;
            }
        }
        return returnNum;
    }// random column in range if boat is horizontal setupBoard() will use this

    public int getX(int number){
        double x = Math.random();
        boolean good = false;
        int randomX = 0;
        while(!good){
            int rNumber = (int) Math.floor(x*columnsX);
            if (rNumber > columnsX){
                x = Math.random();
                good = false;
            }
            else{
                randomX = rNumber;
                good = true;
            }
        }
        return randomX;
    }//any row for horizontal orientation, if boat is vertical setupBoard() will use this

    public void setupBoard(){
        int numBoats = getBoatQuantity();//How many boats to place
        sunkBoats = new int[getBoatQuantity()+1];
        while (numBoats > 0){
            boolean success = false;//checks if boat placement is in range

            while (!success){
                int randomY = getRandomNumY(rowsY);//Random coordinates
                int anyX = getX(columnsX);//Random coordinates
                int randomX = getRandomNumX(columnsX);//Random coordinates
                int anyY = getY(rowsY);//Random coordinates
                double orientation = Math.random();

                //Vertical
                if (orientation > 0.5
                        && battleBoard[randomY][anyX] == 0
                        && battleBoard[randomY+1][anyX] == 0
                        && battleBoard[randomY+2][anyX] == 0 ){
                    battleBoard[randomY][anyX] = numBoats;//each boat gets a unique count which goes into an array
                    battleBoard[randomY+1][anyX] = numBoats;
                    battleBoard[randomY+2][anyX] = numBoats;
                    success = true;
                }
                //Horizontal
                else if (orientation < 0.5
                        && battleBoard[anyY][randomX] == 0
                        && battleBoard[anyY][randomX+1] == 0
                        && battleBoard[anyY][randomX+2] == 0){
                    battleBoard[anyY][randomX] = numBoats;
                    battleBoard[anyY][randomX+1] = numBoats;
                    battleBoard[anyY][randomX+2] = numBoats;
                    success = true;
                }
            }
            numBoats--;
        }
    }//Places ships

    public void shot() {
        Scanner scan = new Scanner(System.in);

            try {
                System.out.print("Would you like to use a drone? (Y/N): ");
                String useDrone = scan.next();
                System.out.print("Please enter a row: ");
                int y = scan.nextInt();
                shotCoordinates[0] = y;
                System.out.print("Please enter a column: ");
                int x = scan.nextInt();
                shotCoordinates[1] = x;

                if (useDrone.equals("Y") || useDrone.equals("y")) {
                    if(y < 0 || y > rowsY-1 || x < 0 || x > columnsX-1){
                        totalTurns+=4;
                        totalShots++;
                        System.out.println();
                        System.out.println("Drone is out of range");//FIX
                    }
                    else if (playerBoard[y][x] == 7 || playerBoard[y][x] == 8){
                        drone();
                        totalTurns+=4;
                        System.out.println();
                        System.out.println("Coordinate has already been shot");
                    }
                    else{
                        drone();
                        totalTurns += 4;
                    }
                }
                else {
                    if (y < 0 || y > rowsY-1 || x < 0 || x > columnsX-1) {
                        totalShots++;
                        totalTurns +=2;
                        System.out.println();
                        System.out.println("Penalty, not within range");
                    }
                    else if(playerBoard[y][x] == 7 || playerBoard[y][x] == 8){
                        totalShots++;
                        totalTurns +=2;
                        System.out.println();
                        System.out.println("Penalty, coordinate has already been shot");
                    }
                    else {
                        totalTurns++;
                        totalShots++;
                        hitOrMiss();
                    }
                }
            }
            catch (Exception e){
                System.out.println();
                System.out.println("Please enter a valid input");

            }
    }

    public void hitOrMiss(){
        if (battleBoard[shotCoordinates[0]][shotCoordinates[1]] == 0){
            playerBoard[shotCoordinates[0]][shotCoordinates[1]] = 8;
            System.out.println();
            System.out.println("Miss");//checks coordinate if theres a ship if its a zero then it will print miss and change playerboard coordinate to 8
        }
        else{
            for(int ship = 1 ; ship <= boatNumbers; ship++){
                if(battleBoard[shotCoordinates[0]][shotCoordinates[1]] == ship){
                    playerBoard[shotCoordinates[0]][shotCoordinates[1]] = 7;// will print hit if theres a ship and change playerboard coordinate to 7
                    sunkBoats[ship]++;
                    if(sunkBoats[ship] ==  3){//if a specific ship has been hit 3 times then it will let the player know the ship has been sunk
                        System.out.println();
                        System.out.println("SUNK");
                        boatQuantity--;
                    }
                    else{
                        System.out.println();
                        System.out.println("Hit");
                    }
                }
            }
        }
    }

    public void drone(){
        if (shotCoordinates[0]-1 < 0 && shotCoordinates[1]-1 < 0){//TOP LEFT
            for (int i = shotCoordinates[0]; i <= shotCoordinates[0]+1; i++){
                for (int j = shotCoordinates[1]; j <= shotCoordinates[1]+1; j++){
                    System.out.println("("+ i + "," + j + ")" + " " +battleBoard[i][j]);
                }
            }
        }

        else if(shotCoordinates[0]+1 > rowsY-1 && shotCoordinates[1]-1 < 0){//BOTTOM LEFT
            for (int i = shotCoordinates[0]-1; i <= shotCoordinates[0]; i++){
                for (int j = shotCoordinates[1]; j <= shotCoordinates[1]+1; j++){
                    System.out.println("("+ i + "," + j + ")" + " " +battleBoard[i][j]);
                }
            }
        }

        else if(shotCoordinates[0]-1 < 0 && shotCoordinates[1]+1 > columnsX-1){//TOP RIGHT
            for(int i = shotCoordinates[0]; i <= shotCoordinates[0]+1; i++){
                for(int j = shotCoordinates[1]-1; j <= shotCoordinates[1];j++){
                    System.out.println("("+ i + "," + j + ")" + " " +battleBoard[i][j]);
                }
            }
        }

        else if(shotCoordinates[0]+1 > rowsY-1 && shotCoordinates[1]+1 > columnsX-1){//TOP RIGHT
            for(int i = shotCoordinates[0]-1; i <= shotCoordinates[0]; i++){
                for(int j = shotCoordinates[1]-1; j <= shotCoordinates[1];j++){
                    System.out.println("("+ i + "," + j + ")" + " " +battleBoard[i][j]);
                }
            }
        }

        else if(shotCoordinates[0]-1 < 0){//TOP BOUND
            for (int i = shotCoordinates[0]; i <= shotCoordinates[0]+1; i++){
                for (int j = shotCoordinates[1]-1; j <= shotCoordinates[1]+1; j++){
                    System.out.println("("+ i + "," + j + ")" + " " +battleBoard[i][j]);
                }
            }
        }

        else if(shotCoordinates[0]+1 > rowsY-1){//BOTTOM BOUND
            for (int i = shotCoordinates[0]-1; i <= shotCoordinates[0]; i++){
                for (int j = shotCoordinates[1]-1; j <= shotCoordinates[1]+1; j++){
                    System.out.println("("+ i + "," + j + ")" + " " +battleBoard[i][j]);
                }
            }
        }

        else if(shotCoordinates[1]+1 > columnsX-1){//RIGHT BOUND
            for (int i = shotCoordinates[0]-1; i <= shotCoordinates[0]+1; i++){
                for (int j = shotCoordinates[1]-1; j <= shotCoordinates[1]; j++){
                    System.out.println("("+ i + "," + j + ")" + " " +battleBoard[i][j]);
                }
            }
        }

        else if(shotCoordinates[1]-1 < 0){//LEFT BOUND
            for (int i = shotCoordinates[0]-1; i <= shotCoordinates[0]+1; i++){
                for (int j = shotCoordinates[1]; j <= shotCoordinates[1]+1; j++){
                    System.out.println("("+ i + "," + j + ")" + " " +battleBoard[i][j]);
                }
            }
        }

        else{
            for (int i = shotCoordinates[0]-1; i <= shotCoordinates[0]+1; i++){
                for (int j = shotCoordinates[1]-1; j <= shotCoordinates[1]+1; j++){
                    System.out.println("("+ i + "," + j + ")" + " " +battleBoard[i][j]);
                }
            }
        }

    }

    public boolean finished(){
        if (boatQuantity == 0){
            System.out.println("Total number of turns: "+ totalTurns);
            System.out.println("Total number of shots: "+ totalShots);
            return true;
        }
        else{
//            System.out.println("Total number of turns: "+ totalTurns);//dbug
//            System.out.println("Total number of shots: "+ totalShots);//dbug
            return false;
        }
    }//Will keep running until all ships has been sunk

    public static void main(String[] args){
        Scanner usrInput = new Scanner(System.in);
        boolean validAnswer = false;

        while(!validAnswer){
            try{
                System.out.print("Debug mode? (Y/N): ");
                String dbug = usrInput.next();
                System.out.print("How many rows (3-12): ");
                int y = usrInput.nextInt();
                System.out.print("How many columns (3-12): ");
                int x = usrInput.nextInt();

                if(y < 3 || y > 12 || x < 3 || x > 12){
                    System.out.println("Invalid input");
                    validAnswer = false;
                }

                else if(dbug.equals("Y") || dbug.equals("y")){
                    System.out.println();
                    System.out.println("A 7 means HIT and 8 means MISS");
                    System.out.println();
                    BattleBoatsBoard BBB = new BattleBoatsBoard(y,x);//Creates the debug board
                    BBB.setupBoard();//places ships
                    System.out.println("Number of boats: " + BBB.getBoatQuantity());
                    BBB.printBoard();
                    System.out.println();
                    System.out.println();
                    BBB.printGameBoard();
                    validAnswer = true;

                    do{
                        BBB.shot();
                        BBB.printGameBoard();//Creates the playerboard
                    }
                    while(!BBB.finished());//will keep running until all ships have sunk
                }
                else if (dbug.equals("N") || dbug.equals("n")){
                    System.out.println();
                    System.out.println("A 7 means it's a HIT and 8 means its a MISS");
                    System.out.println();
                    BattleBoatsBoard BBB = new BattleBoatsBoard(y,x);
                    BBB.setupBoard();
                    System.out.println("Number of boats: " + BBB.getBoatQuantity());
                    BBB.printGameBoard();
                    validAnswer = true;

                    do{
                        BBB.shot();
                        BBB.printGameBoard();
                    }
                    while(!BBB.finished());
                }
            }
            catch (Exception exception)
            {
                System.out.println("Invalid input");
                usrInput.nextLine();
                validAnswer = false;
            }
        }
    }
}
