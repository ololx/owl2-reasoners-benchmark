package owl2reasonersbenchmark;

import TReasoner.TReasoner;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Set;
import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.more.MOReReasoner;
import org.semanticweb.more.OWL2ReasonerManager;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import uk.ac.manchester.cs.factplusplus.owlapiv3.FaCTPlusPlusReasoner;
import uk.ac.manchester.cs.factplusplus.owlapiv3.FaCTPlusPlusReasonerFactory;
import uk.ac.manchester.cs.jfact.JFactFactory;

/**
 * The type Reasoning.
 *
 * @author Alexander A. Kropotin
 * @project owl2-reasoners-benchmark
 * @created 25.06.2014 12:12 <p>
 */
public class Reasoning {
    public boolean consistency;
    public Set<OWLSubClassOfAxiom> taxonomy;
    public NodeSet <OWLClass> types;
    public long zeit;
    protected void finalize () throws Throwable {
        try {
            this.consistency = false;
            this.zeit = 0;
            this.taxonomy = null;
        } finally {
            super.finalize();
        }
    }
    public Reasoning () {
        
    }
    private TReasoner setReasoner (String ontology) {
        TReasoner reasoner = new TReasoner ();
        reasoner.loadOntology(ontology, 
                true, //A_Checker 
                true, //backJumping
                true, //Caching
                false, //Show Stats
                false, //Global Caching
                30000000);//Time Limit
        return reasoner;   
    }
    private MOReReasoner setReasoner (OWLOntology ontology, String name) {
        MOReReasoner reasoner = new MOReReasoner (ontology);
        switch (name.toUpperCase()) {
            case "MORE-HERMIT":
                reasoner.setReasoner(OWL2ReasonerManager.HERMIT);
                break;
            case "MORE-PELLET":
                reasoner.setReasoner(OWL2ReasonerManager.PELLET);
                break;
            case "MORE-JFACT":
                reasoner.setReasoner(OWL2ReasonerManager.JFACT);
                break;
            default:
                reasoner.setReasoner(OWL2ReasonerManager.HERMIT);
                break;    
        }  
        return reasoner;
    }
    private Reasoner setReasoner (OWLOntology ontology) {
        Reasoner reasoner = new Reasoner (ontology);
        return reasoner; 
    }
    private OWLReasoner setReasoner (OWLOntology ontology, JFactFactory factory) {
        OWLReasoner reasoner = factory.createReasoner(ontology);
        return reasoner; 
    }
    private FaCTPlusPlusReasoner setReasoner (OWLOntology ontology, FaCTPlusPlusReasonerFactory factory) {
        FaCTPlusPlusReasoner reasoner = (FaCTPlusPlusReasoner) factory.createReasoner(ontology);
        return reasoner; 
    }
    public boolean consistent (OWLOntology ontology, String name) {
        if (name.toUpperCase().indexOf("MORE") != -1) {
            this.consistency = consistent (setReasoner (ontology, name));
        } else {
            switch (name.toUpperCase()) {
            case "HERMIT":    
                this.consistency = consistent (setReasoner (ontology));
                break;    
            case "JFACT":
                JFactFactory jFactory = new JFactFactory();
                this.consistency = consistent (setReasoner (ontology, jFactory));
                break;
            case "FACT++":
                FaCTPlusPlusReasonerFactory fFactory = new FaCTPlusPlusReasonerFactory();
                this.consistency = consistent (setReasoner (ontology, fFactory));
                break;
            }  
        }
        return this.consistency;
    }
    public boolean consistent (String ontology) {
        return consistent (setReasoner(ontology));  
    }
    public boolean consistent (TReasoner reasoner) {
        long before = System.nanoTime();
        this.consistency = reasoner.isConsistent();
        long after = System.nanoTime();
        this.zeit = after - before;
        return this.consistency;
    }
    public boolean consistent (MOReReasoner reasoner) {
        long before = System.nanoTime();
        this.consistency = reasoner.isConsistent();
        long after = System.nanoTime();
        this.zeit = after - before;
        reasoner.dispose();
        return this.consistency;
    }
    public boolean consistent (Reasoner reasoner) {
        long before = System.nanoTime();
        this.consistency = reasoner.isConsistent();
        long after = System.nanoTime();
        this.zeit = after - before;
        reasoner.dispose();
        return this.consistency;
    }
    public boolean consistent (OWLReasoner reasoner) {
        long before = System.nanoTime();
        this.consistency = reasoner.isConsistent();
        long after = System.nanoTime();
        this.zeit = after - before;
        reasoner.dispose();
        return this.consistency;
    }
    public boolean consistent (FaCTPlusPlusReasoner reasoner) {
        long before = System.nanoTime();
        this.consistency = reasoner.isConsistent();
        long after = System.nanoTime();
        this.zeit = after - before;
        reasoner.dispose();
        return this.consistency;
    }
    public Set<OWLSubClassOfAxiom> classify (OWLOntology ontology, String name) {
        if (name.toUpperCase().indexOf("MORE") != -1) {
            this.consistency = consistent (setReasoner (ontology, name));
        } else {
            switch (name.toUpperCase()) {
            case "HERMIT":    
                classify (setReasoner (ontology));
                break;    
            case "JFACT":
                JFactFactory jFactory = new JFactFactory();
                classify (setReasoner (ontology, jFactory));
                break;
            case "FACT++":
                FaCTPlusPlusReasonerFactory fFactory = new FaCTPlusPlusReasonerFactory();
                classify (setReasoner (ontology, fFactory));
                break;
            }  
        }
        return this.taxonomy;
    }
    public Set<OWLSubClassOfAxiom> classify (String ontology, String out) throws FileNotFoundException, FileNotFoundException, OWLOntologyCreationException, OWLOntologyStorageException {
        return classify (setReasoner(ontology), out);  
    }
    public Set<OWLSubClassOfAxiom> classify (TReasoner reasoner, String out) throws FileNotFoundException, OWLOntologyCreationException, OWLOntologyStorageException {
        long before = System.nanoTime();
        this.taxonomy = reasoner.classifyOntology(out);
        long after = System.nanoTime();
        this.zeit = after - before;
        return this.taxonomy;
    }
    public void classify (MOReReasoner reasoner) {
        long before = System.nanoTime();
        reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);
        long after = System.nanoTime();
        this.zeit = after - before;
    }
    public void classify (Reasoner reasoner) {
        long before = System.nanoTime();
        reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);
        long after = System.nanoTime();
        this.zeit = after - before;
    } 
    public void classify (OWLReasoner reasoner)  {
        long before = System.nanoTime();
        reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);
        long after = System.nanoTime();
        this.zeit = after - before;
    }
    public void classify (FaCTPlusPlusReasoner reasoner)  {
        long before = System.nanoTime();
        reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);
        long after = System.nanoTime();
        this.zeit = after - before;
    }
    public NodeSet realization (OWLOntology ontology, String name, OWLNamedIndividual individual) {
        if (name.toUpperCase().indexOf("MORE") != -1) {
            this.types = realization (setReasoner (ontology, name), individual);
        } else {
            switch (name.toUpperCase()) {
            case "HERMIT":    
                this.types = realization (setReasoner (ontology), individual);
                break;    
            case "JFACT":
                JFactFactory jFactory = new JFactFactory();
                this.types = realization (setReasoner (ontology, jFactory), individual);
                break;
            case "FACT++":
                FaCTPlusPlusReasonerFactory fFactory = new FaCTPlusPlusReasonerFactory();
                this.types = realization (setReasoner (ontology, fFactory),individual);
                break;    
            }  
        }
        return this.types; 
    }
    public NodeSet realization (OWLOntology ontology, String name, Iterator<OWLNamedIndividual> individual) {
        if (name.toUpperCase().indexOf("MORE") != -1) {
            this.types = realization (setReasoner (ontology, name), individual);
        } else {
            switch (name.toUpperCase()) {
            case "HERMIT":    
                this.types = realization (setReasoner (ontology), individual);
                break;    
            case "JFACT":
                JFactFactory jFactory = new JFactFactory();
                this.types = realization (setReasoner (ontology, jFactory), individual);
                break;
            case "FACT++":
                FaCTPlusPlusReasonerFactory fFactory = new FaCTPlusPlusReasonerFactory();
                this.types = realization (setReasoner (ontology, fFactory),individual);
                break;    
            }  
        }
        return this.types; 
    }
    public NodeSet realization (OWLReasoner reasoner, OWLNamedIndividual individual) {
        long before = System.nanoTime();
        classify (reasoner);
        this.types = reasoner.getTypes(individual, consistency);
        long after = System.nanoTime();
        this.zeit = after - before;
        return this.types;  
    }
    public NodeSet realization (FaCTPlusPlusReasoner reasoner, OWLNamedIndividual individual) {
        long before = System.nanoTime();
        classify (reasoner);
        this.types = reasoner.getTypes(individual, consistency);
        long after = System.nanoTime();
        this.zeit = after - before;
        return this.types;  
    }
    public NodeSet realization (MOReReasoner reasoner, OWLNamedIndividual individual) {
        long before = System.nanoTime();
        classify (reasoner);
        this.types = reasoner.getTypes(individual, consistency);
        long after = System.nanoTime();
        this.zeit = after - before;
        return this.types;  
    }
    public NodeSet realization (Reasoner reasoner, OWLNamedIndividual individual) {
        long before = System.nanoTime();
        classify (reasoner);
        this.types = reasoner.getTypes(individual, consistency);
        long after = System.nanoTime();
        this.zeit = after - before;
        return this.types;  
    }
    public NodeSet realization (Reasoner reasoner, Iterator<OWLNamedIndividual> individual) {
        long before = System.nanoTime();
        classify (reasoner);
        long after = System.nanoTime();
        this.zeit = after - before;
        while (individual.hasNext()) {
            before = System.nanoTime();
            this.types = reasoner.getTypes(individual.next(), consistency);
            after = System.nanoTime();
            this.zeit = this.zeit + (after - before);
        }
        return this.types;  
    }
    public NodeSet realization (MOReReasoner reasoner, Iterator<OWLNamedIndividual> individual) {
        long before = System.nanoTime();
        classify (reasoner);
        long after = System.nanoTime();
        this.zeit = after - before;
        while (individual.hasNext()) {
            before = System.nanoTime();
            this.types = reasoner.getTypes(individual.next(), consistency);
            after = System.nanoTime();
            this.zeit = this.zeit + (after - before);
        }
        return this.types;  
    }
    public NodeSet realization (OWLReasoner reasoner, Iterator<OWLNamedIndividual> individual) {
        long before = System.nanoTime();
        classify (reasoner);
        long after = System.nanoTime();
        this.zeit = after - before;
        while (individual.hasNext()) {
            before = System.nanoTime();
            this.types = reasoner.getTypes(individual.next(), consistency);
            after = System.nanoTime();
            this.zeit = this.zeit + (after - before);
        }
        return this.types;  
    }
    public NodeSet realization (FaCTPlusPlusReasoner reasoner, Iterator<OWLNamedIndividual> individual) {
        long before = System.nanoTime();
        classify (reasoner);
        long after = System.nanoTime();
        this.zeit = after - before;
        while (individual.hasNext()) {
            before = System.nanoTime();
            this.types = reasoner.getTypes(individual.next(), consistency);
            after = System.nanoTime();
            this.zeit = this.zeit + (after - before);
        }
        return this.types;  
    }
}

