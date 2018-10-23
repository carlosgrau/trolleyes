package net.daw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import net.daw.bean.UsuarioBean;

public class UsuarioDao {

    Connection oConnection;
    String ob = null;
    String ddbb = "trolleyes";

    public UsuarioDao(Connection oConnection, String ob) {
        super();
        this.oConnection = oConnection;
        this.ob = ob;
    }

    public UsuarioBean get(int id) throws Exception {
        String strSQL = "SELECT * FROM " + ob + " WHERE id=?";
        UsuarioBean oUsuarioBean;
        ResultSet oResultSet = null;
        PreparedStatement oPreparedStatement = null;
        try {
            oPreparedStatement = oConnection.prepareStatement(strSQL);
            oPreparedStatement.setInt(1, id);
            oResultSet = oPreparedStatement.executeQuery();
            if (oResultSet.next()) {
                oUsuarioBean = new UsuarioBean();
                oUsuarioBean.setId(oResultSet.getInt("id"));
                oUsuarioBean.setNombre(oResultSet.getString("nombre"));
                oUsuarioBean.setApe1(oResultSet.getString("ape1"));
                oUsuarioBean.setApe2(oResultSet.getString("ape2"));
                oUsuarioBean.setDni(oResultSet.getString("dni"));
                oUsuarioBean.setId_tipoUsuario(oResultSet.getInt("id_tipousuario"));
            } else {
                oUsuarioBean = null;
            }
        } catch (SQLException e) {
            throw new Exception("Error en Dao get de " + ob, e);
        } finally {
            if (oResultSet != null) {
                oResultSet.close();
            }
            if (oPreparedStatement != null) {
                oPreparedStatement.close();
            }
        }
        return oUsuarioBean;
    }

    public int remove(int id) throws Exception {
        int iRes = 0;
        String strSQL = "DELETE FROM " + ob + " WHERE id=?";
        PreparedStatement oPreparedStatement = null;
        try {
            oPreparedStatement = oConnection.prepareStatement(strSQL);
            oPreparedStatement.setInt(1, id);
            iRes = oPreparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error en Dao remove de " + ob, e);
        } finally {
            if (oPreparedStatement != null) {
                oPreparedStatement.close();
            }
        }
        return iRes;
    }

    public int getcount() throws Exception {
        String strSQL = "SELECT COUNT(id) FROM " + ob;
        int res = 0;
        ResultSet oResultSet = null;
        PreparedStatement oPreparedStatement = null;
        try {
            oPreparedStatement = oConnection.prepareStatement(strSQL);
            oResultSet = oPreparedStatement.executeQuery();
            if (oResultSet.next()) {
                res = oResultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new Exception("Error en Dao get de " + ob, e);
        } finally {
            if (oResultSet != null) {
                oResultSet.close();
            }
            if (oPreparedStatement != null) {
                oPreparedStatement.close();
            }
        }
        return res;
    }

    public UsuarioBean create(UsuarioBean oUsuarioBean) throws Exception {
        String strSQL = "INSERT INTO  " + ddbb + "." + ob
                + " (" + ddbb + "." + ob + ".id, " + ddbb + "." + ob + ".dni,"
                + ddbb + "." + ob + ".nombre," + ddbb + "." + ob + ".ape1,"
                + ddbb + "." + ob + ".ape2,"+ ddbb + "." + ob + ".id_tipousuario)"
                + " VALUES (NULL, ?,?,?,?,?); ";
        ResultSet oResultSet = null;
        PreparedStatement oPreparedStatement = null;
        try {
            oPreparedStatement = oConnection.prepareStatement(strSQL);
            oPreparedStatement.setString(1, oUsuarioBean.getDni());
            oPreparedStatement.setString(2, oUsuarioBean.getNombre());
            oPreparedStatement.setString(3, oUsuarioBean.getApe1());
            oPreparedStatement.setString(4, oUsuarioBean.getApe2());
            oPreparedStatement.setInt(5, oUsuarioBean.getId_tipoUsuario());
            oPreparedStatement.executeUpdate();
            oResultSet = oPreparedStatement.getGeneratedKeys();
            if (oResultSet.next()) {
                oUsuarioBean.setId(oResultSet.getInt(1));
            } else {
                oUsuarioBean.setId(0);
            }
        } catch (SQLException e) {
            throw new Exception("Error en Dao create de " + ob, e);
        } finally {
            if (oResultSet != null) {
                oResultSet.close();
            }
            if (oPreparedStatement != null) {
                oPreparedStatement.close();
            }
        }
        return oUsuarioBean;
    }

    public int update(UsuarioBean oUsuarioBean) throws Exception {
        int iResult = 0;
        String strSQL = "UPDATE " + ob + " SET dni= ?, nombre= ?, ape1= ?, ape2= ?, id_tipousuario=? WHERE id = ?;";

        PreparedStatement oPreparedStatement = null;
        try {
            oPreparedStatement = oConnection.prepareStatement(strSQL);
            oPreparedStatement.setString(1, oUsuarioBean.getDni());
            oPreparedStatement.setString(2, oUsuarioBean.getNombre());
            oPreparedStatement.setString(3, oUsuarioBean.getApe1());
            oPreparedStatement.setString(4, oUsuarioBean.getApe2());
            oPreparedStatement.setInt(5, oUsuarioBean.getId_tipoUsuario());
            oPreparedStatement.setInt(6, oUsuarioBean.getId());
            iResult = oPreparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new Exception("Error en Dao update de " + ob, e);
        } finally {
            if (oPreparedStatement != null) {
                oPreparedStatement.close();
            }
        }
        return iResult;
    }

    public ArrayList<UsuarioBean> getpage(int iRpp, int iPage) throws Exception {
        String strSQL = "SELECT * FROM "+ddbb+"."+ob;
        ArrayList<UsuarioBean> alUsuarioBean;
        if (iRpp > 0 && iRpp < 100000 && iPage > 0 && iPage < 100000000) {
            strSQL += " LIMIT " + (iPage - 1) * iRpp + ", " + iRpp;
            ResultSet oResultSet = null;
            PreparedStatement oPreparedStatement = null;
            try {
                oPreparedStatement = oConnection.prepareStatement(strSQL);
                oResultSet = oPreparedStatement.executeQuery();
                alUsuarioBean = new ArrayList<UsuarioBean>();
                while (oResultSet.next()) {
                    UsuarioBean oUsuarioBean = new UsuarioBean();
                    oUsuarioBean.setId(oResultSet.getInt("id"));
                   // oFacturaBean.setDesc(oResultSet.getString("desc"));
                    alUsuarioBean.add(oUsuarioBean);
                }
            } catch (SQLException e) {
                throw new Exception("Error en Dao getpage de " + ob, e);
            } finally {
                if (oResultSet != null) {
                    oResultSet.close();
                }
                if (oPreparedStatement != null) {
                    oPreparedStatement.close();
                }
            }
        } else {
            throw new Exception("Error en Dao getpage de " + ob);
        }
        return alUsuarioBean;

    }

}
