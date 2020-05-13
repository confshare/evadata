/**
 * Copyright (c) 2011, Regents of the University of California
 * All rights reserved.
 * <p/>
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * <p/>
 * 1. Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * <p/>
 * 2. Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided
 * with the distribution.
 * <p/>
 * 3. Neither the name of the University of California, Berkeley nor
 * the names of its contributors may be used to endorse or promote
 * products derived from this software without specific prior written
 * permission.
 * <p/>
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */

//package edu.berkeley.cs.wise.benchmarks;
package benchmarks;

//import edu.berkeley.cs.wise.concolic.Concolic;

/**
 * @author Jacob Burnim <jburnim@cs.berkeley.edu>
 */
public class TestQuickSortJDK15 {

	// public static void main_origin(String[] args) {
	// final int N = Integer.parseInt(args[0]);
	//
	// int data[] = new int[N];
	// for (int i = 0; i < N; i++) {
	// data[i] = Debug.makeSymbolicInteger("in" + i);//
	// Concolic.input.Integer();
	// }
	//
	// // We only measure the complexity of the sort itself. That
	// // is, we count branches only from this point forward in the
	// // execution.
	// // Concolic.ResetBranchCounting();
	//
	// Arrays.sort(data);
	// }

	// public static void main(String[] args) {
	// TestQuickSortJDK15 o = new TestQuickSortJDK15();
	// try {
	// System.out.println("Start testing...");
	// if (args.length == 1) {
	// RunUtil.runMethod(o, args[0]);
	// }
	// } catch (IllegalAccessException e) {
	// e.printStackTrace();
	// } catch (IllegalArgumentException e) {
	// e.printStackTrace();
	// } catch (InvocationTargetException e) {
	// e.printStackTrace();
	// } catch (InstantiationException e) {
	// e.printStackTrace();
	// }
	// }

	public static void action1(int a1, int a2, int a3, int a4, int a5, int a6,
			int a7) {
		int data[] = new int[7];

		data[0] = a1;
		data[1] = a2;
		data[2] = a3;
		data[3] = a4;
		data[4] = a5;
		data[5] = a6;
		data[6] = a7;
		sort(data);
	}

	public static void sort(int[] a) {
		sort1(a, 0, a.length);
	}

	private static void sort1(int x[], int off, int len) {
		// Insertion sort on smallest arrays
		if (len < 7) {
			for (int i = off; i < len + off; i++)
				for (int j = i; j > off && x[j - 1] > x[j]; j--)
					swap(x, j, j - 1);
			return;
		}

		// Choose a partition element, v
		int m = off + (len >> 1); // Small arrays, middle element
		if (len > 7) {
			int l = off;
			int n = off + len - 1;
			/*
			 * if (len > 40) { // Big arrays, pseudomedian of 9 int s = len/8; l
			 * = med3(x, l, l+s, l+2*s); m = med3(x, m-s, m, m+s); n = med3(x,
			 * n-2*s, n-s, n); }
			 */
			m = med3(x, l, m, n); // Mid-size, med of 3
		}
		int v = x[m];

		// Establish Invariant: v* (<v)* (>v)* v*
		int a = off, b = a, c = off + len - 1, d = c;
		while (true) {
			while (b <= c && x[b] <= v) {
				if (x[b] == v)
					swap(x, a++, b);
				b++;
			}
			while (c >= b && x[c] >= v) {
				if (x[c] == v)
					swap(x, c, d--);
				c--;
			}
			if (b > c)
				break;
			swap(x, b++, c--);
		}

		// Swap partition elements back to middle
		int s, n = off + len;
		s = Math.min(a - off, b - a);
		vecswap(x, off, b - s, s);
		s = Math.min(d - c, n - d - 1);
		vecswap(x, b, n - s, s);

		// Recursively sort non-partition-elements
		if ((s = b - a) > 1)
			sort1(x, off, s);
		if ((s = d - c) > 1)
			sort1(x, n - s, s);
	}

	/**
	 * Swaps x[a] with x[b].
	 */
	private static void swap(int x[], int a, int b) {
		int t = x[a];
		x[a] = x[b];
		x[b] = t;
	}

	/**
	 * Swaps x[a .. (a+n-1)] with x[b .. (b+n-1)].
	 */
	private static void vecswap(int x[], int a, int b, int n) {
		for (int i = 0; i < n; i++, a++, b++)
			swap(x, a, b);
	}

	/**
	 * Returns the index of the median of the three indexed integers.
	 */
	private static int med3(int x[], int a, int b, int c) {
		return (x[a] < x[b] ? (x[b] < x[c] ? b : x[a] < x[c] ? c : a)
				: (x[b] > x[c] ? b : x[a] > x[c] ? c : a));
	}
}
