import javax.swing.*;

class snake extends JFrame{
    snake(){

        add(new board());
        pack();
        setLocationRelativeTo(null);
        setTitle("Snake Game");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args) {
        new snake().setVisible(true);
    }
}