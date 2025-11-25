Team introduction (goal, vision, list of roles)

---

# First part of the semester

Our goal is to make sounds easily recognizable regarding different events that can happen throughout the games. Moreover, it should fit into the vibe of the game so that the player can be completely immersed while playing it.

List of roles:

- Leader : Lucie Ribero
- Git head : Mathys Rageade
- Development team: Leo Sorrentino, Mattéo Revest, Anne-Amélie Nassiet-Combe, Hing-Thanh Truong

Team requirements (overall project)
Creating sound assets and background music fitting the game event and atmosphere and other upcoming functions.
Detailed requirement (5)
- Menu music : a looped music that plays for as long as the player stays on the menu. It must be different from the 
background music and be like a kind of appealing sound giving envy to play.
- Background music fitting for each level : An 8 bit-music to stay in the game atmosphere, genre fitting and different 
for each level to match the progression.
- End of the level (win or lose) : a short sound emphasizing the death or the win of the player. It has to be 
recognizable especially for the loss.
- Sound effect (shooting, buttons and alien appearance) : some background sound not too loud nor too intrusive to 
make the experience more appealing to create atmosphere but not chaos.
- Damage taken : just a short and loud enough sound explaining that the player has taken damage to bring his 
attention to the remaining hp if he didn’t see the projectiles hit him

Dependencies on other team (3)
- Music depending on the level reached : since other teams are creating levels we have to match the sound with the 
pace or the background of said level
- Achievements reached and currency sound : same as when taking damage, just a sound alert signaling that the player 
has reached an achievement or used his money
- Sound when inviting an another player (multiplayer mode) : more of an atmosphere sound to describe the fact that 
another player is joining the game before it starts

---

# Second part of the semester

We decided to start from the last version of the game, so with features already implemented.
Branches `feature/GagnePerdu`, `feature/SoundEffect`, `feature/BackgroundMusic` and `feature/menu-sound` were deleted 
because we start anew, so new branches will replace them and start with `master` as base.

The Git process will operate the same way it has been up until now (see *first part of the semester*).

### Teams Requirements for the 2nd half of the project:
- Save game state: Adding a feature that saves the state of the game in which the player has failed a level, allowing him to restart from this level without having to redo the whole game from the beginning.
- Level selection: A new button will be added on the menu allowing the user to choose which level he wants to play, without having to redo the previous ones.
- Skin choice: Adding a button on the menu that allows the player to change and choose the skin he wants for his ship allowing him to change its appearance.
- Background changes: To fit in with the theme of the music and create a sense of progress, we will change the color of the background depending on which level the player is currently at.
- Add more levels and a real final boss: Adding new levels to enhance the difficulties and the lifespan of the game and moreover, add a real sense of completion by implementing a real big boss at the end on the final level.
- 1-player or 2-player mode selection: Allowing the user to choose at the beginning of the game if he wants to play on one player mode or two player mode instead of the now default two player mode applied in the team-based project from the first part of the semester.
