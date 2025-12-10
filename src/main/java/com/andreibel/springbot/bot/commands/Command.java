package com.andreibel.springbot.bot.commands;

import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Represents a single bot command handler.
 *
 * <p>Implementations of this interface encapsulate the logic for a single command
 * or a small related set of interactions (for example, handling a specific text
 * command, inline keyboard callback, or conversation step). A {@code Command}
 * implementation is responsible for:
 * <ul>
 *   <li>Indicating whether it can handle a given {@link Update} via {@link #canHandle(Update)}.</li>
 *   <li>Executing the handling logic via {@link #handle(Update)} (sending messages, editing messages, etc.).</li>
 *   <li>Exposing its canonical command name via {@link #getCommand()} (used by dispatchers/registries).</li>
 * </ul>
 *
 * <p>Design notes and conventions (used across this project):
 * <ul>
 *   <li>{@code canHandle} should be fast and side-effect free; it is used by the dispatcher
 *       to select which command should process an incoming {@link Update}.</li>
 *   <li>{@code handle} may perform I/O (send messages, call services) and therefore can be blocking;
 *       implementations should catch and log unexpected exceptions—don't let exceptions bubble up to the dispatcher.</li>
 *   <li>When inspecting {@link Update}, prefer the {@code Update#hasMessage()} and {@code Update#hasCallbackQuery()}
 *       helpers to avoid {@code NullPointerException}.</li>
 * </ul>
 *
 * <p>Example:
 * <blockquote><pre>
 * public class StartCommand implements Command {
 *     public boolean canHandle(Update u) {
 *         return u.hasMessage() && u.getMessage().hasText() && "/start".equals(u.getMessage().getText());
 *     }
 *     public void handle(Update u) { ... }
 *     public String getCommand() { return "start"; }
 * }
 * </pre></blockquote>
 *
 * @see CommandHandler
 * @author andreibeloziorove
 */
public interface Command {
    /**
     * Whether this command can handle the provided {@link Update}.
     *
     * <p>The dispatcher calls this method for each registered command to find
     * the best handler for an incoming update. Implementations should check
     * only the parts of {@code Update} they need (e.g. message text, callback data).
     *
     * @param update the incoming Telegram update; callers may pass a non-null value.
     *               Implementations must tolerate updates that don't contain the expected
     *               payload (for example, an update with no message or no callback).
     * @return {@code true} if this command should handle the update; {@code false} otherwise
     */
    boolean canHandle(Update update);

    /**
     * Handle the provided {@link Update}.
     *
     * <p>This method contains the command logic and typically sends or edits messages
     * via the Telegram Bot API client. Implementations should:
     * <ul>
     *   <li>Validate the {@code update} contents (e.g. check {@code hasMessage()} or {@code hasCallbackQuery()}).</li>
     *   <li>Catch and log errors instead of letting exceptions propagate to the dispatcher.</li>
     *   <li>Keep side effects (database updates, external calls) idempotent or clearly documented.</li>
     * </ul>
     *
     * @param update the incoming update to process; never null when called by a well-behaved dispatcher,
     *               but implementations SHOULD still defensively check contents.
     */
    void handle(Update update);

    /**
     * Returns the canonical name of this command.
     *
     * <p>The value is typically used for registration, logging or as the label
     * that maps to the command (for example: "start", "help", "experts").
     *
     * @return the command name (non-null, non-empty) identifying this command
     */
    String getCommand();


    /**
     * Helper to extract a chat id when the {@link Update} contains a {@code Message}.
     *
     * <p>Convenience wrapper around {@code update.getMessage().getChatId()}. Before calling
     * this helper, prefer to check {@code update.hasMessage()} to avoid a {@code NullPointerException}.
     *
     * @param update an update that is expected to contain a {@code Message}
     * @return the chat id of the message's chat
     * @throws NullPointerException if {@code update} or {@code update.getMessage()} is null
     */
    static Long getChatId(Update update) {
        return update.getMessage().getChatId();
    }

    /**
     * Helper to extract a chat id when the {@link Update} contains a {@code CallbackQuery}.
     *
     * <p>Used for inline keyboard callback queries. Prefer checking {@code update.hasCallbackQuery()}
     * before calling this helper to avoid {@code NullPointerException}.
     *
     * @param update an update that is expected to contain a {@code CallbackQuery}
     * @return the chat id associated with the callback's message
     * @throws NullPointerException if {@code update}, {@code update.getCallbackQuery()} or
     *                              {@code update.getCallbackQuery().getMessage()} is null
     */
    static Long getChatIdCallBack(Update update) {
        return update.getCallbackQuery().getMessage().getChatId();
    }
}