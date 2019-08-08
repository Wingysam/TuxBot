package com.nickvision.tux;
import java.awt.Color;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CmdHandler extends ListenerAdapter
{
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        Message msg = event.getMessage();
        String rawMessage = msg.getContentRaw();
        String[] args = msg.getContentRaw().split("\\s+");
        String cmd = args[0].toLowerCase();
        CmdFun fun = new CmdFun();
        CmdGeneral general = new CmdGeneral();
        CmdLinux linux = new CmdLinux();
        CmdMisc misc = new CmdMisc();
        CmdModeration mod = new CmdModeration();
        if(rawMessage.contains("privatepage.vip") || rawMessage.contains("nakedphotos.club") || rawMessage.contains("viewc.site"))
        {
            System.out.println("===SPAM EVENT===");
            System.out.println("User: " + msg.getAuthor().getAsTag());
            System.out.println("Message: " + rawMessage);
            msg.getGuild().ban(msg.getAuthor(), 7, "bot").queue();
            return;
        }
        if(rawMessage.contains("https://discord.gg"))
        {
            msg.delete().queue();
            return;
        }
        if(msg.getAuthor().isBot() || !cmd.startsWith(Bot.prefix) || cmd.equals(Bot.prefix) || msg.getChannel().getType() == ChannelType.PRIVATE) return;
        cmd = cmd.replace(Bot.prefix, "");
        switch(cmd)
        {
            case "addrole":
                mod.addrole(msg, args);
                break;
            case "ban":
                mod.ban(msg, args);
                break;
            case "changelog":
                general.changelog(msg);
                break;
            case "coinflip":
                fun.coinflip(msg);
                break;
            case "d":
                break;
            case "distro":
                linux.distro(msg, args);
                break;
            case "echo":
                fun.echo(msg, args);
                break;
            case "help":
                general.help(msg);
                break;
            case "info":
                general.info(msg);
                break;
            case "invite":
                general.invite(msg);
                break;
            case "kick":
                mod.kick(msg, args);
                break;
            case "mute":
                mod.mute(msg, args);
                break;
            case "nickname":
                mod.nickname(msg, args);
                break;
            case "ping":
                misc.ping(msg);
                break;
            case "rand":
                fun.rand(msg, args);
                break;
            case "removerole":
                mod.removerole(msg, args);
                break;
            case "rm":
                mod.rm(msg, args);
                break;
            case "roleinfo":
                general.roleinfo(msg);
                break;
            case "source":
                general.source(msg);
                break;
            case "stop":
                misc.stop(msg);
                break;
            case "suggest":
                general.suggest(msg, args);
                break;
            case "unmute":
                mod.unmute(msg, args);
                break;
            case "uptime":
                misc.uptime(msg);
                break;
            case "verify":
                general.verify(msg);
                break;
            case "verifyAll":
                mod.verifyAll(msg);
                break;
            case "warn":
                mod.warn(msg, args);
                break;
            case "whois":
                general.whoIs(msg, args);
                break;
            default:
                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("Error")
                        .addField("Invalid Command", "Check help for available commands", false)
                        .setColor(Color.RED);
                msg.getChannel().sendTyping().queue();
                msg.getChannel().sendMessage(embed.build()).queue();
                break;
        }
    }
}
