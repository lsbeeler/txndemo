package org.hazelcast.txndemo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable
{
    private final long id;
    private final String destinationAddress;
    private final List<OrderLine> lines;

    public Order(long id, String destinationAddress)
    {
        this.id = id;
        this.destinationAddress = destinationAddress;
        this.lines = new ArrayList<>( );
    }

    public long getId( )
    {
        return id;
    }

    public String getDestinationAddress( )
    {
        return destinationAddress;
    }

    public List<OrderLine> getLines( )
    {
        return lines;
    }

    public void addLine(OrderLine line)
    {
        this.lines.add(line);
    }
}
