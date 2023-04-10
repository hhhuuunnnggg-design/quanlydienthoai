

package GUI.Panel;

import GUI.Main;
import GUI.Component.IntegratedSearch;
import GUI.Component.MainFunction;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import GUI.Component.PanelBorderRadius;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class PhieuXuat extends JPanel implements ActionListener{

    PanelBorderRadius box1, box2, main, functionBar, right;
    JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter;
    JTable tablePhieuXuat;
    JScrollPane scrollTablePhieuXuat;
    MainFunction mainFunction;
    IntegratedSearch search;
    JLabel lbl1, lblImage;
    DefaultTableModel tblModel;
    
    Main m;
    XuatKho xuatKho;
    
    Color BackgroundColor = new Color(245, 229, 240);

        public PhieuXuat(Main m) {
        initComponent();
        this.m = m;
    }
    
    private void initComponent() {

        this.setBackground(BackgroundColor);
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);

        tablePhieuXuat = new JTable();
        scrollTablePhieuXuat = new JScrollPane();
        tblModel = new DefaultTableModel();
        String[] header = new String[]{"Mã khu vực kho", "Tên khu vực", "Mã kho hàng"};
        tblModel.setColumnIdentifiers(header);
        tablePhieuXuat.setModel(tblModel);
        scrollTablePhieuXuat.setViewportView(tablePhieuXuat);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tablePhieuXuat.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tablePhieuXuat.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tablePhieuXuat.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        this.setBackground(BackgroundColor);
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);

        pnlBorder1 = new JPanel();
        pnlBorder1.setPreferredSize(new Dimension(0, 20));
        pnlBorder1.setBackground(BackgroundColor);
        this.add(pnlBorder1, BorderLayout.NORTH);

        pnlBorder2 = new JPanel();
        pnlBorder2.setPreferredSize(new Dimension(0, 20));
        pnlBorder2.setBackground(BackgroundColor);
        this.add(pnlBorder2, BorderLayout.SOUTH);

        pnlBorder3 = new JPanel();
        pnlBorder3.setPreferredSize(new Dimension(20, 0));
        pnlBorder3.setBackground(BackgroundColor);
        this.add(pnlBorder3, BorderLayout.EAST);

        pnlBorder4 = new JPanel();
        pnlBorder4.setPreferredSize(new Dimension(20, 0));
        pnlBorder4.setBackground(BackgroundColor);
        this.add(pnlBorder4, BorderLayout.WEST);

        contentCenter = new JPanel();
        contentCenter.setPreferredSize(new Dimension(1100, 600));
        contentCenter.setBackground(BackgroundColor);
        contentCenter.setLayout(new BorderLayout(20, 20));
        this.add(contentCenter, BorderLayout.CENTER);

        functionBar = new PanelBorderRadius();
        functionBar.setPreferredSize(new Dimension(0, 100));
        functionBar.setLayout(new GridLayout(1, 2, 50, 0));
        functionBar.setBorder(new EmptyBorder(10, 10, 10, 10));

        mainFunction = new MainFunction();
        functionBar.add(mainFunction);

        search = new IntegratedSearch(new String[]{"Tất cả"});
        functionBar.add(search);

        contentCenter.add(functionBar, BorderLayout.NORTH);

        main = new PanelBorderRadius();
        BoxLayout boxly = new BoxLayout(main, BoxLayout.Y_AXIS);
        main.setLayout(boxly);
        main.setBorder(new EmptyBorder(20, 20, 20, 20));
        contentCenter.add(main, BorderLayout.CENTER);
        

        scrollTablePhieuXuat.setViewportView(tablePhieuXuat);

        main.add(scrollTablePhieuXuat);

        right = new PanelBorderRadius();
        right.setPreferredSize(new Dimension(400, 0));
        right.setLayout(new FlowLayout(1, 15, 40));
        contentCenter.add(right, BorderLayout.EAST);
        
          //Add Event MouseListener
        mainFunction.btnAdd.addActionListener(this);
    }


    
        @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mainFunction.btnAdd) {


                xuatKho = new XuatKho(this.m);
                m.MainContent.removeAll();
                m.MainContent.add(xuatKho).setVisible(true);
                m.MainContent.repaint();
                m.MainContent.validate();

        }
    }

}

