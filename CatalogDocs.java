package me.foldl.corenlp_summarizer;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name= "DOCS")
@XmlAccessorType(XmlAccessType.NONE)
public class CatalogDocs {
	@XmlElement(name= DocumentFrequencyCounter.TAG_DOCUMENT)
	private List<CatalogDoc> docs = new ArrayList<CatalogDoc>();

	public List<CatalogDoc> docs() {
		return docs;
	}
	
}
