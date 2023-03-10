package com.uax.spring.listacompra.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.uax.spring.listacompra.dto.CategoriaDTO;
import com.uax.spring.listacompra.dto.CompraDTO;
import com.uax.spring.listacompra.mappers.CategoriaRowMapper;
import com.uax.spring.listacompra.mappers.CompraRowMapper;

@Repository
public class CompraRepository implements ICompraRepository {

	@Autowired
	@Qualifier("jdbcTemplateDB1")
	private JdbcTemplate jdbctemplate;
	
	@Autowired
	@Qualifier("jdbcTemplateDB2")
	private JdbcTemplate jdbctemplateDB2;

	public boolean saveCompra(CompraDTO compra) {
		boolean save = false;
		int id;
		try {
			if (getCompraByNombre(compra.getDescripcion())!=null) {
				String sql1 = String.format("UPDATE compras SET enabled=1 WHERE id=%d", compra.getId());
				jdbctemplate.execute(sql1);
			}else {
				String sql = String.format("INSERT INTO compras (descripcion, categoria, enabled) VALUES ('%s', %d, 1)", compra.getDescripcion(), compra.getCategoria());
				jdbctemplate.execute(sql);
			}
			
			
			save = true;
		} catch (Exception e) {
			// TODO: handle exception
		}

		return save;
	}

	public Boolean updateCompra(CompraDTO compra) {
		boolean updated = false;
		try {
			String sql = String.format("UPDATE compras SET descripcion='%s', categoria=%d WHERE id=%d", compra.getDescripcion(), compra.getCategoria().getCategoria(), compra.getId());
			jdbctemplate.execute(sql);
			updated = true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return updated;
	}

	public List<CompraDTO> getAllCompras() {
		// hace la consulta y usa el CompraRowMapper para crear un objeto de cada
		// resultado
		List<CompraDTO> listaCompra = new ArrayList();

		listaCompra = jdbctemplate.query("SELECT com.id, com.descripcion, com.categoria, cat.nombre FROM compras com, categorias cat WHERE com.categoria=cat.categoria AND enabled=1", new CompraRowMapper());
		return listaCompra;
	}
	public List<CategoriaDTO> getAllCategorias() {
		// hace la consulta y usa el CompraRowMapper para crear un objeto de cada
		// resultado
		List<CategoriaDTO> listaCategorias = new ArrayList();

		listaCategorias = jdbctemplate.query("SELECT * FROM categorias", new CategoriaRowMapper());
		return listaCategorias;
	}

	public CompraDTO getCompraById(long id) {
		List<CompraDTO> modify = new ArrayList();
		String sql = String.format("SELECT com.id, com.descripcion, com.categoria, cat.nombre FROM compras com, categorias cat WHERE com.categoria=cat.categoria AND id=%d", id);
		modify = jdbctemplate.query(sql, new CompraRowMapper());
		CompraDTO compra = modify.get(0);
		return compra;
	}
	public CompraDTO getCompraByNombre(String descripcion) {
		List<CompraDTO> modify = new ArrayList();
		String sql = String.format("SELECT com.descripcion FROM compras com WHERE descripcion=%s", descripcion);
		modify = jdbctemplate.query(sql, new CompraRowMapper());
		CompraDTO compra = modify.get(0);
		return compra;
	}

	public boolean deleteCompra(int id) {
		boolean deleted = false;
		try {
			String sql = String.format("UPDATE compras SET enabled=0 WHERE id=%d", id);
			jdbctemplate.execute(sql);
			deleted = true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return deleted;
	}

}
