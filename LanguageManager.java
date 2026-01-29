public class LanguageManager {
    public static final int FRENCH = 0;
    public static final int ENGLISH = 1;
    
    private static int currentLanguage = FRENCH;
    
    public static void setLanguage(int lang) {
        currentLanguage = lang;
    }
    
    public static int getLanguage() {
        return currentLanguage;
    }
    
    // UI Texts
    public static String getText(String key) {
        if (currentLanguage == FRENCH) {
            return getTextFR(key);
        } else {
            return getTextEN(key);
        }
    }
    
    private static String getTextFR(String key) {
        if (key.equals("title")) return "AVENTURE ABIDJAN";
        if (key.equals("quit")) return "Quitter";
        if (key.equals("reset")) return "Recommencer";
        if (key.equals("money")) return "Argent";
        if (key.equals("fatigue")) return "Fatigue";
        if (key.equals("reputation")) return "Reputation";
        if (key.equals("morning")) return "MATIN";
        if (key.equals("office")) return "BUREAU";
        if (key.equals("evening")) return "SOIR";
        if (key.equals("inventory")) return "INV:";
        if (key.equals("replay")) return "5 = Rejouer";
        if (key.equals("scroll_up")) return "7=Haut";
        if (key.equals("scroll_down")) return "*=Bas";
        if (key.equals("press_any_key")) return "Appuyez sur une touche...";
        if (key.equals("synopsis_title")) return "SYNOPSIS";
        if (key.equals("select_language")) return "CHOISIR LA LANGUE";
        if (key.equals("french")) return "1. Francais";
        if (key.equals("english")) return "2. English";
        return key;
    }
    
    private static String getTextEN(String key) {
        if (key.equals("title")) return "ABIDJAN ADVENTURE";
        if (key.equals("quit")) return "Quit";
        if (key.equals("reset")) return "Reset";
        if (key.equals("money")) return "Money";
        if (key.equals("fatigue")) return "Fatigue";
        if (key.equals("reputation")) return "Reputation";
        if (key.equals("morning")) return "MORNING";
        if (key.equals("office")) return "OFFICE";
        if (key.equals("evening")) return "EVENING";
        if (key.equals("inventory")) return "INV:";
        if (key.equals("replay")) return "5 = Replay";
        if (key.equals("scroll_up")) return "7=Up";
        if (key.equals("scroll_down")) return "*=Down";
        if (key.equals("press_any_key")) return "Press any key...";
        if (key.equals("synopsis_title")) return "SYNOPSIS";
        if (key.equals("select_language")) return "SELECT LANGUAGE";
        if (key.equals("french")) return "1. French";
        if (key.equals("english")) return "2. English";
        return key;
    }
    
    public static String getSynopsis() {
        if (currentLanguage == FRENCH) {
            return "Bienvenue a Abidjan!\n\n" +
                   "Vous incarnez un jeune professionnel\n" +
                   "ivoirien qui doit se rendre au bureau\n" +
                   "avant 9h00.\n\n" +
                   "Mais ce matin n'est pas ordinaire...\n" +
                   "Un SMS mysterieux, des rencontres\n" +
                   "etranges, et un complot qui pourrait\n" +
                   "changer votre vie.\n\n" +
                   "Gerez votre temps, votre argent et\n" +
                   "votre energie pour decouvrir la\n" +
                   "verite et survivre a cette journee\n" +
                   "explosive!\n\n" +
                   "Multiple fins vous attendent selon\n" +
                   "vos choix...";
        } else {
            return "Welcome to Abidjan!\n\n" +
                   "You play as a young Ivorian\n" +
                   "professional who must get to the\n" +
                   "office before 9:00 AM.\n\n" +
                   "But this morning is not ordinary...\n" +
                   "A mysterious SMS, strange encounters,\n" +
                   "and a conspiracy that could change\n" +
                   "your life.\n\n" +
                   "Manage your time, money and energy\n" +
                   "to uncover the truth and survive\n" +
                   "this explosive day!\n\n" +
                   "Multiple endings await based on\n" +
                   "your choices...";
        }
    }
}