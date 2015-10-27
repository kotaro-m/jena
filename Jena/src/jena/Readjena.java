package jena;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

public class Readjena {
	public static void main(String[] args) throws IOException{
		String filePath = "/data/article2.txt";
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

		String newsURI = "http://example.com/news/";
		String relationURI = "http://news-relation.com/";

		Model model = ModelFactory.createDefaultModel();

		Resource news = model.createResource(newsURI);
		Property ID_is = model.createProperty(relationURI, "ID_is");
		Property title_is = model.createProperty(relationURI,"title_is");
		Property article_is = model.createProperty(relationURI,"article_is");
		Property time_is = model.createProperty(relationURI,"time_is");
		Property tag_is = model.createProperty(relationURI,"tag_is");
		Property brank = model.createProperty(relationURI,"brank");
		

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
				Resource ID = model.createResource(newsURI + s);
				Resource Br = model.createResource();
				ID.addProperty(brank, Br);
				ID.addProperty(title_is,title);
				ID.addProperty(article_is,article);
				ID.addProperty(time_is,time);
				for(int i=1;i<tag.size();i++){
					Br.addProperty(tag_is,tag.get(i));
				}
				s++;
				sb = new StringBuilder();
				tag = new ArrayList<String>();
			}
			if(frag2==1)
				tag.add(readText);

		}
		br.close();


		model.write(System.out, "RDF/XML-ABBREV");
	}
}
