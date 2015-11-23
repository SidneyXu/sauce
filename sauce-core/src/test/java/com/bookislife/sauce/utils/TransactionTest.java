package com.bookislife.sauce.utils;

import junit.framework.TestCase;

import java.util.Random;

/**
 * Created by SidneyXu on 2015/11/22.
 */
public class TransactionTest extends TestCase {

    public void testCore() throws Exception {
        final StringBuilder builder = new StringBuilder();
        builder.append("foo");
        Transaction.with(new Transaction.Handler() {
            public void handle(Transaction transaction) throws Exception {
                int i = 10;
                builder.append(i);
                int j = 1 / 0;
                builder.append(j);
            }

            @Override
            public Object recover(Transaction.PanicHandler panicHandler, Exception e) {
                if (e instanceof ArithmeticException) {
                    builder.append("foo");
                } else {
                    builder.append("bar");
                }
                return null;
            }
        });
        System.out.println(builder.toString());
    }

    public void testChain() {
        final StringBuilder builder = new StringBuilder();
        final Random random = new Random();
        final Exception[] exceptions = {new EA(), new EB(), new EC(), null, null, null};
        Transaction.with(new Transaction.Handler() {
            @Override
            public void handle(Transaction transaction) throws Exception {
                String result = transaction.panic(new Transaction.PanicHandler<String>() {
                    @Override
                    public String handle() throws Exception {
                        Exception e = exceptions[random.nextInt(6)];
                        if (null != e) throw e;
                        return "fooA";
                    }
                });
                result = result + transaction.panic(new Transaction.PanicHandler<String>() {
                    @Override
                    public String handle() throws Exception {
                        Exception e = exceptions[random.nextInt(6)];
                        if (null != e) throw e;
                        return "fooB";
                    }
                });
                result = result + transaction.panic(new Transaction.PanicHandler<String>() {
                    @Override
                    public String handle() throws Exception {
                        Exception e = exceptions[random.nextInt(6)];
                        if (null != e) throw e;
                        return "fooC";
                    }
                });
                System.out.println("result is " + result);
            }

            @Override
            public Object recover(Transaction.PanicHandler<?> panicHandler, Exception e) {
                try {
                    return panicHandler.handle();
                } catch (Exception e1) {
                    return "recover";
                }
//                if (e instanceof EA) {
//                    return "(EA)";
//                } else if (e instanceof EB) {
//                    return "(EB)";
//                } else {
//                    return "(EC)";
//                }
            }

        });
        System.out.println(builder.toString());
    }

    class EA extends Exception {
    }

    class EB extends Exception {
    }

    class EC extends Exception {
    }
}
