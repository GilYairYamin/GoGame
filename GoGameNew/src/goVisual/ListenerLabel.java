package goVisual;

import GoGame.Tool;

import javax.swing.*;
import java.util.HashMap;


public class ListenerLabel extends JLabel {


    public static final String GO_IMG_START = "images/";
    public static final String GO_IMG_END = ".png";
    public static final String SAPERATE_STRING = ",";

    public static final HashMap<Tool, String> TOOL;
    public static final String[] STATE =
            {"", "Possible"};

    static {
        TOOL = new HashMap<>();
        TOOL.put(Tool.BLACK, "black");
        TOOL.put(Tool.WHITE, "white");
    }

    public static final int PUT = 0;
    public static final int POSSIBLE = 1;

    private Tool playerTool;
    private int state;

    private int row, col;

    public ListenerLabel(Tool tool, int state, int row, int col) {
        super();
        this.row = row;
        this.col = col;
        init(tool, state);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    private void init(Tool tool, int state) {
        this.playerTool = tool;
        this.state = state;
        setImage();
    }

    private void setImage() {
        ImageIcon pp = null;
        if (playerTool != Tool.EMPTY) {
            String s = GO_IMG_START + TOOL.get(playerTool) + STATE[state]
                    + GO_IMG_END;
            //java.net.URL imgURL = getClass().getResource(s);
            pp = createImageIcon(s, "");
        }
        this.setIcon(pp);
    }

    protected ImageIcon createImageIcon(String path, String description) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    public void setTool(Tool tool) {
        init(tool, PUT);
    }


    public void setIsMouseOn(Tool tool) {
        init(tool, POSSIBLE);
    }

    public void setIsMouseOff() {
        init(Tool.EMPTY, POSSIBLE);
    }
}
