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
public class MergeSortJDK15 {

	public static void original(String[] args) {
		final int N = Integer.parseInt(args[0]);

		SimpleObject data[] = new SimpleObject[N];
		for (int i = 0; i < N; i++) {
			data[i] = new SimpleObject(Debug.makeSymbolicInteger("in" + i));// Concolic.input.Integer());
		}

		// We only measure the complexity of the sort itself. That
		// is, we count branches only from this point forward in the
		// execution.
		// Concolic.ResetBranchCounting();

		Arrays.sort(data);
	}

	public static void action1(int a1, int a2, int a3) {
		SimpleObject data[] = new SimpleObject[3];
		data[0] = new SimpleObject(a1);
		data[1] = new SimpleObject(a2);
		data[2] = new SimpleObject(a3);
		Arrays.sort(data);
	}

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
		Arrays.sort(data);
	}

	public static void action4(int a1, int a2, int a3, int a4, int a5, int a6,
			int a7, int a8) {
		SimpleObject data[] = new SimpleObject[8];
		data[0] = new SimpleObject(a1);
		data[1] = new SimpleObject(a2);
		data[2] = new SimpleObject(a3);
		data[3] = new SimpleObject(a4);
		data[4] = new SimpleObject(a5);
		data[5] = new SimpleObject(a6);
		data[6] = new SimpleObject(a7);
		data[7] = new SimpleObject(a8);
//		data[8] = new SimpleObject(a9);
//		data[9] = new SimpleObject(a10);
		Arrays.sort(data);
	}

	public static void action2(int a1, int a2, int a3, int a4, int a5, int a6,
			int a7) {
		SimpleObject data[] = new SimpleObject[7];
		data[0] = new SimpleObject(a1);
		data[1] = new SimpleObject(a2);
		data[2] = new SimpleObject(a3);
		data[3] = new SimpleObject(a4);
		data[4] = new SimpleObject(a5);
		data[5] = new SimpleObject(a6);
		data[6] = new SimpleObject(a7);
		Arrays.sort2(data);
	}

	public static void main(String[] args) {
		MergeSortJDK15 o = new MergeSortJDK15();
		try {
			if (args.length == 1) {
				RunUtil.runMethod(o, args[0]);
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
	}
}
