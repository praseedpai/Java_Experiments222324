package com.chistadata.Handlers.ContactMgmt.ContactGroup;
import com.chistadata.Infrastructure.Plugins.*;

public class NullPlugin implements IComputationCommand {
    public boolean PreExecute(COMPUTATION_CONTEXT ctx) {
        return true;
    }
    public boolean Execute(COMPUTATION_CONTEXT ctx) {
        return true;
    }
    public boolean PostExecute(COMPUTATION_CONTEXT ctx) {
        return true;
    }
}
