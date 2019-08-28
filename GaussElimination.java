
public class GaussElimination {

	public static void main (String args []) {
		double arr1[][] = { {2, -2, 4, 0, 0},
							{-3, 3, -6, 5, 15},
							{1, -1, 2, 0, 0}   };

		double arr2[][] = { {2, 3, 1, -11, 1},
							{5, -2, 5, -4, 5},
							{1, -1, 3, -3, 3},
							{3, 4, -7, 2, -7}   };


		double arr3[][] = { { 2, 1, -1, 2, 5},
							{ 4, 5, -3, 6, 9},
							{ -2, 5, -2, 6, 4},
							{ 4, 11, -4, 8, 2}   };

		double arr4[][] = { {1, 4, 7},
							{-2, -7, -5} };
		

		elim(arr1);

		elim(arr2);

		// Uncomment these below to see the result!
		//elim(arr3);
		
		//elim(arr4);

	}

	// Method uses gauss elimination
	// it goes through each row and uses the first row as pivot
	// can solve matrices sized 2x3, 3x4, and 4x5
	public static double[][] elim(double[][] arr) {
		System.out.println("Orginal Matrix:");
		printArr(arr);

		double temp[] = new double[5];

		int pivot =1;
		
		// This is where gaussian elimination takes place
		for(int col= 0; col < 3; col++) {
			if(col ==2)
				pivot++;
			for(int row = 1; row < arr.length; row++) {
				double pivotNum = arr[pivot][col] / arr[col][col];
				//System.out.println("PIVOT: " + arr[  pivot ][col] + " / " + arr[ col ][ col ] + " = " +two); 
				for (int a = 0; a <= arr.length; a++) {
					if(arr[col][a] != 0) {
						temp[a] = arr[col][a]*pivotNum;

						if(col!=0 && a==1 && row <3)
							row++;
						if(col==2 && a==2 && row <3)
							row=row+2;

						//System.out.print( temp[a] +" - " + arr[row][a] + " = ") ;

						arr[row][a] = temp[a] -arr[row][a];

						//System.out.print(arr[row][a] + "\n");
					}
				}

				// this checks of the matrix is done solving or if it's full of zeros
				int count = 0;
				for(int i = 0; i < arr.length; i++) {
					for(int j = 0; j < arr[i].length; j++) {
						if(arr[i][j] == 0)
							count++;
						else 
							break;

						if(count > arr.length) {
							System.out.println("DONE");
							printArr(arr);
							if(checkForm(arr))
								backSub(arr);
							return arr;
						}

						if(i == arr.length-1 && count >= arr.length-1) {
							System.out.println("DONE");
							printArr(arr);
							if(checkForm(arr))
								backSub(arr);
							return arr;
						}
					}
					count=0;
				}

				pivot=pivot+1;
			}

			pivot=2;
			System.out.println("STEP " + (col+1));
			printArr(arr);

		}

		System.out.println("DONE");
		printArr(arr);
		
		if(checkForm(arr))
			backSub(arr);

		return arr;
	}

	
	// method uses back substitution to find variables
	public static void backSub(double arr[][]){
		double w = 0;
		double x = 0;
		double y = 0;
		double z = 0;
		int col = arr.length;
		int row = arr[0].length;
		double temp = 0;

		// finds Z variable
		z = arr[col-1][row-1] / arr[col-1][row-2];

		// finds Y variable
		temp = arr[col-2][row-2] * z;
		if(temp > 0)
			temp = arr[col-2][row-1] - temp;
		else
			temp = arr[col-2][row-1] + (-1*temp);

		y = temp / arr[col-2][row-3];

		// prints if matrix is 2 by 3
		if(arr.length <= 2 && arr[0].length <= 3) {
			System.out.println("X = " + y);
			System.out.println("Y = " + z + "\n==========================================\n");
			return;
		}
		
		// finds X variable
		temp = z * arr[col-3][row-2] + y * arr[col-3][row-3];

		if(temp > 0)
			temp = arr[col-3][row-1] - temp;
		else
			temp = arr[col-3][row-1] + (-1*temp);

		x = temp / arr[col-3][row-4];

		// prints if matrix is 3 by 4
		if(arr.length <= 3 && arr[0].length <= 4) {
			System.out.println("X = " + x);
			System.out.println("Y = " + y);
			System.out.println("Z = " +z +"\n==========================================\n");
			return;
		}

		// finds W variable
		temp = (arr[col-4][row-4] * x) + (arr[col-4][row-3] * y) + (arr[col-4][row-2] * z);
		if(temp > 0)
			temp = arr[col-4][row-1] - temp;
		else
			temp = arr[col-4][row-1] + (-1*temp);

		w = temp / arr[col-4][row-5];

		// prints if matrix is 4 by 5
		if(arr.length >= 4 && arr[0].length >= 5)
			System.out.println("W = " + w);
		System.out.println("X = " + x);
		System.out.println("Y = " + y);
		System.out.println("Z = " +z +"\n==========================================\n");
	}

	
	// Method checks the form of the matrix if it can be solved
	public static boolean checkForm(double[][] arr){
		boolean echelon = false;
		for(int i = 1; i < arr.length; i++) {
			int j = 0;
			while(j < i) {
				if(arr[i][j] == 0 ) 
					echelon = true;
				else 
					echelon = false;					
				j++;
			}
		}

		// if the last row is full of zeros then it is unsolvable
		int count = 0;
		int i = 0;
		while(i < arr[0].length) {
			if(arr[arr.length-1][i] == 0) 
				count++;
			if(count >= arr[0].length) 
				echelon = false;
			i++;
		}

		if(echelon)
			System.out.println("Matrix is in row echelon form and CAN be solved");
		else
			System.out.println("Matrix is NOT in a form that can be solved\n==========================================\n");

		return echelon;
	}

	
	// prints the matrix
	public static void printArr(double[][] arr){
		for(int i = 0; i < arr.length; i++) {
			for(int j = 0; j < arr[i].length; j++) {
				System.out.printf("%.2f, ", arr[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}
}
