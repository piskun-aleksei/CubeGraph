package util;

import java.util.ArrayList;
import java.util.Random;


/**
 * Created by Brotorias on 16.09.2016.
 */
public class ExponentialAlgorithm {
    private static ArrayList<Integer> result = new ArrayList<>();
    private static ArrayList<Double> values = new ArrayList<>();

    private static Double mValue,  dValue, sValue;

    private static Double max = 0D, min = 0D;

    public static ArrayList<Integer> computeSequence(Double lambda, Integer radix) {
        clear();

        initValues();

        exponentialAlgorithmCompute(lambda, radix);

        mathDispSqrCompute();

        return result;
    }

    private static void initValues() {
        mValue = 0D;
        dValue = 0D;
        sValue = 0D;

        for (int i = 0; i < 20; i++) {
            result.add(0);
        }
    }

    private static void exponentialAlgorithmCompute(Double lambda, Integer radix) {
        Random random = new Random();
        for (int i = 0; i < radix; i++) {
            values.add((-1) * (Math.log(random.nextDouble()) / lambda));
            if (i == 0) {
                max = values.get(0);
                min = max;
            } else {
                if (values.get(i) > max) {
                    max = values.get(i);
                }
                if (values.get(i) < min) {
                    min = values.get(i);
                }
            }
        }

        Double step = (max - min) / 20;

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < values.size(); j++){
                if(values.get(j) >= i*step && values.get(j) < (i+1)*step){
                    result.set(i, (result.get(i) + 1));
                }
            }
        }

    }

    private static void mathDispSqrCompute() {
        for (int i = 0; i < values.size(); i++) {
            mValue += values.get(i);
        }

        mValue /= (values.size() - 1);

        for (int i = 0; i < values.size(); i++) {
            dValue += (values.get(i) - mValue) * (values.get(i) - mValue);
        }

        dValue /= (values.size() - 1);

        sValue = Math.sqrt(dValue);
    }

    private static void clear() {
        values.clear();
        result.clear();
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

}
