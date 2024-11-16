
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;






public class MainWindow extends JFrame implements ActionListener{
    JPanel MainPanel;
    Color background;
    JLabel title;
    JButton addrecord;
    JButton checkrecords;
    public MainWindow(){
        this.setTitle("Expenses");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(600, 600);
        this.setResizable(false);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        
        MainPanel = new JPanel();
        background = new Color(220, 228, 201);
        MainPanel.setSize(600,600);
        MainPanel.setOpaque(true);
        MainPanel.setBackground(background);
        MainPanel.setLayout(null);
        this.add(MainPanel);
        
        title = new JLabel();
        helpingMethods.setLabel(title, "Expense Tracker", Color.black, 160, 0, 300, 90, 35, MainPanel);
        
        addrecord = new JButton();
        addrecord.addActionListener(this);
        helpingMethods.setButton(addrecord, "Add New Record", Color.black, new Color(224, 123, 57), 160, 150, 250, 60, 25, MainPanel);

        checkrecords = new JButton();
        checkrecords.addActionListener(this);
        helpingMethods.setButton(checkrecords, "Check Records", Color.black, new Color(224, 123, 57), 160, 300, 250, 60, 25, MainPanel);
        
        this.revalidate();
        this.setVisible(true);
    }



    @Override
    public void actionPerformed(ActionEvent e){
        JButton source = (JButton) e.getSource();
        if (source.equals(addrecord)){
            addrecord win = new addrecord();
        }
    }
}



