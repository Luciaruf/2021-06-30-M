package it.polito.tdp.genes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.genes.model.Genes;
import it.polito.tdp.genes.model.Interactions;


public class GenesDao {
	
	public List<Genes> getAllGenes(){
		String sql = "SELECT DISTINCT GeneID, Essential, Chromosome FROM Genes";
		List<Genes> result = new ArrayList<Genes>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Genes genes = new Genes(res.getString("GeneID"), 
						res.getString("Essential"), 
						res.getInt("Chromosome"));
				result.add(genes);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
	}
	
	public List<Integer> getVertices(){
		String sql = "SELECT DISTINCT(g.`Chromosome`) as chromosome "
				+ "FROM genes g "
				+ "WHERE g.`Chromosome`<>0 ";
		List<Integer> result = new ArrayList<Integer>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				result.add(res.getInt("chromosome"));
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
	}
	
	public List<String> getGeneIDList(int chromosome){
		String sql = "SELECT DISTINCT(g.`GeneID`) as gene "
				+ "FROM genes g "
				+ "WHERE g.`Chromosome`=? ";
		List<String> result = new ArrayList<String>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, chromosome);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				result.add(res.getString("gene"));
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
	}
	public List<String> getListaGeni2(String gene1){
		String sql = "SELECT DISTINCT i.`GeneID2` as gene2 "
				+ "FROM interactions i "
				+ "WHERE i.`GeneID1` = ? ";
		List<String> result = new ArrayList<String>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, gene1);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				result.add(res.getString("gene2"));
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
	}
	public Integer getExpression(String gene1, String gene2){
		String sql = "SELECT i.`Expression_Corr` as expression "
				+ "FROM interactions i "
				+ "WHERE i.`GeneID1`=? "
				+ "AND i.`GeneID2`=? ";
		int peso = 0;
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, gene1);
			st.setString(2, gene2);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				peso = res.getInt("expression");
			}
			res.close();
			st.close();
			conn.close();
			return peso;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
	}
	
	
	
	
	


	
}
