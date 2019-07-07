package com.nickvision.tux;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.*;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.events.guild.member.*;

public class Events extends ListenerAdapter
{
    public void onReady(ReadyEvent event)
    {
        System.out.println("[BOT] Ready");
    }

    public void onDisconnect(DisconnectEvent event)
    {
        System.out.println("[BOT] Disconnected");
    }

    public void onReconnect(ReconnectedEvent event)
    {
        System.out.println("[BOT] Reconnected");
    }

    public void onGuildMemberJoin(GuildMemberJoinEvent event)
    {
        Member member = event.getMember();
        Guild guild = event.getGuild();
        TextChannel joinChannel = guild.getTextChannelsByName("join-leave", true).get(0);
        TextChannel verifyChannel = guild.getTextChannelsByName("verify", true).get(0);
        TextChannel rulesChannel = guild.getTextChannelsByName("rules", true).get(0);
        TextChannel botsChannel = guild.getTextChannelsByName("bots", true).get(0);
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("New Member Joined")
                .setDescription("Welcome " + member.getAsMention() + " to The Linux Community! This server has a verification system. Go into " + verifyChannel.getAsMention() + " and type `!verify` to get verified. Please read the rules before you start " + rulesChannel.getAsMention() + ". Go into " + botsChannel.getAsMention() + " and type `!distro` to give yourself a distro role. Contact a Support member if you need help.")
                .setFooter(member.getUser().getAsTag(), member.getUser().getAvatarUrl())
                .setColor(Bot.randomColor());
        joinChannel.sendMessage(embed.build()).queue();
        if(guild.getMembers().size() % 50 == 0)
        {
            EmbedBuilder embed2 = new EmbedBuilder()
                    .setTitle("Member Milestone")
                    .setDescription("Member Count: " + guild.getMembers().size())
                    .setColor(Bot.randomColor());
            joinChannel.sendMessage(embed2.build()).queue();
        }
    }

    public void onGuildMemberLeave(GuildMemberLeaveEvent event)
    {
        Member member = event.getMember();
        Guild guild = event.getGuild();
        TextChannel leaveChannel = guild.getTextChannelsByName("join-leave", true).get(0);
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Member Left")
                .setDescription(member.getAsMention() + " has left. Goodbye")
                .setFooter(member.getUser().getAsTag(), member.getUser().getAvatarUrl())
                .setColor(Bot.randomColor());
        leaveChannel.sendMessage(embed.build()).queue();
    }
}
