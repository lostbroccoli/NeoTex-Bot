package net.ju;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.ju.listener.ButtonInteractionEvent;
import net.ju.listener.ModalInteractionEvent;
import net.ju.listener.SlashCommandEvent;
import net.ju.listener.VoiceChannelEvent;

import java.util.Arrays;
import java.util.List;

public class Texx {

    private static List<String> categories = Arrays.asList("1315669139892994120", "1252154540111691777", "1313251570208739338", "1260933700540170281", "1315669139892994120");
    private static String bottoken = "youthoughtuwouldfindsomethinghereright_hehe";

    public static void main(String[] args) throws InterruptedException {
        JDA jda = JDABuilder.createDefault(bottoken, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES).
                addEventListeners(new ButtonInteractionEvent())
                .addEventListeners(new ModalInteractionEvent())
                .addEventListeners(new SlashCommandEvent())
                .addEventListeners(new VoiceChannelEvent()).build();

        jda.updateCommands().addCommands(
                Commands.slash("setup", "Used to setup the bot").setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR))
        ).queue();

        jda.awaitReady();
    }

    public static List<String> getCategories() {
        return categories;
    }
}
