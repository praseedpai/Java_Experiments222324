package com.chistadata.Domain.Billing.Auxilary;

import com.chistadata.Domain.Billing.Auxilary.ChargeFactors;
import com.chistadata.Domain.Billing.Auxilary.CostFactors;
import com.chistadata.Domain.Billing.Auxilary.ControlFactors;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "bill")
public class JBill {
    private ControlFactors controlFactors;
    private CostFactors costFactors;
    private ChargeFactors chargeFactors;

    public JBill() {}
    public JBill(CostFactors costfactors, ChargeFactors chargefactors,
                 ControlFactors controlfactors) {
        super();
        this.controlFactors = controlfactors;
        this.costFactors = costfactors;
        this.chargeFactors = chargefactors;
    }
    @XmlElement
    public CostFactors getCostFactors() {
        return costFactors;
    }
    public void setPlan(CostFactors costfactors) {
        this.costFactors = costfactors;
    }
    @XmlElement
    public ChargeFactors getChargeFactors() {
        return chargeFactors;
    }
    public void setChargeFactors(ChargeFactors chargefactors) {
        this.chargeFactors = chargefactors;
    }
    @XmlElement
    public ControlFactors getControlFactors() {
        return controlFactors;
    }
    public void setControlFactors(ControlFactors controlfactors) {
        this.controlFactors = controlfactors;
    }

}

