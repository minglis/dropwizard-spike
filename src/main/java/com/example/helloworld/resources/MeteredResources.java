package com.example.helloworld.resources;

import com.example.helloworld.core.Saying;
import com.google.common.base.Optional;
import com.yammer.metrics.annotation.Metered;
import com.yammer.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@Path("/metered-world")
@Produces(MediaType.APPLICATION_JSON)
public class MeteredResources {
    private final String template;
    private final String defaultName;
    private final AtomicLong counter = new AtomicLong();

    public MeteredResources(String template, String defaultName) {
        this.template = template;
        this.defaultName = defaultName;
    }

    @GET
    @Metered(name="test", eventType = "test", rateUnit = TimeUnit.NANOSECONDS)
    public Saying sayGoodbye(@QueryParam("name") String name, @QueryParam("times") Integer amount) {
        return new Saying(counter.getAndIncrement(), String.format(template, amount.toString()));
    }
}
