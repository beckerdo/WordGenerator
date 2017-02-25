package info.danbecker.wordgenerator;

public enum WordType {	
	NOUN( "n", "n" ),
	VERB( "v", "v" ),
	ADJECTIVE( "j", "adj" ),
	ADVERB( "e", "adv" ),
	PRONOUN( "p", "pro" );

	String shortcut;
	String abbreviation;
	
	private WordType(String shortcut, String abbreviation ) {
    	this.shortcut = shortcut;
    	this.abbreviation = abbreviation;
    }
	public String getShortcut() {
		return shortcut; 
	}
	public String getAbbreviation() {
		return abbreviation; 
	}
}
