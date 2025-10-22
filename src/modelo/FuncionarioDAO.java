/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import dal.ModuloConexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author 02338079698
 */
public class FuncionarioDAO {
    
    CidadeDao objCidadeDao = new CidadeDao();
    
    public List<Funcionario> getLista() {
        String mysql = "select * from funcionario";
        List<Funcionario> listaFuncionario = new ArrayList<>();
        try {
            PreparedStatement pst = ModuloConexao.getPreparableStatement(mysql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Funcionario objFuncionario = new Funcionario();
                objFuncionario.setCodigoFuncionario(rs.getInt("codFuncionario"));
                objFuncionario.setNomeFuncionario(rs.getString("nomeFuncionario"));
                objFuncionario.setSalarioFuncionario(rs.getDouble("salario"));
                
                //conversao de date para calendar
                
                java.sql.Date dt = rs.getDate("dataNascimento");
                Calendar c = Calendar.getInstance();
                c.setTime(dt);
                objFuncionario.setNascimentoFuncionario(c);
                
                objFuncionario.setObjCidade(objCidadeDao.localizarCidade(rs.getInt("codigoCidade")));
                
                
                listaFuncionario.add(objFuncionario);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro de SQL na classe FuncionarioDAO no metodo getFuncionario" + ex.getMessage());
        }

        return listaFuncionario;
    }
    
    
    public boolean inserir(Funcionario funcionario) {
        String mysql = "insert into funcionario(nomeFuncionario, salario, dataNascimento, codigoCidade) VALUES(?,?,?,?)";

        try {
            PreparedStatement prt = ModuloConexao.getPreparableStatement(mysql);
            prt.setString(1, funcionario.getNomeFuncionario());
            prt.setDouble(2, funcionario.getSalarioFuncionario());
            prt.setDate(3, new java.sql.Date(funcionario.getNascimentoFuncionario().getTimeInMillis()));
            prt.setInt(4, funcionario.getObjCidade().getCod());
            if (prt.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Funcionario Cadastrada com sucesso!");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Funcionario não cadastrada!");
                return false;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro em mysql na classe funcionarioDao" + ex.getMessage());
            return false;
        }

    }

    public boolean alterar(Cidade objCidade) {
        String mysql = "update cidade set nomeCidade = ? , ufcidade= ? WHERE codCidade = ?";

        try {
            PreparedStatement prt = ModuloConexao.getPreparableStatement(mysql);
            prt.setString(1, objCidade.getNome());
            prt.setString(2, objCidade.getUf());
            prt.setInt(3, objCidade.getCod());
            if (prt.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Alterado com sucesso");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Erro chave nao encontrada");
                return false;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro em mysql " + ex.getMessage());
            return false;
        }
    }

    public boolean salvar(Cidade objCidade) {
        if (objCidade.getCod() == null) {
            inserir(objCidade);
            return true;
        } else {
            alterar(objCidade);
            return true;
        }

    }

    public boolean remover(Cidade objCidade) {
        String mysql = "delete from cidade where codCidade = ?";

        try {
            PreparedStatement prt = ModuloConexao.getPreparableStatement(mysql);
            prt.setInt(1, objCidade.getCod());
            if (prt.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Apagado com sucesso");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Erro chave nao encontrada");
                return false;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro em mysql " + ex.getMessage());
            return false;
        }
    }
}
