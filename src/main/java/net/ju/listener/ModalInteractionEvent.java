package net.ju.listener;

import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.text.NumberFormat;
import java.text.ParsePosition;

public class ModalInteractionEvent extends ListenerAdapter {

    @Override
    public void onModalInteraction(net.dv8tion.jda.api.events.interaction.ModalInteractionEvent event){
        switch (event.getModalId()){
            case "slotchange":
                String count = event.getValue("count").getAsString();
                if(count.equals("1")) return;

                if(!isNumeric(count)) {
                    event.reply("Sicher, dass es eine Nummer war, die du eingegeben hast?").setEphemeral(true).queue();
                    return;
                }

                event.getMember().getVoiceState().getChannel().asVoiceChannel().getManager().setUserLimit(Integer.parseInt(count)).and(event.reply("Erfolgreich!").setEphemeral(true)).queue();

        }
    }

    public static boolean isNumeric(String s) {
        ParsePosition pos = new ParsePosition(0);
        NumberFormat.getInstance().parse(s, pos);
        return s.length() == pos.getIndex();
    }
}
