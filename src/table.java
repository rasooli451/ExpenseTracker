import java.awt.Color;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import com.mysql.cj.protocol.Resultset;
import javax.swing.table.DefaultTableCellRenderer;

public class table extends JTable implements TableModelListener{
    
    public table(){
        super(new Mymodel());
        this.setPreferredScrollableViewportSize(new Dimension(900, 800));
        this.setFillsViewportHeight(true);
        this.setBackground(new Color(220, 228, 201));
        this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        this.setcolumnsize();
        DefaultTableCellRenderer centerRend = new DefaultTableCellRenderer();
        centerRend.setHorizontalAlignment(JLabel.CENTER);
        this.setDefaultRenderer(String.class, centerRend);
        this.setDefaultRenderer(Integer.class, centerRend);
    }

    private void setcolumnsize(){
        int[] widths = {35, 70, 150, 150, 70, 150, 150, 90, 90, 90};
        for (int i = 0; i < widths.length; i++){
            this.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
        }
    }

    @Override
    public void tableChanged(TableModelEvent e){
        
    }
}



class Mymodel extends AbstractTableModel{
    private String[] headers = {"Num", "Earnings", "Source Of Earnings", "Date Of Earnings", "Spendings", "Source Of Spendings", "Date Of Spendings", "Balance", "Prev Balance", "Final Balance"};
    private Object[][] data = getData();
    public int getColumnCount(){
        return headers.length;
    }

    public int getRowCount(){
        return data.length;
    }

    public String getColumnName(int col){
        return headers[col];
    }

    public Object getValueAt(int row, int column){
        return data[row][column];
    }
    
    public Class getColumnClass(int c){
        return getValueAt(0, c).getClass();
    }
    public boolean isCellEditable(int row, int col){
        if (col == 0 || col > 6){
            return false;
        }
        return true;
    }

    public void setValueAt(Object value, int row, int col){
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }

    public Object[][] getData(){
        Connection con = helpingMethods.establishConnection();
        Object[][] res = null;
        try {
            Statement smt = con.createStatement();
            ResultSet rs = smt.executeQuery("SELECT COUNT(*) FROM records");    
            rs.next();
            int length = Integer.parseInt(rs.getString("COUNT(*)"));
            res = new Object[length][10];
            rs = smt.executeQuery("SELECT * FROM records");
            int index = 0;
            while (rs.next()){
                int number = rs.getInt("number");
                int earned = rs.getInt("earned");
                String srcearn = rs.getString("srcearn");
                String dateearn = rs.getString("dateEarned");
                int spent = rs.getInt("spent");
                String srcspent = rs.getString("srcspent");
                String datespent = rs.getString("datespent");
                int balance = rs.getInt("balance");
                int previousbalance = rs.getInt("previousbalance");
                int finalbalance = rs.getInt("Finalbalance");
                res[index] = new Object[]{number,earned, srcearn, dateearn, spent, srcspent, datespent, balance, previousbalance, finalbalance};
                index++;
            }
        } catch (SQLException e1) {
            // TODO: handle exception
            e1.printStackTrace();
        }
        
        return res;
    }
}







