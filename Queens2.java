import java.util.*;
import java.util.Arrays;
import java.util.List;

public class Queens2 {
	static int oibarnei = 0;

	public static boolean containsAnswer(ArrayList<int[]> l, int[] a){
		for(int i = 0; i < l.size(); i++){
			if(Arrays.equals(a, l.get(i)))
				return true;
		}
		
		return false;
	}
	
	public static boolean isConsistent(int[] q, int n) {
		for (int i = 0; i < n; i++) {
			if (q[i] == q[n])
				return false;
			if ((q[i] - q[n]) == (n - i))
				return false;
			if ((q[n] - q[i]) == (n - i))
				return false;
		}
		return true;
	}

	public static void printList(ArrayList<int[]> l) {
		for (int i = 0; i < l.size(); i++) {
			printQueens(l.get(i));
		}
	}

	public static void printQueens(int[] q) {
		int n = q.length;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (q[i] == j)
					System.out.print("Q ");
				else
					System.out.print("* ");
			}
			System.out.println();
		}
		System.out.println();
	}

	public static void enumerate(int n, ArrayList<int[]> a2, int x, int y) {
		int[] a = new int[n];
		a[x] = y;
		enumerate(a, a2, 0, x, y);
	}

	public static void enumerate(int[] q, ArrayList<int[]> a, int k, int x, int y) {
		int n = q.length;
		if (k == n) {
			oibarnei++;
			if(!containsAnswer(a, q))
				a.add(q.clone());
		} else {
			if (k != x) {
				for (int i = 0; i < n; i++) {
					q[k] = i;
					if (isConsistent(q, k))
						enumerate(q, a, k + 1, x, y);
				}
			} else {
				if (isConsistent(q, k))
					enumerate(q, a, k + 1, x, y);
			}
		}
	}

	public static void main(String[] args) {
		oibarnei = 0;
		ArrayList<int[]> answers = new ArrayList<int[]>();
		int n = Integer.parseInt(args[0]);
		
		for(int i = 0; i < n; i++)
			for(int j = 0; j < n; j++)
			enumerate(n, answers, i, j);
		
		printList(answers);
		System.out.println(answers.size());
		System.out.println(oibarnei);
	}

}
