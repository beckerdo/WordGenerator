package info.danbecker.wordgenerator;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	public static int numPatterns = 2;
	public static String pattern = "ajn";
	
	public static Map<WordType,List<Word>> wordLists = new HashMap<>();
	public static String[] wordSources = null; 

	public static void main(String[] args) throws Exception {
		LOGGER.debug("Hello");
		System.out.println("WordGenerator");
		// Parse command line options
		parseGatherOptions(args);
		System.out.println("numPatterns=" + numPatterns);

		// Init word lists
		for ( WordType wordType : WordType.values()) {
			wordLists.put( wordType, new ArrayList<Word>());
		}
		
		if (null != wordSources) {
			int wordCount = 0;
			for (int i = 0; i < wordSources.length; i++) {
				System.out.println("word source " + i + "=" + wordSources[i]);
				wordCount += scanText(wordLists, wordSources[i]);
			}
			System.out.println("Word count=" + wordCount);
		}
		
		for ( WordType wordType : WordType.values()) {
			System.out.println( wordType.getAbbreviation() + " count=" + wordLists.get( wordType ).size() );
		}
				
		LOGGER.debug("Bye");
	}

	/** Command line options for this application. */
	public static void parseGatherOptions(String[] args) throws ParseException, IOException {
		// Parse the command line arguments
		Options options = new Options();
		// Use dash with shortcut (-h) or -- with name (--help).
		options.addOption("h", "help", false, "print the command line options");
		options.addOption("n", "numPatterns", true, "generates this many patterns");
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
			numPatterns = Integer.parseInt(line.getOptionValue("numWords"));
			System.out.println("numwords=" + numPatterns);
		}
		if (line.hasOption("pattern")) {
			pattern = line.getOptionValue("pattern");
			System.out.println("pattern=" + pattern);
		}

		wordSources = line.getArgs(); // Prints non options.
	}

	/**
	 * Load a word list (one word per line) from the given location. Assumes the
	 * name is a resource from the classpath or JAR.
	 */
	/** Scan file and add words to this list. */
	public static int scanText(Map<WordType,List<Word>> wordLists, String fileName) throws IOException {
		int wordCount = 0;
		Path path = Paths.get(fileName);
		Scanner scanner = new Scanner(path);

		// read file line by line
		scanner.useDelimiter(System.getProperty("line.separator"));
		
		while (scanner.hasNext()) {
			String line = scanner.next();
            Word word = parseWordLine(line);
            if ( null != word ) {
            	System.out.println( "word " + wordCount + "=" + word.text);
            	WordType type = word.type;            	
            	wordLists.get(type).add(word);
            	wordCount++;
            }
		}
		scanner.close();
		return wordCount;
	}
	
	/** Turn a single line into a word. */
    public static Word parseWordLine(String line) {
        Scanner scanner = new Scanner(line);
        // aberrant (adj) - abnormal
        // aberration (n) - anomally; something unusual
        /*
        abc…    Letters
        123…    Digits
        \d      Any Digit
        \D      Any Non-digit character
        .       Any Character
        \.      Period
        [abc]   Only a, b, or c
        [^abc]  Not a, b, nor c
        [a-z]   Characters a to z
        [0-9]   Numbers 0 to 9
        \w      Any Alphanumeric character
        \W      Any Non-alphanumeric character
        {m}     m Repetitions
        {m,n}   m to n Repetitions
        *       Zero or more repetitions
        +       One or more repetitions
        ?       Optional character
        \s      Any Whitespace
        \S      Any Non-whitespace character
        ^…$     Starts and ends
        (…)     Capture Group
        (a(bc)) Capture Sub-group
        (.*)    Capture all
        (ab|cd) Matches ab or cd
        */        
        scanner.useDelimiter("\\s*\\(\\)-\\s*");
        String text = null;
        if ( scanner.hasNext() ) {
           text = scanner.next();
        }
        if ( null == text || text.startsWith( "#" )) {
        	scanner.close();
        	return null;
        }
        // WordType type = scanner.next();
        String desc = null;
        if ( scanner.hasNext() ) {
           desc = scanner.next();
        }
        scanner.close();
       	return new Word( text, WordType.NOUN, desc);
   }

}