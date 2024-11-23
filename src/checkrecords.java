import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableCellRenderer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class checkrecords extends JFrame implements ActionListener{

    JButton back = new JButton();
    JLabel title = new JLabel();
    JPanel main = new JPanel();
    public checkrecords(){
        this.setTitle("Records");
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setSize(1150, 1000);
        this.setResizable(false);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);
        main.setSize(1150, 1000);
        main.setOpaque(true);
        main.setBackground(new Color(220, 228, 201));
        main.setLayout(null);
        this.add(main);
        main.setLayout(null);
        back.addActionListener(this);
        helpingMethods.setLabel(title, "Records", Color.black, 490, 20, 300, 70, 45, main);
        helpingMethods.setButton(back, "Back", Color.black, new Color(224, 123, 57), 30, 30, 80, 40, 20, main);
        table table = new table();
        JScrollPane jsp = new JScrollPane(table);
        table.getTableHeader().setReorderingAllowed(false);
        jsp.setBounds(50, 100, 1050, 800);
        jsp.setBackground(new Color(220, 228, 201));
        main.add(jsp);

        this.setVisible(true);
    }





    @Override
    public void actionPerformed(ActionEvent e){
        JButton src = (JButton)e.getSource();

        if (src.equals(back)){
            this.dispose();
        }
    }



}