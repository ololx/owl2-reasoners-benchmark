/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package owl2reasonersbenchmark;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;


/**
 *
 * @author SheldonCooper
 */
public class OWL2ReasonersBenchmark {

    /**
     * @param args the command line arguments
     */
    
    private static String SEPARATOR, RESULTS_DIR, TEST_DIR;
    private static String [] REASONERS_LIST, REASONING_LIST;
    private static Integer TEST_ITERATION;
    private static Long DELAY_BETWEEN_ITERATION;
    private static ArrayList<String> testList = new ArrayList<String>();
    private static Workbook results = new HSSFWorkbook ();
    private static Sheet consistency, classify, realization;
        
    private void getCFG (Properties cfg, String path) throws IOException {
        cfg.load(new FileInputStream(new File(path)));
        this.SEPARATOR = cfg.getProperty("SEPARATOR", ";");
        this.RESULTS_DIR = cfg.getProperty("RESULTS_DIR", "./results");
        this.TEST_DIR = cfg.getProperty("TEST_DIR", "./ontologies");
        this.REASONERS_LIST = cfg.getProperty("REASONERS_LIST").split (this.SEPARATOR);
        this.REASONING_LIST = cfg.getProperty("REASONING_LIST").split (this.SEPARATOR);
        this.TEST_ITERATION = Integer.valueOf(cfg.getProperty("TEST_ITERATION"));
        this.DELAY_BETWEEN_ITERATION = Long.valueOf(cfg.getProperty("DELAY_BETWEEN_ITERATION"));
    }
    private static boolean getTestList (File dir) {
        File []dirList = dir.listFiles();
        for (int i=0; i<dirList.length; i++) {
            if(dirList[i].isFile()) {
                OWL2ReasonersBenchmark.testList.add (dirList[i].getName());
            } else if (dirList[i].isDirectory()) {
                getTestList (dirList[i], dirList[i].getName());
            }
        }
        if (OWL2ReasonersBenchmark.testList.size()>0) {
            return true;
        } else {
            return false;
        } 
    }
    private static boolean getTestList (File dir, String parentDirName) {
        File []dirList = dir.listFiles();
        for (int i=0; i<dirList.length; i++) {
            if(dirList[i].isFile()) {
                OWL2ReasonersBenchmark.testList.add (parentDirName+"/"+dirList[i].getName());
            } else if (dirList[i].isDirectory()) {
                getTestList (dirList[i], parentDirName+"/"+dirList[i].getName());
            }
        }
        if (OWL2ReasonersBenchmark.testList.size()>0) {
            return true;
        } else {
            return false;
        }  
    }
    private static String getFileName () {
        Calendar nowDate = new GregorianCalendar(); 
        return OWL2ReasonersBenchmark.RESULTS_DIR+"/"+new SimpleDateFormat("yyyy-MM-dd(hh-mm)").format(nowDate.getTime());  
    }
    private static void saveResults (String fileName) throws FileNotFoundException, IOException {
        try (FileOutputStream fileResults = new FileOutputStream(fileName+".xls")) {
            OWL2ReasonersBenchmark.results.write(fileResults);
            fileResults.close ();
        }
    }
    private static void createResult () {
        OWL2ReasonersBenchmark.results = new HSSFWorkbook ();
        OWL2ReasonersBenchmark.consistency = OWL2ReasonersBenchmark.results.createSheet("Consistency Checking");
        OWL2ReasonersBenchmark.classify = OWL2ReasonersBenchmark.results.createSheet("Classify");
        OWL2ReasonersBenchmark.realization = OWL2ReasonersBenchmark.results.createSheet("Realization");
    }
    public OWL2ReasonersBenchmark () throws IOException {
        this.getCFG (new Properties(), "./cfg.ini");
    }
    public static void main(String[] args) throws FileNotFoundException, OWLOntologyCreationException, OWLOntologyStorageException, IOException, IOException, Throwable {
        //System.out.println("Java libraries path: "+System.getProperty("java.library.path"));
        OWLOntologyManager manager;
        OWLOntology ontology;
        new OWL2ReasonersBenchmark ();
        String fileName = getFileName();
        HTMLLogger log = new HTMLLogger (fileName+".html");
        log.write("ALLERT", "The program was started");
        log.write("ATTENTION", "he config file was loaded:");
        log.write ("INFO", "Separator: "+OWL2ReasonersBenchmark.SEPARATOR);
        log.write ("INFO", "Results directory: "+OWL2ReasonersBenchmark.RESULTS_DIR);
        log.write ("INFO", "Ontologies directory: "+OWL2ReasonersBenchmark.TEST_DIR);
        log.write ("INFO", "Iterations value of ontologies test : "+OWL2ReasonersBenchmark.TEST_ITERATION);
        log.write ("INFO", "Delay time value: "+OWL2ReasonersBenchmark.DELAY_BETWEEN_ITERATION);
        log.write ("ATTENTION", "Reasoners list:");
        for (int i = 0; i < OWL2ReasonersBenchmark.REASONERS_LIST.length; i++) {
            log.write ("INFO", "Reasoner: "+OWL2ReasonersBenchmark.REASONERS_LIST[i]);
        }
        log.write ("ATTENTION", "The process of an ontologies list loading was started");
        log.write ("ATTENTION", "The ontologies list was loaded: "+OWL2ReasonersBenchmark.getTestList (new File(OWL2ReasonersBenchmark.TEST_DIR)));
        log.write ("ATTENTION", "The ontologies list: ");
        for (int i = 0; i < OWL2ReasonersBenchmark.testList.size(); i++) {
            log.write ("INFO", Integer.valueOf(i+1)+"th ontology: "+OWL2ReasonersBenchmark.testList.get(i));
        }
        manager = OWLManager.createOWLOntologyManager();
        Reasoning reasoning = new Reasoning ();
        createResult ();
        Row consistency = OWL2ReasonersBenchmark.consistency.createRow(0);
        Row classify = OWL2ReasonersBenchmark.classify.createRow(0);
        Row realization = OWL2ReasonersBenchmark.realization.createRow(0);
        consistency.createCell(0).setCellValue("Ontology name");
        consistency.createCell(1).setCellValue("Number");
        for (int i = 0; i < OWL2ReasonersBenchmark.REASONERS_LIST.length; i++) {
            consistency.createCell(i+2).setCellValue(OWL2ReasonersBenchmark.REASONERS_LIST[i]);
        }
        classify.createCell(0).setCellValue("Ontology name");
        classify.createCell(1).setCellValue("Number");
        for (int i = 0; i < OWL2ReasonersBenchmark.REASONERS_LIST.length; i++) {
            classify.createCell(i+2).setCellValue(OWL2ReasonersBenchmark.REASONERS_LIST[i]);
        }
        realization.createCell(0).setCellValue("Ontology name");
        realization.createCell(1).setCellValue("Number");
        for (int i = 0; i < OWL2ReasonersBenchmark.REASONERS_LIST.length; i++) {
            realization.createCell(i+2).setCellValue(OWL2ReasonersBenchmark.REASONERS_LIST[i]);
        }
        log.write ("ALLERT", "The making experiments process was started: ");
        for (int i = 0; i < OWL2ReasonersBenchmark.testList.size(); i++) {
            ontology = manager.loadOntologyFromOntologyDocument(new File(OWL2ReasonersBenchmark.TEST_DIR+"/"+testList.get(i)));
            log.write ("INFO", "The "+Integer.valueOf(i+1)+"th ontology ("+testList.get(i)+") was loaded");
            //File x = new File(OWL2ReasonersBenchmark.TEST_DIR+"/"+testList.get(i));
            ontology.getIndividualsInSignature();
            Set<OWLNamedIndividual> aBox = ontology.getIndividualsInSignature();
            Iterator<OWLNamedIndividual> individuals = aBox.iterator();
            consistency = OWL2ReasonersBenchmark.consistency.createRow(i+1);
            classify = OWL2ReasonersBenchmark.classify.createRow(i+1);
            realization = OWL2ReasonersBenchmark.realization.createRow(i+1);
            consistency.createCell(0).setCellValue(testList.get(i));
            consistency.createCell(1).setCellValue(i+1);
            classify.createCell(0).setCellValue(testList.get(i));
            classify.createCell(1).setCellValue(i+1);
            realization.createCell(0).setCellValue(testList.get(i));
            realization.createCell(1).setCellValue(i+1);
            for (int j = 0; j < OWL2ReasonersBenchmark.REASONERS_LIST.length; j++) {
                Long classifyTime = Long.valueOf(0), consistencyTime= Long.valueOf(0), realizationTime= Long.valueOf(0);
                log.write ("ALLERT", OWL2ReasonersBenchmark.REASONERS_LIST[j]+" was executed:");
                for (int k = 0; k < OWL2ReasonersBenchmark.REASONING_LIST.length; k++) {
                    if (!"TREASONER".equals(OWL2ReasonersBenchmark.REASONERS_LIST[j].toUpperCase())) {
                        log.write ("ATTENTION", "Method - "+OWL2ReasonersBenchmark.REASONING_LIST [k].toUpperCase()+ " was executed:");
                        switch (OWL2ReasonersBenchmark.REASONING_LIST [k].toUpperCase()) {
                            case "CONSISTENT":
                                for (int h = 1; h <= OWL2ReasonersBenchmark.TEST_ITERATION; h++) {
                                    reasoning.consistent (ontology, OWL2ReasonersBenchmark.REASONERS_LIST[j]);
                                    consistencyTime = consistencyTime+reasoning.zeit;
                                    log.write ("INFO", "Consistency checking time (nano sec): "+reasoning.zeit);
                                    Thread.sleep (OWL2ReasonersBenchmark.DELAY_BETWEEN_ITERATION);
                                }
                                break;
                            case "CLASSIFY":
                                for (int h = 1; h <= OWL2ReasonersBenchmark.TEST_ITERATION; h++) {
                                    reasoning.classify (ontology, OWL2ReasonersBenchmark.REASONERS_LIST[j]);
                                    classifyTime = classifyTime+reasoning.zeit;
                                    log.write ("INFO", "Classify classes time (nano sec): "+reasoning.zeit);
                                    Thread.sleep (OWL2ReasonersBenchmark.DELAY_BETWEEN_ITERATION);
                                }
                                break;
                            case "REALIZATION":
                                for (int h = 1; h <= OWL2ReasonersBenchmark.TEST_ITERATION; h++) {
                                    reasoning.realization(ontology, OWL2ReasonersBenchmark.REASONERS_LIST[j], individuals);
                                    realizationTime = realizationTime+reasoning.zeit;
                                    log.write ("INFO", "Realization time (nano sec): "+reasoning.zeit);
                                    Thread.sleep (OWL2ReasonersBenchmark.DELAY_BETWEEN_ITERATION);
                                }
                                break;    
                        }
                    } else {
                        switch (OWL2ReasonersBenchmark.REASONING_LIST [k].toUpperCase()) {
                            case "CONSISTENT":
                                for (int h = 1; h <= OWL2ReasonersBenchmark.TEST_ITERATION; h++) {
                                    reasoning.consistent (OWL2ReasonersBenchmark.TEST_DIR+"/"+testList.get(i));
                                    consistencyTime = consistencyTime+reasoning.zeit;
                                    log.write ("INFO", "Consistency checking time (nano sec): "+reasoning.zeit);
                                    Thread.sleep (OWL2ReasonersBenchmark.DELAY_BETWEEN_ITERATION);
                                }
                                break;
                            case "CLASSIFY":
                                for (int h = 1; h <= OWL2ReasonersBenchmark.TEST_ITERATION; h++) {
                                    reasoning.classify (OWL2ReasonersBenchmark.TEST_DIR+"/"+testList.get(i), null);
                                    classifyTime = classifyTime+reasoning.zeit;
                                    log.write ("INFO", "Classify classes time (nano sec): "+reasoning.zeit);
                                    Thread.sleep (OWL2ReasonersBenchmark.DELAY_BETWEEN_ITERATION);
                                }
                                break;
                        }
                    }
                }
                consistencyTime = consistencyTime/OWL2ReasonersBenchmark.TEST_ITERATION;
                classifyTime = classifyTime/OWL2ReasonersBenchmark.TEST_ITERATION;
                realizationTime = realizationTime/OWL2ReasonersBenchmark.TEST_ITERATION;
                log.write ("ATTENTION", "Average results:");
                log.write ("INFO", "Consistency checking time (nano sec): "+consistencyTime);
                log.write ("INFO", "Classify classes time (nano sec): "+classifyTime);
                log.write ("INFO", "Realization time (nano sec): "+realizationTime);
                consistency.createCell(j+2).setCellValue(consistencyTime);
                classify.createCell(j+2).setCellValue(classifyTime);
                realization.createCell(j+2).setCellValue(realizationTime);
            }
        manager.removeOntology(ontology);
        }
        OWL2ReasonersBenchmark.saveResults (fileName);
        log.write ("ALLERT", "The results was saved");
        log.write ("ALLERT", "The program has been completed");
        log.finalize();
    }
}
