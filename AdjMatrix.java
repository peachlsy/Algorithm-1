import java.io.*;
import java.util.*;


/**
 * Adjacency matrix implementation for the FriendshipGraph interface.
 * 
 * Your task is to complete the implementation of this class.  You may add methods, but ensure your modified class compiles and runs.
 *
 * @author Jeffrey Chan, 2016.
 */
public class AdjMatrix <T extends Object> implements FriendshipGraph<T>
{

	private static final int GRAPH_VERTEX_INIT_SIZE = 100;
	private static final int GRAPH_EDGE_INIT_SIZE = 100;
	
    Object[][] edges;//save all edges for vertex
    Object[] vertexes;//save all vertexes
    int availableVertexIndex = -1;//for quickly find empty index in vertex array.
    
    /**
	 * Contructs empty graph.
	 */
    public AdjMatrix() {
    	// Implement me!
    	//array of vertex
    	vertexes = new Object[GRAPH_VERTEX_INIT_SIZE];
    	//array of array of edge
    	edges = new Object[vertexes.length][];
    } // end of AdjList()
    
    /**********  1.graph management methods area   *****************/
    
    
    /**
     * if the vertex array is full ,expand the capacity of vertex array
     */
    private void expandVetexArray(){
    	Object[][] oldEdges = edges;
    	Object[] oldVetexes = vertexes;
    	//create a bigger array of vertex
    	vertexes = new Object[vertexes.length+GRAPH_VERTEX_INIT_SIZE];
    	edges = new Object[vertexes.length][];
    	//copy vertex data from old to new
    	System.arraycopy(oldVetexes, 0, vertexes, 0, oldVetexes.length);
    	System.arraycopy(oldEdges, 0, edges, 0, oldEdges.length);
    	availableVertexIndex = oldVetexes.length;
    }
    /**
     * expand the edge array of vertexIndex
     * @param vertexIndex
     */
    private int expandEdgesArray(int vertexIndex){
    	int availableEdgeIndex = -1;
    	if(vertexIndex < vertexes.length){
    		Object[] oldEdgeArray = edges[vertexIndex];
    		if(oldEdgeArray == null){
    			edges[vertexIndex] = new Object[GRAPH_EDGE_INIT_SIZE];
    			availableEdgeIndex = 0;
    		}else{
    			edges[vertexIndex] = new Object[edges[vertexIndex].length+GRAPH_EDGE_INIT_SIZE];
    			System.arraycopy(oldEdgeArray, 0, edges[vertexIndex], 0, oldEdgeArray.length);
    			availableEdgeIndex = oldEdgeArray.length;
    		}
    		
    	}
    	return availableEdgeIndex;
    }
    
    private boolean isVertexArrayFull(){
    	boolean full = true;
    	//firstly, check the lastRemovedVertexIndex:
    	if(availableVertexIndex!=-1){
    		full = false;
    	}else{//loop the array of vertex,check if any is empty
    		for(int i = 0 ; i < vertexes.length ; i ++){
        		Object o = vertexes[i];
    			if(o == null){
    				full = false;
    				availableVertexIndex = i;
    				break;
    			}
    		}
    	}
    	return full;
    }
    
    /**
     * get an availabel edge index of vertex
     * @param vertex
     * @return
     */
    private int getAvailableEdgeIndexForNewEdge(int vertex,Object newEdge){
    	int edgeIndex = -1;
    	boolean exist = false;//if find that the new edge has been in array,should not add again.
    	if(vertex < vertexes.length){
    		Object[] edgesOfvertex = edges[vertex]; 
    		if(edgesOfvertex != null){
    			for(int i = 0 ; i < edgesOfvertex.length ; i ++){
    				if(edgeIndex == -1 && edgesOfvertex[i] == null){
    					edgeIndex = i;
    				}
    				if(edgesOfvertex[i] != null && edgesOfvertex[i].equals(newEdge)){
    					exist = true;
    					break;
    				}
    			}
    		}
    		//if the edgeIndex is -1, means the array of edge must be null or full,it's time to expand it 
    		if(edgeIndex == -1 && !exist) edgeIndex = expandEdgesArray(vertex);
    	}
    	return edgeIndex;
    }
    
    private int getIndexOfEdge(int vertexIndex , Object edge){
    	int index = -1;
    	Object[] edgesOfvertex = edges[vertexIndex]; 
    	if(edgesOfvertex!=null){
    		for(int i = 0 ; i < edgesOfvertex.length ; i ++){
    			Object currentEdge = edgesOfvertex[i];
    			if(currentEdge!=null && currentEdge.equals(edge)){
    				index = i;
    				break;
    			}
    		}
    	}
    	return index;
    }
    
    /**
     * get the index of vertex in array
     * @param v
     * @return
     */
    private int indexOfVertex(Object v){
    	int index = -1;
    	for(int i = 0 ; i < vertexes.length ; i ++){
    		Object o = vertexes[i];
			if(o != null && o.equals(v)){
				index = i;
				break;
			}
		}
    	return index;
    }
    
    
    /********** 2 end   *****************/
    
    
    /**********  2.graph operation methods area   *****************/
    
    public void addVertex(T vertex) {
        // Implement me!
    	//check the vertex should not be null and not exist before
    	if(vertex !=null && indexOfVertex(vertex)==-1){
    		//add
    		//1.check if the vertex array if full ,should expand
    		if(isVertexArrayFull()){
    			expandVetexArray();
    		}
    		//2.after step 1, the availableVertexIndex got an available value
			vertexes[availableVertexIndex] = vertex;
			edges[availableVertexIndex] = new Object[GRAPH_VERTEX_INIT_SIZE];
			availableVertexIndex = -1;
    	}
    } // end of addVertex()
	
    
    public void addEdge(T v1, T v2) {
        // Implement me!
    	//1.check: they shouldn't be null and euqals each other
    	if(v1 != null && v2 !=null && !v1.equals(v2)){
    		//2.check: they must exist already.
    		int indexOfv1 = indexOfVertex(v1);
    		int indexOfv2 = indexOfVertex(v2);
    		if(indexOfv1 != -1 && indexOfv2 != -1){
    			//3. add edge v1-v2
    			int edgeIndex1 = getAvailableEdgeIndexForNewEdge(indexOfv1,v2);
    			if(edgeIndex1 == -1) return;//if find v2 in edge array of v1 , shouldn't add this edge
    			int edgeIndex2 = getAvailableEdgeIndexForNewEdge(indexOfv2,v1);
    			if(edgeIndex2 == -1 )return;//if find v1 in edge array of v2 , shouldn't add this edge
    			edges[indexOfv1][edgeIndex1] = v2;
    			edges[indexOfv2][edgeIndex2] = v1;
    		}else{
    			throw new IllegalArgumentException();
    		}
    		
    	}
    } // end of addEdge()
	

    public ArrayList<T> neighbours(T vertex) {
        ArrayList<T> neighbours = new ArrayList<T>();
        // Implement me!
        int indexOfvertex;
        //1.check: the vertex should exist
        if(vertex!=null && ((indexOfvertex=this.indexOfVertex(vertex)) != -1)){
        	//2. get all neighbours
        	for(int i = 0 ; i < edges[indexOfvertex].length ; i ++){
        		T t = (T)edges[indexOfvertex][i];
        		if(t != null){
        			neighbours.add(t);
        		}
        	}
        }else{
			throw new IllegalArgumentException();
		}
        
        return neighbours;
    } // end of neighbours()
    
    
    public void removeVertex(T vertex) {
        // Implement me!
        int indexOfvertex;
        //1.check: the vertex should exist
        if(vertex!=null && ((indexOfvertex=this.indexOfVertex(vertex)) != -1)){
        	//2. remove vertex
        	vertexes[indexOfvertex] = null;
        	Object[] oldEdges = edges[indexOfvertex];
        	edges[indexOfvertex] = null;
        	//3.remove all edges about this vertex
        	for(int i = 0 ; i < oldEdges.length ; i ++){
        		if(oldEdges[i]!=null){
        			int tempIndex = indexOfVertex(oldEdges[i]);
            		int tempIndex2 = getIndexOfEdge(tempIndex,vertex);
            		if(tempIndex!=-1 && tempIndex2!=-1){
            			edges[tempIndex][tempIndex2] = null;
            		}
        		}
        	}
        	this.availableVertexIndex = indexOfvertex;
        }else{
			throw new IllegalArgumentException();
		}
    } // end of removeVertex()
	
    
    public void removeEdge(T v1, T v2) {
        // Implement me!
    	//1.check: they shouldn't be null and euqals each other
    	if(v1 != null && v2 !=null && !v1.equals(v2)){
    		//2.check: they must exist already.
    		int indexOfv1 = indexOfVertex(v1);
    		int indexOfv2 = indexOfVertex(v2);
    		if(indexOfv1 != -1 && indexOfv2 != -1){
    			//3. add edge v1-v2
    			int edgeIndex1 = getIndexOfEdge(indexOfv1,v2);
    			if(edgeIndex1 == -1) return; // if v2 doesn't exist in edge array of v1, shouldn't remove this edge
    			int edgeIndex2 = getIndexOfEdge(indexOfv2,v1);
    			if(edgeIndex2 == -1 )return; // if v1 doesn't exist in edge array of v2, shouldn't remove this edge
    			edges[indexOfv1][edgeIndex1] = null;
    			edges[indexOfv2][edgeIndex2] = null;
    		}else{
    			throw new IllegalArgumentException();
    		}
    		
    	}
    } // end of removeEdges()
	
    /**
     * print all vertices
     */
    public void printVertices(PrintWriter os) {
        // Implement me!
    	//simply loop and print
    	for(int i = 0 ; i < vertexes.length ; i ++){
    		Object vertex = vertexes[i];
    		if(vertex != null){
    			os.print(vertex.toString() + " ");
    		}
    	}
    	os.println();
    	
    } // end of printVertices()
	
    /**
     * print all edges
     */
    public void printEdges(PrintWriter os) {
        // Implement me!
     	//simply loop and print
    	for(int i = 0 ; i < vertexes.length ; i ++){
    		Object vertex = vertexes[i];
    		if(vertex != null && edges[i]!=null){
    			for(int j = 0 ; j < edges[i].length ; j ++){
    				if(edges[i][j]!=null){
    					os.println(vertex.toString() + " " + edges[i][j].toString());
    				}
    			}
    		}
    	}
	
    } // end of printEdges()
    
    
    public int shortestPathDistance(T vertex1, T vertex2) {
    	// Implement me!
    	int shortestPathDistance = disconnectedDist;
    	int vertex2Index;
    	if(vertex2 != null && (vertex2Index= indexOfVertex(vertex2)) != -1 && edges[vertex2Index] != null){
    		
    		int distance = 0;
    		MyLinkedList<T> visitedLinkedList = new MyLinkedList<T>();
    		MyLinkedList<T> unvisitLinkedList = new MyLinkedList<T>();
    		unvisitLinkedList.add(vertex1);
    		while(unvisitLinkedList.size() != 0){	
    			MyLinkedList<T> newUnvisitLinkedList = new MyLinkedList<T>();
    			//step 2 : loop uncheckedList, add all vertexes's neighbours into the sameStepChildrenList
    			for(int m = 0 ; m < unvisitLinkedList.size() ; m++){
    				T current = unvisitLinkedList.get(m);
    				
    				//if find any vertex equals the 'end' vertex ,the shortest path has been found
    				if(vertex2.equals(current)){
    					return distance;
    				}
    				int curIndex = indexOfVertex(current);
    				if(curIndex == -1) continue;
    				Object[] edgesOfcurrentVertex = this.edges[curIndex];
    				if(edgesOfcurrentVertex == null) continue;//the list shouldn't be null
    				for(int n = 0 ; n < edgesOfcurrentVertex.length; n ++){
    					T vertex = (T)edgesOfcurrentVertex[n];
    					if(vertex != null && !visitedLinkedList.contains(vertex) && !newUnvisitLinkedList.contains(vertex)){
    						newUnvisitLinkedList.add(vertex);
    					}
    				}
    				visitedLinkedList.add(current);
    			}
    			/** Let the old unvisit list point to the new unvisit list.**/
    			unvisitLinkedList =newUnvisitLinkedList;
    			newUnvisitLinkedList = null;
    			distance++;/** update step at very beginning of sub-loop **/
    		}
    		
    		
    	}

        // if we reach this point, source and target are disconnected
        return shortestPathDistance;    	
    } // end of shortestPathDistance()	
    /********** 2 end    *****************/
    
} // end of class AdjMatrix