

public class MyLinkedList <I extends Object>{

	private static int EXTEND_SIZE = 10;
	private Element<I> first,last;
	private int size;
	/**
	 * 
	 *  A MyLinkedList is made of elements
	 *
	 * @param <I>
	 */
    private static class Element<I> {
        I item;
        Element<I> next;
        Element<I> previous;

        Element(I element, Element<I> previous, Element<I> next) {
            this.item = element;
            this.next = next;
            this.previous = previous;
        }
    }
	
	public MyLinkedList() {  

		this.size = 0;
	    this.first = this.last = null;  
	}  
	  
	public void add(I item) { 
		if(item == null){
			System.out.println("the element cannot be null.");
			return;
		}
		Element<I> oldLast = last;
		Element<I> newNode = new Element<I>(item,last, null);
        last = newNode;
        //if oldLast is null, it must be first time add element into list.
        if (oldLast == null){
            first = newNode;
        }else{
        	oldLast.next = newNode;
        }
        size++;
	}
	/**
	 * 
	 * @param index
	 * @return
	 */
    public I get(int index) {
		if(isOutOfrange(index)){
			System.err.println("<Error operation>(MyLinkedList): The index is out of current capacity.");
			return null;
		}
		int curIndex = 0;
		Element<I> current = this.first;
  		while(current!=null){
			if(index == curIndex){
				return current.item;
			}
			current = current.next;
			curIndex++;
		}
  		return null;
    }
	
	/**
	 * return the first index of element which equals object o , otherwise return -1
	 * @param o
	 * @return
	 */
    public int indexOf(Object o) {
        int index = 0;
        if (o != null) {
        	Element<I> element = this.first;
        	if(element != null){
        		while(element!=null){
        			if(o.equals(element.item)){
        				return index;
        			}
        			element = element.next;
        			index++;
        		}
        	}
        }
        return -1;
    }
    /**
     * remove the element equals object o in array list.
     * @param o
     * @return
     */
    public boolean remove(Object o) {
        if (o != null) {
        	Element<I> element = this.first;
    		while(element!=null){
    			if(o.equals(element.item)){
    			    final Element<I> next = element.next;
    		        final Element<I> previous = element.previous;
    		        //if previous == null, the current must be the first element
    		        if (previous == null) {
    		            first = next;
    		        } else {
    		        	previous.next = next;
    		            element.previous = null;
    		        }
    		        //if next == null the current must be the last element
    		        if (next == null) {
    		            last = previous;
    		        } else {
    		            next.previous = previous;
    		            element.next = null;
    		        }
    		        size--;
    				return true;
    			}
    			element = element.next;
    		}
        }
        return false;
    }
    /**
     * return the first element in list
     * @return
     */
    public I getFirst() {

        if (first == null)
            return null;
        else
        	return first.item;
    }
    /**
     * return true if there is a element in list equals object o,otherwise return false.
     * @param o
     * @return
     */
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

	/**
	 * get the current size of my array list 
	 * @return
	 */
    public int size() {
        return size;
    }
    /**
     * check index if it is out of range ,return true;otherwise return false.
     * @param index
     * @return
     */
	private boolean isOutOfrange(int index) {  
	    return (index > size || index < 0);  
	         
	} 

}
