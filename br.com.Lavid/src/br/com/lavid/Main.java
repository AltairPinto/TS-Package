/*
* Programa para ler o fluxo de pacotes TS do arquivo de vídeo.ts e extrair as 
* informações das tabelas de sinalização PAT e PMT, apresentando as principais 
* infomações contidas nessas tabelas.
*
*  @author: Altair Jussadir da Silva Pinto - Engenharia de Computação / UFPB
*  Data: 27/06/2016
*/
package br.com.lavid;

import java.util.*;
import java.io.*;
/**
 *
 * @author Altair
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static List<PAT> listaPAT; //Criar lista PAT    
    public static List<PMT> listaPMT; //Criar lista PMT
    public static List<LoopPMT> listaloop; //Criar lista do loop do PMT
    
        public static void main(String[] args) throws FileNotFoundException, IOException {
            tabelaPAT();
            tabelaPMT();
        }
//----------------------------------PAT----------------------------------------        
    public static void tabelaPAT() throws FileNotFoundException, IOException {
    //Aqui serão extraídas as tabelas PAT do arquivo de vídeo 
         listaPAT = new LinkedList<PAT>(); //Lista para PAT
         PAT pat;
         byte[] b = new byte[188]; //Fluxo de TV Digital = 188 bytes
	 FileInputStream arquivoTS = new FileInputStream(Conversores.Diretorio);
         //Carregamento do arquivo .ts com diretório setado em Conversores
         int read = 0;
            while ((read = arquivoTS.read(b)) != -1) {
                   String resultadoBinario = Conversores.inteiroParaBinario(b[1]);
                   String pid = resultadoBinario.substring(3);
                   /*5 bits restantes. A substring começa na posição 
                    * especifica e tem um comprimento especifica.*/
                   boolean patepmt = false;
                        for (int i = 0; i < pid.length(); i++) {
                            if (pid.charAt(i) == '1') { 
                               /*Retorna o caractere no índice especificado
                                *Caso seja 1, é PMT*/
                                   patepmt = true;
                                   break;
                            } //Fim do if
                        } //Fim do for
             if (!patepmt) {
                if (b[2] == 0) { //O pacote de 188 bytes é um PAT
                    pat = new PAT(); //Crio um PAT
                    pat.setTable_id(b[5]);
                    String result = Conversores.inteiroParaBinario(b[6]);
                    /* Como cada F equivale a 4 bits, em x e 0xFF são extraídos 
                     * os 8 últimos bits e de x e 0xFFF os últimos 12 bits*/
                    pat.setSection_syntax_indicator(result.substring(0, 1));
                    pat.setSection_length(((b[6] & 0xFF) & 0xF) + b[7] & 0xFF);
                    pat.setTransport_stream_id(Conversores.binarioParaDecimal(
                            Conversores.inteiroParaBinario(b[8])
                          + Conversores.inteiroParaBinario(b[9])));
                    result = Conversores.inteiroParaBinario(b[10]);
                    pat.setVersion_number(((b[10] & 0xFF) >> 1) & 0x1F); //Afastar um bit e pegar os mais significantes
                    pat.setCurrent_next_indicator((b[10] & 0xFF) & 0x01);
                    pat.setSection_number(b[11]);
                    pat.setLast_section_number(b[12]);
                    pat.setProgram_number(b[13]+b[14]);
                        if(pat.getProgram_number() == 0){
                        pat.setNetwork_PID(((b[15] & 0xFF) & 0x1F)+b[16] & 0xFF); //13 bits
                        pat.setCRC_32(b[17]+b[18]+b[19]+b[20]);
                        listaPAT.add(pat);
                        } //Fim do if
                        else{
                        String program_map_pid =
                            Integer.toBinaryString((b[15] & 0xFF) & 0x1F) //Negativo
                          + Integer.toBinaryString((b[16] & 0xFF));
                        pat.setProgram_map_PID((Conversores.binarioParaDecimal(program_map_pid)));
                        pat.setCRC_32(b[17]+b[18]+b[19]+b[20]);
			listaPAT.add(pat);
			} //Fim do else
                } //Fim do if
                  } //Fim do if
                } //Fim do while 
                System.out.println("Total PAT - " + listaPAT.size());
                for(int cont = 1; cont < listaPAT.size();cont++){
		for (PAT p : listaPAT) {
                System.out.println("PAT - "+cont + " || table_id = " + p.getTable_id()
		+ " || section_syntax_indicator = " + p.getSection_syntax_indicator() 
                + " || section_lenght = " + p.getSection_length() 
                + " || transport_stream_id = " + p.getTransport_stream_id()
                + " || version_number = " + p.getVersion_number()
                + " || current_next_indicator = " + p.getCurrent_next_indicator()
                + " || section_number = " + p.getSection_number()
                + " || last_section_number = " + p.getLast_section_number()
                + " || program_number = " + p.getProgram_number()        
                + " || program_map_PID = " + p.getProgram_map_PID()
                + " || CRC_32 " + p.getCRC_32());
                if(p.getProgram_number() ==0){
                    System.out.println("LOOP network_PID = "+p.getNetwork_PID());
                }
		cont++;
		} //Fim do for
                } //Fim do for*/
    } //Fim tabelaPAT    
//----------------------------------PMT----------------------------------------        
 public static void tabelaPMT() throws FileNotFoundException, IOException {
            listaPMT = new LinkedList<PMT>();
            listaloop = new LinkedList<LoopPMT>();
            LoopPMT loop = new LoopPMT();
            int cont = 0, read = 0;
            byte[] b = new byte[188]; //Fluxo de TV Digital = 188 bytes
            FileInputStream arquivoTS = new FileInputStream(Conversores.Diretorio);
            Map<String, String> hashmap = new HashMap<String, String>();
            //HashMap para uma busca mais rápida
            for (PAT p : listaPAT) {//Não adicionar chave PAT repetida
		hashmap.put(p.getProgram_map_PID(), "valor"); 
            } //Fim do for
            List<String> list = new ArrayList<String>(hashmap.keySet());
            //Cria e preenche o objeto pra depois varrer ele e pegar todos os dados
		for (int i = 0; i < list.size(); i++) {
                    while ((read = arquivoTS.read(b)) != -1) {
                          String pidMap = //PID com HashMap
                                  Integer.toBinaryString((b[1] & 0xFF) & 0x1f)
				+ Integer.toBinaryString(b[2] & 0xFF);
				pidMap = Conversores.binarioParaDecimal(pidMap);
				PMT pmt;
			if (pidMap.equals(list.get(i))) {
                             //Tem uma PMT associada a uma PAT
                             pmt = new PMT(); //Crio novo PMT
                             pmt.setTable_id(b[5]);
                             System.out.println("table_id = "+pmt.getTable_id());
                             pmt.setSection_syntax_indicator(b[6] >>> 7 & 0x1);
                             System.out.println("section_syntax_indicator = "+pmt.getSection_syntax_indicator());
                             pmt.setSection_length(((b[6] & 0xFF) & 0xF) + b[7] & 0xFF);
                             System.out.println("section_length = "+pmt.getSection_length());
                             pmt.setProgram_number(((b[8] & 0xFF) & 0xF) + b[9] & 0xFF);
                             System.out.println("program_number = "+pmt.getProgram_number());
                             pmt.setVersion_number(((b[10] & 0xFF) >> 1) & 0x1F); // Afastar um bit
                             System.out.println("version_number = "+pmt.getVersion_number());
                             pmt.setCurrent_next_indicator((b[10] & 0xFF) & 0x01);
                             System.out.println("current_next_indicator = "+pmt.getCurrent_next_indicator());
                             pmt.setSection_number(b[11]);
                             System.out.println("section_number = "+pmt.getSection_number());
                             pmt.setLast_section_number(b[12]);
                             System.out.println("last_section_number = "+pmt.getLast_section_number());
                             pmt.setPCR_PID(((b[13] << 8) | b [14]) & 0x1FF);
                             System.out.println("PCR_PID = "+pmt.getPCR_PID());
                             pmt.setProgram_info_length(((b[15] & 0xFF) & 0xF) + b[16] & 0xFF);
                             System.out.println("program_info_length = " + pmt.getProgram_info_length());
                             int n = (pmt.getSection_length() - 13)/5; 
                             /*section_length do vídeo selecionado
                             *13 é o número de bytes somados após o section_length.*/
                             int ciclo = 17;
                             int marcador = 1;
                             while (n !=0){
                                 loop.setStream_type(b[ciclo++]);
                                System.out.println("\nLOOP "+marcador+" || stream_type = "+loop.getStream_type());
                                 loop.setElementary_PID(((b[ciclo++] << 8) | b [ciclo++]) & 0x1FF);
                                System.out.println("elementary_pid = "+loop.getElementary_PID());
                                 loop.setES_info_length(((b[ciclo++] & 0xFF) & 0xF) + b[ciclo++] & 0xFF);
                                System.out.println("ES_info = "+loop.getES_info_length()+"\n");
                                marcador++; 
                                n--;
                                listaloop.add(loop);
                             } //Fim do while
                             listaPMT.add(pmt); //Adiciono o PMT
                                System.out.println("_________FIM_________\n");
			} //Fim do if
                     } //Fim do while
		} //Fim do for
                System.out.println("Total PMT - " + listaPMT.size());
		int cont1 = 1;
                int cont2 = 2;
		for (PMT pp : listaPMT) {
		System.out.println("PMT - "+cont1 + " || table_id = " + pp.getTable_id()
		+ "|| section_syntax_indicator = " + pp.getSection_syntax_indicator() 
                + "|| section_lenght = " + pp.getSection_length() 
                + "|| program_number = " + pp.getProgram_number()
                + "|| version_number = " + pp.getVersion_number() 
                + "|| current_next_indicator = " + pp.getCurrent_next_indicator()
                + "|| section_number = " + pp.getSection_number()
                + "|| last_section_number = " + pp.getLast_section_number()
                + "|| PCR_PID = " + pp.getPCR_PID()
                + "|| program_info_length = " + pp.getProgram_info_length()
                + "|| CRC_32 = " + pp.getCRC_32());
                while (cont2 !=0){
                  System.out.println(
                  "|| LOOP "+cont2+ "|| stream_type = " + loop.getStream_type()
                + "|| elementary_PID = " + loop.getElementary_PID()
                + "|| ES_info_length = " + loop.getES_info_length());
                cont2--;
                } //Fim do while
		cont1++;
                cont2=2;
		} //Fim do for*/
    } //Fim tabelaPMT
} //Fim
