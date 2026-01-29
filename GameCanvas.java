import javax.microedition.lcdui.*;
import java.util.Random;

public class GameCanvas extends Canvas implements CommandListener, Runnable {

    private AdventureMIDlet midlet;
    private GameState state;
    private Random rand = new Random();
    private Thread animThread;
    private boolean running = true;
    
    // Système de défilement
    private int narrativeScroll = 0;
    private int choiceScroll = 0;

    // Scenes ENRICHIES
    private String[] scenes = {
        // PHASE MATIN - MYSTERE (0-15)
        "[CHEZ TOI] - 7h15\nTu te reveilles brusquement. Un SMS inconnu: 'Ne va pas au bureau aujourd'hui.' Ton coeur bat vite. Que faire?\n\n1 Ignorer et partir\n2 Appeler le numero\n3 Rester a la maison\n4 Prevenir ton chef",
        
        "[APPEL MYSTERIEUX]\nVoix distordue: 'Ton collegue Marc est en danger. Retrouve-moi au marche Adjame a 8h.' CLICK. Qui etait-ce?\n\n1 Y aller maintenant\n2 Appeler Marc\n3 Appeler la police\n4 Partir au bureau",
        
        "[ARRET SOTRA] - 7h35\nUne vieille dame te fixe: 'Ton grand-pere m'a parle de toi avant de mourir. J'ai quelque chose pour toi.' Elle te tend une enveloppe scellee.\n\n1 Prendre l'enveloppe\n2 Refuser poliment\n3 Lui poser des questions\n4 Partir en courant",
        
        "[ENVELOPPE MYSTERIEUSE]\nDedans: Une vieille photo de ton pere jeune avec un homme inconnu. Au dos: 'La verite est au port.' Et 50000 FCFA en billets.\n\n1 Garder l'argent\n2 Aller au port\n3 Montrer a maman\n4 Continuer au bureau",
        
        "[DANS LE SOTRA]\nUn homme en costume noir monte. Il te regarde intensement. Tu remarques un tatouage etrange sur son poignet. Il s'approche...\n\n1 Descendre au prochain arret\n2 Le fixer aussi\n3 Faire semblant de rien\n4 Lui parler",
        
        "[HOMME MYSTERIEUX]\n'Tu ressembles a quelqu'un que je connais,' dit-il. 'Ton pere travaillait pour nous. Tu veux savoir la verite?' Il te tend une carte.\n\n1 Prendre la carte\n2 Refuser\n3 Demander des explications\n4 Crier pour avoir de l'aide",
        
        "[ADJAME] - 8h00\nTon ami Koffi t'appelle, paniqué: 'Gars! On a cambriole mon appartement! Ils ont tout pris! Peut-tu m'aider?'\n\n1 Le rejoindre maintenant\n2 Lui dire d'appeler la police\n3 Promettre de venir ce soir\n4 Demander plus de details",
        
        "[INCIDENT KOFFI]\n'Ils cherchaient quelque chose de specifique,' dit Koffi. 'Ils ont laisse mon ordi, ma tele... Juste mes vieux documents. C'est bizarre non?'\n\n1 Fouiller avec lui\n2 Appeler la police maintenant\n3 Partir au bureau\n4 Suggerer qu'il se cache",
        
        "[BARRAGE POLICE]\nL'agent te reconnait: 'Eh! Toi! Ton ami Marc a eu un accident ce matin. Il est a l'hopital. Il demande apres toi.'\n\n1 Foncer a l'hopital\n2 Demander ce qui s'est passe\n3 Continuer au bureau\n4 Appeler la famille de Marc",
        
        "[HOPITAL GENERAL]\nMarc est dans un lit, blesse: 'Ecoute... ce n'est pas un accident. Quelqu'un essaie de me faire taire. Le projet au bureau... c'est louche.'\n\n1 Ecouter la suite\n2 Appeler la police\n3 Le rassurer et partir\n4 Lui demander des preuves",
        
        "[REVELATION MARC]\n'Le chef detourne de l'argent. J'ai les preuves dans mon casier au bureau. Numero 47. Code: 2580. Va les chercher avant qu'il ne les trouve!'\n\n1 Y aller maintenant\n2 Refuser, c'est trop dangereux\n3 Demander pourquoi toi\n4 Appeler un journaliste",
        
        "[RUE MARCHANDE]\nUne femme elegante te bloque: 'Je sais ce que tu cherches. Mon mari est implique. Je peux t'aider mais ca va te couter cher.'\n\n1 Ecouter son offre\n2 La repousser\n3 Lui demander son nom\n4 Continuer ton chemin",
        
        "[PROPOSITION]\n'100000 FCFA et je te donne les vrais documents. Ceux de Marc sont des copies. Les originaux sont chez moi. Tu as jusqu'a midi.'\n\n1 Accepter le marche\n2 Refuser\n3 Negocier le prix\n4 La menacer d'appeler la police",
        
        "[PLATEAU] - 8h50\nTu arrives au bureau. Tout semble normal. Mais tu remarques que le bureau du chef est allume. Il n'arrive jamais avant 9h30...\n\n1 Entrer discretement\n2 Aller a ton poste\n3 Faire semblant d'aller aux toilettes\n4 Appeler Marc",
        
        "[BUREAU DU CHEF]\nLa porte est entrouverte. Tu entends des voix: 'Il faut retrouver ce document avant midi sinon on est finis!' Tu reconnais la voix du chef.\n\n1 Ecouter discretement\n2 Entrer brusquement\n3 Partir vite\n4 Enregistrer avec ton telephone",
        
        "[CASIER 47]\nTu ouvres le casier de Marc. Une enveloppe rouge. A l'interieur: des photos compromettantes, des releves bancaires, et... une cle USB etiquetee 'PREUVE'.\n\n1 Tout prendre\n2 Juste prendre la cle USB\n3 Tout photographier\n4 Tout remettre et partir",
        
        // PHASE BUREAU - TENSION (16-25)
        "[TON BUREAU] - 9h05\nTa collegue Marie chuchote: 'Le chef te cherche. Il a l'air furieux. Et il y a deux hommes en costume avec lui. Tu as fait quelque chose?'\n\n1 Aller le voir\n2 Te cacher\n3 Partir discretement\n4 Appeler quelqu'un",
        
        "[CONFRONTATION]\nLe chef te fixe: 'Ou etais-tu ce matin? Marc a eu un accident. C'est etrange que tu arrives en retard le meme jour...'\n\n1 Dire la verite\n2 Mentir\n3 Demander un avocat\n4 Rester silencieux",
        
        "[LES HOMMES]\nUn des hommes s'approche: 'Nous sommes de la police judiciaire. Vous connaissez bien Marc N'Guessan? Quand l'avez-vous vu pour la derniere fois?'\n\n1 Cooperer\n2 Demander un mandat\n3 Dire que tu as vu Marc ce matin\n4 Nier connaitre Marc",
        
        "[FOUILLE]\n'Nous devons fouiller votre bureau,' dit le policier. Tu penses a la cle USB dans ta poche. Si ils la trouvent...\n\n1 La donner volontairement\n2 La cacher vite\n3 L'effacer\n4 Accepter la fouille",
        
        "[MARIE EN SECRET]\nAux toilettes, Marie te rejoint: 'J'ai entendu tout. Je sais que Marc enquetait. Mon pere est avocat. Je peux t'aider mais tu dois me faire confiance.'\n\n1 Lui faire confiance\n2 La remercier et partir\n3 Lui donner la cle USB\n4 Lui demander des preuves",
        
        "[PLAN D'EVASION]\n'Il faut que tu partes maintenant,' dit Marie. 'Ils vont t'arreter. Je connais une sortie secrete. Mais apres, tu seras en fuite...'\n\n1 Partir maintenant\n2 Rester et affronter\n3 Appeler un journaliste\n4 Aller voir le directeur general",
        
        "[FUITE]\nTu cours dans les couloirs. Des voix derriere toi. Tu arrives a l'escalier de service. En bas, une porte qui donne sur la rue...\n\n1 Descendre vite\n2 Monter sur le toit\n3 Te cacher dans un bureau\n4 Appeler Koffi",
        
        "[DANS LA RUE]\nTu es dehors. Libre. Mais pour combien de temps? Ton telephone sonne: numero inconnu. C'est la femme de ce matin: 'Tu as la cle? Rendez-vous au port. Maintenant.'\n\n1 Y aller\n2 Aller a la police\n3 Aller chez tes parents\n4 Quitter Abidjan",
        
        "[LE PORT] - 11h30\nL'odeur de poisson, les cris des vendeurs. La femme t'attend pres d'un conteneur rouge. 'Tu as apporte l'argent?' demande-t-elle.\n\n1 Donner l'argent\n2 Exiger les documents d'abord\n3 Tenter de l'arreter\n4 Partir en courant",
        
        "[ECHANGE]\nElle te tend une enveloppe marron. 'Tout est la. Les comptes offshore, les pots-de-vin, tout. Avec ca, tu peux faire tomber tout le reseau.'\n\n1 Prendre et partir\n2 Verifier le contenu\n3 Lui demander pourquoi\n4 La remercier",
        
        // PHASE SOIR - RESOLUTION (26-35)
        "[CHEZ TOI] - 19h00\nTu regardes tous les documents. C'est enorme. Le chef, le maire, des ministres... Tout le monde est implique. Que faire maintenant?\n\n1 Appeler la presse\n2 Tout envoyer en ligne\n3 Aller a la police\n4 Tout bruler et oublier",
        
        "[JOURNALISTE]\nLe journaliste examine les documents: 'C'est enorme! Mais je dois verifier. Laisse-moi 24h. Et reste cache. Ils vont te chercher.'\n\n1 Lui faire confiance\n2 Reprendre les documents\n3 Faire des copies\n4 Demander protection",
        
        "[CACHE]\nTu passes la nuit chez ta tante au village. Demain, ta vie va changer. Que tu le veuilles ou non...\n\n1 Dormir\n2 Relire les documents\n3 Appeler Marc\n4 Prier",
        
        "[LENDEMAIN MATIN]\nLe telephone explose de notifications. C'est partout: journaux, TV, radio. 'MEGA SCANDALE DE CORRUPTION REVELÉ!' Ton nom n'est pas mentionne.\n\n1 Sortir voir\n2 Rester cache\n3 Appeler le journaliste\n4 Rentrer a Abidjan",
        
        "[EPILOGUE 1]\nDeux mois plus tard. Le chef est en prison. Marc s'est retabli. Tu as un nouveau travail... dans le journalisme. Une nouvelle aventure commence.\n\n5 = Rejouer",
        
        // FINS ALTERNATIVES (31-35)
        "[VICTOIRE SIMPLE]\nTu es arrive au bureau a l'heure. Juste une journee normale finalement. Mais etait-ce vraiment normal?\n\n5 = Rejouer",
        
        "[DEFAITE - FATIGUE]\nTu t'effondres d'epuisement. Les mysteres resteront des mysteres. Parfois, il vaut mieux ne pas savoir...\n\n5 = Rejouer",
        
        "[DEFAITE - ARGENT]\nSans argent, impossible de continuer. Le mystere restera entier. L'histoire continue sans toi.\n\n5 = Rejouer",
        
        "[DEFAITE - RETARD]\nTrop tard. Le chef t'attendait. 'Tu es vire!' Les secrets restent caches.\n\n5 = Rejouer",
        
        "[DEFAITE - CAPTURE]\nLa police t'a arrete. Sans preuves, tu ne peux rien faire. Le reseau de corruption continue...\n\n5 = Rejouer"
    };

    // Navigation entre scenes
    private int[][] sceneChoices = {
        {1, 2, 3, 14},      {8, 2, 9, 14},      {3, 14, 3, 14},     {4, 8, 4, 14},
        {6, 5, 6, 5},       {6, 6, 6, 6},       {7, 6, 7, 6},       {8, 9, 14, 8},
        {9, 9, 14, 9},      {10, 9, 14, 10},    {15, 14, 10, 11},   {12, 11, 12, 11},
        {13, 13, 13, 13},   {14, 14, 14, 14},   {16, 16, 16, 16},   {16, 16, 16, 16},
        {17, 16, 16, 17},   {18, 18, 18, 18},   {19, 19, 19, 19},   {20, 20, 20, 20},
        {21, 20, 21, 20},   {22, 17, 26, 22},   {23, 23, 23, 23},   {24, 26, 24, 24},
        {25, 25, 25, 25},   {26, 26, 26, 26},   {27, 27, 27, 27},   {28, 26, 28, 28},
        {29, 28, 29, 28},   {30, 28, 27, 29},   {-1,-1,-1,-1},      {-1,-1,-1,-1},
        {-1,-1,-1,-1},      {-1,-1,-1,-1},      {-1,-1,-1,-1},      {-1,-1,-1,-1}
    };

    private Command exitCmd  = new Command(LanguageManager.getText("quit"), Command.EXIT,  1);
    private Command resetCmd = new Command(LanguageManager.getText("reset"), Command.SCREEN, 2);

    private int minutesElapsed = 0;
    private final int START_HOUR   = 7;
    private final int START_MINUTE = 15;

    public GameCanvas(AdventureMIDlet midlet) {
        this.midlet = midlet;
        state = new GameState();
        state.load();

        addCommand(exitCmd);
        addCommand(resetCmd);
        setCommandListener(this);

        try {
            setFullScreenMode(true);
        } catch (Exception ignored) {}
        
        animThread = new Thread(this);
        animThread.start();
    }
    
    public void run() {
        while (running) {
            state.animFrame++;
            repaint();
            try { Thread.sleep(100); } catch (Exception e) {}
        }
    }

    protected void paint(Graphics g) {
        int w = getWidth();
        int h = getHeight();

        drawBackground(g, w, h);
        drawSceneVisual(g, w, h);

        // Titre
        g.setColor(0, 0, 0);
        g.setFont(Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        g.drawString(LanguageManager.getText("title"), w/2 + 1, 6, Graphics.HCENTER | Graphics.TOP);
        g.setColor(255, 220, 60);
        g.drawString(LanguageManager.getText("title"), w/2, 5, Graphics.HCENTER | Graphics.TOP);

        drawStats(g, w);
        drawInventory(g, w, h);

        if (state.ending == 0 && state.currentScene < 20) {
            PixelArtManager.drawMiniMap(g, w - 65, 45, state.currentScene, state.gamePhase);
        }

        drawSceneText(g, w, h);
    }
private void drawBackground(Graphics g, int w, int h) {
        if (state.gamePhase == 0) {
            for (int i = 0; i < h; i++) {
                int blue = 180 - (i * 100 / h);
                g.setColor(100 + i/5, 150 + i/4, blue);
                g.drawLine(0, i, w, i);
            }
        } else if (state.gamePhase == 1) {
            for (int i = 0; i < h; i++) {
                int gray = 200 - (i * 40 / h);
                g.setColor(gray, gray+10, gray+20);
                g.drawLine(0, i, w, i);
            }
        } else {
            for (int i = 0; i < h; i++) {
                int val = 80 + (i * 60 / h);
                g.setColor(val+40, val+20, val-20);
                g.drawLine(0, i, w, i);
            }
        }
    }
    
    private void drawSceneVisual(Graphics g, int w, int h) {
        if (state.ending > 0) {
            int cx = w/2, cy = h/2-40;
            switch(state.ending) {
                case 1: PixelArtManager.drawVictoryEnding(g, cx, cy, state.animFrame); break;
                case 2: PixelArtManager.drawFatigueEnding(g, cx, cy, state.animFrame); break;
                case 3: PixelArtManager.drawNoMoneyEnding(g, cx, cy, state.animFrame); break;
                case 4: PixelArtManager.drawLateEnding(g, cx, cy, state.animFrame); break;
            }
        } else {
            int scene = state.currentScene;
            if (scene <= 5) {
                PixelArtManager.drawHomeScene(g, w, h, state.animFrame);
            } else if (scene <= 10) {
                PixelArtManager.drawStreetScene(g, w, h, state.animFrame);
            } else if (scene <= 15) {
                PixelArtManager.drawBusStopScene(g, w, h, state.animFrame);
            } else if (scene <= 25) {
                PixelArtManager.drawOfficeScene(g, w, h, state.animFrame);
            } else {
                PixelArtManager.drawHomeScene(g, w, h, state.animFrame);
            }
            
            if (scene < 16) {
                PixelArtManager.drawPlayerWalking(g, 30, h-110, state.animFrame);
            }
        }
    }
    
    private void drawStats(Graphics g, int w) {
        g.setFont(Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_PLAIN, Font.SIZE_SMALL));

        if (state.argent < 500)      g.setColor(255, 60, 60);
        else if (state.argent < 1000) g.setColor(255, 180, 60);
        else                         g.setColor(100, 255, 100);
        g.drawString("$:" + state.argent, 4, 26, Graphics.TOP | Graphics.LEFT);

        if (state.fatigue > 7)       g.setColor(255, 60, 60);
        else if (state.fatigue > 4)  g.setColor(255, 180, 80);
        else                         g.setColor(120, 220, 255);
        g.drawString("Fat:" + state.fatigue, 68, 26, Graphics.TOP | Graphics.LEFT);
        
        g.setColor(220, 180, 255);
        g.drawString("Rep:" + state.reputation, 4, 40, Graphics.TOP | Graphics.LEFT);

        g.setColor(255, 255, 100);
        g.drawString(getCurrentTime(), w - 52, 26, Graphics.TOP | Graphics.LEFT);
        
        String phaseText = state.gamePhase == 0 ? LanguageManager.getText("morning") : 
                          (state.gamePhase == 1 ? LanguageManager.getText("office") : LanguageManager.getText("evening"));
        g.setColor(255, 200, 100);
        g.drawString(phaseText, w - 52, 40, Graphics.TOP | Graphics.LEFT);
    }
    
    private void drawInventory(Graphics g, int w, int h) {
        int invX = 4;
        int invY = h - 140;
        int invW = 70;
        int invH = 30;
        
        g.setColor(40, 40, 60);
        g.fillRect(invX, invY, invW, invH);
        g.setColor(120, 140, 180);
        g.drawRect(invX, invY, invW, invH);
        
        g.setColor(255, 220, 100);
        g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
        g.drawString(LanguageManager.getText("inventory"), invX+2, invY+2, 0);
        
        int iconX = invX + 4;
        int iconY = invY + 12;
        
        if (state.hasTicket) {
            g.setColor(255, 200, 100);
            g.fillRect(iconX, iconY, 8, 6);
            g.setColor(0, 0, 0);
            g.drawString("T", iconX+1, iconY, 0);
            iconX += 12;
        }
        
        if (state.hasPhoneCharged) {
            g.setColor(100, 200, 255);
            g.fillRect(iconX, iconY, 8, 10);
            iconX += 12;
        }
        
        if (state.hasCleMaison) {
            g.setColor(255, 215, 0);
            g.fillRect(iconX, iconY, 6, 6);
            g.fillRect(iconX+6, iconY+2, 4, 2);
            iconX += 12;
        }
        
        if (state.hasEnvelope) {
            g.setColor(255, 255, 200);
            g.fillRect(iconX, iconY, 10, 7);
            iconX += 12;
        }
        
        if (state.hasUSB) {
            g.setColor(200, 50, 50);
            g.fillRect(iconX, iconY, 8, 4);
            iconX += 12;
        }
    }
    
    private void drawSceneText(Graphics g, int w, int h) {
        // BOITE NARRATION (en haut) - POSITION CORRIGÉE POUR ÊTRE VISIBLE
        int narrationY = h - 108;
        int narrationH = 52;
        
        // Fond OPAQUE avec bordures visibles
        g.setColor(30, 40, 60);
        g.fillRect(4, narrationY, w-8, narrationH);
        
        g.setColor(100, 150, 220);
        g.drawRect(5, narrationY+1, w-10, narrationH-2);
        g.drawRect(6, narrationY+2, w-12, narrationH-4);
        
        // Indicateurs de scroll pour narration
        g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_SMALL));
        if (narrativeScroll > 0) {
            g.setColor(255, 255, 100);
            g.drawString("7", w-14, narrationY+4, 0);
        }
        
        // Texte narration avec scroll
        String fullText = scenes[state.currentScene];
        String mainText = extractMainText(fullText);
        
        g.setColor(255, 255, 255);
        g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
        drawScrollableText(g, mainText, 10, narrationY+6, w-24, narrationH-12, narrativeScroll);
        
        // Indicateur pour défiler vers le bas
        int totalLines = countLines(mainText, w-24);
        int visibleLines = (narrationH-12) / (g.getFont().getHeight() + 1);
        if (totalLines > visibleLines + narrativeScroll) {
            g.setColor(255, 220, 100);
            g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_SMALL));
            g.drawString("*", 10, narrationY + narrationH - 8, 0);
        }
        
        // BOITE CHOIX (en bas) - POSITION CORRIGÉE POUR ÊTRE VISIBLE
        int choiceY = h - 54;
        int choiceH = 52;
        
        // Fond OPAQUE avec bordures visibles
        g.setColor(30, 40, 60);
        g.fillRect(4, choiceY, w-8, choiceH);
        
        g.setColor(220, 150, 100);
        g.drawRect(5, choiceY+1, w-10, choiceH-2);
        g.drawRect(6, choiceY+2, w-12, choiceH-4);
        
        // Indicateurs de scroll pour choix
        g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_SMALL));
        if (choiceScroll > 0) {
            g.setColor(255, 255, 100);
            g.drawString("9", w-14, choiceY+4, 0);
        }
        
        // Afficher les choix
        if (state.ending > 0 || state.currentScene >= 30) {
            g.setColor(255, 255, 100);
            g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_SMALL));
            g.drawString(LanguageManager.getText("replay"), w/2, choiceY+26, Graphics.HCENTER | Graphics.BASELINE);
        } else {
            String[] choices = extractChoices(fullText);
            int numChoices = countChoices(choices);
            
            // Afficher à partir de choiceScroll
            int displayIndex = 0;
            for (int i = choiceScroll; i < numChoices && displayIndex < 3; i++, displayIndex++) {
                int cy = choiceY + 8 + displayIndex*14;
                
                g.setColor(255, 220, 80);
                g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_SMALL));
                g.drawString(String.valueOf(i+1), 10, cy, 0);
                
                g.setColor(255, 255, 255);
                g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
                
                String choiceText = choices[i];
                if (choiceText.length() > 28) {
                    choiceText = choiceText.substring(0, 25) + "...";
                }
                
                g.drawString(choiceText, 22, cy, 0);
            }
            
            // Indicateur plus de choix
            if (numChoices > choiceScroll + 3) {
                g.setColor(255, 220, 100);
                g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_SMALL));
                g.drawString("#", 10, choiceY + choiceH - 8, 0);
            }
        }
    }
private void drawScrollableText(Graphics g, String text, int x, int y, int maxW, int maxH, int scrollOffset) {
        Font f = g.getFont();
        int lineH = f.getHeight() + 1;
        String[] lines = wrapText(text, maxW, f);
        
        int startLine = scrollOffset;
        int cy = y;
        
        for (int i = startLine; i < lines.length && cy < y + maxH; i++) {
            g.drawString(lines[i], x, cy, Graphics.TOP | Graphics.LEFT);
            cy += lineH;
        }
    }
    
    private String[] wrapText(String text, int maxW, Font f) {
        java.util.Vector result = new java.util.Vector();
        String[] paragraphs = split(text, '\n');
        
        for (int p = 0; p < paragraphs.length; p++) {
            String para = paragraphs[p];
            if (f.stringWidth(para) <= maxW) {
                result.addElement(para);
            } else {
                String[] words = split(para, ' ');
                StringBuffer line = new StringBuffer();
                
                for (int w = 0; w < words.length; w++) {
                    String testLine = line.toString();
                    if (testLine.length() > 0) testLine += " ";
                    testLine += words[w];
                    
                    if (f.stringWidth(testLine) <= maxW) {
                        if (line.length() > 0) line.append(" ");
                        line.append(words[w]);
                    } else {
                        if (line.length() > 0) {
                            result.addElement(line.toString());
                        }
                        line = new StringBuffer(words[w]);
                    }
                }
                if (line.length() > 0) {
                    result.addElement(line.toString());
                }
            }
        }
        
        String[] arr = new String[result.size()];
        for (int i = 0; i < result.size(); i++) {
            arr[i] = (String)result.elementAt(i);
        }
        return arr;
    }
    
    private int countLines(String text, int maxW) {
        Font f = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);
        return wrapText(text, maxW, f).length;
    }
    
    private int countChoices(String[] choices) {
        int count = 0;
        for (int i = 0; i < choices.length; i++) {
            if (choices[i] != null && choices[i].length() > 0) count++;
        }
        return count;
    }
    
    private String extractMainText(String fullText) {
        int pos = fullText.indexOf("\n\n");
        if (pos > 0) {
            return fullText.substring(0, pos);
        }
        return fullText;
    }
    
    private String[] extractChoices(String fullText) {
        String[] result = new String[6];
        for (int i = 0; i < 6; i++) result[i] = "";
        
        String[] lines = split(fullText, '\n');
        int choiceCount = 0;
        
        for (int i = 0; i < lines.length && choiceCount < 6; i++) {
            String line = lines[i].trim();
            for (int c = 1; c <= 6; c++) {
                if (line.startsWith(c + " ")) {
                    result[c-1] = line.substring(2);
                    choiceCount++;
                    break;
                }
            }
        }
        
        return result;
    }

    private String getCurrentTime() {
        int totalMin = START_MINUTE + minutesElapsed;
        int h = START_HOUR + (totalMin / 60);
        int m = totalMin % 60;
        return (h < 10 ? "0" : "") + h + ":" + (m < 10 ? "0" : "") + m;
    }

    private String[] split(String s, char delim) {
        java.util.Vector v = new java.util.Vector();
        int start = 0, pos;
        while ((pos = s.indexOf(delim, start)) >= 0) {
            v.addElement(s.substring(start, pos));
            start = pos + 1;
        }
        v.addElement(s.substring(start));
        String[] res = new String[v.size()];
        for (int i = 0; i < v.size(); i++) res[i] = (String)v.elementAt(i);
        return res;
    }

    private void applyChoiceConsequences(int scene, int choice) {
        switch (scene) {
            case 0:
                if (choice == 1) minutesElapsed += 5;
                else if (choice == 2) minutesElapsed += 10;
                else if (choice == 3) minutesElapsed += 60;
                else minutesElapsed += 15;
                break;
            case 3:
                if (choice == 1) state.argent += 50000;
                minutesElapsed += 5;
                break;
            default:
                minutesElapsed += 10;
                if (Math.abs(rand.nextInt()) % 10 < 3) {
                    state.fatigue += 1;
                }
        }
    }

    protected void keyPressed(int keyCode) {
        int choice = -1;

        if (keyCode == KEY_NUM1) choice = 1;
        if (keyCode == KEY_NUM2) choice = 2;
        if (keyCode == KEY_NUM3) choice = 3;
        if (keyCode == KEY_NUM4) choice = 4;
        
        // Touche 7 pour remonter la narration
        if (keyCode == KEY_NUM7) {
            if (narrativeScroll > 0) {
                narrativeScroll--;
            }
            repaint();
            return;
        }
        
        // Touche * pour descendre la narration
        if (keyCode == KEY_STAR) {
            String mainText = extractMainText(scenes[state.currentScene]);
            int totalLines = countLines(mainText, getWidth()-24);
            int visibleLines = 3;
            if (narrativeScroll < totalLines - visibleLines) {
                narrativeScroll++;
            }
            repaint();
            return;
        }
        
        // Touche 9 pour remonter les choix
        if (keyCode == KEY_NUM9) {
            if (choiceScroll > 0) {
                choiceScroll--;
            }
            repaint();
            return;
        }
        
        // Touche # pour descendre les choix
        if (keyCode == KEY_POUND) {
            String[] choices = extractChoices(scenes[state.currentScene]);
            int numChoices = countChoices(choices);
            if (choiceScroll < numChoices - 3) {
                choiceScroll++;
            }
            repaint();
            return;
        }

        if (keyCode == KEY_NUM5 && (state.ending > 0 || state.currentScene >= 30)) {
            state.reset();
            minutesElapsed = 0;
            narrativeScroll = 0;
            choiceScroll = 0;
            repaint();
            return;
        }

        if (choice >= 1 && choice <= 4 && state.ending == 0) {
            // Reset scroll pour nouvelle scene
            narrativeScroll = 0;
            choiceScroll = 0;
            
            applyChoiceConsequences(state.currentScene, choice);
            checkGameOver();
            
            if (state.ending == 0 && state.currentScene < sceneChoices.length) {
                int[] choices = sceneChoices[state.currentScene];
                int next = choices[choice - 1];
                
                if (next >= 0) {
                    state.currentScene = next;
                    
                    if (state.currentScene == 30) {
                        state.ending = 1;
                    }
                }
            }
            
            state.save();
            repaint();
        }
    }
    
    private void checkGameOver() {
        if (state.fatigue >= 10) {
            state.ending = 2;
            state.currentScene = 32;
        } else if (state.argent < -5000) {
            state.ending = 3;
            state.currentScene = 33;
        } else if (minutesElapsed >= 180) {
            state.ending = 4;
            state.currentScene = 34;
        }
    }

    public void commandAction(Command c, Displayable d) {
        if (c == exitCmd) {
            running = false;
            midlet.notifyDestroyed();
        } else if (c == resetCmd) {
            state.reset();
            minutesElapsed = 0;
            narrativeScroll = 0;
            choiceScroll = 0;
            repaint();
        }
    }
}