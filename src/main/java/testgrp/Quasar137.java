package testgrp;

import co.paralleluniverse.fibers.Suspendable;

import co.paralleluniverse.fibers.Fiber;

/**
 * Increasing-Echo Quasar Example
 *
 * @author circlespainter
 */
public class Quasar137 {
    public interface HandlerWithException<E>
    {
        @Suspendable
        void handle(E event) throws Exception;
    }

    static class ServiceHandler
    {
        @Suspendable
        protected void executeWithEvent(HandlerWithException<Integer> handler) throws Exception {
            handler.handle(0);
        }
    }

    static public void main(String[] args) throws Exception {
        doAll();
    }

    static public void doAll() throws Exception {
        new Fiber(() -> {
            try {
                new ServiceHandler().executeWithEvent((Integer event) -> {
                    System.out.println("Fiber sleeping...");
                    Fiber.sleep(1000);
                    System.out.println("...Fiber slept");
                });
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start().join();
    }
}
