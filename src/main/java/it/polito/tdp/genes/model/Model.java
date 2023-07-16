package it.polito.tdp.genes.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.genes.db.GenesDao;

public class Model {
	GenesDao dao;
	Graph<Integer, DefaultWeightedEdge> graph;
	
	
	public Model() {
		this.dao = new GenesDao();
		this.graph = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
	}
	
	public Graph creaGrafo() {
		this.graph = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(this.graph, this.dao.getVertices());
		
		
		for(Integer chr1 : this.graph.vertexSet()) {
			for(Integer chr2 : this.graph.vertexSet()) {
				if(chr1 != chr2) {
					
					List<String> listaGeniChr1 = this.dao.getGeneIDList(chr1);
					List<String> listaGeniChr2 = this.dao.getGeneIDList(chr2);
					
					int peso =0;
					List<Integer> pesi = new ArrayList<>();
					
					for(String gene1 : listaGeniChr1) {
						for(String gene2 : listaGeniChr2) {
							if(this.dao.getListaGeni2(gene1).contains(gene2)) {
								pesi.add(this.dao.getExpression(gene1, gene2));
							}
						}
					}
					
					for(Integer p : pesi) {
						peso += p;
					}
					
					Graphs.addEdgeWithVertices(this.graph, chr1, chr2, peso);
				}
			}
		}
		
		return this.graph;
	}
	
	

}