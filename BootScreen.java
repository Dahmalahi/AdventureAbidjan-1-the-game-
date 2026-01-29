import javax.microedition.lcdui.*;

public class BootScreen extends Canvas implements Runnable {
    
    private AdventureMIDlet midlet;
    private int animFrame = 0;
    private int currentScreen = 0; // 0=splash, 1=language, 2=synopsis
    private Thread animThread;
    private boolean running = true;
    
    public BootScreen(AdventureMIDlet midlet) {
        this.midlet = midlet;
        
        try {
            setFullScreenMode(true);
        } catch (Exception ignored) {}
        
        animThread = new Thread(this);
        animThread.start();
    }
    
    public void run() {
        while (running) {
            animFrame++;
            repaint();
            try { Thread.sleep(50); } catch (Exception e) {}
        }
    }
    
    public void stop() {
        running = false;
    }
    
    protected void paint(Graphics g) {
        int w = getWidth();
        int h = getHeight();
        
        if (currentScreen == 0) {
            drawSplashScreen(g, w, h);
        } else if (currentScreen == 1) {
            drawLanguageSelection(g, w, h);
        } else if (currentScreen == 2) {
            drawSynopsisScreen(g, w, h);
        }
    }
    
    private void drawSplashScreen(Graphics g, int w, int h) {
        // Gradient background
        for (int i = 0; i < h; i++) {
            int val = 255 - (i * 150 / h);
            g.setColor(255 - i/3, val - 50, 0);
            g.drawLine(0, i, w, i);
        }
        
        int cx = w / 2;
        int cy = h / 2;
        
        // Drapeau de la Côte d'Ivoire en pixel art
        drawIvoryCoastFlag(g, cx - 50, cy - 80, 100, 60, animFrame);
        
        // Titre avec effet
        g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE));
        
        // Ombre
        g.setColor(0, 0, 0);
        g.drawString("AVENTURE", cx + 2, cy - 5 + 2, Graphics.HCENTER | Graphics.BASELINE);
        g.drawString("ABIDJAN", cx + 2, cy + 15 + 2, Graphics.HCENTER | Graphics.BASELINE);
        
        // Texte principal avec animation
        int colorShift = (animFrame % 60);
        g.setColor(255, 200 - colorShift, 50);
        g.drawString("AVENTURE", cx, cy - 5, Graphics.HCENTER | Graphics.BASELINE);
        g.setColor(255 - colorShift/2, 150, 50 + colorShift);
        g.drawString("ABIDJAN", cx, cy + 15, Graphics.HCENTER | Graphics.BASELINE);
        
        // Sous-titre
        g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
        g.setColor(255, 255, 255);
        g.drawString("A Rogue-Lite Text Adventure", cx, cy + 35, Graphics.HCENTER | Graphics.BASELINE);
        
        // Sparkles animés
        drawSparkle(g, cx - 70, cy - 40, animFrame);
        drawSparkle(g, cx + 70, cy - 40, animFrame + 20);
        drawSparkle(g, cx - 60, cy + 50, animFrame + 40);
        drawSparkle(g, cx + 60, cy + 50, animFrame + 30);
        
        // Instructions qui clignotent
        if (animFrame % 40 < 30) {
            g.setColor(255, 255, 100);
            g.drawString(LanguageManager.getText("press_any_key"), cx, h - 30, Graphics.HCENTER | Graphics.BASELINE);
        }
        
        // Version
        g.setColor(200, 200, 200);
        g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
        g.drawString("v2.0", w - 5, h - 5, Graphics.RIGHT | Graphics.BOTTOM);
    }
    
    private void drawIvoryCoastFlag(Graphics g, int x, int y, int w, int h, int frame) {
        // Bordure du drapeau
        g.setColor(50, 50, 50);
        g.fillRect(x - 2, y - 2, w + 4, h + 4);
        
        // Mât
        g.setColor(80, 60, 40);
        g.fillRect(x - 8, y - 20, 4, h + 40);
        
        int stripeW = w / 3;
        
        // Orange (gauche) - avec effet de vague CORRIGÉ
        g.setColor(255, 140, 0);
        for (int i = 0; i < h; i++) {
            int waveOffset = fixedSin((frame + i) * 72) / 91; // Approximation de sin * 3
            g.fillRect(x + waveOffset, y + i, stripeW, 1);
        }
        
        // Blanc (centre) - avec effet de vague CORRIGÉ
        g.setColor(255, 255, 255);
        for (int i = 0; i < h; i++) {
            int waveOffset = fixedSin((frame + i + 20) * 72) / 91;
            g.fillRect(x + stripeW + waveOffset, y + i, stripeW, 1);
        }
        
        // Vert (droite) - avec effet de vague CORRIGÉ
        g.setColor(0, 153, 63);
        for (int i = 0; i < h; i++) {
            int waveOffset = fixedSin((frame + i + 40) * 72) / 91;
            g.fillRect(x + stripeW * 2 + waveOffset, y + i, stripeW, 1);
        }
        
        // Étoile au centre (optionnel, effet spécial)
        if (frame % 100 < 10) {
            g.setColor(255, 215, 0);
            drawStar(g, x + w/2, y + h/2, 8);
        }
    }
    
    private void drawStar(Graphics g, int cx, int cy, int size) {
        // Étoile simple avec lignes
        g.drawLine(cx - size, cy, cx + size, cy);
        g.drawLine(cx, cy - size, cx, cy + size);
        
        int dsize = (size * 7) / 10; // 0.7 * size
        g.drawLine(cx - dsize, cy - dsize, cx + dsize, cy + dsize);
        g.drawLine(cx - dsize, cy + dsize, cx + dsize, cy - dsize);
    }
    
    private void drawSparkle(Graphics g, int x, int y, int offset) {
        int phase = (animFrame + offset) % 30;
        int size = 3 + (phase < 15 ? phase : 30 - phase) / 3;
        
        g.setColor(255, 255, 200);
        g.drawLine(x - size, y, x + size, y);
        g.drawLine(x, y - size, x, y + size);
        
        int dsize = size - 1;
        if (dsize > 0) {
            g.drawLine(x - dsize, y - dsize, x + dsize, y + dsize);
            g.drawLine(x - dsize, y + dsize, x + dsize, y - dsize);
        }
    }
    
    private void drawLanguageSelection(Graphics g, int w, int h) {
        // Background
        for (int i = 0; i < h; i++) {
            int val = 40 + (i * 80 / h);
            g.setColor(val, val + 20, val + 40);
            g.drawLine(0, i, w, i);
        }
        
        int cx = w / 2;
        int cy = h / 2;
        
        // Titre
        g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE));
        g.setColor(255, 200, 50);
        g.drawString(LanguageManager.getText("select_language"), cx, cy - 60, Graphics.HCENTER | Graphics.BASELINE);
        
        // Petit drapeau décoratif
        drawIvoryCoastFlag(g, cx - 30, cy - 100, 60, 40, animFrame);
        
        // Options de langue avec des boîtes
        drawLanguageOption(g, cx, cy - 10, "1. FRANCAIS", animFrame % 60 < 30 && LanguageManager.getLanguage() == LanguageManager.FRENCH);
        drawLanguageOption(g, cx, cy + 30, "2. ENGLISH", animFrame % 60 < 30 && LanguageManager.getLanguage() == LanguageManager.ENGLISH);
        
        // Instructions
        g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
        g.setColor(200, 200, 200);
        g.drawString("Appuyez 1 ou 2 / Press 1 or 2", cx, h - 20, Graphics.HCENTER | Graphics.BASELINE);
    }
    
    private void drawLanguageOption(Graphics g, int cx, int cy, String text, boolean selected) {
        // Boîte
        int boxW = 140;
        int boxH = 30;
        
        if (selected) {
            g.setColor(255, 200, 50);
            g.fillRoundRect(cx - boxW/2, cy - boxH/2, boxW, boxH, 8, 8);
            g.setColor(0, 0, 0);
        } else {
            g.setColor(100, 120, 140);
            g.fillRoundRect(cx - boxW/2, cy - boxH/2, boxW, boxH, 8, 8);
            g.setColor(255, 255, 255);
        }
        
        // Bordure
        g.drawRoundRect(cx - boxW/2, cy - boxH/2, boxW, boxH, 8, 8);
        
        // Texte
        g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        g.drawString(text, cx, cy + 4, Graphics.HCENTER | Graphics.BASELINE);
    }
    
    // Fonction sin en arithmétique fixe (évite Math.sin qui retourne double)
    // Retourne sin(angle) * 256 pour angle en 1/256 de cercle
    private int fixedSin(int angle) {
        angle = angle % 360;
        if (angle < 0) angle += 360;
        
        // Table de sin approximative (0-90 degrés)
        int[] sinTable = {0, 4, 9, 13, 18, 22, 27, 31, 35, 40, 44, 48, 53, 57, 61, 65, 70, 74, 78, 81, 85, 89, 93, 96, 100, 104, 107, 111, 114, 117, 120, 124, 127, 129, 132, 135, 138, 141, 143, 146, 148, 150, 153, 155, 157, 159, 161, 163, 165, 167, 168, 170, 172, 173, 175, 176, 177, 179, 180, 181, 182, 183, 184, 185, 186, 187, 188, 188, 189, 190, 190, 191, 191, 192, 192, 193, 193, 193, 193, 194, 194, 194, 194, 194, 194, 194, 193, 193, 193, 193, 192};
        
        if (angle <= 90) return sinTable[angle];
        if (angle <= 180) return sinTable[180 - angle];
        if (angle <= 270) return -sinTable[angle - 180];
        return -sinTable[360 - angle];
    }
private void drawSynopsisScreen(Graphics g, int w, int h) {
        // Background avec pattern
        for (int i = 0; i < h; i++) {
            int val = 20 + (i * 60 / h);
            g.setColor(val + 40, val + 20, val);
            g.drawLine(0, i, w, i);
        }
        
        // Pattern de grille subtil
        g.setColor(60, 60, 80);
        for (int i = 0; i < w; i += 20) {
            g.drawLine(i, 0, i, h);
        }
        for (int i = 0; i < h; i += 20) {
            g.drawLine(0, i, w, i);
        }
        
        int cx = w / 2;
        
        // Titre
        g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE));
        g.setColor(255, 200, 50);
        g.drawString(LanguageManager.getText("synopsis_title"), cx, 25, Graphics.HCENTER | Graphics.BASELINE);
        
        // Ligne décorative
        g.setColor(255, 140, 0);
        g.fillRect(cx - 60, 30, 120, 2);
        
        // Synopsis dans une boîte
        int boxX = 10;
        int boxY = 45;
        int boxW = w - 20;
        int boxH = h - 90;
        
        // Boîte avec bordure
        g.setColor(30, 35, 50);
        g.fillRoundRect(boxX, boxY, boxW, boxH, 10, 10);
        g.setColor(255, 200, 50);
        g.drawRoundRect(boxX, boxY, boxW, boxH, 10, 10);
        g.setColor(100, 120, 140);
        g.drawRoundRect(boxX + 1, boxY + 1, boxW - 2, boxH - 2, 10, 10);
        
        // Texte du synopsis
        g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
        g.setColor(255, 255, 255);
        
        String synopsis = LanguageManager.getSynopsis();
        drawWrappedText(g, synopsis, boxX + 10, boxY + 10, boxW - 20, boxH - 20);
        
        // Petites icônes décoratives animées
        drawPixelIcon(g, boxX + 15, boxY + boxH - 25, "clock", animFrame);
        drawPixelIcon(g, boxX + boxW - 35, boxY + boxH - 25, "money", animFrame + 20);
        
        // Instructions qui clignotent
        if (animFrame % 40 < 30) {
            g.setColor(255, 255, 100);
            g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_SMALL));
            g.drawString(LanguageManager.getText("press_any_key"), cx, h - 15, Graphics.HCENTER | Graphics.BASELINE);
        }
    }
    
    private void drawPixelIcon(Graphics g, int x, int y, String type, int frame) {
        if (type.equals("clock")) {
            // Horloge animée
            g.setColor(200, 200, 100);
            g.fillArc(x, y, 16, 16, 0, 360);
            g.setColor(40, 40, 40);
            g.fillArc(x + 2, y + 2, 12, 12, 0, 360);
            
            // Aiguilles - CORRIGÉ sans Math.cos/sin
            g.setColor(255, 255, 255);
            int angle = (frame * 6) % 360;
            int handX = x + 8 + (fixedCos(angle) * 4) / 256;
            int handY = y + 8 + (fixedSin(angle) * 4) / 256;
            g.drawLine(x + 8, y + 8, handX, handY);
            
        } else if (type.equals("money")) {
            // Pièce de monnaie
            g.setColor(255, 215, 0);
            g.fillArc(x, y, 16, 16, 0, 360);
            g.setColor(200, 160, 0);
            g.drawArc(x, y, 16, 16, 0, 360);
            g.drawArc(x + 1, y + 1, 14, 14, 0, 360);
            
            // Symbole F
            g.setColor(180, 140, 0);
            g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_SMALL));
            g.drawString("F", x + 5, y + 12, Graphics.LEFT | Graphics.BASELINE);
        }
    }
    
    private void drawWrappedText(Graphics g, String text, int x, int y, int maxW, int maxH) {
        Font f = g.getFont();
        int lineH = f.getHeight() + 2;
        int cy = y;
        
        String[] paragraphs = split(text, '\n');
        
        for (int p = 0; p < paragraphs.length && cy < y + maxH; p++) {
            String para = paragraphs[p];
            
            if (para.trim().length() == 0) {
                cy += lineH / 2;
                continue;
            }
            
            if (f.stringWidth(para) <= maxW) {
                g.drawString(para, x, cy, Graphics.TOP | Graphics.LEFT);
                cy += lineH;
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
                            g.drawString(line.toString(), x, cy, Graphics.TOP | Graphics.LEFT);
                            cy += lineH;
                        }
                        line = new StringBuffer(words[w]);
                    }
                }
                
                if (line.length() > 0 && cy < y + maxH) {
                    g.drawString(line.toString(), x, cy, Graphics.TOP | Graphics.LEFT);
                    cy += lineH;
                }
            }
        }
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
    
    // Fonction cos en arithmétique fixe
    private int fixedCos(int angle) {
        return fixedSin((angle + 90) % 360);
    }
    
    protected void keyPressed(int keyCode) {
        if (currentScreen == 0) {
            // Splash screen -> Language selection
            currentScreen = 1;
            repaint();
        } else if (currentScreen == 1) {
            // Language selection
            if (keyCode == KEY_NUM1) {
                LanguageManager.setLanguage(LanguageManager.FRENCH);
                currentScreen = 2;
                repaint();
            } else if (keyCode == KEY_NUM2) {
                LanguageManager.setLanguage(LanguageManager.ENGLISH);
                currentScreen = 2;
                repaint();
            }
        } else if (currentScreen == 2) {
            // Synopsis -> Start game
            stop();
            midlet.startGame();
        }
    }
}