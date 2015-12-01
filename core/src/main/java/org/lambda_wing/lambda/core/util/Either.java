package org.lambda_wing.lambda.core.util;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by makotan on 2015/09/19.
 */
public interface Either<L,R> {

    class Left<L,R>  implements Either<L,R> {
        private L l;
        public Left(L l) {
            this.l = l;
        }

        @Override
        public boolean isLeft() {
            return true;
        }

        @Override
        public L getLeft() {
            return l;
        }

        @Override
        public boolean isRight() {
            return false;
        }

        @Override
        public R getRight() {
            return null;
        }
    }

    class Right<L,R>  implements Either<L,R> {
        private R r;
        public Right(R r) {
            this.r = r;
        }

        @Override
        public boolean isLeft() {
            return false;
        }

        @Override
        public L getLeft() {
            return null;
        }

        @Override
        public boolean isRight() {
            return true;
        }

        @Override
        public R getRight() {
            return r;
        }
    }

    static <L,R> Either<L, R> left(L l) {
        return new Left<L,R>(l);
    }
    static <L,R> Either<L, R> right(R r) {
        return new Right<L,R>(r);
    }

    boolean isLeft();
    default <T> void processLeft(Consumer<L> c) {
        if (isLeft()) {
            c.accept(this.getLeft());
        }
    }
    L getLeft();
    boolean isRight();
    default <T> void processRight(Consumer<R> c) {
        if (isRight()) {
            c.accept(this.getRight());
        }
    }
    R getRight();

    @SuppressWarnings("unchecked")
    default <L2,R2> Either<L2,R2> flatMap(Function<R, Either<L2, R2>> f) {
        if (isRight()) {
            return f.apply(getRight());
        } else {
            return (Either<L2, R2>) this;
        }
    }
}
