package JavaFX.tris;

import java.util.*;
import java.util.stream.Stream;

public class AiPlayer
{

    public static void main(String[] args)
    {
        AiPlayer p = new AiPlayer();

        System.out.println(p.decide(new ArrayList<>(List.of("X", "X", "O", "X", "O", "X", " ", " ", "O"))));
    }

    public int decide(List<String> board)
    {
        return findBestMove(true, board)[0];
    }

    public int decideRandom(List<String> board)
    {
        List<Integer> options = new ArrayList<>();

        for (int i = 0; i < board.size(); i++)
        {
            if (board.get(i).equals(" ")) options.add(i);
        }

        int chosen = options.get(new Random().nextInt(options.size()));

        return chosen;
    }

    public List<Integer> possibleMoves(List<String> board)
    {
        List<Integer> options = new ArrayList<>();

        for (int i = 0; i < board.size(); i++)
        {
            if (board.get(i).equals(" ")) options.add(i);
        }
        return options;
    }

    public int[] findBestMove(boolean forAi, List<String> board)
    {
        List<Integer> options = possibleMoves(board);
//        List<Integer> values = new ArrayList<>();

        Map<Integer, Integer> moves = new HashMap<>();

        for (int move: options)
        {
        //calcola il valore della mossa

            //esegue la mossa
            board.set(move, forAi? "X" : "O");

            GameState state = checkState(board);

            int value = switch(state)
                    {
                        // se fa terminare la partita, allora il valore è determinato
                        case AI_WON -> 1;
                        case USER_WON -> -1;
                        case TIE -> 0;

                        //altrimenti è il valore della migliore mossa per l'avversario
                        case PLAYING -> findBestMove(!forAi, board)[1];
                    };

//            values.add(value);
            moves.put(move, value);

            //annulla la mossa
            board.set(move, " ");
        }

        int bestValue = moves.values().stream().max( (a,b) -> forAi? a-b : b-a ).get();
        int bestMovesCount = (int) moves.keySet().stream().filter(k -> moves.get(k) == bestValue).count();
        int bestMove = moves.keySet().stream().filter(k -> moves.get(k) == bestValue).skip(new Random().nextInt(bestMovesCount)).findAny().get();

        return new int[] {bestMove, bestValue};
    }

    public GameState checkState(List<String> board)
    {
//        System.out.println("checking the game state...");
//        System.out.println(board);
        boolean aiWins = false;
        boolean userWins = false;

        //checks the rows
        for (int row = 0; row < 3; row++)
        {
            int cell = row * 3;
            if (!board.get(cell).equals(" ") && board.get(cell).equals(board.get(cell + 1)) && board.get(cell)
                    .equals(board.get(cell + 2)))
                return board.get(cell).equals("X") ? GameState.AI_WON : GameState.USER_WON;

        }

        // checks the columns
        for (int column = 0; column < 3; column++)
        {
            if (!board.get(column).equals(" ") && board.get(column).equals(board.get(column + 3)) && board.get(column)
                    .equals(board.get(column + 6)))
                return board.get(column).equals("X") ? GameState.AI_WON : GameState.USER_WON;

        }

        // checks the first diagonal
        if (!board.get(0).equals(" ") && board.get(0).equals(board.get(4)) && board.get(0).equals(board.get(8)))
            return board.get(0).equals("X") ? GameState.AI_WON : GameState.USER_WON;

        // checks the second diagonal
        if (!board.get(2).equals(" ") && board.get(2).equals(board.get(4)) && board.get(2).equals(board.get(6)))
            return board.get(2).equals("X") ? GameState.AI_WON : GameState.USER_WON;


        if (board.stream().filter(s -> s.equals(" ")).count() == 0)
            return GameState.TIE;

        return GameState.PLAYING;
    }

}
