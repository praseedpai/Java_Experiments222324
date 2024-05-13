package com.chistadata.Domain.Billing.Auxilary;

import com.chistadata.Domain.Billing.Entities.Plan;
import com.chistadata.Domain.Billing.Entities.Usage;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;


@XmlRootElement(name = "ChargeFactors")
public class ChargeFactors {
    /* <CostingFactors>
        <compute>899</compute>
        <containers-eks>255</containers-eks>
        <containers-ecs>176</containers-ecs>
        <containers-ecr>146</containers-ecr>
        <amazon-elastic-block-storage-ebs>76</amazon-elastic-block-storage-ebs>
        <amazon-simple-storage-service-s3>80</amazon-simple-storage-service-s3>
    </CostingFactors> */
    /* <managed-shared-cluster>23.88</managed-shared-cluster>
        <enterprise-portal>23.88</enterprise-portal>
        <query-editor>23.88</query-editor>
        <import-export-data>23.88</import-export-data>
        <ticket-support>23.88</ticket-support>*/
    //------------- Actual Factors
    public BigDecimal compute_cost;
    public BigDecimal container_eks;
    public BigDecimal container_ecs;
    public BigDecimal container_ecr;
    public BigDecimal amazon_s3_c3;
    public BigDecimal amazon_ebs_c3;
    public BigDecimal managed_shared_cluster;
    public BigDecimal enterprise_portal;
    public BigDecimal query_editor;
    public BigDecimal import_export_data;
    public BigDecimal ticket_support;
    public ChargeFactors() {}
    public ChargeFactors(BigDecimal compute_cost , BigDecimal container_eks,
                       BigDecimal container_ecs, BigDecimal container_ecr ,  BigDecimal amazon_s3_c3 ,
                       BigDecimal  amazon_ebs_c3, BigDecimal managed_shared_cluster,
                         BigDecimal enterprise_portal,
                         BigDecimal query_editor,
                         BigDecimal import_export_data,
                         BigDecimal ticket_support  ) {
        super();
        this.compute_cost = compute_cost;
        this.container_eks = container_eks;
        this.container_ecs = container_ecs;
        this.container_ecr = container_ecr;
        this.amazon_s3_c3 = amazon_s3_c3;
        this.amazon_ebs_c3 = amazon_ebs_c3;
        this.enterprise_portal = enterprise_portal;
        this.query_editor = query_editor;
        this.import_export_data = import_export_data;
        this.ticket_support = ticket_support;


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
        return this.container_eks;
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
        return this.amazon_s3_c3;
    }

    public void setAmazon_s3_c3(BigDecimal amazon_s3_c3) {
        this.amazon_s3_c3=amazon_s3_c3;
    }
    @XmlElement(name="amazon_ebs_c3")
    public BigDecimal getAmazon_ebs_c3() {
        return this.amazon_ebs_c3;
    }
    public void setAmazon_ebs_c3(BigDecimal amazon_ebs_c3) {
        this.amazon_ebs_c3=amazon_ebs_c3;
    }
    @XmlElement(name = "enterprise_portal")
    public BigDecimal getEnterprise_portal() {
        return this.enterprise_portal;
    }
    public void setEnterprise_portal(BigDecimal enterprise_portal) {
        this.enterprise_portal = enterprise_portal;
    }
    @XmlElement(name="query_editor")
    public BigDecimal getQuery_editor() {
        return this.query_editor;
    }
    public void setQuery_editor(BigDecimal query_editor) {
        this.query_editor=query_editor;
    }

    @XmlElement(name="import_export_data")
    public BigDecimal getImport_export_data() {
        return this.import_export_data;
    }
    public void setImport_export_data(BigDecimal import_export_data) {
        this.import_export_data=import_export_data;
    }
    @XmlElement(name="ticket_support")
    public BigDecimal getTicket_support() {
        return this.ticket_support;
    }
    public void setTicket_support(BigDecimal ticket_support) {
        this.ticket_support=ticket_support;
    }



}
