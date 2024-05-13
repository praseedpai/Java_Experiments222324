///////////////////////////////
// A Simple RxJava Program
//
//

import io.reactivex.rxjava3.core.*;
import io.reactivex.rxjava3.observers.*;
import io.reactivex.rxjava3.disposables.*;
import io.reactivex.rxjava3.functions.*;
public class FirstReactive
{
	public static void main(String[] args) {
		// Create a Observable Stream
		// using the Just Operator
		Observable<String> myStrings =
			Observable.just("Alpha", "Beta", "Gamma");
		// Drain the Stream
		myStrings.subscribe(s -> System.out.println(s));
		//-- Map or Apply a Function (Length )
		// and Drain the stream
		myStrings.map(s -> s.length()).
			subscribe(s -> System.out.println(s));
		//--- Apply Method Handles through map
		//--- Also Predicate (filter)
		myStrings.map(String::length).
			filter(i -> i >= 5)
			.subscribe(
				s -> 
				System.out.println("RECEIVED: " + s)
			);
		//------------------- Create an Observable and 
		//------ Consume in Place
		//	interface Observer<T> {
		//		void onNext(T t)
		//		void onError(Throwable t)
		//		void onCompleted()
		//	}
		Observable.create(s -> {
			s.onNext("Hello World!");
		}).subscribe(hello -> System.out.println(hello));
		//---------- How to Use ReactiveX
		//---------- Consumer Type 
        	Observable<String> observable = 
			Observable.just("how", "to", "do", "in", "java");
		Consumer<? super String> consumer = System.out::println;
 		observable.subscribe(consumer);
		observable.map(w -> w.toUpperCase()).
			subscribe(consumer);
		//-------------- How to use Disposible
		Disposable d = Observable.range(1, 5)
			.subscribeWith(new DisposableObserver<Integer>() {
        			 @Override 
				 public void onStart() {
             				System.out.println("Start!");
         			 }
        			 @Override 
				 public void onNext(Integer t) {
             				if (t == 3) {
                 				dispose();
             				}
            			        System.out.println(t);
        			 }
         			 @Override 
				 public void onError(Throwable t) {
             				t.printStackTrace();
         			 }
         			 @Override 
				 public void onComplete() {
             				System.out.println("Done!");
         			 }
     			});
 			// ...
 			d.dispose();
		}
	}