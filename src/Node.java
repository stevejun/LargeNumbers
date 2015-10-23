
public class Node {
	private	int data;
	private Node next;
	
	public Node(){
		data=0;
		next=null;
	}
	
	public Node(int x){
		data=x;
		next=null;
	}
	
	public Node(int x, Node nextNode){
		data=x;
		next=nextNode;
	}
	
	public void setData(int x){
		data=x;	
	}
	
	public int getData(){
		return data;
	}
	
	public void setNext(Node nextNode){
		next=nextNode;
	}
	
	public Node getNext(){
		return next;
	}
	
	public boolean hasNext(){
		return next!=null;
	}
}
