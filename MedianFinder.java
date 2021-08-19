
public class MedianFinder {

	public MedianFinder() {
	}
	
	/**
	* Publicly available function that finds the median of 2 sorted arrays A and B.
	* Uses a partitioning method to determine the median of the 2 arrays without having to merge them. 
	* The method searches though a series of partitions (using the shorter of the 2 arrays to determine where to partition)
	* in A and B until it finds one such that all elements on the left of the partitions in A and B are 
	* less than all elements to the right of the partitions in A and B. Because the arrays are sorted this condition 
	* can be checked by testing if the element to the left of the partition in A is less than or equal to the element
	* to the right of the partition in B AND the element to the right of the partition in A is greater than or equal to
	* the element to the left of the partition in B. If this condition is met we can determine the median. 
	* When the total number of elements in both arrays is odd, we take the maximum of the value to the left of the partition
	* in A and the value to the left of the partition in B. When the total number of elements is even, we must take 
	* the average of the maximum of the values to the left of the partitions in A and B and the minimum of the 2 values
	* to the right of the partitions in A and B. 
	* 
	* When the condition to end is not met we check which way to move the partition. If the value to the left of the
	* partition in A is greater than the value to the right of the partition in B, we eliminate everything greater than 
	* or equal to the current partition in X and check conditions again. Otherwise we eliminate everything less than
	* or equal to the current partition and check again. 
	* 
	* 
	*
	* @return	int		The median of 2 sorted arrays A and B. 
	*/
	
	public float FindMedian(int[] A, int[] B) {
	
		//for clarity in the code, we control it such that A will always be less than or equal to B
		if (A.length > B.length) {
			return FindMedian(B, A);// when this argument is passed "A" will now be less than or equal to "B"
		}
	
		int x = A.length; //always the shorter array
		int y = B.length; //always the longer array
		
		//the initial search range
		int low = 0;
		int high = x;
		
		while (low <= high) { //if this condition ever fails, it means the initial arguments were not sorted
			
			/* we choose our initial partition as the midpoint of the smaller array
			 * so that we can remove the largest possible search area on the next iteration.
			 * We want the size of the partition to always include half of the elements of the array on each side,
			 * so we have the relationship that:
			 * partitionX + partitionY = (lengthSmallerArray + lengthLargerArray + 1 ) / 2 */
			int partitionX = (low + high) / 2;
			int partitionY = (x + y + 1 )/ 2 - partitionX;
			
			//now find the values directly to the left and right of the partitions in each array
			int leftX, rightX, leftY, rightY;
			
			/* we also need to control what happens when there is nothing on either side of the partition
			 * do this by using either maximum or minimum "dummy" values*/
			
			if (partitionX == 0) { //there is nothing left of the partition in the smaller array
				leftX = Integer.MIN_VALUE;
			}
			else leftX = A[partitionX - 1];
			
			if (partitionX == x) { //there is nothing right of the partition in the smaller array
				rightX = Integer.MAX_VALUE;
			}
			else rightX = A[partitionX];
			
			if (partitionY == 0) {//there is nothing left of the partition in the larger array
				leftY = Integer.MIN_VALUE;
			}
			else leftY = B[partitionY -1];
			
			if (partitionY == y) { //there is nothing right of the partition in the larger array
				rightY = Integer.MAX_VALUE;
			}
			else rightY = B[partitionY];
			
			/* now that the values at of the current partition is established, we check to see if all the 
			 * values on the left are less than all of those on the right. If so, we can return the median.*/
			
			if (leftX <= rightY && leftY <= rightX) {
				if ((x + y) % 2 == 0) { //total number of elements is even
					return ((Math.max((float)leftX, (float)leftY) + Math.min((float)rightX, (float)rightY)) / 2);
				}
				else { //total number of elements is odd
					return Math.max((float)leftX, (float)leftY);
				}
			}
			
			/* if the condition is not met we must reduce the search criteria and iterate again*/
			else {
				if (leftX > rightY) { //the median must be less than the current partitionX
					high = partitionX - 1;//eliminate the search area greater than partitionX
				}
				else { //the median must be greater than the current partitionX
					low = partitionX + 1; //eliminate the search area less than partitionX
				}
			}
		
			
		}
		/* As stated above, we only exit the while loop without returning a median when the arrays were not 
		 * originally sorted, so here we can throw an exception*/
		throw new IllegalArgumentException("Input arrays were not sorted.");
		
	}

}
