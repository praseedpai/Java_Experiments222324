package com.chistadata.Domain.Billing.Auxilary;
import javax.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;

public class ControlFactors {
    /* <ControlParameters>
        <plan-id>PLAN_1</plan-id>
        <cost-apportionment-a>0.6</cost-apportionment-a>
        <cost-apportionment-b>0.4</cost-apportionment-b>
        <computation-model>AGGREGATE_COST_PRO_RATA</computation-model>
    </ControlParameters> */
    public String plan_id;
    public BigDecimal cost_apportionment_a;
    public BigDecimal cost_apportionment_b;
    public String computationmodel;
    public BigDecimal total_amount;

    public ControlFactors(String plan_id,BigDecimal cost_apportionment_a,
                             BigDecimal cost_apportionment_b, String computationmodel) {
        super();
        this.plan_id = plan_id;
        this.cost_apportionment_a = cost_apportionment_a;
        this.cost_apportionment_b = cost_apportionment_b;
        this.computationmodel = computationmodel;

    }
    public ControlFactors() {}
    @XmlElement(name = "plan_id")
    public String getPlan_id() {
        return this.plan_id;
    }
    public void setPlan_id(String  plan_id) {
        this.plan_id = plan_id;
    }
    @XmlElement(name="cost_apportionment_a")
    public BigDecimal getCost_apportionment_a() {
        return this.cost_apportionment_a;
    }
    public void setCost_apportionment_a(BigDecimal cost_apportionment_a) {
        this.cost_apportionment_a=cost_apportionment_a;
    }
    @XmlElement(name="cost_apportionment_b")
    public BigDecimal getCost_apportionment_b() {
        return this.cost_apportionment_b;
    }
    public void setCost_apportionment_b(BigDecimal cost_apportionment_b) {
        this.cost_apportionment_b=cost_apportionment_b;
    }
    @XmlElement(name = "computationmodel")
    public String getComputationmodel() {
        return this.computationmodel;
    }
    public void setComputationmodel(String  computationmodel) {
        this.computationmodel = computationmodel;
    }
    @XmlElement(name="total_amount")
    public BigDecimal getTotal_amount() {
        return this.total_amount;
    }
    public void setTotal_amount(BigDecimal total_amount) {
        this.total_amount=total_amount;
    }

}
