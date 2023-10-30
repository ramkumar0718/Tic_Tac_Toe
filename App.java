import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class App {
    static Scanner in = new Scanner(System.in);
    static ArrayList<String> board = new ArrayList<String>();
    static ArrayList<String> boardClone = new ArrayList<String>();
    static String oxList[] = {"X", "O"};
    static String currentWinner;

    public static void main(String[] args) {
        app();
    }
    
    public static ArrayList<String> board() {
        for (int i = 1; i <= 9; i++) {
            board.add(" ");
        }
        return board;
    }

    public static void printBoard(int index, String element) {
        board.set(index, element);
        System.out.println("   |   |");
        System.out.println(" " + board.get(0) + " | " + board.get(1) + " | " + board.get(2));
        System.out.println("   |   |");
        System.out.println("-----------");
        System.out.println("   |   |");
        System.out.println(" " + board.get(3) + " | " + board.get(4) + " | " + board.get(5));
        System.out.println("   |   |");
        System.out.println("-----------");
        System.out.println("   |   |");
        System.out.println(" " + board.get(6) + " | " + board.get(7) + " | " + board.get(8));
        System.out.println("   |   |");
    }
    
    public static boolean isFull() {
        return !(board.contains(" "));
    }
    
    public static int pickRandomElement(ArrayList<Integer> list) {
        Random random = new Random();
        int randomIndex = random.nextInt(list.size());
        int randomElement = list.get(randomIndex);
        return randomElement;
    }
    
    public static boolean checkWinner(ArrayList<String> board, String ele) {
        currentWinner = ele;
        return ((board.get(0) == ele && board.get(1) == ele && board.get(2) == ele) || (board.get(0) == ele && board.get(3) == ele && board.get(6) == ele) || (board.get(0) == ele && board.get(4) == ele && board.get(8) == ele) || (board.get(1) == ele && board.get(4) == ele && board.get(7) == ele) || (board.get(2) == ele && board.get(5) == ele && board.get(8) == ele) || (board.get(2) == ele && board.get(4) == ele && board.get(6) == ele) || (board.get(3) == ele && board.get(4) == ele && board.get(5) == ele) || (board.get(6) == ele && board.get(7) == ele && board.get(8) == ele));
    }
    
    public static String playAgain() {
        System.out.print("\nDo you want to play again (y or n): ");
        String inputPlayAgain = in.next().toLowerCase();
        if (inputPlayAgain.equals("y"))
            App.main(null);
        else if(inputPlayAgain.equals("n"))
            System.exit(0);
        else
            playAgain();
        return inputPlayAgain;
    }
    
    public static int humanMove() {
        System.out.println("\n======================================================================");
        System.out.println("Human's Move:");
        System.out.print("Enter your position: ");
        Integer pos = in.nextInt();
        return pos -1;
    }
    
    public static int compMove() {
        ArrayList<Integer> possibleMoves = new ArrayList<Integer>();
        
        for (int i = 0; i <= 8; i++) {
            if (board.get(i) == " ")
                possibleMoves.add(i + 1);
        }
        
        for (String var : oxList) {
            for (int i : possibleMoves) {
                boardClone = (ArrayList)board.clone();
                String setVar = boardClone.set(i-1, var);
                if (checkWinner(boardClone, var) == true)
                    return i -1;
            }
        }
        
        ArrayList<Integer> cornerMoves = new ArrayList<Integer>();
        for (int i : possibleMoves) {
            if (i == 1 || i == 3 || i == 7 || i == 9)
                cornerMoves.add(i);
        }
        if (cornerMoves.size() > 0) {
            int pos = pickRandomElement(cornerMoves);
            return pos -1;
        }
        
        if (possibleMoves.contains(5))
            return 4;
        
        ArrayList<Integer> edgeMoves = new ArrayList<Integer>();
        for (int i : possibleMoves) {
            if (i == 2 || i == 4 || i == 6 || i == 8)
                edgeMoves.add(i);
        }
        
        if (edgeMoves.size() > 0) {
            int pos = pickRandomElement(edgeMoves);
            return pos -1;
        }
        return 10;
    }
    
    public static void app() {
        board.clear();
        board();
        while (true) {
            if (checkWinner(board,"O") == true || checkWinner(board,"X") == true) {
                System.out.println("Winner is " + currentWinner);
                playAgain();
            }
            
            int human_pos = humanMove();
            if ((human_pos >= 0 && human_pos <= 8) && board.get(human_pos)== " "){
                printBoard(human_pos, "O");
                if (isFull() == false && (checkWinner(board,"O") == false && checkWinner(board,"X") == false)) {
                    int comp_pos = compMove();
                    System.out.println("\nComputer's Placed Move On " + Integer.toString(comp_pos+1) + " :");
                    printBoard(comp_pos, "X");
                }else if(isFull() == true) {
                    System.out.println("It's tie");
                    playAgain();
                }
            }else {
                System.out.println("Please type the unoccupied numbers within the range(1 - 9) !!!\n");
                continue;
            }
        }
    }
}