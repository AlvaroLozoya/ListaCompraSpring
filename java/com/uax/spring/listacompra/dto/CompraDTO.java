package com.uax.spring.listacompra.dto;

public class CompraDTO {

	private long id;
	private String descripcion;
	private CategoriaDTO categoria;
	
	private static int counterById = 1;
	
	public CompraDTO() {
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
	public CategoriaDTO getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaDTO categoria) {
		this.categoria = categoria;
	}

	public static void setCounterById(int counterById) {
		CompraDTO.counterById = counterById;
	}

	public static int getCounterById() {
		return counterById++;
	}

}
