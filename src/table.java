import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class table extends JTable{
    
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
        int[] widths = {35, 42, 120, 120, 100, 120, 120, 120, 90, 90,90,90,90};
        for (int i = 0; i < widths.length; i++){
            this.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
        }
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int col){
        Component c = super.prepareRenderer(renderer, row, col);
        Color bg = Color.decode((String)this.getModel().getValueAt(row, 12));
        c.setBackground(bg);
        c.setForeground(this.getModel().getValueAt(row, 11) == "black" ? Color.black:Color.white);

        return c;
    }
}



class Mymodel extends AbstractTableModel{
    private String[] headers = {"Num", "Count", "Earnings", "Source Of Earnings", "Date Of Earnings", "Spendings", "Source Of Spendings", "Date Of Spendings", "Balance", "Prev Balance", "Final Balance", "FontColor", "BackgroundColor"};
    private Object[][] data = getData();

    public int getColumnCount(){
        return headers.length;
    }

    public int getRowCount(){
        return data.length;
    }

    public void setRowColor(int row, Color c){
        fireTableRowsUpdated(row, row);
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
        if (col < 2 || col > 7){
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
                int count = rs.getInt("counts");
                int earned = rs.getInt("earned");
                String srcearn = rs.getString("srcearn");
                String dateearn = rs.getString("dateEarned");
                int spent = rs.getInt("spent");
                String srcspent = rs.getString("srcspent");
                String datespent = rs.getString("datespent");
                int balance = rs.getInt("balance");
                int previousbalance = rs.getInt("previousbalance");
                int finalbalance = rs.getInt("Finalbalance");
                String fontcolor = rs.getString("fontcolor");
                String background = rs.getString("backgroundcolor");
                res[index] = new Object[]{number, count, earned, srcearn, dateearn, spent, srcspent, datespent, balance, previousbalance, finalbalance, fontcolor, background};
                index++;
            }
        } catch (SQLException e1) {
            // TODO: handle exception
            e1.printStackTrace();
        }
        
        return res;
    }
}







