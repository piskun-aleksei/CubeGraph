package util;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.regex.Matcher;

/**
 * Created by Aliaksei_Piskun1 on 16-Sep-16.
 */
public class LemerAlgorithm {
    private static ArrayList<Double> values = new ArrayList<>();
    private static ArrayList<Double> valuesDivided = new ArrayList<>();
    private static ArrayList<Integer> result = new ArrayList<>();

    private static Double mValue, dValue, sValue, piValue, knValue;

    private static Integer pValue, lValue;

    public static ArrayList<Integer> computeSequence(Double a, Double r, Double m, Integer radix) {

        clear();

        initValues();

        lemerAlgorithmCompute(a, r, m, radix);

        mathDispSqrCompute();

        periodCompute();

        knCompute();

        return result;
    }

    private static void initValues() {
        mValue = 0D;
        dValue = 0D;
        sValue = 0D;
        piValue = 0D;
        knValue = 0D;
        pValue = 0;
        lValue = 0;

        for (int i = 0; i < 20; i++) {
            result.add(0);
        }
    }

    private static void lemerAlgorithmCompute(Double a, Double r, Double m, Integer radix) {

        Double tempValue = r, tempParam = 0D;

        Double step = 0.05D;

        for (int i = 0; i < radix - 1; i++) {
            tempValue = ((a * tempValue) % m);
            values.add(tempValue);
            tempParam = tempValue / m;
            valuesDivided.add(tempParam);

            for (int j = 0; j < 20; j++) {
                if (tempParam >= j * step && tempParam < (j + 1) * step) {
                    result.set(j, (result.get(j) + 1));
                }
            }
        }
    }

    private static void mathDispSqrCompute() {

        for (int i = 0; i < values.size(); i++) {
            mValue += values.get(i);
        }

        for (int i = 0; i < values.size(); i++) {
            dValue += (values.get(i) - mValue) * (values.get(i) - mValue);
        }

        mValue /= (values.size() - 1);

        dValue /= (values.size() - 1);

        sValue = Math.sqrt(dValue);
    }

    private static void periodCompute() {
        Integer firstEntry = 0, secondEntry = 0;

        boolean flag = false;

        for (int i = 1; i < valuesDivided.size(); i++) {
            if (valuesDivided.get(i).equals(valuesDivided.get(0))) {
                if (!flag) {
                    flag = true;
                    firstEntry = i;
                    continue;

                } else {
                    secondEntry = i;
                    break;
                }
            }
        }

        pValue = secondEntry - firstEntry;

        lValue = pValue;
    }

    private static void knCompute() {

        piValue = Math.PI / 4;

        Double pairsCount = 0D;

        for (int i = 0; i < (valuesDivided.size() / 2); i++) {
            if ((Math.pow(valuesDivided.get(2 * i), 2) + Math.pow(valuesDivided.get(2 * i + 1), 2)) < 1) {
                pairsCount++;

            }
        }

        knValue = pairsCount / valuesDivided.size();

    }

    private static void clear() {
        values.clear();
        valuesDivided.clear();
        result.clear();
    }

    public static ArrayList<Double> getValues() {
        return values;
    }


    public static Double getmValue() {
        return mValue;
    }

    public static Double getdValue() {
        return dValue;
    }

    public static Double getsValue() {
        return sValue;
    }

    public static Integer getpValue() {
        return pValue;
    }

    public static Integer getlValue() {
        return lValue;
    }

    public static Double getKnValue() {
        return knValue;
    }

    public static Double getPiValue() {
        return piValue;
    }


}

