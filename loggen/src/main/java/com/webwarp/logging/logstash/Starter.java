package com.webwarp.logging.logstash;
//package com.example.logstash;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Starter {
    private final static Logger log = LoggerFactory.getLogger( Starter.class );
 
    public static void main( String[] args ) {
        final ExecutorService executor = Executors.newCachedThreadPool();
        final Collection< Future< Void > > futures = new ArrayList< Future< Void > >();
        final Random random = new Random();
  
        for( int i = 0; i < 10; ++i ) {
            futures.add( 
                executor.submit(
                    new Callable< Void >() {
                        @Override
                        public Void call() throws Exception {
                            int sleep = Math.abs( random.nextInt( 10000 ) % 10000 );
                            log.warn( "Sleeping for " + sleep + "ms" );
                            Thread.sleep( sleep );
                            return null;
                        }
                    }
                )
            );
        }
  
        for( final Future< Void > future: futures ) {
            try {
                Void result = future.get( 3, TimeUnit.SECONDS );
                log.info( "Result " + result );
            } catch (InterruptedException | ExecutionException | TimeoutException ex ) {
                log.error( ex.getMessage(), ex );
            }   
        }
    }
}