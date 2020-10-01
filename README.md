# TabTime
Minecraft Spigot/Bukkit Plugin: shows who has recently been on the server in the tab list  
Works with versions 1.14-1.16

TabTime counts the minutes that every player spends online, and displays offline players in the tab list footer.  If a player hasn't logged on in over a week, their count is reset to zero and they are no longer displayed on the list.  The tab list display can be customized through editing line 65, 67, and 90 in `/src/main/java/ironeagl/tabtime/Main.java`.  

This repository is a full project, and should be accessible to almost any IDE, although I recommend NetBeans: https://netbeans.org/. Chat color and text are relatively self-explanatory, simply edit and then recompile.  

A precompiled version can be found in the `/target` folder for convenience.
