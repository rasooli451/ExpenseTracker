
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;






public class MainWindow extends JFrame implements ActionListener{
    JPanel MainPanel =  new JPanel();
    Color background = new Color(220, 228, 201); 
    Color buttonColor = new Color(224, 123, 57);
    JLabel title = new JLabel();
    JButton addrecord = new JButton();
    JButton checkrecords = new JButton();
    ImageIcon icon = new ImageIcon("./ExpenseTracker/src/icon.png");
    public MainWindow(){
        this.setTitle("Expenses");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(600, 600);
        this.setResizable(false);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setIconImage(icon.getImage());
        
        MainPanel.setSize(600,600);
        MainPanel.setOpaque(true);
        MainPanel.setBackground(background);
        MainPanel.setLayout(null);
        this.add(MainPanel);
        
        helpingMethods.setLabel(title, "Expense Tracker", Color.black, 160, 0, 300, 90, 35, MainPanel);
        helpingMethods.setButton(addrecord, "Add New Record", Color.black, buttonColor, 160, 150, 250, 60, 25, MainPanel);
        helpingMethods.setButton(checkrecords, "Check Records", Color.black, buttonColor, 160, 300, 250, 60, 25, MainPanel);
        
        checkrecords.addActionListener(this);
        addrecord.addActionListener(this);
        
        this.revalidate();
        this.setVisible(true);
        
    }



    @Override
    public void actionPerformed(ActionEvent e){
        JButton source = (JButton) e.getSource();
        if (source.equals(addrecord)){
            addrecord win = new addrecord();
        }
        else if(source.equals(checkrecords)){
            checkrecords cr = new checkrecords();
        }
    }
}



