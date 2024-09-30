package GUI.Panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import BUS.NhaCungCapBUS;
import DAO.NhaCungCapDAO;
import DTO.NhaCungCapDTO;
import GUI.Main;
import GUI.Component.IntegratedSearch;
import GUI.Component.MainFunction;
import GUI.Component.PanelBorderRadius;
import GUI.Dialog.NhaCungCapDialog;
import helper.JTableExporter;
import helper.Validation;

public final class NhaCungCap extends JPanel implements ActionListener, ItemListener {

    PanelBorderRadius main, functionBar;
    JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter;
    JTable tableNhaCungCap;
    JScrollPane scrollTableSanPham;
    MainFunction mainFunction;
    IntegratedSearch search;
    JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this);
    Color BackgroundColor = new Color(240, 247, 250);
    DefaultTableModel tblModel;
    JButton btnStopHopTac, btnAgainHopTac;
    Main m;
    public NhaCungCapBUS nccBUS = new NhaCungCapBUS();
    public ArrayList<NhaCungCapDTO> listncc = nccBUS.getAll();

    private void initComponent() {
        // Set model table
        tableNhaCungCap = new JTable();
        scrollTableSanPham = new JScrollPane();
        tblModel = new DefaultTableModel();
        String[] header = new String[] { "Mã NCC", "Tên nhà cung cấp", "Địa chỉ", "Email", "Số điện thoại..." };
        tblModel.setColumnIdentifiers(header);
        tableNhaCungCap.setModel(tblModel);
        tableNhaCungCap.setFocusable(false);
        scrollTableSanPham.setViewportView(tableNhaCungCap);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        TableColumnModel columnModel = tableNhaCungCap.getColumnModel();
        columnModel.getColumn(0).setCellRenderer(centerRenderer);
        columnModel.getColumn(0).setPreferredWidth(2);
        columnModel.getColumn(2).setPreferredWidth(300);
        columnModel.getColumn(4).setCellRenderer(centerRenderer);

        this.setBackground(BackgroundColor);
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);

        // Tạo các panel để tạo khoảng cách
        pnlBorder1 = new JPanel();
        pnlBorder1.setPreferredSize(new Dimension(0, 10));
        pnlBorder1.setBackground(BackgroundColor);
        this.add(pnlBorder1, BorderLayout.NORTH);

        pnlBorder2 = new JPanel();
        pnlBorder2.setPreferredSize(new Dimension(0, 10));
        pnlBorder2.setBackground(BackgroundColor);
        this.add(pnlBorder2, BorderLayout.SOUTH);

        pnlBorder3 = new JPanel();
        pnlBorder3.setPreferredSize(new Dimension(10, 0));
        pnlBorder3.setBackground(BackgroundColor);
        this.add(pnlBorder3, BorderLayout.EAST);

        pnlBorder4 = new JPanel();
        pnlBorder4.setPreferredSize(new Dimension(10, 0));
        pnlBorder4.setBackground(BackgroundColor);
        this.add(pnlBorder4, BorderLayout.WEST);

        contentCenter = new JPanel();
        contentCenter.setPreferredSize(new Dimension(1100, 600));
        contentCenter.setBackground(BackgroundColor);
        contentCenter.setLayout(new BorderLayout(10, 10));
        this.add(contentCenter, BorderLayout.CENTER);

        // Tạo thanh chức năng
        functionBar = new PanelBorderRadius();
        functionBar.setPreferredSize(new Dimension(0, 100));
        functionBar.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Thay đổi layout
        functionBar.setBorder(new EmptyBorder(10, 10, 10, 10));

        String[] action = { "update", "delete", "detail", "import", "export" };
        mainFunction = new MainFunction(m.user.getManhomquyen(), "nhacungcap", action);
        for (String ac : action) {
            mainFunction.btn.get(ac).addActionListener(this);
        }
        functionBar.add(mainFunction);

        // Tạo nút "Danh sách ngưng hợp tác"
        btnStopHopTac = new JButton("Danh sách ngưng hợp tác");
        btnStopHopTac.setPreferredSize(new Dimension(180, 40)); // Đặt kích thước
        btnStopHopTac.setMaximumSize(new Dimension(150, 40)); // Đặt kích thước tối đa
        btnStopHopTac.setBackground(Color.green);
        btnStopHopTac.addActionListener(this);
        functionBar.add(btnStopHopTac);
        // tạo nút bấm hợp tác lại nè
        btnAgainHopTac = new JButton("Hợp tác lại");
        btnAgainHopTac.setVisible(false);
        btnAgainHopTac.setPreferredSize(new Dimension(100, 40)); // Đặt kích thước
        btnAgainHopTac.setMaximumSize(new Dimension(150, 40)); // Đặt kích thước tối đa
        btnAgainHopTac.setBackground(Color.orange);
        btnAgainHopTac.addActionListener(this);
        functionBar.add(btnAgainHopTac);
        // Tạo thanh tìm kiếm
        search = new IntegratedSearch(
                new String[] { "Tất cả", "Mã nhà cung cấp!", "Tên nhà cung cấp", "Địa chỉ", "Email", "Số điện thoại" });
        search.cbxChoose.addItemListener(this);
        search.txtSearchForm.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String type = (String) search.cbxChoose.getSelectedItem();
                String txt = search.txtSearchForm.getText();
                listncc = nccBUS.search(txt, type);
                loadDataTable(listncc);
            }
        });

        search.btnReset.addActionListener(this);
        functionBar.add(search);
        contentCenter.add(functionBar, BorderLayout.NORTH);

        // Phần thống kê bảng biểu
        main = new PanelBorderRadius();
        BoxLayout boxly = new BoxLayout(main, BoxLayout.Y_AXIS);
        main.setLayout(boxly);
        contentCenter.add(main, BorderLayout.CENTER);
        main.add(scrollTableSanPham);
    }

    public NhaCungCap(Main m) {
        this.m = m;
        initComponent();
        tableNhaCungCap.setDefaultEditor(Object.class, null);
        loadDataTable(listncc);
    }

    public void loadDataTable(ArrayList<NhaCungCapDTO> result) {
        tblModel.setRowCount(0);
        for (NhaCungCapDTO ncc : result) {
            tblModel.addRow(new Object[] {
                    ncc.getMancc(), ncc.getTenncc(), ncc.getDiachi(), ncc.getEmail(), ncc.getSdt()
            });
        }
    }

    public void openFile(String file) {
        try {
            File path = new File(file);
            Desktop.getDesktop().open(path);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void importExcel() {
        File excelFile;
        FileInputStream excelFIS = null;
        BufferedInputStream excelBIS = null;
        XSSFWorkbook excelJTableImport = null;
        ArrayList<DTO.NhaCungCapDTO> listExcel = new ArrayList<DTO.NhaCungCapDTO>();
        JFileChooser jf = new JFileChooser();
        int result = jf.showOpenDialog(null);
        jf.setDialogTitle("Open file");
        Workbook workbook = null;
        int k = 0;
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                excelFile = jf.getSelectedFile();
                excelFIS = new FileInputStream(excelFile);
                excelBIS = new BufferedInputStream(excelFIS);
                excelJTableImport = new XSSFWorkbook(excelBIS);
                XSSFSheet excelSheet = excelJTableImport.getSheetAt(0);
                for (int row = 1; row <= excelSheet.getLastRowNum(); row++) {
                    int check = 1;
                    XSSFRow excelRow = excelSheet.getRow(row);
                    int id = NhaCungCapDAO.getInstance().getAutoIncrement();
                    String tenNCC = excelRow.getCell(0).getStringCellValue();
                    String diachi = excelRow.getCell(1).getStringCellValue();
                    String email = excelRow.getCell(2).getStringCellValue();
                    String sdt = excelRow.getCell(3).getStringCellValue();
                    if (Validation.isEmpty(tenNCC) || Validation.isEmpty(email)
                            || !Validation.isEmail(email) || Validation.isEmpty(sdt) || !isPhoneNumber(sdt)
                            || sdt.length() != 10 || Validation.isEmpty(diachi)) {
                        check = 0;
                    }
                    if (check == 0) {
                        k += 1;
                    } else {
                        nccBUS.add(new NhaCungCapDTO(id, tenNCC, diachi, email, sdt));
                    }
                }
                if (k != 0) {
                    JOptionPane.showMessageDialog(this, "Những dữ liệu không chuẩn không được thêm vào");
                } else {
                    JOptionPane.showMessageDialog(this, "Nhập dữ liệu thành công");
                }
            } catch (FileNotFoundException ex) {
                System.out.println("Lỗi đọc file");
            } catch (IOException ex) {
                System.out.println("Lỗi đọc file");
            }
        }

        loadDataTable(listncc);
    }

    public int getRowSelected() {
        int index = tableNhaCungCap.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp");
        }
        return index;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mainFunction.btn.get("create")) {
            NhaCungCapDialog dvtDialog = new NhaCungCapDialog(this, owner, "Thêm nhà cung cấp", true, "create");
        } else if (e.getSource() == mainFunction.btn.get("update")) {
            int index = getRowSelected();
            if (index != -1) {
                NhaCungCapDialog nccDialog = new NhaCungCapDialog(this, owner, "Chỉnh sửa nhà cung cấp", true, "update",
                        listncc.get(index));
            }
        } else if (e.getSource() == mainFunction.btn.get("delete")) {
            int index = getRowSelected();
            if (index != -1) {
                int input = JOptionPane.showConfirmDialog(null,
                        "Bạn có chắc chắn muốn xóa nhà cung cấp!", "Xóa nhà cung cấp",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (input == 0) {
                    nccBUS.delete(listncc.get(index), index);
                    loadDataTable(listncc);
                }
            }
        } else if (e.getSource() == mainFunction.btn.get("detail")) {
            int index = getRowSelected();
            if (index != -1) {
                NhaCungCapDialog nccDialog = new NhaCungCapDialog(this, owner, "Chi tiết nhà cung cấp", true, "view",
                        listncc.get(index));
            }
        } else if (e.getSource() == search.btnReset) {
            search.txtSearchForm.setText("");
            listncc = nccBUS.getAll();
            loadDataTable(listncc);
        } else if (e.getSource() == mainFunction.btn.get("import")) {
            importExcel();
        } else if (e.getSource() == mainFunction.btn.get("export")) {
            try {
                JTableExporter.exportJTableToExcel(tableNhaCungCap);
            } catch (IOException ex) {
                Logger.getLogger(NhaCungCap.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (e.getSource() == btnStopHopTac) {
            listncc = nccBUS.getAllStopped();
            if (listncc != null && !listncc.isEmpty()) {
                btnAgainHopTac.setVisible(true);
                loadDataTable(listncc); // Tải dữ liệu vào bảng
            } else {
                btnAgainHopTac.setVisible(false);
                JOptionPane.showMessageDialog(this, "Không có danh sách ngưng hợp tác với nhà cung cấp");
            }

        } else if (e.getSource() == btnAgainHopTac) {
            int index = getRowSelected(); // Lấy chỉ số hàng được chọn
            if (index != -1) {
                int input = JOptionPane.showConfirmDialog(null,
                        "Bạn có chắc chắn muốn hợp tác lại với nhà cung cấp này!", "Hợp tác lại",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (input == JOptionPane.OK_OPTION) {
                    // Khôi phục trạng thái hợp tác
                    boolean restored = nccBUS.restore(listncc.get(index), index); // Giả sử restore trả về true/false
                    if (restored) {
                        // Tải lại danh sách nhà cung cấp từ cơ sở dữ liệu
                        listncc = nccBUS.getAll(); // Hoặc getAllActive nếu cần thiết
                        // Cập nhật giao diện người dùng
                        SwingUtilities.invokeLater(() -> {
                            loadDataTable(listncc);
                            JOptionPane.showMessageDialog(null, "Hợp tác lại thành công!");
                        });
                    } else {
                        JOptionPane.showMessageDialog(null, "Khôi phục không thành công.");
                    }
                }
            }
            System.out.println("Bạn đã click vào hợp tác lại");
        }
    }

    // nccBUS.restore(listncc.get(index), index);
    public static boolean isPhoneNumber(String str) {
        // Loại bỏ khoảng trắng và dấu ngoặc đơn nếu có
        str = str.replaceAll("\\s+", "").replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("\\-", "");

        // Kiểm tra xem chuỗi có phải là một số điện thoại hợp lệ hay không
        if (str.matches("\\d{10}")) { // Kiểm tra số điện thoại 10 chữ số
            return true;
        } else if (str.matches("\\d{3}-\\d{3}-\\d{4}")) { // Kiểm tra số điện thoại có dấu gạch ngang
            return true;
        } else if (str.matches("\\(\\d{3}\\)\\d{3}-\\d{4}")) { // Kiểm tra số điện thoại có dấu ngoặc đơn
            return true;
        } else {
            return false; // Trả về false nếu chuỗi không phải là số điện thoại hợp lệ
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        String type = (String) search.cbxChoose.getSelectedItem();
        String txt = search.txtSearchForm.getText();
        listncc = nccBUS.search(txt, type);
        loadDataTable(listncc);
    }

}
