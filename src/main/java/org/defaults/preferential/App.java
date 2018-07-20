package org.defaults.preferential;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.io.PrintWriter;

import org.rdfhdt.hdt.hdt.HDT;
import org.rdfhdt.hdt.hdt.HDTManager;
import org.rdfhdt.hdtjena.HDTGraph;
import org.rdfhdt.hdt.triples.IteratorTripleString;
import org.rdfhdt.hdt.triples.TripleString;


import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLDataFactory;
import java.io.File;

public class App 
{
    public static int classCount = 0;
    public static int propertyCount = 0;
    public static int listCount = 0;
    public static int classAssCount = 0;
    public static int relationAssCount = 0;
    
    public static int classCountMax = 100;
    public static int propertyCountMax = 50;
    public static int listCountMax = 100;
    public static int classAssCountMax = 100;
    public static int relationAssCountMax = 100;

    public static void main( String[] args ) throws Exception
    {
		// Load HDT file NOTE: Use loadIndexedHDT() if you are doing ?P?, ?PO, ??O queries
		HDT hdt = HDTManager.mapIndexedHDT("/home/lwpb/LOD_a_lot_v1.hdt", null);
		
		// Enumerate all triples. Empty string means "any"
		System.out.println("Starting search");
                //String query = "SELECT ?a ?C ?D WHERE { ?a <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?C . ?a <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?D . ?C <http://www.w3.org/2002/07/owl#disjointWith> ?D . } LIMIT 1";
                String query = "SELECT ?C ?D WHERE { ?C <http://www.w3.org/2000/01/rdf-schema#subClassOf> ?D . }";
		ResultSet results = sparqlQuery(hdt, query);
                System.out.println(results.hasNext());
                int counter = 0;
                Set<CharSequence> tried = new HashSet<>();
                Set<CharSequence> standard = new HashSet<>();
                standard.add("http://www.w3.org/2000/01/rdf-schema#Resource");
                standard.add("http://www.w3.org/2000/01/rdf-schema#Class");
                standard.add("http://www.w3.org/2000/01/rdf-schema#subClassOf");
                standard.add("http://www.w3.org/2000/01/rdf-schema#subPropertyOf");
                standard.add("http://www.w3.org/2000/01/rdf-schema#comment");
                standard.add("http://www.w3.org/2000/01/rdf-schema#label");
                standard.add("http://www.w3.org/2000/01/rdf-schema#domain");
                standard.add("http://www.w3.org/2000/01/rdf-schema#range");
                standard.add("http://www.w3.org/2000/01/rdf-schema#seeAlso");
                standard.add("http://www.w3.org/2000/01/rdf-schema#isDefinedBy");
                standard.add("http://www.w3.org/2000/01/rdf-schema#Literal");
                standard.add("http://www.w3.org/2000/01/rdf-schema#Container");
                standard.add("http://www.w3.org/2000/01/rdf-schema#ContainerMembershipProperty");
                standard.add("http://www.w3.org/2000/01/rdf-schema#member");
                standard.add("http://www.w3.org/2000/01/rdf-schema#Datatype");
                standard.add("http://www.w3.org/2002/07/owl#Thing");
                standard.add("http://www.w3.org/2002/07/owl#Nothing");
                standard.add("http://www.w3.org/2002/07/owl#Class");
                standard.add("http://www.w3.org/2000/01/rdf-schema#Class");
                standard.add("http://www.w3.org/2000/01/rdf-schema#subClassOf");
                standard.add("http://www.w3.org/2002/07/owl#AllDifferent");
                standard.add("http://www.w3.org/2002/07/owl#AllDisjointClasses");
                standard.add("http://www.w3.org/2002/07/owl#AllDisjointProperties");
                standard.add("http://www.w3.org/2002/07/owl#Annotation");
                standard.add("http://www.w3.org/2002/07/owl#AnnotationProperty");
                standard.add("http://www.w3.org/2002/07/owl#AsymmetricProperty");
                standard.add("http://www.w3.org/2002/07/owl#Axiom");
                standard.add("http://www.w3.org/2002/07/owl#Class");
                standard.add("http://www.w3.org/2002/07/owl#DataRange");
                standard.add("http://www.w3.org/2002/07/owl#DatatypeProperty");
                standard.add("http://www.w3.org/2002/07/owl#DeprecatedClass");
                standard.add("http://www.w3.org/2002/07/owl#DeprecatedProperty");
                standard.add("http://www.w3.org/2002/07/owl#FunctionalProperty");
                standard.add("http://www.w3.org/2002/07/owl#InverseFunctionalProperty");
                standard.add("http://www.w3.org/2002/07/owl#IrreflexiveProperty");
                standard.add("http://www.w3.org/2002/07/owl#NamedIndividual");
                standard.add("http://www.w3.org/2002/07/owl#NegativePropertyAssertion");
                standard.add("http://www.w3.org/2002/07/owl#Nothing");
                standard.add("http://www.w3.org/2002/07/owl#ObjectProperty");
                standard.add("http://www.w3.org/2002/07/owl#Ontology");
                standard.add("http://www.w3.org/2002/07/owl#OntologyProperty");
                standard.add("http://www.w3.org/2002/07/owl#ReflexiveProperty");
                standard.add("http://www.w3.org/2002/07/owl#Restriction");
                standard.add("http://www.w3.org/2002/07/owl#SymmetricProperty");
                standard.add("http://www.w3.org/2002/07/owl#TransitiveProperty");
                standard.add("http://www.w3.org/2002/07/owl#Thing");
                standard.add("http://www.w3.org/2002/07/owl#allValuesFrom");
                standard.add("http://www.w3.org/2002/07/owl#annotatedProperty");
                standard.add("http://www.w3.org/2002/07/owl#annotatedSource");
                standard.add("http://www.w3.org/2002/07/owl#annotatedTarget");
                standard.add("http://www.w3.org/2002/07/owl#assertionProperty");
                standard.add("http://www.w3.org/2002/07/owl#backwardCompatibleWith");
                standard.add("http://www.w3.org/2002/07/owl#bottomDataProperty");
                standard.add("http://www.w3.org/2002/07/owl#bottomObjectProperty");
                standard.add("http://www.w3.org/2002/07/owl#cardinality");
                standard.add("http://www.w3.org/2002/07/owl#complementOf");
                standard.add("http://www.w3.org/2002/07/owl#datatypeComplementOf");
                standard.add("http://www.w3.org/2002/07/owl#deprecated");
                standard.add("http://www.w3.org/2002/07/owl#differentFrom");
                standard.add("http://www.w3.org/2002/07/owl#disjointUnionOf");
                standard.add("http://www.w3.org/2002/07/owl#disjointWith");
                standard.add("http://www.w3.org/2002/07/owl#distinctMembers");
                standard.add("http://www.w3.org/2002/07/owl#equivalentClass");
                standard.add("http://www.w3.org/2002/07/owl#equivalentProperty");
                standard.add("http://www.w3.org/2002/07/owl#hasKey");
                standard.add("http://www.w3.org/2002/07/owl#hasSelf");
                standard.add("http://www.w3.org/2002/07/owl#hasValue");
                standard.add("http://www.w3.org/2002/07/owl#imports");
                standard.add("http://www.w3.org/2002/07/owl#incompatibleWith");
                standard.add("http://www.w3.org/2002/07/owl#intersectionOf");
                standard.add("http://www.w3.org/2002/07/owl#inverseOf");
                standard.add("http://www.w3.org/2002/07/owl#maxCardinality");
                standard.add("http://www.w3.org/2002/07/owl#maxQualifiedCardinality");
                standard.add("http://www.w3.org/2002/07/owl#members");
                standard.add("http://www.w3.org/2002/07/owl#minCardinality");
                standard.add("http://www.w3.org/2002/07/owl#minQualifiedCardinality");
                standard.add("http://www.w3.org/2002/07/owl#onClass");
                standard.add("http://www.w3.org/2002/07/owl#onDataRange");
                standard.add("http://www.w3.org/2002/07/owl#onDatatype");
                standard.add("http://www.w3.org/2002/07/owl#oneOf");
                standard.add("http://www.w3.org/2002/07/owl#onProperties");
                standard.add("http://www.w3.org/2002/07/owl#onProperty");
                standard.add("http://www.w3.org/2002/07/owl#priorVersion");
                standard.add("http://www.w3.org/2002/07/owl#propertyChainAxiom");
                standard.add("http://www.w3.org/2002/07/owl#propertyDisjointWith");
                standard.add("http://www.w3.org/2002/07/owl#qualifiedCardinality");
                standard.add("http://www.w3.org/2002/07/owl#sameAs");
                standard.add("http://www.w3.org/2002/07/owl#someValuesFrom");
                standard.add("http://www.w3.org/2002/07/owl#sourceIndividual");
                standard.add("http://www.w3.org/2002/07/owl#targetIndividual");
                standard.add("http://www.w3.org/2002/07/owl#targetValue");
                standard.add("http://www.w3.org/2002/07/owl#topDataProperty");
                standard.add("http://www.w3.org/2002/07/owl#topObjectProperty");
                standard.add("http://www.w3.org/2002/07/owl#unionOf");
                standard.add("http://www.w3.org/2002/07/owl#versionInfo");
                standard.add("http://www.w3.org/2002/07/owl#versionIRI");
                standard.add("http://www.w3.org/2002/07/owl#withRestrictions");
                standard.add("http://www.w3.org/1999/02/22-rdf-syntax-ns#HTML");
                standard.add("http://www.w3.org/1999/02/22-rdf-syntax-ns#langString");
                standard.add("http://www.w3.org/1999/02/22-rdf-syntax-ns#PlainLiteral");
                standard.add("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
                standard.add("http://www.w3.org/1999/02/22-rdf-syntax-ns#Property");
                standard.add("http://www.w3.org/1999/02/22-rdf-syntax-ns#Statement");
                standard.add("http://www.w3.org/1999/02/22-rdf-syntax-ns#subject");
                standard.add("http://www.w3.org/1999/02/22-rdf-syntax-ns#predicate");
                standard.add("http://www.w3.org/1999/02/22-rdf-syntax-ns#object");
                standard.add("http://www.w3.org/1999/02/22-rdf-syntax-ns#Bag");
                standard.add("http://www.w3.org/1999/02/22-rdf-syntax-ns#Seq");
                standard.add("http://www.w3.org/1999/02/22-rdf-syntax-ns#Alt");
                standard.add("http://www.w3.org/1999/02/22-rdf-syntax-ns#value");
                standard.add("http://www.w3.org/1999/02/22-rdf-syntax-ns#List");
                standard.add("http://www.w3.org/1999/02/22-rdf-syntax-ns#nil");
                standard.add("http://www.w3.org/1999/02/22-rdf-syntax-ns#first");
                standard.add("http://www.w3.org/1999/02/22-rdf-syntax-ns#rest");
                standard.add("http://www.w3.org/1999/02/22-rdf-syntax-ns#XMLLiteral");
		while(results.hasNext()) {
                    PrintWriter out = new PrintWriter("/home/lwpb/PreferentialDefaults/test" + String.valueOf(counter) + ".nt", "UTF-8");
		    QuerySolution sol = results.next();
                    Set<CharSequence> search = new HashSet<CharSequence>();
		    String c = sol.getResource("?C").getURI();
                    search.add(c);
		    String d = sol.getResource("?D").getURI();
                    search.add(d);
                    if(tried.contains(c) || tried.contains(d)) {
                        continue;
                    }
                    tried.add(c);
                    tried.add(d);
                    StringBuilder build = new StringBuilder(1024);
                    Set<CharSequence> known = new HashSet<>();
                    for(CharSequence fromStandard : standard) {
                        known.add(fromStandard);
                    }
                    System.out.println("Starting search");
                    for(TripleString st : getClassRelations(search, hdt, 3, known)) {
			build.delete(0, build.length());
                        st.dumpNtriple(build);
                        out.print(build);
                        //System.out.println(st);
                    }
                    out.close();
                    System.out.println("Starting consistency check");
		    OWLOntologyManager man = OWLManager.createOWLOntologyManager();
                    File writtenOntology = new File("/home/lwpb/PreferentialDefaults/test" + String.valueOf(counter) + ".nt");
		    OWLOntology ont = man.loadOntologyFromOntologyDocument(writtenOntology);
		    Reasoner hermit = new Reasoner(ont);
                    if(!hermit.isConsistent()) {
                        counter++;
                    }
                    System.out.println(counter);
                    System.out.println(tried.size());
                    classCount = 0;
                    propertyCount = 0;
                    listCount = 0;
                    classAssCount = 0;
                    relationAssCount = 0;
		}
    }

    public static ResultSet sparqlQuery(HDT hdt, String sparqlQuery) {
	//HDT hdt = HDTManager.mapIndexedHDT(fileHDT, null);
        ResultSet results = null;
	try {
		// Create Jena wrapper on top of HDT.
            HDTGraph graph = new HDTGraph(hdt);
            Model model = ModelFactory.createModelForGraph(graph);

            // Use Jena ARQ to execute the query.
            Query query = QueryFactory.create(sparqlQuery);
            QueryExecution qe = QueryExecutionFactory.create(query, model);
            
            results = qe.execSelect();
	} catch (Exception e) {
            System.out.println(e);
        } finally {			
            return results;
	}
    }

    public static boolean add(IteratorTripleString it, ArrayList<TripleString> result, Set<CharSequence> set, int where, int type) {
            switch(type) {
                case 0: if(classCount == classCountMax) {
                            return false;
                        }
                        break;
                case 1: if(propertyCount == propertyCountMax) {
                            return false;
                        }
                        break;
                case 2: if(listCount == listCountMax) {
                            return false;
                        }
                        break;
            }
            if(!it.hasNext()) {
                return false;
            }
            switch(type) {
                case 0: classCount++;
                        break;
                case 1: propertyCount++;
                        break;
                case 2: listCount++;
                        break;
            }
            TripleString next = it.next();
            result.add(next);
            switch(where) {
                case 0: set.add(next.getSubject());
                        break;
                case 1: set.add(next.getPredicate());
                        break;
                case 2: set.add(next.getObject());
                        break;
            }
            return true;
        }

    public static ArrayList<TripleString> getPropertyRelations(Set<CharSequence> containing, HDT hdt, int depth, Set<CharSequence> known) throws Exception {
        ArrayList<TripleString> result = new ArrayList<>();
        Set<CharSequence> classes = new HashSet<>();
        Set<CharSequence> properties = new HashSet<>();
	TripleString next = null;
        for(CharSequence p : containing) {
            IteratorTripleString it = hdt.search("", "http://www.w3.org/2002/07/owl#propertyDisjointWith", p);
            while(add(it,result,properties,0,1)) {}
            it = hdt.search(p, "http://www.w3.org/2002/07/owl#propertyDisjointWith", "");
            while(add(it,result,properties,2,1)) {}  
            it = hdt.search(p, "http://www.w3.org/2002/07/owl#equivalentProperty", "");
            while(add(it,result,properties,2,1)) {}  
            it = hdt.search("", "http://www.w3.org/2002/07/owl#equivalentProperty", p);
            while(add(it,result,properties,0,1)) {}  
            it = hdt.search("", "http://www.w3.org/2002/07/owl#onProperty", p);
            while(add(it,result,classes,0,0)) {}  
            it = hdt.search("", p, "");
            while(it.hasNext() && relationAssCount < relationAssCountMax) {
                 result.add(it.next());
                 relationAssCount++;
            }
        }
        for(CharSequence c : containing) {
            properties.remove(c);
        }
        for(CharSequence c : known) {
            properties.remove(c);
        }
        for(CharSequence c : containing) {
            known.add(c);
        }
        if(depth > 0) {
            result.addAll(getClassRelations(classes, hdt, depth - 1, known));
            result.addAll(getPropertyRelations(properties, hdt, depth - 1, known));
        }
        return result;
    }

    public static ArrayList<TripleString> getListRelations(Set<CharSequence> containing, HDT hdt, int depth, Set<CharSequence> known) throws Exception {
        ArrayList<TripleString> result = new ArrayList<>();
        Set<CharSequence> lists = new HashSet<>();
	TripleString next = null;
        for(CharSequence l : containing) {
            IteratorTripleString it = hdt.search(l, "http://www.w3.org/1999/02/22-rdf-syntax-ns#first", "");
            while(it.hasNext()) {
                next = it.next();
                result.add(next);
            }
            it = hdt.search(l, "http://www.w3.org/1999/02/22-rdf-syntax-ns#rest", "");
            while(add(it,result,lists,2,2)) {}  
        }
        for(CharSequence c : containing) {
            known.add(c);
        }
        for(CharSequence c : known) {
            lists.remove(c);
        }
        if(depth > 0) {
            result.addAll(getListRelations(lists, hdt, depth - 1, known));
        }
        return result;
    }

    public static ArrayList<TripleString> getClassRelations(Set<CharSequence> containing, HDT hdt, int depth, Set<CharSequence> known) throws Exception {
        ArrayList<TripleString> result = new ArrayList<>();
        Set<CharSequence> classes = new HashSet<>();
        Set<CharSequence> properties = new HashSet<>();
        Set<CharSequence> lists = new HashSet<>();
        TripleString next = null;
        for(CharSequence c : containing) {
            IteratorTripleString it = hdt.search("", "http://www.w3.org/2002/07/owl#allValuesFrom", c);
            while(add(it,result,classes,0,0)) {}
            it = hdt.search(c, "http://www.w3.org/2002/07/owl#allValuesFrom", "");
            while(add(it,result,classes,2,0)) {}
            it = hdt.search(c, "http://www.w3.org/2002/07/owl#cardinality", "");
            it.forEachRemaining(result::add);
            it = hdt.search(c, "http://www.w3.org/2002/07/owl#complementOf", "");
            while(add(it,result,classes,2,0)) {}
            it = hdt.search("", "http://www.w3.org/2002/07/owl#complementOf", c);
            while(add(it,result,classes,0,0)) {}
            it = hdt.search(c, "http://www.w3.org/2002/07/owl#disjointUnionOf", "");
            while(add(it,result,lists,2,2)) {}
            it = hdt.search(c, "http://www.w3.org/2002/07/owl#disjointWith", "");
            while(add(it,result,classes,2,0)) {}
            it = hdt.search("", "http://www.w3.org/2002/07/owl#disjointWith", c);
            while(add(it,result,classes,0,0)) {}
            it = hdt.search(c, "http://www.w3.org/2002/07/owl#equivalentClass", "");
            while(add(it,result,classes,2,0)) {}
            it = hdt.search("", "http://www.w3.org/2002/07/owl#equivalentClass", c);
            while(add(it,result,classes,0,0)) {}
            it = hdt.search(c, "http://www.w3.org/2002/07/owl#intersectionOf", "");
            while(add(it,result,lists,2,2)) {}
            it = hdt.search(c, "http://www.w3.org/2002/07/owl#maxCardinality", "");
            it.forEachRemaining(result::add);
            it = hdt.search(c, "http://www.w3.org/2002/07/owl#maxQualifiedCardinality", "");
            it.forEachRemaining(result::add);
            it = hdt.search(c, "http://www.w3.org/2002/07/owl#minCardinality", "");
            it.forEachRemaining(result::add);
            it = hdt.search(c, "http://www.w3.org/2002/07/owl#minQualifiedCardinality", "");
            it.forEachRemaining(result::add);
            it = hdt.search(c, "http://www.w3.org/2002/07/owl#onClass", "");
            while(add(it,result,classes,2,0)) {}
            it = hdt.search("", "http://www.w3.org/2002/07/owl#onClass", c);
            while(add(it,result,classes,0,0)) {}
            it = hdt.search(c, "http://www.w3.org/2002/07/owl#onDataRange", "");
            it.forEachRemaining(result::add);
            it = hdt.search(c, "http://www.w3.org/2002/07/owl#oneOf", "");
            while(add(it,result,lists,2,2)) {}
            it = hdt.search(c, "http://www.w3.org/2002/07/owl#onProperties", "");
            while(add(it,result,lists,2,2)) {}
            it = hdt.search(c, "http://www.w3.org/2002/07/owl#onProperty", "");
            while(add(it,result,properties,2,1)) {}
            it = hdt.search(c, "http://www.w3.org/2002/07/owl#qualifiedCardinality", "");
            it.forEachRemaining(result::add);
            it = hdt.search(c, "http://www.w3.org/2002/07/owl#someValuesFrom", "");
            while(add(it,result,classes,2,0)) {}
            it = hdt.search("", "http://www.w3.org/2002/07/owl#someValuesFrom", c);
            while(add(it,result,classes,0,0)) {}
            it = hdt.search(c, "http://www.w3.org/2002/07/owl#unionOf", "");
            while(add(it,result,lists,2,2)) {}
            it = hdt.search(c, "http://www.w3.org/2000/01/rdf-schema#subClassOf", "");
            while(add(it,result,classes,2,0)) {}
            it = hdt.search("", "http://www.w3.org/2000/01/rdf-schema#subClassOf", c);
            while(add(it,result,classes,0,0)) {}
            it = hdt.search("", "http://www.w3.org/2000/01/rdf-schema#domain", c);
            while(add(it,result,properties,0,1)) {}
            it = hdt.search("", "http://www.w3.org/2000/01/rdf-schema#range", c);
            while(add(it,result,properties,0,1)) {}
            it = hdt.search("", "http://www.w3.org/1999/02/22-rdf-syntax-ns#type", c);
            while(it.hasNext() && classAssCount < classAssCountMax) {
                 result.add(it.next());
                 classAssCount++;
            }
        }
        for(CharSequence c : containing) {
            known.add(c);
        }
        for(CharSequence c : known) {
            classes.remove(c);
        }
        if(depth > 0) {
            result.addAll(getClassRelations(classes, hdt, depth - 1, known));
            result.addAll(getPropertyRelations(properties, hdt, depth - 1, known));
            result.addAll(getListRelations(lists, hdt, depth - 1, known));
        }
        return result;
    }

}
