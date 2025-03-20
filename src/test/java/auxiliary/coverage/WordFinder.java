package auxiliary.coverage;

import org.testng.Assert;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordFinder {

    static List<String> file;
    static List<String> keywords;

    public static List<String> parser(String parameter, String rout) {
        int i = 0;
        File folderT = new File(rout);
        file = new ArrayList<>();
        keywords = new ArrayList<>();
        List<String> classList = listFilesByFolder(folderT);
        String aux = "";
        List<String> totalPerClass = new ArrayList<>();
        for (String classSingle : classList) {
            //if (!classSingle.split("\\\\")[classSingle.split("\\\\").length - 2].equals(aux)) {
                //System.out.println("\n#####   PACKAGE " + (aux = classSingle.split("\\\\")[classSingle.split("\\\\").length - 2]) + "   #####");
            //}
            totalPerClass = extractValues(classSingle, parameter);
            for (String total : totalPerClass) {
                keywords.add(total);
            }
            String windows = "Windows 10";
            String system = "os.name";
            if(System.getProperty(system).equals(windows)){
                keywords.add(classSingle.split("\\\\")[classSingle.split("\\\\").length-1]);
            }else{
                keywords.add(classSingle.split("/")[classSingle.split("/").length-1]);
            }
            //System.out.println(classSingle.split("\\\\")[classSingle.split("\\\\").length - 1] + ", [" + parameter.replace("(", "") + ": " + totalPerClass + "]");
        }
        return keywords;
    }

    private static List<String> listFilesByFolder(File file) {
        for (File inputFile : file.listFiles()) {
            if (inputFile.isDirectory()) {
                listFilesByFolder(inputFile);
            } else {
                WordFinder.file.add(inputFile.getAbsolutePath());
            }
        }
        return WordFinder.file;
    }

    static List<String> extractValues(String absolutePath, String parameter) {
        File file1 = new File(absolutePath);
        List<String> temp;
        List<String> values;
        List<String> importantValues;
        importantValues = new ArrayList<>();
        values = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(file1);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String linea;
            while ((linea = bufferedReader.readLine()) != null) {
                if (linea.contains(parameter)) {
                    temp = Arrays.asList(linea.split(parameter + "="));
                    values.add(temp.get(1));
                }
            }
            Pattern p = Pattern.compile("\"([^\"]*)\"");
            int j = 0;
            while (j < values.size()) {
                Matcher m = p.matcher(values.get(j));
                while (m.find()) {
                    importantValues.add(m.group(0));
                }
                j++;
            }
            fileReader.close();
        } catch (Exception e) {
            System.out.println("Exception reading file " + file1 + ": ");
            Assert.fail(e.getMessage());
        }
        //System.out.println(importantValues);
        return importantValues;
    }
}