package goVisual;

import javax.swing.*;
import java.awt.*;

public class GoBackgroundPanel extends JPanel {
    private static final String GO_IMG_START = "images/";
    private static final String GO_IMG_END = ".png";

    public static final String[] SIDE_STATE = { "Left", "Middle", "Middle2", "Right" };
    public static final String[] HEIGHT_STATE = { "Top", "Middle", "Middle2", "Bottom" };

    public static final int LEFT = 0;
    public static final int MIDDLE = 1;
    public static final int MIDDLE2 = 2;
    public static final int RIGHT = 3;

    public static final int PIC_SIZE = 25;

    public static final int OFFSET = 4;
    public static final int SMALL_OFFSET = 3;

    private int size;

    public GoBackgroundPanel(int size) {
        super();
        this.size = 0;
        this.reset(size);
    }

    private void init(int size) {
        this.size = size;
        this.setLayout(new GridLayout(size, size));
        resetLabels();
        this.setBackground(Color.BLACK);
        this.setVisible(true);
        this.setOpaque(true);
    }

    private void resetLabels() {
        this.removeAll();

        JLabel label;
        int sideState, heightState;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                heightState = getHeightState(row, col);
                sideState = getSideState(row, col);
                label = new JLabel();
                label.setOpaque(true);
                label.setVisible(true);

                String s = GO_IMG_START + HEIGHT_STATE[heightState]
                        + SIDE_STATE[sideState] + GO_IMG_END;

                ImageIcon pp = createImageIcon(s, "");

                label.setIcon(pp);
                label.setSize(PIC_SIZE, PIC_SIZE);
                label.setBackground(Color.RED);
                label.setOpaque(true);
                label.setVisible(true);
                this.add(label);
            }
        }

        this.setSize(PIC_SIZE * size, PIC_SIZE * size);
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

    private int getSideState(int row, int col) {
        if (col == 0)
            return LEFT;
        if (col == size - 1)
            return RIGHT;
        if (isSpecialMiddle(row, col))
            return MIDDLE2;
        return MIDDLE;
    }

    private int getHeightState(int row, int col) {
        if (row == 0)
            return LEFT;
        if (row == size - 1)
            return RIGHT;
        if (isSpecialMiddle(row, col))
            return MIDDLE2;
        return MIDDLE;
    }

    private boolean isSpecialMiddle(int row, int col) {
        int rowOffset = getOffset(size);
        int colOffset = getOffset(size);
        if ((row == rowOffset - 1 || row == size - rowOffset || row == size / 2)
                && (col == colOffset - 1 || col == size - colOffset || col == size / 2))
            return true;
        return false;
    }

    private int getOffset(int size) {
        if (size > 12)
            return OFFSET;
        return SMALL_OFFSET;
    }

    public void reset(int size) {
        if (size != this.size)
            init(size);
    }
}
