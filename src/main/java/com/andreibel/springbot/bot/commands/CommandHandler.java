package com.andreibel.springbot.bot.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Collection;


/**
 * Dispatches incoming Telegram Update objects to the registered Command handlers.
 *
 * <p>Responsibilities:
 * - Hold a collection of available {@code Command} implementations (injected by Spring).
 * - For each incoming {@link org.telegram.telegrambots.meta.api.objects.Update}, ask each
 *   Command whether it can handle the update and, if so, invoke its handling logic.
 *
 * <p>Injection:
 * - The {@code commands} collection is provided by Spring. Lombok's {@code @RequiredArgsConstructor}
 *   generates a constructor that accepts this collection and sets the final field.
 *
 * <p>Dispatch semantics and important notes:
 * - Every command whose {@code canHandle(update)} returns {@code true} will have its {@code handle(update)}
 *   method invoked. This implementation allows multiple commands to process the same update.
 *   If you want only the first matching command to run, add a {@code break;} after invoking {@code handle(...)}.
 * - The order of elements in the injected {@code Collection<Command>} matters when multiple commands can match
 *   the same update—use an ordered collection if you rely on priority.
 * - This class does not perform null-checks on the incoming {@code update}; callers should avoid passing {@code null}.
 *   Consider adding defensive checks here if null updates are possible in your environment.
 *
 * <p>Thread-safety:
 * - The class itself is effectively stateless (it only stores a final reference to the commands collection).
 *   Thread-safety depends on the immutability and thread-safety of the provided collection and on the
 *   thread-safety of individual {@code Command} implementations.
 * @author andreibeloziorove
 */
@RequiredArgsConstructor
@Service
public class CommandHandler {

    /**
     * Collection of registered {@link Command} handlers.
     *
     * <p>Provided by Spring through constructor injection. The collection may be ordered (e.g. {@code List})
     * which affects dispatch behavior when multiple commands match the same update.
     */
    private final Collection<Command> commands;

    /**
     * Dispatch the given {@link org.telegram.telegrambots.meta.api.objects.Update} to all commands that claim they can handle it.
     *
     * <p>For each registered command, this method calls {@link Command#canHandle(Update)} and, if {@code true},
     * calls {@link Command#handle(Update)}. Implementations of {@link Command} should ensure {@code canHandle}
     * is quick and side-effect free, and that {@code handle} is robust to unexpected update content.
     *
     * @param update the incoming Telegram update to be processed; callers should not pass {@code null}.
     *               Implementations may assume the update contains whatever payload they expect (but defensive checks
     *               inside individual commands are recommended to avoid {@link NullPointerException}s).
     */
    public void handle(Update update) {
        for (Command command : commands) {
            if (command.canHandle(update)) {
                command.handle(update);
                return;
            }
        }
    }
}

