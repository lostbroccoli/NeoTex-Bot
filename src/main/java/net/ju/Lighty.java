package net.ju;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.ju.modules.slotchange.listener.ButtonInteraction;
import net.ju.modules.slotchange.listener.ModalInteraction;
import net.ju.modules.slotchange.listener.SlashCommand;
import net.ju.modules.slotchange.listener.VoiceChannel;
import net.ju.mongodb.MongoDB_Manager;

public class Lighty {

    private static Dotenv dotenv = Dotenv.configure().load();
    private static JDA jda;

    public static void main(String[] args) throws InterruptedException {
        jda = JDABuilder.createDefault(dotenv.get("bottoken"), GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES).
                addEventListeners(new ButtonInteraction())
                .addEventListeners(new ModalInteraction())
                .addEventListeners(new SlashCommand())
                .addEventListeners(new VoiceChannel()).build();

        jda.updateCommands().addCommands(
                Commands.slash("slotchange", "Used to setup the bot")
                        .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR))
                        .addSubcommands(new SubcommandData("setup", "Setup the message and sends it to channel")
                                .addOption(OptionType.CHANNEL, "panelchannel", "Provide me with the channel where i am supposed to send the clickable message in!"))
                        .addSubcommands(new SubcommandData("ignorecategory", "Add a category to ignore for the slotchange")
                                .addOption(OptionType.CHANNEL, "ignored_category", "The category i should ignore"))
                        .addSubcommands(new SubcommandData("ignorechannel", "Add a channel to ignore for the slotchange")
                                .addOption(OptionType.CHANNEL, "ignored_channel", "The channel i should ignore"))
        ).queue();

        jda.awaitReady();

        new MongoDB_Manager().init();
    }

    public static Dotenv getDotenv() {
        return dotenv;
    }

    public static JDA getJDA() {
        return jda;
    }
}
