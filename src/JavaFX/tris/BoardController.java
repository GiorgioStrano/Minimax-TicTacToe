package JavaFX.tris;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class BoardController
{
    //    App app = App.getInstance();
    private boolean turnAI = true;

    private GameState state = GameState.PLAYING;

    private List<String> board = new ArrayList<>(Collections.nCopies(9, " "));

    private List<Label> labels;

    @FXML
    private Label c;
    @FXML
    private Label n;
    @FXML
    private Label ne;
    @FXML
    private Label e;
    @FXML
    private Label se;
    @FXML
    private Label s;
    @FXML
    private Label so;
    @FXML
    private Label o;
    @FXML
    private Label no;

    @FXML
    private AnchorPane container;

    private AiPlayer player;

    public void initialize()
    {
//        System.out.println("initialization");

        labels = List.of(no, n, ne, o, c, e, so, s, se);

        player = new AiPlayer();

        turnAI = new Random().nextBoolean();

        if (turnAI) takeTurn();
    }

    /*
     * takes the turn for the AI player
     */
    public void takeTurn()
    {
        int i = player.decide(board);
        makeMove(labels.get(i));
    }

    /*
     * makes the move corresponding to the click and passes the turn to the AI
     */
    public void click(MouseEvent evt)
    {
        if (!(evt.getTarget() instanceof Label)) return;
        Label label = ((Label) evt.getTarget());

        makeMove(label);
    }

    /*
     * takes a label and draws in it the symbol of the current player, then updates the board and checks the game state
     */
    public void makeMove(Label label)
    {
        if (state != GameState.PLAYING) return;
        if (!label.getText().equals("")) return;

        if (turnAI)
            label.setText("X");
        else
            label.setText("O");

        int index = labels.indexOf(label);

        if (index == -1) return;

        board.set(index, turnAI ? "X" : "O");

        state = player.checkState(board);
        if (state != GameState.PLAYING)
        {
            System.out.println(state == GameState.AI_WON? "AI won!" : state == GameState.USER_WON? "You won!" : "It's a tie!");
        }
        else
        {
            turnAI = !turnAI;
            if(turnAI) takeTurn();
        }

    }



    @FXML
    public void reset(KeyEvent evt)
    {
        int i = 0;
        state = GameState.PLAYING;

        if (evt.getCode() == KeyCode.ENTER)
            for (Node n : container.getChildren())
            {
                ((Label) n).setText("");
                board.set(i++, " ");
            }

        turnAI = new Random().nextBoolean();
        if (turnAI) takeTurn();
    }

}
