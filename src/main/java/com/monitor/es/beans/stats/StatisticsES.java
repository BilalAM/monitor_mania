package com.monitor.es.beans.stats;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.joda.time.DateTime;

/**
 * @author saifasif
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class StatisticsES {

    private int id;
    private String userName;
    private long virtualSize;
    private String processName;
    private double cpuUsagePercentage;
    private double memUsuagePercentage;
    private double uptime;

    @JsonProperty("timestamp")
    private String timeOfEvent;

}
