package edu.utexas.testsource;

import java.lang.reflect.InvocationTargetException;

import edu.utexas.util.RunUtil;

public class Rational {

	private int num;
	private int den;

	public Rational() {
		num = 0;
		den = 1;
	}

	public Rational(int n, int d) {
		num = n;
		den = d;
	}

	public int abs(int x) {
		if (x >= 0)
			return x;
		else
			return -x;
	}

	public int gcd(int x, int y) {
		// int a = 1;
		if (x == 0)
			return abs(y);
		int i = 0;
		while ((y != 0) && (i < 2)) {
			if (x > y)
				x = x - y;
			else
				y = y - x;
			if (i == 2)
				return -1;
			i++;
		}
		return abs(x);
	}

	// public static int gcd(int a,int b){
	// /*
	// int res;
	// while (b != 0){
	// res = a%b;
	// a = b;
	// b = res;
	// }
	// return abs(a);
	// */
	//
	// int c = abs(a);
	// if (c == 0)
	// return abs(b);
	//
	// int count=0;
	// while (b != 0) {
	// count++;
	// System.out.println("count "+count);
	// if(count>=4) assert false;
	// if (c > b)
	// c = c-b;
	// else
	// b = b-c;
	// }
	// return c;
	// }

	public void simplify(int a, int b) {
		int gcd = gcd(a, b);
		if (gcd == 0) {
			return;
		}
		if (gcd != 0) {
			num = num / gcd;
			den = den / gcd;
		}
	}

	public static void arraycopy(Object[] src, Object[] dest, int length) {
		if (length < 0)
			throw new ArithmeticException();// ArrayIndexOutOfBoundsException();
		for (int i = 0; i < length; i++)
			dest[i] = src[i];
	}

	// public static Rational[] simp(Rational[] rs) {
	// int length = rs.length;
	// Rational[] oldRs = new Rational[length];
	// arraycopy(rs, oldRs, length);
	// for (int i = 0; i < length; i++)
	// rs[i].simplify(rs[i].num, rs[i].den);
	// return oldRs;
	// }

	// public Rational[] simp(int i1, int i2, int i3, int i4, int i5, int i6) {
	// Rational r0 = new Rational(i1, i2);
	// Rational r1 = new Rational(i3, i4);
	// Rational r2 = new Rational(i5, i6);
	// Rational[] rs = new Rational[3];
	// rs[0] = r0;
	// rs[1] = r1;
	// rs[2] = r2;
	// int length = rs.length;
	// Rational[] oldRs = new Rational[length];
	// arraycopy(rs, oldRs, length);
	// for (int i = 0; i < length; i++)
	// rs[i].simplify(rs[i].num, rs[i].den);
	// return oldRs;
	// }

	public void simp(int i1, int i2, int i3, int i4, int i5, int i6) {
		simplify(i1, i2);
		simplify(i3, i4);
		simplify(i5, i6);
	}

	public boolean equals(Object obj) {
		if (obj instanceof Rational) {
			Rational rat = (Rational) obj;
			return this.num == rat.num && this.den == rat.den;
		}
		return false;
	}

	public float toFloat() {
		return ((float) num / den);
	}

	public String toString() {
		if (den != 1)
			return num + "/" + den;
		else
			return String.valueOf(num);
	}

	public Rational mul(Rational r) {
		return new Rational(num * r.num, den * r.den);
	}

	public Rational div(Rational r) {
		return new Rational(num * r.den, den * r.num);
	}

	@SuppressWarnings("unused")
	private static void printArray(Rational[] rs) {
		for (int i = 0; i < rs.length; i++)
			System.out.print(rs[i].toString() + " ");
		System.out.println();
	}

	public static void main(String[] args) {
		Rational r = new Rational();
		try {
			RunUtil.runMethod(r, args[0]);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// if (args[0].equals("abs"))
		// new Rational().abs(0);
		// else if (args[0].equals("gcd"))
		// new Rational().gcd(0, 0);
		// else if (args[0].equals("simp"))
		// new Rational().simp(0,0,0,0,0,0);
		// else if (args[0].equals("simplify"))
		// new Rational().simplify(0,0);
	}
	// public static void main(String[] args){
	// Rational r0 = new Rational(5,10);
	// Rational r1 = new Rational(2,10);
	// Rational r2 = new Rational(4,6);
	// Rational[] rs = new Rational[3];
	// rs[0] = r0; rs[1] = r1; rs[2] = r2;
	// System.out.print("Original array: ");
	// printArray(rs);
	// Rational[] oldRs = simp(rs);
	// System.out.print("Simplified array: ");
	// printArray(rs);
	// System.out.print("Copied array: ");
	// printArray(oldRs);
	// }
	
	public void mysimp(int i1, int i2, int i3, int i4, int i5, int i6) {
		int gcd = 0;
		if (i1 == 0)
			gcd = i2 >= 0 ? i2 : -i2;
		int i = 0;
		while ((i2 != 0) && (i < 2)) {
			if (i1 > i2)
				i1 = i1 - i2;
			else
				i2 = i2 - i1;
			if (i == 2)
				gcd = -1;
			i++;
		}
		gcd = i1 >= 0 ? i1 : -i1;

		if (gcd == 0) {
			return;
		}
		if (gcd != 0) {
			num = num / gcd;
			den = den / gcd;
		}
		
		gcd = 0;
		if (i3 == 0)
			gcd = i4 >= 0 ? i4 : -i4;
		i = 0;
		while ((i4 != 0) && (i < 2)) {
			if (i3 > i4)
				i3 = i3 - i4;
			else
				i4 = i4 - i3;
			if (i == 2)
				gcd = -1;
			i++;
		}
		gcd = i3 >= 0 ? i3 : -i3;

		if (gcd == 0) {
			return;
		}
		if (gcd != 0) {
			num = num / gcd;
			den = den / gcd;
		}
		
		gcd = 0;
		if (i5 == 0)
			gcd = i6 >= 0 ? i6 : -i6;
		i = 0;
		while ((i6 != 0) && (i < 2)) {
			if (i5 > i6)
				i5 = i5 - i6;
			else
				i6 = i6 - i5;
			if (i == 2)
				gcd = -1;
			i++;
		}
		gcd = i5 >= 0 ? i5 : -i5;

		if (gcd == 0) {
			return;
		}
		if (gcd != 0) {
			num = num / gcd;
			den = den / gcd;
		}
	}

}
