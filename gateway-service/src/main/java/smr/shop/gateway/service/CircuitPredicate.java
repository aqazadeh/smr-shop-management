package smr.shop.gateway.service;

import java.util.function.Predicate;

public class CircuitPredicate implements Predicate<Throwable> {
    @Override
    public boolean test(Throwable throwable) {
            return false;

    }
}