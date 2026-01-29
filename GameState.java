import javax.microedition.rms.*;

public class GameState {

    // Progression
    public int currentScene = 0;
    public int gamePhase = 0; // 0=matin, 1=bureau, 2=soir
    
    // Ressources
    public int argent = 2500;
    public int fatigue = 0;
    public int reputation = 2;
    public int bossHappiness = 50;
    
    // Inventaire & statuts
    public boolean hasTicket = false;
    public boolean hasPhoneCharged = true;
    public boolean hasCleMaison = false;
    public boolean hadBreakfast = false;
    public boolean metFriend = false;
    public boolean helpedOldLady = false;
    public boolean boughtLunch = false;
    // --- AJOUTS POUR CORRIGER L'ERREUR ---
    public boolean hasEnvelope = false; 
    public boolean hasUSB = false;
    // -------------------------------------
    
    // Fin de jeu
    public int ending = 0;
    
    // Animation
    public int animFrame = 0;

    private static final String RECORD_NAME = "abidjan_adventure_v2";

    public void save() {
        try {
            RecordStore rs = RecordStore.openRecordStore(RECORD_NAME, true);
            // Ajout des nouvelles variables dans la chaîne de sauvegarde
            String data = currentScene + "," +
                          gamePhase + "," +
                          argent + "," +
                          fatigue + "," +
                          reputation + "," +
                          bossHappiness + "," +
                          (hasTicket ? "1" : "0") + "," +
                          (hasPhoneCharged ? "1" : "0") + "," +
                          (hasCleMaison ? "1" : "0") + "," +
                          (hadBreakfast ? "1" : "0") + "," +
                          (metFriend ? "1" : "0") + "," +
                          (helpedOldLady ? "1" : "0") + "," +
                          (boughtLunch ? "1" : "0") + "," +
                          (hasEnvelope ? "1" : "0") + "," + // Ajouté
                          (hasUSB ? "1" : "0") + "," +      // Ajouté
                          ending;

            byte[] bytes = data.getBytes("ISO-8859-1");

            if (rs.getNumRecords() == 0) {
                rs.addRecord(bytes, 0, bytes.length);
            } else {
                rs.setRecord(1, bytes, 0, bytes.length);
            }
            rs.closeRecordStore();
        } catch (Exception e) {}
    }

    public void load() {
        try {
            RecordStore rs = RecordStore.openRecordStore(RECORD_NAME, true);
            if (rs.getNumRecords() > 0) {
                byte[] bytes = rs.getRecord(1);
                String data = new String(bytes, "ISO-8859-1");
                String[] parts = splitSimple(data, ',');

                // La taille vérifiée passe de 14 à 16 car on a ajouté 2 variables
                if (parts.length >= 16) {
                    currentScene    = parseIntSafe(parts[0], 0);
                    gamePhase       = parseIntSafe(parts[1], 0);
                    argent          = parseIntSafe(parts[2], 2500);
                    fatigue         = parseIntSafe(parts[3], 0);
                    reputation      = parseIntSafe(parts[4], 2);
                    bossHappiness   = parseIntSafe(parts[5], 50);
                    hasTicket       = "1".equals(parts[6]);
                    hasPhoneCharged = "1".equals(parts[7]);
                    hasCleMaison    = "1".equals(parts[8]);
                    hadBreakfast    = "1".equals(parts[9]);
                    metFriend       = "1".equals(parts[10]);
                    helpedOldLady   = "1".equals(parts[11]);
                    boughtLunch     = "1".equals(parts[12]);
                    // Récupération des nouvelles variables
                    hasEnvelope     = "1".equals(parts[13]);
                    hasUSB          = "1".equals(parts[14]);
                    // Ending est décalé à l'index 15
                    ending          = parseIntSafe(parts[15], 0);
                }
            }
            rs.closeRecordStore();
        } catch (Exception e) {}
    }

    public void reset() {
        currentScene = 0;
        gamePhase = 0;
        argent = 2500;
        fatigue = 0;
        reputation = 2;
        bossHappiness = 50;
        hasTicket = false;
        hasPhoneCharged = true;
        hasCleMaison = false;
        hadBreakfast = false;
        metFriend = false;
        helpedOldLady = false;
        boughtLunch = false;
        // Réinitialisation des ajouts
        hasEnvelope = false;
        hasUSB = false;
        
        ending = 0;
        animFrame = 0;
        save();
    }

    private static String[] splitSimple(String str, char delimiter) {
        if (str == null || str.length() == 0) return new String[0];
        int count = 1;
        for (int i = 0; i < str.length(); i++) if (str.charAt(i) == delimiter) count++;
        String[] result = new String[count];
        int pos = 0, start = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == delimiter) {
                result[pos++] = str.substring(start, i);
                start = i + 1;
            }
        }
        result[pos] = str.substring(start);
        return result;
    }

    private static int parseIntSafe(String s, int def) {
        try { return Integer.parseInt(s.trim()); } catch (Exception e) { return def; }
    }
}