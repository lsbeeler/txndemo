package org.hazelcast.txndemo;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.XmlClientConfigBuilder;
import com.hazelcast.core.HazelcastInstance;

import java.io.File;
import java.io.IOException;
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

        LOG.info("closing client connection");
        client.shutdown( );
    }
}
