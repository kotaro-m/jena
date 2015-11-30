package jena;

import java.io.IOException;
import java.util.List;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class Readjena {
public static void main(String[] args) throws IOException{
	Model model = ModelFactory.createDefaultModel();
	 model.read("/home/fujii-lab/git/CrawlSoup/CrawlSoup/data/RDF/news.rdf", "RDF/XML");
	 
	 String queryString = "PREFIX schema: <http://schema.org/>"+
			 "SELECT DISTINCT  ?o  " +
             "WHERE {"+
			 "?s schema:dataline ?data FILTER regex (?data, \"2015年11月10日\") ."+
			 "?s schema:articleBody ?o.}";
	Query query = QueryFactory.create(queryString);
	// Execute the query and obtain results
	QueryExecution qe = QueryExecutionFactory.create(query, model);
	ResultSet results = qe.execSelect();
	// Output query results		
	List<QuerySolution> Temp = ResultSetFormatter.toList(results);
	for(int i=0;i < Temp.size(); i++){
		String t = Temp.get(i).toString().replace("( ?o = \"", "").replace("\" )", "");
		System.out.println(t);
	}
	// Important - free up resources used running the query
	qe.close();
	}
}