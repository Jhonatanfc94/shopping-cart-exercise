package auxiliary.coverage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainReliability {
    public static void main(String[] args) {
        List<String> dataProviders = WordFinder.parser("value","src/test/resources");
        List<String> packageName = WordFinder.parser("package name","src/test/resources");

        List<List<String>> ReadBrowserPacks = new ArrayList<>();
        List<String> auxiliary = new ArrayList<>();
        int currentPackagePosition = 0;
        for (String dataProvider:dataProviders){
            if(dataProvider.contains(".xml")){
                auxiliary = new ArrayList<>();
            }else{
                auxiliary.add(dataProvider);
                while (currentPackagePosition<packageName.size()){
                    String packageString = packageName.get(currentPackagePosition);
                    if(packageString.contains(".xml")){
                        currentPackagePosition = currentPackagePosition + 1;
                        break;
                    }else{
                        auxiliary.add(packageString);
                        ReadBrowserPacks.add(auxiliary);
                        auxiliary = new ArrayList<>();
                        auxiliary.add(dataProvider);
                    }
                    currentPackagePosition = currentPackagePosition + 1;
                }
            }
        }
        //System.out.println(ReadBrowserPacks);

        Weighing weighing = new Weighing(ReadBrowserPacks);
        weighing.go();
        System.out.println("BROWSERS");
        weighing.printResult();

        List<String> IPAAutomatize = new ArrayList<>();
        try {
            File myObj = new File("src/test/java/auxiliary/coverage/pending.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                IPAAutomatize.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        System.out.println("CALCULATING AUTOMATION PERCENTAGE");
        for(String ipAutomatize:IPAAutomatize){
            System.out.print(ipAutomatize);
            WordFileSearch.counter(ipAutomatize);
        }
        System.out.println("Automation Percentage: " + (WordFileSearch.totalGeneral/IPAAutomatize.size())*100);

        System.out.println("CALCULATION OF THE RANDOM PERCENTAGE");
        WordFileSearch.totalGeneral = 0;
        for(String ipAutomatize:IPAAutomatize){
            System.out.print(ipAutomatize);
            WordFileSearch.counter(ipAutomatize + " : true");
        }
        System.out.print("Randomness Percentage: " + (WordFileSearch.totalGeneral/IPAAutomatize.size())*100);
    }
}