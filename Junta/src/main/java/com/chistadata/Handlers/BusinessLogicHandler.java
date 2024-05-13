package com.chistadata.Handlers;

import com.chistadata.Infrastructure.Plugins.COMPUTATION_CONTEXT;
import com.chistadata.Infrastructure.Plugins.IComputationCommand;

public class BusinessLogicHandler implements IComputationCommand {
    public boolean PreExecute(COMPUTATION_CONTEXT ctx) {
        return true;
    }
    public boolean Execute(COMPUTATION_CONTEXT ctx) {
        System.out.println("Default Business Logic Handler");
        return true;
    }
    public boolean PostExecute(COMPUTATION_CONTEXT ctx) {
        return true;
    }
}
