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
            Guild guild = msg.getGuild();
            Member toDistro = msg.getMember();
            Role distroRole;
            try
            {
                distroRole = guild.getRolesByName(args[1], true).get(0);
            }
            catch(Exception x)
            {
                embed.setTitle("Error");
                embed.addField("Invalid Distro", "That distro does not exist in the server", false);
                embed.addField("Available Distros", "arch, manjaro, ubuntu. debian, gentoo, fedora, suse, centos", false);
                embed.setColor(Color.RED);
                msg.getChannel().sendTyping().queue();
                msg.getChannel().sendMessage(embed.build()).queue();
                return;
            }
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
            embed.addField("Invalid Distro", "That distro role does not exist in the server", false);
            embed.addField("Available Distros", "arch, manjaro, ubuntu. debian, gentoo, fedora, suse, centos", false);
            embed.setColor(Color.RED);
        }
        msg.getChannel().sendTyping().queue();
        msg.getChannel().sendMessage(embed.build()).queue();
    }
}
