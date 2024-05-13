package com.chistadata.Handlers.ContactMgmt.ContactGroup;

import com.chistadata.Infrastructure.Plugins.COMPUTATION_CONTEXT;
import com.chistadata.Infrastructure.Plugins.IComputationCommand;

public class SamplePlugin implements IComputationCommand {
    public boolean PreExecute(COMPUTATION_CONTEXT ctx) {
        return true;
    }
    public boolean Execute(COMPUTATION_CONTEXT ctx) {
        System.out.println("Hello World...A Sample Plugin Template");
        return true;
    }
   public boolean PostExecute(COMPUTATION_CONTEXT ctx) {
        return true;
    }
}
