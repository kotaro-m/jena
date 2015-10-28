package jena;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFWriter;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;
public class Readjena {
public static void main(String[] args) throws IOException{
	String filePath = "/home/fujii-lab/git/CrawlSoup/CrawlSoup/data/article2.txt";
	BufferedReader br = new BufferedReader(new FileReader(filePath));
	StringBuilder sb = new StringBuilder();
	ArrayList<String> tag = new ArrayList<String>();
	String readText = null;
	String article = null;
	String title = null;
	String time = null;
	int frag1 = 0;
	int frag2 = 0;
	int s=0;
	
	final String NEWS_NS = "http://localhost:8080/resource/";
	final String SCHEMA_NS = "http://schema.org/";
	final String RDFS_NS = "http://www.w3.org/2000/01/rdf-schema#";
	final String RDF_NS = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
	Model model = ModelFactory.createDefaultModel();
	
	model.setNsPrefix("news", NEWS_NS);
	model.setNsPrefix("schema", SCHEMA_NS);
	model.setNsPrefix("rdf", RDF_NS);
	model.setNsPrefix("rdfs", RDFS_NS);
	Resource SCHEMA_newsArticleClass = model.createResource(SCHEMA_NS + "NewsArticle");
	
	while ((readText = br.readLine()) != null){
		if(readText.startsWith("title:"))
			title = readText.replace("title:", "");
		if(readText.startsWith("time:"))
			time = readText.replace("time:", "");
		if(readText.startsWith("article:")){
			frag1=1;
			readText=readText.replace("article:", "");
		}
		if(readText.startsWith("tag")){
			frag1=0;
			article = new String(sb);
		}
		if(frag1==1)
			sb.append(readText);
		if(readText.startsWith("tag:")){
			frag2=1;
			readText=readText.replace("tag:", "");
		}
		if(readText.startsWith("--")){
			frag2=0;
			Resource news = model.createResource(NEWS_NS + s);
			model.add(news, RDF.type, SCHEMA_newsArticleClass);
			
			Property dataline = model.createProperty(SCHEMA_NS + "dataline");
			model.add(news, dataline, time);
			
			Property headline = model.createProperty(SCHEMA_NS + "headline");
			model.add(news, headline, title);
			
			Property articleBody = model.createProperty(SCHEMA_NS + "articleBody");
			model.add(news, articleBody, article);
			
			for(int i=1;i<tag.size();i++){
				Property keywords = model.createProperty(SCHEMA_NS + "keyword" + i);
				model.add(news, keywords, tag.get(i));
			}
			s++;
			sb = new StringBuilder();
			tag = new ArrayList<String>();
		}
		if(frag2==1)
			tag.add(readText);
		}
	br.close();
	
	FileOutputStream out = new FileOutputStream("/home/fujii-lab/git/CrawlSoup/CrawlSoup/data/RDF/news.rdf");
	RDFWriter writer = model.getWriter("RDF/XML");

	writer.write(model, out, "RDF/XML");
	model.write(out);
	}
}