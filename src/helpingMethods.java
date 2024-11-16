import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JFrame;




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

}
