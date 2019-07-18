package com.nickvision.tux;
import java.awt.Color;
import java.util.List;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

public class CmdModeration
{
    EmbedBuilder embed;

    private boolean hasPerms(Member m, Permission p) { return m.getPermissions().contains(p); }

    public void addrole(Message msg, String[] args)
    {
        embed = new EmbedBuilder();
        if(args.length >= 3)
        {
            if(hasPerms(msg.getMember(), Permission.MANAGE_ROLES))
            {
                Guild guild = msg.getGuild();
                Member toAddRole;
                Role role;
                try
                {
                    toAddRole = msg.getMentionedMembers().get(0);
                } catch (Exception ex)
                {
                    embed.setTitle("Error");
                    embed.addField("Invalid User", "Please mention the user to a the role to", false);
                    embed.setColor(Color.RED);
                    msg.getChannel().sendTyping().queue();
                    msg.getChannel().sendMessage(embed.build()).queue();
                    return;
                }
                try
                {
                    role = guild.getRolesByName(args[2], true).get(0);
                } catch (Exception ex)
                {
                    embed.setTitle("Error");
                    embed.addField("Invalid Role", "That role does not exist in this server", false);
                    embed.setColor(Color.RED);
                    msg.getChannel().sendTyping().queue();
                    msg.getChannel().sendMessage(embed.build()).queue();
                    return;
                }
                if(!toAddRole.getRoles().contains(role))
                {
                    guild.addRoleToMember(toAddRole, role).queue();
                    embed.setTitle("Role Added To User");
                    embed.addField("User", toAddRole.getAsMention(), true);
                    embed.addField("Role", role.getAsMention(), true);
                    embed.setColor(Bot.randomColor());
                }
                else {
                    embed.setTitle("Error");
                    embed.setDescription(toAddRole.getAsMention() + " already has the role: " + role.getAsMention());
                    embed.setColor(Color.RED);
                }
            }
            else
            {
                embed.setTitle("Error");
                embed.addField("Invalid Permissions", "You need the permission: MANAGE_ROLES", false);
                embed.setColor(Color.RED);
            }
        }
        else
        {
            embed.setTitle("Error");
            embed.addField("Missing Args", "addrole <@User> <Role Name>", false);
            embed.setColor(Color.RED);
        }
        msg.getChannel().sendTyping().queue();
        msg.getChannel().sendMessage(embed.build()).queue();
    }

    public void ban(Message msg, String[] args)
    {
        embed = new EmbedBuilder();
        if(args.length >= 3)
        {
            if(hasPerms(msg.getMember(), Permission.BAN_MEMBERS))
            {
                Guild guild = msg.getGuild();
                Member toBan;
                String reason = "";
                try
                {
                    toBan = msg.getMentionedMembers().get(0);
                } catch (Exception ex)
                {
                    embed.setTitle("Error");
                    embed.addField("Invalid User", "Please mention a user to ban", false);
                    embed.setColor(Color.RED);
                    msg.getChannel().sendTyping().queue();
                    msg.getChannel().sendMessage(embed.build()).queue();
                    return;
                }
                for(int i = 2; i < args.length; i++) reason += args[i] + " ";
                if(toBan == msg.getGuild().getOwner())
                {
                    embed.setTitle("Error");
                    embed.addField("Invalid User", "You can not ban the owner", false);
                    embed.setColor(Color.RED);
                }
                else
                {
                    guild.ban(toBan, 7, reason).queue();
                    embed.setTitle("User Banned");
                    embed.addField("User", toBan.getAsMention(), true);
                    embed.addField("Reason", reason, true);
                    embed.setColor(Bot.randomColor());
                }
            }
            else
            {
                embed.setTitle("Error");
                embed.addField("Invalid Permissions", "You need the permission: BAN_MEMBERS", false);
                embed.setColor(Color.RED);
            }
        }
        else
        {
            embed.setTitle("Error");
            embed.addField("Missing Args", "ban <@User> <Reason>", false);
            embed.setColor(Color.RED);
        }
        msg.getChannel().sendTyping().queue();
        msg.getChannel().sendMessage(embed.build()).queue();
    }

    public void kick(Message msg, String[] args)
    {
        embed = new EmbedBuilder();
        if(args.length >= 3)
        {
            if(hasPerms(msg.getMember(), Permission.KICK_MEMBERS))
            {
                Guild guild = msg.getGuild();
                Member toKick;
                String reason = "";
                try
                {
                    toKick = msg.getMentionedMembers().get(0);
                } catch (Exception ex)
                {
                    embed.setTitle("Error");
                    embed.addField("Invalid User", "Please mention a user to kick", false);
                    embed.setColor(Color.RED);
                    msg.getChannel().sendTyping().queue();
                    msg.getChannel().sendMessage(embed.build()).queue();
                    return;
                }
                for(int i = 2; i < args.length; i++) reason += args[i] + " ";
                if(toKick == msg.getGuild().getOwner())
                {
                    embed.setTitle("Error");
                    embed.addField("Invalid User", "You can not kick the owner", false);
                    embed.setColor(Color.RED);
                }
                else
                {
                    guild.kick(toKick, reason).queue();
                    embed.setTitle("User Kicked");
                    embed.addField("User", toKick.getAsMention(), true);
                    embed.addField("Reason", reason, true);
                    embed.setColor(Bot.randomColor());
                }
            }
            else
            {
                embed.setTitle("Error");
                embed.addField("Invalid Permissions", "You need the permission: KICK_MEMBERS", false);
                embed.setColor(Color.RED);
            }
        }
        else
        {
            embed.setTitle("Error");
            embed.addField("Missing Args", "kick <@User> <Reason>", false);
            embed.setColor(Color.RED);
        }
        msg.getChannel().sendTyping().queue();
        msg.getChannel().sendMessage(embed.build()).queue();
    }

    public void mute(Message msg, String[] args)
    {
        embed = new EmbedBuilder();
        if(args.length > 1)
        {
            if(hasPerms(msg.getMember(), Permission.MANAGE_ROLES))
            {
                Guild guild = msg.getGuild();
                Member toMute;
                Role mutedRole = guild.getRolesByName("muted", true).get(0);
                try
                {
                    toMute = msg.getMentionedMembers().get(0);
                } catch (Exception ex)
                {
                    embed.setTitle("Error");
                    embed.addField("Invalid User", "Please mention a user to mute", false);
                    embed.setColor(Color.RED);
                    msg.getChannel().sendTyping().queue();
                    msg.getChannel().sendMessage(embed.build()).queue();
                    return;
                }
                if(toMute == msg.getGuild().getOwner())
                {
                    embed.setTitle("Error");
                    embed.addField("Invalid User", "You can not mute the owner", false);
                    embed.setColor(Color.RED);
                }
                else
                {
                    if(!toMute.getRoles().contains(mutedRole))
                    {
                        guild.addRoleToMember(toMute, mutedRole).queue();
                        embed.setTitle("User Muted");
                        embed.addField("User", toMute.getAsMention(), true);
                        embed.setColor(Bot.randomColor());
                    }
                    else {
                        embed.setTitle("Error");
                        embed.setDescription(toMute.getAsMention() + " is already muted");
                        embed.setColor(Color.RED);
                    }
                }
            }
            else
            {
                embed.setTitle("Error");
                embed.addField("Invalid Permissions", "You need the permission: MANAGE_ROLES", false);
                embed.setColor(Color.RED);
            }
        }
        else
        {
            embed.setTitle("Error");
            embed.addField("Missing Args", "mute <@User>", false);
            embed.setColor(Color.RED);
        }
        msg.getChannel().sendTyping().queue();
        msg.getChannel().sendMessage(embed.build()).queue();
    }

    public void nickname(Message msg, String[] args)
    {
        embed = new EmbedBuilder();
        if(args.length > 1)
        {
            Member toNickname = msg.getMember();
            String oldNickname = toNickname.getNickname();
            if(oldNickname == null || oldNickname.isEmpty())
            {
                oldNickname = toNickname.getUser().getName();
            }
            String newNickname = args[1];
            toNickname.modifyNickname(newNickname).queue();
            embed.setTitle("Nickname Changed");
            embed.addField("User", toNickname.getAsMention(), true);
            embed.addField("Old Nickname", oldNickname, true);
            embed.addField("New Nickname", newNickname, true);
            embed.setColor(Bot.randomColor());
        }
        else
        {
            embed.setTitle("Error");
            embed.addField("Missing Args", "nickname <New Nickname>", false);
            embed.setColor(Color.RED);
        }
        msg.getChannel().sendTyping().queue();
        msg.getChannel().sendMessage(embed.build()).queue();
    }

    public void removerole(Message msg, String[] args)
    {
        embed = new EmbedBuilder();
        if(args.length >= 3)
        {
            if(hasPerms(msg.getMember(), Permission.MANAGE_ROLES))
            {
                Guild guild = msg.getGuild();
                Member toAddRole;
                Role role;
                try
                {
                    toAddRole = msg.getMentionedMembers().get(0);
                } catch (Exception ex)
                {
                    embed.setTitle("Error");
                    embed.addField("Invalid User", "Please mention the user to remove the role from", false);
                    embed.setColor(Color.RED);
                    msg.getChannel().sendTyping().queue();
                    msg.getChannel().sendMessage(embed.build()).queue();
                    return;
                }
                try
                {
                    role = guild.getRolesByName(args[2], true).get(0);
                } catch (Exception ex)
                {
                    embed.setTitle("Error");
                    embed.addField("Invalid Role", "That role does not exist in this server", false);
                    embed.setColor(Color.RED);
                    msg.getChannel().sendTyping().queue();
                    msg.getChannel().sendMessage(embed.build()).queue();
                    return;
                }
                if(toAddRole.getRoles().contains(role))
                {
                    guild.removeRoleFromMember(toAddRole, role).queue();
                    embed.setTitle("Role Removed From User");
                    embed.addField("User", toAddRole.getAsMention(), true);
                    embed.addField("Role", role.getAsMention(), true);
                    embed.setColor(Bot.randomColor());
                }
                else {
                    embed.setTitle("Error");
                    embed.setDescription(toAddRole.getAsMention() + " does not have the role: " + role.getAsMention());
                    embed.setColor(Color.RED);
                }
            }
            else
            {
                embed.setTitle("Error");
                embed.addField("Invalid Permissions", "You need the permission: MANAGE_ROLES", false);
                embed.setColor(Color.RED);
            }
        }
        else
        {
            embed.setTitle("Error");
            embed.addField("Missing Args", "removerole <@User> <Role Name>", false);
            embed.setColor(Color.RED);
        }
        msg.getChannel().sendTyping().queue();
        msg.getChannel().sendMessage(embed.build()).queue();
    }

    public void rm(Message msg, String[] args)
    {
        embed = new EmbedBuilder();
        if(args.length > 1)
        {
            if(hasPerms(msg.getMember(), Permission.MESSAGE_MANAGE))
            {
                TextChannel channel = msg.getTextChannel();
                int toDelete;
                try
                {
                    toDelete = Integer.parseInt(args[1]);
                }
                catch(Exception ex)
                {
                    embed.setTitle("Error");
                    embed.addField("Invalid Number", "Please provide a number of messages to delete", false);
                    embed.setColor(Color.RED);
                    msg.getChannel().sendTyping().queue();
                    msg.getChannel().sendMessage(embed.build()).queue();
                    return;
                }
                if(toDelete < 2 || toDelete > 100)
                {
                    embed.setTitle("Error");
                    embed.addField("Invalid Number", "Please provide a number between 2-100", false);
                    embed.setColor(Color.RED);
                }
                else
                {
                    MessageHistory history = new MessageHistory(channel);
                    List<Message> msgs;
                    msg.delete().queue();
                    msgs = history.retrievePast(toDelete).complete();
                    channel.deleteMessages(msgs).queue();
                    embed.setTitle("Messages Deleted");
                    embed.addField("Number of Messages Deleted", String.valueOf(toDelete), false);
                    embed.setColor(Bot.randomColor());
                }
            }
            else
            {
                embed.setTitle("Error");
                embed.addField("Invalid Permissions", "You need the permission: MESSAGE_MANAGE", false);
                embed.setColor(Color.RED);
            }
        }
        else
        {
            embed.setTitle("Error");
            embed.addField("Missing Args", "rm <# of Messages>", false);
            embed.setColor(Color.RED);
        }
        msg.getChannel().sendTyping().queue();
        msg.getChannel().sendMessage(embed.build()).queue();
    }

    public void unmute(Message msg, String[] args)
    {
        embed = new EmbedBuilder();
        if(args.length > 1)
        {
            if(hasPerms(msg.getMember(), Permission.MANAGE_ROLES))
            {
                Guild guild = msg.getGuild();
                Member toUnmute;
                Role mutedRole = guild.getRolesByName("muted", true).get(0);
                try
                {
                    toUnmute = msg.getMentionedMembers().get(0);
                } catch (Exception ex)
                {
                    embed.setTitle("Error");
                    embed.addField("Invalid User", "Please mention a user to mute", false);
                    embed.setColor(Color.RED);
                    msg.getChannel().sendTyping().queue();
                    msg.getChannel().sendMessage(embed.build()).queue();
                    return;
                }
                if(toUnmute.getRoles().contains(mutedRole))
                {
                    guild.removeRoleFromMember(toUnmute, mutedRole).queue();
                    embed.setTitle("User Unmuted");
                    embed.addField("User", toUnmute.getAsMention(), true);
                    embed.setColor(Bot.randomColor());
                }
                else {
                    embed.setTitle("Error");
                    embed.setDescription(toUnmute.getAsMention() + " is not muted");
                    embed.setColor(Color.RED);
                }
            }
            else
            {
                embed.setTitle("Error");
                embed.addField("Invalid Permissions", "You need the permission: MANAGE_ROLES", false);
                embed.setColor(Color.RED);
            }
        }
        else
        {
            embed.setTitle("Error");
            embed.addField("Missing Args", "unmute <@User>", false);
            embed.setColor(Color.RED);
        }
        msg.getChannel().sendTyping().queue();
        msg.getChannel().sendMessage(embed.build()).queue();
    }

    public void warn(Message msg, String[] args)
    {
        embed = new EmbedBuilder();
        if(args.length >= 3)
        {
            if(hasPerms(msg.getMember(), Permission.KICK_MEMBERS))
            {
                Member toWarn;
                String reason = "";
                try
                {
                    toWarn = msg.getMentionedMembers().get(0);
                } catch (Exception ex)
                {
                    embed.setTitle("Error");
                    embed.addField("Invalid User", "Please mention a user to warn", false);
                    embed.setColor(Color.RED);
                    msg.getChannel().sendTyping().queue();
                    msg.getChannel().sendMessage(embed.build()).queue();
                    return;
                }
                for(int i = 2; i < args.length; i++) reason += args[i] + " ";
                if(toWarn == msg.getGuild().getOwner())
                {
                    embed.setTitle("Error");
                    embed.addField("Invalid User", "You can not warn the owner", false);
                    embed.setColor(Color.RED);
                }
                else
                {
                    embed.setTitle("New Warning");
                    embed.addField("User", toWarn.getAsMention(), true);
                    embed.addField("Reason", reason, true);
                    embed.setColor(Bot.randomColor());
                }
            }
            else
            {
                embed.setTitle("Error");
                embed.addField("Invalid Permissions", "You need the permission: KICK_MEMBERS", false);
                embed.setColor(Color.RED);
            }
        }
        else
        {
            embed.setTitle("Error");
            embed.addField("Missing Args", "warn <@User> <Reason>", false);
            embed.setColor(Color.RED);
        }
        msg.getChannel().sendTyping().queue();
        msg.getChannel().sendMessage(embed.build()).queue();
    }
}
