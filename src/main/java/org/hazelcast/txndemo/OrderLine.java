package org.hazelcast.txndemo;

import com.hazelcast.partition.PartitionAware;

import java.io.Serializable;

public class OrderLine implements PartitionAware<Long>, Serializable
{
    private final long id;
    private final Order parentOrder;
    private final String description;
    private final double amount;

    public OrderLine(long id, Order parentOrder, String description,
            double amount)
    {
        this.id = id;
        this.parentOrder = parentOrder;
        this.description = description;
        this.amount = amount;
    }

    public long getId( )
    {
        return id;
    }

    public String getDescription( )
    {
        return description;
    }

    public double getAmount( )
    {
        return amount;
    }

    @Override
    public Long getPartitionKey( )
    {
        return parentOrder.getId( );
    }
}
