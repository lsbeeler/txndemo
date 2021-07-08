package org.hazelcast.txndemo;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.transaction.TransactionContext;
import com.hazelcast.transaction.TransactionOptions;
import com.hazelcast.transaction.TransactionalMap;

import java.util.logging.Logger;

public class OrderDAO
{
    private static final Logger LOG = Logger.getLogger(
            OrderDAO.class.getName( ));

    private static HazelcastInstance hzClient = null;

    public static void init(HazelcastInstance hzClient)
    {
        if (OrderDAO.hzClient != null)
            throw new IllegalStateException("OrderDAO.init( ) can only be " +
                    "invoked once.");

        OrderDAO.hzClient = hzClient;
    }

    public static void store(Order order)
    {
        TransactionOptions txnOpts = new TransactionOptions( )
                .setTransactionType(TransactionOptions.TransactionType.TWO_PHASE);
        TransactionContext txnContext = hzClient.newTransactionContext(txnOpts);

        txnContext.beginTransaction( );

        TransactionalMap<Long, Order> orderMap = txnContext.getMap("orderMap");
        TransactionalMap<Long, OrderLine> orderLineMap = txnContext.getMap(
                "orderLinesMap");

        try {
            orderMap.put(order.getId( ), order);

            for (OrderLine line : order.getLines( )) {
                orderLineMap.put(line.getId( ), line);
            }

            txnContext.commitTransaction( );
        } catch (Throwable throwable) {
            txnContext.rollbackTransaction( );
        }
    }

}
