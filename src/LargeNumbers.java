
/**
 * LargeNumbers class
 * utilizes int nodes and organizes large numbers into a linked list
 * Each node represents 3 digits (a power of 10^3)
 * 
 * LargeNumbers are stored as little-endian, where the least significant value
 * will be the head of the list. This allows for easy multiplication and addition.
 * Since LargeNumbers are stored as little-endian, a reverse method is used to print
 * and convert LargeNumbers into String.
 * 
 * Addition. Empty nodes are appended to the smaller number such that the two LargeNumbers 
 * are the same "size" (size is defined above the method)
 * A carry is generated for each addition of a node to be carried over to the next pair of nodes.
 * 
 * Multiplication. A multiplyByScalar method is used to multiply a node (which can be considered
 * as some constant c). A carry is generated as well. The method multiplyByScalar is done 
 * repeated until the correct number of partialProducts are produced. The add method is used to 
 * add these partial products. The product is the sum of these partial products.
 * 
 * @author Steve Jun
 *
 */
public class LargeNumbers {
	
	private Node head, tail;
	private int size;
	
	/**Default constructor	@param	no parameters*/
	public LargeNumbers(){
		head=null;
		tail=null;
		size=0;
	}
	
	/**
	 * Prepends a node with the int value x.
	 * If the list is empty tail becomes x.
	 * Method is private.
	 * 
	 * @param x; the int value we want to prepend
	 */
	private void prepend(int x){
		Node n = new Node(x);
		
		if (size==0)	tail=n;
		else			n.setNext(head);
		head=n;
		++size;
	}
	
	/**
	 * Append a node with the int value x.
	 * If the list is empty head becomes x.
	 * Method is private.
	 * 
	 * @param x; the int value we want to append
	 */
	private void append(int x){
		Node n = new Node(x);
		if (size==0)	head=n;
		else			tail.setNext(n);
		tail=n;
		++size;
	}
	
	/**Storing into LargeNumbers is a prepend.
	 * This is to maintain the little-endianness. 
	 * 
	 * @param x; the int value we want to store
	 */
	public void store(int x){
		prepend(x);
	}
	
	/**Reverses the LargeNumbers*/
	public void reverse(){
		Node p,q;
		if (head!=null){
			p=head.getNext();
			head.setNext(null);
			while(p!=null){
				q=p.getNext();
				p.setNext(head);
				head=p;
				p=q;
			}
		}
	}
	
	/**
	 * Converts the LargeNumbers into a String;
	 * reverse() is used, since LargeNumbers is little-endian
	 * toString() can be used for printing String values
	 */
	public String toString(){
		String outNumber= new String();
		Node q;
		
		reverse();
		q=head;
		outNumber=((Integer)q.getData()).toString();
		while(q.hasNext()){
			q=q.getNext();
			outNumber=outNumber+((Integer)q.getData()).toString();
		}
		reverse();
		return outNumber;		
	}
	
	/** 
	 * Adds two LargeNumbers.
	 * First, compare two large numbers by how many nodes they have.
	 * A while loop appends enough nodes such that N1 and N2 have the same number of nodes
	 * Each node is added. An appropriate carry is generated and passed.
	 * 
	 * largerN and smallerN does not necessarily indicate that one number is explicitly 
	 * larger than the other. largerN simply has a larger size than smallerN
	 * 
	 * @param N2; this LargeNumbers is added with N2 
	 * @return sum of the two LargeNumbers
	 */
	public LargeNumbers add(LargeNumbers N2){
		LargeNumbers smallerN,largerN,sum = new LargeNumbers(); 
		int i, j, partialSum, carry=0;
		
		if (this.size <= N2.size)	{smallerN=this;	largerN=N2;}
		else 						{largerN=this; smallerN=N2;}
		
		i=smallerN.size; 
		j=largerN.size;

		while(i<j){
			smallerN.append(000);
			++i;
		}
		
		Node p,q;
		p=smallerN.head;
		q=largerN.head;
		for (int k=0; k<j; ++k){
			partialSum= p.getData()+q.getData() + carry;
			sum.append(partialSum%1000);
			carry = partialSum/1000;
			p=p.getNext();
			q=q.getNext();
		}
		
		if(carry!=0) sum.append(carry);
		return sum;
	}
	
	/**
	 * Multiply this LargeNumbers with an int c.
	 * This method will be used to facilitate the multiplication for 
	 * multiplying two LargeNumbers. 
	 * 
	 * An appropriate carry is generated and passed.
	 * 
	 * @param c; some constant integer value
	 * @return
	 */
	public LargeNumbers multiplyByScalar(int c){
		LargeNumbers scalarProduct = new LargeNumbers();
		Node p=head;
		int partialProduct, carry=0;
		
		for(int i=0; i<this.size; ++i){
			partialProduct = p.getData()*c + carry;
			scalarProduct.append(partialProduct%1000);
			carry=partialProduct/1000;
			p=p.getNext();
		}
		if(carry!=0) scalarProduct.append(carry);
		return scalarProduct;
	}
	
	/**
	 * Multiplies two LargeNumbers.
	 * First, compare which LargeNumbers is greater in size.
	 * This will help with consistent multiplication, where the larger LargeNumbers is
	 * multiplied by each node of the smaller LargeNumbers to generate a partialProduct.
	 * 
	 * The product is the sum of all the partialProducts.
	 * 
	 * The add() and multiplyByScalar() are used.
	 * Carry is handled by these two methods.
	 * 
	 * @param N2; a LargeNumbers N2 that will be multiplied with this LargeNumbers
	 * @return product, which is the sum of all partialProducts
	 */
	public LargeNumbers multiply(LargeNumbers N2){
		LargeNumbers smallerN,largerN, partialProduct, product;
		
		if (this.size <= N2.size)	{smallerN=this;	largerN=N2;}
		else 						{largerN=this; smallerN=N2;}

		Node p=smallerN.head;
		
		partialProduct = largerN.multiplyByScalar(p.getData());
		product = partialProduct;
		for (int k=1; k<smallerN.size; ++k){
			p=p.getNext();
			partialProduct=largerN.multiplyByScalar(p.getData());
			for (int l=0;l<k;++l)
				partialProduct=partialProduct.multiplyByScalar(1000);
			product = product.add(partialProduct);
		}
		return product;
	}
	
}
