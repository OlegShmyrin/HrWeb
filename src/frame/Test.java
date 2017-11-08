package frame;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class Test extends JFrame implements ListSelectionListener, ActionListener{

    private JList list;
    private JButton add = new JButton("Add");
    private JButton del = new JButton("Del");

    public Test() {
        list=new JList();
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        DefaultListModel defaultListModel = new DefaultListModel();
        list.setModel(defaultListModel);
        list.addListSelectionListener(this);

        add.addActionListener(this);
        del.addActionListener(this);

        add.setName("Adding");
        del.setName("Delete");

        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1,2));
        buttons.add(add);
        buttons.add(del);

        getContentPane().add(new JScrollPane(list), BorderLayout.CENTER);
        getContentPane().add(buttons,BorderLayout.SOUTH);

        setBounds(100,100,200,200);

    }
    public static void main (String [] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Test t =new Test();
            t.setDefaultCloseOperation(t.EXIT_ON_CLOSE);
            t.setVisible(true);
            }
        });
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

        if(!e.getValueIsAdjusting()) System.out.println("New index: " +list.getSelectedIndex());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DefaultListModel dlm = (DefaultListModel) list.getModel();
        JButton sender =(JButton) e.getSource();
        if(sender.getName().equals("Adding")){
            dlm.addElement(String.valueOf(dlm.getSize()));
        }
        if(sender.getName().equals("Delete")&&list.getSelectedIndex()>=0){
            int index = list.getSelectedIndex();
            dlm.remove(list.getSelectedIndex());
            list.setSelectedIndex(index-1);
        }

    }
}
