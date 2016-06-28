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
public class PAT{
        
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
 }   

