# IMPOSTOR — Minecraft Manhunt Twist

A **Minecraft Manhunt gamemode inspired by Among Us**.  
One (or more) players are secretly chosen as **Impostors**, trying to sabotage the hunt from within…

> 💡 Based on **[Zotaro’s](https://www.youtube.com/@zorato_04)** Secret Hunter series  
> 🧩 This is my first Minecraft plugin, built using the **Bukkit/Spigot API**

---

## 📦 Installation

If you’d like to download the plugin directly:

1. Go to the **[Releases tab](https://github.com/kamemae/impostor/releases/tag/minecraft)**
2. Download the **latest version**
3. Drop the `.jar` file into your server’s `plugins` folder
4. Restart the server

## 🛠️ Build it yourself
1. Setup maven (on windows add it into Env Variables) and download java (17 or higher)
2. Clone or download repo
3. Open terminal and navigate into folder with plugin
4. Compile plugin and create package
```
    mvn clean compile package
```
5. Then drop compiled `.jar` package into your server's `plugins` folder
6. Restart your server

---

## In-Game Commands

### 🔧 Game Setup
**Select the number of impostors**
```
    /setimpostors <number>
```
**Disable/Enable Jester (disabled on default)**
```
    /jester
```
**Change round time (in minutes)**
```
    /setroundtime <number>
```
**Start the game**
```
    /start
```

### 🧭 Player utilities
**Send your current coordinates to chat**
```
    /wherami
```
**Let others know you’re lost**
```
    /lost
```
**As Impersonator, you can fake death of someone**
```
    /impersonate <nickname>
```
**As Investigator, you can accuse someone of being Imposotr (will be revealed as impostor) or Jester (will die), but if you miss u'll be punished (you will die)**
```
    /accuse <nickname>
```

---

## 📝 NOTES
- Designed for multiplayer
- Best expirience with **[voice-chat](https://www.curseforge.com/minecraft/mc-mods/simple-voice-chat)** plugin
- Works on **latest Minecraft** version **(1.21.11)**

## 📋 Future plans
- More impostor and innocent roles
- Cleaning up code
- Better powers
- Player perks
- In game settings (via panel or chat, so player dont have to use commands on chat)
- More **customization**
- Better **game menager**
- Hiding players in playerlist
- ...and maybe integration with **[Multiverse-Core](https://www.spigotmc.org/resources/multiverse-core.390/)**  
- Grant roles after specified time
- Lock settings modification during countdown

## 👜 You can check this plugin out on:
- **[Spigpt](https://www.spigotmc.org/resources/impostor.133598/)**
- **[Modrinth]()** Under Review
- **[CurseForge]()** I dont have icon rn
