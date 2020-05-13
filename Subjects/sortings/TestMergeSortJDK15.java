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

package benchmarks;

import java.lang.reflect.InvocationTargetException;

import gov.nasa.jpf.symbc.Debug;
import benchmarks.java15.util.Arrays;

//import edu.berkeley.cs.wise.concolic.Concolic;

/**
 * @author Jacob Burnim <jburnim@cs.berkeley.edu>
 */
public class TestMergeSortJDK15 {

	// public static void original(String[] args) {
	// final int N = Integer.parseInt(args[0]);
	//
	// SimpleObject data[] = new SimpleObject[N];
	// for (int i = 0; i < N; i++) {
	// data[i] = new SimpleObject(Debug.makeSymbolicInteger("in" + i));//
	// Concolic.input.Integer());
	// }
	//
	// // We only measure the complexity of the sort itself. That
	// // is, we count branches only from this point forward in the
	// // execution.
	// // Concolic.ResetBranchCounting();
	//
	// Arrays.sort(data);
	// }
	//
	// public static void action1(int a1, int a2, int a3) {
	// SimpleObject data[] = new SimpleObject[3];
	// data[0] = new SimpleObject(a1);
	// data[1] = new SimpleObject(a2);
	// data[2] = new SimpleObject(a3);
	// Arrays.sort(data);
	// }

	public static void action3(int a1, int a2, int a3, int a4, int a5, int a6,
			int a7) {
		SimpleObject data[] = new SimpleObject[7];
		data[0] = new SimpleObject(a1);
		data[1] = new SimpleObject(a2);
		data[2] = new SimpleObject(a3);
		data[3] = new SimpleObject(a4);
		data[4] = new SimpleObject(a5);
		data[5] = new SimpleObject(a6);
		data[6] = new SimpleObject(a7);
		// Arrays.sort(data);
		Object[] aux = (Object[]) data.clone();
		mergeSort(aux, data, 0, data.length, 0);

	}

	private static final int INSERTIONSORT_THRESHOLD = 7;

	static void mergeSort(Object[] src, Object[] dest, int low, int high,
			int off) {
		int length = high - low;

		// Insertion sort on smallest arrays
		if (length < INSERTIONSORT_THRESHOLD) {
			for (int i = low; i < high; i++)
				for (int j = i; j > low
						&& ((Comparable) dest[j - 1]).compareTo(dest[j]) > 0; j--) {
					// swap(dest, j, j - 1);
					// swap(Object[] x, int a, int b)
					Object t = dest[j];
					dest[j] = dest[j - 1];
					dest[j - 1] = t;
				}
			return;
		}

		// Recursively sort halves of dest into src
		int destLow = low;
		int destHigh = high;
		low += off;
		high += off;
		int mid = (low + high) >> 1;
		mergeSort(dest, src, low, mid, -off);
		mergeSort(dest, src, mid, high, -off);

		// If list is already sorted, just copy from src to dest. This is an
		// optimization that results in faster sorts for nearly ordered lists.
		if (((Comparable) src[mid - 1]).compareTo(src[mid]) <= 0) {
			System.arraycopy(src, low, dest, destLow, length);
			return;
		}

		// Merge sorted halves (now in src) into dest
		for (int i = destLow, p = low, q = mid; i < destHigh; i++) {
			if (q >= high || p < mid
					&& ((Comparable) src[p]).compareTo(src[q]) <= 0)
				dest[i] = src[p++];
			else
				dest[i] = src[q++];
		}
	}

	// public static void action4(int a1, int a2, int a3, int a4, int a5, int
	// a6,
	// int a7, int a8, int a9, int a10) {
	// SimpleObject data[] = new SimpleObject[10];
	// data[0] = new SimpleObject(a1);
	// data[1] = new SimpleObject(a2);
	// data[2] = new SimpleObject(a3);
	// data[3] = new SimpleObject(a4);
	// data[4] = new SimpleObject(a5);
	// data[5] = new SimpleObject(a6);
	// data[6] = new SimpleObject(a7);
	// data[7] = new SimpleObject(a8);
	// data[8] = new SimpleObject(a9);
	// data[9] = new SimpleObject(a10);
	// Arrays.sort(data);
	// }
	//
	// public static void action2(int a1, int a2, int a3, int a4, int a5) {
	// SimpleObject data[] = new SimpleObject[5];
	// data[0] = new SimpleObject(a1);
	// data[1] = new SimpleObject(a2);
	// data[2] = new SimpleObject(a3);
	// data[3] = new SimpleObject(a4);
	// data[4] = new SimpleObject(a5);
	// Arrays.sort(data);
	// }

	// public static void main(String[] args) {
	// TestMergeSortJDK15 o = new TestMergeSortJDK15();
	// try {
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
}
