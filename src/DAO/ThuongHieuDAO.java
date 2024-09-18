/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import DTO.ThuocTinhSanPham.ThuongHieuDTO;
import config.JDBCUtil;

public class ThuongHieuDAO implements DAOinterface<ThuongHieuDTO> {
    public static ThuongHieuDAO getInstance() {
        return new ThuongHieuDAO();
    }

    @Override
    public int insert(ThuongHieuDTO t) {
        int result = 0;
        try {
            // tạo kết nối với cơ sở dữ liệu
            Connection con = (Connection) JDBCUtil.getConnection();
            // sql
            String sql = "INSERT INTO `thuonghieu`(`tenthuonghieu`) VALUES (?)";
            // tạo prepareStatement để truyền giá trị sql vào
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            // chèn dữ liệu vào
            pst.setString(1, t.getTenthuonghieu());
            // stament update
            result = pst.executeUpdate();
            // ngắt kết nối
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(ThuongHieuDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int update(ThuongHieuDTO t) {
        int result = 0;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "UPDATE `thuonghieu` SET`tenthuonghieu`=? WHERE `mathuonghieu`=?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, t.getTenthuonghieu());
            pst.setInt(2, t.getMathuonghieu());
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(ThuongHieuDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int delete(String t) {
        int result = 0;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "UPDATE `thuonghieu` SET `trangthai` = 0 WHERE `mathuonghieu`= ?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, t);
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(ThuongHieuDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public ArrayList<ThuongHieuDTO> selectAll() {
        ArrayList<ThuongHieuDTO> result = new ArrayList<ThuongHieuDTO>();
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT * FROM thuonghieu WHERE `trangthai`=1";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while (rs.next()) {
                int mathuonghieu = rs.getInt("mathuonghieu");
                String tenthuonghieu = rs.getString("tenthuonghieu");

                ThuongHieuDTO lh = new ThuongHieuDTO(mathuonghieu, tenthuonghieu);
                result.add(lh);
            }
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
        }
        return result;
    }

    @Override
    public ThuongHieuDTO selectById(String t) {
        ThuongHieuDTO result = null;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT * FROM thuonghieu WHERE mathuonghieu=?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, t);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while (rs.next()) {
                int mathuonghieu = rs.getInt("mathuonghieu");
                String tenloaihang = rs.getString("tenthuonghieu");
                result = new ThuongHieuDTO(mathuonghieu, tenloaihang);
            }
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
        }
        return result;
    }

    @Override
    public int getAutoIncrement() {
        int result = -1;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT `AUTO_INCREMENT` FROM  INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'quanlikhohang' AND   TABLE_NAME   = 'thuonghieu'";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs2 = pst.executeQuery(sql);
            if (!rs2.isBeforeFirst()) {
                System.out.println("No data");
            } else {
                while (rs2.next()) {
                    result = rs2.getInt("AUTO_INCREMENT");

                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ThuongHieuDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

}
