package org.dm.test;

import java.io.*;

/**
 * Created by Mirage on 2016-08-15.
 */
public class MainTest {

    public static void main(String[] args) {

        Tree tree = buildModel();


    }

    private static Tree buildModel() {
        File file = new File("E:\\UoM\\Sem 7\\Data Mining and Information Retrieval\\Project\\train.csv");
        Tree tree = new Tree();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            long lineCount = 0;
            long start = System.currentTimeMillis();
            br.readLine();
            while ((line = br.readLine()) != null) {
                // process the line.

                String[] rowData = line.split(",");
                DataEntry dataEntry = new DataEntry();
                dataEntry.Semana = Integer.valueOf(rowData[0]);
                dataEntry.Agencia_ID = Integer.valueOf(rowData[1]);
                dataEntry.Canal_ID = Integer.valueOf(rowData[2]);
                dataEntry.Ruta_SAK = Integer.valueOf(rowData[3]);
                dataEntry.Cliente_ID = Integer.valueOf(rowData[4]);
                dataEntry.Producto_ID = Integer.valueOf(rowData[5]);
                dataEntry.Demanda_uni_equil = ((rowData[10] != null) ? Integer.valueOf(rowData[10]) : -1);
//                lineCount++;
//                if (lineCount > 10000000) {
////                    System.out.println(line);
//                    break;
//                }
                if (dataEntry.Demanda_uni_equil != 0) {
                    lineCount++;
                }

            }
            long end = System.currentTimeMillis();
//            tree.printTree();

            System.out.println(lineCount);
            System.out.println((end - start) / 1000f);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tree;
    }
}
