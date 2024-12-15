package net.ju.listener;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;

public class SlashCommandEvent extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event){
        if(event.getGuild() == null) return;
        if(!event.getGuild().getId().equals("1252144274263379978")) return;

        switch (event.getName()){
            case "setup":
                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("Slotanzahl begrenzen");
                builder.setDescription("Klicke auf den unteren Knopf, damit du in deinem aktuellen Voice-Channel die Slots begrenzen kannst!");
                builder.setColor(new Color(137, 207, 240));

                event.getGuild().getTextChannelById("1288908735250436137").sendMessageEmbeds(builder.build()).addActionRow(
                        Button.primary("slotchange", "Slotanzahl begrenzen")
                ).queue();
        }
    }
}
