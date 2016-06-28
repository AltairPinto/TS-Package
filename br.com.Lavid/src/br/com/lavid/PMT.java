/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lavid;

import java.util.*;
import java.io.*;
/**
 *
 * @author Altair
 */
public class PMT{
    
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
}