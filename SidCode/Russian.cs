class Russian {
    public static void Main( String [] args ) 
    {
        if ( args.Length != 2 ) { 
            Console.WriteLine("Usage Russian <a> <b> "); 
        return; 
        }
        try {
            int x = Convert.ToInt32(args[0]); 
            int y = Convert.ToInt32(args[1]);
            if ( x < 0 || y < 0 ) {
                Console.WriteLine("The values should be positive"); 
                return; 
            }
            long z = Calc( x , y );
            Console.WriteLine("Product is " + z.ToString() ); 
        }
        catch( Exception e )
        {
            Console.WriteLine(e.ToString()); 
        }
    }

    public static long Calc(int x,int y) { 
        int a = x; int b = y; long sum = 0; 
        while(a >= 1) { 
            if( a % 2 !=0) 
                sum = sum+b;
            a = a >> 1; 
            b = b<<1; 
        } 
        return sum; 
    }
}
