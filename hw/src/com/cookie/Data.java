package com.cookie;

public class Data {
	
String keyword,link;

public Data(String keyword,String link)
{
	this.keyword=keyword;
	this.link=link;
}

public Data(String keyword)
{
	this.keyword=keyword;
}

public String getKeyword()
{
	return keyword;
}
public void setKeyword(String keyword)
{
	this.keyword=keyword;
}
public String getLink()
{
	return link;
}
public void setLink(String link)
{
	this.link=link;
}
@Override
public boolean equals(Object obj) {
	// TODO Auto-generated method stub
	Data d=(Data) obj;
	return d.getKeyword().equals(keyword)&&d.getLink().equals(link);
}
@Override
public String toString() {
	// TODO Auto-generated method stub
	return String.format("제목: %s, 링크: %s ", keyword,link);
}

}
