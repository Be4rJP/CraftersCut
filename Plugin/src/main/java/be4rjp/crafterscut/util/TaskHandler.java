package be4rjp.crafterscut.util;

import be4rjp.crafterscut.CraftersCut;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class TaskHandler{

    public static <U> CompletableFuture<U> supplySync(Supplier<U> supplier){
        CompletableFuture<U> completableFuture = new CompletableFuture<>();
        new BukkitTaskHandler<>(completableFuture, supplier, false).runTask(CraftersCut.getPlugin());

        return completableFuture;
    }

    public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier){
        CompletableFuture<U> completableFuture = new CompletableFuture<>();
        new BukkitTaskHandler<>(completableFuture, supplier, true).runTaskAsynchronously(CraftersCut.getPlugin());

        return completableFuture;
    }

    public static void runSync(Runnable runnable){
        Bukkit.getScheduler().runTask(CraftersCut.getPlugin(), runnable);
    }

    public static void runAsync(Runnable runnable){
        Bukkit.getScheduler().runTaskAsynchronously(CraftersCut.getPlugin(), runnable);
    }

    public static void runAsyncImmediately(Runnable runnable){
        Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        thread.start();
    }


    private static class BukkitTaskHandler<T> extends BukkitRunnable{
        private final CompletableFuture<T> completableFuture;
        private final Supplier<T> supplier;
        private final boolean isAsync;

        private BukkitTaskHandler(CompletableFuture<T> completableFuture, Supplier<T> supplier, boolean isAsync){
            this.completableFuture = completableFuture;
            this.supplier = supplier;
            this.isAsync = isAsync;
        }

        @Override
        public void run() {
            T result = supplier.get();

            Runnable runnable = () -> completableFuture.complete(result);
            if(isAsync){
                TaskHandler.runSync(runnable);
            }else{
                TaskHandler.runAsync(runnable);
            }
        }
    }

}