package com.nickvision.tux;
import java.awt.Color;
import java.lang.management.*;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.*;

public class CmdMisc
{
    EmbedBuilder embed;

    public void ping(Message msg)
    {
        embed = new EmbedBuilder()
                .setTitle("Ping")
                .setDescription("Pong!")
                .setColor(Bot.randomColor());
        msg.getChannel().sendTyping().queue();
        msg.getChannel().sendMessage(embed.build()).queue();
    }

    public void stop(Message msg)
    {
        embed = new EmbedBuilder();
        if(msg.getGuild().getOwner() == msg.getMember())
        {
            embed.setTitle("Shutdown");
            embed.setDescription("Tux was shutdown");
            embed.setColor(Bot.randomColor());
            msg.getChannel().sendTyping().queue();
            msg.getChannel().sendMessage(embed.build()).queue();
            try
            {
                Thread.sleep(200);
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
            System.exit(0);
        }
        else
        {
            embed.setTitle("Error");
            embed.addField("Invalid Permissions", "You must be the server owner to run this command", false);
            embed.setColor(Color.RED);
            msg.getChannel().sendTyping().queue();
            msg.getChannel().sendMessage(embed.build()).queue();
        }
    }

    public void uptime(Message msg)
    {
        RuntimeMXBean rb = ManagementFactory.getRuntimeMXBean();
        long uptime = rb.getUptime();
        int seconds = (int) (uptime / 1000) % 60 ;
        int minutes = (int) ((uptime / (1000*60)) % 60);
        int hours   = (int) ((uptime / (1000*60*60)) % 24);
        String uptimeString = String.format("%d:%d:%d", hours, minutes, seconds);
        embed = new EmbedBuilder()
                .setTitle("Uptime")
                .setDescription(uptimeString)
                .setColor(Bot.randomColor());
        msg.getChannel().sendTyping().queue();
        msg.getChannel().sendMessage(embed.build()).queue();
    }
}
