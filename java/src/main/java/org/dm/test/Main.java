package org.dm.test;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Mirage on 2016-08-09.
 */
public class Main {

    public static void main(String[] args) {

//        ExecutorService executorService = Executors.newFixedThreadPool(8);
        Tree tree = buildModel();

        System.out.println("done model. node count:" + tree.getNodeCount());
        File file = new File("E:\\UoM\\Sem 7\\Data Mining and Information Retrieval\\Project\\test.csv");
        File out = new File("E:\\UoM\\Sem 7\\Data Mining and Information Retrieval\\Project\\submission.csv");

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(out))) {


                String line;
                long lineCount = 0;
                long start = System.currentTimeMillis();
                br.readLine();
                bw.write("id,Demanda_uni_equil\n");
                while ((line = br.readLine()) != null) {
                    // process the line.

                    String[] rowData = line.split(",");
                    DataEntry dataEntry = new DataEntry();
                    dataEntry.Semana = Integer.valueOf(rowData[0]);
//                dataEntry.Agencia_ID = Integer.valueOf(rowData[2]);
                    dataEntry.Canal_ID = Integer.valueOf(rowData[3]);
                    dataEntry.Ruta_SAK = Integer.valueOf(rowData[4]);
                    dataEntry.Cliente_ID = Integer.valueOf(rowData[5]);
                    dataEntry.Producto_ID = Integer.valueOf(rowData[6]);
                    dataEntry.Demanda_uni_equil = -1;
                    lineCount++;
//                    if (lineCount > 1000) {
////                    System.out.println(line);
//                        break;
//                    }
                    int prediction = tree.getPrediction(dataEntry);

                    bw.write(dataEntry.Semana + "," + prediction + "\n");

                }
                long end = System.currentTimeMillis();
//            tree.printTree();

                System.out.println(lineCount);
                System.out.println((end - start) / 1000f);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


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
                lineCount++;
//                if (lineCount > 20000000) {
////                    System.out.println(line);
//                    break;
//                }
                tree.addEntry(dataEntry);
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
