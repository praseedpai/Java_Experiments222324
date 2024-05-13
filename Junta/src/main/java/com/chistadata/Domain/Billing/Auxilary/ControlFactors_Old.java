package com.chistadata.Domain.Billing.Auxilary;


public class ControlFactors_Old {
    public JPlan [] plans;
    public JActivePlan [] activeplans;
    public float factor_a;
    public float factor_b;
    public ControlFactors_Old() {
        plans = new JPlan[3];
        plans[0] = new JPlan();
        plans[0].planname = "PLAN_1";
        plans[0].total_amount = 1000;
        plans[1] = new JPlan();
        plans[1].planname = "PLAN_2";
        plans[1].total_amount = 2000;
        plans[2] = new JPlan();
        plans[2].planname = "PLAN_3";
        plans[2].total_amount = 3000;
        activeplans = new JActivePlan[3];
        activeplans[0] = new JActivePlan();
        activeplans[0].planname = "PLAN_1";
        activeplans[0].count = 5;
        activeplans[1] = new JActivePlan();
        activeplans[1].planname = "PLAN_2";
        activeplans[1].count= 2;
        activeplans[2] = new JActivePlan();
        activeplans[2].planname = "PLAN_3";
        activeplans[2].count = 3;
        factor_a = 0.40f;
        factor_b = 0.60f;

    }

}
