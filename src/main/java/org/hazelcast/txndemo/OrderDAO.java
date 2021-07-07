package org.hazelcast.txndemo;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

public class OrderDAO
{
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
        if (OrderDAO.hzClient == null)
            throw new IllegalStateException("OrderDAO.store( ) can only be " +
                    "invoked after OrderDAO.init( ).");

        IMap<Long, Order> orderMap = hzClient.getMap("orderMap");
        IMap<Long, OrderLine> orderLineMap = hzClient.getMap("orderLinesMap");

        orderMap.put(order.getId( ), order);

        for (OrderLine line : order.getLines( )) {
            orderLineMap.put(line.getId( ), line);
        }
    }
}
