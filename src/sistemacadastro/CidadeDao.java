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
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Cidade;
/**
 *
 * @author 02338079698
 */
public class CidadeDao {
    
    public List<Cidade> getLista(){
        String mysql = "select * from cidade";
        List<Cidade> listaCidade = new ArrayList<>();
        try{
            PreparedStatement pst = ModuloConexao.getPreparableStatement(mysql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                Cidade objCidade = new Cidade();
                objCidade.setCod(rs.getInt("codCidade"));
                objCidade.setNome(rs.getString("nomeCidade"));
                objCidade.setUf(rs.getString("ufcidade"));
                listaCidade.add(objCidade);
            }
            
            
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro de SQL "+ex.getMessage());
        }
        
        return listaCidade;
    }
    
    public boolean inserir(Cidade objCidade){
        String mysql = "insert into cidade(nomeCidade, ufcidade) VALUES(?,?)";
        
        try{
            PreparedStatement prt = ModuloConexao.getPreparableStatement(mysql);
            prt.setString(1, objCidade.getNome());
            prt.setString(2, objCidade.getUf());
            if(prt.executeUpdate()>0){
                JOptionPane.showMessageDialog(null, "Cidade Cadastrada com sucesso!");
                return true;
            }else{
                JOptionPane.showMessageDialog(null, "Cidae não cadastrada!");
                return false;
            }
            
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro em mysql "+ex.getMessage());
            return false;
        }

    }
    
    public boolean alterar(Cidade objCidade){
        String mysql = "update cidade set nomeCidade = ? , ufcidade= ? WHERE codCidade = ?";
        
        try{
            PreparedStatement prt = ModuloConexao.getPreparableStatement(mysql);
            prt.setString(1, objCidade.getNome());
            prt.setString(2, objCidade.getUf());
            prt.setInt(3, objCidade.getCod());
            if(prt.executeUpdate()>0){
                JOptionPane.showMessageDialog(null, "Alterado com sucesso");
                return true;
            }else{
                JOptionPane.showMessageDialog(null, "Erro chave nao encontrada");
                return false;
            }
            
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro em mysql "+ex.getMessage());
            return false;
        }
    }
    
    
    public boolean salvar(Cidade objCidade){
        if(objCidade.getCod() == null){
            inserir(objCidade);
            return true;
        }else{
            alterar(objCidade);
            return true;
        }
        
        
    }
    
    
    
    
    public boolean remover(Cidade objCidade){
       String mysql = "delete from cidade where codCidade = ?";
        
        try{
            PreparedStatement prt = ModuloConexao.getPreparableStatement(mysql);
            prt.setInt(1, objCidade.getCod());
            if(prt.executeUpdate()>0){
                JOptionPane.showMessageDialog(null, "Apagado com sucesso");
                return true;
            }else{
                JOptionPane.showMessageDialog(null, "Erro chave nao encontrada");
                return false;
            }
            
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro em mysql "+ex.getMessage());
            return false;
        }
    }
}
