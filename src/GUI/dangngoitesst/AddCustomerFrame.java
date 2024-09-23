// package GUI.dangngoitesst;

// import DTO.KhachHangDTO;
// import GUI.Panel.KhachHang;

// import javax.swing.*;
// import java.awt.*;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;
// import java.util.ArrayList;
// import java.util.Date;

// public class AddCustomerFrame extends JFrame {

// private JTextField txtTenKH, txtSDT, txtDiaChi;
// private JButton btnThem;
// private ArrayList<KhachHangDTO> danhSachKhachHang;
// private KhachHang khachHangPanel; // Tham chiếu đến class KhachHang

// public AddCustomerFrame(KhachHang khachHangPanel, ArrayList<KhachHangDTO>
// danhSachKhachHang) {
// this.khachHangPanel = khachHangPanel; // Gán tham chiếu
// this.danhSachKhachHang = danhSachKhachHang;
// initUI();
// }

// private void initUI() {
// setTitle("Thêm khách hàng");
// setSize(400, 300);
// setLayout(new GridLayout(4, 2));

// JLabel lblTenKH = new JLabel("Tên khách hàng:");
// txtTenKH = new JTextField();
// JLabel lblSDT = new JLabel("Số điện thoại:");
// txtSDT = new JTextField();
// JLabel lblDiaChi = new JLabel("Địa chỉ:");
// txtDiaChi = new JTextField();
// btnThem = new JButton("Thêm");

// add(lblTenKH);
// add(txtTenKH);
// add(lblSDT);
// add(txtSDT);
// add(lblDiaChi);
// add(txtDiaChi);
// add(btnThem);

// btnThem.addActionListener(new ActionListener() {
// @Override
// public void actionPerformed(ActionEvent e) {
// // Lấy dữ liệu từ các trường nhập liệu
// String tenKH = txtTenKH.getText();
// String sdt = txtSDT.getText();
// String diaChi = txtDiaChi.getText();

// // Kiểm tra nếu thông tin bị bỏ trống
// if (tenKH.isEmpty() || sdt.isEmpty() || diaChi.isEmpty()) {
// JOptionPane.showMessageDialog(AddCustomerFrame.this, "Vui lòng nhập đầy đủ
// thông tin!", "Lỗi",
// JOptionPane.ERROR_MESSAGE);
// return;
// }

// // Tạo đối tượng KhachHangDTO
// KhachHangDTO khachHang = new KhachHangDTO(danhSachKhachHang.size() + 1,
// tenKH, sdt, diaChi, new Date());

// // Thêm khách hàng vào danh sách
// danhSachKhachHang.add(khachHang);

// // Cập nhật bảng trong KhachHang
// khachHangPanel.updateCustomerList(danhSachKhachHang);

// // Thông báo thành công
// JOptionPane.showMessageDialog(AddCustomerFrame.this,
// "Khách hàng " + tenKH + " đã được thêm thành công!");

// // Xóa dữ liệu sau khi thêm
// txtTenKH.setText("");
// txtSDT.setText("");
// txtDiaChi.setText("");
// }
// });
// }
// }
