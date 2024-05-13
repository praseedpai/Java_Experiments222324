// C# program to print Hello World! 
using System; 
using System.Collections.Generic;
// namespace declaration 

	public static class Composition{
   
		public static Func<T2> Compose<T1, T2>(Func<T1> f1, 
				Func<T1, T2> f2){
        		return () => f2(f1());
    		}

    		public static Func<T1, T3> Compose<T1, T2, T3>(
			Func<T1, T2> f1, Func<T2, T3> f2){
        			return v => f2(f1(v));
    		}
	}

	class ListContainer
	{
		private List<int> store = new List<int>();
		public ListContainer(int [] temp) {
			AddAll(temp);
		}
		public ListContainer(List<int> param) {
			store = param;
		}
                public bool AddAll( int [] temp )
		{
			foreach( int t in temp )
				store.Add(t);
			return true;
		}
		public ListContainer Map(Func<int,int> pfunc )
		{
			List<int> ts = new List<int>();
			foreach( int t in store )
				ts.Add(pfunc(t));
			return new ListContainer(ts);

		}

		public List<int> getCont() { return store; }

	}

	class LazyListContainer
	{
		private List<int> store = new List<int>();
		private Func<int,int> m_pfunc = null;
		public LazyListContainer(int [] temp) {
			AddAll(temp);
		}
		public LazyListContainer(List<int> param,
			Func<int,int> ppfunc) {
			store = param;
			m_pfunc = ppfunc;
		}
                public bool AddAll( int [] temp )
		{
			foreach( int t in temp )
				store.Add(t);
			return true;
		}
		public LazyListContainer Map(Func<int,int> pfunc )
		{
			#if false
			List<int> ts = new List<int>();
			foreach( int t in store )
				ts.Add(pfunc(t));
			return new ListContainer(ts);
			#else
			if ( m_pfunc == null ) {
				m_pfunc = pfunc;
			}
			else {
				m_pfunc =
					Composition.Compose<int,int,int>(
						m_pfunc,pfunc);
			}
			return new LazyListContainer(store,m_pfunc);

			#endif
		}

		public List<int> getCont() { return store; }
		public List<int> Evaluate( ) {
			if (m_pfunc == null ) return null;
			List<int> ts = new List<int>();
			foreach( int t in store )
				ts.Add(m_pfunc(t));
			return ts;

		}

	}

	// Class declaration 
	class Geeks { 
          
        // Main Method 
        static void Main(string[] args) { 
            	//----- A Simple Program to Demonstrate List
            	List<int> rs = new List<int>();
	    	rs.Add(20); rs.Add(30); rs.Add(40);
	    	foreach(int ns in rs ) {
			Console.WriteLine(ns);
	    	}
	    	//------------------ List Container
		ListContainer cont = new ListContainer( new int[] { 1 , 2,3 } );
		var s = cont.Map( a => a + a ).Map(a => a * a);
		List<int> result = s.getCont();
		foreach(int ns2 in result ) {
			Console.WriteLine(ns2);
	    	}

		//--------------- LazyList Container
		LazyListContainer cont2 = new 
			LazyListContainer( new int[] { 1 , 2,3 } );
		var s2 = cont2.Map( a => a + a ).Map(a => a * a);
		List<int> result2 = s2.Evaluate();
		foreach(int ns3 in result2 ) {
			Console.WriteLine(ns3);
	    	}
		
        } 
    } 
