package net.ju.modules.slotchange.listener;

import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.ju.mongodb.MongoDB_Manager;

import java.util.ArrayList;

public class VoiceChannel extends ListenerAdapter {

    private MongoDB_Manager manager = new MongoDB_Manager();

    @Override
    public void onGuildVoiceUpdate(GuildVoiceUpdateEvent event) {
        if(event.getChannelLeft() == null) return;
        if(event.getChannelLeft().getType() != ChannelType.VOICE) return;
        if(!event.getChannelLeft().asVoiceChannel().getMembers().isEmpty()) return;
        if(manager.getQuery(event.getGuild().getId()).get("ignored_categories", ArrayList.class).contains(event.getChannelLeft().getParentCategoryId())) return;
        if(manager.getQuery(event.getGuild().getId()).get("ignored_channels", ArrayList.class).contains(event.getChannelLeft().getId())) return;

        if(event.getChannelLeft().getParentCategoryId().equals("1315756590418231397")){
            event.getChannelLeft().asVoiceChannel().getManager().setUserLimit(4).queue();
            return;
        }

        event.getChannelLeft().asVoiceChannel().getManager().setUserLimit(4).queue();
    }
}
