import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.awt.Adjustable;

public class checkrecords extends JFrame implements ActionListener, TableModelListener{

    JButton back = new JButton();
    JButton ApplyEdit = new  JButton();
    JLabel title = new JLabel();
    JPanel main = new JPanel();
    table table = new table();
    boolean useredit = true;
    HashMap<Integer,List<List<Integer>>> changes = new HashMap<Integer, List<List<Integer>>>();
    //idea: in the tablechanged method, just store changes in an array, come up with an efficient alghoritm that will make the program fast, either use array, or dictionary or map, and then whenever user clicks apply, then you update database, and when updating always start updating changes from lower rowes and then do the higher rows. 
    public checkrecords(){
        this.setTitle("Records");
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setSize(1200, 1000);
        this.setResizable(false);
        
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);
        
        main.setSize(1150, 1000);
        main.setOpaque(true);
        main.setBackground(new Color(220, 228, 201));
        main.setLayout(null);
        main.setLayout(null);
        this.add(main);
        
        back.addActionListener(this);
        ApplyEdit.addActionListener(this);
        ApplyEdit.setEnabled(false);
        
        helpingMethods.setLabel(title, "Records", Color.black, 490, 20, 300, 70, 45, main);
        helpingMethods.setButton(back, "Back", Color.black, new Color(224, 123, 57), 30, 30, 80, 40, 20, main);
        helpingMethods.setButton(ApplyEdit, "Apply Edit", Color.black, new Color(224, 123, 57), 950, 30, 140, 40, 20, main);
        
        JScrollPane jsp = new JScrollPane(table);
        table.getModel().addTableModelListener(this);
        table.removeColumn(table.getColumnModel().getColumn(11));
        table.removeColumn(table.getColumnModel().getColumn(11));
        table.getTableHeader().setReorderingAllowed(false);
        jsp.setBounds(50, 100, 1050, 800);
        jsp.setBackground(new Color(220, 228, 201));
        scrollToBottom(jsp);
        main.add(jsp);

        this.setVisible(true);
    }





    @Override
    public void actionPerformed(ActionEvent e){
        JButton src = (JButton)e.getSource();

        if (src.equals(back)){
            this.dispose();
        }

        else if(src.equals(ApplyEdit)){
            ApplyEdit.setEnabled(false);
            useredit = false;
            UpdateDBandTable();
        }


    }





    @Override
    public void tableChanged(TableModelEvent e) {
        // TODO Auto-generated method stub
        if (useredit){
            ApplyEdit.setEnabled(true);
            List<Integer> change = new ArrayList<>();
            change.add(e.getColumn());
            change.add(e.getFirstRow());
            Integer num =(Integer) table.getValueAt(e.getFirstRow(), 0);
            List<List<Integer>> value = null;
            if (changes.containsKey(num)){
                value = changes.get(num);
            }
            else{
                value = new ArrayList<>();
            }
            value.add(change);
            changes.put(num, value);
    }
}

    private void UpdateDBandTable(){
    Object[] keys = changes.keySet().toArray();
    int next = 0;
    boolean noNeedForUpdate = false;
    for (int i = 0; i < keys.length; i++){
        List<List<Integer>> change = changes.get(keys[i]);
        int row = 0;
        int col = 0;
        if (i == keys.length - 1){
            int lastrow = table.getRowCount() - 1;
            if (i == lastrow){
                noNeedForUpdate = true;
            }
            else{
                next = lastrow + 1;
            }
        }
        else{
            next = (Integer)keys[i + 1];
        }
        for (int j = 0; j < change.size(); j++){
             List<Integer> temp = change.get(j);
              row = temp.get(1);
              col = temp.get(0);
             if (col == 1 || col == 4){
                int finalbalance = Updaterow(row, col);
                if (!noNeedForUpdate){
                    UpdateRestOfTable(row, next, finalbalance);
                }
                break;
             }
        }
        //apply the change to DB here
        List<Integer> currbalance = new ArrayList<>();
        currbalance.add(7);
        currbalance.add(row);
        List<Integer> Final = new ArrayList<>();
        Final.add(9);
        Final.add(row);
        change.add(currbalance);
        change.add(Final);
        transferchangetoDB((Integer)keys[i], change);
        changes.remove((Integer)keys[i]);
    }
    useredit = true;
    }

    private void UpdateRestOfTable(int row,int next, int finalbalance){
        //until you get to next, update previous balance and final balance and apply the change to database for each row.
        for (int i = row + 1; i < next; i++){
            table.setValueAt(finalbalance, i, 8);
            int currbalance = (Integer)table.getValueAt(i, 7);
            finalbalance = finalbalance + currbalance;
            table.setValueAt(finalbalance, i, 9);
            List<List<Integer>> changesForThisRow = new ArrayList<>();
            List<Integer> change1 = new ArrayList<>();
            change1.add(8);
            change1.add(i);
            List<Integer> change2 = new ArrayList<>();
            change2.add(9);
            change2.add(i);
            changesForThisRow.add(change1);
            changesForThisRow.add(change2);
            transferchangetoDB(i + 1, changesForThisRow);
        }
    }


    private int Updaterow(int row, int col){
        int newbalance = 0;
        if (col == 1){
            newbalance = (Integer)table.getValueAt(row, col) - (Integer)table.getValueAt(row, 4);
        }
        else{
            newbalance = (Integer)table.getValueAt(row, 1) - (Integer)table.getValueAt(row, col);
        }
        table.setValueAt(newbalance, row, 7);
        int previousbalance = (Integer)table.getValueAt(row, 8);
        int finalbalance = newbalance + previousbalance;
        table.setValueAt(finalbalance, row, 9);
        return finalbalance;
    }

    private void transferchangetoDB(int row, List<List<Integer>> changesForRow){
        Connection con = helpingMethods.establishConnection();
        String[] cols = {"number", "counts" , "earned", "srcearn", "dateEarned", "Spent", "srcspent", "datespent", "balance", "previousbalance", "Finalbalance", "fontcolor", "backgroundcolor"};
        Object[] vals = new Object[changesForRow.size()];
        try {
            String updatestr = "UPDATE records SET " + cols[changesForRow.get(0).get(0)] + "=?";
            int firstrow = changesForRow.get(0).get(1);
            int firstcol = changesForRow.get(0).get(0);
            vals[0] = table.getValueAt(firstrow,firstcol);
            for (int i = 1; i < changesForRow.size(); i++){
                List<Integer> temp = changesForRow.get(i);            
                updatestr += ","+ cols[temp.get(0)] + "=?";
                vals[i] = table.getValueAt(temp.get(1), temp.get(0));
            }
            updatestr += " WHERE number=" + row;
            PreparedStatement stmnt = con.prepareStatement(updatestr);
            for (int i = 0; i < vals.length; i++){
                if (vals[i] instanceof String){
                    stmnt.setString(i + 1, (String)vals[i]);
                }
                else{
                    stmnt.setInt(i + 1, (Integer)vals[i]);
                }
            }
            stmnt.execute();
            stmnt.close();
        } catch (Exception e) {
            // TODO: handle exception
            JOptionPane.showMessageDialog(null, "There was a problem while transfering changes:" + e.getMessage());
        }
    
    }


    private void scrollToBottom(JScrollPane scrollPane) {
        JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
        AdjustmentListener downScroller = new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                Adjustable adjustable = e.getAdjustable();
                adjustable.setValue(adjustable.getMaximum());
                verticalBar.removeAdjustmentListener(this);
            }
        };
        verticalBar.addAdjustmentListener(downScroller);
    }

    

}