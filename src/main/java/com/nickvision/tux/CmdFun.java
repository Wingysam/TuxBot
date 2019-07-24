package com.nickvision.tux;
import java.awt.Color;
import java.util.Random;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;

public class CmdFun
{
    EmbedBuilder embed;

    public void coinflip(Message msg)
    {
        Random r = new Random();
        int rNum = r.nextInt(5);
        String result = "";
        switch (rNum)
        {
            case 0:
                result = "heads";
                break;
            case 1:
                result = "tails";
                break;
            case 2:
                result = "tails";
                break;
            case 3:
                result = "heads";
                break;
            case 4:
                result = "heads";
                break;
            case 5:
                result = "tails";
                break;
        }
        embed = new EmbedBuilder()
                .setTitle("Coin Flip")
                .setDescription(result)
                .setColor(Bot.randomColor());
        msg.getChannel().sendTyping().queue();
        msg.getChannel().sendMessage(embed.build()).queue();
    }

    public void echo(Message msg, String[] args)
    {
        embed = new EmbedBuilder();
        if(args.length > 1)
        {
            String toEcho = "";
            for(int i = 1; i < args.length; i++) toEcho += args[i] + " ";
            embed.setTitle(toEcho);
            embed.setColor(Bot.randomColor());
        }
        else
        {
            embed.setTitle("Error");
            embed.addField("Missing Args", "You need something to echo", false);
            embed.setColor(Color.RED);
        }
        msg.getChannel().sendTyping().queue();
        msg.getChannel().sendMessage(embed.build()).queue();
    }

    public void rand(Message msg, String[] args)
    {
        embed = new EmbedBuilder();
        if(args.length >= 3)
        {
            int min, max;
            try
            {
                int a = Integer.parseInt(args[1]);
                int b = Integer.parseInt(args[2]);
                if(a > b)
                {
                    min = b;
                    max = a;
                }
                else
                {
                    min = a;
                    max = b;
                }
            }
            catch(Exception ex)
            {
                embed.setTitle("Error");
                embed.addField("Invalid Args", "Your min and max need to be numbers", false);
                embed.setColor(Color.RED);
                msg.getChannel().sendTyping().queue();
                msg.getChannel().sendMessage(embed.build()).queue();
                return;
            }
            if(min == max)
            {
                embed.setTitle("Error");
                embed.addField("Invalid Args", "Your min can't be equal to your max", false);
                embed.setColor(Color.RED);
            }
            else {
                int rNum = new Random().nextInt((max - min) + 1) + min;
                embed.setTitle("Random Number");
                embed.setDescription(String.valueOf(rNum));
                embed.setColor(Bot.randomColor());
            }
        }
        else
        {
            embed.setTitle("Error");
            embed.addField("Missing Args", "rand <Range 1> <Range 2>", false);
            embed.setColor(Color.RED);
        }
        msg.getChannel().sendTyping().queue();
        msg.getChannel().sendMessage(embed.build()).queue();
    }
}
