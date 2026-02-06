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

## 🛠️ Building it yourself
1. Clone or download repo
2. Open terminal and navigate into folder with plugin
3. Download maven (on windows add it into Env Variables)
4. Compile plugin and create package
```
    mvn clean compile
    mvn clean package
```
5. Then drop compiled `.jar` package into your server's `plugins` folder
6. Restart your server

---

## In-Game Commands

### 🔧 Game Setup
**Select the number of impostors**
```
    /setimpostors <num>
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

---

## NOTES
- Designed for multiplayer
- Best expirience with **[voice-chat](https://www.curseforge.com/minecraft/mc-mods/simple-voice-chat)** plugin
- Works on **latest Minecraft** version (1.21.11)
