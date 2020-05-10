# MCR_Game
Le concept de ce jeu est fortement inspiré de Krosmaga, un jeu vidéo mêlant tower defense et jeu de cartes à collectionner virtuel développé par Ankama Games. 
Il a été réalisé en suivant le modèle Commande.

## Receptors
Un joueur possède : 
- un nom
- des points de vie
- des points d'action, qui le limite à un certain nombre d'action durant le tour.

### Turn
Le temps et limité pour éviter les blocage si un joueur ne joue pas.
Le tour d'un joueur se déroule ainsi :
		
1. Pioche une carte
2. Initialise ses potins d'action pour correspondre au numéro du tour (à partir du 15eme, ne reçoit que 15, pas plus)
3. Fait autant d'action qu'il veut, il est limité par les cartes et le nbr de points d'action qu'il a
4. Fini son tour en cliquant sur un bouton

## Turn
Chaque tour du jeu, chaque joueur fait son tour, et le nombre de tours totaux incrémente (commence à 1)

## Creature
Une créature possède : 
- un nom
- des points de vie
- des points de mouvement
- des points d'attaque

### Turn
A chaque tour, la créature avance de movePoints pas et, s'arrête au premier obstacle. Elle attaque l'obstacle en face de attackPoints. Si ses lifePoints atteignent 0, elle meurt (disparait du board)

## Card
Une carte est déterminée par :
- son coût (dans points d'action)
- ses commandes
- ses récepteurs de commande, s'il y en a.
Quand elle est jouée, elle execute sa commande et diminue de cost les actionsPoints de son joueur.

### Spell
A une commande de type sort qui aura un effet immédiat sur le joueur adverse
### Receptors.Trap
A une commande de type piège qui va poser un piège sur une case du jeu
### Creature
A une commande qui permet de créer et placer une nouvelle créature sur le plateau de jeu
