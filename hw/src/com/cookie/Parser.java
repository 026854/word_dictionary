package com.cookie;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Scanner;

import javax.naming.CommunicationException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

public class Parser {
	
	public static int relsize;
	public int wordsize;
	
	
public static ArrayList<Data>goodsSearch(String keyword)
{
	ArrayList<Data> goodsArr = new ArrayList<Data>();
	String url = "http://alldic.daum.net/search.do?q=";
	//String word = URLEncoder.encode(keyword);
	String result = url+keyword;
	
	InputStream in = URLManager.getURLInputStream(result, URLManager.USER_AGENT_PC);
	
	try{
		Document doc = Jsoup.parse(in,URLManager.ENCODING_UTF8,"");
		
		Elements root = doc.select("div[class=card_word #word #eng]").select("ul[class=list_search]");
		//System.out.println(root.size());
		Elements rankList = root.select("li").select("span[class=txt_search]");
		//System.out.println(rankList.size() +rankList.text());
		for(int i=0;i<3;++i)
		{
			String link = rankList.get(i).attr("href");
			if(link.contains("#"))
			{
				continue;
			}
			else{
			String text = (i+1)+". "+rankList.get(i).text();
			Data x = new Data(text, link);
			goodsArr.add(x);
			}
			
		}
	}catch(IOException e){
		e.printStackTrace();
	}
	return goodsArr;
}



public static ArrayList<Data>relativeword(String keyword) {
	ArrayList<Data> exsArr = new ArrayList<Data>();
	
	String url =  "http://alldic.daum.net/search.do?q=";
	String result = url+keyword;

	InputStream in = URLManager.getURLInputStream(result, URLManager.USER_AGENT_PC);
	
try{
		Document doc = Jsoup.parse(in,URLManager.ENCODING_UTF8,"");
		Elements rel = doc.select("div[id=relatedQuery]").select("li").select("a");
		if(rel.size()>6){
			for(int i=0; i<6;++i){
				relsize=6;
				String relative = rel.get(i).text();
				Data x = new Data(relative);
				exsArr.add(x);
				}
			}
		else {
			relsize = rel.size();
			System.out.println(relsize+"parser");
			for(int i=0; i<rel.size();++i){
				
				
				String relative = rel.get(i).text();
				Data x = new Data(relative);
				exsArr.add(x);
				
				
			}
		}
	}catch(IOException e){
		e.printStackTrace();
	}
	return exsArr;
	
	}

public static int getrelsize(){
	System.out.println(relsize+"get");
	return relsize;
}

public static ArrayList<Data>exsentance(String keyword) {
	ArrayList<Data> relArr = new ArrayList<Data>();
	
	String url =  "http://alldic.daum.net/search.do?q=";
	String result = url+keyword+"&dic=eng&search_first=Y";
	
	

	InputStream in = URLManager.getURLInputStream(result, URLManager.USER_AGENT_PC);
	
	
	
try{
		Document doc = Jsoup.parse(in,URLManager.ENCODING_UTF8,"");
		Elements rel = doc.select("div[class=box_example box_sound]").select("span[class=txt_ex]");
		
		//System.out.println(rel.size()+"/"+rel.text());
		
		
			for(int i=0; i<rel.size();++i){
				relsize = rel.size();
				String relative = rel.get(i).text();
				Data x = new Data(relative);
				relArr.add(x);
			}
	
	}catch(IOException e){
		e.printStackTrace();
	}
	return relArr;
	
	}

}
