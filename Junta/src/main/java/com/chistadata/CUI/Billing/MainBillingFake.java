package com.chistadata.CUI.Billing;
import com.chistadata.Domain.Billing.Auxilary.ControlFactors;
import com.chistadata.Domain.Billing.Auxilary.ChargeFactors;
import com.chistadata.Domain.Billing.Auxilary.JPlan;
import com.chistadata.Domain.Billing.Auxilary.JActivePlan;
import com.chistadata.Domain.Billing.Auxilary.CostFactors;
import com.chistadata.Domain.Billing.Auxilary.ComputeBill;
import java.math.BigDecimal;

public class MainBillingFake {
    public static void main(String [] args) {
        System.out.println("Hello....World....");
         CostFactors cost_f = new CostFactors();
          cost_f.compute_cost= new BigDecimal(2000);
          cost_f.container_eks = new BigDecimal(3000);
          ChargeFactors charge_f = new ChargeFactors();
          ControlFactors control_f = new ControlFactors();
          control_f.cost_apportionment_a = new BigDecimal(0.4);
          control_f.cost_apportionment_b = new BigDecimal( 0.6);
          control_f.plan_id = "PLAN_1";
          control_f.computationmodel="AGGREGATE_COST_PRO_RATA";
          ComputeBill.ComputeBill(cost_f,charge_f, control_f);

    }
}
