import java.io.File;
import java.lang.String;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

//make args[0] = OutputDir
//make args[1] = tandemExe
//make args[2] = tandem2XMLExe

public class CreateTandemBat {
	static String outputDirStr;
	static String tandemExe;
	static String tandem2XMLExe;
	static StringBuffer tandemBatFile = new StringBuffer();

	public static void main( String [] args ) {
		String tandemBatFilePath;
		outputDirStr = args[0];
		tandemExe = args[1].replace(".exe", "");
		tandem2XMLExe = args[2].replace(".exe", "");
		
		File outputDir = new File(outputDirStr);
		File[] files = outputDir.listFiles();
		
		for( File file : files ) {
			if( !file.isDirectory() && (file.getAbsolutePath().contains(".tandem.params")) ) {
				tandemBatFile.append("\r\n");
				tandemBatFile.append(tandemExe);
				tandemBatFile.append(" ");
				tandemBatFile.append(file.getAbsolutePath());
				tandemBatFile.append("\r\n");
				tandemBatFile.append("\r\n");
				tandemBatFile.append(tandem2XMLExe);
				tandemBatFile.append(" ");
				tandemBatFile.append(file.getAbsolutePath().replace(".tandem.params", ".tandem"));
				tandemBatFile.append(" ");
				tandemBatFile.append(file.getAbsolutePath().replace(".tandem.params", ".tandem.pep.xml"));
				tandemBatFile.append("\r\n");
			}
		}
		
		tandemBatFilePath = createTandemBatPath();
		writeToFile( tandemBatFilePath );
				
	}
	
	
	public static String createTandemBatPath() {
		String tandemBatFilePath = outputDirStr + "\\" + "tandemSearch.bat";
		return tandemBatFilePath;
	}
	
	
	public static void writeToFile( String tandemBatFilePath ) {
		try {
			BufferedWriter bufwriter = new BufferedWriter( new FileWriter(tandemBatFilePath) );
			bufwriter.write(tandemBatFile.toString());
			bufwriter.close();
		} catch( Exception e ) {
			e.printStackTrace();
		}
	}
	
}
