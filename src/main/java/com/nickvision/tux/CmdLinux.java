package com.nickvision.tux;
import java.awt.Color;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;

public class CmdLinux
{
    EmbedBuilder embed;

    public void distro(Message msg, String[] args)
    {
        embed = new EmbedBuilder();
        if(args.length > 1)
        {
            String[] distros = {"arch", "manjaro", "ubuntu", "debian", "gentoo", "fedora", "suse", "centos", "other"};
            boolean validDistro = false;
            Guild guild = msg.getGuild();
            Member toDistro = msg.getMember();
            Role distroRole;
            for (String s : distros)
            {
                if (args[1].toLowerCase().equals(s))
                {
                    validDistro = true;
                    break;
                }
            }
            if (validDistro)
            {
                distroRole = guild.getRolesByName(args[1], true).get(0);
                if(toDistro.getRoles().contains(distroRole))
                {
                    embed.setTitle("Distro Role Removed");
                    embed.addField("User", toDistro.getAsMention(), true);
                    embed.addField("Role", distroRole.getAsMention(), true);
                    embed.setColor(Bot.randomColor());
                    guild.removeRoleFromMember(toDistro, distroRole).queue();
                }
                else {
                    embed.setTitle("Distro Role Added");
                    embed.addField("User", toDistro.getAsMention(), true);
                    embed.addField("Role", distroRole.getAsMention(), true);
                    embed.setColor(Bot.randomColor());
                    guild.addRoleToMember(toDistro, distroRole).queue();
                }
            }
            else
            {
                embed.setTitle("Error");
                embed.addField("Invalid Distro", "That distro does not exist in the server", false);
                embed.addField("Available Distros", "arch, manjaro, ubuntu. debian, gentoo, fedora, suse, centos, other", false);
                embed.setColor(Color.RED);
            }
        }
        else
        {
            embed.setTitle("Error");
            embed.addField("Invalid Distro", "That distro role does not exist in the server", false);
            embed.addField("Available Distros", "arch, manjaro, ubuntu. debian, gentoo, fedora, suse, centos, other", false);
            embed.setColor(Color.RED);
        }
        msg.getChannel().sendTyping().queue();
        msg.getChannel().sendMessage(embed.build()).queue();
    }
}
