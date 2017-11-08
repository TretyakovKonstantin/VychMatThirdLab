import java.util.Scanner;
import javax.swing.JFrame;

public class Main extends JFrame {
    public static void main(String[] args) {

        try {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Введите координаты x через пробел:");
            String[] xStrRepresentation = scanner.nextLine().split(" ");
            System.out.println("Введите координаты y через пробел:");
            String[] yStrRepresentation = scanner.nextLine().split(" ");
            System.out.println("Введите степень многочлена");
            int basis = scanner.nextInt();
            if (yStrRepresentation.length != xStrRepresentation.length) {
                throw new IllegalArgumentException();
            }

            int length = xStrRepresentation.length;
            if (basis > length - 2) {
                throw new ArithmeticException();
            }

            double[] x = new double[length];
            double[] y = new double[length];
            for (int i = 0; i < length; i++) {
                x[i] = Double.parseDouble(xStrRepresentation[i]);
                y[i] = Double.parseDouble(yStrRepresentation[i]);
            }

            double[] result = new FormMain().solve(x, y, basis);
            GUICreater.createGUI(x, y, result, basis);

        } catch (NumberFormatException e) {
            System.out.println("Аргументы должны быть типа double");
        } catch (ArithmeticException e) {
            System.out.println("Базис должен быть хотя бы на 2 больше, чем количество точек");
        } catch (IllegalArgumentException e) {
            System.out.println("Количество координат x и y должно быть равно");
        }

    }

}