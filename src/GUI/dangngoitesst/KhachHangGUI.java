package GUI.dangngoitesst;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import BUS.KhachHangBUS;
import DTO.KhachHangDTO;

public class KhachHangGUI extends JFrame {
    private JTextField txtTenKH, txtSoDT, txtDiaChi;
    private JButton btnXacNhan, btnChinhSua, btnDong, btnTimKiem;
    private JTable tableKhachHang;
    private DefaultTableModel tableModel;
    private KhachHangBUS khBUS;

    public KhachHangGUI() {
        khBUS = new KhachHangBUS();

        // Thiết lập tiêu đề và layout cho frame
        setTitle("Thêm/Sửa Khách Hàng");
        setLayout(new BorderLayout());

        // Panel phía trên chứa các form input
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 10)); // Thêm dòng cho nút tìm kiếm

        // Tạo các label và text field
        JLabel lblTenKH = new JLabel("Tên khách hàng:");
        txtTenKH = new JTextField();

        JLabel lblSoDT = new JLabel("Số điện thoại:");
        txtSoDT = new JTextField();

        JLabel lblDiaChi = new JLabel("Địa chỉ:");
        txtDiaChi = new JTextField();

        // Tạo các button
        btnXacNhan = new JButton("Xác nhận thêm");
        btnChinhSua = new JButton("Chỉnh sửa khách hàng");
        btnDong = new JButton("Đóng"); // Nút đóng
        btnTimKiem = new JButton("Tìm kiếm theo SĐT"); // Nút tìm kiếm theo SĐT

        // Tùy chỉnh màu và phông chữ cho các button
        btnXacNhan.setBackground(new Color(76, 175, 80));
        btnXacNhan.setForeground(Color.WHITE);
        btnChinhSua.setBackground(new Color(33, 150, 243));
        btnChinhSua.setForeground(Color.WHITE);
        btnDong.setBackground(new Color(244, 67, 54));
        btnDong.setForeground(Color.WHITE);
        btnTimKiem.setBackground(new Color(255, 193, 7));
        btnTimKiem.setForeground(Color.BLACK);

        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        btnXacNhan.setFont(buttonFont);
        btnChinhSua.setFont(buttonFont);
        btnDong.setFont(buttonFont);
        btnTimKiem.setFont(buttonFont);

        // Thêm các thành phần vào input panel
        inputPanel.add(lblTenKH);
        inputPanel.add(txtTenKH);
        inputPanel.add(lblSoDT);
        inputPanel.add(txtSoDT);
        inputPanel.add(lblDiaChi);
        inputPanel.add(txtDiaChi);
        inputPanel.add(btnXacNhan);
        inputPanel.add(btnChinhSua);
        inputPanel.add(new JLabel()); // Chèn khoảng trống
        inputPanel.add(btnDong); // Thêm nút đóng vào form
        inputPanel.add(new JLabel()); // Chèn khoảng trống
        inputPanel.add(btnTimKiem); // Thêm nút tìm kiếm vào form

        // Panel trung tâm chứa bảng hiển thị khách hàng
        tableModel = new DefaultTableModel(new Object[] { "Mã KH", "Tên khách hàng", "Số điện thoại", "Địa chỉ" }, 0);
        tableKhachHang = new JTable(tableModel);
        tableKhachHang.setFillsViewportHeight(true);

        // Cải thiện giao diện bảng
        tableKhachHang.setBackground(new Color(240, 240, 240));
        tableKhachHang.setForeground(Color.BLACK);
        tableKhachHang.setFont(new Font("Arial", Font.PLAIN, 14));
        tableKhachHang.setRowHeight(25);

        // Cải thiện header của bảng
        JTableHeader header = tableKhachHang.getTableHeader();
        header.setBackground(new Color(63, 81, 181));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setPreferredSize(new Dimension(100, 35));

        // Căn chỉnh text trong bảng (căn giữa cho các cột)
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableKhachHang.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tableKhachHang.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tableKhachHang.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tableKhachHang.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);

        JScrollPane tableScrollPane = new JScrollPane(tableKhachHang);

        // Thêm dữ liệu ban đầu vào bảng
        loadTableData();

        // Thêm inputPanel vào phía trên và table vào trung tâm của frame
        add(inputPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);

        // Thiết lập kích thước và vị trí frame
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        // Sự kiện khi nhấn nút Xác nhận thêm khách hàng
        // Sự kiện khi nhấn nút Xác nhận thêm khách hàng
        btnXacNhan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tenKH = txtTenKH.getText();
                String soDT = txtSoDT.getText();
                String diaChi = txtDiaChi.getText();

                // Kiểm tra tính hợp lệ của dữ liệu
                if (tenKH.isEmpty() || soDT.isEmpty() || diaChi.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin!", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Kiểm tra xem số điện thoại đã tồn tại hay chưa
                boolean isDuplicate = false;
                for (KhachHangDTO kh : khBUS.getAll()) {
                    if (kh.getSdt().equals(soDT)) {
                        isDuplicate = true;
                        break;
                    }
                }

                if (isDuplicate) {
                    // Hiển thị thông báo lỗi nếu số điện thoại đã tồn tại
                    JOptionPane.showMessageDialog(null, "Số điện thoại đã tồn tại! Vui lòng nhập số khác.", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    // Tạo DTO và gửi đến BUS nếu số điện thoại không trùng
                    KhachHangDTO kh = new KhachHangDTO(khBUS.listKhachHang.size() + 1, tenKH, soDT, diaChi,
                            new java.sql.Date(System.currentTimeMillis()));
                    boolean isSuccess = khBUS.add(kh);

                    if (isSuccess) {
                        JOptionPane.showMessageDialog(null, "Thêm khách hàng thành công!");
                        loadTableData(); // Cập nhật bảng
                    } else {
                        JOptionPane.showMessageDialog(null, "Thêm khách hàng thất bại!", "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Sự kiện khi nhấn nút Chỉnh sửa khách hàng
        btnChinhSua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tenKH = txtTenKH.getText();
                String soDT = txtSoDT.getText();
                String diaChi = txtDiaChi.getText();

                // Nhập mã khách hàng cần chỉnh sửa
                int makh = Integer.parseInt(JOptionPane.showInputDialog("Nhập mã khách hàng cần chỉnh sửa:"));
                KhachHangDTO kh = new KhachHangDTO(makh, tenKH, soDT, diaChi,
                        new java.sql.Date(System.currentTimeMillis()));
                boolean isSuccess = khBUS.update(kh);

                if (isSuccess) {
                    JOptionPane.showMessageDialog(null, "Chỉnh sửa khách hàng thành công!");
                    loadTableData(); // Cập nhật bảng
                } else {
                    JOptionPane.showMessageDialog(null, "Chỉnh sửa khách hàng thất bại!", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Sự kiện khi nhấn nút Tìm kiếm theo số điện thoại
        btnTimKiem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String soDT = JOptionPane.showInputDialog("Nhập số điện thoại cần tìm:");

                // Tìm kiếm khách hàng theo số điện thoại
                KhachHangDTO khachHang = null;
                for (KhachHangDTO kh : khBUS.getAll()) {
                    if (kh.getSdt().equals(soDT)) {
                        khachHang = kh;
                        break;
                    }
                }

                // Nếu tìm thấy khách hàng
                if (khachHang != null) {
                    // Hiển thị thông tin lên các trường input để người dùng có thể sửa
                    txtTenKH.setText(khachHang.getHoten());
                    txtSoDT.setText(khachHang.getSdt());
                    txtDiaChi.setText(khachHang.getDiachi());
                } else {
                    // Nếu không tìm thấy khách hàng
                    JOptionPane.showMessageDialog(null, "Không tìm thấy khách hàng với số điện thoại này!", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Sự kiện khi nhấn nút Đóng
        btnDong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Đóng cửa sổ hiện tại
            }
        });
    }

    // Hàm để load dữ liệu từ BUS vào bảng
    private void loadTableData() {
        // Xóa tất cả dữ liệu cũ trong bảng
        tableModel.setRowCount(0);

        // Lấy danh sách khách hàng từ BUS và đưa vào bảng
        for (KhachHangDTO kh : khBUS.getAll()) {
            tableModel.addRow(new Object[] { kh.getMaKH(), kh.getHoten(), kh.getSdt(), kh.getDiachi() });
        }
    }

    public static void main(String[] args) {
        new KhachHangGUI();
    }
}
