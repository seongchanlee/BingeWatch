import helper.MessageHelper;
import model.Commands;
import model.Show;
import model.ShowManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static helper.TextHelper.toTitleCase;

public class MessageHandler extends ListenerAdapter {
    private static final Logger LOGGER = LogManager.getLogger(MessageHandler.class);
    private ShowManager showManager;

    public MessageHandler() {
        this.showManager = ShowManager.INSTANCE.getInstance();
    }

    /*
     * Checks if the message send by a user is a bot command on an message received event
     */
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        // Ignore received message if the author is a bot
        if (event.getAuthor().isBot()) {
            return;
        }

        String messageContent = event.getMessage().getContentRaw();

        if (MessageHelper.isBotMessage(messageContent)) {
            handleMessage(event);
        }
    }

    // TODO: refactor and clean up this method
    private void handleMessage(MessageReceivedEvent event) {
        String messageContent = event.getMessage().getContentRaw();
        String givenCommand = messageContent.split(Commands.BOT_SEPARATOR)[1];
        String showName = MessageHelper.extractShowName(messageContent);

        switch(givenCommand) {
            case Commands.ADD_SHOW:
                if (showName != null) {
                    showManager.addShow(showName);
                    LOGGER.info(toTitleCase(showName) + " added by " + event.getAuthor().getName());
                    event.getChannel()
                            .sendMessage("Show successfully added!")
                            .queue();
                } else {
                    event.getChannel()
                            .sendMessage("Please check the format for the command.")
                            .queue();
                }
                break;

            case Commands.REMOVE_SHOW:
                if (showName != null) {
                    showManager.removeShow(showName);
                    LOGGER.info(toTitleCase(showName) + " removed by " + event.getAuthor().getName());
                    event.getChannel()
                            .sendMessage("Show successfully removed!")
                            .queue();
                } else {
                    event.getChannel()
                            .sendMessage("Please check the format for the command.")
                            .queue();
                }
                break;

            case Commands.ADD_FILLERS:
                if (showName != null) {
                    List<String> args = MessageHelper.splitArguments(messageContent);
                    try {
                        int start = Integer.parseInt(args.get(args.size() - 2));
                        int end = Integer.parseInt(args.get(args.size() - 1));
                        showManager.addFillersForGivenShow(showName, start, end);
                        LOGGER.info("Filler episodes added to \"" + toTitleCase(showName)
                                + "\" by " + event.getAuthor().getName());
                        event.getChannel()
                                .sendMessage("Filler episodes successfully added!")
                                .queue();
                    } catch (NumberFormatException e) {
                        event.getChannel()
                                .sendMessage("Please check arguments for the number range.")
                                .queue();
                    }
                } else {
                    event.getChannel()
                            .sendMessage("Please check the argument for show title.")
                            .queue();
                }
                break;

            case Commands.UPDATE_EPISODE:
                if (showName != null) {
                    List<String> args = MessageHelper.splitArguments(messageContent);
                    try {
                        int currEpisode = Integer.parseInt(args.get(args.size() - 1));
                        showManager.updateGivenEpisode(showName, currEpisode);
                        LOGGER.info(toTitleCase(showName) + " updated by " + event.getAuthor().getName());
                        event.getChannel()
                                .sendMessage("Show successfully updated!")
                                .queue();
                    } catch (NumberFormatException e) {
                        event.getChannel()
                                .sendMessage("Please check arguments for the episode number.")
                                .queue();
                    }
                } else {
                    event.getChannel()
                            .sendMessage("Please check the format for the command.")
                            .queue();
                }
                break;

            case Commands.NEXT_EPISODE:
                if (showName != null) {
                    int nextEpiNum = showManager.getNextEpisodeForGivenShow(showName);

                    if (nextEpiNum == -1) {
                        event.getChannel()
                                .sendMessage("Invalid show name entered.")
                                .queue();
                    } else {
                        event.getChannel()
                                .sendMessage("Next episode for \"" + toTitleCase(showName)
                                        + "\" is: Episode " + nextEpiNum + ".")
                                .queue();
                    }
                } else {
                    event.getChannel()
                            .sendMessage("Please check the format for the command.")
                            .queue();
                }
                break;

            case Commands.GET_LIST:
                List<Show> showList = showManager.getShows();
                if (showList.size() > 0) {
                    String showListStr = MessageHelper.buildShowListString(showList);
                    event.getChannel()
                            .sendMessage(showListStr)
                            .queue();
                } else {
                    event.getChannel()
                            .sendMessage("There are no show added.")
                            .queue();
                }
                break;

            case Commands.HELP:
                event.getAuthor().openPrivateChannel()
                        .flatMap(channel ->
                                channel.sendMessage("Check https://github.com/seongchanlee/BingeWatch/blob/master/COMMANDS.md"))
                        .queue();
        }
    }
}
