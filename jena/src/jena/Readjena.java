package jena;

import java.io.IOException;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
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
			 "?s schema:keyword7 \"有名人:0.03773059248577996\" ."+
			 "?s schema:articleBody ?o.}";
	Query query = QueryFactory.create(queryString);
	// Execute the query and obtain results
	QueryExecution qe = QueryExecutionFactory.create(query, model);
	ResultSet results = qe.execSelect();
	// Output query results	
	ResultSetFormatter.out(System.out, results, query);
	// Important - free up resources used running the query
	qe.close();
	}
}