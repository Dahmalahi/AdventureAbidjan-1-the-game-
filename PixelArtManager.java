import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Font;

public class PixelArtManager {

    // Table de sin pour arithmétique fixe (évite Math.sin qui retourne double)
    // Valeurs pour 0-90 degrés, multipliées par 256
    private static final int[] SIN_TABLE = {
        0, 4, 9, 13, 18, 22, 27, 31, 35, 40, 44, 48, 53, 57, 61, 65, 70, 74, 78, 81, 
        85, 89, 93, 96, 100, 104, 107, 111, 114, 117, 120, 124, 127, 129, 132, 135, 
        138, 141, 143, 146, 148, 150, 153, 155, 157, 159, 161, 163, 165, 167, 168, 
        170, 172, 173, 175, 176, 177, 179, 180, 181, 182, 183, 184, 185, 186, 187, 
        188, 188, 189, 190, 190, 191, 191, 192, 192, 193, 193, 193, 193, 194, 194, 
        194, 194, 194, 194, 194, 193, 193, 193, 193, 192, 192, 191, 191, 190, 190, 
        189, 188, 188, 187, 186, 185, 184, 183, 182, 181, 180, 179, 177, 176, 175, 
        173, 172, 170, 168, 167, 165, 163, 161, 159, 157, 155, 153, 150, 148, 146, 
        143, 141, 138, 135, 132, 129, 127, 124, 120, 117, 114, 111, 107, 104, 100, 
        96, 93, 89, 85, 81, 78, 74, 70, 65, 61, 57, 53, 48, 44, 40, 35, 31, 27, 22, 
        18, 13, 9, 4, 0
    };
    
    private static int fixedSin(int angle) {
        angle = angle % 360;
        if (angle < 0) angle += 360;
        
        if (angle <= 90) return SIN_TABLE[angle];
        if (angle <= 180) return SIN_TABLE[180 - angle];
        if (angle <= 270) return -SIN_TABLE[angle - 180];
        return -SIN_TABLE[360 - angle];
    }
    
    private static int fixedCos(int angle) {
        return fixedSin((angle + 90) % 360);
    }

    // ================================================================
    // ANIMATIONS DE PERSONNAGES AMÉLIORÉES
    // ================================================================
    
    public static void drawPlayerWalking(Graphics g, int x, int y, int frame) {
        // Ombre dynamique
        int shadowSize = 16 + (frame % 10 < 5 ? 1 : -1);
        g.setColor(30, 30, 50);
        g.fillArc(x - shadowSize/2, y + 24, shadowSize, 6, 0, 360);
        
        // Corps avec ombrage
        g.setColor(60, 120, 200);
        g.fillRect(x - 8, y + 8, 16, 16);
        g.setColor(40, 90, 160);
        g.fillRect(x - 8, y + 16, 16, 8);
        
        // Détails du costume
        g.setColor(80, 140, 220);
        g.drawLine(x, y + 8, x, y + 24);
        
        // Tête avec ombrage
        g.setColor(220, 170, 130);
        g.fillArc(x - 6, y, 12, 14, 0, 360);
        g.setColor(200, 150, 110);
        g.fillArc(x - 6, y + 7, 12, 7, 180, 180);
        
        // Cheveux détaillés
        g.setColor(20, 15, 10);
        g.fillArc(x - 7, y - 2, 14, 10, 0, 180);
        for (int i = 0; i < 3; i++) {
            g.drawLine(x - 5 + i*3, y - 2, x - 5 + i*3, y + 2);
        }
        
        // Yeux avec animation de clignement
        g.setColor(0, 0, 0);
        if (frame % 30 < 28) {
            g.fillRect(x - 4, y + 5, 2, 2);
            g.fillRect(x + 2, y + 5, 2, 2);
            g.setColor(255, 255, 255);
            g.fillRect(x - 3, y + 5, 1, 1);
            g.fillRect(x + 3, y + 5, 1, 1);
        } else {
            g.drawLine(x - 4, y + 6, x - 2, y + 6);
            g.drawLine(x + 2, y + 6, x + 4, y + 6);
        }
        
        // Sourire subtil
        g.setColor(180, 100, 100);
        g.drawArc(x - 4, y + 8, 8, 4, 200, 140);
        
        // Jambes avec animation de marche
        g.setColor(30, 60, 100);
        int legOffset = (frame % 20 < 10) ? 3 : -3;
        
        // Jambe gauche
        g.fillRect(x - 6, y + 20, 5, 10);
        if (legOffset > 0) {
            g.fillRect(x - 6, y + 28, 5, 3);
        }
        
        // Jambe droite
        g.fillRect(x + 1, y + 20 - legOffset/2, 5, 10);
        if (legOffset < 0) {
            g.fillRect(x + 1, y + 28, 5, 3);
        }
        
        // Bras avec balancement
        g.setColor(220, 170, 130);
        int armSwing = (frame % 20 < 10) ? 2 : -2;
        
        // Bras gauche
        g.fillRect(x - 10, y + 10 + armSwing, 3, 10);
        g.fillArc(x - 11, y + 18 + armSwing, 4, 4, 0, 360);
        
        // Bras droit
        g.fillRect(x + 7, y + 10 - armSwing, 3, 10);
        g.fillArc(x + 6, y + 18 - armSwing, 4, 4, 0, 360);
        
        // Accessoire: sac à dos
        g.setColor(100, 60, 40);
        g.fillRect(x - 9, y + 9, 3, 8);
        g.setColor(120, 80, 60);
        g.fillRect(x - 8, y + 10, 2, 2);
    }
    
    public static void drawNPCTalking(Graphics g, int x, int y, int frame, int skinTone, int shirtColor) {
        // Ombre
        g.setColor(30, 30, 50);
        g.fillArc(x - 8, y + 24, 16, 6, 0, 360);
        
        // Corps avec détails
        g.setColor(shirtColor, shirtColor/2, shirtColor/3);
        g.fillRect(x - 8, y + 8, 16, 18);
        g.setColor(shirtColor - 30, shirtColor/2 - 15, shirtColor/3 - 10);
        g.fillRect(x - 8, y + 16, 16, 10);
        
        // Col de chemise
        g.setColor(shirtColor + 40, shirtColor/2 + 20, shirtColor/3 + 15);
        g.drawLine(x - 4, y + 8, x - 1, y + 12);
        g.drawLine(x + 4, y + 8, x + 1, y + 12);
        
        // Tête avec ombrage
        g.setColor(skinTone, skinTone - 30, skinTone - 60);
        g.fillArc(x - 6, y, 12, 14, 0, 360);
        g.setColor(skinTone - 20, skinTone - 50, skinTone - 80);
        g.fillArc(x - 6, y + 7, 12, 7, 180, 180);
        
        // Cheveux variés
        g.setColor(15, 10, 5);
        g.fillArc(x - 7, y - 2, 14, 10, 0, 180);
        if (frame % 3 == 0) {
            g.fillRect(x - 7, y, 14, 4);
        }
        
        // Yeux expressifs
        g.setColor(0, 0, 0);
        g.fillRect(x - 4, y + 5, 2, 3);
        g.fillRect(x + 2, y + 5, 2, 3);
        g.setColor(255, 255, 255);
        g.fillRect(x - 3, y + 5, 1, 1);
        g.fillRect(x + 3, y + 5, 1, 1);
        
        // Bouche qui parle avec animation - CORRIGÉ: fillArc au lieu de fillOval
        g.setColor(160, 60, 60);
        int mouthPhase = frame % 16;
        if (mouthPhase < 4) {
            g.fillArc(x - 3, y + 9, 6, 5, 0, -180);
        } else if (mouthPhase < 8) {
            g.fillArc(x - 2, y + 9, 4, 6, 0, 360); // CHANGÉ: fillOval -> fillArc
        } else if (mouthPhase < 12) {
            g.fillArc(x - 3, y + 9, 6, 4, 0, -180);
        } else {
            g.drawLine(x - 3, y + 11, x + 3, y + 11);
        }
        
        // Bras gestuels
        g.setColor(skinTone - 20, skinTone - 50, skinTone - 80);
        int gesture = (frame / 10) % 4;
        
        if (gesture == 0 || gesture == 2) {
            g.fillRect(x - 10, y + 10, 3, 10);
            g.fillRect(x + 7, y + 10, 3, 10);
        } else if (gesture == 1) {
            // Bras levé
            g.fillRect(x - 10, y + 6, 3, 8);
            g.fillRect(x + 7, y + 10, 3, 10);
        } else {
            // Autre bras levé
            g.fillRect(x - 10, y + 10, 3, 10);
            g.fillRect(x + 7, y + 6, 3, 8);
        }
        
        // Jambes
        g.setColor(40, 60, 80);
        g.fillRect(x - 6, y + 22, 5, 10);
        g.fillRect(x + 1, y + 22, 5, 10);
    }
// ================================================================
    // SCÈNES DÉTAILLÉES AMÉLIORÉES
    // ================================================================
    
    public static void drawHomeScene(Graphics g, int w, int h, int frame) {
        // Ciel matin avec nuages
        for (int i = 0; i < h/2; i++) {
            int blue = 140 + (i * 80 / (h/2));
            int orange = 120 + (i * 40 / (h/2));
            g.setColor(orange, 100 + i/2, blue);
            g.drawLine(0, i, w, i);
        }
        
        // Nuages animés
        drawCloud(g, 20 + (frame % 200), 20, 30, 15);
        drawCloud(g, 100 + (frame % 150), 35, 25, 12);
        
        // Sol avec herbe
        g.setColor(80, 120, 60);
        g.fillRect(0, h/2, w, h/2);
        
        // Herbe animée
        g.setColor(60, 100, 40);
        for (int i = 0; i < w; i += 8) {
            int grassH = 3 + (frame + i) % 4;
            g.drawLine(i, h/2 + 5, i, h/2 + 5 + grassH);
        }
        
        // Maison détaillée
        int hx = 20, hy = h/2 - 60;
        
        // Murs avec texture
        g.setColor(200, 150, 100);
        g.fillRect(hx, hy, 60, 55);
        g.setColor(180, 130, 80);
        for (int i = 0; i < 55; i += 10) {
            g.drawLine(hx, hy + i, hx + 60, hy + i);
        }
        
        // Toit avec tuiles
        g.setColor(160, 80, 40);
        g.fillTriangle(hx - 5, hy, hx + 30, hy - 35, hx + 65, hy);
        g.setColor(140, 60, 20);
        for (int i = 0; i < 7; i++) {
            g.drawLine(hx + i*10, hy - i*5, hx + i*10, hy);
        }
        
        // Cheminée avec fumée
        g.setColor(180, 100, 60);
        g.fillRect(hx + 45, hy - 25, 8, 15);
        g.setColor(200, 200, 220);
        int smokeOffset = frame % 30;
        drawSmoke(g, hx + 49, hy - 25, smokeOffset);
        
        // Fenêtres avec reflets
        g.setColor(200, 220, 255);
        g.fillRect(hx + 10, hy + 12, 16, 16);
        g.fillRect(hx + 34, hy + 12, 16, 16);
        
        // Reflets animés
        g.setColor(255, 255, 255);
        if (frame % 60 < 30) {
            g.fillRect(hx + 12, hy + 14, 4, 4);
            g.fillRect(hx + 36, hy + 14, 4, 4);
        }
        
        // Croisillons
        g.setColor(100, 80, 60);
        g.drawLine(hx + 18, hy + 12, hx + 18, hy + 28);
        g.drawLine(hx + 10, hy + 20, hx + 26, hy + 20);
        g.drawLine(hx + 42, hy + 12, hx + 42, hy + 28);
        g.drawLine(hx + 34, hy + 20, hx + 50, hy + 20);
        
        // Porte avec détails
        g.setColor(120, 60, 30);
        g.fillRect(hx + 22, hy + 32, 16, 23);
        g.setColor(100, 40, 10);
        for (int i = 0; i < 20; i += 5) {
            g.drawLine(hx + 22, hy + 34 + i, hx + 38, hy + 34 + i);
        }
        
        // Poignée
        g.setColor(200, 180, 100);
        g.fillArc(hx + 33, hy + 44, 3, 3, 0, 360); // CORRIGÉ: fillOval -> fillArc
        
        // Soleil levant avec rayons - SIMPLIFIÉ sans Math
        int sunX = w - 60, sunY = 15 + (frame % 60) / 6;
        g.setColor(255, 240, 120);
        g.fillArc(sunX, sunY, 45, 45, 0, 360);
        g.setColor(255, 220, 80);
        g.fillArc(sunX + 3, sunY + 3, 39, 39, 0, 360);
        
        // Rayons du soleil simplifiés
        g.setColor(255, 240, 120);
        int rayLen = 25 + (frame % 10);
        // 8 rayons aux angles fixes
        g.drawLine(sunX + 22, sunY + 22, sunX + 22, sunY + 22 - rayLen); // Haut
        g.drawLine(sunX + 22, sunY + 22, sunX + 22 + rayLen, sunY + 22); // Droite
        g.drawLine(sunX + 22, sunY + 22, sunX + 22, sunY + 22 + rayLen); // Bas
        g.drawLine(sunX + 22, sunY + 22, sunX + 22 - rayLen, sunY + 22); // Gauche
        g.drawLine(sunX + 22, sunY + 22, sunX + 22 + rayLen/2, sunY + 22 - rayLen/2); // Diagonales
        g.drawLine(sunX + 22, sunY + 22, sunX + 22 + rayLen/2, sunY + 22 + rayLen/2);
        g.drawLine(sunX + 22, sunY + 22, sunX + 22 - rayLen/2, sunY + 22 - rayLen/2);
        g.drawLine(sunX + 22, sunY + 22, sunX + 22 - rayLen/2, sunY + 22 + rayLen/2);
        
        // Chemin vers la maison
        g.setColor(160, 140, 100);
        int pathY = hy + 55;
        for (int i = 0; i < 5; i++) {
            g.fillRect(hx + 28 - i, pathY + i*3, 6 + i*2, 3);
        }
        
        // Arbuste décoratif
        g.setColor(40, 80, 40);
        g.fillArc(hx - 15, hy + 40, 20, 20, 0, 360);
        g.fillArc(hx - 20, hy + 45, 15, 15, 0, 360);
        g.setColor(60, 100, 60);
        g.fillArc(hx - 12, hy + 42, 12, 12, 0, 360);
    }
    
    public static void drawStreetScene(Graphics g, int w, int h, int frame) {
        // Ciel avec gradient
        for (int i = 0; i < h/2; i++) {
            int val = 100 + (i * 100 / (h/2));
            g.setColor(80 + i/4, 130 + i/3, val);
            g.drawLine(0, i, w, i);
        }
        
        // Soleil
        g.setColor(255, 240, 150);
        g.fillArc(w - 50, 10, 35, 35, 0, 360);
        
        // Route avec texture
        g.setColor(60, 60, 70);
        g.fillRect(0, h/2 + 25, w, h/2 - 25);
        
        // Texture de route
        g.setColor(70, 70, 80);
        for (int i = 0; i < h/2 - 25; i += 5) {
            for (int j = 0; j < w; j += 10) {
                if ((i + j) % 20 == 0) {
                    g.fillRect(j, h/2 + 25 + i, 3, 2);
                }
            }
        }
        
        // Lignes blanches de route animées
        g.setColor(255, 255, 255);
        int roadCenter = h/2 + 40;
        for (int i = -(frame % 40); i < w; i += 40) {
            g.fillRect(i, roadCenter, 20, 4);
        }
        
        // Bordures de route
        g.setColor(200, 200, 200);
        g.fillRect(0, h/2 + 25, w, 3);
        
        // Trottoir avec détails
        g.setColor(140, 130, 120);
        g.fillRect(0, h/2 + 12, w, 13);
        
        // Dalles de trottoir
        g.setColor(120, 110, 100);
        for (int i = 0; i < w; i += 30) {
            g.drawLine(i, h/2 + 12, i, h/2 + 25);
        }
        
        // Bâtiments avec fenêtres
        for (int i = 0; i < 4; i++) {
            int bx = 10 + i * 55;
            int bh = 65 + (i * 12);
            int by = h/2 - bh + 12;
            
            // Bâtiment principal
            g.setColor(160 - i*15, 140 - i*12, 110 - i*10);
            g.fillRect(bx, by, 45, bh);
            
            // Ombrage
            g.setColor(140 - i*15, 120 - i*12, 90 - i*10);
            g.fillRect(bx + 40, by, 5, bh);
            
            // Toit
            g.setColor(80, 80, 90);
            g.fillRect(bx - 2, by - 4, 49, 4);
            
            // Fenêtres avec lumières
            for (int r = 0; r < Math.min(5, bh / 15); r++) {
                for (int c = 0; c < 3; c++) {
                    int wx = bx + 6 + c * 13;
                    int wy = by + 8 + r * 13;
                    
                    // Fenêtre
                    boolean lit = (frame + r + c) % 40 < 35;
                    if (lit) {
                        g.setColor(255, 240, 180);
                    } else {
                        g.setColor(80, 100, 120);
                    }
                    g.fillRect(wx, wy, 9, 9);
                    
                    // Croisillon
                    g.setColor(60, 60, 70);
                    g.drawLine(wx + 4, wy, wx + 4, wy + 9);
                    g.drawLine(wx, wy + 4, wx + 9, wy + 4);
                }
            }
            
            // Climatiseurs
            if (i % 2 == 0) {
                g.setColor(100, 100, 110);
                g.fillRect(bx + 35, by + 20, 8, 6);
                g.setColor(80, 80, 90);
                g.fillRect(bx + 36, by + 21, 6, 4);
            }
        }
        
        // Lampadaire
        int lampX = w - 30;
        g.setColor(60, 60, 70);
        g.fillRect(lampX, h/2 + 12, 4, 40);
        
        // Lampe
        g.setColor(200, 200, 100);
        g.fillArc(lampX - 6, h/2 + 8, 16, 8, 0, 180);
        
        // Oiseaux animés
        drawBird(g, 40 + (frame * 2) % w, 30 + ((frame % 30) / 3), frame);
        drawBird(g, 80 + (frame * 2) % w, 45 + ((frame % 40) / 4), frame + 10);
    }
    
    public static void drawBusStopScene(Graphics g, int w, int h, int frame) {
        // Ciel
        for (int i = 0; i < h * 2/3; i++) {
            int val = 120 + (i * 100 / (h * 2/3));
            g.setColor(80 + i/5, 150 + i/4, val);
            g.drawLine(0, i, w, i);
        }
        
        // Sol avec texture
        g.setColor(150, 140, 130);
        g.fillRect(0, h * 2/3, w, h/3);
        
        // Dalles du sol
        g.setColor(130, 120, 110);
        for (int i = 0; i < w; i += 25) {
            for (int j = 0; j < h/3; j += 25) {
                g.drawRect(i, h * 2/3 + j, 24, 24);
            }
        }
        
        // Abribus amélioré
        int ax = w/2 - 45;
        int ay = h * 2/3 - 70;
        
        // Structure métallique
        g.setColor(140, 140, 150);
        g.fillRect(ax, ay - 5, 90, 3);
        
        // Toit avec ombre
        g.setColor(180, 40, 40);
        g.fillRect(ax - 5, ay - 2, 100, 10);
        g.setColor(160, 20, 20);
        g.fillRect(ax - 5, ay + 6, 100, 2);
        
        // Supports verticaux détaillés
        g.setColor(160, 160, 170);
        g.fillRect(ax + 3, ay + 8, 6, 60);
        g.fillRect(ax + 81, ay + 8, 6, 60);
        
        // Détails métalliques
        g.setColor(180, 180, 190);
        g.fillRect(ax + 4, ay + 8, 2, 60);
        g.fillRect(ax + 82, ay + 8, 2, 60);
        
        // Vitre arrière
        g.setColor(180, 200, 220);
        g.fillRect(ax + 10, ay + 10, 70, 50);
        g.setColor(160, 180, 200);
        g.drawRect(ax + 10, ay + 10, 70, 50);
        
        // Reflets sur la vitre
        g.setColor(255, 255, 255);
        g.fillRect(ax + 15, ay + 15, 20, 30);
        
        // Banc en bois détaillé
        g.setColor(120, 80, 50);
        g.fillRect(ax + 15, ay + 50, 60, 6);
        g.fillRect(ax + 15, ay + 40, 60, 3);
        
        // Planches du banc
        g.setColor(100, 60, 30);
        for (int i = 0; i < 60; i += 12) {
            g.drawLine(ax + 15 + i, ay + 40, ax + 15 + i, ay + 56);
        }
        
        // Pieds du banc
        g.setColor(140, 100, 70);
        g.fillRect(ax + 20, ay + 56, 6, 12);
        g.fillRect(ax + 65, ay + 56, 6, 12);
        
        // Panneau SOTRA amélioré
        g.setColor(255, 215, 60);
        g.fillRoundRect(ax + 12, ay + 18, 35, 25, 5, 5);
        g.setColor(240, 190, 40);
        g.drawRoundRect(ax + 12, ay + 18, 35, 25, 5, 5);
        
        g.setColor(0, 0, 0);
        g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_SMALL));
        g.drawString("SOTRA", ax + 15, ay + 28, 0);
        
        // Horaires
        g.setColor(255, 255, 255);
        g.fillRect(ax + 50, ay + 18, 25, 25);
        g.setColor(0, 0, 0);
        g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
        g.drawString("07:30", ax + 52, ay + 22, 0);
        g.drawString("08:00", ax + 52, ay + 30, 0);
        g.drawString("08:30", ax + 52, ay + 38, 0);
        
        // Corbeille
        g.setColor(80, 120, 80);
        g.fillRect(ax - 15, ay + 52, 12, 16);
        g.setColor(60, 100, 60);
        g.fillRect(ax - 14, ay + 53, 10, 2);
        
        // Foule variée
        drawNPCTalking(g, ax + 12, ay + 42, frame, 220, 100);
        drawNPCTalking(g, ax + 30, ay + 42, frame + 7, 200, 150);
        drawNPCTalking(g, ax + 48, ay + 42, frame + 14, 180, 80);
        drawNPCTalking(g, ax + 66, ay + 42, frame + 21, 210, 120);
        
        // Bus à l'horizon (animation)
        if (frame % 200 < 150) {
            int busX = -60 + ((frame % 200) * 2);
            drawBus(g, busX, h * 2/3 + 10);
        }
    }
public static void drawOfficeScene(Graphics g, int w, int h, int frame) {
        // Murs du bureau avec texture
        g.setColor(210, 210, 220);
        g.fillRect(0, 0, w, h);
        
        // Texture des murs
        g.setColor(200, 200, 210);
        for (int i = 0; i < h; i += 20) {
            g.drawLine(0, i, w, i);
        }
        
        // Sol carrelé amélioré
        for (int i = 0; i < w/20 + 1; i++) {
            for (int j = h/2/20; j < h/20 + 1; j++) {
                if ((i + j) % 2 == 0) {
                    g.setColor(170, 170, 180);
                } else {
                    g.setColor(190, 190, 200);
                }
                g.fillRect(i * 20, j * 20, 20, 20);
                
                // Reflet
                g.setColor(255, 255, 255);
                g.fillRect(i * 20 + 2, j * 20 + 2, 8, 8);
            }
        }
        
        // Bureau principal détaillé
        int dx = w/2 - 45;
        int dy = h/2 + 10;
        
        // Bureau en bois
        g.setColor(90, 70, 50);
        g.fillRect(dx, dy, 90, 55);
        g.setColor(110, 90, 70);
        g.fillRect(dx, dy, 90, 8);
        
        // Veinures du bois
        g.setColor(80, 60, 40);
        for (int i = 0; i < 90; i += 20) {
            g.drawLine(dx + i, dy + 8, dx + i + 15, dy + 63);
        }
        
        // Tiroirs
        g.setColor(100, 80, 60);
        g.fillRect(dx + 5, dy + 15, 25, 12);
        g.fillRect(dx + 5, dy + 32, 25, 12);
        
        // Poignées
        g.setColor(180, 160, 100);
        g.fillRect(dx + 15, dy + 20, 6, 2);
        g.fillRect(dx + 15, dy + 37, 6, 2);
        
        // Ordinateur moderne
        g.setColor(30, 30, 40);
        g.fillRect(dx + 30, dy + 12, 35, 30);
        g.setColor(35, 35, 45);
        g.fillRect(dx + 30, dy + 40, 35, 3);
        
        // Écran avec contenu
        g.setColor(120, 180, 255);
        g.fillRect(dx + 32, dy + 14, 31, 25);
        
        // Fenêtre de programme
        g.setColor(255, 255, 255);
        g.fillRect(dx + 34, dy + 16, 27, 4);
        g.setColor(200, 200, 200);
        g.fillRect(dx + 34, dy + 21, 27, 16);
        
        // Texte sur l'écran
        g.setColor(0, 0, 0);
        for (int i = 0; i < 4; i++) {
            g.drawLine(dx + 36, dy + 23 + i*3, dx + 58, dy + 23 + i*3);
        }
        
        // Support d'écran
        g.setColor(40, 40, 50);
        g.fillRect(dx + 44, dy + 43, 8, 5);
        g.fillRect(dx + 42, dy + 48, 12, 2);
        
        // Clavier détaillé
        g.setColor(180, 180, 190);
        g.fillRect(dx + 25, dy + 45, 45, 12);
        
        // Touches
        g.setColor(200, 200, 210);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 3; j++) {
                g.fillRect(dx + 27 + i*5, dy + 47 + j*3, 4, 2);
            }
        }
        
        // Souris
        g.setColor(160, 160, 170);
        g.fillArc(dx + 72, dy + 48, 8, 10, 0, 360);
        g.setColor(140, 140, 150);
        g.drawLine(dx + 76, dy + 48, dx + 76, dy + 58);
        
        // Documents empilés
        g.setColor(255, 255, 255);
        for (int i = 0; i < 3; i++) {
            g.fillRect(dx + 5, dy + 10 - i*2, 18, 24);
            g.setColor(240, 240, 240);
            g.drawRect(dx + 5, dy + 10 - i*2, 18, 24);
            g.setColor(255, 255, 255);
        }
        
        // Texte sur les documents
        g.setColor(0, 0, 0);
        for (int i = 0; i < 6; i++) {
            g.drawLine(dx + 7, dy + 14 + i*3, dx + 20, dy + 14 + i*3);
        }
        
        // Tasse à café
        g.setColor(180, 100, 60);
        g.fillRect(dx + 72, dy + 10, 12, 14);
        g.setColor(200, 120, 80);
        g.fillArc(dx + 72, dy + 9, 12, 6, 0, 180);
        
        // Anse
        g.setColor(180, 100, 60);
        g.drawArc(dx + 83, dy + 12, 6, 8, 270, 180);
        
        // Café à l'intérieur
        g.setColor(80, 50, 30);
        g.fillArc(dx + 74, dy + 10, 8, 4, 0, 180);
        
        // Vapeur animée
        if (frame % 40 < 30) {
            g.setColor(200, 200, 220);
            int steam = frame % 10;
            g.drawLine(dx + 77, dy + 8 - steam, dx + 78, dy + 5 - steam);
            g.drawLine(dx + 79, dy + 8 - steam, dx + 80, dy + 5 - steam);
        }
        
        // Chaise de bureau
        g.setColor(70, 70, 90);
        g.fillRect(dx + 35, dy + 60, 25, 6);
        g.setColor(80, 80, 100);
        g.fillRect(dx + 32, dy + 55, 8, 12);
        g.fillRect(dx + 55, dy + 55, 8, 12);
        
        // Dossier de chaise
        g.setColor(70, 70, 90);
        g.fillRect(dx + 40, dy + 42, 15, 18);
        g.setColor(80, 80, 100);
        g.fillRect(dx + 42, dy + 44, 11, 14);
        
        // Horloge murale détaillée
        int clockX = w - 45, clockY = 25;
        g.setColor(40, 40, 40);
        g.fillArc(clockX, clockY, 35, 35, 0, 360);
        g.setColor(250, 250, 250);
        g.fillArc(clockX + 2, clockY + 2, 31, 31, 0, 360);
        
        // Chiffres
        g.setColor(0, 0, 0);
        g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
        g.drawString("12", clockX + 14, clockY + 6, 0);
        g.drawString("3", clockX + 26, clockY + 18, 0);
        g.drawString("6", clockX + 16, clockY + 28, 0);
        g.drawString("9", clockX + 6, clockY + 18, 0);
        
        // Aiguilles (8h45) - positions fixes
        g.setColor(0, 0, 0);
        int centerX = clockX + 17;
        int centerY = clockY + 17;
        
        // Heure (8h) - angle 240°
        g.drawLine(centerX, centerY, centerX - 7, centerY + 4);
        
        // Minute (45) - angle 270°
        g.drawLine(centerX, centerY, centerX, centerY + 12);
        
        // Centre
        g.setColor(200, 0, 0);
        g.fillArc(centerX - 2, centerY - 2, 4, 4, 0, 360);
        
        // Plante décorative
        g.setColor(120, 80, 40);
        g.fillRect(w - 25, h/2 + 20, 15, 20);
        g.setColor(40, 100, 40);
        g.fillArc(w - 28, h/2 + 10, 20, 15, 0, 360);
        g.fillArc(w - 25, h/2 + 5, 14, 12, 0, 360);
        g.setColor(60, 120, 60);
        for (int i = 0; i < 3; i++) {
            g.drawLine(w - 20 + i*3, h/2 + 15, w - 22 + i*3, h/2 + 8);
        }
        
        // Fenêtre avec vue
        int winX = 10, winY = 30;
        g.setColor(150, 200, 255);
        g.fillRect(winX, winY, 50, 60);
        
        // Cadre de fenêtre
        g.setColor(200, 200, 200);
        g.drawRect(winX - 2, winY - 2, 54, 64);
        g.drawLine(winX + 25, winY, winX + 25, winY + 60);
        g.drawLine(winX, winY + 30, winX + 50, winY + 30);
        
        // Nuage par la fenêtre
        g.setColor(255, 255, 255);
        g.fillArc(winX + 20, winY + 15, 15, 10, 0, 360);
        g.fillArc(winX + 28, winY + 12, 12, 10, 0, 360);
    }
public static void drawMarketScene(Graphics g, int w, int h, int frame) {
        // Ciel
        for (int i = 0; i < h/2; i++) {
            int val = 140 + (i * 80 / (h/2));
            g.setColor(100 + i/4, 160 + i/3, val);
            g.drawLine(0, i, w, i);
        }
        
        // Sol du marché
        g.setColor(160, 140, 120);
        g.fillRect(0, h/2, w, h/2);
        
        // Texture du sol
        g.setColor(140, 120, 100);
        for (int i = 0; i < w; i += 15) {
            for (int j = 0; j < h/2; j += 15) {
                if ((i + j) % 30 == 0) {
                    g.fillRect(i, h/2 + j, 3, 3);
                }
            }
        }
        
        // Étal 1 - Fruits
        int e1x = 15, e1y = h/2 + 15;
        g.setColor(140, 100, 60);
        g.fillRect(e1x, e1y, 55, 45);
        
        // Bâche
        g.setColor(220, 100, 60);
        g.fillArc(e1x - 10, e1y - 35, 75, 25, 0, 180);
        
        // Fruits (oranges, tomates, bananes)
        g.setColor(255, 150, 40);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                g.fillArc(e1x + 8 + i*14, e1y + 12 + j*12, 12, 12, 0, 360);
            }
        }
        
        g.setColor(220, 40, 40);
        g.fillArc(e1x + 38, e1y + 14, 11, 11, 0, 360);
        g.fillArc(e1x + 32, e1y + 20, 11, 11, 0, 360);
        
        g.setColor(255, 240, 60);
        for (int i = 0; i < 4; i++) {
            g.fillArc(e1x + 10 + i*8, e1y + 32, 10, 6, 0, 180);
        }
        
        // Vendeuse
        drawNPCTalking(g, e1x + 27, e1y - 18, frame, 200, 160);
        
        // Étal 2 - Poisson
        int e2x = 85, e2y = h/2 + 15;
        g.setColor(130, 120, 110);
        g.fillRect(e2x, e2y, 55, 45);
        
        // Bâche bleue
        g.setColor(100, 150, 220);
        g.fillArc(e2x - 10, e2y - 35, 75, 25, 0, 180);
        
        // Poissons
        g.setColor(180, 180, 200);
        g.fillArc(e2x + 12, e2y + 15, 16, 10, 0, 360);
        g.fillArc(e2x + 28, e2y + 18, 16, 10, 0, 360);
        g.fillArc(e2x + 18, e2y + 25, 16, 10, 0, 360);
        
        // Vendeur
        drawNPCTalking(g, e2x + 27, e2y - 18, frame + 15, 170, 100);
    }
    
    public static void drawBossScene(Graphics g, int cx, int cy, int frame, boolean angry) {
        // Ombre
        g.setColor(40, 40, 60);
        g.fillArc(cx - 16, cy + 22, 32, 10, 0, 360);
        
        // Costume
        g.setColor(25, 25, 45);
        g.fillRect(cx - 14, cy, 28, 28);
        
        // Boutons
        g.setColor(200, 200, 210);
        g.fillArc(cx - 2, cy + 4, 3, 3, 0, 360);
        g.fillArc(cx - 2, cy + 11, 3, 3, 0, 360);
        g.fillArc(cx - 2, cy + 18, 3, 3, 0, 360);
        
        // Cravate
        if (angry) {
            g.setColor(220, 20, 20);
        } else {
            g.setColor(120, 50, 50);
        }
        g.fillRect(cx - 4, cy + 2, 8, 20);
        g.fillTriangle(cx - 5, cy + 2, cx, cy - 2, cx + 5, cy + 2);
        
        // Tête
        g.setColor(200, 160, 120);
        g.fillArc(cx - 12, cy - 24, 24, 26, 0, 360);
        g.setColor(180, 140, 100);
        g.fillArc(cx - 12, cy - 10, 24, 14, 180, 180);
        
        // Cheveux gris
        g.setColor(110, 110, 120);
        g.fillArc(cx - 13, cy - 26, 26, 14, 0, 180);
        
        // Lunettes
        g.setColor(70, 70, 80);
        g.fillRect(cx - 10, cy - 14, 8, 8);
        g.fillRect(cx + 2, cy - 14, 8, 8);
        g.fillRect(cx - 2, cy - 12, 4, 2);
        
        // Expression
        if (angry) {
            g.setColor(0, 0, 0);
            g.drawLine(cx - 10, cy - 16, cx - 5, cy - 15);
            g.drawLine(cx + 5, cy - 15, cx + 10, cy - 16);
            
            g.setColor(100, 0, 0);
            g.fillArc(cx - 7, cy - 6, 14, 10, 0, -180);
            
            // Lignes de colère
            g.setColor(255, 100, 100);
            g.drawLine(cx - 16, cy - 10, cx - 20, cy - 12);
            g.drawLine(cx + 16, cy - 10, cx + 20, cy - 12);
        } else {
            g.setColor(0, 0, 0);
            g.fillRect(cx - 7, cy - 12, 2, 3);
            g.fillRect(cx + 5, cy - 12, 2, 3);
            
            g.setColor(180, 100, 100);
            g.drawArc(cx - 6, cy - 8, 12, 8, 200, 140);
        }
        
        // Bras
        g.setColor(25, 25, 45);
        g.fillRect(cx - 16, cy + 10, 5, 12);
        g.fillRect(cx + 11, cy + 10, 5, 12);
        
        // Mains
        g.setColor(200, 160, 120);
        g.fillArc(cx - 17, cy + 20, 6, 6, 0, 360);
        g.fillArc(cx + 10, cy + 20, 6, 6, 0, 360);
        
        // Jambes
        g.setColor(20, 20, 40);
        g.fillRect(cx - 10, cy + 24, 7, 12);
        g.fillRect(cx + 3, cy + 24, 7, 12);
        
        // Chaussures
        g.setColor(10, 10, 20);
        g.fillRect(cx - 11, cy + 34, 9, 4);
        g.fillRect(cx + 2, cy + 34, 9, 4);
    }
    
    public static void drawMiniMap(Graphics g, int bx, int by, int scene, int phase) {
        int x = bx, y = by;
        
        // Fond
        for (int i = 0; i < 78; i++) {
            int val = 20 + (i * 50 / 78);
            g.setColor(val, val + 15, val + 50);
            g.drawLine(x - 6, y - 6 + i, x + 60, y - 6 + i);
        }
        
        // Bordure
        g.setColor(255, 200, 50);
        g.drawRoundRect(x - 6, y - 6, 66, 78, 10, 10);
        
        // Titre
        g.setColor(40, 60, 100);
        g.fillRoundRect(x + 5, y - 4, 45, 14, 6, 6);
        
        g.setColor(255, 255, 100);
        g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_SMALL));
        String phaseText = phase == 0 ? LanguageManager.getText("morning") : 
                          (phase == 1 ? LanguageManager.getText("office") : LanguageManager.getText("evening"));
        g.drawString(phaseText, x + 27, y + 4, Graphics.HCENTER | Graphics.BASELINE);
        
        // Grille
        g.setColor(80, 110, 150);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                g.fillRect(x + i * 14, y + 12 + j * 16, 12, 14);
                g.setColor(100, 130, 170);
                g.drawRect(x + i * 14 + 1, y + 12 + j * 16 + 1, 10, 12);
                g.setColor(80, 110, 150);
            }
        }
        
        // Maison
        g.setColor(200, 120, 60);
        g.fillRect(x + 2, y + 14, 9, 9);
        g.setColor(160, 60, 30);
        g.fillTriangle(x + 2, y + 14, x + 6, y + 10, x + 11, y + 14);
        
        // Bureau
        g.setColor(70, 90, 150);
        g.fillRect(x + 44, y + 54, 11, 14);
        
        // Position joueur
        int gridSize = scene < 16 ? 4 : 1;
        int px = x + (scene % gridSize) * 14 + 4;
        int py = y + 12 + (scene / gridSize) * 16 + 4;
        
        g.setColor(255, 50, 50);
        g.fillArc(px - 3, py - 3, 6, 6, 0, 360);
        g.setColor(255, 200, 50);
        g.fillArc(px - 2, py - 2, 4, 4, 0, 360);
    }
    
    // Fonctions utilitaires
    private static void drawCloud(Graphics g, int x, int y, int w, int h) {
        g.setColor(255, 255, 255);
        g.fillArc(x, y, w/2, h, 0, 360);
        g.fillArc(x + w/3, y - h/3, w/2, h, 0, 360);
        g.fillArc(x + w/2, y, w/2, h, 0, 360);
    }
    
    private static void drawSmoke(Graphics g, int x, int y, int offset) {
        for (int i = 0; i < 3; i++) {
            int sy = y - offset - i * 8;
            int sx = x + ((offset + i*3) % 8) - 4;
            g.setColor(200, 200, 220);
            g.fillArc(sx - 3, sy, 6, 6, 0, 360);
        }
    }
    
    private static void drawBird(Graphics g, int x, int y, int frame) {
        int wing = (frame % 20 < 10) ? 4 : 2;
        g.setColor(50, 50, 60);
        g.fillArc(x, y, 6, 4, 0, 360);
        g.drawLine(x - wing, y, x + 2, y + 2);
        g.drawLine(x + 8 + wing, y, x + 4, y + 2);
    }
    
    private static void drawBus(Graphics g, int x, int y) {
        g.setColor(220, 140, 40);
        g.fillRect(x, y, 50, 25);
        
        g.setColor(150, 200, 255);
        g.fillRect(x + 5, y + 3, 12, 10);
        g.fillRect(x + 20, y + 3, 12, 10);
        g.fillRect(x + 35, y + 3, 10, 10);
        
        g.setColor(40, 40, 50);
        g.fillArc(x + 8, y + 22, 8, 8, 0, 360);
        g.fillArc(x + 34, y + 22, 8, 8, 0, 360);
    }
    
    // Fins de jeu
    public static void drawVictoryEnding(Graphics g, int cx, int cy, int frame) {
        EndingIllustrations.drawVictory(g, cx, cy);
        
        // Étoiles supplémentaires
        g.setColor(255, 255, 100);
        int star1 = (frame % 30) / 6;
        g.drawLine(cx - 40, cy - 20 - star1, cx - 40, cy - 20 + star1);
        g.drawLine(cx - 40 - star1, cy - 20, cx - 40 + star1, cy - 20);
        
        int star2 = ((frame + 10) % 30) / 6;
        g.drawLine(cx + 40, cy - 20 - star2, cx + 40, cy - 20 + star2);
        g.drawLine(cx + 40 - star2, cy - 20, cx + 40 + star2, cy - 20);
    }
    
    public static void drawFatigueEnding(Graphics g, int cx, int cy, int frame) {
        EndingIllustrations.drawFatigue(g, cx, cy);
    }
    
    public static void drawNoMoneyEnding(Graphics g, int cx, int cy, int frame) {
        EndingIllustrations.drawNoMoney(g, cx, cy);
    }
    
    public static void drawLateEnding(Graphics g, int cx, int cy, int frame) {
        EndingIllustrations.drawLate(g, cx, cy);
    }
}