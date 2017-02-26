package info.danbecker.wordgenerator;

public enum WordType {	
	NOUN( "n", "n" ),
	VERB( "v", "v" ),
	ADJECTIVE( "j", "adj" ),
	ADVERB( "e", "adv" ),
	PRONOUN( "p", "pro" ),
	OTHER( "o", "oth");

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
	
	/** Returns an enum based on the given shortcut or abbreviation. */
	public static WordType fromAbbreviation( String typeStr ) {
        WordType type = WordType.OTHER;
        if ( null != typeStr ) {
            switch ( typeStr.toLowerCase() ) {
        		case "n": type = WordType.NOUN; break;
        		case "v": type = WordType.VERB; break;
        		case "adj": type = WordType.ADJECTIVE; break;
        		case "j": type = WordType.ADJECTIVE; break;
        		case "adv": type = WordType.ADVERB; break;
        		case "e": type = WordType.ADVERB; break;
        		case "pro": type = WordType.PRONOUN; break;
        		case "p": type = WordType.PRONOUN; break;
            }        	
        }
        return type;
	}
}
