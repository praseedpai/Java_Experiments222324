package com.chistadata.Domain.Billing.Entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "plan")
public class Plan {
    private int customerId;
    private String servicePeriod;
    private String planName;
    private String tierName;
    private float planPrice;
    private String clusterSize;
    private int noOfReplica;
    private int noOfShard;
    private float costUnitPerNode;
    private float resourceRate;
    private float storageRate;
    private float networkRate;
    public Plan() {}
    public Plan(int customerId, String servicePeriod, String planName, String tierName,
                float planPrice, String clusterSize, int noOfReplica, int noOfShard,
                float costUnitPerNode, float resourceRate, float storageRate, float networkRate) {
        super();
        this.customerId = customerId;
        this.servicePeriod = servicePeriod;
        this.planName = planName;
        this.planPrice = planPrice;
        this.clusterSize = clusterSize;
        this.tierName = tierName;
        this.noOfReplica = noOfReplica;
        this.noOfShard = noOfShard;
        this.costUnitPerNode = costUnitPerNode;
        this.resourceRate = resourceRate;
        this.storageRate = storageRate;
        this.networkRate = networkRate;
    }

    @XmlElement(name = "customer-id")
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    @XmlElement(name = "service-period")
    public String getServicePeriod() {
        return servicePeriod;
    }

    public void setServicePeriod(String servicePeriod) {
        this.servicePeriod = servicePeriod;
    }
    @XmlElement(name = "plan-name")
    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }
    @XmlElement(name = "tier-name")
    public String getTierName() {
        return tierName;
    }

    public void setTierName(String tierName) {
        this.tierName = tierName;
    }
    @XmlElement(name = "plan-price")
    public float getPlanPrice() {
        return planPrice;
    }

    public void setPlanPrice(float planPrice) {
        this.planPrice = planPrice;
    }
    @XmlElement(name = "cluster-size")
    public String getClusterSize() {
        return clusterSize;
    }

    public void setClusterSize(String clusterSize) {
        this.clusterSize = clusterSize;
    }
    @XmlElement(name = "no-of-replica")
    public int getNoOfReplica() {
        return noOfReplica;
    }

    public void setNoOfReplica(int noOfReplica) {
        this.noOfReplica = noOfReplica;
    }
    @XmlElement(name = "no-of-shard")
    public int getNoOfShard() {
        return noOfShard;
    }

    public void setNoOfShard(int noOfShard) {
        this.noOfShard = noOfShard;
    }
    @XmlElement(name = "cost-unit-per-node")
    public float getCostUnitPerNode() {
        return costUnitPerNode;
    }

    public void setCostUnitPerNode(float costUnitPerNode) {
        this.costUnitPerNode = costUnitPerNode;
    }
    @XmlElement(name = "resource-rate")
    public float getResourceRate() {
        return resourceRate;
    }

    public void setResourceRate(float resourceRate) {
        this.resourceRate = resourceRate;
    }
    @XmlElement(name = "storage-rate")
    public float getStorageRate() {
        return storageRate;
    }

    public void setStorageRate(float storageRate) {
        this.storageRate = storageRate;
    }
    @XmlElement(name = "network-rate")
    public float getNetworkRate() {
        return networkRate;
    }

    public void setNetworkRate(float networkRate) {
        this.networkRate = networkRate;
    }
}

