package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MyGameWindow extends JFrame implements MouseListener {
    public static final int DEFUALT_SIZE = 19;
    public static final int DEFAULT_BLACK_DEAPTH = -1;
    public static final int DEFAULT_WHITE_DEAPTH = 0;
    public static final int MAX_DEPTH = GoGameLoop.MAX_DEPTH + 1;

    private Thread gameThread;
    private GoGameLoop goGameLoop;
    private Container thisContainer;

    public MyGameWindow() {
        super();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        init();
    }

    public void init() {
        thisContainer = this.getContentPane();
        thisContainer.setLayout(new BorderLayout());
        thisContainer.add(createMenuPanel(), BorderLayout.NORTH);
    }

    public void startGame() {
        this.gameThread = new Thread(goGameLoop);
        this.gameThread.start();
    }

    private JMenuBar createMenuPanel() {
        JMenuBar menuPanel = new JMenuBar();
        menuPanel.add(createFileMenu());
        menuPanel.add(createSettingsMenu());
        return menuPanel;
    }

    private JMenu createSettingsMenu() {
        JMenu settingsMenu = new JMenu("Settings");
        settingsMenu.add(createBlackPlayerMenu());
        settingsMenu.add(createWhitePlayerMenu());
        return settingsMenu;
    }

    private JMenuItem createBlackPlayerMenu() {
        JMenu menu = new JMenu("Black");
        JRadioButtonMenuItem[] buttons = new JRadioButtonMenuItem[MAX_DEPTH];
        
        for(int i = 0; i < buttons.length; i++){
            final int x = i-1;
            buttons[i] = new JRadioButtonMenuItem("Computer Depth " + x);
            buttons[i].addActionListener(e -> {
                goGameLoop.setBlackDepth(x);
            });
        }
        buttons[0].setText("Human");

        ButtonGroup group = new ButtonGroup();
        for (JRadioButtonMenuItem button : buttons) {
            group.add(button);
            menu.add(button);
        }
        buttons[DEFAULT_BLACK_DEAPTH + 1].setSelected(true);
        return menu;
    }

    private JMenuItem createWhitePlayerMenu() {
        JMenu menu = new JMenu("White");
        JRadioButtonMenuItem[] buttons = new JRadioButtonMenuItem[MAX_DEPTH];
        
        for(int i = 0; i < buttons.length; i++){
            final int x = i-1;
            buttons[i] = new JRadioButtonMenuItem("Computer Depth " + x);
            buttons[i].addActionListener(e -> {
                goGameLoop.setWhiteDepth(x);
            });
        }
        buttons[0].setText("Human");

        ButtonGroup group = new ButtonGroup();
        for (JRadioButtonMenuItem button : buttons) {
            group.add(button);
            menu.add(button);
        }
        buttons[DEFAULT_WHITE_DEAPTH + 1].setSelected(true);
        return menu;
    }

    private JMenu createFileMenu() {
        JMenu fileMenu = new JMenu("File");
        fileMenu.add(createNewGameMenu());
        fileMenu.add(createNewGameSize(19));
        fileMenu.add(createNewGameSize(13));
        fileMenu.add(createNewGameSize(9));
        fileMenu.add(createExit());
        return fileMenu;
    }

    private JMenuItem createExit() {
        JMenuItem exitButton = new JMenuItem("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        return exitButton;
    }

    private JMenuItem createNewGameMenu() {
        JMenuItem newGameMenu = new JMenuItem("New Game");
        newGameMenu.addActionListener(e -> resetGame());
        return newGameMenu;
    }

    private JMenuItem createNewGameSize(int size) {
        JMenuItem newGameMenu = new JMenuItem("New Game " + size);
        newGameMenu.addActionListener(e -> resetGame(size));
        return newGameMenu;
    }

    public void resetGame() {
        if (goGameLoop == null)
            this.resetGame(DEFUALT_SIZE);
        else
            this.resetGame(goGameLoop.getCurrentSize());
    }

    private void resetGame(int size) {
        int blackDepth = DEFAULT_BLACK_DEAPTH;
        int whiteDepth = DEFAULT_WHITE_DEAPTH;
        if (goGameLoop != null) {
            goGameLoop.stopRunning();
            thisContainer.remove(goGameLoop.getVisualBoard());
            blackDepth = goGameLoop.getBlackDepth();
            whiteDepth = goGameLoop.getWhiteDepth();
        }

        goGameLoop = new GoGameLoop(size, blackDepth, whiteDepth);
        thisContainer.add(goGameLoop.getVisualBoard(), BorderLayout.CENTER);

        this.repaint();
        this.revalidate();
        this.pack();

        this.startGame();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        mousePressed(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent arg0) {

    }

    public static void main(String[] args) {
        MyGameWindow window = new MyGameWindow();
        window.resetGame();
    }
}
