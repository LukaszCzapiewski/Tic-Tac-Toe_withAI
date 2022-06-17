
import java.util.*;


public class Main {
    static Random rand = new Random();
    static Scanner sc = new Scanner(System.in);
    static char[] board;


    public static void main(String[] args) {
        Main main = new Main();
        main.Menu();
    }

    void Menu() {
        String command = sc.nextLine();
        switch (command) {
            case "start easy easy": {
                Game(1, 1);
                return;
            }
            case "start user easy": {
                Game(0, 1);
                return;
            }
            case "start easy user": {
                Game(1, 0);
                return;
            }
            case "start user user": {
                Game(0, 0);
                return;
            }

            case "start user medium": {
                Game(0, 2);
                return;
            }
            case "start medium user": {
                Game(2, 0);
                return;
            }
            case "start medium easy": {
                Game(2, 1);
                return;
            }
            case "start easy medium": {
                Game(1, 2);
                return;
            }
            case "start medium medium": {
                Game(2, 2);
                return;
            }

            case "start user hard": {
                Game(0, 3);
                return;
            }
            case "start hard user": {
                Game(3, 0);
                return;
            }

            case "start hard hard": {
                Game(3, 3);
                return;
            }

            case "start medium hard": {
                Game(2, 3);
                return;
            }

            case "start hard medium": {
                Game(3, 2);
                return;
            }
            case "start easy hard": {
                Game(1, 3);
                return;
            }
            case "start hard easy": {
                Game(3, 1);
                return;
            }


            case "exit": {
                System.exit(0);
                return;
            }

            default: {
                System.out.println("Bad parameters!");
                Menu();
                //user 0 easy 1
            }
        }
    }

    void Game(int player1, int player2) {
        int state = 0;
        board = "         ".toCharArray();
        char currentChar;
        printBoard();
        for (int i = 0; i < 9; i++) {
            if (i % 2 == 0) {
                currentChar='X';
                makeMove(currentChar, player1);
            }
            else {
                currentChar='O';
                makeMove(currentChar, player2);
            }
            printBoard();
            state = checkWin(currentChar);
            if (state=='X'||state=='O') break;
        }
        if (state == 'X') System.out.println("\nX wins");
        else if (state == 'O') System.out.println("\nO wins");
        else System.out.println("\nDraw");
        sc.next();
        Menu();
        sc.next();
    }

    static void printBoard() {
        System.out.println("---------");
        for (int i = 0; i < 9; i += 3) {
            System.out.printf("| %c %c %c |\n", board[i], board[i + 1], board[i + 2]);
        }
        System.out.print("---------");
    }


    static char checkWin(char c) {
        int winCount1 = 0;
        int winCount2= 0;

        for (int i = 2; i <= 6; i += 2) {
            if (board[i] == c) winCount1++;
            if (board[(i - 2) * 2] == c) winCount2++;
        }
        if (winCount1 == 3 || winCount2 == 3) return c;
        int j = 0;
        for (int i = 0; i < 9; i += 3) {
            if (board[i] == c && board[i + 1] == c && board[i + 2] == c) return c;
            if (board[j] == c && board[j + 3] == c && board[j + 6] == c) return c;
            j++;
        }
        return 0;
    }

    void makeMove(char c, int move) {
        if (move == 0) {
            getInput(c);
        } else if(move==1){
            moveEasy(c);
        }
        else if(move==2){
            moveMedium(c);
        }
        else if(move==3){
            moveHard(c);
        }
    }

    static void moveEasy(char c) {

        System.out.println("\nMaking move level \"easy\"");
        makeRandomMove(c);
    }

    static void makeRandomMove(char c){
        int x;
        int y;
        int conversion;
        while (true) {
            x = rand.nextInt(3);
            y = rand.nextInt(3);
            conversion = 3 * (x) + y;
            if (board[conversion] == ' ') {
                board[conversion] = c;
                break;
            }
        }
    }

    static void getInput(char c) {
        System.out.println("\nEnter the coordinates:");
        int conversion;
        int x;
        int y;
        try {
            x = sc.nextInt();
            y = sc.nextInt();
        } catch (InputMismatchException e){
            sc.next();
            System.out.println("You should enter numbers!");
            getInput(c);
            return;
        }
        conversion = 3 * (x - 1) + y - 1;
        if ((x <= -1 || x > 3) || (y <= -1 || y > 3)) {
            System.out.println("Coordinates should be from 1 to 3!");
            getInput(c);
            return;
        } else if (board[conversion] == 'X' || board[conversion] == 'O') {
            System.out.println("This cell is occupied! Choose another one!");
            getInput(c);
            return;
        }
        board[conversion] = c;
    }


    static void moveMedium(char c) {
        char c2;
        System.out.println("\nMaking move level \"medium\"");
        if(c=='X') c2='O';
        else c2='X';
        //try to win
        boolean Moved = tryWin(c, c);
        //block enemy winning
        if(!Moved) Moved=tryWin(c2, c);
        if(!Moved) makeRandomMove(c);
    }

    static boolean tryWin(char c, char c2){
        int j=0;
        for (int i = 0; i < 9 ; i+=3) {
            //rows
            if(board[i]==c&&board[i+1]==c&&board[i+2]==' '){
                board[i+2] = c2; return true;
            }
            if(board[i]==c&&board[i+1]==' '&&board[i+2]==c){
                board[i+1] = c2; return true;
            }
            if(board[i]==' '&&board[i+1]==c&&board[i+2]==c){
                board[i] = c2; return true;
            }

            //cols
            if(board[j]==c&&board[j+3]==c&&board[j+6]==' '){
                board[j+6] = c2; return true;
            }
            if(board[j]==c&&board[j+3]==' '&&board[j+6]==c){
                board[j+3] = c2; return true;
            }
            if(board[j]==' '&&board[j+3]==c&&board[j+6]==c){
                board[j] = c2; return true;
            }
            j++;
        }
        //diagonally
        if(board[2]==c&&board[4]==c&&board[6]==' ') {board[6] = c2; return true;}
        if(board[2]==c&&board[4]==' '&&board[6]==c) {board[4] = c2; return true;}
        if(board[2]==' '&&board[4]==c&&board[6]==c) {board[2] = c2; return true;}
        if(board[0]==c&&board[4]==c&&board[8]==' ') {board[8] = c2; return true;}
        if(board[0]==c&&board[4]==' '&&board[8]==c) {board[4] = c2; return true;}
        if(board[2]==' '&&board[4]==c&&board[8]==c) {board[2] = c2; return true;}

        return false;
    }

    void moveHard(char c) {
        System.out.println("\nMaking move level \"hard\"");
        if(tryWin(c, c)) return;
        char[] boardCopy = board.clone();
        int index = miniMax(c).index;
        board = boardCopy;
        board[index] = c;
    }

    Move miniMax(char player){
        char huPlayer = 'X';
        char aiPlayer = 'O';
        List<Integer> availableSpots = new ArrayList<>(0);
        for (int i = 0; i < board.length ; i++) {
            if(board[i]==' ') {availableSpots.add(i);}
        }
        if (checkWin(huPlayer)==huPlayer){
            return new Move(-10);
        } else if (checkWin(aiPlayer)==aiPlayer) {
            return new Move(10);
        } else if (availableSpots.size()==0) {
            return new Move(0);
        }
        List<Move> moves= new ArrayList<>(0);

        for (int i = 0; i < availableSpots.size(); i++) {
            Move move = new Move();
            move.index = availableSpots.get(i);
            board[availableSpots.get(i)] = player;
            if( player == aiPlayer){
                Move result = miniMax(huPlayer);
                move.score = result.score;
            }
            else {
                Move result = miniMax(aiPlayer);
                move.score = result.score;
            }
            board[availableSpots.get(i)] = ' ';
            moves.add(move);

        }
        int bestMove = -1;
        if (player== aiPlayer){
            int bestScore = -10000;
            for (int i = 0; i < moves.size() ; i++) {
                if(moves.get(i).score>bestScore){
                    bestScore=moves.get(i).score;
                    bestMove = i;
                }
            }
        } else {
            int bestScore = 10000;
            for (int i = 0; i < moves.size() ; i++) {
                if(moves.get(i).score< bestScore){
                    bestScore = moves.get(i).score;
                    bestMove = i;
                }
            }
        }
        return moves.get(bestMove);
    }

    static class Move {
        int index;
        int score;

        public Move(int score) {
            this.score = score;
        }
        public Move() {

        }
    }
}