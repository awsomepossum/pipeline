import java.io.File;
import java.lang.String;

public class MSGFSearch {
  
	public static void main( String [] args ) {
   
		//outputDir = args[0]
		//fastaFile = args[1]
		//ppm = args[2]
		//enzyme = args[3]
		//modsFileLocation = args[4]
	
		File outputDir = new File(args[0]);
		File[] files = outputDir.listFiles();
		int threadsToUse = 10;
		
		for( File file : files ) {

			if( !file.isDirectory() && ( file.getAbsolutePath().contains("Q1.mzXML") || file.getAbsolutePath().contains("Q2.mzXML") || file.getAbsolutePath().contains("Q3.mzXML") ) ) {
				//System.out.print( "java -d64 -Xmx10000M -jar C:\\MSGFplus.20140716\\msgfplus.jar" );
				System.out.print( "java -d64 -Xmx10000M -jar C:\\Jesse\\MSGFplus.20140716\\msgfplus.jar" );
				System.out.print( " -s " + file.getAbsolutePath() );
				System.out.print( " -d " + args[1] );
				System.out.print( " -o " + args[0] + "\\" + file.getName().replace(".mzXML", "_MSGF.mzid") );
				System.out.print( " -thread " + threadsToUse );
				System.out.print( " -t " + args[2] );
				System.out.print( " -tda 1 -ti -1,2 -inst 2 -m 3 ");
				System.out.print( " -e " + args[3] );
				System.out.print( " -ntt 1 " );
				System.out.print( " -mod " + args[4] );		
				System.out.print( "\r\n" );
			}
			
		}
	}
}