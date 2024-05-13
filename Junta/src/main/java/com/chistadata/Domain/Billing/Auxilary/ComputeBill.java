package com.chistadata.Domain.Billing.Auxilary;

import java.math.BigDecimal;

public  class ComputeBill {
    public static void ComputeBill(CostFactors  cf1, ChargeFactors cf2, ControlFactors ctr) {
        System.out.println("Compute Bill");
        /*BigDecimal Total = new BigDecimal(0);
        //TOTAL REVENUE = 5x 1000 + 2 x 2000 + 3 x 3000 = 18000
        //Total VAS Charge -  600 x 5 + 1200x2 + 1800x3 = 10800
        double total_revenue =  (ctr.activeplans[0].count)*(ctr.plans[0].total_amount) +
                (ctr.activeplans[1].count)*(ctr.plans[1].total_amount) +
                (ctr.activeplans[2].count)*(ctr.plans[2].total_amount) ;
        double total_vas = (ctr.activeplans[0].count)*(ctr.plans[0].total_amount)* ctr.factor_b +
                (ctr.activeplans[1].count)*(ctr.plans[1].total_amount)* ctr.factor_b +
                (ctr.activeplans[2].count)*(ctr.plans[2].total_amount)* ctr.factor_b ;
        double cost_factor =  (ctr.activeplans[0].count)*(ctr.plans[0].total_amount)* ctr.factor_a +
                (ctr.activeplans[1].count)*(ctr.plans[1].total_amount)* ctr.factor_a +
                (ctr.activeplans[2].count)*(ctr.plans[2].total_amount)* ctr.factor_a;
        System.out.println(total_revenue);
        System.out.println(total_vas);
        System.out.println(cost_factor);
        BigDecimal dec = cf1.A.add( cf1.B);
        double aratio = cf1.A.doubleValue()/dec.doubleValue();
        double bratio = cf1.B.doubleValue()/dec.doubleValue();
        System.out.println(aratio);
        System.out.println(bratio);
        ChargeFactors cf3 = new ChargeFactors();
        cf3.C_A = BigDecimal.valueOf(ctr.factor_a*ctr.plans[0].total_amount).multiply(BigDecimal.valueOf(aratio));
        cf3.C_B = BigDecimal.valueOf(ctr.factor_a*ctr.plans[0].total_amount).multiply(BigDecimal.valueOf(bratio));
        cf3.C_C = BigDecimal.valueOf(ctr.factor_b*ctr.plans[0].total_amount).multiply(BigDecimal.valueOf(0.5));
        cf3.C_D = BigDecimal.valueOf(ctr.factor_b*ctr.plans[0].total_amount).multiply(BigDecimal.valueOf(0.5));
        System.out.println("==========================================");
        System.out.println("====Bill for Plan #1==================");
        System.out.println(cf3.C_A.toString());
        System.out.println(cf3.C_B.toString());
        System.out.println(cf3.C_C.toString());
        System.out.println(cf3.C_D.toString());
        ChargeFactors cf4 = new ChargeFactors();
        cf4.C_A = BigDecimal.valueOf(ctr.factor_a*ctr.plans[1].total_amount).multiply(BigDecimal.valueOf(aratio));
        cf4.C_B = BigDecimal.valueOf(ctr.factor_a*ctr.plans[1].total_amount).multiply(BigDecimal.valueOf(bratio));
        cf4.C_C = BigDecimal.valueOf(ctr.factor_b*ctr.plans[1].total_amount).multiply(BigDecimal.valueOf(0.5));
        cf4.C_D = BigDecimal.valueOf(ctr.factor_b*ctr.plans[1].total_amount).multiply(BigDecimal.valueOf(0.5));
        System.out.println("==========================================");
        System.out.println("====Bill for Plan #2==================");
        System.out.println(cf4.C_A.toString());
        System.out.println(cf4.C_B.toString());
        System.out.println(cf4.C_C.toString());
        System.out.println(cf4.C_D.toString());
        ChargeFactors cf5 = new ChargeFactors();
        cf5.C_A = BigDecimal.valueOf(ctr.factor_a*ctr.plans[2].total_amount).multiply(BigDecimal.valueOf(aratio));
        cf5.C_B = BigDecimal.valueOf(ctr.factor_a*ctr.plans[2].total_amount).multiply(BigDecimal.valueOf(bratio));
        cf5.C_C = BigDecimal.valueOf(ctr.factor_b*ctr.plans[2].total_amount).multiply(BigDecimal.valueOf(0.5));
        cf5.C_D = BigDecimal.valueOf(ctr.factor_b*ctr.plans[2].total_amount).multiply(BigDecimal.valueOf(0.5));
        System.out.println("==========================================");
        System.out.println("====Bill for Plan #3==================");
        System.out.println(cf5.C_A.toString());
        System.out.println(cf5.C_B.toString());
        System.out.println(cf5.C_C.toString());
        System.out.println(cf5.C_D.toString());
*/

    }

    public static void ComputeAggregateBill(CostFactors costFactors,ChargeFactors chargeFactors,ControlFactors conrolParameters)
    {
        BigDecimal total_amount = conrolParameters.total_amount;
        BigDecimal aggregate = costFactors.compute_cost.add(costFactors.container_eks);
        BigDecimal a_ratio = costFactors.compute_cost.divide(aggregate);
        BigDecimal b_ratio = costFactors.container_eks.divide(aggregate);
        BigDecimal total_cost_provider = total_amount.multiply(conrolParameters.cost_apportionment_a);
        BigDecimal total_cost_vas = total_amount.multiply(conrolParameters.cost_apportionment_b);


        ChargeFactors cf1 = new ChargeFactors();
        //cf1.compute_cost = BigDecimal.valueOf(ctr.factor_a*ctr.plans[0].total_amount).multiply(BigDecimal.valueOf(aratio));
        //cf3.C_B = BigDecimal.valueOf(ctr.factor_a*ctr.plans[0].total_amount).multiply(BigDecimal.valueOf(bratio));
        //cf3.C_C = BigDecimal.valueOf(ctr.factor_b*ctr.plans[0].total_amount).multiply(BigDecimal.valueOf(0.5));
        //cf3.C_D = BigDecimal.valueOf(ctr.factor_b*ctr.plans[0].total_amount).multiply(BigDecimal.valueOf(0.5));




    }


}
