package com.orangeleap.tangerine.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class AESEncrypt {

	public static void main(String[] args) {
		
		try {
		
			FileReader fr = null;
			FileWriter fw = null;
		
			try {
	
				if (args.length < 2) {
					System.out.println("Parameters: <datafile> <keyfile> ");
					System.exit(1);
				}
					
				String keyfile = args[0];
				String datafile = args[1];
				String outfile = datafile+".cpt";
				
				System.setProperty("key.file.path", keyfile);
				
				File fin = new File(datafile);
				File fout = new File(outfile);
				
				fw = new FileWriter(fout);
				BufferedWriter out = new BufferedWriter(fw);
				
				fr = new FileReader(fin);
				BufferedReader in = new BufferedReader(fr);
				
				String line;
				while ((line = in.readLine()) != null) {
					String[] fields = line.trim().split(",");
					if (fields.length == 2) {
						out.write(fields[0]+","+AES.encrypt(fields[1])+"\r\n");
					}
				}
				out.flush();
				fw.flush();
	
				System.out.println("Created "+ outfile + ".");
				
			
			} finally {
			    if (fr != null) fr.close();
			    if (fw != null) fw.close();
			}
			
		} catch (Exception e) {
		    System.out.println("" + e.getMessage());
		}
		
	}

}
