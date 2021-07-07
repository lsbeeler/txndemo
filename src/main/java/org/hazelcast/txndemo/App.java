package org.hazelcast.txndemo;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.XmlClientConfigBuilder;
import com.hazelcast.core.HazelcastInstance;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

public class App
{
    private static final Logger LOG = Logger.getLogger(App.class.getName( ));

    public static void main(String[ ] args)
    {
        ClientConfig clientConfig = null;
        try {
            clientConfig = new XmlClientConfigBuilder(
                    new File("lib/hazelcast-client.xml")).build( );
        } catch (IOException e) {
            System.err.println("could not read client configuration XML: " +
                    e.getMessage( ));
            e.printStackTrace( );
            System.exit(127);
        }

        LOG.info("client config = " + clientConfig);

        LOG.info("opening client connection");
        HazelcastInstance client = HazelcastClient.newHazelcastClient(
                clientConfig);

        OrderDAO.init(client);

        Collection<Order> sampleOrders = generateSampleOrders( );
        for (Order order : sampleOrders) {
            OrderDAO.store(order);
        }

        LOG.info("closing client connection");
        client.shutdown( );
    }

    private static Collection<Order> generateSampleOrders( )
    {
        ArrayList<Order> result = new ArrayList<>( );

        result.add(generateSampleOrder1( ));
        result.add(generateSampleOrder2( ));

        return result;
    }

    private static Order generateSampleOrder1( )
    {
        Order result = new Order(1,
                "Bob's Burgers\n" +
                "1216 North Hayward Lane\n" +
                "Corte Madera, CA 94977\n");

        result.addLine(new OrderLine(1, result,
                "Calphalon Commercial Cookware 15pc. Set",
                899.99));

        result.addLine(new OrderLine(2, result,
                "Wonder Bread Buns Wholesale x1800",
                600.00));

        return result;
    }

    private static Order generateSampleOrder2( )
    {
        Order result = new Order(2,
                "Thai Me Up\n" +
                        "38940 Pompano Expressway\n" +
                        "Mill Valley, CA 94942\n");

        result.addLine(new OrderLine(3, result,
                "BFM Seating Nexus Outdoor Dining Tables x16",
                4_295.00));

        result.addLine(new OrderLine(4, result,
                "Mercer Genesis Fillet Knife Stainless (x4)",
                899.99));

        result.addLine(new OrderLine(5, result,
                "Proctor Silex 3.9 US Gallon Coffee Urn",
                109.95));

        return result;
    }
}
