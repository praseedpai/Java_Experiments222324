package com.chistadata.Domain.Billing.Entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "usage")
public class Usage {
    private String clusterUsageTimeInHour;
    private String networkUsageTimeInHour;
    private String storageUsageTimeInHour;

    public Usage() {}
    public Usage(String clusterUsageTimeInHour, String networkUsageTimeInHour, String storageUsageTimeInHour) {
        super();
        this.clusterUsageTimeInHour = clusterUsageTimeInHour;
        this.networkUsageTimeInHour = networkUsageTimeInHour;
        this.storageUsageTimeInHour = storageUsageTimeInHour;
    }
    @XmlElement(name = "cluster_usage_time_in_hour")
    public String getClusterUsageTimeInHour() {
        return clusterUsageTimeInHour;
    }
    public void setClusterUsageTimeInHour(String clusterUsageTimeInHour) {
        this.clusterUsageTimeInHour = clusterUsageTimeInHour;
    }
    @XmlElement(name = "network_usage_time_in_hour")
    public String getNetworkUsageTimeInHour() {
        return networkUsageTimeInHour;
    }
    public void setNetworkUsageTimeInHour(String networkUsageTimeInHour) {
        this.networkUsageTimeInHour = networkUsageTimeInHour;
    }
    @XmlElement(name = "storage_usage_in_gb")
    public String getStorageUsageTimeInHour() {
        return storageUsageTimeInHour;
    }
    public void setStorageUsageTimeInHour(String storageUsageTimeInHour) {
        this.storageUsageTimeInHour = storageUsageTimeInHour;
    }
}

