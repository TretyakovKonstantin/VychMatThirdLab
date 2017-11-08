class FormMain {


    private double[][] makeSystem(double[][] xyTable, int basis) {
        double[][] matrix = new double[basis][basis + 1];

        for (int i = 0; i < basis; i++) {
            for (int j = 0; j < basis; j++) {
                matrix[i][j] = 0;
            }
        }

        for (int i = 0; i < basis; i++) {
            for (int j = 0; j < basis; j++) {
                double sumA = 0, sumB = 0;
                for (int k = 0; k < xyTable[0].length; k++) {
                    sumA += Math.pow(xyTable[0][k], i) * Math.pow(xyTable[0][k], j);
                    sumB += xyTable[1][k] * Math.pow(xyTable[0][k], i);
                }
                matrix[i][j] = sumA;
                matrix[i][basis] = sumB;
            }
        }

        for (int i = 0; i < basis; i++) {
            for (int j = 0; j < basis + 1; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        return matrix;
    }

    double[] solve(double[] x, double[] y, int basis) {
        basis += 1;
        double[][] xyTable = new double[2][x.length];

        for (int i = 0; i < x.length; i++) {
            xyTable[0][i] = x[i];
            xyTable[1][i] = y[i];
        }

        double[][] matrix = makeSystem(xyTable, basis);
        return Gauss.getSolution(matrix);
    }
}