package auxiliary.coverage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Weighing {
    String[][] browsersWeight = {{"chromeExtension","1.0"}};

    List<List<String>> packageWeight;
    List<String> allNamePackages;
    List<String> allPackageWeight;
    List<String> allPackageWeightTotal;
    public Weighing(List<List<String>> browsersXML){
        this.packageWeight = browsersXML;
        allNamePackages = new ArrayList<>();
        allPackageWeight = new ArrayList<>();
        allPackageWeightTotal = new ArrayList<>();
    }

    public void go(){
        getWeightAndNames();
    }

    public void getWeightAndNames(){
        for(int i = 0; i< packageWeight.size(); i++){
            for(int j = 0; j< packageWeight.get(i).size(); j++){
                if(j == 1){
                    allNamePackages.add(packageWeight.get(i).get(j));
                }else{
                    allPackageWeight.add(computeListValue(packageWeight.get(i).get(j).replace("\"","")));
                }
            }
        }
        allNamePackages = allNamePackages.stream().distinct().collect(Collectors.toList());
        addListValue();
    }
    public String computeListValue(String browser){
        String calculatedValue = null;
        for(int i = 0; i< browsersWeight.length; i++){
            if(browser.equals(browsersWeight[i][0])){
                calculatedValue = browsersWeight[i][1];
            }
        }
        return calculatedValue;
    }

    public void printWeightList(){
        System.out.println("PACKAGE | VALUE |");
        for(int i = 0; i< packageWeight.size(); i++){
            for(int j = 0; j< packageWeight.get(i).size(); j++){
                System.out.print(packageWeight.get(i).get(j) + " | ");
            }
            System.out.println();
        }
    }

    public void addListValue(){
        for(String browserActual: allNamePackages){
            Double total = 0.00;
            for(int i = 0; i< packageWeight.size(); i++){
                if(packageWeight.get(i).get(1).equals(browserActual)){
                    total=total + Double.parseDouble(allPackageWeight.get(i));
                }
            }
            allPackageWeightTotal.add((total*100) + "");
        }
    }

    public void printResult(){
        System.out.println(allNamePackages);
        System.out.println(allPackageWeightTotal);
    }
}