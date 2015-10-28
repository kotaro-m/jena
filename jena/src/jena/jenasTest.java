package jena;
import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.ReadWrite;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.tdb.TDBFactory;
public class jenasTest {
	public static void main (String[] args){
		Dataset dataset = TDBFactory.createDataset("/home/fujii-lab/git/CrawlSoup/CrawlSoup/data/RDF");
		try{
			dataset.begin(ReadWrite.WRITE);
			String newsURI = "http://example.com/news/";
			String relationURI = "http://news-relation.com/";
			Model model = ModelFactory.createDefaultModel();
			Resource news = model.createResource(newsURI);
			Resource ID = model.createResource(newsURI + "ID=1");
			Resource br = model.createResource();
			Resource br1 = model.createResource();
			Property ID_is = model.createProperty(relationURI, "ID_is");
			Property title_is = model.createProperty(relationURI,"title_is");
			Property article = model.createProperty(relationURI,"article_is");
			Property time_is = model.createProperty(relationURI,"time_is");
			Property tag = model.createProperty(relationURI,"tag");
			Property inf = model.createProperty(relationURI,"newsinf");
			Property List = model.createProperty(relationURI,"TagForder");
			news.addProperty(inf, br);
			br.addProperty(ID_is,"1");
			br.addProperty(title_is,"勝俣、谷田、畔上…ドラフトで名前が呼ばれなかった注目選手は？");
			br.addProperty(article,"　2015年のドラフト会議が終了。高橋純平、オコエ瑠偉、小笠原慎之介、熊原健人と注目選手が次々と指名されていった。 　一方で指名されなかった選手もいる。");
			br.addProperty(time_is,"最終更新:10月22日(木)22時4分");
			br.addProperty(List,br1);
			br1.addProperty(tag,"北村祥治");
			br1.addProperty(tag,"法政大");
			br1.addProperty(tag,"指名漏れ");
			dataset.commit();
		}finally{
			dataset.end();
		}
		//model.write(System.out, "RDF/XML-ABBREV");
	}
}