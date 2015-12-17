package me.foldl.corenlp_summarizer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name= DocumentFrequencyCounter.TAG_DOCUMENT)
@XmlAccessorType(XmlAccessType.NONE)
public class CatalogDoc {
	
	@XmlAttribute
	private String id;
	
	@XmlAttribute
	private String type;
	
	@XmlElement(name = DocumentFrequencyCounter.TAG_TEXT)
	private String text;
	
	@XmlElement(name = "HEADLINE")
	private String headline;
	
}
