package com.chistadata.Domain.Billing.Auxilary;

import com.chistadata.Domain.Billing.Entities.Plan;
import com.chistadata.Domain.Billing.Entities.Usage;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.util.function.BiFunction;

@XmlRootElement(name = "CostFactors")
public class CostFactors {
    /* <CostingFactors>
        <compute>899</compute>
        <containers-eks>255</containers-eks>
        <containers-ecs>176</containers-ecs>
        <containers-ecr>146</containers-ecr>
        <amazon-elastic-block-storage-ebs>76</amazon-elastic-block-storage-ebs>
        <amazon-simple-storage-service-s3>80</amazon-simple-storage-service-s3>
    </CostingFactors> */
    //------------- Actual Factors
    public BigDecimal compute_cost;
    public BigDecimal container_eks;
    public BigDecimal container_ecs;
    public BigDecimal container_ecr;
    public BigDecimal amazon_s3_c3;
    public BigDecimal amazon_ebs_c3;

    public CostFactors() {}
    public CostFactors(BigDecimal compute_cost , BigDecimal container_eks,
                       BigDecimal container_ecs, BigDecimal container_ecr ,  BigDecimal amazon_s3_c3 ,
                       BigDecimal  amazon_ebs_c3) {
        super();
        this.compute_cost = compute_cost;
        this.container_eks = container_eks;
        this.container_ecs = container_ecs;
        this.container_ecr = container_ecr;
        this.amazon_s3_c3 = amazon_s3_c3;
        this.amazon_ebs_c3 = amazon_ebs_c3;


    }
    @XmlElement(name = "compute_cost")
    public BigDecimal getCompute_cost() {
        return this.compute_cost;
    }
    public void setCompute_cost(BigDecimal compute_cost) {
         this.compute_cost=compute_cost;
    }
    @XmlElement(name="container_eks")
    public BigDecimal getContainer_eks() {
        return this.compute_cost;
    }
    public void setContainer_eks(BigDecimal container_eks) {
        this.compute_cost=container_eks;
    }

    @XmlElement(name="container_ecs")
    public BigDecimal getContainer_ecs() {
        return this.container_ecs;
    }
    public void setContainer_ecs(BigDecimal container_ecs) {
        this.container_ecs=container_ecs;
    }
    @XmlElement(name="container_ecr")
    public BigDecimal getContainer_ecr() {
        return this.container_ecr;
    }
    public void setContainer_ecr(BigDecimal container_ecr) {
        this.container_ecr=container_ecr;
    }
    @XmlElement(name="amazon_s3_c3")
    public BigDecimal getAmazon_s3_c3() {
        return this.container_ecr;
    }
    public void setAmazon_s3_c3(BigDecimal amazon_s3_c3) {
        this.amazon_s3_c3=amazon_s3_c3;
    }
    @XmlElement(name="amazon_ebs_c3")
    public BigDecimal getAmazon_ebs_c3() {
        return this.amazon_ebs_c3;
    }
    public void setAmazon_ebs_c3(BigDecimal amazon_ebs_c3) {
        this.container_ecr=container_ecr;
    }


}
