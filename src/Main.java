import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        int[] input = {6,0,3};
        List<Integer> inputList = new ArrayList<>();
        for (int i : input)
            inputList.add(i);

        int result = 0;
        System.out.println(operations(inputList, result));

    }

    private static int operations(List<Integer> inputList, int result) {

        int indexOfFirstLocalMax = inputList.indexOf(Collections.max(inputList));

        if (indexOfFirstLocalMax == 0) {
            int indexOfSecondLocalMax = getSecondMaxIndexOnRightSide(inputList, indexOfFirstLocalMax);
            result += getResult(inputList, indexOfSecondLocalMax, indexOfFirstLocalMax);
            if (indexOfSecondLocalMax == inputList.size() - 1)
                return result;
            else {
                inputList = removeElements(inputList, indexOfFirstLocalMax, indexOfSecondLocalMax);
                return operations(inputList, result);
            }

        } else if (indexOfFirstLocalMax > 0 && indexOfFirstLocalMax < inputList.size() - 1) {
            int tempLeftSide = getSecondMaxIndexOnLeftSide(inputList, indexOfFirstLocalMax);
            int tempRightSide = getSecondMaxIndexOnRightSide(inputList, indexOfFirstLocalMax);
            int indexOfSecondLocalMax = ((inputList.get(tempLeftSide) > inputList.get(tempRightSide)) ? tempLeftSide : tempRightSide);
            result += getResult(inputList, indexOfSecondLocalMax, indexOfFirstLocalMax);
            inputList = removeElements(inputList, indexOfFirstLocalMax, indexOfSecondLocalMax);
            return operations(inputList, result);


        } else if (indexOfFirstLocalMax == inputList.size() - 1) {
            int indexOfSecondLocalMax = getSecondMaxIndexOnLeftSide(inputList, indexOfFirstLocalMax);
            result += getResult(inputList, indexOfSecondLocalMax, indexOfFirstLocalMax);
            if (indexOfSecondLocalMax == 0)
                return result;
            else {
                inputList = removeElements(inputList, indexOfFirstLocalMax, indexOfSecondLocalMax);
                return operations(inputList, result);
            }
        }
        return result;
    }

    /**
     * The function deletes the values between the two local maximum values and the first local maximum value.The recursive process continues with the resulting new list.
     * Ex : {3,5,13,6,9} -> {3,5,9}
     *
     * @param inputList             alist of data
     * @param indexOfFirstLocalMax  a first local max value on list
     * @param indexOfSecondLocalMax a second local max value on list
     * @return a list in which the values of the two local maximum values and the first local maximum value are deleted
     */
    private static List<Integer> removeElements(List<Integer> inputList, int indexOfFirstLocalMax, int indexOfSecondLocalMax) {

        if (indexOfFirstLocalMax < indexOfSecondLocalMax)
            inputList.subList(indexOfFirstLocalMax, indexOfSecondLocalMax).clear();

        else
            inputList.subList(indexOfSecondLocalMax + 1, indexOfFirstLocalMax + 1).clear();

        return inputList;

    }

    /**
     * The function finds the index of the first large value to the left of the local maximum value.
     * Ex: {6,4,3,15,2,1,0} -> indexOfFirstLocalMax = 3  AND return 0;
     *
     * @param inputList            a list of data
     * @param indexOfFirstLocalMax a first local max value on list
     * @return the index of the first max value to the left of the first local max
     */

    private static int getSecondMaxIndexOnLeftSide(List<Integer> inputList, int indexOfFirstLocalMax) {

        List<Integer> temp = new ArrayList<>(inputList);
        for (int i = indexOfFirstLocalMax; i < temp.size(); i++)
            temp.set(i, -1);

        return temp.indexOf(Collections.max(temp));

    }

    /**
     * The function finds the index of the first large value to the right of the local maximum value.
     * Ex: {6,4,3,15,2,1,11} -> indexOfFirstLocalMax = 3  AND return 6;
     *
     * @param inputList            a list of data
     * @param indexOfFirstLocalMax a first local max value on list
     * @return the index of the first max value to the right of the first local max
     */

    private static int getSecondMaxIndexOnRightSide(List<Integer> inputList, int indexOfFirstLocalMax) {

        List<Integer> temp = new ArrayList<>(inputList);


        for (int i = 0; i <= indexOfFirstLocalMax; i++)
            temp.set(i, -1);

        return temp.indexOf(Collections.max(temp));

    }

    /**
     * The function calculates the volume of water remaining between the local maximum
     * Ex:{6,4,3,15,2,1,11} -> return 11*3-(2+1);
     *
     * @param inputList             a list of data
     * @param indexOfSecondLocalMax a second max value on list
     * @param indexOfFirstLocalMax  a first max value on list
     * @return the volume between two local max
     */

    private static int getResult(List<Integer> inputList, int indexOfSecondLocalMax, int indexOfFirstLocalMax) {

        int volumeUnderWater = 0;

        for (int i = Math.min(indexOfFirstLocalMax, indexOfSecondLocalMax) + 1; i < Math.max(indexOfFirstLocalMax, indexOfSecondLocalMax); i++)
            volumeUnderWater += inputList.get(i);


        return inputList.get(indexOfSecondLocalMax) * (Math.abs(indexOfFirstLocalMax - indexOfSecondLocalMax)-1) - volumeUnderWater;
    }

}
