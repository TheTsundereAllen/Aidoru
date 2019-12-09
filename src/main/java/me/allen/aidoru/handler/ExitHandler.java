package me.allen.aidoru.handler;

import me.allen.aidoru.wrapper.ExitPredicateWrapper;
import org.bukkit.entity.Player;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class ExitHandler {

    private final Consumer<Player> onExit;

    private final Predicate<ExitPredicateWrapper> canExit;

    public ExitHandler(Consumer<Player> onExit, Predicate<ExitPredicateWrapper> canExit) {
        this.onExit = onExit;
        this.canExit = canExit;
    }

    public Consumer<Player> getOnExit() {
        return this.onExit;
    }

    public Predicate<ExitPredicateWrapper> getCanExit() {
        return this.canExit;
    }

    public static ExitHandlerBuilder builder() {
        return new ExitHandlerBuilder();
    }

    static class ExitHandlerBuilder {
        private Consumer<Player> onExit;

        private Predicate<ExitPredicateWrapper> canExit;

        public ExitHandlerBuilder onExit(Consumer<Player> onExit) {
            this.onExit = onExit;
            return this;
        }

        public ExitHandlerBuilder canExit(Predicate<ExitPredicateWrapper> canExit) {
            this.canExit = canExit;
            return this;
        }

        public ExitHandler build() {
            return new ExitHandler(this.onExit, this.canExit);
        }
    }
}
