package info.danbecker.wordgenerator;

/** A word with required text, optional type, and optional description. */
public class Word {
	public String text;
	public String type; // can be a comma separated list if it can be noun,verb,adj,adv,etc.
	public String desc;
	
	public Word( String text, String type, String desc ) {
		this.text = text;
		this.type = type;
		this.desc = desc;
	}
}
