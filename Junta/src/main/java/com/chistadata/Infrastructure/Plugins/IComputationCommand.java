package com.chistadata.Infrastructure.Plugins;

public interface IComputationCommand {
   public boolean PreExecute(COMPUTATION_CONTEXT ctx);
   public  boolean Execute(COMPUTATION_CONTEXT ctx);
   public boolean PostExecute(COMPUTATION_CONTEXT ctx);
}
