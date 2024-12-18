import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.Color;




public class helpingMethods {
    public static void setLabel(JLabel label, String text, Color color, int x, int y, int width, int height, int fontsize, JPanel panel){
        label.setText(text);
        label.setForeground(color);
        label.setBounds(x,y,width,height);
        label.setFont(new Font("OpenSans", Font.BOLD, fontsize));
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.CENTER);
        panel.add(label);
    }

    public static void setButton(JButton button, String text, Color color, Color Background, int x, int y, int width, int height, int fontsize, JPanel panel){
        button.setText(text);
        button.setForeground(color);
        button.setBackground(Background);
        button.setFocusable(false);
        button.setBounds(x,y,width, height);
        button.setFont(new Font("OpenSans", Font.PLAIN, fontsize));
        button.setVerticalTextPosition(JButton.CENTER);
        button.setHorizontalTextPosition(JButton.CENTER);
        panel.add(button);
    }

    public static void settextfield(JTextField textfield, int x, int y, int width, int height, JPanel panel) {
		textfield.setBounds(x, y, width, height);
		textfield.setFont(new Font("OpenSans", Font.PLAIN, 15));
		textfield.setForeground(Color.black);
		panel.add(textfield);
	}

    public static boolean isnumerical(String str){
        if (str == null){
            return false;
        }
        boolean isinteger = true;

        try{
            int num = Integer.parseInt(str);
        }
        catch(NumberFormatException exception){
            isinteger = false;
        }

        return isinteger;
    }

    public static Connection establishConnection(){
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String[] creds = helpingMethods.getCreds();
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ETS", creds[0], creds[1]);

        } catch (ClassNotFoundException e) {
            // TODO: handle exception
            Logger.getLogger(addrecord.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(null, "Driver Class wasn't found : " + e.getMessage());
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null,"Couldn't find the database : " + e.getMessage());
            e.printStackTrace();
        }
        return con;
    }

    
    public static String[] getCreds(){
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
            JOptionPane.showMessageDialog(null,"File was not found" + e.getMessage());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        result = res.split(",");
        return result;
    }
}
