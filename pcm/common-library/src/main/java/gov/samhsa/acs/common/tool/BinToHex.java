package gov.samhsa.acs.common.tool;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//converts file data into hexvalues

public class BinToHex
{
    private final static String[] hexSymbols = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    public final static int BITS_PER_HEX_DIGIT = 4;
	/** The logger. */
	private final static Logger logger = LoggerFactory
			.getLogger(BinToHex.class);

    public static String toHexFromByte(final byte b)
    {
    	byte leftSymbol = (byte)((b >>> BITS_PER_HEX_DIGIT) & 0x0f);
    	byte rightSymbol = (byte)(b & 0x0f);

    	return (hexSymbols[leftSymbol] + hexSymbols[rightSymbol]);
    }

    public static String toHexFromBytes(final byte[] bytes)
    {
    	if(bytes == null || bytes.length == 0)
    	{
    		return ("");
    	}

    	// there are 2 hex digits per byte
    	StringBuilder hexBuffer = new StringBuilder(bytes.length * 2);

    	// for each byte, convert it to hex and append it to the buffer
    	for(int i = 0; i < bytes.length; i++)
    	{
    		hexBuffer.append(toHexFromByte(bytes[i]));
    	}

    	return (hexBuffer.toString());
    }

    public static byte[] readFile(File file) throws IOException {
        // Open file
        RandomAccessFile f = new RandomAccessFile(file, "r");
        try {
            // Get and check length
            long longlength = f.length();
            int length = (int) longlength;
            if (length != longlength)
                throw new IOException("File size >= 2 GB");
            // Read file and return data
            byte[] data = new byte[length];
            f.readFully(data);
            return data;
        } finally {
            f.close();
        }
    }  
    
    public static void main(final String[] args) throws IOException
    {
    	try
    	{
    		
    		// Input : C:\\Sadhana\\FEi\\Sprint-24-ACS\\defaultconsentxacml.xml   
    		// output: C:\\Sadhana\\FEi\\Sprint-24-ACS\\output.xml
    	//	FileInputStream fis = new FileInputStream(new File(args[0]));
    		logger.debug("input file that is converted to hex" + args[0]);
    		logger.debug("output file that has hex string" + args[1]);
    		BufferedWriter fos = new BufferedWriter(new FileWriter(new File(args[1])));

    		byte[] bytes = readFile(new File(args[0]));
    		fos.write(toHexFromBytes(bytes));
    		logger.debug("Complete");
    		fos.flush();
    		fos.close();
     	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
}