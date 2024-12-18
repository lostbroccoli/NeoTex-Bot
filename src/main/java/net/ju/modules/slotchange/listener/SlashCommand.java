package net.ju.modules.slotchange.listener;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.ju.mongodb.MongoDB_Manager;

import java.awt.*;
import java.util.ArrayList;

public class SlashCommand extends ListenerAdapter {

    private MongoDB_Manager manager = new MongoDB_Manager();

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event){
        if(event.getGuild() == null) return;
        if(!event.getName().equals("slotchange")) return;

        switch (event.getSubcommandName()){
            case "setup":
                if(event.getOption("panelchannel") == null){
                    event.reply("Please provide me with a valid channel!").setEphemeral(true).queue();
                    return;
                }

                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle(manager.getQuery(event.getGuild().getId()).get("message_title", String.class));
                builder.setDescription(manager.getQuery(event.getGuild().getId()).get("message_description", String.class));
                builder.setColor(Color.decode(manager.getQuery(event.getGuild().getId()).get("message_color", String.class)));
                builder.setFooter(manager.getQuery(event.getGuild().getId()).get("message_footer", String.class));

                manager.updateValue(event.getGuild().getId(), "panelchannel", event.getOption("panelchannel").getAsChannel().getId());

                event.getGuild().getTextChannelById(event.getOption("panelchannel").getAsChannel().getId()).sendMessageEmbeds(builder.build()).addActionRow(
                        Button.primary("slotchange", "Limit Usercount")
                ).and(event.reply("Successfully sent!").setEphemeral(true)).queue();
                break;

            case "ignorecategory":
                if(event.getOption("ignored_category") == null){
                    event.reply("Please provide me with a valid category!").setEphemeral(true).queue();
                    return;
                }

                if(!event.getOption("ignored_category").getAsChannel().getType().equals(ChannelType.CATEGORY)){
                    event.reply("Please provide me with a valid category!").setEphemeral(true).queue();
                    return;
                }

                ArrayList<String> ignored_categories = manager.getQuery(event.getGuild().getId()).get("ignored_categories", ArrayList.class);
                if(ignored_categories == null){
                    ignored_categories = new ArrayList<>();
                }
                ignored_categories.add(event.getOption("ignored_category").getAsString());

                manager.updateValue(event.getGuild().getId(), "ignored_categories", ignored_categories);
                event.reply("Successful!").setEphemeral(true).queue();
                break;

            case "ignorechannel":
                if(event.getOption("ignored_channel") == null){
                    event.reply("Please provide me with a valid channel!").setEphemeral(true).queue();
                    return;
                }

                if(!event.getOption("ignored_channel").getAsChannel().getType().equals(ChannelType.VOICE)){
                    event.reply("Please provide me with a valid channel!").setEphemeral(true).queue();
                    return;
                }

                ArrayList<String> ignored_channels = manager.getQuery(event.getGuild().getId()).get("ignored_channels", ArrayList.class);
                if(ignored_channels == null){
                    ignored_channels = new ArrayList<>();
                }
                ignored_channels.add(event.getOption("ignored_channel").getAsString());

                manager.updateValue(event.getGuild().getId(), "ignored_channels", ignored_channels);
                event.reply("Successful!").setEphemeral(true).queue();
                break;
        }
    }
}
