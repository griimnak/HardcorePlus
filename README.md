<p align="center">
  <img src="https://i.imgur.com/ak1ZAlR.png" alt="Hradcore+"/>
</p>

[![stability-experimental](https://img.shields.io/badge/stability-stable-green.svg)](https://github.com/emersion/stability-badges#stable)
<b>Latest release build:</b> <a href="https://github.com/griimnak/Minecraft-HardPlus/releases">here</a>

<b>A Minecraft 1.14.1+ Bukkit/Spigot Plugin</b>

Enhances the vanilla Minecraft hardcore experience by degrading players' max health on death.

Once a user has ran out of hearts, they are perma dead.

[June 14 2019]

HardcorePlus 0.1.8 corrects some minor annoyances and finally introduces the configuration system.

Thank you to @LucasLogical for finding the following bugs.

- Fixed bug with shields not working properly
- Fixed bug with blood effect showing through shields

- New command: /hardcoreplus reload - reloads the config.yml

- Config system complete
- Config options for toggling: bloodEffect, respawnSound, respawnEffect, respawnWeakness
- Config option for toggling max hp restore by ender dragon kill
- Config options for all ingame texts
- Config option for enforcing hardcore mode upon server.

[May 18 2019]

- increased weakness after death to 3 minutes 30 sec
- hardcoreplus commands are accessible from server console now
- if hardcore is not set in server.properties console will be notified

- fixed bug with setmax command always settings to 10 hearts
- fixed bug with fire and potion effects persisting after death
- fixed bug with setmax command allowing use of negative numbers
- fixed bug with setmax command allowing use of invalid numbers

[May 15 2019]

- Users can now restore their max hp by killing the ender dragon.
- Added blood effect on hit
- Corrected creeper instakill bug
- Added sound effect to death
- Added admin permission
- Enderdragon kill event
- Started admin commands
  
[May 13 2019]
 
- Changed health lost from 1 to 2 hearts.
- Changed DeathEvent to EntityDamagedEvent
- Death is no longer announced.
- "Dream" effect created.

<details>
  <summary>View old media</summary>

##### 2nd Youtube Demonstration:
[![Alt text](https://img.youtube.com/vi/z5rxjSrnwJY/0.jpg)](https://www.youtube.com/watch?v=z5rxjSrnwJY)
  
##### [OLD] Youtube Demonstration:
[![Alt text](https://img.youtube.com/vi/C36bSUXwPZw/0.jpg)](https://www.youtube.com/watch?v=C36bSUXwPZw)
</details>

##### Youtube Demonstration:
[![Alt text](https://img.youtube.com/vi/DiMFgSwdqvc/0.jpg)](https://www.youtube.com/watch?v=DiMFgSwdqvc)

