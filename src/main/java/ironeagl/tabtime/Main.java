/*
 * Copyright (C) 2020 Iron_Eagl
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ironeagl.tabtime;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

/**
 *
 * @author Iron_Eagl
 */
public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        // start the updater
        updateScores();
    }
    
    @Override
    public void onDisable() {
        
    }
    
    // Runs in background, updates time for online players
    public void updateScores() {
        // make sure the objective exists and is set as default
        Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
        if (board.getObjective("z_tabtime") == null) {
            Objective obj = board.registerNewObjective("z_tabtime", "dummy", "none");
            obj.setDisplaySlot(DisplaySlot.PLAYER_LIST);
        } else {
            Objective obj = board.getObjective("z_tabtime");
            obj.setDisplaySlot(DisplaySlot.PLAYER_LIST);
        }
        
        // set up background update task
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                // initialize header and footer
                String header = ChatColor.AQUA + "Phoenix";
                String footer = ChatColor.AQUA + "--On-This-Week--\n";
                Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
                Objective obj = board.getObjective("z_tabtime");
                // loop through all players
                for (OfflinePlayer offline : Bukkit.getOfflinePlayers()) {
                    String name = offline.getName();
                    Score score = obj.getScore(name);
                    int currentscore = score.getScore();
                    //Online players are already in tab list, so just increment score
                    if (offline.isOnline()) {
                        int newscore = currentscore + 1;
                        score.setScore(newscore);
                    }
                    // add offline players with positive scores to footer
                    else {
                        if (score.getScore() > 0) {
                            // remove players who have not played for more than one week
                            long lastweek = System.currentTimeMillis() - 604800000;
                            if (offline.getLastPlayed() < lastweek) {
                                score.setScore(0);
                            } else {
                                // add each player to the "recently played" list
                                footer = footer + "\n" + ChatColor.DARK_AQUA + name + " " + ChatColor.GOLD + currentscore;
                            }
                        }
                    }
                }
                // send header & footer to every online player
                if (Bukkit.getOnlinePlayers().size() > 0) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.setPlayerListHeader(header);
                        player.setPlayerListFooter(footer);
                    }
            }
            }
        }, 60, 1200);   
    }   
}
