package info.danbecker.wordgenerator;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * WordGenerator
 * <p>
 * A tool for generating random words, phrases, and sentences
 * 
 * @author <a href="mailto://dan@danbecker.info>Dan Becker</a>
 */
public class WordGenerator {
	public static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(WordGenerator.class);

	// options
	public static int numWords = 3;
	public static String pattern = "ajn";
	
	public static List<Word> wordList = new ArrayList<>();

	public static void main(String[] args) throws Exception {
		LOGGER.debug("Hello");
		// Parse command line options
		parseGatherOptions(args);
		System.out.println("WordGenerator numWords=" + numWords);

		// load dictionary
		// loadList( WordGenerator.wordList, dictionaryName );

		// String passPhrase = getPassPhrase( WordGenerator.wordList, numWords,
		// specialCharEntropy );
		// System.out.println( "WordGenerator passPhrase=" + passPhrase );
		LOGGER.debug("Bye");
	}

	/** Command line options for this application. */
	public static void parseGatherOptions(String[] args) throws ParseException, IOException {
		// Parse the command line arguments
		Options options = new Options();
		// Use dash with shortcut (-h) or -- with name (--help).
		options.addOption("h", "help", false, "print the command line options");
		options.addOption("n", "numWords", true, "generates this many words");
		options.addOption("p", "pattern", true,
				"use a pattern with n=noun, v=verb, j=adjective, e=adverb, a=article, such as \"ajn\"");

		CommandLineParser cliParser = new DefaultParser();
		CommandLine line = cliParser.parse(options, args);

		// // Gather command line arguments for execution
		if (line.hasOption("help")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("java -jar wordgenerator.jar <options> info.danbecker.wordgenerator.WordGenerator",
					options);
			System.exit(0);
		}
		if (line.hasOption("numWords")) {
			numWords = Integer.parseInt(line.getOptionValue("numWords"));
			System.out.println("numwords=" + numWords);
		}
		if (line.hasOption("pattern")) {
			pattern = line.getOptionValue("pattern");
			System.out.println("pattern=" + pattern);
		}

		String [] parsedArg = line.getArgs(); // Prints non options.
		int wordCount = 0;
		for ( int i = 0; i < parsedArg.length; i++ ) {
			System.out.println( "" + i + " " + parsedArg[ i ] );
			wordCount += scanText( wordList, parsedArg[ i ]);
		}
		System.out.println(  "Word count=" + wordCount);
	}

	/**
	 * Load a word list (one word per line) from the given location. Assumes the
	 * name is a resource from the classpath or JAR.
	 */
	/** Scan file and add words to this list. */
	public static int scanText(List<Word> wordList, String fileName) throws IOException {
		int count = 0;
		Path path = Paths.get(fileName);
		Scanner scanner = new Scanner(path);

		// read file line by line
		scanner.useDelimiter(System.getProperty("line.separator"));
		while (scanner.hasNext()) {
			String line = scanner.next();
			System.out.println("Lines: " + line);
            Word word = parseWordLine(line);
            if ( null != word ) {
            	wordList.add(word);
            	count++;
            }
		}
		scanner.close();
		return count;
	}
	
	/** Turn a single line into a word. */
    public static Word parseWordLine(String line) {
        Scanner scanner = new Scanner(line);
        scanner.useDelimiter("\\s* \\s*");
        String text = null;
        if ( scanner.hasNext() ) {
           text = scanner.next();
        }
        // WordType type = scanner.next();
        String desc = null;
        if ( scanner.hasNext() ) {
           desc = scanner.next();
        }
        scanner.close();
        if ( null != text ) {
        	return new Word( text, WordType.NOUN, desc);
        }
        return null;
   }

}