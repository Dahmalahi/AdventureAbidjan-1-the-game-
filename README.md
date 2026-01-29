# Aventure Abidjan

**Jeu textuel rogue-lite J2ME (Java ME)**  
Un jeune professionnel à Abidjan vit une matinée pleine de mystères : SMS anonyme, complot au bureau, choix risqués et contraintes de temps/argent/fatigue.  
Plusieurs fins possibles selon vos décisions.

**Résumé en une phrase**  
Un jeu narratif mobile rétro (240x320<img width="240" height="320" alt="2026_01_29_19_44_28_121_lcd" src="https://github.com/user-attachments/assets/ddce4f25-4cd7-4543-8282-035765d9e866" />
<img width="240" height="320" alt="2026_01_29_19_43_59_145_lcd" src="https://github.com/user-attachments/assets/04737081-d236-4891-b92d-0e3d43c0648e" />
<img width="240" height="320" alt="2026_01_29_19_44_24_198_lcd" src="https://github.com/user-attachments/assets/8907b640-81f5-4351-bac7-d21670307856" />
<img width="240" height="320" alt="2026_01_29_19_44_03_204_lcd" src="https://github.com/user-attachments/assets/e9409d4f-8d9f-4fca-ac01-a358673e5eb9" />
<img width="240" height="320" alt="2026_01_29_19_44_05_090_lcd_1" src="https://github.com/user-attachments/assets/da933045-a69b-4681-9f4f-e8e075810fab" />
<img width="240" height="320" alt="2026_01_29_19_43_55_248_lcd" src="https://github.com/user-attachments/assets/4854ca6d-0a34-4371-ae2b-5b8c79126b02" />
<img width="240" height="320" alt="2026_01_29_19_44_09_239_lcd" src="https://github.com/user-attachments/assets/f710c980-cdc9-4950-a5e3-f00a8facc976" />
 px) où vous gérez votre matinée à Abidjan entre routine professionnelle, enquête dangereuse et survie quotidienne – le tout en Java ME avec sauvegarde, pixel art et 4 fins différentes.

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
