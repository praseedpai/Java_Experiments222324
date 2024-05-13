using System;
using System.Linq;
using System.Collections.Generic;

public class Student{

	public int StudentID { get; set; }
	public string StudentName { get; set; }
	public int Age { get; set; }
}

public class User {

	public String UserName { get; set; }
	public List<String> Roles { get; set; }
	
}
public class Program
{
	public static void Main()
	{
	    IList<Student> studentList = new List<Student>() { 
            new Student() { StudentID = 1, StudentName = "John", Age = 13 } ,
            new Student() { StudentID = 2, StudentName = "Steve",  Age = 15 } ,
            new Student() { StudentID = 3, StudentName = "Bill",  Age = 18 } ,
            new Student() { StudentID = 4, StudentName = "Ram" , Age = 12 } ,
            new Student() { StudentID = 5, StudentName = "Ron" , Age = 21 } 
        };
            
	var users = new List<User>{
                new User { UserName = "Reza" ,
			 Roles = new List<string>{"Superadmin" } },
                new User { UserName = "Amin" , 
			Roles = new List<string>{"Guest","Reseption" } },
                new User { UserName = "Nima" , 
			Roles = new List<string>{"Nurse","Guest" } },
        };

	//var query = users.SelectMany(user => user.Roles, 
        //   (user, role) => new { user.UserName, role });
	var query = users.SelectMany(user => user.Roles);
		//, 
             //(user, role) => new { user.UserName, role });

	foreach (var obj in query)
	{
   		 Console.WriteLine(obj);
	}
	 List<List<int>> numbers = new List<List<int>> {
                new List<int> {1, 2, 3},
                new List<int> {12},
                new List<int> {5, 6, 5, 7},
                new List<int> {10, 10, 10, 12}
            };

 	IEnumerable<int> result = numbers
                .SelectMany( list => { return 
					new int[] { list.Count , 
				list.Count*list.Count };});
                
	foreach( int t in result ) { Console.Write(t+ " " ); }

        Console.WriteLine("=====================");
	List<int> aList = new List<int>(new int[] {1, 2, 3});
    	List<int> bList = new List<int>(new int[] {4, 5, 6});
        IEnumerable<int[]> rst =aList.SelectMany( 
		avalue => bList.Select( bvalue =>
               bvalue , new int [] {avalue, bvalue } ));
    
    
    }
}

