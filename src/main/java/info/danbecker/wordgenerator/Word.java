package info.danbecker.wordgenerator;

/** A word with required text, optional type, and optional description. */
public class Word {
	public String text;
	public WordType type;
	public String desc;
	
	public Word( String text, WordType type, String desc ) {
		this.text = text;
		this.type = type;
		this.desc = desc;
	}
}
