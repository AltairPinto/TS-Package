/*
* Programa para ler o fluxo de pacotes TS do arquivo de vídeo.ts e extrair as 
* informações das tabelas de sinalização PAT e PMT, apresentando as principais 
* infomações contidas nessas tabelas.
*
*  @author: Altair Jussadir da Silva Pinto - Engenharia de Computação / UFPB
*  Data: 27/06/2016
*/

import java.util.*;
import java.io.*;
   
    abstract class Conversores {
  
        public static String inteiroParaBinario(int i) { 
            int num = i;
            StringBuilder buf1 = new StringBuilder();
            StringBuilder buf2 = new StringBuilder();
                while (num != 0) {
                    int digit = num % 2;
                    buf1.append(digit);
                    num = num / 2;
                }
                String binary = buf1.reverse().toString();
            int length = binary.length();
		if (length < 8) {
			while (8 - length > 0) {
				buf2.append("0");
				length++;
			}
		}
		String bin = buf2.toString() + binary;
		return bin;
	}
    
    public static String binarioParaDecimal(String s) {
            int degree = 1;
            int n = 0;
		for (int k = s.length() - 1; k >= 0; k--) {
			n += degree * (s.charAt(k) - '0');
			degree *= 2;
		}
		return n + "";
	}
    
    public static String Diretorio = "C:/video.ts"; //Local do video

} //Fim conversores
    
class PAT{
        
/*Formato tabela PAT (Tabela de Associação de Programa)
     Syntax               Nº bits               Mnemônico
program_association_section() {
table_id                    8                   uimsbf
section_syntax_indicator    1                   bslbf
'0'                         1                   bslbf
reserved                    2                   bslbf
section_length              12                  uimsbf
transport_stream_id         16                  uimsbf
reserved                    2                   bslbf
version_number              5                   uimsbf
current_next_indicator      1                   bslbf
section_number              8                   uimsbf
last_section_number         8                   uimsbf
for (i = 0; i < N; i++) {
program_number              16                  uimsbf
reserved                    3                   bslbf
if (program_number == '0') {
network_PID                 13                  uimsbf
}
else {
program_map_PID             13                  uimsbf
}
}
CRC_32                      32                  rpchof
}
 */
        private int table_id;//
	private String section_syntax_indicator;//
	private int section_length;//
	private String transport_stream_id;//
	private int version_number;//
	private int current_next_indicator;//
        private int section_number;//
	private int last_section_number;//
        private int program_number;
        private int network_PID;
	private String program_map_PID;
        private int CRC_32;
        //String para conversão
        
    public int getTable_id() {
        return table_id;
    }

    public void setTable_id(int table_id) {
        this.table_id = table_id;
    }

    public String getSection_syntax_indicator() {
        return section_syntax_indicator;
    }

    public void setSection_syntax_indicator(String section_syntax_indicator) {
        this.section_syntax_indicator = section_syntax_indicator;
    }

    public int getSection_length() {
        return section_length;
    }

    public void setSection_length(int section_length) {
        this.section_length = section_length;
    }

    public String getTransport_stream_id() {
        return transport_stream_id;
    }

    public void setTransport_stream_id(String transport_stream_id) {
        this.transport_stream_id = transport_stream_id;
    }

    public int getVersion_number() {
        return version_number;
    }

    public void setVersion_number(int version_number) {
        this.version_number = version_number;
    }

    public int getCurrent_next_indicator() {
        return current_next_indicator;
    }

    public void setCurrent_next_indicator(int current_next_indicator) {
        this.current_next_indicator = current_next_indicator;
    }

    public int getSection_number() {
        return section_number;
    }

    public void setSection_number(int section_number) {
        this.section_number = section_number;
    }
    
    public int getLast_section_number() {
        return last_section_number;
    }

    public void setLast_section_number(int last_section_number) {
        this.last_section_number = last_section_number;
    }

    public int getProgram_number() {
        return program_number;
    }

    public void setProgram_number(int program_number) {
        this.program_number = program_number;
    }

    public int getNetwork_PID() {
        return network_PID;
    }

    public void setNetwork_PID(int network_PID) {
        this.network_PID = network_PID;
    }

    public String getProgram_map_PID() {
        return program_map_PID;
    }

    public void setProgram_map_PID(String program_map_PID) {
        this.program_map_PID = program_map_PID;
    }
    
    public int getCRC_32() {
        return CRC_32;
    }

    public void setCRC_32(int CRC_32) {
        this.CRC_32 = CRC_32;
    }
 } //Fim PAT  

 class PMT{
    
/*Formato tabela PMT (Tabela de Mapeamento de Programa)
     Syntax               Nº bits               Mnemônico
TS_program_map_section() {
table_id                    8                   uimsbf
section_syntax_indicator    1                   bslbf
'0'                         1                   bslbf
reserved                    2                   bslbf
section_length              12                  uimsbf
program_number              16                  uimsbf
reserved                    2                   bslbf
version_number              5                   uimsbf
current_next_indicator      1                   bslbf
section_number              8                   uimsbf
last_section_number         8                   uimsbf
reserved                    3                   bslbf
PCR_PID                     13                  uimsbf
reserved                    4                   bslbf
program_info_length         12                  uimsbf
for (i = 0; i < N; i++) {
descriptor()
}
for (i = 0; i < N1; i++) {
stream_type                 8                   uimsbf
reserved                    3                   bslbf
elementary_PID              13                  uimsbf
reserved                    4                   bslbf
ES_info_length              12                  uimsbf
for (i = 0; i < N2; i++) {
descriptor()
}
}
CRC_32                      32                  rpchof
}
* SOMA TOTAL = 104 >> a partir do section_length exceto loop 
* N = SECTION_LENGTH BYTES - (O TAMANHO DO PACOTE RESTANTE (SOMA DO RESTO) / 8) 
* /5(tamanho do loop) = 2 
*/    
        private int table_id ;
	private int section_syntax_indicator ;
	private int section_length;
        private int program_number;
	private int version_number;
	private int current_next_indicator;
        private int section_number;
	private int last_section_number;
        private int PCR_PID;
        private int program_info_length;
        private int CRC_32;
        
        //Parte do loop
        
        private int stream_type;
        private int elementary_PID;
        private int ES_info_length;
        
        
    public int getTable_id() {
        return table_id;
    }

    public void setTable_id(int table_id) {
        this.table_id = table_id;
    }

    public int getSection_syntax_indicator() {
        return section_syntax_indicator;
    }

    public void setSection_syntax_indicator(int section_syntax_indicator) {
        this.section_syntax_indicator = section_syntax_indicator;
    }

    public int getSection_length() {
        return section_length;
    }

    public void setSection_length(int section_length) {
        this.section_length = section_length;
    }

    public int getProgram_number() {
        return program_number;
    }

    public void setProgram_number(int program_number) {
        this.program_number = program_number;
    }

    public int getVersion_number() {
        return version_number;
    }

    public void setVersion_number(int version_number) {
        this.version_number = version_number;
    }

    public int getCurrent_next_indicator() {
        return current_next_indicator;
    }

    public void setCurrent_next_indicator(int current_next_indicator) {
        this.current_next_indicator = current_next_indicator;
    }
    
    public int getSection_number() {
        return section_number;
    }

    public void setSection_number(int section_number) {
        this.section_number = section_number;
    }
    
    public int getLast_section_number() {
        return last_section_number;
    }

    public void setLast_section_number(int last_section_number) {
        this.last_section_number = last_section_number;
    }

    public int getPCR_PID() {
        return PCR_PID;
    }

    public void setPCR_PID(int PCR_PID) {
        this.PCR_PID = PCR_PID;
    }

    public int getProgram_info_length() {
        return program_info_length;
    }

    public void setProgram_info_length(int program_info_length) {
        this.program_info_length = program_info_length;
    }
    
      public int getCRC_32() {
        return CRC_32;
    }

    public void setCRC_32(int CRC_32) {
        this.CRC_32 = CRC_32;
    }
    
    //loop
    
    public int getStream_type() {
        return stream_type;
    }

    public void setStream_type(int stream_type) {
        this.stream_type = stream_type;
    }

    public int getElementary_PID() {
        return elementary_PID;
    }

    public void setElementary_PID(int elementary_PID) {
        this.elementary_PID = elementary_PID;
    }

    public int getES_info_length() {
        return ES_info_length;
    }

    public void setES_info_length(int ES_info_length) {
        this.ES_info_length = ES_info_length;
    }
} //Fim PMT   
    
    
class LoopPMT {
    
        private int stream_type;
        private int elementary_PID;
        private int ES_info_length;

    public int getStream_type() {
        return stream_type;
    }

    public void setStream_type(int stream_type) {
        this.stream_type = stream_type;
    }

    public int getElementary_PID() {
        return elementary_PID;
    }

    public void setElementary_PID(int elementary_PID) {
        this.elementary_PID = elementary_PID;
    }

    public int getES_info_length() {
        return ES_info_length;
    }

    public void setES_info_length(int ES_info_length) {
        this.ES_info_length = ES_info_length;
    }
        
        
}//Fim Loop PMT

// MAIN
public class PAT_e_PMT {
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
                /*System.out.println("Total PMT - " + listaPMT.size());
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
    
    

    
}
