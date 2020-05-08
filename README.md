# MCR_Game
A game based on the Command Pattern for MCR course in HEIG-VD

1. Player
name, lifePoints, actionPoints
	a. Turn
Le temps et limité pour éviter les blocage si un joueur ne joue pas.
Le tour d'un joueur se déroule ainsi :
		1. Pioche une carte
		2. Initialise ses potins d'action pour correspondre au numéro du tour (à partir du 15eme, ne reçoit que 15, pas plus)
		3. Fait autant d'action qu'il veut, il est limité par les cartes et le nbr de points d'action qu'il a
		4. Fini son tour en cliquant sur un bouton

2. Turn
Chaque tour du jeu, chaque joueur fait son tour, et le nombre de tours totaux incrémente (commence à 1)

3. Creature
name, lifePoints, movePoints, attackPoints
	a. Turn
A chaque tour, la créature avance de movePoints pas et, s'arrête au premier obstacle. Elle attaque l'obstacle en face de attackPoints. Si ses lifePoints atteignent 0, elle meurt (disparait du board)

4. Card
cost (in actionPoints), commands, Recepteurs de commande (s'il y en a)
Quand elle est jouée, elle execute sa commande et diminue de cost les actionsPoints de son joueur.
	a. Spell
A une commande de type sort qui aura un effet immédiat sur le joueur adverse
	b. Trap
A une commande de type piège qui va poser un piège sur une case du jeu
	c. Creature
A une commande qui permet de créer et placer une nouvelle créature sur le plateau de jeu
