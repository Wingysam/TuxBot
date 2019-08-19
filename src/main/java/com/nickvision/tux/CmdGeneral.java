package com.nickvision.tux;
import java.awt.Color;
import java.sql.Date;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.*;

public class CmdGeneral
{
    EmbedBuilder embed;

    public void changelog(Message msg)
    {
        embed = new EmbedBuilder()
                .setAuthor("Tux Changelog", null, msg.getGuild().getIconUrl())
                .addField("Current Version", Bot.version, false)
                .addField("Updated JDA Version", "JDA 4 is no longer in beta", false)
                .addField("Added Slots Command", "Test your luck", false)
                .addField("Added 8ball Command", "Ask Tux a question", false)
                .setColor(Bot.randomColor());
        msg.getChannel().sendTyping().queue();
        msg.getChannel().sendMessage(embed.build()).queue();
    }

    public void help(Message msg)
    {
        String general = "`changelog` ~ Shows the new features of the current version" +
                "\n`help` ~ Shows a list of avaliable commands" +
                "\n`info` ~ Shows info about the bot and server" +
                "\n`invite` ~ Shows The Linux Community's invite link" +
                "\n`roleinfo` ~ Shows the number of users in each role in the server" +
                "\n`source` ~ Shows the link to Tux's source code" +
                "\n`suggest <Suggestion>` ~ Adds your suggestion to the suggestion channel" +
                "\n`verify` ~ Verifies the user who uses the command" +
                "\n`whois <@User>` ~ Shows info about the pinged user";

        String linux = "`distro <Distro Name>` ~ Get a distro role";

        String fun = "`coinflip` ~ Heads or Tails?" +
                "\n`echo <Phrase>` ~ Make Tux look like he is saying something" +
                "\n`rand <Range 1> <Range 2>` ~ Generates a random number in the given range" +
                "\n`slots` ~ Test your luck and spin the machine" +
                "\n`8ball <Question>` ~ Ask Tux a question";

        String mod = "`addrole <@User> <Role Name>` ~ Adds the given role to the pinged user" +
                "\n`ban <@User> <Reason>` ~ Bans the pinged user for the given reason" +
                "\n`kick <@User> <Reason>` ~ Kicks the pinged user for the given reason" +
                "\n`mute <@User>` ~ Mutes the pinged user" +
                "\n`nickname <New Nickname>` ~ Changes your nickname" +
                "\n`removerole <@User> <Role Name>` ~ Removes the given role from the pinged user" +
                "\n`rm <# of Messages>` ~ Deletes the number of messages provided" +
                "\n`unmute <@User>` ~ Unmutes the pinged user" +
                "\n`warn <@User> <Reason>` ~ Warns the pinged user for the given reason";

        String misc = "`ping` ~ Pings the bot" +
                "\n`shutdown` ~ Shuts the bot down" +
                "\n`uptime` ~ Shows the current uptime of the bot";

        embed = new EmbedBuilder()
                .setTitle("Commands for Tux")
                .addField("Prefix", Bot.prefix, false)
                .addField("General Commands", general, false)
                .addField("Linux Commands", linux, false)
                .addField("Fun Commands", fun, false)
                .addField("Mod Commands", mod, false)
                .addField("Misc Commands", misc, false)
                .setColor(Bot.randomColor());
        msg.getAuthor().openPrivateChannel().queue((channel) ->
                {
                    channel.sendMessage(embed.build()).queue((good) ->
                    {
                        EmbedBuilder embed2 = new EmbedBuilder()
                                .setTitle("Check Your DMs")
                                .setDescription("The commands have been sent to your DMs")
                                .setColor(Bot.randomColor());
                        msg.getChannel().sendTyping().queue();
                        msg.getChannel().sendMessage(embed2.build()).queue();
                    }, (bad) ->
                    {
                        msg.getChannel().sendTyping().queue();
                        msg.getChannel().sendMessage(embed.build()).queue();
                    });

                });
    }

    public void info(Message msg)
    {
        Guild guild = msg.getGuild();
        int humanCount = 0, botCount = 0, onlineCount = 0, offlineCont = 0;
        String roleList = "";
        for(Member m : guild.getMembers())
        {
            OnlineStatus mStatus = m.getOnlineStatus();
            if(m.getUser().isBot()) botCount++;
            else humanCount++;
            if(mStatus == OnlineStatus.ONLINE || mStatus == OnlineStatus.IDLE || mStatus == OnlineStatus.DO_NOT_DISTURB) onlineCount++;
            else offlineCont++;
        }
        for(Role r : guild.getRoles()) roleList += r.getName() + ", ";
        embed = new EmbedBuilder()
                .setAuthor(guild.getName(), null, guild.getIconUrl())
                .addField("Bot Version", Bot.version, true)
                .addField("API", "Discord JDA", true)
                .addField("Owner", guild.getOwner().getAsMention(), true)
                .addField("Region", guild.getRegionRaw(), true)
                .addField("Categories", String.valueOf(guild.getCategories().size()), true)
                .addField("Text Channels", String.valueOf(guild.getTextChannels().size()), true)
                .addField("Voice Channels", String.valueOf(guild.getVoiceChannels().size()), true)
                .addField("Members", String.valueOf(guild.getMembers().size()), true)
                .addField("Humans", String.valueOf(humanCount), true)
                .addField("Bots", String.valueOf(botCount), true)
                .addField("Online", String.valueOf(onlineCount), true)
                .addField("Offline", String.valueOf(offlineCont), true)
                .addField("Roles", String.valueOf(guild.getRoles().size()), true)
                .addField("Roles List", roleList, false)
                .setFooter("Server Created: " + Date.from(guild.getTimeCreated().toInstant()).toString())
                .setColor(Bot.randomColor());
        msg.getChannel().sendTyping().queue();
        msg.getChannel().sendMessage(embed.build()).queue();
    }

    public void invite(Message msg)
    {
        embed = new EmbedBuilder()
                .setTitle("The Linux Community")
                .setDescription("https://discord.gg/uuKtzCw")
                .setColor(Bot.randomColor());
        msg.getChannel().sendTyping().queue();
        msg.getChannel().sendMessage(embed.build()).queue();
    }

    public void roleinfo(Message msg)
    {
        Guild guild = msg.getGuild();
        embed = new EmbedBuilder();
        embed.setTitle("Role Info");
        guild.getRoles().forEach(role ->
        {
            int memberCount = 0;
            for(Member m : guild.getMembers())
            {
                if(m.getRoles().contains(role)) memberCount++;
            }
            if(role.getName().equals("@everyone")) embed.addField(role.getName(), String.valueOf(guild.getMembers().size()), true);
            else embed.addField(role.getName(), String.valueOf(memberCount), true);
        });
        embed.setColor(Bot.randomColor());
        msg.getChannel().sendTyping().queue();
        msg.getChannel().sendMessage(embed.build()).queue();
    }

    public void source(Message msg)
    {
        embed = new EmbedBuilder()
                .setTitle("Github Repo")
                .setDescription("https://github.com/nlogozzo/TuxBot")
                .setColor(Bot.randomColor());
        msg.getChannel().sendTyping().queue();
        msg.getChannel().sendMessage(embed.build()).queue();
    }

    public void suggest(Message msg, String[] args)
    {
        TextChannel suggestionChannel = msg.getGuild().getTextChannelsByName("suggestions", true).get(0);
        embed = new EmbedBuilder();
        if(args.length > 1)
        {
            String toSuggest = "";
            for(int i = 1; i < args.length; i++) toSuggest += args[i] + " ";
            embed.setTitle("New Suggest");
            embed.setDescription(toSuggest);
            embed.setFooter(msg.getAuthor().getAsTag(), msg.getAuthor().getAvatarUrl());
            embed.setColor(Bot.randomColor());
            suggestionChannel.sendTyping().queue();
            suggestionChannel.sendMessage(embed.build()).queue(newMsg -> {
                newMsg.addReaction("\uD83D\uDC4D").queue();
                newMsg.addReaction("\uD83D\uDC4E").queue();
            });
            msg.delete().queue();

        }
        else
        {
            embed.setTitle("Error");
            embed.addField("Missing Args", "You need something to suggest", false);
            embed.setColor(Color.RED);
            msg.getChannel().sendTyping().queue();
            msg.getChannel().sendMessage(embed.build()).queue();
        }
    }

    public void verify(Message msg)
    {
        Member toVerify = msg.getMember();
        Role verifiedRole = msg.getGuild().getRolesByName("verified", true).get(0);
        embed = new EmbedBuilder();
        if(toVerify.getRoles().contains(verifiedRole))
        {
            embed.setTitle("Error");
            embed.setDescription(toVerify.getAsMention() + " is already verified");
            embed.setColor(Color.red);
        }
        else
        {
            msg.getGuild().addRoleToMember(toVerify, verifiedRole).queue();
            embed.setTitle("Verified!");
            embed.setDescription(toVerify.getAsMention() + " is now verified");
            embed.setColor(Bot.randomColor());
        }
        msg.getChannel().sendTyping().queue();
        msg.getChannel().sendMessage(embed.build()).queue();
    }

    public void whoIs(Message msg, String[] args)
    {
        Member toWhoIs = msg.getMember();
        Role verifiedRole = msg.getGuild().getRolesByName("verified", true).get(0);
        Role mutedRole = msg.getGuild().getRolesByName("muted", true).get(0);
        String roleList = "", isBot = "No", isVerified = "No", isMuted = "No", nickname = "";
        if(args.length > 1)
        {
            try
            {
                toWhoIs = msg.getMentionedMembers().get(0);
            }
            catch(Exception ex)
            {
                toWhoIs = msg.getMember();
            }
        }
        if(toWhoIs.getUser().isBot()) isBot = "Yes";
        for(Role r : toWhoIs.getRoles())
        {
            roleList += r.getName() + ", ";
            if(r == verifiedRole) isVerified = "Yes";
            if(r == mutedRole) isMuted = "Yes";
        }
        nickname = toWhoIs.getNickname();
        if(nickname == null || nickname.isEmpty())
        {
            nickname = toWhoIs.getUser().getName();
        }
        embed = new EmbedBuilder()
                .setAuthor(toWhoIs.getUser().getAsTag(), null, toWhoIs.getUser().getAvatarUrl())
                .addField("User Tag", toWhoIs.getAsMention(), true)
                .addField("Nickname", nickname, true)
                .addField("Status", toWhoIs.getOnlineStatus().name(), true)
                .addField("Joined", Date.from(toWhoIs.getTimeJoined().toInstant()).toString(), true)
                .addField("Is Bot", isBot, true)
                .addField("Is Verified", isVerified, true)
                .addField("Is Muted", isMuted, true)
                .addField("Role List", roleList, false)
                .setThumbnail(toWhoIs.getUser().getAvatarUrl())
                .setColor(Bot.randomColor());
        msg.getChannel().sendTyping().queue();
        msg.getChannel().sendMessage(embed.build()).queue();
    }
}
