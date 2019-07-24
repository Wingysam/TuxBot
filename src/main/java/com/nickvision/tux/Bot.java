package com.nickvision.tux;
import java.awt.*;
import java.util.*;
import java.io.*;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.*;
import javax.security.auth.login.LoginException;

public class Bot
{
    private String token;
    public static String prefix, version;
    public JDA jda;

    public Bot()
    {
        version = "9.2.2";
        System.out.println("===Tux Bot for Discord===");
        System.out.println("Version: " + version);
        getBotConfig();
        System.out.println("=========================");
    }

    private void getBotConfig()
    {
        Scanner sc = new Scanner(System.in);
        Properties prop = new Properties();
        String configPath = System.getProperty("user.dir") + "\\config.properties";
        configPath = configPath.replace("\\", "/");
        File configFile = new File(configPath);
        if(configFile.exists())
        {
            try (InputStream input = new FileInputStream(configPath))
            {
                prop.load(input);
                token = prop.getProperty("token");
                prefix = prop.getProperty("prefix");
                System.out.println("Token: " + token);
                System.out.println("Prefix: " + prefix);
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
        else
        {
            try (OutputStream output = new FileOutputStream(configPath))
            {
                System.out.print("NEW TOKEN: ");
                token = sc.nextLine();
                System.out.println("NEW PREFIX: ");
                prefix = sc.nextLine();
                prop.setProperty("token", token);
                prop.setProperty("prefix", prefix);
                prop.store(output, null);
                System.out.println("Your setting have been saved for further sessions");
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args)
    {
        Bot b = new Bot();
        try
        {
            b.run();
        }
        catch(LoginException le)
        {
            le.printStackTrace();
        }
    }

    private void run() throws LoginException
    {
        Random r = new Random();
        Activity a = Activity.playing("Discord JDA");
        jda = new JDABuilder(AccountType.BOT).setToken(token).build();
        int rNum = r.nextInt(4);
        switch (rNum)
        {
            case 0:
                a = Activity.playing("Super Tux Kart");
                System.out.println("[TUX] Activity = Playing Super Tux Kart");
                break;
            case 1:
                a = Activity.playing("ZSH Terminal");
                System.out.println("[TUX] Activity = Playing ZSH Terminal");
                break;
            case 2:
                a = Activity.playing("Bash Terminal");
                System.out.println("[TUX] Activity = Playing Bash Terminal");
                break;
            case 3:
                a = Activity.watching("Arch Install Tutorial");
                System.out.println("[TUX] Activity = Watching Arch Install Tutorial");
                break;
            case 4:
                a = Activity.listening("Linus");
                System.out.println("[TUX] Activity = Listening to Linus");
                break;
        }
        jda.getPresence().setActivity(a);
        jda.getGuilds().forEach(guild -> System.out.println("[GUILD] " + guild.getName()));
        jda.addEventListener(new Events());
        jda.addEventListener(new CmdHandler());
    }

    public static Color randomColor()
    {
        Color c = Color.GRAY;
        Random r = new Random();
        int rNum = r.nextInt(8);
        switch(rNum)
        {
            case 0:
                c = Color.RED;
                break;
            case 1:
                c = Color.ORANGE;
                break;
            case 2:
                c = Color.YELLOW;
                break;
            case 3:
                c = Color.GREEN;
                break;
            case 4:
                c = Color.BLUE;
                break;
            case 5:
                c = Color.MAGENTA;
                break;
            case 6:
                c = Color.PINK;
                break;
            case 7:
                c = Color.BLACK;
                break;
            case 8:
                c = Color.CYAN;
                break;
        }
        return c;
    }
}
