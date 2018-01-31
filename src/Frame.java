import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class Frame extends JFrame{
    private JPanel buttonSelectionPanel;
    private Panel sPanel;

    public Frame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Sudoku");
        this.setMinimumSize(new Dimension(800, 600));

        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("Game");
        JMenu newGame = new JMenu("New Game");
        JMenuItem EasyGame = new JMenuItem("Easy");
        JMenuItem MediumGame = new JMenuItem("Medium");
        JMenuItem HardGame = new JMenuItem("Hard");

        EasyGame.addActionListener(new NewGameListener(1));
        MediumGame.addActionListener(new NewGameListener(2));
        HardGame.addActionListener(new NewGameListener(3));

        newGame.add(EasyGame);
        newGame.add(MediumGame);
        newGame.add(HardGame);

        file.add(newGame);
        menuBar.add(file);
        this.setJMenuBar(menuBar);

        JPanel windowPanel = new JPanel();
        windowPanel.setLayout(new FlowLayout());
        windowPanel.setPreferredSize(new Dimension(800, 600));

        buttonSelectionPanel = new JPanel();
        buttonSelectionPanel.setPreferredSize(new Dimension(900, 500));

        sPanel = new Panel();

        windowPanel.add(sPanel);
        windowPanel.add(buttonSelectionPanel);
        this.add(windowPanel);

        rebuildInterface(2);
    }
    public void rebuildInterface(int level) {
        Puzzle generatedPuzzle = new Puzzle(level);
        sPanel.newSudokuPuzzle(generatedPuzzle);
        sPanel.setLevel(level);
        buttonSelectionPanel.removeAll();
        for(String value : generatedPuzzle.getValidValues()) {
            JButton b = new JButton(value);
            b.setPreferredSize(new Dimension(50,50));
            b.addActionListener(sPanel.new NumActionListener());
            buttonSelectionPanel.add(b);
        }
        sPanel.repaint();
        buttonSelectionPanel.revalidate();
        buttonSelectionPanel.repaint();
    }

    private class NewGameListener implements ActionListener {

        private int level;

        public NewGameListener(int level) {
            this.level = level;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            rebuildInterface(level);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Frame frame = new Frame();
                frame.setVisible(true);
            }
        });
    }
}
