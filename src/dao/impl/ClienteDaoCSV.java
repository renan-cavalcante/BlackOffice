package dao.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import dao.IArquivoCSV;
import db.DB;
import model.entity.Cliente;
import model.entity.Endereco;

public class ClienteDaoCSV implements IArquivoCSV<Cliente>{
	
	private final String NOME = "cliente";

	@Override
	public void insert(Cliente conteudo) throws IOException {
		File arquivo = DB.getArquivo(NOME);
		FileWriter fileWriter = new FileWriter(arquivo, true);
		PrintWriter printWriter = new PrintWriter(fileWriter);
		conteudo.getEndereco().setId(DB. getSequencia("enderecoSequenci"));
		printWriter.write(conteudo.toStringCSV()+"\r\n");
		printWriter.close();
		fileWriter.close();
	}

	@Override
	public void delete(String id) throws IOException {
		List<Cliente> list = findAll();
		File arquivo = DB.getArquivo(NOME);
		FileWriter fileWriter = new FileWriter(arquivo, false);
		PrintWriter printWriter = new PrintWriter(fileWriter);
		for(Cliente c : list) {
			if(!id.equals(c.getId())) {
				printWriter.write(c.toStringCSV()+"\r\n");
			}
			
		}
		printWriter.close();
		fileWriter.close();
		
	}

	@Override
	public void update(Cliente conteudo) throws IOException {
		
		List<Cliente> list = findAll();
		
		int tamanho = list.size();
		for(int i = 0; i < tamanho; i++){
			
			
			if(conteudo.getId().equals(list.get(i).getId())) {
				conteudo.getEndereco().setId(list.get(i).getEndereco().getId());
				list.set(i, conteudo);
			}
	
		}
		File arquivo = DB.getArquivo(NOME);
		FileWriter fileWriter = new FileWriter(arquivo, false);
		PrintWriter printWriter = new PrintWriter(fileWriter);
		for(Cliente c : list) {
			printWriter.write(c.toStringCSV()+"\r\n");
		}
		printWriter.close();
		fileWriter.close();
	}

	@Override
	public List<Cliente> findAll() throws IOException {
		List<Cliente> list = new ArrayList<>();
		File arquivo = DB.getArquivo(NOME);
		FileInputStream fluxo = new FileInputStream(arquivo);
		InputStreamReader leitor = new InputStreamReader(fluxo);
		BufferedReader buffer = new BufferedReader(leitor);
		String linha = buffer.readLine();
		
		while(linha!=null){
			String[] dados = linha.split(";");
			list.add(new Cliente(dados[0], dados[1], new Endereco(Long.parseLong(dados[2]), dados[3], dados[4], dados[5], dados[6]), dados[7],dados[8]));
			linha = buffer.readLine();
		}
		fluxo.close();
		leitor.close();
		buffer.close();
		return list;
	}

	@Override
	public Cliente findById(String id) throws IOException {
		File arquivo = DB.getArquivo(NOME);
		FileInputStream fluxo = new FileInputStream(arquivo);
		InputStreamReader leitor = new InputStreamReader(fluxo);
		BufferedReader buffer = new BufferedReader(leitor);
		String linha = buffer.readLine();
		
		while(linha!=null){
			String[] dados = linha.split(";");
			if(id.trim().equals(dados[0])) {
				fluxo.close();
				leitor.close();
				buffer.close();
				return (new Cliente(dados[0], dados[1], new Endereco(Long.parseLong(dados[2]), dados[3], dados[4], dados[5], dados[6]), dados[7]));
			}
			linha = buffer.readLine();
		}
		fluxo.close();
		leitor.close();
		buffer.close();
		return null;
	}

	@Override
	public List<Cliente> findByName(String name) throws IOException {
		List<Cliente> list = new ArrayList<>();
		File arquivo = DB.getArquivo(NOME);
		FileInputStream fluxo = new FileInputStream(arquivo);
		InputStreamReader leitor = new InputStreamReader(fluxo);
		BufferedReader buffer = new BufferedReader(leitor);
		String linha = buffer.readLine();
		
		while(linha!=null){
			String[] dados = linha.split(";");
			if(name.trim().toLowerCase().contains(dados[1])) {
				fluxo.close();
				leitor.close();
				buffer.close();
				list.add(new Cliente(dados[0], dados[1], new Endereco(Long.parseLong(dados[2]), dados[3], dados[4], dados[5], dados[6]), dados[7]));
			}
			linha = buffer.readLine();
		}
		System.out.println("nada");
		fluxo.close();
		leitor.close();
		buffer.close();
		return list;
	}

	
}
