package com.example.notifyme;

public class Nota
{
	private String titulo;
	private String subtitulo;

	public Nota(String tit, String sub){
		titulo = tit;
		subtitulo = sub;
	}
	
	public String getTitulo(){
		return titulo;
	}
	
	public String getSubtitulo(){
		return subtitulo;
	}
}

