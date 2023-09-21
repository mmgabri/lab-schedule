package com.mmgabri.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmgabri.adapter.ScheduleEvent;
import com.mmgabri.domain.ScheduleHello;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.services.scheduler.SchedulerClient;
import software.amazon.awssdk.services.scheduler.model.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
public class ScheduleEventImpl implements ScheduleEvent {
    private final SchedulerClient client;
    private final ObjectMapper oMapper;
    private static final String ARN = "arn:aws:lambda:us-east-1:146570171569:function:helloFunction";
    private static final String ROLE_ARN = "arn:aws:iam::146570171569:role/service-role/Amazon_EventBridge_Scheduler_LAMBDA_478ebd3d07";
    private static final String NAME_SCHEDULE = "schedule-test";
    private static final int INTERVAL = 1;

    private static final Logger logger = LoggerFactory.getLogger(ScheduleEventImpl.class);

    @Override
    public void execute(String id) {

        ScheduleHello event = ScheduleHello.builder()
                .id(id)
                .build();

        deleteSchedule();
        scheduleEvent(event);
    }

    private void deleteSchedule() {

        DeleteScheduleRequest deleteScheduleRequest = DeleteScheduleRequest.builder()
                .name(NAME_SCHEDULE)
                .build();

        try {
            client.deleteSchedule(deleteScheduleRequest);
            logger.info("Schedule deletado com sucesso !");
        } catch (ResourceNotFoundException e) {
            logger.info("Schedule inexistente");
        } catch (Exception e) {
            logger.error("Erro ao delelar Schedule", e);
        }
    }

    private void scheduleEvent(ScheduleHello event) {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nowPlusMinute = now.plusMinutes(INTERVAL);
        LocalDateTime nowPlusMinuteEndTime = now.plusMinutes(INTERVAL + 2);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

        String startTimeFormat = nowPlusMinute.format(dateTimeFormatter);
        Instant startTime = Instant.parse(startTimeFormat);

        String endTimeFormat = nowPlusMinuteEndTime.format(dateTimeFormatter);
        Instant endTime = Instant.parse(endTimeFormat);

        CreateScheduleRequest createScheduleRequest = CreateScheduleRequest.builder()
                .name(NAME_SCHEDULE)
                .endDate(endTime)
                .startDate(startTime)
                .scheduleExpression("rate(1 days)")
                .target(builderTarget(event))
                .flexibleTimeWindow(FlexibleTimeWindow.builder()
                        .mode(FlexibleTimeWindowMode.OFF)
                        .build())
                .build();

        try {
            client.createSchedule(createScheduleRequest);
            logger.info("Schedule criado com sucesso !");
        } catch (Exception e) {
            logger.error("Erro no createSchedule", e);
        }
    }

    @SneakyThrows
    private Target builderTarget(ScheduleHello eventSonda) {
        return Target.builder()
                .roleArn(ROLE_ARN)
                .arn(ARN)
                .input(oMapper.writeValueAsString(eventSonda))
                .build();
    }
}
