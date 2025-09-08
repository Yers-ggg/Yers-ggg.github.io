import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Solves the "Two Boxes" problem.
 * The program determines the minimum possible height of a stack of two boxes,
 * where one box can be placed on another only if its base fits completely on the
 * top face of the bottom box.
 */
public class Main {

    /**
     * A helper class to represent a single orientation of a box.
     * An orientation is defined by its height and the two dimensions of its base.
     * The base dimensions are always stored in a sorted order (base1 <= base2)
     * to simplify the logic for checking if one box fits on another.
     */
    static class BoxOrientation {
        int height;
        int base1;
        int base2;

        public BoxOrientation(int h, int b1, int b2) {
            this.height = h;
            // Store base dimensions sorted to make comparisons straightforward.
            if (b1 <= b2) {
                this.base1 = b1;
                this.base2 = b2;
            } else {
                this.base1 = b2;
                this.base2 = b1;
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Read dimensions for the first box
        int[] box1Dims = new int[3];
        for (int i = 0; i < 3; i++) {
            box1Dims[i] = scanner.nextInt();
        }

        // Read dimensions for the second box
        int[] box2Dims = new int[3];
        for (int i = 0; i < 3; i++) {
            box2Dims[i] = scanner.nextInt();
        }
        
        scanner.close();

        // Generate all unique orientations for each box
        List<BoxOrientation> orientations1 = generateOrientations(box1Dims);
        List<BoxOrientation> orientations2 = generateOrientations(box2Dims);

        long minTotalHeight = Long.MAX_VALUE;
        boolean isPossible = false;

        // --- Check all stacking combinations ---

        // Case 1: Box 2 on top of Box 1
        for (BoxOrientation bottomBox : orientations1) {
            for (BoxOrientation topBox : orientations2) {
                // Check if the top box's base fits on the bottom box's top face
                if (topBox.base1 <= bottomBox.base1 && topBox.base2 <= bottomBox.base2) {
                    long currentHeight = (long) bottomBox.height + topBox.height;
                    minTotalHeight = Math.min(minTotalHeight, currentHeight);
                    isPossible = true;
                }
            }
        }

        // Case 2: Box 1 on top of Box 2
        for (BoxOrientation bottomBox : orientations2) {
            for (BoxOrientation topBox : orientations1) {
                // Check if the top box's base fits on the bottom box's top face
                if (topBox.base1 <= bottomBox.base1 && topBox.base2 <= bottomBox.base2) {
                    long currentHeight = (long) bottomBox.height + topBox.height;
                    minTotalHeight = Math.min(minTotalHeight, currentHeight);
                    isPossible = true;
                }
            }
        }

        // --- Output the result ---
        if (isPossible) {
            System.out.println(minTotalHeight);
        } else {
            System.out.println(-1);
        }
    }

    /**
     * Generates a list of the three possible orientations for a box given its dimensions.
     * Each dimension (a, b, c) can serve as the height once.
     * @param dims An array of 3 integers representing the box's side lengths.
     * @return A list of BoxOrientation objects.
     */
    private static List<BoxOrientation> generateOrientations(int[] dims) {
        List<BoxOrientation> orientations = new ArrayList<>();
        // Orientation 1: dims[0] is height, base is (dims[1], dims[2])
        orientations.add(new BoxOrientation(dims[0], dims[1], dims[2]));
        // Orientation 2: dims[1] is height, base is (dims[0], dims[2])
        orientations.add(new BoxOrientation(dims[1], dims[0], dims[2]));
        // Orientation 3: dims[2] is height, base is (dims[0], dims[1])
        orientations.add(new BoxOrientation(dims[2], dims[0], dims[1]));
        return orientations;
    }
}
