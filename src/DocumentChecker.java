import akka.actor.UntypedActor;
import akka.actor.ActorRef;
import static akka.actor.Actors.actorOf;

/**
 * Stub
 * 
 * 
 * @author Chris
 * @author Carol
 * @MasterOfConcurrency Andrew Lyne
 *
 */
public class DocumentChecker extends UntypedActor {
	
	private final ActorRef stations[];
	
	public DocumentChecker(int numStations){
		stations = new ActorRef[numStations];
		for(int i=0; i<numStations; i++){
			stations[i] = actorOf(Queue.class).start();; 
		}
	}
	
	public void onReceive(Object message) throws Exception {
		
		if(message instanceof Configure){
			for(int i=0; i < stations.length; i++){
			stations[i].tell(message);	
			}
		}
		
		
	}

	public void sendPersonToQueue(Person person, ActorRef security){
		int check = (int) (Math.random() * 100);
		ActorRef queue;
		if(check <= 20) {
			rejectPerson(person);
		}
		else {
			/*
			 * Upon building the queues, we need to construct a
			 * configure object which possesses all the parameters
			 * needed to build queues, bag and body scans, and
			 * security.
			 * 
			 * Thus, each actor will need to compare (instanceof)
			 * on the incoming message to determine if it
			 * is a configure object and process accordingly
			 * 
			 */
			for(int i = 0; i < 4; i++) {
				queue = actorOf(Queue.class).start();
				queue.tell(person, security);
			}
		}
	}

	
	public void rejectPerson(Person person){
		System.out.println("Person: " + person.getPersonId() + " has been turned away.");
	}
}
