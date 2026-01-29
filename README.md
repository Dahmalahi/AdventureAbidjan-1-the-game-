# Aventure Abidjan

**Jeu textuel rogue-lite J2ME (Java ME)**  
Un jeune professionnel à Abidjan vit une matinée pleine de mystères : SMS anonyme, complot au bureau, choix risqués et contraintes de temps/argent/fatigue.  
Plusieurs fins possibles selon vos décisions.

**Résumé en une phrase**  
Un jeu narratif mobile rétro (240x320 px) où vous gérez votre matinée à Abidjan entre routine professionnelle, enquête dangereuse et survie quotidienne – le tout en Java ME avec sauvegarde, pixel art et 4 fins différentes.

## Fonctionnalités principales

- **Histoire non-linéaire** : ~35 scènes + branches (mystère le matin → tension au bureau)
- **Ressources à gérer** : Argent (FCFA), Fatigue, Réputation, Bonheur du chef
- **Mécaniques temps réel** : Début à 7h15 – retard = game over
- **4 fins distinctes** : Victoire, Épuisement, Ruine, En retard
- **Sauvegarde RMS** persistante
- **Pixel art & animations** légères (marche, mini-map, drapeau CI animé, fins illustrées)
- **Interface bilingue** FR/EN (partielle)
- **Contrôles téléphone classique** : 1–4 = choix, 7/* = scroll texte, 9/# = scroll choix, 5 = rejouer

## Contrôles

| Touche   | Action                        |
|----------|-------------------------------|
| 1–4      | Choisir option                |
| 7 / *    | Monter / descendre narration  |
| 9 / #    | Monter / descendre choix      |
| 5        | Rejouer après fin             |
| Soft key | Quitter / Recommencer         |

## État du projet (janv. 2026)

- Mécaniques de base + matinée + bureau : ~70–80 %
- Manque : fin complète de l’histoire (soir/résolution), traduction anglaise totale, sons/vibrations, optimisation mémoire

## Technologies

- Java ME (MIDP 2.0 / CLDC 1.1)
- RMS pour sauvegarde
- Pixel art dessiné à la main (Graphics)
- Arithmétique fixe (table sinus) pour animations sans Math.*

## Comment tester / compiler

- **Outils recommandés** : Sun WTK 2.5.2, NetBeans Mobility, ou freej2me (émulateur moderne)
- **Build typique** (WTK) : `wtkbuild -midlet AdventureMIDlet`

## Licence

MIT – libre de modifier, porter (Android, web, etc.) ou publier.

> Nostalgie des jeux Java 2025–2026, made in Abidjan 🇨🇮

Bienvenue dans l'aventure !

<img width="240" height="320" alt="2026_01_29_19_44_28_121_lcd" src="https://github.com/user-attachments/assets/c230a828-7efe-4b7e-994d-d194adcb90cc" />
<img width="240" height="320" alt="2026_01_29_19_43_59_145_lcd" src="https://github.com/user-attachments/assets/3cf5d476-d33d-4a0b-b6fa-016006bd2469" />
<img width="240" height="320" alt="2026_01_29_19_44_24_198_lcd" src="https://github.com/user-attachments/assets/b5e3987b-cc47-4af1-897b-e88b5b7fd0e9" />
<img width="240" height="320" alt="2026_01_29_19_44_03_204_lcd" src="https://github.com/user-attachments/assets/34c47061-32fa-4a06-af61-b7c2416fcf0b" />
<img width="240" height="320" alt="2026_01_29_19_44_05_090_lcd_1" src="https://github.com/user-attachments/assets/805d5204-a430-4480-bad7-b7b1cb87b7db" />
<img width="240" height="320" alt="2026_01_29_19_43_55_248_lcd" src="https://github.com/user-attachments/assets/f6e8bf70-eb85-4a41-82e4-87d63a00c50d" />
<img width="240" height="320" alt="2026_01_29_19_44_09_239_lcd" src="https://github.com/user-attachments/assets/97c1a09c-48fe-4fc0-a9c5-8ddafc3a4356" />

