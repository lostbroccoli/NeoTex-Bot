package net.ju.listener;

import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.ju.Texx;

public class VoiceChannelEvent extends ListenerAdapter {


    @Override
    public void onGuildVoiceUpdate(GuildVoiceUpdateEvent event) {
        if(event.getChannelLeft() == null) return;
        if(event.getChannelLeft().getType() != ChannelType.VOICE) return;
        if(!event.getChannelLeft().asVoiceChannel().getMembers().isEmpty()) return;
        if(Texx.getCategories().contains(event.getChannelLeft().getParentCategoryId())) return;
        if(event.getChannelLeft().getId().equals("1286131901693956126")) return;

        if(event.getChannelLeft().getParentCategoryId().equals("1315756590418231397")){
            event.getChannelLeft().asVoiceChannel().getManager().setUserLimit(4).queue();
            return;
        }

        event.getChannelLeft().asVoiceChannel().getManager().setUserLimit(4).queue();
    }
}
