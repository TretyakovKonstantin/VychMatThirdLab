import java.util.Arrays;

public class Gauss {
    static double[] getSolution(double[][] oldMatrix) {
        double[][] matrix = new double[oldMatrix.length][];
        double[] freeElements = new double[oldMatrix.length];
        int size = freeElements.length;
        for (int i = 0; i < size; i++) {
            matrix[i] = Arrays.copyOf(oldMatrix[i], oldMatrix.length);
            freeElements[i] = oldMatrix[i][oldMatrix.length];
        }

        if (Math.abs(getDeterminant(matrix)) == 0) {
            throw new ArithmeticException("Определитель равен нулю, решения нет");
        }


        for (int k = 0; k < size; k++) {
            int max = k;
            for (int i = k + 1; i < size; i++) {
                if (Math.abs(matrix[i][k]) > Math.abs(matrix[max][k])) {
                    max = i;
                }
            }

            swap(matrix, freeElements, k, max);

            for (int i = k + 1; i < size; i++) {
                double mainElement = matrix[i][k] / matrix[k][k];
                freeElements[i] -= mainElement * freeElements[k];
                for (int j = k; j < size; j++) {
                    matrix[i][j] -= mainElement * matrix[k][j];
                }
            }
        }

        double[] answer = new double[size];
        for (int i = size - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < size; j++) {
                sum += matrix[i][j] * answer[j];
            }
            answer[i] = (freeElements[i] - sum) / matrix[i][i];
        }
        return answer;
    }

    private static void swap(double[][] matrix, double[] freeElements, int k, int max) {
        double[] temp = matrix[k];
        matrix[k] = matrix[max];
        matrix[max] = temp;
        double swap = freeElements[k];
        freeElements[k] = freeElements[max];
        freeElements[max] = swap;
    }

    public static double getDeterminant(double[][] matrix) {
        int n = matrix.length;
        double det = 0;
        if (n == 1)
            det = matrix[0][0];
        else if (n == 2)
            det = matrix[0][0] * matrix[1][1] - matrix[1][0] * matrix[0][1];
        else {
            for (int i = 0; i < n; i++) {
                double[][] temp = new double[n - 1][n - 1];
                for (int j = 1; j < n; j++) {
                    for (int k = 0; k < n; k++) {
                        if (k < i) {
                            temp[j - 1][k] = matrix[j][k];
                        } else if (k > i) {
                            temp[j - 1][k - 1] = matrix[j][k];
                        }
                    }
                }
                det += Math.pow(-1, i) * matrix[0][i] * getDeterminant(temp);
            }
        }
        return det;
    }

    public static double[] getDiscrepancy(double[][] oldMatrix, double[] answer) {
        double[][] matrix = new double[oldMatrix.length][];
        double[] freeElements = new double[oldMatrix.length];
        for (int i = 0; i < oldMatrix.length; i++) {
            matrix[i] = Arrays.copyOf(oldMatrix[i], oldMatrix.length);
            freeElements[i] = oldMatrix[i][oldMatrix.length];
        }

        double[] result = new double[answer.length];
        for (int i = 0; i < matrix.length; i++) {
            double sum = 0;
            for (int j = 0; j < matrix.length; j++) {
                sum += matrix[i][j] * answer[j];
            }
            result[i] = freeElements[i] - sum;
        }
        return result;
    }
}