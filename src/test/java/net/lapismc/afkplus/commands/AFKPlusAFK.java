/*
 * Copyright 2018 Benjamin Martin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.lapismc.afkplus.commands;

import net.lapismc.afkplus.AFKPlayer;
import net.lapismc.afkplus.AFKPlus;
import net.lapismc.afkplus.AFKPlusPerms;
import net.lapismc.afkplus.util.LapisCommand;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class AFKPlusAFK extends LapisCommand {

    public AFKPlusAFK(AFKPlus plugin) {
        super(plugin, "afk", "Toggles your AFK status", new ArrayList<>());
    }

    @Override
    public void onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("afk")) {
            if (args.length == 0) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(plugin.AFKConfig.getMessage("AFKNotAPlayer"));
                    return;
                }
                Player p = (Player) sender;
                AFKPlayer player = plugin.getPlayer(p);
                if (!plugin.AFKPerms.isPermitted(p.getUniqueId(), AFKPlusPerms.Perm.UseCommand)) {
                    p.sendMessage(plugin.AFKConfig.getColoredMessage("NoPerms"));
                    return;
                }
                if (player.isAFK()) {
                    player.stopAFK();
                } else {
                    player.startAFK(true);
                }
            } else if (args.length == 1) {
                if (isNotAdmin(sender)) return;
                //noinspection deprecation
                OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
                if (op.isOnline()) {
                    AFKPlayer player = plugin.getPlayer(op);
                    if (player.isAFK()) {
                        player.stopAFK();
                    } else {
                        player.startAFK(true);
                    }
                } else {
                    if (sender instanceof Player) {
                        sender.sendMessage(plugin.AFKConfig.getColoredMessage("AFKNotOnline"));
                    } else {
                        sender.sendMessage(plugin.AFKConfig.getMessage("AFKNotOnline"));
                    }
                }
            } else {
                sender.sendMessage("Usage: /afk (player) Permission required to use another players name");
            }
        }
    }


}