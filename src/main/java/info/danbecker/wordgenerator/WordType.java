package info.danbecker.wordgenerator;

public enum WordType {	
	NOUN( "n" ),
	VERB( "v" ),
	ADJECTIVE( "j" ),
	ADVERB( "e" ),
	PRONOUN( "p" );

	String shortcut;
	
	private WordType(String shortcut ) {
    	this.shortcut = shortcut;
    }
	public String getShortcut() {
		return shortcut; 
	}
}
