package com.mmgabri.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmgabri.adapter.ScheduleEvent;
import com.mmgabri.domain.EventRequest;
import com.mmgabri.domain.EventResponse;
import com.mmgabri.services.ScheduleEventImpl;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.scheduler.SchedulerClient;

public class Handler implements RequestHandler<EventRequest, Object> {
    private final ScheduleEvent scheduleEvent;
    final SchedulerClient schedulerClient = SchedulerClient.builder().region(Region.US_EAST_1).build();
    private static final Logger logger = LoggerFactory.getLogger(Handler.class);

    public Handler() {
        scheduleEvent = new ScheduleEventImpl(schedulerClient, new ObjectMapper());
    }

    @SneakyThrows
    public EventResponse handleRequest(final EventRequest event, final Context context) {
        logger.info("Iniciando processamento...");

        scheduleEvent.execute(event.getId());

        return new EventResponse("Schedule efetuado!");

    }
}
