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

//package edu.berkeley.cs.wise.benchmarks;

//import edu.berkeley.cs.wise.concolic.Concolic;

/**
 * @author Sudeep Juvekar <sjuvekar@cs.berkeley.edu>
 * @author Jacob Burnim <jburnim@cs.berkeley.edu>
 */
public class Tsp {

	public static class TspSolver {
		private final int N;
		private int D[][];
		private boolean visited[];
		private int best;

		public int nCalls;

		public TspSolver(int N, int D[][]) {
			this.N = N;
			this.D = D;
			this.visited = new boolean[N];
			this.nCalls = 0;
		}

		public int solve() {
//			best = Integer.MAX_VALUE;
			best = 100000;

			for (int i = 0; i < N; i++)
				visited[i] = false;

			visited[0] = true;
			search(0, 0, N - 1);

			return best;
		}

		private int bound(int src, int length, int nLeft) {
			return length;
		}

		private void search(int src, int length, int nLeft) {
			nCalls++;

			if (nLeft == 0) {
				if (length + D[src][0] < best)
					best = length + D[src][0];
				return;
			}

			if (bound(src, length, nLeft) >= best)
				return;

			for (int i = 0; i < N; i++) {
				if (visited[i])
					continue;

				visited[i] = true;
				search(i, length + D[src][i], nLeft - 1);
				visited[i] = false;
			}
		}
	}

	public void run(int i00, int i01, int i02, int i03, int i04,
			int i10, int i11, int i12, int i13, int i14, int i20, int i21,
			int i22, int i23, int i24, int i30, int i31, int i32, int i33,
			int i34, int i40, int i41, int i42, int i43, int i44) {
		int N = 5;
		int D[][] = new int[N][N];
		D[0][0] = i00;
		D[0][1] = i01;
		D[0][2] = i02;
		D[0][3] = i03;
		D[0][4] = i04;
		D[1][0] = i10;
		D[1][1] = i11;
		D[1][2] = i12;
		D[1][3] = i13;
		D[1][4] = i14;
		D[2][0] = i20;
		D[2][1] = i21;
		D[2][2] = i22;
		D[2][3] = i23;
		D[2][4] = i24;
		D[3][0] = i30;
		D[3][1] = i31;
		D[3][2] = i32;
		D[3][3] = i33;
		D[3][4] = i34;
		D[4][0] = i40;
		D[4][1] = i41;
		D[4][2] = i42;
		D[4][3] = i43;
		D[4][4] = i44;
		
		TspSolver tspSolver = new TspSolver(N, D);
		tspSolver.solve();
	}

	// public static void main(String args[]) {
	// final int N = Integer.parseInt(args[0]);
	//
	// int D[][] = new int[N][N];
	//
	// for (int i = 0; i < N; i++) {
	// for (int j = 0; j < N; j++) {
	// D[i][j] = Debug.makeSymbolicInteger("in" + i + j);//
	// Concolic.input.Integer(0,
	// // 1000);
	// }
	// }
	//
	// TspSolver tspSolver = new TspSolver(N, D);
	//
	// // We only measure the complexity (i.e. path length) of the
	// // Tsp solving. That is, we count branches only from this
	// // point forward in the execution.
	// // Concolic.ResetBranchCounting();
	//
	// tspSolver.solve();
	// }
	
	public static void main(String[] args) {
    	Tsp o = new Tsp(); 
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
};
