import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import sweeper.Box;
import sweeper.Coord;
import sweeper.Game;
import sweeper.Ranges;

public class JavaSweeper extends JFrame {

    private Game game;

    private JPanel panel;
    private JLabel lable;

    private final int cols = 9;
    private final int rows = 9;
    private final int bombs = 10;
    private final int image_size = 50;

    public static void main(String[] args) {
        new JavaSweeper();
    }
    private JavaSweeper(){
        game = new Game(cols, rows, bombs);
        game.start();
        setImage();
        initLable();
        initPanel();
        initFrame();
    }

    private void initLable(){
        lable = new JLabel("Welcome");
        add(lable, BorderLayout.SOUTH);
    }

    private void initPanel(){
        panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for(Coord coord : Ranges.getAllCoords()){
                    g.drawImage((Image)game.getBox(coord).image,coord.x * image_size, coord.y * image_size, this);
                }

            }
        };

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / image_size;
                int y = e.getY() / image_size;
                Coord coord = new Coord(x, y);
                if(e.getButton() == MouseEvent.BUTTON1)
                    game.pressLeftButton(coord);
                if(e.getButton() == MouseEvent.BUTTON3)
                    game.pressRightButton(coord);
                if(e.getButton() == MouseEvent.BUTTON2)
                    game.start();
                lable.setText(getMessage());
                panel.repaint();
            }
        });
        panel.setPreferredSize(new Dimension(Ranges.getSize().x * image_size,Ranges.getSize().y * image_size));
        add(panel);
    }

    private String getMessage() {
        switch (game.getState()){
            case PLAYED: return "Think twice";
            case BOMBED: return "Game Over";
            case WINNER: return "CONGRATULATIONS";
            default: return "Welcome";
        }
    }

    private void initFrame(){

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("мой сапер");

        setResizable(false);
        setVisible(true);
        setIconImage(getImage("icon"));
        pack();
        setLocationRelativeTo(null);
    }
    private void setImage(){
        for(Box box : Box.values()){
             box.image = getImage(box.name().toLowerCase());
        }
    }
    private Image getImage(String name){
        String filename = "img/" + name + ".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(filename));
        return icon.getImage();

    }
}
