package auxiliary.coverage;

import org.testng.Assert;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class WordFileSearch {
    static String rootTest = System.getProperty("user.dir") + "\\src\\test\\java\\test";
    static String rutaTest = rootTest;
    static int addParameter1 = 0;
    static double totalGeneral = 0;
    static List<String> file;

    public static void counter(String parameter1){
        String windows = "Windows 10";
        String system = "os.name";
        if(!System.getProperty(system).equals(windows)){
            rootTest = System.getProperty("user.dir") + "/src/test/java/test";
            rutaTest = rootTest;
        }

        File folderT = new File(rutaTest);
        file = new ArrayList<>();
        List<String> classList = listFilesByFolder(folderT);
        String aux = "";
        for (String classSingle : classList) {
            /*if (!classSingle.split("\\\\")[classSingle.split("\\\\").length - 2].equals(aux)) {
                System.out.println("\n#####   PACKAGE " + (aux = classSingle.split("\\\\")[classSingle.split("\\\\").length - 2]) + "   #####");
            }
             */
            String pattern = parameter1;
            if(pattern.contains(":")){
                pattern = pattern.replace(":",".+");
                pattern = pattern.replace(" ","");
            }
            countTestCases(classSingle, pattern);
            //System.out.println(classSingle.split("\\\\")[classSingle.split("\\\\").length - 1] + ", [" + parameter1.replace("(", "") + ": " + totalPerClass + "]");
        }
        if(addParameter1 != 0){
            totalGeneral++;
        }
        System.out.println("| Total : " + addParameter1);
        addParameter1 = 0;
    }

    private static List<String> listFilesByFolder(File folder) {
        for (File inputFile : folder.listFiles()) {
            if (inputFile.isDirectory()) {
                listFilesByFolder(inputFile);
            } else {
                file.add(inputFile.getAbsolutePath());
            }
        }
        return file;
    }

    private static void countTestCases(String absolutePath, String parameter1) {
        File file1 = new File(absolutePath);
        try {
            FileReader fileReader = new FileReader(file1);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String linea;
            while ((linea = bufferedReader.readLine()) != null) {
                if (linea.matches(".+" + parameter1 + ".+")) {
                    addParameter1++;
                }
            }
            fileReader.close();
        } catch (Exception e) {
            System.out.println("Exception reading file " + file1 + ": ");
            Assert.fail(e.getMessage());
        }
    }
}