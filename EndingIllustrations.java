import javax.microedition.lcdui.Graphics;

public class EndingIllustrations {

    public static void drawEnding(Graphics g, int ending, int cx, int cy, int sw, int sh) {
        switch (ending) {
            case 1: drawVictory(g, cx, cy); break;
            case 2: drawFatigue(g, cx, cy); break;
            case 3: drawNoMoney(g, cx, cy); break;
            case 4: drawLate(g, cx, cy); break;
            default:
                g.setColor(255, 100, 100);
                g.drawString("FIN #" + ending, cx, cy, Graphics.HCENTER | Graphics.BASELINE);
        }
    }

    // CHANGÉ: private -> public
    public static void drawVictory(Graphics g, int cx, int cy) {
        // Bureau
        g.setColor(80, 100, 160); g.fillRect(cx-40, cy-20, 80, 50);
        g.setColor(200, 220, 255);
        for (int i=0; i<3; i++) for (int j=0; j<2; j++)
            g.fillRect(cx-34 + i*24, cy-14 + j*20, 12, 12);

        // Perso content
        g.setColor(255, 200, 140); g.fillArc(cx-10, cy+30, 20, 20, 0, 360);
        g.setColor(80, 140, 220);  g.fillRect(cx-12, cy+45, 24, 30);
        g.setColor(255, 200, 140); g.fillRect(cx-20, cy+42, 8, 20); g.fillRect(cx+12, cy+42, 8, 20);
        g.setColor(40, 80, 40);    g.fillRect(cx-12, cy+70, 10, 20); g.fillRect(cx+2, cy+70, 10, 20);

        g.setColor(0,0,0); g.drawArc(cx-8, cy+38, 16, 10, 200, 140); // sourire
        g.setColor(255,255,80);
        drawStar(g, cx-35, cy+5); drawStar(g, cx+35, cy+5);
    }

    // CHANGÉ: private -> public
    public static void drawFatigue(Graphics g, int cx, int cy) {
        g.setColor(140, 100, 60); g.fillRect(cx-60, cy+50, 120, 40); // sol
        g.setColor(255, 200, 140); g.fillArc(cx-18, cy+35, 36, 18, 0, 360);
        g.setColor(80, 120, 200);  g.fillRoundRect(cx-25, cy+45, 50, 25, 12, 12);
        g.setColor(0,0,0);
        g.drawLine(cx-10, cy+32, cx-4, cy+38); g.drawLine(cx-4, cy+32, cx-10, cy+38);
        g.drawLine(cx+4,  cy+32, cx+10, cy+38); g.drawLine(cx+10, cy+32, cx+4,  cy+38);
        g.setColor(180,220,255); g.drawString("Zzz", cx+20, cy+25, 0);
    }

    // CHANGÉ: private -> public
    public static void drawNoMoney(Graphics g, int cx, int cy) {
        g.setColor(255,200,140); g.fillArc(cx-12, cy+20, 24, 24, 0, 360);
        g.setColor(80,120,200);  g.fillRect(cx-15, cy+38, 30, 35);
        g.setColor(220,180,100);
        g.fillTriangle(cx-15, cy+38, cx-5, cy+50, cx-25, cy+50);
        g.fillTriangle(cx+15, cy+38, cx+5, cy+50, cx+25, cy+50);
        g.setColor(0,0,0); g.drawArc(cx-8, cy+28, 16, 10, 0, -180); // bouche triste
        g.setColor(255,40,40); g.drawString("0 FCFA", cx-35, cy-5, 0);
    }

    // CHANGÉ: private -> public
    public static void drawLate(Graphics g, int cx, int cy) {
        // Horloge
        g.setColor(240,240,120); g.fillArc(cx-25, cy-15, 50, 50, 0, 360);
        g.setColor(40,40,40);    g.fillArc(cx-22, cy-12, 44, 44, 0, 360);
        g.setColor(255,0,0);     g.drawLine(cx, cy+10, cx, cy-18);   // grande
        g.setColor(200,200,200); g.drawLine(cx, cy+10, cx+20, cy+2); // petite

        // Chef en colère
        g.setColor(220,170,130); g.fillArc(cx+28, cy+5, 32, 32, 0, 360);
        g.setColor(0,0,0);
        g.fillRect(cx+34, cy+13, 6, 8); g.fillRect(cx+44, cy+13, 6, 8);
        g.drawArc(cx+32, cy+20, 24, 14, 0, -180);
    }

    private static void drawStar(Graphics g, int x, int y) {
        g.drawLine(x-4,y, x+4,y); g.drawLine(x,y-4, x,y+4);
        g.drawLine(x-3,y-3,x+3,y+3); g.drawLine(x-3,y+3,x+3,y-3);
    }
}