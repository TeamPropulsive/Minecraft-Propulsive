package io.github.teampropulsive.util;

public class MathUtil {
    // fun fact: java's % operator isn't actually modulus
    public static int mod(int a, int b) {
        return (a % b + b) % b;
    }
}
