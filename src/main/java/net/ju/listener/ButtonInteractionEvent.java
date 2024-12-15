package net.ju.listener;

import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import net.ju.Texx;

public class ButtonInteractionEvent extends ListenerAdapter {

    @Override
    public void onButtonInteraction(net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent event){
        switch (event.getButton().getId()){
            case "slotchange":

                if(event.getMember().getVoiceState().getChannel() == null){
                    event.reply("Du befindest dich in keinem Voice-Channel!").setEphemeral(true).queue();
                    return;
                }
                if(Texx.getCategories().contains(event.getMember().getVoiceState().getChannel().getParentCategoryId())) {
                    event.reply("Du befindest dich in einem falschen Voice-Channel! Hier kann ich keine Slots anpassen!").setEphemeral(true).queue();
                    return;
                }

                TextInput input = TextInput.create("count", "Slotanzahl", TextInputStyle.SHORT).setMaxLength(2).setRequired(true).setPlaceholder("10").build();
                Modal modal = Modal.create("slotchange", "Slotanzahl ändern").addActionRow(input).build();

                event.replyModal(modal).queue();
        }
    }
}
