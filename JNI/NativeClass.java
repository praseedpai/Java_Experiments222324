public class NativeClass
{
    public String[] strs;
    public int[] vals;

    public native String[] createStrs(int siz);
    public native int[] createVals(int siz);
    public native void sortStrs();
    public native void reverseArray(int[] val);
    public native void genException();

    public void outputVals(String mess)
    {
        System.out.println(mess);
        for (int x = 0; x < vals.length; x++)
        {
            System.out.println("vals[" + x + "] = " + vals[x]);
        }
    }

    public void outputStrs(String mess)
    {
        System.out.println(mess);
        for (int x = 0; x < strs.length; x++)
        {
            System.out.println("strs[" + x + "] = " + strs[x]);
        }
    }

    // a static method that provides a convenient entry point
    // for external processes (who are using the Invocation API)
    public static void start()
    {
        // Create the Demonstration class
        NativeClass demo = new NativeClass();

        demo.vals = demo.createVals(3);
        demo.outputVals("After Creation");
        demo.reverseArray(demo.vals);
        demo.outputVals("After reversing");

        demo.strs = demo.createStrs(3);
        demo.outputStrs("After creation");
        demo.sortStrs();
        demo.outputStrs("After sorting");
        demo.genException();
    }

    public static void main(String args[])
    {
       try {
        start();
       }
       catch(Exception e ) {
          System.out.println("Exception Hello World...");

          return;
       }
    }

    static
    {
        System.loadLibrary("NativeClass");
    }


}