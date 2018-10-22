package com.data.write.csv.writer;

import java.io.IOException;
import java.io.Writer;

import org.springframework.batch.item.file.FlatFileHeaderCallback;

public class CsvHeaderWriter implements FlatFileHeaderCallback{

	private final String header;
	 
	public CsvHeaderWriter(String header) {
        this.header = header;
    }
 
    @Override
    public void writeHeader(Writer writer) throws IOException {
        writer.write(header);
    }

}
