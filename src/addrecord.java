import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JPanel;
import java.awt.Color;

public class addrecord extends JFrame implements ActionListener{
    JButton back = new JButton();
    JButton Reset = new JButton();
    JButton add = new JButton();
    JTextField newearned = new JTextField();
    JTextField srcOfEarning = new JTextField();
    JTextField dateOfEarning = new JTextField();
    JTextField amountspent = new JTextField();
    JTextField reasonForUse = new JTextField();
    JTextField dateOfspent = new JTextField();
    JPanel main = new JPanel();
    JLabel forearned = new JLabel();
    JLabel srcearnedlabel = new JLabel();
    JLabel date = new JLabel();
    JLabel amountspentlabel = new JLabel();
    JLabel reasonforuselabel = new JLabel();
    JLabel datespentlabel = new JLabel();
    JLabel title = new JLabel();
    Connection con;
    public addrecord(){
        this.setTitle("Add new Record");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(600, 600);
        this.setResizable(false);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);
        this.setLayout(null);
        
        main.setSize(600,600);
        main.setOpaque(true);
        main.setBackground(new Color(220, 228, 201));
        main.setLayout(null);
        this.add(main);

        back.addActionListener(this);
        Reset.addActionListener(this);
        add.addActionListener(this);

        helpingMethods.setButton(back, "Back", Color.black, new Color(224, 123, 57), 20, 30, 80, 40, 20, main);

        helpingMethods.setLabel(title, "Add New Record", Color.black, 160, 0, 300, 70, 35, main);
        helpingMethods.setLabel(forearned, "Amount Earned: ", Color.black, 110, 90, 200, 50, 20, main);
        helpingMethods.setLabel(srcearnedlabel, "Source Of Earning: ", Color.black, 110, 140, 200, 50, 20, main);
        helpingMethods.setLabel(date, "Date Of Earning: ", Color.black, 110, 190, 200, 50, 20, main);
        helpingMethods.setLabel(amountspentlabel, "Amount Spent: ", Color.black, 110, 240, 200, 50, 20, main);
        helpingMethods.setLabel(reasonforuselabel, "Reason For Use: ", Color.black, 110, 290, 200, 50, 20, main);
        helpingMethods.setLabel(datespentlabel, "Date of Spent: ", Color.black, 110, 340, 200, 50, 20, main);
        
        helpingMethods.settextfield(newearned, 300, 100, 150, 30, main);
        helpingMethods.settextfield(srcOfEarning, 300, 150, 150, 30, main);
        helpingMethods.settextfield(dateOfEarning, 300, 200, 150, 30, main);
        helpingMethods.settextfield(amountspent, 300, 250, 150, 30, main);
        helpingMethods.settextfield(reasonForUse, 300, 300, 150, 30, main);
        helpingMethods.settextfield(dateOfspent, 300, 350, 150, 30, main);
        
        helpingMethods.setButton(Reset, "Reset", Color.black, new Color(224, 123, 57), 120, 460, 100, 40, 20, main);
        helpingMethods.setButton(add, "Add", Color.black, new Color(224, 123, 57), 360, 460, 100, 40, 20, main);
        this.setVisible(true);
    }



    @Override
    public void actionPerformed(ActionEvent e){
        JButton source = (JButton) e.getSource();

        if (source.equals(back)){
            this.dispose();
        }
        else if(source.equals(Reset)){
            this.reset();
        }
        else{
            if (checkinputs()){
                this.ConnectToDatabase();
                try {
                    Statement smt = con.createStatement();
                    ResultSet rs;
                    rs = smt.executeQuery("SELECT Finalbalance FROM records ORDER BY number DESC LIMIT 1");
                    rs.next();
                    int previousbalance = Integer.parseInt(rs.getString("Finalbalance"));
                    int balance = Integer.parseInt(newearned.getText()) - Integer.parseInt(amountspent.getText());
                    int finalbalance = previousbalance + balance;
                    String command = "INSERT INTO records(earned, srcearn, dateEarned, spent, srcspent, datespent, balance, previousbalance, Finalbalance) VALUES(" + Integer.parseInt(newearned.getText())+ ", '" + srcOfEarning.getText() + "', '" + dateOfEarning.getText() + "', " + Integer.parseInt(amountspent.getText()) + ", '" +reasonForUse.getText() + "', '"+ dateOfspent.getText() + "', "+ balance + ", " + previousbalance + "," + finalbalance +")";
                    smt.execute(command);
                    smt.close();
                    JOptionPane.showMessageDialog(null, "Records was Inserted successfully");
                    this.reset();
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "Check Inputs, Something is Wrong!");
            }
        }
    }
    
    private boolean checkinputs(){
        if (newearned.getText().length() == 0 || srcOfEarning.getText().length() == 0 || dateOfEarning.getText().length() == 0 || amountspent.getText().length() == 0 || reasonForUse.getText().length() == 0 || amountspent.getText().length() == 0){
                    return false;
        }

        if (!helpingMethods.isnumerical(newearned.getText()) || !helpingMethods.isnumerical(amountspent.getText())){
            return false;
        }
        return true;
    }

    private void ConnectToDatabase(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String[] creds = this.getCreds();
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ETS", creds[0], creds[1]);
        } catch (ClassNotFoundException e) {
            // TODO: handle exception
            Logger.getLogger(addrecord.class.getName()).log(Level.SEVERE, null, e);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private String[] getCreds(){
        File file = new File("./ExpenseTracker/src/credentials.txt");
        String[] result = new String[2];
        String res = "";
        try {
            FileReader reader = new FileReader(file);
            int data = reader.read();
            
            while(data != -1){
                res += (char)data;
                data = reader.read();
            }
            reader.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        result = res.split(",");
        return result;
    }
    private void reset(){
        newearned.setText("");
        srcOfEarning.setText("");
        dateOfEarning.setText("");
        amountspent.setText("");
        reasonForUse.setText("");
        dateOfspent.setText("");
    }
}



