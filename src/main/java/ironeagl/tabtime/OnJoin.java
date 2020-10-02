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
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

/**
 *
 * @author Iron_Eagl
 */
public class OnJoin implements Listener {
    
    @EventHandler
     public void onPlayerJoin(PlayerJoinEvent event) {
        // initialize header and footer
        // This is the only line displayed above the online player names
        String header = ChatColor.AQUA + "- Phoenix -";  
        // This is the first line displayed below the online player names
        String footer = ChatColor.AQUA + "--Time-Played-Board--\n" + ChatColor.GRAY + "(no breaks > 7d)";
        Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
        Objective obj = board.getObjective("z_tabtime");
        // loop through all players
        for (OfflinePlayer offline : Bukkit.getOfflinePlayers()) {
            String name = offline.getName();
            Score score = obj.getScore(name); // score uses the names (entry) now
            int currentscore = score.getScore();
            // don't care about online/offline, just add to list
            if (score.getScore() > 0) {
                // convert score (minutes) to score(DDD:HH:MM)
                int days = (int) Math.floor(currentscore / 1440.0);
                int hours = (int) Math.floor((currentscore / 60.0) % 24);
                int minutes = (int) Math.floor(currentscore % 60);
                String time = String.format("%03d:%02d:%02d", days, hours, minutes);
                // add each player to the "recently played" list
                footer = footer + "\n" + ChatColor.DARK_AQUA + name + " " + ChatColor.GOLD + time;
            }
        }
        // send header & footer to the just-joined player
        event.getPlayer().setPlayerListHeader(header);
        event.getPlayer().setPlayerListFooter(footer);
     }
}
