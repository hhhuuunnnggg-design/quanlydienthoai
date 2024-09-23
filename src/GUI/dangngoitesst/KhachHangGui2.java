package GUI.dangngoitesst;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import BUS.KhachHangBUS;

public class KhachHangGui2 extends JFrame {
    private JTextField txtTenkh, txtSoDt, txtDiaChi;
    private JButton btnXacNhan, btnDong, btnChinhsua;
    private DefaultTableModel tableModel;
    private KhachHangBUS khachHangBUS;

    public KhachHangGui2() {
        khachHangBUS = new KhachHangBUS();
        setTitle("Thêm/Sửa Khách Hàng");
        setLayout(new BorderLayout(10, 10));

        // phần thông tin ở trên
        JPanel inputJPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        // lbl và input
        JLabel lbltenkh = new JLabel("Tên khách hàng");
        txtTenkh = new JTextField();
        JLabel lblsdt = new JLabel("Số điện thoại");
        txtSoDt = new JTextField();
        JLabel lbldiachi = new JLabel("Địa chỉ");
        txtDiaChi = new JTextField();

        inputJPanel.add(lbltenkh);
        inputJPanel.add(txtTenkh);
        inputJPanel.add(lblsdt);
        inputJPanel.add(txtSoDt);
        inputJPanel.add(lbldiachi);
        inputJPanel.add(txtDiaChi);

        // phần thông tin ở thân
        JPanel btnChucNang = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Đổi thành FlowLayout
        btnXacNhan = new JButton("Xác nhận thêm");
        btnChinhsua = new JButton("Sửa thông tin");
        btnDong = new JButton("Đóng");

        // Đặt kích thước cho các nút
        btnXacNhan.setPreferredSize(new Dimension(150, 40)); // Kích thước nút lớn hơn
        btnChinhsua.setPreferredSize(new Dimension(150, 40));
        btnDong.setPreferredSize(new Dimension(150, 40));

        btnChucNang.add(btnXacNhan);
        btnChucNang.add(btnChinhsua);
        btnChucNang.add(btnDong);

        // Panel cho phía Bắc (phần nhập thông tin)
        add(inputJPanel, BorderLayout.NORTH);

        // Panel cho phía Nam (màu đỏ)
        JPanel southPanel = new JPanel();
        southPanel.setBackground(Color.RED); // màu đỏ
        southPanel.setPreferredSize(new Dimension(400, 50)); // Giảm kích thước phần Nam
        add(southPanel, BorderLayout.SOUTH);

        // Panel cho trung tâm (các nút chức năng)
        add(btnChucNang, BorderLayout.CENTER);

        // Thiết lập cửa sổ
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Căn giữa màn hình
        setVisible(true);
    }

    public static void main(String[] args) {
        new KhachHangGui2();
    }
}
