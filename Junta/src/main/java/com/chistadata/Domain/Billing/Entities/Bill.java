package com.chistadata.Domain.Billing.Entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "bill")
public class Bill {
    private Plan plan;
    private Usage usage;

    public Bill() {}
    public Bill(Plan plan, Usage usage) {
        super();
        this.plan = plan;
        this.usage = usage;
    }
    @XmlElement
    public Plan getPlan() {
        return plan;
    }
    public void setPlan(Plan plan) {
        this.plan = plan;
    }
    @XmlElement
    public Usage getUsage() {
        return usage;
    }
    public void setUsage(Usage usage) {
        this.usage = usage;
    }
}

