import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
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
    JButton addToNewmonth = new JButton();
    JButton addToPreviousmont = new JButton();
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
    Color buttonColor = new Color(224, 123, 57);
    Connection con;
    public addrecord(){
        this.setTitle("Add new Record");
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
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
        addToNewmonth.addActionListener(this);
        addToPreviousmont.addActionListener(this);

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
        
        helpingMethods.setButton(back, "Back", Color.black, buttonColor, 20, 30, 80, 40, 20, main);
        helpingMethods.setButton(Reset, "Reset", Color.black, buttonColor, 90, 460, 100, 40, 20, main);
        helpingMethods.setButton(addToNewmonth, "New Add", Color.black, buttonColor, 220, 460, 130, 40, 20, main);
        helpingMethods.setButton(addToPreviousmont, "Previous Add", Color.black, buttonColor, 370, 460, 150, 40, 20, main);
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
                if (source.equals(addToNewmonth))
                     this.addToDatabase(true);
                else if (source.equals(addToPreviousmont)){
                     this.addToDatabase(false);
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "Check Inputs, Something is Wrong! No Field must be empty and Non-number Fields should not have more than 20 characters.");
            }
        }
    }
    
    private boolean checkinputs(){
        if (newearned.getText().length() == 0 || srcOfEarning.getText().length() == 0 || dateOfEarning.getText().length() == 0 || amountspent.getText().length() == 0 || reasonForUse.getText().length() == 0 || amountspent.getText().length() == 0 || dateOfspent.getText().length() == 0){
                    return false;
        }

        if (!helpingMethods.isnumerical(newearned.getText()) || !helpingMethods.isnumerical(amountspent.getText())){
            return false;
        }

        if (srcOfEarning.getText().length() > 20 || dateOfEarning.getText().length() > 20 || reasonForUse.getText().length() > 20 || dateOfspent.getText().length() > 20){
            return false;
        }
        return true;
    }

    private void ConnectToDatabase(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String[] creds = helpingMethods.getCreds();
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ETS", creds[0], creds[1]);
        } catch (ClassNotFoundException e) {
            // TODO: handle exception
            Logger.getLogger(addrecord.class.getName()).log(Level.SEVERE, null, e);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void reset(){
        newearned.setText("");
        srcOfEarning.setText("");
        dateOfEarning.setText("");
        amountspent.setText("");
        reasonForUse.setText("");
        dateOfspent.setText("");
    }

    private void addToDatabase(boolean newmonth){
        this.ConnectToDatabase();
        try {
            Statement smt = con.createStatement();
            ResultSet rs = null;
            int count = 1;
            String fontcolor = "";
            String background = "";
            if (!newmonth){
                rs = smt.executeQuery("SELECT Finalbalance, counts, fontcolor, backgroundcolor FROM records ORDER BY number DESC LIMIT 1");
                rs.next();
                count = rs.getInt("counts") + 1;
                fontcolor = rs.getString("fontcolor");
                background = rs.getString("backgroundcolor");
            }
            else{
                rs = smt.executeQuery("SELECT Finalbalance FROM records ORDER BY number DESC LIMIT 1");
                rs.next();
                background = this.getrandombackground();
                fontcolor = this.getfontcolor(Color.decode(background));
            }
            int previousbalance = rs.getInt("Finalbalance");
            int Earn = Integer.parseInt(newearned.getText()) < 0 ? 0:Integer.parseInt(newearned.getText());
            int Spent = Integer.parseInt(amountspent.getText()) < 0 ? 0:Integer.parseInt(amountspent.getText());
            int balance = Earn - Spent;
            int finalbalance = previousbalance + balance;
            smt.close();
            PreparedStatement stmnt = con.prepareStatement("INSERT INTO records(earned, counts, fontcolor, backgroundcolor, srcearn, dateEarned, spent, srcspent, datespent, balance, previousbalance, Finalbalance) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
            stmnt.setInt(1, Earn);
            stmnt.setInt(2, count);
            stmnt.setString(3, fontcolor);
            stmnt.setString(4, background);
            stmnt.setString(5, srcOfEarning.getText());
            stmnt.setString(6, dateOfEarning.getText());
            stmnt.setInt(7, Spent);
            stmnt.setString(8, reasonForUse.getText());
            stmnt.setString(9, dateOfspent.getText());
            stmnt.setInt(10, balance);
            stmnt.setInt(11, previousbalance);
            stmnt.setInt(12, finalbalance);
            stmnt.execute();
            stmnt.close();
            if (checkLength()){
                removeFirstRow();
            }
            JOptionPane.showMessageDialog(null, "Records was Inserted successfully!");
            this.reset();
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    private boolean checkLength(){
        Connection con = helpingMethods.establishConnection();
        try {
            Statement smt = con.createStatement();
            ResultSet rs = smt.executeQuery("SELECT COUNT(*) FROM records");
            rs.next();
            int length = rs.getInt("COUNT(*)");
            smt.close();
            if (length > 1500){
                return true;
            }
            else{
                return false;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }


    private String getrandombackground(){
        String bg = "#";
        String alphabet = "0123456789abcdef";
        Random rand = new Random();
        for (int i = 1; i < 7; i++){
            bg += alphabet.charAt(rand.nextInt(16));
        }

        return bg;
    }

    private String getfontcolor(Color color){
        double a = 1 - (0.299 * color.getRed() + 0.587 * color.getGreen() + 0.114 * color.getBlue()) / 255;
        return a < 0.5 ? "black" : "white";
    } 
    private void removeFirstRow(){
        Connection con = helpingMethods.establishConnection();
        try {
            Statement smt = con.createStatement();
            ResultSet rs = smt.executeQuery("SELECT number FROM records LIMIT 1");
            rs.next();
            int firstrownum = rs.getInt("number");
            smt.close();
            PreparedStatement stmnt = con.prepareStatement("DELETE FROM records WHERE number=?");
            stmnt.setInt(1, firstrownum);
            stmnt.execute();
            stmnt.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}



